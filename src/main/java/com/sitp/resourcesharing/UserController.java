package com.sitp.resourcesharing;

import com.sitp.resourcesharing.Common.ServerResponse;
import com.sitp.resourcesharing.Constant.CookieConstant;
import com.sitp.resourcesharing.Constant.RedisConstant;
import com.sitp.resourcesharing.Entity.User;
import com.sitp.resourcesharing.Repository.ResourceRepository;
import com.sitp.resourcesharing.Repository.UserRepository;
import com.sitp.resourcesharing.Util.CookieUtil;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@RestController
public class UserController {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public ResourceRepository resourceRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CookieUtil cookieUtil;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value="/login")
    public String login(@RequestBody JSONObject jsonObject,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        String login = jsonObject.getAsString("login");
        String password= jsonObject.getAsString("password");
        String cookie_string=jsonObject.getAsString("cookie");
        System.out.println(cookie_string);

        //判断是否已经登录
        Object value=redisTemplate.opsForValue().get(login);
        if (value!=null){
            if (!cookie_string.contains(value.toString())){
                logger.info("hacker!!!");
                redisTemplate.opsForValue().getOperations().delete(String.format(login,value));
                return ServerResponse.createByHackerMessage("hacker!!!").toString();
            }
            logger.info("You have already logged in.");
            Cookie cookie=cookieUtil.updateCookie(response,login);
            return ServerResponse.createByErrorMessage("You have already logged in.",cookie).toString();
        }

        //登录操作
        User user = userRepository.getOne(login);
        if (user == null) {
            logger.info(login + " is invalid");
            return ServerResponse.createByErrorMessage(login + " is invalid",null).toString();

        }
        else if (!user.getPassword().equals(password)) {
            logger.info("Wrong password");
            return ServerResponse.createByErrorMessage("Wrong password",null).toString();
        }
        else {
            logger.info(login+" log in successfully.");
            //生成uuid存放在cookie
            String uuid =UUID.randomUUID().toString();
            //添加保存cookie
            System.out.println(login+" login successfully.");
            //设置有失效时间的redis
            redisTemplate.opsForValue().set(login,uuid, RedisConstant.EXPIRE, TimeUnit.SECONDS);
            //添加保存cookie
            return ServerResponse.createBySuccessMessage(login+" login successfully.",cookieUtil.set(response, login,uuid, CookieConstant.EXPIRE)).toString();

        }
    }

    public void logout(@RequestBody JSONObject jsonObject,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map){
        String login = jsonObject.getAsString("login");
        Object value=redisTemplate.opsForValue().get(login);
        redisTemplate.opsForValue().getOperations().delete(String.format(login,value));
        System.out.println(login+" log out successfully.");
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, login);
        if (cookie != null) {
            //2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(cookie.getName(),cookie.getValue()));

            //3. 清除cookie
            cookieUtil.set(response, cookie.getName(),cookie.getValue(),0);
        }
    }
}
