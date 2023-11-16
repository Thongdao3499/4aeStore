$(document).ready(function () {
  $("#btn").click(function () {
    // console.log("");
    $.ajax({
      url: "http://localhost:8080/",
      method: "get",
      data: {
        // email: username,
        // password: password,
      },
    }).done(function (data) {
      if (data && data.statusCode == 200) {
        localStorage.setItem("token", data.data);
      }
    });
  });
});
