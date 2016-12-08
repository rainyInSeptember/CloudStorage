package controller;

import model.UserShareInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "showFile")
public class ShowFileController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "showFilePrivate", method = RequestMethod.POST)
    public ModelAndView showShareFilePrivate(ModelMap mod, HttpServletRequest request) {
        System.out.println("Private查看分享的文件");
        String password = request.getParameter("password");
//		对密码校验
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String sharePath = (String) application.getAttribute("sharePath");
        String identify;
        String sharetype;
        int index;
        index = sharePath.lastIndexOf("/");
        identify = sharePath.substring(index + 1);
        sharetype = sharePath.substring(1, index);
        String url;
        String filesize = "--";
        String modifytime = "--";
        String filekinds = "--";
        String filename;
        //公开分享 identify=>密码  ，密码和输入相等 查看分享的文件 =》share.jsp，否则，返回到输入密码的页面
        //私密分享  identify=>密码 ，如果密码为空 则查看分享的文件 share.jsp
        List<UserShareInfo> shareList = userService.getSharePassword(identify);
        if (shareList.size() > 0) {
            String getSharePassword = shareList.get(0).getPassword();
            System.out.println("获取的分享密码" + getSharePassword);
            if (getSharePassword.equals(password)) {
                url = shareList.get(0).getUrl();
                modifytime = shareList.get(0).getModifytime();
                filesize = shareList.get(0).getFilesize();
                filekinds = shareList.get(0).getFilekinds();
                filename = shareList.get(0).getFilename();
                System.out.println("url");
                mod.addAttribute("url", url);
                mod.addAttribute("modifytime", modifytime);
                mod.addAttribute("filesize", filesize);
                mod.addAttribute("filekinds", filekinds);
                mod.addAttribute("filename", filename);
                return new ModelAndView("login/share");
            } else {
                mod.addAttribute("errormessage", "密码错误");
                return new ModelAndView("login/share_input");
            }
        } else {
            mod.addAttribute("errormessage", "文件不存在");
            return new ModelAndView("login/filenotfound");
        }

    }

    @RequestMapping(value = "showFileOpen")
    public void showShareFileOpen(HttpServletRequest request) {
        System.out.println("Open查看分享的文件");
//		返回文件列表
    }
}
