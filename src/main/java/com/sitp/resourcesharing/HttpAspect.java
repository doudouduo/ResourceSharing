package com.sitp.resourcesharing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sitp.resourcesharing.Common.ServerResponse;
import com.sitp.resourcesharing.Util.CookieUtil;
import net.sf.json.JSONString;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Aspect
@Component
public class HttpAspect {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    CookieUtil cookieUtil;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Pointcut("execution(public * com.sitp.resourcesharing.Controller.*.*(..))")//定义切面
    public void log1(){//定义公用注解log

    }
    @Around("log1()")
    public String LoginCheck(ProceedingJoinPoint pjp)throws Throwable{
        Object result=null;
        ServerResponse serverResponse=null;
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//获取请求特征
        HttpServletRequest request=attributes.getRequest();//获取请求内容

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod(); //获取被拦截的方法
        String methodName = method.getName(); //获取被拦截的方法名

        String user_id=request.getHeader("user_id");
        Cookie[] cookies=request.getCookies();
        Cookie cookie=null;
        if (cookies!=null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(user_id)) {
                    cookie=c;
                    break;
                }
            }
        }

        if(user_id==null){
            logger.info("You should log in first.");
            return  ServerResponse.createByErrorMessage("You should log in first.",cookie).toString();
        }
        Object value=redisTemplate.opsForValue().get(user_id);
        if (value!=null){
            if (!cookie.getValue().equals(value)){
                logger.info("hacker!!!");
                redisTemplate.opsForValue().getOperations().delete(String.format(user_id,value));
                return ServerResponse.createByHackerMessage("hacker!!!").toString();
            }
            else {
                logger.info("Hello, online judge.");
                try{
                    result=pjp.proceed();
                    JSON json=(JSON)JSONObject.parse((String)result);
                    serverResponse= JSONObject.toJavaObject(json,ServerResponse.class);
                    serverResponse=CookieUpdate(serverResponse);
                }catch (Throwable e) {
                    e.printStackTrace();
                }
                return JSONObject.toJSONString(serverResponse);
            }
            //return null;
        }
        else {
            logger.info("You should log in first.");
//            return "You should log in first.";
            return ServerResponse.createByErrorMessage("You should log in first.",cookie).toString();
        }
    }

    @Pointcut("execution(public * com.sitp.resourcesharing.Controller.*.*(..))")//定义切面
    public void log2(){//定义公用注解log

    }

    @Pointcut("execution(public * com.sitp.resourcesharing.HttpAspect.log1())")
    public void log3(){

    }

//    @AfterReturning(returning="result",pointcut="log2()||log3()")
//    public String CookieUpdate(JoinPoint joinPoint,ServerResponse result){
    public ServerResponse CookieUpdate(ServerResponse result){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//获取请求特征
        HttpServletRequest request=attributes.getRequest();//获取请求内容
        HttpServletResponse response=attributes.getResponse();//获取请求内容

        if(!result.getMsg().equals("You should log in first.")) {
            String user_id = request.getHeader("user_id");
            Cookie cookie = cookieUtil.updateCookie(response, user_id);
            result = (ServerResponse) result;
//            result.setCookie(cookie);
        }
//        String s=result.toString();
        return result;
    }

}
