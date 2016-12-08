<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'share_inputpassword.jsp' starting page</title>
    
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
<style>

#inputpassword_div{
width:400px;
height:200px;
margin: 300px auto;

}
#password_input{
width:200px;
height:33px;
}
</style>





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
	<div id="inputpassword_div">
	<form action="showFile/showFilePrivate" method="post">
	<span>${errormessage}</span></br>
	<input id="password_input" type="text" name="password" />
	<button class="btn btn-primary" type="submit">查看文件</button>
	</form>
	</div>
  </body>
</html>
