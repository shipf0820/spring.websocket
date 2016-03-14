package spf.websocket.mybitis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import spf.websocket.beans.WebUser;

@Repository
public interface UserMapper {


    void insertUser(WebUser user);
    
    WebUser selectUserInfo(@Param("userId")  String userId);
    
    List<String> queryUsers();

}
