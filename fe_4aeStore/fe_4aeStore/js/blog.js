$(document).ready(function () {
  $.ajax({
    url: "http://localhost:8080/blog",
    method: "get",
  }).done(function (data) {
    console.log("Server tra ve", data);
  });
});
