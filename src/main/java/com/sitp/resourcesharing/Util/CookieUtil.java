package com.sitp.resourcesharing.Util;

import com.sitp.resourcesharing.Constant.CookieConstant;
import com.sitp.resourcesharing.Constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class CookieUtil {
    @Autowired
    RedisTemplate redisTemplate;

    public static Cookie set(HttpServletResponse response, String name, String value, int maxAge)
    {
        Cookie cookie=new Cookie(name,value);//设置cookie的key和value的值
        cookie.setPath("/");//路径
        cookie.setMaxAge(maxAge);//设置过期时间
        response.addCookie(cookie);//添加cookie
        System.out.println(cookie);
        return cookie;
    }

    public static Cookie get(HttpServletRequest request, String name){
        Map<String,Cookie> cookieMap=readCookieMap(request);
        if (cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }
        else {
            return null;
        }
    }

    public static Map<String,Cookie>readCookieMap(HttpServletRequest request){
        Map<String,Cookie>cookieMap=new HashMap<>();
        Cookie[] cookies=request.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }

    public Cookie updateCookie(HttpServletResponse response,String user_id){
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(user_id,uuid, RedisConstant.EXPIRE, TimeUnit.SECONDS);
        return CookieUtil.set(response,user_id,uuid, CookieConstant.EXPIRE);
    }
}
