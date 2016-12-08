package controller;


import com.alibaba.fastjson.JSONArray;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
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
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "CopyAndMove")
public class CopyAndMoveController {

    @Autowired
    private FileExistController fileExistController;
    @Autowired
    private Environment env;
    //读取属性配置文件hdfs地址
    String hdfsAddress = env.getProperty("hdfsAddress");

    @RequestMapping(value = "CopyAndMove")
    @ResponseBody
    public String GetCopyAndMoveDictionary(@RequestParam(required = false) String goToPath) {

        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String username = (String) application.getAttribute("username");
        if (goToPath == null) {
            goToPath = "/" + username;
        } else if (goToPath.equals("/")) {
            goToPath = goToPath + username;
        } else {
            goToPath = "/" + username + goToPath;
        }
        int index;
        String path;
        path = goToPath;
        List<String> dicList = new ArrayList<>();
        try {
            Path dictionary = new Path(path);
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsAddress), conf);
            FileStatus[] fileStatus = fs.listStatus(dictionary);
            for (int i = 0; i < fileStatus.length; i++) {
                //dictionary 递归调用 遍历文件结构
                //保存获取的文件信息
                if (fileStatus[i].isDir()) {
                    index = fileStatus[i].getPath().toString().lastIndexOf("/");
                    //添加文件名
                    dicList.add(URLEncoder.encode(fileStatus[i].getPath().toString().substring(index + 1), "UTF-8"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONArray.toJSONString(dicList);
    }

    @RequestMapping(value = "Copy")
    @ResponseBody
    public void Copy(@RequestParam(required = false) String[] copyFilePath, @RequestParam(required = false) String copyToPath) {
        String cfP;
        Path cfPath;
        String completePath;
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String username = (String) application.getAttribute("username");
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsAddress), conf);
            copyToPath = "/" + username + URLDecoder.decode(copyToPath, "UTF-8");
            for (int i = 0; i < copyFilePath.length; i++) {
                cfP = URLDecoder.decode(copyFilePath[i], "UTF-8");
                completePath = fileExistController.CopyAndMove(cfP, copyToPath);
                cfPath = new Path("/" + username + cfP);
                Path ctPath = new Path(completePath);
                FileUtil.copy(fs, cfPath, fs, ctPath, false, conf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "Move")
    @ResponseBody
    public void Move(@RequestParam(required = false) String[] moveFilePath, @RequestParam(required = false) String moveToPath) {
        String mfP;
        String completePath;
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String username = (String) application.getAttribute("username");
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsAddress), conf);
            moveToPath = "/" + username + URLDecoder.decode(moveToPath, "UTF-8");
            for (int i = 0; i < moveFilePath.length; i++) {
                mfP = URLDecoder.decode(moveFilePath[i], "UTF-8");
                completePath = fileExistController.CopyAndMove(mfP, moveToPath);
                Path mfPath = new Path("/" + username + mfP);
                Path mtPath = new Path(completePath);
                FileUtil.copy(fs, mfPath, fs, mtPath, true, conf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


