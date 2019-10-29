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

    <title>Admin Account Management</title>

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

    <script src="${pageScope.basePath}js/admin/account.js"></script>

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
                        <a class="nav-link" href="/admin?page=degree" id="degreeLink" >
                            Degree List
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin?page=subject" id="subjectLink" >
                            Subject List
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="/admin?page=account" id="accountLink">
                            Account List
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"  style="margin-top: 10px">
            <div id="divUserTable">
                <div id="toolbar">
                    <button type="button" class="btn btn-primary" onclick="btnShowAddModal()">Add</button>
                    <button type="button" class="btn btn-secondary" onclick="btnDeleteAccount()">Delete</button>
                </div>

                <table id="tb_user">
                </table>

                <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addModalLabel">Add User</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p><h5> Username:</h5> <input type="text" id="addUserUsername"  class="form-control"/> </p>
                                <p><h5> Password: </h5><input type="text" id="addUserPassword"  class="form-control"/> </p>
                                <p><h5> Role: </h5>
                                <label class="radio-inline">
                                        <input type="radio" name="roleRD" value="0" checked> Admin
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="roleRD" value="1"> Student
                                    </label>
                                </p>
                                <div id="studentInfo" >
                                    <p><h5> Student Degree: </h5>
                                        <select id="selectAddUserDegree" class="form-control">
                                            <option value="-1"></option>
                                        </select>
                                    </p>
                                </div>
                                <p><h5> First Name: </h5><input type="text" id="addFirstName"  class="form-control"/> </p>
                                <p><h5> Last Name: </h5><input type="text" id="addLastName"  class="form-control"/> </p>
                                <p><h5> Gender: </h5>
                                        <label class="radio-inline">
                                            <input type="radio" name="genderRD" value="0"> Male
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="genderRD" value="1"> Female
                                        </label>
                                        </p>
                                <p><h5> E-Mail: </h5><input type="text" id="addEmail"  class="form-control"/> </p>
                                <p><h5> Phone Number: </h5><input type="text" id="addPhone"  class="form-control"/> </p>
                                <p><h5> Address: </h5><input type="text" id="addAddress"  class="form-control"/> </p>

                            </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="btnAddUser()">Add</button>
                        </div>
                    </div>
                    </div>
                </div>

                <div class="modal fade" id="modalResetPassword" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="modalResetLabel">Reset Password</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p><h5> Username: </h5><input type="text" id="resetPWDusername"  class="form-control" readonly/> </p>
                                <p><h5> Password: </h5><input type="text" id="resetPWDpassword"  class="form-control"/> </p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary" onclick="resetPassword()">Reset</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div id="divUserDetailed" style="display:none">
                <button type="button" id="btnBackToUserTable" class="btn btn-primary" onclick="btnBackToUserTable()">Back</button>
                <button type="button" id="btnUpdateSaveUser" class="btn btn-primary" onclick="btnUpdateSaveUser()">Update</button>
                <p><h5> User Username: </h5><input type="text" id="updateUserUsername"  class="form-control"/> </p>
                <p><h5> User Password: </h5><button type="button" class="btn btn-primary" onclick="showUpResetModal()">Reset</button>
                <p><h5> User Role: </h5>
                    <label class="radio-inline">
                        <input type="radio" name="updateRoleRD" value="0" checked> Admin
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="updateRoleRD" value="1"> Student
                    </label>
                </p>
                <div id="updateStudentInfo" >
                    <p><h5> Student Degree: </h5>
                        <select id="selectUpdateUserDegree" class="form-control">
                            <option value="-1"></option>
                        </select>
                    </p>
                </div>
                <p><h5> First Name: </h5><input type="text" id="updateFirstName"  class="form-control"/> </p>
                <p><h5> Last Name: </h5><input type="text" id="updateLastName"  class="form-control"/> </p>

                <p><h5> Gender: </h5>
                        <label class="radio-inline">
                            <input type="radio" name="updateGenderRD" value="0"> Male
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="updateGenderRD" value="1"> Female
                        </label>
                    </p>
                    <p><h5> E-Mail: </h5><input type="text" id="updateEmail"  class="form-control"/> </p>
                    <p><h5> Phone Number: </h5><input type="text" id="updatePhone"  class="form-control"/> </p>
                    <p><h5> Address: </h5><input type="text" id="updateAddress"  class="form-control"/> </p>

                    <div class="modal fade" id="modalUpdateResetPassword" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="modalUpdateResetResetLabel">Reset Password</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <p><h5> Username: </h5><input type="text" id="upResetPWDusername"  class="form-control" readonly/> </p>
                                    <p><h5> Password: </h5><input type="text" id="upResetPWDpassword"  class="form-control"/> </p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-primary" onclick="upResetPassword()">Reset</button>
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