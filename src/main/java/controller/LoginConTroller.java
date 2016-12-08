package controller;



import model.NodeInfo;
import model.User;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;


@Controller

@RequestMapping(value="/login")//所有action请求，必须是以/login开头
public class LoginConTroller
{

	@Autowired
	private UserService userService;
	@Autowired
	private Environment env;
	//读取属性配置文件hdfs地址
	String hdfsAddress = env.getProperty("hdfsAddress");
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView  login(ModelMap mod,@RequestParam String username,@RequestParam String password,HttpServletRequest re)
	{
		System.out.println("denglu");
		List<User> u=userService.getPassword(username);
		mod.addAttribute("name",username);
		mod.addAttribute("password",password);
		if(u.size()==0)
		{
			mod.addAttribute("message","用户名或者密码错误");
			return new ModelAndView("login/login");
		}
		if(u.get(0).getPassword().equals(password))
		{
			//把用户名保存为全局变量
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			ServletContext application = webApplicationContext.getServletContext();
			application.setAttribute("username",username);

			if(u.get(0).getRole().equals("user"))
			{
				System.out.println("password    "+u.get(0).getPassword());
				mod.addAttribute("username", username);
				return new ModelAndView("login/index");
			}
			else
			{
				//管理员界面
				ModelAndView modelAndView=new ModelAndView("login/admin");
				List<NodeInfo> nodeInfos=userService.getNodeInfo();
				modelAndView.addObject("nodeInfos",nodeInfos);
				System.out.println(nodeInfos.size());
				System.out.println("admin");
				mod.addAttribute("username", username);
				return modelAndView;
			}
		}
		else
		{
			mod.addAttribute("message","用户名或者密码错误");
			return new ModelAndView("login/login");
		}
	}

	/**
	 * @author 董森 2016/4/16
	 * @param email 邮箱
	 * @param password 密码一
	 * @param password2 密码二
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public ModelAndView addUser(ModelMap mod,@RequestParam String email,@RequestParam String username,@RequestParam String password,@RequestParam String password2) throws UnsupportedEncodingException

	{
		List<User> u=userService.getPassword(username);
		if(u.size()!=0)
		{
			mod.addAttribute("message","用户名已经存在");
			return new ModelAndView("login/login");
		}
		userService.addUser(username,password,email,"user");
		//保存用户名为全局变量
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext application = webApplicationContext.getServletContext();
		application.setAttribute("username",username);

		//为用户创建自己的文件存放根目录
		Path userRootPath=new Path(hdfsAddress+"/"+username);
		try {
			Configuration conf=new Configuration();
			FileSystem fs = FileSystem.get(URI.create(hdfsAddress),conf);
			fs.mkdirs(userRootPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("login/index");
	}



//	@RequestMapping(value="/findPasswordPage")
//	public String gotofindPasswordPage()
//	{
//		return "login/findPassword";
//	}
//
//	@RequestMapping(value="/findPassword",method=RequestMethod.GET)
//	public String findPassword(@RequestParam String username, @RequestParam String email,ModelMap model)
//	{
//
//		if(username.equals(""))
//		{
//			model.addAttribute("errormessage", "用户名不能为空");
//			return "login/findPassword";
//		}
//		if(email.equals(""))
//		{
//			model.addAttribute("errormessage", "邮箱不能为空");
//			return "login/findPassword";
//		}
//
//
//		List <User> u =userService.getPassword(username);
//		if(u.size()==0)
//		{
//			model.addAttribute("errormessage", "该用户不存在");
//			return "login/findPassword";
//		}
//		String email2=u.get(0).getEmail();
//		if(!email.equals(email2))
//		{
//			model.addAttribute("errormessage", "邮箱输入错误");
//			return "login/findPassword";
//		}
//		userService.setAddress("1602366628@qq.com", email, "密码");
//		String pass=u.get(0).getPassword();
//
//		userService.send("您的密码是："+pass+"    如非本人操作，请更换密码，确保账户安全");
//		System.out.println("passsssss"+pass);
//
//		model.addAttribute("errormessage", "邮件发送成功，注意查收");
//		return "login/findPassword";
//	}
//
//
//
//
//
//
//	@RequestMapping(value="/updatePassword")
//	public String updatePassword()
//	{
//		return "login/updatePassword";
//	}
//
//	//	修改密码action
//	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
//	public String changePassword(@RequestParam String password,@RequestParam String newpassword1,@RequestParam String newpassword2,ModelMap mod,User user)
//	{
//		//		获取application中保存的用户名
//		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//		ServletContext application = webApplicationContext.getServletContext();
//		String username=(String) application.getAttribute("name");
//		System.out.println("username***********"+username);
//		//	根据用户名读取密码
//		if(username==null)
//		{
//			mod.addAttribute("message", "无法判断当前密码，请刷新，或者重新登录");
//			return "login/updatePassword";
//		}
//		List<User> u=userService.getPassword(username);
//
//
//		if(password.equals(""))
//		{
//			mod.addAttribute("message","当前密码不能为空");
//			return "login/updatePassword";
//		}
//		if(newpassword1.equals(""))
//		{
//			mod.addAttribute("message","新密码不能为空");
//			return "login/updatePassword";
//
//		}
//		if(newpassword2.equals(""))
//		{
//			mod.addAttribute("message", "确认密码不能为空");
//			return "login/updatePassword";
//		}
//
//		if(u.get(0).getPassword().equals(password))//输入当前密码正确
//		{
//			if(newpassword1.equals(newpassword2))//修改密码
//			{
//
//				List<User> un=userService.getPassword(username);
//
//				user.setName(username);
//				user.setPassword(newpassword1);
//				user.setEmail(un.get(0).getEmail());
//				user.setRole(un.get(0).getRole());
//				user.setId(u.get(0).getId());
//				userService.updateUser(user);
//				mod.addAttribute("message", "密码修改成功");
//				return "login/updatePassword";
//			}
//			else
//			{
//				mod.addAttribute("message", "两次输入密码不一致");
//				return "login/updatePassword";
//			}
//
//		}
//		else
//		{
//			mod.addAttribute("message", "当前密码不正确");
//			return "login/updatePassword";
//		}
//
//	}
//
//
//	//国际化中英文切换
//	@RequestMapping(value="/i18n/{type}",method=RequestMethod.GET)
//	public String lang(HttpServletRequest request,@PathVariable String type){
//
//		String langType = type;
//		//	    中文
//		if(langType.equals("zh")){
//			Locale locale = new Locale("zh", "CN");
//			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
//		}
//		//        英文
//		else if(langType.equals("en")){
//			Locale locale = new Locale("en", "US");
//			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
//		}
//		//        默认
//		else 
//			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());
//		return "login/index";
//	}
//
//

}
