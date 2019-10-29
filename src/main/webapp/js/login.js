function login() {

    $.ajax({
        type: "POST",
        url: 'servlet/LoginServlet',
        data: {
            "username":$('#inputUsername'),
            "password":$('#inputPassword'),
        },
        success: function (data) {
            alert("success");
        }
    });

}