<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>云存储</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/index_head.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/index.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/index.css">
<link rel="stylesheet" type="text/css" href="uploadify/uploadify.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/CopyAndMove.js"></script>


<script type="text/javascript">
	$(function(){
	var currentPath;
		$("#uploadify").uploadify({    
			'debug'     : false, //开启调试
	        'auto'           : true, //是否自动上传   
	        'swf'            : 'uploadify/uploadify.swf',  //引入uploadify.swf  
	        'uploader'       : '../fileUpload/fileUpload',//请求路径  后台处理程序的相对路径    处理上传文件的服务类
	        'queueID'        : 'fileQueue',//队列id,用来展示上传进度的  
	        'width'     : '105',  //按钮宽度  
	        'height'    : '30',  //按钮高度
	        'queueSizeLimit' : 10,  //同时上传文件的个数   
	        'fileTypeDesc'   : '可选文件',     //可选择文件类型说明
	        'fileTypeExts'   : '*.*', //控制可上传文件的扩展名   
	        'multi'          : true,  //允许多文件上传 
	        'buttonText'     : '点击上传',//按钮上的文字  
	        'fileSizeLimit'  : '2000MB',  
	        'fileObjName'    : 'uploadify',   //设置单个文件大小限制
	        'method'         : 'get',  
	        'removeCompleted' : true,//上传完成后自动删除队列  
	        'onFallback':function(){    
	            alert("未安装flash");    
	        },         
	        'onUploadStart' : function(file) 
	        { 
	         currentPath=$("#currentPath").text();
	         currentPath=encodeURIComponent(currentPath);
                //传递参数
	         $("#uploadify").uploadify("settings", "formData", {'currentPath':currentPath});  
	        },  
	        'onUploadSuccess' : function(file, data, response)
	        {
	        },
	        'onQueueComplete' : function(){ 
	        	alert("上传成功!");
	        	 var currentPath=$("#currentPath").text();
	            
	            if($(".table-content-div").has("table").length)
				{
				     if(currentPath=="/")
				         {
				          tableInit();//不能调用
				          }
				      else
				     {       
				         gotoTable(encodeURIComponent(encodeURIComponent(currentPath)));
				     }			
				}

				else
				{
				if(currentPath=="/")
				{
				divInit();
				}
				else
				{gotoDiv(encodeURIComponent(encodeURIComponent(currentPath)));}
				
				}
				$("#myModal").modal("hide");
	       		}  
	        });
	});					
	</script>
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a href="#" class="navbar-brand">云存储</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="#">主页</a></li>
				<li class="active"><a href="#">网盘</a></li>
				<li><a href="#">分享</a></li>
			
			</ul>
		<div style="padding-left:1100px;padding-top:10px;font-size:20px;">
		<font color="white">${username}</font>	
		</div>
			

		</div>
	</div>
	</nav>
	<div class="frame-all">

		<div class="frame-aside">
			<br> <br> <br>
			<div>
			<ul class="list-group">
				<li class="list-group-item">全部文件 <span class="badge">*</span></li>
				<li class="list-group-item">图片 <span class="badge">*</span></li>
				<li class="list-group-item">文档 <span class="badge">*</span></li>
				<li class="list-group-item">视频 <span class="badge">*</span></li>
				<li class="list-group-item">音乐 <span class="badge">*</span></li>
				<li class="list-group-item">其他 <span class="badge">*</span></li>
				<li class="list-group-item">我的分享 <span class="badge">*</span></li>
			</ul>

          </div>
          <br/>
          <br/>
          <br/>
          <br/>
          <span >&nbsp;&nbsp;容量</span>
          <div class="frame-pb" style="width:180px;height:25px;margin-left:10px;border:1px solid #5BC0DE">
                   <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
                        <span >6G/10G</span>
                    </div>
          </div>
                 
		</div>
		

		<div class="frame-main">
			<div class="main_header">
				<div class="header-button">
					
					<button id="showUl" type="button" data-backdrop="static" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
						上传文件</button>
					<button type="button" class="btn btn-primary" onclick="createDic()">新建文件夹</button>
				
				</div>
				
				
				<div class="header-list">
					<ul class="list-switch">
						<li class="list-1" id="list-1" style="background-position: 0px 0px"></li>
						<li class="list-2" id="list-2" style="background-position: -30px 0px"></li>
					</ul>
				</div>
			</div>
			<!-- 信息显示 -->
			<div class="fileinfo">
				<a class="goback">返回上一级</a> <span class="filenum"></span> 当前文件位置：<span id="currentPath"></span>
			</div>


			<!-- 头部信息 -->
			<div id="frame-head">
				<div class="frame-head-checkbox">
					<input type="checkbox" class="all-Checkbox">
				</div>

				<div id="frame-head-tr">
				<div class='frame-head-filename'>文件名</div>" +
			"<div class='frame-head-filesize'>文件大小</div>" +
	"<div class='frame-head-modifytime'>修改时间</div>
				
				</div>
			</div>

			<!--内容显示 -->
			<div class="table-content-div"></div>
		</div>

	</div>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">当前目录</h4>
				</div>
				<div class="modal-body">
					<div class="container">

						<input type="file" id="uploadify" name="uploadify" multiple="true" enctype="multipart/form-data">
						<div id="fileQueue"></div>
						<br> <a href="javascript:$('#uploadify').uploadify('cancel','*')"><input type="button" class="btn-default" style="width:150px ;height:30px" name="stopUpload" value="取消上传 "></a>



					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary">确定</button>
				</div>
			</div>
		</div>
	</div>

	<!-- download modal -->

	<div class="modal fade" id="prompts-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">提示</h4>
				</div>
				<div class="modal-body" id="prompts-modal-content">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				    <button type="button" class="btn btn-primary" id="prompts-sure-btn" onclick="deleteFile()">确定</button>
				</div>
			</div>
		</div>
	</div>

<!--显示移动复制的文件选择框 -->
	<div class="modal fade" id="file-info-myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						当前文件目录: <span id="current-file-path"></span>
					</h4>
				</div>
				<div class="modal-body">
				<div class="file-info-container">
				<!--全部文件问题，第一个点击事件有bug -->
                <ul class="file-info-ul">
				</ul>
				
				</div>		
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="copyAndMoveBtn" class="btn btn-primary">确定</button>
				</div>
			</div>
		</div>
	</div>





<!--显示创建分享-->
	<div class="modal fade" id="file-share-myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				
				</div>
				<div class="modal-body">
				     <div class="file-share-container">
				     <div id="share_button">
				     <!-- 生成公开链接 localhost:8080/CloudStorage/Open/XXXXXXXX -->
				    <button type="button" class="btn btn-primary" id="share-open" onclick="share_open()">创建公开分享</button> 
				     <!-- 生成带密码链接 localhost:8080/CloudStorage/Private/XXXXXXXX  密码XXXXXXX-->
				     <button type="button" class="btn btn-primary" id="share-private" onclick="share_private()">创建私密分享</button>             
				       <!-- 显示公开或者私密分享的链接 -->
				      </div>
				      <div id="share_info">
				        
				     </div>
				   <!-- <button type="button" class="btn btn-primary" id="share-copy" onclick="share_copy()">复制链接</button>  -->    
				     </div>		
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<!-- <button type="button" id="copyAndMoveBtn" class="btn btn-primary">确定</button> -->	
				</div>
			</div>
		</div>
	</div>











</body>
</html>