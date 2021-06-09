
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




function nextLevel(this,categoryId) {
	$.ajax({
		type: 'POST',//方法类型
		url: '/admin/saleGoods/nextCategory',
		contentType: 'application/json',
		data: JSON.stringify(categoryId),
		success: function(result) {
			//サーバーが成功した場合
			if (result.resultCode == 200) {
				showResult(this,result)
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

$(".buttonPlus").focusout(function() {

	//return means jump out of this function. end.
	if (MouseOnSearchResultUl) {
		return;
	}
	$("#searchResultUl").children().toArray().forEach(function(value, index, array) {
		var incFlag = $(value).attr('class').includes("dumyLi");
		// delete elements besides dumyLi
		if (!incFlag) {
			$(value).remove();
		}

	})
	//hide #searchResultUl
	$("#searchResultUl").hide();

})

function clearResultList() {
	$("#searchResultUl").children().toArray().forEach(function(value, index, array) {
		var incFlag = $(value).attr('class').includes("dumyLi");
		if (!incFlag) {
			$(value).remove();
		}
	})
}
function showResult(this,result) {
	var goodsList = result.data.goodsList;
	var goodsSales = result.data.goodsSales;
	var option = "";
	var cloneUl = $('.searchResultUl').clone();
	for (var i = 0; i < goodsSales.length; i++) {
		var select = $('<select/>');
		option += '<option value=\"' + goodsSales[i].id + '\">' + goodsSales[i].campaign + '</option>'
		select.html(option);
		var el = $(".dumyLi").clone().removeClass("dumyLi");
		for (var j = 0; j < goodsList.length; j++) {
			if (goodsList[j].id == null) {
				select.val(goodsSales[0].id);
			}
			if (goodsList[j].id != null && goodsSales[i].id == goodsList[j].id) {
				select.val(goodsSales[i].id);
			}
		}
		el.find('input:first-child').before(select);
		var sd = el.find("input:nth-child(4)");
		var ed = el.find("input:nth-child(5)");
		var checked = el.find("input:nth-child(2)");
		checked.prop('checked', true);
		sd.val(goodsSales[i].startDate);
		ed.val(goodsSales[i].endDate);
		var link = el.find("a");
		link.text(goodsList[i].categoryName);
		link.val(goodsList[i].categoryId);
		$('.buttonPlus').attr('onClick','nextLevel('+goodsList[i].categoryId+');');
		$(".dumyLi").before(el);
	}
	$("#searchResultUl").show();
	appendToSearchBar(this,$("#searchResultUl"));
}


function appendToSearchBar(this,el) {
	debugger;
	//var searchBar = $(".checkBox");//jquery object
	//var searchBar = document.getElementById("button");//dom
	var rect = this.getBoundingClientRect();//转换成dom加[0]  convert jquery object to dom by searchBar[0]
	console.log(rect.top, rect.right, rect.bottom, rect.left);
	el.css({ top: rect.top, left: rect.right, position: 'fixed' });//相对定位relative  绝对定位absolute
}
$("#searchResultUl").mousemove(function() {
	MouseOnSearchResultUl = true;
});
$("#searchResultUl").mouseleave(function() {
	MouseOnSearchResultUl = false;
})

