<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'share_open.jsp' starting page</title>
    
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/share.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/share.js"></script>
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
		</div>
	</div>
	</nav>

	<div id="buttonGroup">
	<button type='button' class='btn btn-primary' id='download-btn' onclick='filedownload("${url}")'>文件下载</button>
	<button type='button' class='btn btn-primary'>保存到我的网盘</button>		
    </div>	
	<div id="frame-head-tr">
	<div class='frame-head-filename'>文件名</div>
	<div class='frame-head-filesize'>文件大小</div>
	<div class='frame-head-modifytime'>修改时间</div>
	</div>	

<br/>
<div id="shareContent">
    <table class="table" style="width:1088px;">
    <tr height="50px"><td width="600px"><img src="img/filetype/${filekinds}.png"/>${filename}</td><td width="248px">${filesize}</td><td width="240px">${modifytime}</td></tr>
    <tr><td></td><td></td><td></td></tr>
    </table>
	
</div>


  </body>
</html>
