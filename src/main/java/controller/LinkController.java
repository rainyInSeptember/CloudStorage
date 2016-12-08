package controller;

import model.UserShareInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LinkController {
	@Autowired
	private UserService userService;


	@RequestMapping(value="/")
	public ModelAndView mainPage()
	{
		return new ModelAndView("login/login");
	}

	@RequestMapping(value="/O/*")
	public ModelAndView open(ModelMap mod,HttpServletRequest request)
	{
		System.setProperty("hadoop.home.dir", "c:/hadoop");
		String sharePath=request.getServletPath();
		String identify;
		int index;
		index=sharePath.lastIndexOf("/");
		identify=sharePath.substring(index+1);
		String url;
		String filesize="--";
		String modifytime="--";
		String filekinds="--";
		String filename="--";
		List<UserShareInfo> shareList =userService.getSharePassword(identify);
		if(shareList.size()>0)
		{
			System.out.println("share");
			System.out.println(shareList.get(0).getPassword());
			if(shareList.get(0).getPassword().equals("")||shareList.get(0).getType().equals("O"))
			{
				url=shareList.get(0).getUrl();
				modifytime=shareList.get(0).getModifytime();
				filesize=shareList.get(0).getFilesize();
				filekinds=shareList.get(0).getFilekinds();
				filename=shareList.get(0).getFilename();
				mod.addAttribute("url", url);
				mod.addAttribute("modifytime", modifytime);
				mod.addAttribute("filesize", filesize);
				mod.addAttribute("filekinds", filekinds);
				mod.addAttribute("filename", filename);
				return new ModelAndView("login/share");
			} else {
				return new ModelAndView("login/filenotfound");
			}

		} else
		{
			return new ModelAndView("login/filenotfound");
		}


	}
	@RequestMapping(value="/P/*")
	public ModelAndView privat(HttpServletRequest request)
	{
		String sharePath=request.getServletPath();
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext application = webApplicationContext.getServletContext();
		application.setAttribute("sharePath",sharePath);
		return new ModelAndView("login/share_input");
	}
}
