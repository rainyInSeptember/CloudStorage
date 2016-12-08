package service;


import dao.UserMapper;
import model.NodeInfo;
import model.User;
import model.UserShareInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    //	添加用户
    public void addUser(String username, String password, String email, String role) {
        userMapper.saveUser(username, password, email, role);
    }

    //保存分享链接
    public void addShareUrl(String username, String url, String type, String password, String identify, String filesize, String modifytime, String fileKinds, String filename) {
        userMapper.saveShareInfo(username, url, type, password, identify, filesize, modifytime, fileKinds, filename);
    }

    //根据用户名查找密码
    public List<User> getPassword(String Name) {
        return userMapper.getUserByName(Name);
    }

    public List<UserShareInfo> getSharePassword(String identify) {

        return userMapper.getSharePassword(identify);
    }


    //查找所有用户
    public List<NodeInfo> getNodeInfo() {
        return userMapper.findAllNode();
    }


    //发送邮件


    private String host = "smtp.qq.com"; // smtp服务器
    private String user = "1602366628@qq.com"; // 用户名
    private String pwd = "yurainy10247678"; // 密码
    private String from = ""; // 发件人地址
    private String to = ""; // 收件人地址
    private String subject = ""; // 邮件标题

    public void setAddress(String from, String to, String subject) {
        this.from = from;
        this.to = to;
        this.subject = subject;

    }

    public void send(String txt) {
        Properties props = new Properties();
        // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);
        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true);
        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(from));

            // 加载收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 加载标题
            message.setSubject(subject);
            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(txt);
            multipart.addBodyPart(contentPart);

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱

            transport.connect(host, user, pwd);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
