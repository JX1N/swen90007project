var choosenUser;

$(function () {
    initUserTable();
    $('input[type=radio][name=roleRD]').change(function() {
        if (this.value == 0) {
            $('#studentInfo').hide();
        }
        else {
            $('#studentInfo').show();
        }
    });
    $('input[type=radio][name=updateRoleRD]').change(function() {
        if (this.value == 0) {
            $('#updateStudentInfo').hide();
        }
        else {
            $('#updateStudentInfo').show();
        }
    });
});

function initUserTable() {
    $('#tb_user').bootstrapTable('destroy');
    $('#tb_user').bootstrapTable({
        url: "/user/list",
        method:'POST',
        pagination: true,
        pageSize: 20,
        pageList:[10,15,20,25],
        maintainSelected:true,
        search:true,
        toolbar:'#toolbar',
        sortable: true,
        sortOrder: "asc",
        sortName:'id',
        clickToSelect: false,
        cardView: false,
        detailView: false,
        columns: [{
            checkbox:true,
        },{
            field: 'id',
            title: 'ID'
        },{
            title: 'Role',
            formatter:function (value,row,index) {
                if (row.role==0) {
                    return 'Admin';
                } else {
                    return 'Student';
                }
            }
        },{
            field: 'username',
            title: 'Username',
        },{
            title: 'Name',
            formatter:function (value,row,index) {
                var re='';
                if(row.firstName!=null){
                    re+=row.firstName+' ';
                }
                if(row.lastName!=null){
                    re+=row.lastName;
                }
                return re;
            }
        },{
            title: 'Operation',
            formatter:function (value,row,index) {
                un=row.username;
                return '<input id="btnResetPassword'+un+'" type="button" class="btn btn-primary" onclick="showResetModal(id)" value="Reset Password"/>';
            }
        }],
        onClickCell: function (field,value,row) {
            if(field==0||field==5){
            }else {
                choosenUser = row;
                userShowDetail(row);
            }
        }
    });
};

function btnShowAddModal() {
    $("input[name='roleRD']").get(0).checked = true;
    $("input[name='genderRD']").get(0).checked = true;
    $('#addUserUsername').val('');
    $('#addUserPassword').val('');
    $('#addUserDegree').val('');
    $('#studentInfo').hide();
    $('#addFirstName').val('');
    $('#addLastName').val('');
    $('#addEmail').val('');
    $('#addPhone').val('');
    $('#addAddress').val('');
    $.ajax({
        url: "/degree/list",
        method: 'POST',
        success: function(data){
            for(var i=0;i<data.length;i++)
            {
                $('#selectAddUserDegree').append($('<option>', {
                    value: data[i].id,
                    text: data[i].degreeName
                }));
            }
        }
    });

    $('#addUserModal').modal();
}

function btnAddUser() {
    if($('#addUserUsername').val()==''){
        alert('Please input username');
    }else if($('#addUserPassword').val()==''){
        alert('Please input password');
    }else {

        var obj = {
            username: $('#addUserUsername').val(),
            password: $('#addUserPassword').val(),
            role: $("input[name='roleRD']:checked").val(),
            firstName: $('#addFirstName').val(),
            lastName: $('#addLastName').val(),
            gender: $("input[name='genderRD']:checked").val(),
            email: $('#addEmail').val(),
            phone: $('#addPhone').val(),
            address: $('#addAddress').val(),
        };
        $.ajax({
            url: "/user/add",
            method: 'POST',
            data: {
                degreeId: $('#selectAddUserDegree').val(),
                user: JSON.stringify(obj)
            },
            success: function (data) {
                if (data.message == 'success') {
                    alert("Success");
                    $('#addUserModal').modal('hide');
                    initUserTable();
                } else {
                    alert("Failed");
                }
            }
        });
    }
}

function btnDeleteAccount() {
    var rows = $('#tb_user').bootstrapTable('getSelections');
    if(rows.length==0)
    {
        alert("Please choose the users to be deleted");
    }else {
        var delete_confirm = confirm('This action will delete selected users!Are you sure?');
        if (delete_confirm) {
            var usernames = new Array();
            for (var i = 0; i < rows.length; i++) {
                usernames[i] = rows[i].username;
            }
            usernames = JSON.stringify(usernames);
            $.ajax({
                url: "/user/delete",
                type: "POST",
                data: {'usernames': usernames},
                success: function (data) {
                    if (data.message == 'success') {
                        alert("Success");
                        initUserTable();
                    }
                    else{
                        alert("Failed")
                    }
                }
            });
        }
    }
}

function showResetModal(btnID) {
    var username=btnID.replace('btnResetPassword', '');
    $('#resetPWDusername').val(username);
    $('#resetPWDpassword').val('');
    $('#modalResetPassword').modal()
}

function resetPassword() {
    var obj={
        username:$('#resetPWDusername').val(),
        password:$('#resetPWDpassword').val(),
    }
    $.ajax({
        url: "/user/reset",
        type: "POST",
        data: {user: JSON.stringify(obj)},
        success: function (data) {
            if (data.message == 'success') {
                alert("Success");
                $('#modalResetPassword').modal('hide')
            }
            else{
                alert("Failed")
            }
        }
    });
}



