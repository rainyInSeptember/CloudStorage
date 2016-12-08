package controller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;


@Controller
@RequestMapping(value = "fileDownload")
public class FileDownloadController {
    @Autowired
    private Environment env;
    //读取属性配置文件hdfs地址
    String hdfsAddress = env.getProperty("hdfsAddress");
    @RequestMapping(value = "fileDownload")
    public void downloadIndex(@RequestParam(required = false) String downloadFilePath,
                              @RequestParam(required = false) String filesize,
                              HttpServletResponse response) throws Exception {
        String filePath;
        String filenamedisplay;
        int index;
        filePath = URLDecoder.decode(downloadFilePath, "UTF-8");
        index = filePath.lastIndexOf("/");
        filenamedisplay = filePath.substring(index + 1);
        try {
            response.setContentType("application/x-download");//设置为下载application/x-download
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext application = webApplicationContext.getServletContext();
            String username = (String) application.getAttribute("username");

            Path path = new Path(hdfsAddress+"/" + username + filePath);
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(hdfsAddress), conf);
            FSDataInputStream fsOut = fs.open(path);
            //中文不显示问题
            filenamedisplay = new String(filenamedisplay.getBytes("utf-8"), "ISO-8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=" + filenamedisplay);
            response.addHeader("Content-Length", filesize);
            //attachment表示网页会出现保存、打开对话框
            OutputStream output;
            output = response.getOutputStream();
            IOUtils.copyBytes(fsOut, output, 4096, true);

            fsOut.close();
            output.close();
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
