package controller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;


@Controller
@RequestMapping(value = "fileUpload")
public class FileUploadController {

    @Autowired
    private FileExistController fileExistController;
    @Autowired
    private Environment env;
    //读取属性配置文件hdfs地址
    String hdfsAddress = env.getProperty("hdfsAddress");
    @RequestMapping(value = "/gotofileUpload")
    public ModelAndView FileUpload() {
        ModelAndView modelAndView = new ModelAndView("login/fileUpload");
        return modelAndView;
    }
    @RequestMapping(value = "/fileUpload")
    public void Upload(HttpServletRequest request) throws IOException {
        String fileName;
        String uploadPath;
        String fullPath;
        uploadPath = request.getParameter("currentPath");
        //获取文件上传时当前路径，文件上传到当前路径
        uploadPath = URLDecoder.decode(uploadPath, "UTF-8");
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("uploadify");
        //文件名 ，去除空格，负责出错
        fileName = file.getOriginalFilename().trim().replace(" ", "");
        if (uploadPath.equals("/")) {
            fullPath = "/" + fileName;
        } else {
            fullPath = uploadPath + "/" + fileName;
        }
        while (fileExistController.FileExistJudgement(fullPath)) {
            //重命名文件
            fileName = fileExistController.Rename(fileName);
            if (uploadPath.equals("/")) {
                fullPath = "/" + fileName;
            } else {
                fullPath = uploadPath + "/" + fileName;
            }
        }

        if (file.getSize() > 0) {
            try {
                SaveFileFromInputStream(file.getInputStream(), fullPath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void SaveFileFromInputStream(InputStream stream, String fullPath) throws IOException {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String username = (String) application.getAttribute("username");
        String dst = hdfsAddress+ "/" + username + fullPath;
        Configuration conf = new Configuration();
        FileSystem fs2 = FileSystem.get(URI.create(dst), conf);
        OutputStream out = fs2.create(new Path(dst), new Progressable() {
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(stream, out, 4096, true);
        stream.close();
    }
}
