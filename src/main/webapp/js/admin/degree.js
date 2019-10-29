var choosenDegreeID='';

$(function () {
    initDegreeTable();
});

function initDegreeTable() {
    $('#tb_degree').bootstrapTable('destroy');
    $('#tb_degree').bootstrapTable({
        url: "/degree/list",
        method:'POST',
        pagination: true,
        pageSize: 20,
        pageList:[10,15,20,25],
        maintainSelected:true,
        sortable: false,
        sortName:'id',
        search:true,
        sortOrder: "asc",
        clickToSelect: false,
        toolbar:'#toolbar1',
        cardView: false,
        detailView: false,
        columns: [{
            checkbox:true,
        },{
            field: 'id',
            title: 'Degree ID'
        }, {
            field: 'degreeName',
            title: 'Degree Name',
        }],
        onClickCell: function (field,value,row) {
            if(field==0){
            }else {
                choosenDegreeID=row.id;
                degreeShowDetail(row);
            }
        }
    });
};

function initDegreeSubjectTable(degreeId) {
    $('#tb_subjectInDegree').bootstrapTable('destroy');
    $.ajax({
        url: "/subject/list",
        method: 'POST',
        data: {id: degreeId},
        success: function(data){
            $('#tb_subjectInDegree').bootstrapTable({
                data:data,
                pagination: true,
                pageList:[10,15,20,25],
                pageSize: 10,
                maintainSelected:true,
                sortable: false,
                sortOrder: "asc",
                toolbar:'#toolbar2',
                sortName:'id',
                search:true,
                clickToSelect: false,
                cardView: false,
                detailView: false,
                columns: [{
                    checkbox:true,
                },{
                    field: 'id',
                    title: 'Subject ID'
                },{
                    field: 'subjectCode',
                    title: 'Subject Code'
                }, {
                    field: 'subjectName',
                    title: 'Subject Name',
                }],
            });
        }
    });
};

function btnShowSubjectTable() {
    $('#modalShowSubject').modal('show');
    $('#tb_allSubject').bootstrapTable('destroy');
    $.ajax({
        url: "/subject/list/exclude",
        method: 'POST',
        data: {id: choosenDegreeID},//get all subject not in this degree
        success: function (data) {
            $('#tb_allSubject').bootstrapTable({
                data:data,
                pagination: true,
                pageSize: 20,
                pageList:[10,15,20,25],
                sortable: false,
                maintainSelected:true,
                sortOrder: "asc",
                toolbar:'#toolbar',
                search:true,
                sortName:'id',
                clickToSelect: true,
                cardView: false,
                detailView: false,
                columns: [{
                    checkbox: true,
                }, {
                    field: 'id',
                    title: 'Subject ID'
                }, {
                    field: 'subjectCode',
                    title: 'Subject Code'
                }, {
                    field: 'subjectName',
                    title: 'Subject Name',
                }],
            });
        }
    });
}

function btnShowAddModal() {
    $('#addDegreeDesc').val(''),
    $('#addDegreeName').val(''),
    $('#addDegreeModal').modal();
}


function btnAddDegree() {
    var obj = {
        degreeName:$('#addDegreeName').val(),
        degreeDesc:$('#addDegreeDesc').val(),
    };
    $.ajax({
        url: "/degree/add",
        method: 'POST',
        data: {degree: JSON.stringify(obj)},
        dataType: "text",
        success: function(data){
            if(data.message='success') {
                alert("Success");
                $('#addDegreeModal').modal('hide');
                initDegreeTable();
            } else{
                alert("Failed");
            }
        }
    });
}


function degreeShowDetail(degree) {
    initDegreeSubjectTable(degree.id);
    $('#btnUpdateSaveDegree').text('Update');
    $('#detailedDegreeName').attr('readonly', true);
    $('#detailedDegreeDesc').attr('readonly', true);
    $('#detailedDegreeName').val(degree.degreeName);
    $('#detailedDegreeDesc').val(degree.degreeDesc);
    $('#divDegreeTable').attr('style','display:none');
    $('#divDegreeDetailed').attr('style','display:')
}

function btnUpdateDegree() {
    if($('#btnUpdateSaveDegree').text()=='Update') {
        $('#btnUpdateSaveDegree').text('Save');
        $('#detailedDegreeName').attr('readonly', false);
        $('#detailedDegreeDesc').attr('readonly', false);
    } else {
        var obj = {
            id:choosenDegreeID,
            degreeName:$('#detailedDegreeName').val(),
            degreeDesc:$('#detailedDegreeDesc').val(),
        };
        $.ajax({
            url: "/degree/update",
            method: 'POST',
            data: {degree: JSON.stringify(obj)},
            success: function(data){
                if(data.message=='success') {
                    alert("Success");
                    degreeShowDetail(obj);
                }
                else {
                    alert("Failed");
                }
            }
        });
    }
}

function btnDeleteDegree() {
    var rows = $('#tb_degree').bootstrapTable('getSelections');
    if(rows.length==0)
    {
        alert("Please choose the degree to be deleted");
    }else {
        var delete_confirm = confirm('This action will delete selected degrees!Are you sure?');
        if (delete_confirm) {
            var ids = new Array();
            for (var i = 0; i < rows.length; i++) {
                ids[i] = rows[i].id;
            }
            ids = JSON.stringify(ids);
            $.ajax({
                url: "/degree/delete",
                type: "POST",
                data: {'ids': ids},
                success: function (data) {
                    if (data.message == 'success') {
                        alert("Success");
                        initDegreeTable();
                    } else{
                        alert("Failed")
                    }
                }
            });
        }
    }
}

function btnBackToDegreeTable() {
    var back_confirm=true
    if($('#btnUpdateSaveDegree').text()=='Save') {
         back_confirm = confirm('All the changs will not be save');
    }
    if(back_confirm) {
        choosenDegreeID = '';
        $('#detailedDegreeName').val('');
        $('#detailedDegreeDesc').val('');
        $('#divDegreeTable').attr('style', 'display');
        $('#divDegreeDetailed').attr('style', 'display:none');
        initDegreeTable();
    }
}

function btnAddSubjectInDegree(){
    var rows=$('#tb_allSubject').bootstrapTable('getSelections');
    var ids=new Array();
    for (var i=0;i<rows.length;i++) {
        ids[i]=rows[i].id;
    }
    ids=JSON.stringify(ids);
    $.ajax({
        url: "/degree/addSubject",
        type:"POST",
        data:{
            'degreeId':choosenDegreeID,
            'subjectIds':ids},
        success: function(data){
            if(data.message == 'success') {
                alert("Success");
                $('#modalShowSubject').modal('hide');
                initDegreeSubjectTable(choosenDegreeID);
            }else {
                alert("Failed");
            }
        }
    });
}

function btnDeleteSubjectInDegree() {
    var rows = $('#tb_subjectInDegree').bootstrapTable('getSelections');
    if (rows.length == 0) {
        alert("Please choose the subject to be deleted");
    } else {
        var delete_confirm = confirm('This action will delete selected subject in this degree!Are you sure?');
        if (delete_confirm) {
            var ids = new Array();
            for (var i = 0; i < rows.length; i++) {
                ids[i] = rows[i].id;
            }
            ids = JSON.stringify(ids);
            $.ajax({
                url: "/degree/deleteSubject",
                type: "POST",
                data: {
                    'degreeId': choosenDegreeID,
                    'subjectIds': ids
                },
                success: function (data) {
                    if (data.message == 'success') {
                        alert("Success");
                        initDegreeSubjectTable(choosenDegreeID);
                    } else {
                        alert("Failed")
                    }
                }
            });
        }
    }
}