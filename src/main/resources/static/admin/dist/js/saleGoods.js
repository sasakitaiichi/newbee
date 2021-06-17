
var MouseOnSearchResultUl

/*$(function () {
   var goodsId = $('#addprimaryGoodsId').val();
   $.ajax({
		type: 'POST',//方法类型
		url: '/admin/giftGoods',
		contentType: 'application/json',
		data: JSON.stringify(goodsId),
		success: function(result) {
			if (result.resultCode == 200) {
				
			} else {
				swal(result.message, {
					icon: "error",
				});
			}
			;
		},
		error: function() {
			swal("操作失败", {
				icon: "error",
			});
		}
	});
});*/

$(".checkBox").change(function() {
	var categoryId;

	var ischecked = $(this).is(':checked');
	if (!ischecked) {
		categoryId = $(this).val();



		var url = '/admin/saleCategory/delete';

		var swlMessage = '刪除成功';



		$.ajax({
			type: 'POST',//方法类型
			url: url,
			contentType: 'application/json',
			data: JSON.stringify(categoryId),
			success: function(result) {
				if (result.resultCode == 200) {
					swal({
						title: swlMessage,
						type: 'success',
						showCancelButton: false,
						confirmButtonColor: '#1baeae',
						confirmButtonText: '確定',
						confirmButtonClass: 'btn btn-success',
						buttonsStyling: false
					})
				} else {
					swal(result.message, {
						icon: "error",
					});
				}
				;
			},
			error: function() {
				swal("操作失败", {
					icon: "error",
				});
			}
		});
	} else {
		var startDate = $(this).parent().find('.startDate').val();
		var endDate = $(this).parent().find('.endDate').val();
		var id = $(this).parent().parent().find('.custom-select').val();
		var categoryId = $(this).val();
		var data = {
			"id": id,
			"categoryId": categoryId,
			"startDate": startDate,
			"endDate": endDate
		}
		var url1 = '/admin/saleCategory/save';
		var swlMessage1 = '插入成功';
		$.ajax({
			type: 'POST',//方法类型
			url: url1,
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function(result) {
				if (result.resultCode == 200) {
					swal({
						title: swlMessage1,
						//type: 'success',
						//showCancelButton: false,
						//confirmButtonColor: '#1baeae',
						//confirmButtonText: '確定',
						//confirmButtonClass: 'btn btn-success',
						//buttonsStyling: false
					})
				} else {
					swal(result.message, {
						icon: "error",
					});
				}
				;
			},
			error: function() {
				swal("操作失败", {
					icon: "error",
				});
			}
		});
	};
});


function campaignAdd() {
	reset();
	$('.modal-title').html('买二送一');
	$('#campaignSetModal').modal('show');
}

function reset() {
	$("#addprimaryGoodsId").val('');
	$("#addSubGoodsId").val('');
	$('#edit-error-msg').css("display", "none");
}

$('#saveButton').click(function() {
	var addprimaryGoodsId = $("#addprimaryGoodsId").val();
	var addSubGoodsId = $("#addSubGoodsId").val();
	var data = {
		"primaryGoodsId": addprimaryGoodsId,
		"subGoodsId": addSubGoodsId

	};
	var url = '/admin/sale/campaignSet';

	$.ajax({
		type: 'POST',//方法类型
		url: url,
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(result) {
			if (result.resultCode == 200) {
				$('#campaignSetModal').modal('hide');
				swal("保存成功", {
					icon: "success",
				});
				reload();
			} else {
				$('#campaignSetModal').modal('hide');
				swal(result.message, {
					icon: "error",
				});
			}
			;
		},
		error: function() {
			swal("操作失败", {
				icon: "error",
			});
		}
	});
	//$(".modal").fadeOut();
});




function nextLevel(thi, categoryId) {
	$.ajax({
		type: 'POST',//方法类型
		url: '/admin/saleGoods/nextCategory',
		contentType: 'application/json',
		data: JSON.stringify(categoryId),
		success: function(result) {
			//サーバーが成功した場合
			if (result.resultCode == 200) {
				showResult(thi, result)
			} else {
				swal(result.message, {
					icon: "error",
				});
			}

		},
		error: function() {
			swal("操作失败", {
				icon: "error",
			});
		}
	})
}

