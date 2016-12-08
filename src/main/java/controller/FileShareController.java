package controller;

import model.UserShareInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import service.UserService;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Administrator
 *         把要分享的文件插入到数据库
 */

@Controller
@RequestMapping(value = "fileShareController")
public class FileShareController {

    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    @Autowired
    private Environment env;
    //读取属性配置文件hdfs地址
    String hdfsAddress = env.getProperty("hdfsAddress");
    @Autowired
    private UserService userService;

    @RequestMapping(value = "fileShare")
    @ResponseBody
    public String fileShare(UserShareInfo userShareInfo,
                            @RequestParam(required = false) String[] shareFilePath,
                            @RequestParam(required = false) String type) {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String username = (String) application.getAttribute("username");
        String shareInfo = null;
        String url;
        String identify = getRandomALLChar();
        String password = getRandomALLChar();
        String modifytime = null;
        String filesize = null;
        int index;
        int subIndex;
        String filename = null;
        String fileKinds = null;
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy/MM/dd a HH:mm:ss");
        for (int i = 0; i < shareFilePath.length; i++) {
            //只添加了一项
            try {
                Path dictionary = new Path(hdfsAddress + "/" + username + URLDecoder.decode(shareFilePath[i], "UTF-8"));
                Configuration conf = new Configuration();
                FileSystem fs = FileSystem.get(URI.create(hdfsAddress), conf);
                FileStatus[] fileStatus = fs.listStatus(dictionary);
                for (int j = 0; j < fileStatus.length; j++) {
                    modifytime = sdfDateFormat.format(new Date(fileStatus[i].getModificationTime()));
                    index = fileStatus[i].getPath().toString().lastIndexOf("/");
                    filename = fileStatus[i].getPath().toString().substring(index + 1);
                    filesize = fileStatus[i].getLen() + "B";
                    subIndex = filename.lastIndexOf(".");
                    fileKinds = filename.substring(subIndex + 1);
                    //处理文本
                    if (fileKinds.equals("txt") || fileKinds.equals("doc") || fileKinds.equals("docx") || fileKinds.equals("wps") || fileKinds.equals("hlp") ||
                            fileKinds.equals("rtf") || fileKinds.equals("html") || fileKinds.equals("pdf") || fileKinds.equals("xlsx")) {
                        fileKinds = "text";
                    }
                    //处理图片
                    else if (fileKinds.equals("png") || fileKinds.equals("jpg") || fileKinds.equals("gif") || fileKinds.equals("ai") || fileKinds.equals("bmp") || fileKinds.equals("svg")) {
                        fileKinds = "pic";
                    }
                    //处理视频
                    else if (fileKinds.equals("mkv") || fileKinds.equals("avi") || fileKinds.equals("mp4") || fileKinds.equals("rmvb") || fileKinds.equals("wmv") || fileKinds.equals("flv") || fileKinds.equals("3gp")) {
                        fileKinds = "video";
                    }
                    //处理压缩()
                    else if (fileKinds.equals("rar") || fileKinds.equals("zip")) {
                        fileKinds = "compressed";
                    }
                    //处理音乐
                    else if (fileKinds.equals("mp3") || fileKinds.equals("wma") || fileKinds.equals("wav")) {
                        fileKinds = "music";
                    }
                    //处理未知
                    else {
                        fileKinds = "unknown";
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                url = URLDecoder.decode(shareFilePath[i], "UTF-8");
                if (type.equals("open")) {
                    userShareInfo.setType("O");
                    shareInfo = "分享链接：" + "localhost:8080/CloudStorage/O/" + identify;
                    userShareInfo.setPassword("");
                } else {
                    userShareInfo.setType("P");
                    shareInfo = "分享链接：" + "localhost:8080/CloudStorage/P/" + identify + "密码：" + password;
                    userShareInfo.setPassword(password);
                }
                userService.addShareUrl(username, url, userShareInfo.getType(), userShareInfo.getPassword(), identify, filesize, modifytime, fileKinds, filename);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            shareInfo = URLEncoder.encode(shareInfo, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return shareInfo;
    }

    public static String getRandomALLChar() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }


}
