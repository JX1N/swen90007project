var choosenSubjectID='';
var enrolledSubjectData;

$(function () {
    loadEnrolledSubject();
});

function loadEnrolledSubject() {
    $.ajax({
        url: "/student/subjects",
        method: 'POST',
        success: function(data){
            enrolledSubjectData=data;
            initEnrolledSubjectTable(data.subjects);
        }
    });
}
function initEnrolledSubjectTable(data) {
    $('#tb_enrolledSubject').bootstrapTable('destroy');
    $('#tb_enrolledSubject').bootstrapTable({
        data:data,
        pagination: true,
        pageSize: 20,
        pageList:[10,15,20,25],
        maintainSelected:true,
        sortable: false,
        sortName:'id',
        search:true,
        toolbar:'#toolbar1',
        sortOrder: "asc",
        clickToSelect: false,
        cardView: false,
        detailView: false,
        columns: [{
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
            choosenSubjectID=row.id;
            showModalSubjectDetailed(row,'Withdraw')
        }
    });
};

function showModalSubjectDetailed(subject,operation) {
    $('#detailedSubjectName').val('');
    $('#detailedSubjectCode').val('');
    $('#detailedSubjectDesc').val('');
    $('#detailedSubjectName').val(subject.subjectName);
    $('#detailedSubjectCode').val(subject.subjectCode);
    $('#detailedSubjectDesc').val(subject.subjectDesc);
    $('#btnModalOperation').val(operation);
    $('#btnModalOperation').text(operation);
    $('#modalSubjectDetailed').modal();
}

function btnModalOperation() {
    var btnVal=$('#btnModalOperation').val();
    //use different url based on btnModalOperation.val
    var loadUrl='';
    if(btnVal=='Withdraw'){
        loadUrl='/student/withdraw';
    }else{
        loadUrl='/student/enroll';
    }

    $.ajax({
        url: loadUrl,
        method: 'POST',
        data:{
            subjectId:choosenSubjectID
        },
        success: function(data){
            data=JSON.parse(data);
            if(data.message=='success') {
              $('#modalSubjectDetailed').modal('hide');
              alert("Success");
              RefreshData(btnVal);
            }
            else {
                alert("Failed");
            }
        }
    });
    
}

function RefreshData(btnVal) {

    loadEnrolledSubject();
    if(btnVal=='Enroll'){
        $('#tb_enrolledSubject').bootstrapTable('destroy');
        $('#tb_subject').bootstrapTable('destroy');
        loadSubject();
    }
}

function btnEnrollSubject(){
    $('#divEnrollSubject').hide();
    $('#divSubject').show();
    $('#tb_enrolledSubject').bootstrapTable('destroy');
    loadSubject();
}

function btnBack(){
    $('#divSubject').hide();
    $('#divEnrollSubject').show();
    $('#tb_subject').bootstrapTable('destroy');
    loadEnrolledSubject();
}

function loadSubject() {
    $.ajax({
        url: "/subject/list",
        method: 'POST',
        data:{
            degreeId:enrolledSubjectData.degreeIds[0],
        },
        success: function(data){
            initSubjectTable(data);
        }
    });
}

function initSubjectTable(data) {
    $('#tb_subject').bootstrapTable('destroy');
    $('#tb_subject').bootstrapTable({
        data:data,
        pagination: true,
        pageSize: 20,
        pageList:[10,15,20,25],
        maintainSelected:true,
        sortable: false,
        sortName:'id',
        search:true,
        toolbar:'#toolbar2',
        sortOrder: "asc",
        clickToSelect: false,
        cardView: false,
        uniqueId:'id',
        detailView: false,
        columns: [{
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
            choosenSubjectID=row.id;
            showModalSubjectDetailed(row,'Enroll');
        }
    });
    var enrolledSubjects=enrolledSubjectData.subjects;

    for(var i=0;i<enrolledSubjects.length;i++){
        $('#tb_subject').bootstrapTable('removeByUniqueId',enrolledSubjects[i].id);
    }
}