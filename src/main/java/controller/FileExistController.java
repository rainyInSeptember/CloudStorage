package controller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URI;

@Controller
@RequestMapping(value = "FileExistController")
public class FileExistController {
    @Autowired
    private Environment env;
    //读取属性配置文件hdfs地址
    String hdfsAddress = env.getProperty("hdfsAddress");
    @RequestMapping("fileExistJudgement")
    public boolean FileExistJudgement(String fcPath) throws IOException {
        //测试判断同名文件、文件夹是不是已存在
        Path path = new Path(fcPath);
        FileSystem fs = null;
        try {
            Configuration conf = new Configuration();
            fs = FileSystem.get(URI.create(hdfsAddress), conf);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fs.exists(path)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @param fileName 要重命名的文件或者文件夹名
     * @return 文件名
     * @author 董森  2016/3/28
     */
    @RequestMapping(value = "rename")
    public String Rename(String fileName) {
        //带后缀
        if (fileName.contains(".")) {
            String preString;
            String subString;
            int index;
            index = fileName.lastIndexOf(".");
            preString = fileName.substring(0, index) + "(2)";
            subString = fileName.substring(index + 1);
            fileName = preString + "." + subString;
        } else {
            fileName = fileName + "(2)";
        }
        return fileName;

    }

    /**
     * @param copyfromPath 文件原始路径
     * @param copytoPath   复制、移动到到的路径
     * @return 移动到的路径
     * @author 董森 2016/3/28
     */
    @RequestMapping(value = "copyandmove")
    public String CopyAndMove(String copyfromPath, String copytoPath) {
        String fdname;
        String completePath = null; //移动后文件路径
        int index = copyfromPath.lastIndexOf("/");
        fdname = copyfromPath.substring(index + 1);
        try {
            //移动后的文件路径   /foodir/1
            completePath = copytoPath + "/" + fdname;
            while (FileExistJudgement(completePath)) {
                fdname = Rename(fdname);
                completePath = copytoPath + "/" + fdname;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return completePath;
    }

    /**
     * @param fartherDic      父文件路径  /  /hadoop/
     * @param changedFilename 要改为文件名
     * @return
     * @author 董森 2016/4/4
     */
    @RequestMapping(value = "rename2")
    public String WebRename(String fartherDic, String changedFilename) {
        String changedPath = fartherDic + changedFilename;
        try {
            while (FileExistJudgement(changedPath)) {
                changedFilename = Rename(changedFilename);
                changedPath = fartherDic + changedFilename;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return changedFilename;
    }

    @RequestMapping(value = "rename3")
    public String createDicNameJudgement(String dicName, String currentPath) {
        String path;
        if (currentPath.equals("/")) {
            path = hdfsAddress + "/" + dicName;
        } else {
            path = hdfsAddress + currentPath + "/" + dicName;
        }
        try {
            while (FileExistJudgement(path)) {
                dicName = Rename(dicName);
                path = hdfsAddress + currentPath + "/" + dicName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dicName;
    }

}
