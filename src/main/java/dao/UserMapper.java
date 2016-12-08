package dao;

import model.NodeInfo;
import model.User;
import model.UserShareInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by rainy on 2016/11/30.
 */

public interface UserMapper {

    @Insert(value = "insert into user_login(name,password,email,role) values(#{username},#{password},#{email},#{role})")
    void saveUser(@Param(value = "username") String username,
                  @Param(value = "password") String password,
                  @Param(value = "email") String email,
                  @Param(value = "role") String role);
    @Insert(value = "insert into user_share_info(username,url,type,password,identity,filesize,modifytime,filekinds,filename) values(#{username},#{url},#{type},#{password},#{identity},#{filesize},#{modifytime},#{filekinds},#{filename})")
    void saveShareInfo(@Param(value = "username") String username,
                       @Param(value = "url") String url,
                       @Param(value = "type") String type,
                       @Param(value = "password") String password,
                       @Param(value = "identity") String identity,
                       @Param(value = "filesize") String filesize,
                       @Param(value = "modifytime") String modifytime,
                       @Param(value = "fileKinds") String filekinds,
                       @Param(value = "filename") String filename);

    @Select("select * from user_login where name = #{username}")
    List<User> getUserByName(String username);

    @Select("select * from user_share_info where identify = #{identify}")
    List<UserShareInfo> getSharePassword(String identify);

    @Select("select * from user_login where id = #{id}")
    User getUserById(Integer id);

    @Select("select * from node_info")
    List<NodeInfo> findAllNode();
}
