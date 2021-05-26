$('#selectAll').click(function(e){
    var table= $(e.target).closest('table');
    $('td input:checkbox',table).prop('checked',this.checked);
});

new AjaxUpload('#csvUpload', {
        action: '/admin/goods/upload',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif|csv)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif,csv格式的文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
              swal("uploadがOKです！" ,{
        icon:"success",
       });
            } else {
                alert("error");
            }
        }
    });

$('#download').on('click', function(){
 
 	var ids = [];
 	var format = $("#selectfile").val();
 	
	   $('input:checkbox:checked').parent().next().map(function () {
	        ids.push($(this).text())
	        return ids;
	    })
	    var index = ids.indexOf("CAMPAIGN ID");
			if(index > -1){
			  ids.splice(index,1);
			}
	    if (ids == null) {
	    swal("请选择一条记录", {
            icon: "warning",
        });
        return
    }
    var data = {
 		"ids":ids,
 		"format":format
 	}
    $.ajax({
            type: 'POST',
            url: '/admin/sale/download',           
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
    //サーバーが成功した場合
                if (result.resultCode == 200) {
               swal("成功", {
                    icon: "success",
                }); 
                
                  Download(result.data); 
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
 
 function Download(url) {
 document.getElementById('my_iframe').src = url;
    };
  

//絞り込み検索 改修 2021/05/25
(function(document) {
  'use strict';
  var LightTableFilter = (function(Arr) {
    var _input;
    function _onInputEvent(e) {
      _input = e.target;
      var tables = document.getElementsByClassName(_input.getAttribute('data-table'));
      Arr.forEach.call(tables, function(table) {
        Arr.forEach.call(table.tBodies, function(tbody) {
          Arr.forEach.call(tbody.rows, _filter);
        });
      });
    }
    function _filter(row) {
      var text = row.textContent.toLowerCase(), val = _input.value.toLowerCase();
      row.style.display = text.indexOf(val) === -1 ? 'none' : 'table-row';
    }
    return {
      init: function() {
        var inputs = document.getElementsByClassName('light-table-filter');
        Arr.forEach.call(inputs, function(input) {
          input.oninput = _onInputEvent;
        });
      }
    };
  })(Array.prototype);
  document.addEventListener('readystatechange', function() {
    if (document.readyState === 'complete') {
      LightTableFilter.init();
    }
  });

})(document);

function saleAdd() {
    reset();
    $('.modal-title').html('キャンペーン添加');
    $('#saleModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var addId = $("#addId").val();
    var addName = $("#addName").val();
    var addStartDate = $("#addStartDate").val();
    var addEndDate = $("#addEndDate").val();
    var addSaleCampaign = $("#addSaleCampaign").val();
    var addContent1 = $("#addContent1").val();
    var addContent2 = $("#addContent2").val();
    var addContent3 = $("#addContent3").val();
    var addContent4 = $("#addContent4").val();
    var addContent5 = $("#addContent5").val();
    var addFlag = $("#addFlag").val();
        var data = {
            "id": addId,
            "name": addName,
            "startDate": addStartDate,
            "endDate": addEndDate,
            "campaign": addSaleCampaign,
            "content1": addContent1,
            "content2": addContent2,
            "content3": addContent3,
            "content4": addContent4,
            "content5": addContent5,
            "flag": addFlag
        };
        var url = '/admin/sale/save';
        
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#categoryModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $('#categoryModal').modal('hide');
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
        $(".modal").fadeOut();
});


function reset() {
    $("#saleId").val(0);
    $("#saleName").val('');
    $("#startDate").val('');
    $("#endDate").val('');
    $('#edit-error-msg').css("display", "none");
}

function sort(orderBy,ascOrDesc) {
   $.ajax({
            url: '/admin/sale-order?orderBy=' + orderBy + '&ascOrDesc=' + ascOrDesc,
            type: 'GET',
            success: function (result) {
            var sortBy = " ";
            var saleList = result.data.saleList;
            var length = saleList.length;
            for(var i=0;i<length;i++) {
            sortBy += '<tr>'
					 +'<td>'
					 +'<input type=\"checkbox\"/>'
		             +'</td>'
					 +'<td><th:block th:text=\"${' + saleList[i].id + '}\"></th:block></td>'
					 +'<td><th:block th:text=\"${' +saleList[i].name + '}\"></td>'
					 +'<td><th:block th:text=\"${#dates.format(' + saleList[i].startDate +',\'yyyy-MM-dd HH:mm:ss\')}\"></td>'
					 +'<td><th:block th:text=\"${#dates.format(' + saleList[i].endDate + ',\'yyyy-MM-dd HH:mm:ss\')}\"></td>'
					 +'<td></td>'
					 +'</tr>'
            }
            $('#table > tbody').html(sortBy);
           }
        });
}
