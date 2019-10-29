var choosenSubjectID='';

$(function () {
    initSubjectTable();
});

function initSubjectTable() {
    $('#tb_subject').bootstrapTable('destroy');
    $('#tb_subject').bootstrapTable({
        url: "/subject/list",
        method:'POST',
        pagination: true,
        pageSize: 20,
        pageList:[10,15,20,25],
        maintainSelected:true,
        sortable: false,
        sortName:'id',
        search:true,
        toolbar:'#toolbar',
        sortOrder: "asc",
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
            title: 'Subject Code',
        },{
            field: 'subjectName',
            title: 'Subject Name',
        }],
        onClickCell: function (field,value,row) {
            if(field==0){
            }else {
                choosenSubjectID=row.id;
                subjectShowDetail(row);
            }
        }
    });
};

function btnShowAddModal() {
    $('#addSubjectCode').val(''),
    $('#addSubjectName').val(''),
    $('#addSubjectDesc').val(''),
    $('#addSubjectModal').modal();
}

function btnAddSubject() {
    var obj = {
        subjectCode:$('#addSubjectCode').val(),
        subjectName:$('#addSubjectName').val(),
        subjectDesc:$('#addSubjectDesc').val(),
    };
    $.ajax({
        url: "/subject/add",
        method: 'POST',
        data: {subject: JSON.stringify(obj)},
        dataType: "text",
        success: function(data){
            if(data.message=='success') {
                alert("Success");
                $('#addSubjectModal').modal('hide');
                initSubjectTable();
            }else {
                alert("Failed");
            }
        }
    });
}


function subjectShowDetail(subject) {
    $('#btnUpdateSaveSubject').text('Update');
    $('#detailedSubjectName').attr('readonly', true);
    $('#detailedSubjectCode').attr('readonly', true);
    $('#detailedSubjectDesc').attr('readonly', true);
    $('#detailedSubjectName').val(subject.subjectName);
    $('#detailedSubjectCode').val(subject.subjectCode);
    $('#detailedSubjectDesc').val(subject.subjectDesc);
    $('#divSubjectTable').attr('style','display:none');
    $('#divSubjectDetailed').attr('style','display:');
}

function btnBackToSubjectTable() {
    var back_confirm=true
    if($('#btnUpdateSaveSubject').text()=='Save') {
        back_confirm = confirm('All the changs will not be save');
    }
    if(back_confirm) {
        choosenSubjectID = '';
        $('#detailedSubjectName').val('');
        $('#detailedSubjectCode').val('');
        $('#detailedSubjectDesc').val('');
        $('#divSubjectTable').attr('style', 'display:');
        $('#divSubjectDetailed').attr('style', 'display:none');
        initSubjectTable();
    }
}

function btnUpdateSubject() {
    if($('#btnUpdateSaveSubject').text()=='Update') {
        $('#btnUpdateSaveSubject').text('Save');
        $('#detailedSubjectName').attr('readonly', false);
        $('#detailedSubjectCode').attr('readonly', false);
        $('#detailedSubjectDesc').attr('readonly', false);
    } else {
        var obj = {
            id:choosenSubjectID,
            subjectCode:$('#detailedSubjectCode').val(),
            subjectName:$('#detailedSubjectName').val(),
            subjectDesc:$('#detailedSubjectDesc').val(),
        };
        $.ajax({
            url: "subject/update",
            method: 'POST',
            data: {subject: JSON.stringify(obj)},
            success: function(data){
                if(data.message == 'success') {
                    alert("Success");
                    subjectShowDetail(obj);
                } else {
                    alert("Failed");
                }
            }
        });
    }
}

function btnDeleteSubject() {
    var rows = $('#tb_subject').bootstrapTable('getSelections');
    if(rows.length==0)
    {
        alert("Please choose the subject to be deleted");
    }else {
        var delete_confirm = confirm('This action will delete selected subjects!Are you sure?');
        if (delete_confirm) {
            var ids = new Array();
            for (var i = 0; i < rows.length; i++) {
                ids[i] = rows[i].id;
            }
            ids = JSON.stringify(ids);
            $.ajax({
                url: "subject/delete",
                type: "POST",
                data: {'ids': ids},
                success: function (data) {
                    if (data.message == 'success') {
                        alert("Success");
                        initSubjectTable();
                    }
                    else{
                        alert("Failed")
                    }
                }
            });
        }
    }
}