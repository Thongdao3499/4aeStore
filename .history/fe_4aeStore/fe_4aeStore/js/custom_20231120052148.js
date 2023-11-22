//File này có tác dụng khi đăng nhập thành công sẽ chuyển sang file index.html
$(document).ready(function() {
    
    $("adtn-signin").click(function() {

        var email = $("email").val()
        var password = $("password").val()

        $.ajax({
            method: "POST",
            url: "http://localhost:8080/login/signin",
            data: {
                username: email,
                password: password,
            }
        })
            .done(function( msg ) {
                console.log(msg)
                if(msg.success) {
                    localStorage.setItem("token", msg.data)
                    window.location.href = "./index.html"
                } else {
                    alert("Đăng nhập thất bại!")
                }
            });
    })




})