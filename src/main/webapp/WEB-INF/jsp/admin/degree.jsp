<%@ page import="utils.AppSession" %>
<%
    String path = request.getContextPath();
    String scheme;
    if (request.getHeader("x-forwarded-proto") != null) {
        scheme = request.getHeader("x-forwarded-proto");
    } else {
        scheme = request.getScheme();
    }
    String basePath;
    if (request.getServerPort() != 80) {
        basePath = scheme + "://" + request.getServerName() + ":"+request.getServerPort()+path + "/";
    } else {
        basePath = scheme + "://" + request.getServerName() + path + "/";
    }
    pageContext.setAttribute("basePath", basePath);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Admin Degree Management</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    <link href="${pageScope.basePath}css/dashboard.css" rel="stylesheet">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.1/bootstrap-table.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.1/bootstrap-table.min.js"></script>

    <!-- Latest compiled and minified Locales -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.1/locale/bootstrap-table-en-US.min.js"></script>

    <script src="${pageScope.basePath}js/admin/degree.js"></script>

</head>
<body>
<nav class="navbar navbar-dark fixed-top flex-md-nowrap p-0 " style="background: black;">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Hi! <%= AppSession.getUser().getUsername()%></a>
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a class="nav-link" href="/logout">Sign out</a>
        </li>
    </ul>
</nav>

<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
            <div class="sidebar-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" href="/admin?page=degree" id="degreeLink" >
                            Degree List
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin?page=subject" id="subjectLink" >
                            Subject List
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin?page=account" >
                            Account List
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4" style="margin-top: 10px">
            <div id="divDegreeTable">
                <div id="toolbar1">
                <button type="button" class="btn btn-primary" onclick="btnShowAddModal()">Add</button>
                <button type="button" class="btn btn-secondary" onclick="btnDeleteDegree()">Delete</button>
                </div>

                <table id="tb_degree">
                </table>

                <div class="modal fade" id="addDegreeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addModalLabel">Add Degree</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p><h5> Degree Name: </h5><input type="text" id="addDegreeName"  class="form-control"/> </p>
                                <p><h5> Degree Description: </h5></p>
                                <textarea cols="50" rows="10" id="addDegreeDesc" class="form-control"></textarea></p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary" onclick="btnAddDegree()">Add</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="divDegreeDetailed" style="display:none">
                <button type="button" id="btnBackToDegreeTable" class="btn btn-primary" onclick="btnBackToDegreeTable()">Back</button>
                <button type="button" id="btnUpdateSaveDegree" class="btn btn-primary" onclick="btnUpdateDegree()">Update</button>
                <p><h5> Degree Name: </h5>><input type="text" id="detailedDegreeName"  class="form-control" readonly="true"/> </p>
                <p><h5> Degree Description: </h5></p>
                <textarea cols="50" rows="10" id="detailedDegreeDesc" class="form-control" readonly="true"></textarea></p>
                <div id="toolbar2">
                <p>Subjects: <button type="button" id="btnAddSubject" class="btn btn-primary" onclick="btnShowSubjectTable()">Add</button>
                    <button type="button" id="btnDeleteSubject" class="btn btn-primary" onclick="btnDeleteSubjectInDegree()">Delete</button></p>
                </div>
                <table id="tb_subjectInDegree"></table>

                <div class="modal fade" id="modalShowSubject" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="showSubjectLabel">Add Subject</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                               <table id="tb_allSubject"></table>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary" onclick="btnAddSubjectInDegree()">Add</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
</body>
</html>