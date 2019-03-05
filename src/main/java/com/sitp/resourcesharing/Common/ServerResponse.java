package com.sitp.resourcesharing.Common;

import javax.servlet.http.Cookie;
import java.io.Serializable;

public class ServerResponse {

    private int status;
    private String msg;

    private ServerResponse(int status){
        this.status = status;
    }

    public ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    public ServerResponse(int status,String msg,Cookie cookie){
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public static ServerResponse createBySuccess(){
        return new ServerResponse(ResponseCode.SUCCESS.getCode());
    }

    public static ServerResponse createBySuccessMessage(String msg,Cookie cookie){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg,cookie);
    }

    public static ServerResponse createByError(){
        return new ServerResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }


    public static ServerResponse createByErrorMessage(String errorMessage,Cookie cookie){
        return new ServerResponse(ResponseCode.ERROR.getCode(),errorMessage,cookie);
    }

    public static ServerResponse createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse(errorCode,errorMessage);
    }

    public static ServerResponse createByHackerMessage(String errorMessage){
        return new ServerResponse(ResponseCode.HACKER.getCode(),errorMessage);
    }

    @Override
    public String toString(){
        return "{"+
                "status:"+Integer.toString(status)+
//                ",cookie:"+cookie+
                ",message:'"+msg+'\''+'}';
    }
}
