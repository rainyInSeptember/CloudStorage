


 function hideError()
   {
   document.getElementById("spanError").style.display="none";
   }
 
 function checkName()
 {
 	var myname=document.getElementById("usernamesignup").value;
 	var myDivname=document.getElementById("unamesignup");
 	if(myname.length < 6 || myname.length > 12)
 	{
 		myDivname.innerHTML="<font color='red'>长度6~12数字、字母、下划线</font>";
 		return false;
 	}

 	else if(6<= myname.length <=12)
 	{
 		for(var i=0;i<myname.length;i++)
 		{
 			var text=myname.charAt(i);
 			if(!(text<=9&&text>=0)&&!(text>='a'&&text<='z')&&!(text>='A'&&text<='Z')&&text!="_")
 			{
 				myDivname.innerHTML="<font color='red'>由数字、字母、下划线组成</font>";
 				break;
 			}
 		}
 		if(i>=myname.length)
 		{
 			myDivname.innerHTML="<font color='green'>√</font>";
 			 return true;
 		}
      
 	}
 }
 
 
 function checkPassword()  //检查密码 
 {
 	var myPassword=document.getElementById("passwordsignup").value;
 	var mySpanPassword=document.getElementById("upasswordsignup");
 	
 	if(myPassword.length < 6 || myPassword.length > 12)
 	{
 		mySpanPassword.innerHTML="<font color='red'>长度6~12数字、字母、下划线</font>";
 		return false;
 	}

 	else if(6<= myPassword.length <=12)
 	{
 		for(var i=0;i<myPassword.length;i++)
 		{
 			var text=myPassword.charAt(i);
 			if(!(text<=9&&text>=0)&&!(text>='a'&&text<='z')&&!(text>='A'&&text<='Z')&&text!="_")
 			{
 				mySpanPassword.innerHTML="<font color='red'>由数字、字母、下划线组成</font>";
 				break;
 			}
 		}
 		if(i>=myPassword.length)
 		{
 			mySpanPassword.innerHTML="<font color='green'>√</font>";
 			return true;
 		}
 	}

 }
 function checkPasswordAgain()  //检查密码 
 {
 	var myPassword=document.getElementById("passwordsignup_confirm").value;
 	var mySpanPassword=document.getElementById("upassword2signup");
 
 	 if(document.getElementById("passwordsignup").value!=document.getElementById("passwordsignup_confirm").value)
 	{
 		mySpanPassword.innerHTML="<font color='red'>两次输入的密码不一致</font>";
 	    return false;
 	}
 	else
 	{
 		mySpanPassword.innerHTML="<font color='green'>√</font>";
 		return true;
 	}
 	 
 }

 function checkAll()
 {
	if(checkPasswordAgain()&&checkName()&&checkPassword() )
		{
		return true;
		}
	else
		{
      	return false;      
		}
	 
 }
 
 
 
 
 
 