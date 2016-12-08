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
import java.net.URI;
import java.net.URLDecoder;

/**
 * @author 董森     2016/3/26
 *         文件或者文件夹删除
 *         deletePath 要删除的文件或者文件夹数组
 */
@Controller
@RequestMapping(value = "DeleteController")
public class DeleteController {
    @Autowired
    private Environment env;
    //读取属性配置文件hdfs地址
    String hdfsAddress = env.getProperty("hdfsAddress");
    @RequestMapping(value = "Delete")
    @ResponseBody
    public void deleteFileOrDictionary(@RequestParam(required = false) String[] deletePath) {
        String deletefd;
        Path deleteP;
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String username = (String) application.getAttribute("username");
        try {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsAddress), conf);
            for (int i = 0; i < deletePath.length; i++) {
                deletefd = "/" + username + URLDecoder.decode(deletePath[i], "UTF-8");
                deleteP = new Path(deletefd);
                fs.delete(deleteP, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