function userShowDetail(user) {

    $.ajax({
        url: "/degree/list",
        method: 'POST',
        success: function(data){
            for(var i=0;i<data.length;i++)
            {
                $('#selectUpdateUserDegree').append($('<option>', {
                    value: data[i].id,
                    text: data[i].degreeName
                }));
            }
            if(user.role==1) {
                $.ajax({
                    url: "/student/detail",
                    method: 'POST',
                    data: {
                        id: user.id,
                    },
                    success: function (data) {
                        user=data;
                        if (user.degreeName == null) {
                            $('#selectUpdateUserDegree').val('');
                        } else {
                            $("#selectUpdateUserDegree option:contains("+user.degreeName+")").attr('selected', true);
                        }
                    }
                });
            }
        }
    });

    $('#updateUserUsername').val(user.username);
    $("input[name='updateRoleRD']").get(user.role).checked = true;
    $('#updateFirstName').val(user.firstName);
    $('#updateLastName').val(user.lastName);
    $("input[name='updateGenderRD']").get(user.gender).checked = true;
    $('#updateEmail').val(user.email)
    $('#updatePhone').val(user.phone)
    $('#updateAddress').val(user.address)


    $('#btnUpdateSaveUser').text('Update');
    $('#updateUserUsername').attr('readonly', true);
    $("input[name='updateRoleRD']").attr('disabled', true);
    if(user.role==0){
        $('#updateStudentInfo').hide();
    }else{
        $('#updateStudentInfo').show();
    }
    $('#updateFirstName').attr('readonly', true);
    $('#updateLastName').attr('readonly', true);
    $("input[name='updateGenderRD']").attr('disabled', true);
    $('#updateEmail').attr('readonly', true);
    $('#updatePhone').attr('readonly', true);
    $('#updateAddress').attr('readonly', true);
    $('#selectUpdateUserDegree').attr('disabled', true);


    $('#divUserTable').attr('style','display:none');
    $('#divUserDetailed').attr('style','display:')

}


function showUpResetModal() {
    var username=choosenUser.username;
    $('#upResetPWDusername').val(username);
    $('#upResetPWDpassword').val('');
    $('#modalUpdateResetPassword').modal()

}


function upResetPassword() {
    var obj={
        username:$('#upResetPWDusername').val(),
        password:$('#upResetPWDpassword').val(),
    }
    $.ajax({
        url: "/user/reset",
        type: "POST",
        data: {user: JSON.stringify(obj)},
        success: function (data) {
            if (data.message == 'success') {
                alert("Success");
                $('#modalUpdateResetPassword').modal('hide')
            }
            else{
                alert("Failed")
            }
        }
    });
}

function btnUpdateSaveUser() {
    if($('#btnUpdateSaveUser').text()=='Update') {
        $('#btnUpdateSaveUser').text('Save');
        $('#updateUserUsername').attr('readonly', false);
        $("input[name='updateRoleRD']").attr('disabled', false);
        $('#updateFirstName').attr('readonly', false);
        $('#updateLastName').attr('readonly', false);
        $("input[name='updateGenderRD']").attr('disabled', false);
        $('#updateEmail').attr('readonly', false);
        $('#updatePhone').attr('readonly', false);
        $('#updateAddress').attr('readonly', false);
        $('#selectUpdateUserDegree').attr('disabled', false);
    } else {
        var obj = {
            id:choosenUser.id,
            username: $('#updateUserUsername').val(),
            role:$("input[name='updateRoleRD']:checked").val(),
            firstName:$('#updateFirstName').val(),
            lastName:$('#updateLastName').val(),
            gender:$("input[name='updateGenderRD']:checked").val(),
            email:$('#updateEmail').val(),
            phone:$('#updatePhone').val(),
            address:$('#updateAddress').val(),
        };
        $.ajax({
            url: "/user/update",
            method: 'POST',
            data: {
                user: JSON.stringify(obj),
                degreeId:$('#selectUpdateUserDegree').val()
            },
            success: function(data){
                if(data.message=='success') {
                    alert("Success");
                    userShowDetail(obj);
                    initUserTable();
                }
                else {
                    alert("Failed");
                }
            }
        });
    }
}


function btnBackToUserTable() {
    var back_confirm=true
    if($('#btnUpdateSaveUser').text()=='Save') {
        back_confirm = confirm('All the changs will not be save');
    }
    if(back_confirm) {
        choosenDegreeID = '';
        $('#updateUserUsername').val('');
        $("input[name='updateRoleRD']").get(0).checked = true;
        $('#updateFirstName').val('');
        $('#updateLastName').val('');
        $("input[name='updateGenderRD']").get(0).checked = true;
        $('#updateEmail').val('');
        $('#updatePhone').val('');
        $('#updateAddress').val('');
        $('#selectUpdateUserDegree').val('');
        $('#divUserTable').attr('style', 'display');
        $('#divUserDetailed').attr('style', 'display:none');
    }
}
