// Khi nội dung file html đã được hiển thị trên browser thì sẽ kích hoạt
$(document).ready(function () {
  // Đăng ký sự kiện click cho thẻ tag được chỉ định bên HTML
  $("#btn-sign-in").click(function () {
    // .val() : Lấy giá trị của thẻ input được chỉ định
    var username = $("#user").val();
    var password = $("#pass").val();

    // Xuất giá trị ra trên tab console trên trình duyệt
    console.log("username : ", username, " password : ", password);

    //ajax : Dùng để call ngầm API mà không cần trình duyệt
    //axios, fetch
    //data : chỉ có khi tham số truyền ngầm
    $.ajax({
      url: "http://localhost:8080/login/signin",
      method: "post",
      data: {
        email: username,
        password: password,
      },
    }).done(function (data) {
      if (data && data.statusCode == 200) {
        localStorage.setItem("token", data.data);
      } else {
        alert("Sai email hoac mat khau!");
      }
      console.log("Server tra ve ", data);
    });
  });

  //Xử lý đăng ký
  $("#btn-sign-up").click(function () {
    // .val() : Lấy giá trị của thẻ input được chỉ định
    var username = $("#user-signup").val();
    var password = $("#pass-signup").val();
    var repassword = $("#re-pass-signup").val();
    var email = $("#email-signup").val();

    // Xuất giá trị ra trên tab console trên trình duyệt
    console.log(
      "username : ",
      username,
      " password : ",
      password,
      " repassword : ",
      repassword,
      " email : ",
      email
    );

    $.ajax({
      url: "http://localhost:8080/login/signup",
      method: "post",
      contentType: "application/json",
      data: JSON.stringify({
        username: username,
        password: password,
        email: email,
      }),
    }).done(function (data) { 
      console.log("Server tra ve ", data);
    });
  });
});
