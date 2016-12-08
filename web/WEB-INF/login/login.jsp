<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/animate-custom.css">
        
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/video/bg.css">
    </head>
    
    
    
    <body>
      <video autoplay  loop style="width:100%;">
        <source src="${pageContext.request.contextPath}/css/video/bg.ogg"/>
    </video>

        <div class="container">

            <section>				
                <div id="container_demo" >

                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <a class="hiddenanchor" id="tofindpassword"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form  action="${pageContext.request.contextPath}/login/login.action" method="post">
                                <h1>登录</h1>
                                 <span id="spanError" ><font color="red">${message}</font></span>
                                <p>
                                    <label for="username" class="uname" data-icon="u" > 用户名 </label>
                                    <input id="username" name="username" required="required" type="text" placeholder="USERNAME"/>
                                </p>
                                <p>
                                    <label for="password" class="youpasswd" data-icon="p">密码</label>
                                    <input id="password" name="password" required="required" type="password" placeholder="PASSWORD" />
                                </p>

                                <p class="login button"> 
                                    <input type="submit" value="Login" /> 
								</p>
                                <p class="change_link">
									还没有账号 ?
									<a href="#toregister" class="to_register">注册</a>
                                                                                                忘记密码？
                                    <a href="#tofindpassword" class="to_findpassword">找回密码</a>
								</p>
                            </form>
                        </div>
                        <div id="register" class="animate form">

                            <form  action="${pageContext.request.contextPath}/login/addUser.action" method="post">
                                <h1> 注册</h1>
                                <p> 
                                    <label for="usernamesignup" class="uname" data-icon="u">用户名</label>
                                    <input id="usernamesignup" name="username" required="required" type="text" placeholder="USERNAME" />
                                </p>
                                <p> 
                                    <label for="emailsignup" class="youmail" data-icon="e" > 邮箱</label>
                                    <input id="emailsignup" name="email" required="required" type="email" placeholder="EMAIL"/>
                                </p>
                                <p> 
                                    <label for="passwordsignup" class="youpasswd" data-icon="p">密码 </label>
                                    <input id="passwordsignup" name="password" required="required" type="password" placeholder="PASSWORD"/>
                                </p>
                                <p> 
                                    <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">确认密码</label>
                                    <input id="passwordsignup_confirm" name="password2" required="required" type="password" placeholder="PASSWORD"/>
                                </p>
                                <p class="signin button"> 
									<input type="submit" value="Sign up"/> 
								</p>
                                <p class="change_link">  
									已经有账号?
									<a href="#tologin" class="to_register">登录</a>
								</p>
                            </form>

                        </div>
                        <div id="findpassword" class="animate form">
                            <form  action="" method="post">
                                <h1> 找回密码</h1>
                                <p>
                                    <label for="usernamesignup" class="uname" data-icon="u">用户名</label>
                                    <input  name="usernamesignup" required="required" type="text" placeholder="USERNAME" />
                                </p>
                                <p>
                                    <label for="emailsignup" class="youmail" data-icon="e" > 邮箱</label>
                                    <input  name="emailsignup" required="required" type="email" placeholder="EMAIL"/>
                                </p>


                                <p class="signin button">
                                    <input type="submit" value="查询"/>
                                </p>
                                <p class="change_link">
                                                                                              已经有账号?
                                    <a href="#tologin" class="to_register">登录</a>
                                </p>
                            </form>
                        </div>

                    </div>
                </div>  
            </section>
        </div>
    </body>
</html>