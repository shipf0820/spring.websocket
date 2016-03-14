package spring.websocket.repository.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import spf.websocket.beans.WebUser;
import spf.websocket.mybitis.UserMapper;
import spf.websocket.utils.JsonUtil;

public class UserMapperTest extends BaseTest{
	
	@Autowired
	private UserMapper userDto;
	@Test
	public void selectUserInfoTest(){
		WebUser user = userDto.selectUserInfo("100000");
		System.out.println(JsonUtil.toString(user));
	}
	
	

}
