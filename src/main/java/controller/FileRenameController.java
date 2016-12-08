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
@RequestMapping(value = "FileRenameController")
public class FileRenameController {
    @Autowired
    private FileExistController fileExistController;
    @Autowired
    private Environment env;
    //读取属性配置文件hdfs地址
    String hdfsAddress = env.getProperty("hdfsAddress");
    @RequestMapping(value = "fileRename")
    @ResponseBody
    public void FileRename(@RequestParam(required = false) String filePath, @RequestParam(required = false) String fileName, @RequestParam(required = false) String changedFilename) {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String username = (String) application.getAttribute("username");
        try {
            changedFilename = URLDecoder.decode(changedFilename, "UTF-8");
            filePath = "/" + username + URLDecoder.decode(filePath, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        int index;
        index = filePath.lastIndexOf("/");
        String fartherDic = filePath.substring(0, index + 1);
        changedFilename = fileExistController.WebRename(fartherDic, changedFilename);
        Path originPath = new Path(filePath);
        Path changedPath = new Path(fartherDic + changedFilename);
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsAddress), conf);
            fs.rename(originPath, changedPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
