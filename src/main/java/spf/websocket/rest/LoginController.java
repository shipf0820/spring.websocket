package spf.websocket.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import spf.websocket.config.Constants;
import spf.websocket.service.LoginService;

@RestController
public class LoginController {
	static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired(required = false)
	private LoginService loginService;
	
    
    /**
     * 验证登录
     * @param request
     * @returnLoginServiceImpl.java
     */
    @RequestMapping("/msLogin")
    @ResponseBody
    public String login(HttpServletRequest request,HttpServletResponse response){
        String result = "";
        String name = request.getParameter("username");
        String passwd = request.getParameter("password");
        logger.debug("name:"+name);
        logger.debug("passwd:"+passwd);
        result = loginService.checkLogin(name,passwd);
        if(result.equals("succ")){
            //response.addCookie(new Cookie("JSSESIONID",request.getSession().getId()));
            HttpSession session = request.getSession();
            session.setAttribute(Constants.WEBSOCKET_SESSION_NANE,name);
            System.out.println(session.getId());
        }
        return result;
    }
    
    @RequestMapping("/get/users")
    @ResponseBody
    public List<String> queryUsers(){
    	return loginService.getUsers();
    }
    
    /**
     * 验证登录
     * @param request
     * @return
     */
/*    @RequestMapping("/checkLogin")
    @ResponseBody
    public String checkLogin(HttpServletRequest request){
    	String name = request.getParameter("userName");
    	HttpSession session = request.getSession();
    	if(StringUtils.isNotEmpty(name)&&name.equals(session.getAttribute(SystemConstant.SESSION_NANE))){
    		return "true";
    	}  	
        return "false";
    }*/
}
