
$('#saveButton').click(function () {
    var goodsId = $('#goodsId').val();
    var goodsCategoryId = $('#levelThree option:selected').val();
    var parentId = $('#levelTwo option:selected').val();
    var firstLevelId = $('#levelOne option:selected').val();
    var saleId = $('#saleId').val();
    var saleValue = $('#saleValue').val();
    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();
    
    var url = '/admin/sale/save';
    var url1 = '/admin/sale/saveList';
    var swlMessage = '保存成功';
    var data = {
        "goodsId": goodsId,
        "goodsCategoryId": goodsCategoryId,
        "parentId": parentId,
        "firstLevelId": firstLevelId,
        "saleId": saleId,
        "saleValue": saleValue,
        "startDate": startDate,
        "endDate": endDate,
    };
    var data1 = {
        "saleId": saleId,
        "startDate": startDate,
        "endDate": endDate,
    };
    
    console.log(data);
    console.log(data1);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: '確定',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/goods/sale";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
    $.ajax({
        type: 'POST',//方法类型
        url: url1,
        contentType: 'application/json',
        data: JSON.stringify(data1),
        success: function (result) {
            if (result.resultCode == 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: '確定',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/goods/sale";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

$('#changeButton').click(function () {
    var goodsId = $('#goodsId').val();
    var goodsCategoryId = $('#levelThree option:selected').val();
    var parentId = $('#levelTwo option:selected').val();
    var firstLevelId = $('#levelOne option:selected').val();
    var saleId = $('#saleId').val();
    var saleValue = $('#saleValue').val();
    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();
   
    var swlMessage = '保存成功';
    var data = {
        "goodsId": goodsId,
        "goodsCategoryId": goodsCategoryId,
        "parentId": parentId,
        "firstLevelId": firstLevelId,
        "saleId": saleId,
        "saleValue": saleValue,
        "startDate": startDate,
        "endDate": endDate,
    };
    var data1 = {
        "saleId": saleId,
        "startDate": startDate,
        "endDate": endDate,
    };
    
    $.ajax({
        type: 'POST',//方法类型
        url: '/admin/sale/update',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: '修改',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/goods/sale";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });

$.ajax({
        type: 'POST',//方法类型
        url: '/admin/sale/updateList',
        contentType: 'application/json',
        data: JSON.stringify(data1),
        success: function (result) {
            if (result.resultCode == 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: '修改',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/goods/sale";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

$('#cancelButton').click(function () {
    window.location.href = "/admin/goods/sale";
});

$('#levelOne').on('change', function () {
    $.ajax({
        url: '/admin/categories/listForSelect?categoryId=' + $(this).val(),
        type: 'GET',
        success: function (result) {
            if (result.resultCode == 200) {
                var levelTwoSelect = '';
                var secondLevelCategories = result.data.secondLevelCategories;
                var length2 = secondLevelCategories.length;
                for (var i = 0; i < length2; i++) {
                    levelTwoSelect += '<option value=\"' + secondLevelCategories[i].categoryId + '\">' + secondLevelCategories[i].categoryName + '</option>';
                }
                $('#levelTwo').html(levelTwoSelect);
                var levelThreeSelect = '';
                var thirdLevelCategories = result.data.thirdLevelCategories;
                var length3 = thirdLevelCategories.length;
                for (var i = 0; i < length3; i++) {
                    levelThreeSelect += '<option value=\"' + thirdLevelCategories[i].categoryId + '\">' + thirdLevelCategories[i].categoryName + '</option>';
                }
                $('#levelThree').html(levelThreeSelect);
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

$('#levelTwo').on('change', function () {
    $.ajax({
        url: '/admin/categories/listForSelect?categoryId=' + $(this).val(),
        type: 'GET',
        success: function (result) {
            if (result.resultCode == 200) {
                var levelThreeSelect = '';
                var thirdLevelCategories = result.data.thirdLevelCategories;
                var length = thirdLevelCategories.length;
                for (var i = 0; i < length; i++) {
                    levelThreeSelect += '<option value=\"' + thirdLevelCategories[i].categoryId + '\">' + thirdLevelCategories[i].categoryName + '</option>';
                }
                $('#levelThree').html(levelThreeSelect);
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});
