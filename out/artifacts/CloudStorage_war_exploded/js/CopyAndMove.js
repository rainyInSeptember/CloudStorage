$(document). ready(function() {
//事件冒泡捕获li点击事件
	$(".file-info-ul").click(function(e){

		//只要被点击，更改当前路径
		//获取被点击的文件夹名
		var filename=$(e.target).text(); 
		//获取父文件夹路径
		var parentPath=$(e.target).attr("parent");
		//被点击的文件夹，修改前文件位置，
		if($(e.target).attr("class")=="root")
		{
			$("#current-file-path").text("/"+filename);
		}
		
		else
		{
				$("#current-file-path").text(parentPath+"/"+filename);
		}
		//如果子文件信息，没有被点击则查询子文件信息，否则删除
		if( !$(e.target).next().is("UL"))
		{
			var goToPath=$("#current-file-path").text();
			var onClickLi=$(e.target);
			//查询
			goToUl(goToPath,onClickLi);
		}
		else{
			//子文件夹信息已经展开，则移除
			$("li[parent*="+filename+"]").parent().remove();
		}	
	});
	
	/**
	 * goToPath 要获取子文件夹目录信息的路径
	 * onClickLi 当前被点击的li标签，用来追加显示子文件夹信息
	 */
	function goToUl(goToPath,onClickLi){
		$.ajax({	
			data:"goToPath="+goToPath,
			url:"CopyAndMove/CopyAndMove",
			success:function(data1){
				var data = eval("(" + data1 + ")");
				for(var i=0;i<data.length;i++)
				{
					onClickLi.after("<ul class='file-info-ul'><li parent="+goToPath+"><img src='img/filetype/dictionary.png'>"+decodeURIComponent(decodeURIComponent(data[i]))+"</li></ul>");
				}		
			},  
			error:function(){  
				alert("出错啦，请刷新页面，重试");  
			},
		});
	} 
});
