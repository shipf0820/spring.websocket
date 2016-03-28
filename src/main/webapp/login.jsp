<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <script src="js/jquery.js"></script>
</head>

<body>
    <form name="loginForm" id="loginForm" action="index.jsp" method="post"  ">
        用户名:<input type="text" id="username" name="username"/><br/>
        密码:<input type="text" id="password" name="password"/><br/>
       <input type="button" onclick="check()" value="提交"/>
       <input type="reset" value="重置"/>
    </form>
    
    <script type="text/javascript"> 
        function check()
		{
        	$.ajax({
                url: "${pageContext.request.contextPath}/msLogin",
                type: "post",
                async: false, 
                data: {username: $("input[name='username']").val(), password: $("input[name='password']").val()},
                dataType: "json",
                complete:function(data){
                	if (data.responseText == 'fail') {
                        $('#msg').html('<font color="red" >用户名或密码错误</font>');
                    }else if(data.responseText == 'succ'){
                    	if($("input[name='username']").val() == 'test'){
                    		location.href="index.jsp?userId="+$("input[name='username']").val();
                    	}else{
                    		location.href="index.jsp";
                    	}
                        

                    }else{
                        $('#msg').html('<font color="red">系统错误请稍后重试</font>');
                    }
                }
            })
			//验证不通过时
			return;
		} 
    </script>
</body>

</html>
