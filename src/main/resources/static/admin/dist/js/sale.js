var MouseOnSearchResultUl;

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
	   $('input:checkbox:checked').parent().next().map(function () {
	        ids.push($(this).text())
	        return ids;
	    })
	    if (ids == null) {
	    swal("请选择一条记录", {
            icon: "warning",
        });
        return
    }
    $.ajax({
            type: 'POST',
            url: '/admin/sale/download',           
            contentType: 'application/json',
            data: JSON.stringify(ids),
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
  


var MouseOnSearchResultUl  //全局变量
    //ajax与后台通信，查找查询履历
$( "#keywordSale" ).focus(function(){
 var keyword = $( "#keywordSale" ).val();
 if(keyword != ""){
  $( "#keywordSale" ).trigger("keyup");
 }
});  
//鼠标移开时候删除elements的内容delete elements when focus out
$("#keywordSale").focusout(function(){
 if(MouseOnSearchResultUl)
 return;
    clearResultList()
 //hide #searchResultUl
 $("#searchResultUl").hide();
});
  //add by niu  2021/05/21 ajax あいまい検索
$("#keywordSale").keyup(function(){
 var keyword = $("#keywordSale").val();
     $.ajax({
            type: 'get',//方法类型  //method = "POST"
            url: "/goods/searchSale?name="+keyword,  //Post送信先のurl
            dataType:"json",
            success: function (json_data) {
   debugger;
   clearResultList();
   showResultForLikeSearch(json_data);
   debugger;
         var list = json_data.data.list[0];
      var str = list.name;
  },
  error: function() {
   alert("Service Error. Pleasy try again later.");
  }
 });
  
});
function clearResultList(){
 $("#searchResultUl").children().toArray().forEach(function(value,index,array){
  var incFlag = $(value).attr('class').includes("dumyLi");
  if(!incFlag){
   $(value).remove();
  }
 })
}
function showResultForLikeSearch(result){
 var list = result.data.list;
 for(var i = 0; i< list.length; i++){
  var el = $(".dumyLi").clone().removeClass("dumyLi");
  var link = el.find("a");
  link.text(list[i].name);
  $(".dumyLi").before(el);
 }
 $("#searchResultUl").show();
 appendToSearchBar($("#searchResultUl"));
}
function appendToSearchBar(el){
 debugger;
 var searchBar = $("#keywordSale");//jquery object
 //var searchBar = document.getElementById("keyword");//dom
 var rect = searchBar[0].getBoundingClientRect();//转换成dom加[0]  convert jquery object to dom by searchBar[0]
 console.log(rect.top,rect.right,rect.bottom,rect.left);
 var sbHeight = searchBar.height();
 el.css({top: rect.top + sbHeight,left: rect.left,position:'absolute'});//相对定位relative  绝对定位absolute
 }
$("#searchResultUl").mousemove(function(){
	MouseOnSearchResultUl = true;
});
$("#searchResultUl").mouseleave(function(){
	 MouseOnSearchResultUl = false;
})

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
        var data = {
            "id": addId,
            "name": addName,
            "startDate": addStartDate,
            "endDate": addEndDate
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
            var saleList = result.data;
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
