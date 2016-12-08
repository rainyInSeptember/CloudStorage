<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'admin.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/admin.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
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

	<div id="frame-head-tr">
	<div class='frame-head-filename'>主机名</div>
	<div class='frame-head-filesize'>主机ip</div>
	<div class='frame-head-modifytime'>容量</div>
	<div class='frame-head-operate'>操作</div>
	</div>	

<br/>
<div id="shareContent">
    <table class="table" style="width:1088px;">
    	<c:forEach var="nodeInfos" items="${nodeInfos}">
    <tr height="50px">
    <td width="400px">${nodeInfos.nodename}</td>
    <td width="248px">${nodeInfos.nodeip}</td>
    <td width="240px">${nodeInfos.capacity}</td>
    <td width="200px"><button type='button' class='btn btn-primary' id='download-btn' onclick='showDetail("${nodeInfos.nodename}")'>查看详情</button></td></tr>
    </c:forEach>
    <tr><td></td><td></td><td></td><td></td></tr>
    </table>
	
	
	
	<!--显示详细信息摩太狂 -->
	<div class="modal fade" id="detail-info-myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
					  当前节点详细信息
					</h4>
				</div>
				<div class="modal-body">
				<div class="file-info-container" style="font-size:18px">
						
				</div>		
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="copyAndMoveBtn" class="btn btn-primary">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	
	
	
	
	
</div>
  </body>
</html>