/*$(".buttonPlus").focusout(function() {

	//return means jump out of this function. end.
	if (MouseOnSearchResultUl) {
		return;
	}
	$(".nextLevelCategory").children().toArray().forEach(function(value, index, array) {
		var incFlag = $(value).attr('class').includes("dumyLi");
		if (!incFlag) {
			$(value).remove();
		}
	})
	$(".nextLevelCategory").hide();

})

function clearResultList() {
	$(".nextLevelCategory").children().toArray().forEach(function(value, index, array) {
		var incFlag = $(value).attr('class').includes("dumyLi");
		if (!incFlag) {
			$(value).remove();
		}
	})
}*/
function showResult(thi, result) {
	var goodsList = result.data.goodsList;
	var goodsSales = result.data.goodsSales;
	var cloneUl = $('.nextLevelCategory').clone().removeClass("nextLevelCategory");
	for (var i = 0; i < goodsList.length; i++) {
		var option = "";
		var select = $('<select/>', { class: "custom-select" });
		var el = cloneUl.find(".dumyLi").clone().removeClass("dumyLi");
		for (var j = 0; j < goodsSales.length; j++) {
			option += '<option value=\"' + goodsSales[j].id + '\">' + goodsSales[j].campaign + '</option>'
			select.html(option);
			if (goodsList[i].id == goodsSales[j].id) {
				select.val(goodsSales[j].id);
				el.find(".checkbox").prop('checked', true);
			}
		}
		el.find('input:first-child').before(select);
		var a = el.find("a");
		a.text(goodsList[i].categoryName);
		var startDated = el.find("input:nth-child(6)");
		var endDate = el.find("input:nth-child(7)");
		var categoryId = el.find("input:nth-child(3)");
		categoryId.val(goodsList[i].categoryId);
		startDated.val(goodsList[i].startDate);
		endDate.val(goodsList[i].endDate);
		el.find('.buttonPlus').attr('onClick', 'nextLevel(this,' + goodsList[i].categoryId + ')');
		el.find(".checkbox").attr('onchange', 'checkPopup(this)');
		cloneUl.find(".dumyLi").before(el);
		cloneUl.find(".close").click(function() {
			cloneUl.find("#levelCategory").remove();
		})
	}
	cloneUl.show();
	//pendToSearchBar(thi, cloneUl);
	var rect = thi.getBoundingClientRect();
	cloneUl.css({ top: rect.top, left: rect.right, position: 'absolute' });
	$("#main").append(cloneUl);
}


function checkPopup(thi) {
	debugger;
	var flag = $(thi).is(':checked');
	var id = $(thi).parent().parent().find(".custom-select").val();
	var startDate = $(thi).parent().find(".startDate").val();
	var endDate = $(thi).parent().find(".endDate").val();
	var categoryId = $(thi).parent().find(".categoryValue").val();
	var data = {
		"flag": flag,
		"id": id,
		"startDate": startDate,
		"endDate": endDate,
		"categoryId": categoryId
	};
	$.ajax({
		type: 'POST',//方法类型
		url: '/admin//saleCategory/saveOrDelete',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(result) {
			//サーバーが成功した場合
			if (result.resultCode == 200) {
				if (data.flag) {
					swal("插入成功", {
						icon: "success",
					});
				} else {
					swal("删除成功", {
						icon: "success",
					});
				}
			} else {
				swal(result.message, {
					icon: "error",
				});
			}

		},
		error: function() {
			swal("有効期限外", {
				icon: "error",
			});
		}
	})
}
/*function appendToSearchBar(thi, el) {
	debugger;
	//var searchBar = $(".checkBox");//jquery object
	//var searchBar = document.getElementById("button");//dom
	var rect = thi.getBoundingClientRect();//转换成dom加[0]  convert jquery object to dom by searchBar[0]
	console.log(rect.top, rect.right, rect.bottom, rect.left);
	el.css({ top: rect.top, left: rect.right, position: 'fixed' });//相对定位relative  绝对定位absolute
}*/
$(".nextLevelCategory").mousemove(function() {
	MouseOnSearchResultUl = true;
});
$(".nextLevelCategory").mouseleave(function() {
	MouseOnSearchResultUl = false;
})

