var choosenUser='';

$(function () {
    showUser();
})


function showUser() {
    $('#btnUpdateSaveUser').text('Update');
    $('#updateUsername').attr('readonly', true);
    $('#updateFirstName').attr('readonly', true);
    $('#updateLastName').attr('readonly', true);
    $('#updateDegree').attr('readonly', true);
    $("input[name='updateGenderRD']").attr('disabled', true);
    $('#updateEmail').attr('readonly', true);
    $('#updatePhone').attr('readonly', true);
    $('#updateAddress').attr('readonly', true);

    $.ajax({
        url: "/student/info",
        method: 'POST',
        success: function(data){
            choosenUser=data;
            $('#updateUsername').val(choosenUser.username);
            $('#updateFirstName').val(choosenUser.firstName);
            $('#updateLastName').val(choosenUser.lastName);
            $('#updateDegree').val(choosenUser.degreeName);
            $("input[name='updateGenderRD']").get(choosenUser.gender).checked = true;
            $('#updateEmail').val(choosenUser.email);
            $('#updatePhone').val(choosenUser.phone);
            $('#updateAddress').val(choosenUser.address);
        }
    });

}

function btnUpdateSaveUser() {
    if($('#btnUpdateSaveUser').text()=='Update') {
        $('#btnUpdateSaveUser').text('Save');
        $('#updateFirstName').attr('readonly', false);
        $('#updateLastName').attr('readonly', false);
        $("input[name='updateGenderRD']").attr('disabled', false);
        $('#updateEmail').attr('readonly', false);
        $('#updatePhone').attr('readonly', false);
        $('#updateAddress').attr('readonly', false);
    } else {
        var obj = {
            id:choosenUser.id,
            username: $('#updateUsername').val(),
            firstName:$('#updateFirstName').val(),
            lastName:$('#updateLastName').val(),
            email:$('#updateEmail').val(),
            phone:$('#updatePhone').val(),
            address:$('#updateAddress').val(),
            role:"1",
        };
        $.ajax({
            url: "/user/update",
            method: 'POST',
            data: {
                user: JSON.stringify(obj),
                degreeId:'-1',
            },
            success: function(data){
                if(data.message=='success') {
                    alert("Success");
                    showUser();
                }
                else {
                    alert("Failed");
                }
            }
        });
    }
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