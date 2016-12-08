package controller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@Controller
@RequestMapping(value="nodeInfo")
public class NodeInfoController {

	@Autowired
	private Environment env;
	//读取属性配置文件hdfs地址
	String hdfsAddress = env.getProperty("hdfsAddress");
	@RequestMapping(value="nodeInfo")
	public @ResponseBody String getNodeInfo(@RequestParam(required=false) String nodename)
	{
		System.setProperty("hadoop.home.dir", "c:/hadoop");
		String detailinfo = null;
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(URI.create(hdfsAddress),conf);
			DistributedFileSystem hdfs = (DistributedFileSystem)fs;
			DatanodeInfo[] dataNodeStats = hdfs.getDataNodeStats();

			for(int i=0;i<dataNodeStats.length;i++){
				//传递节点名
				if(dataNodeStats[i].getHostName().equals(nodename))
				{
					detailinfo=dataNodeStats[i].getDatanodeReport();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailinfo;
	}
}
