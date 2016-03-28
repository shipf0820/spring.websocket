package spf.websocket.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import spf.websocket.beans.WebUser;
import spf.websocket.mybitis.UserMapper;

@Service
public class LoginService{
	
	static Logger logger = LoggerFactory.getLogger(LoginService.class);
	
    @Autowired
    private UserMapper userMapper;

    /**
     * 验证登录
     * @param username
     * @param passwd
     * @return
     */
    public String checkLogin(String userId,String passwd){
        String result = "fail";
        Assert.notNull(userId, "用户不能为空");
        Assert.notNull(passwd, "用户密码不能为空");
        WebUser user = userMapper.selectUserInfo(userId);
        if(passwd.equals(user.getPasswd())){
        	 result = "succ";
        }
        return result;
    }
    
    public List<String> getUsers(){
    	return  userMapper.queryUsers();
    }

}
