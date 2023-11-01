$(function () {
  loadUserData();

  $("#modal-default form").on("submit", function (e) {
    e.preventDefault();
    $.ajax({
      type: "Post",
      url: "/user/save",
      data: $(this).serialize(),
      success: function (data) {
        $("#modal-default").modal("hide");
        loadUserData();
      },
    });
  });
});

let loadUserData = function () {
  $.ajax({
    url: "/user/load-data",
    method: "Get",
    dataType: "HTML",
    success: function (data) {
      $("#user_table").html(data);
      var currentPage;
      var totalPages = $("#data_page").data("total-pages");

      $(document).ready(function () {
        $("a#loadDataButton").on("click", function (e) {
          e.preventDefault();

          var pageNumber = $(this).data("page");
          var size = $(this).data("size");
          $.ajax({
            type: "GET",
            url: "/user/load-data",
            data: {
              pageNumber: pageNumber,
              pageSize: size,
            },
            success: function (data) {
              console.log(pageNumber);
              $("#user_table").html(data);
              $("#nextPageButton").data("page-next", pageNumber);
            },
          });
        });
      });

      // Xu ly nut next
      $(document).ready(function () {
        // Gán sự kiện click cho thẻ có id "nextPageButton"
        $("#nextPageButton").on("click", function (e) {
          e.preventDefault();
          if (currentPage === undefined) {
            currentPage = $("#nextPageButton").data("page-next");
            if (currentPage >= totalPages) {
              $("#nextPageButton").css("display", "none");
            }
          }
          console.log(currentPage, totalPages);

          if (isNaN(currentPage)) {
            currentPage = 1;
          }
          console.log(currentPage);
          var pageSize = $("#loadDataButton").first().data("size");
          // Tính toán trang tiếp theo
          var nextPage = currentPage + 1;

          // Gọi Ajax để lấy dữ liệu trang tiếp theo
          $.ajax({
            type: "GET",
            url: "/user/load-data",
            data: {
              pageNumber: nextPage,
              pageSize: pageSize,
            },
            success: function (data) {
              // Thay đổi nội dung bảng dữ liệu
              $("#user_table").html(data);

              // Cập nhật trang hiện tại
              currentPage = nextPage;
              // Cập nhật thuộc tính data cho nút "nextPageButton"
              $("#nextPageButton").data("page", currentPage);

              // Kiểm tra và cập nhật trạng thái của nút "Previous"
              if (currentPage > 1) {
                $(".previousPageButton").removeClass("disabled");
              } else {
                $(".previousPageButton").addClass("disabled");
              }
              if (currentPage >= totalPages) {
                currentPage = $("#nextPageButton").data("page-next");
                $("#nextPageButton").css("display", "none");
              }
            },
            error: function (error) {
              console.error(error);
            },
          });
        });
      });
    },
  });
};
