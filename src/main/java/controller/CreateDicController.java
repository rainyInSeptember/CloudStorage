package controller;

import org.apache.hadoop.conf.Configuration;
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

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

@Controller
@RequestMapping(value = "CreateDicController")
public class CreateDicController {

    @Autowired
    private FileExistController fileExistController;
    @Autowired
    private Environment env;
    //读取属性配置文件hdfs地址
    String hdfsAddress = env.getProperty("hdfsAddress");
    @RequestMapping(value = "createDic")
    @ResponseBody
    public void CreateDic(@RequestParam(required = false) String dicName,
                          @RequestParam(required = false) String currentPath) {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String username = (String) application.getAttribute("username");
        try {
            dicName = URLDecoder.decode(dicName, "UTF-8");
            currentPath = URLDecoder.decode(currentPath, "UTF-8");
            if (currentPath.equals("/")) {
                currentPath = "/" + username;
            } else {
                currentPath = "/" + username + URLDecoder.decode(currentPath, "UTF-8");
            }

        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        //去掉收尾空格
        dicName = dicName.trim();
        dicName = fileExistController.createDicNameJudgement(dicName, currentPath);
        Path dicPath = new Path(hdfsAddress + currentPath + "/" + dicName);
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsAddress), conf);
            fs.mkdirs(dicPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
