package controller;


import com.alibaba.fastjson.JSONArray;
import model.FileShow;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "browseDictionary")
public class BrowseDictionaryController {

    @Autowired
    private Environment env;
    //读取属性配置文件hdfs地址
    String hdfsAddress = env.getProperty("hdfsAddress");
    @RequestMapping(value = "browseDictionary")
    @ResponseBody
    public String BrowseDictionary(@RequestParam(required = false) String goToPath,
                                   HttpServletResponse response) {

        System.setProperty("hadoop.home.dir","C:/hadoop");
//        获取每个用户的根目录文件夹名
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        String username = (String) application.getAttribute("username");
        Map<String, String> filenumMap = new HashMap<>();
        int fileNum = 0;//加载文件个数
        int index;
        int subIndex;
        String filename;   //文件名
        String modifytime; //修改时间
        String fileKinds;  //文件类型
        String path;
        List<FileShow> fileShowList = new ArrayList<>();
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy/MM/dd a HH:mm:ss");
        try {
            goToPath = URLDecoder.decode(goToPath, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        if (goToPath == null) {
            path = "/" + username;
        } else {
            if (goToPath.equals("/")) {
                path = goToPath+username;
            } else {
                path = "/" + username + goToPath;
            }
        }
        try {
            Path dictionary = new Path(hdfsAddress + path);
            Configuration conf = new Configuration();
            FileSystem fs =FileSystem.get(URI.create(hdfsAddress),conf);
            FileStatus[] fileStatus = fs.listStatus(dictionary);
            fileNum = fileStatus.length;
            for (int i = 0; i < fileStatus.length; i++) {
                System.out.println(fileStatus[i].getPath());
                //dictionary 递归调用 遍历文件结构
                //保存获取的文件信息
                if (fileStatus[i].isDir()) {
                    index = fileStatus[i].getPath().toString().lastIndexOf("/");

                    filename = fileStatus[i].getPath().toString().substring(index + 1);
                    modifytime = sdfDateFormat.format(new Date(fileStatus[i].getModificationTime()));
                    FileShow f =new FileShow();
                    f.setFilename(URLEncoder.encode(filename,"UTF-8"));
                    f.setFilesize("--");
                    f.setFiletype("dictionary");
                    f.setModefitime(URLEncoder.encode(modifytime,"UTF-8"));
                    fileShowList.add(f);
                } else {
                    index = fileStatus[i].getPath().toString().lastIndexOf("/");
                    filename = fileStatus[i].getPath().toString().substring(index + 1);
                    modifytime = sdfDateFormat.format(new Date(fileStatus[i].getModificationTime()));
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
                    FileShow f =new FileShow();
                    f.setFilename(URLEncoder.encode(filename,"UTF-8"));
                    f.setFilesize(fileStatus[i].getLen() + "B");
                    f.setFiletype(fileKinds);
                    f.setModefitime(URLEncoder.encode(modifytime,"UTF-8"));
                    fileShowList.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //显示文件个数  未添加进json！！！！！！！！！！！！！！！
        filenumMap.put("filenum", fileNum + "");
        return JSONArray.toJSONString(fileShowList);
    }

}
