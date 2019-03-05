package com.sitp.resourcesharing.Controller;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.sitp.resourcesharing.Common.ServerResponse;
import com.sitp.resourcesharing.Constant.CookieConstant;
import com.sitp.resourcesharing.Entity.Accusation;
import com.sitp.resourcesharing.Entity.Comment;
import com.sitp.resourcesharing.Entity.Resource;
import com.sitp.resourcesharing.Repository.ResourceRepository;
import com.sitp.resourcesharing.Sftp.SftpAuthority;
import com.sitp.resourcesharing.Sftp.SftpService;
import com.sitp.resourcesharing.Util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ResourceController {

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private CommentController commentController;
    @Autowired
    private AccusationController accusationController;
    @Autowired
    private SftpService sftpService;
    private ChannelSftp channelSftp;

    @PostMapping(value = "/upload")
    public String uploadResource(){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//获取请求特征
        HttpServletRequest request=attributes.getRequest();//获取请求内容
        HttpServletResponse response=attributes.getResponse();//获取请求内容

        String user_id=request.getHeader("user_id");
        String uuid=request.getHeader("uuid");
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

        SftpAuthority sftpAuthority = new SftpAuthority("root", "129.204.200.27", 22);
        sftpAuthority.setPassword("root");
        channelSftp=sftpService.createChannel(sftpAuthority);
        String src="./USA_b1802.xml";
        String dst="USA_b1802.xml";
        boolean flag=sftpService.uploadFile(channelSftp,sftpAuthority, src, dst);
        sftpService.closeChannel();
        if (flag==true){
            Resource resource=new Resource();
            resource.setFilepath(dst);
            resource.setFilename(dst);
            resourceRepository.save(resource);
            return JSONObject.toJSONString(ServerResponse.createBySuccessMessage("upload successfully.",cookie));
        }
        else return JSONObject.toJSONString(ServerResponse.createByError());
    }

    @PostMapping(value="download")
    public void downloadResource() throws SftpException {
        SftpAuthority sftpAuthority = new SftpAuthority("root", "129.204.200.27", 22);
        sftpAuthority.setPassword("root");
        channelSftp=sftpService.createChannel(sftpAuthority);
        String src="图片1.jpg";
        String dst="/Users/mybook/Downloads/图片2.jpg";
        try {
            channelSftp.get(src, dst);
            Resource resource=resourceRepository.findResourceByFilepath(src);
            resource.setDownload(resource.getDownload()+1);
            resourceRepository.save(resource);
        }
        catch (SftpException e){
            System.out.println("Can't find this file!");
        }
    }

    public void removeResource(String id){
        resourceRepository.deleteById(id);
    }

    public boolean putResourceOnShelf(String id){
        Resource resource=resourceRepository.findResourceBy_id(id);
        if (resource==null)return false;
        else {
            if (resource.isOnshelf())return false;
            else {
                resource.setOnshelf(true);
                resourceRepository.save(resource);
                return true;
            }
        }
    }

    public boolean putResourceOffShelf(String id){
        Resource resource=resourceRepository.findResourceBy_id(id);
        if (resource==null)return false;
        else {
            if (!resource.isOnshelf())return false;
            else {
                resource.setOnshelf(false);
                resourceRepository.save(resource);
                return true;
            }
        }
    }

    public Comment makeComment(String resource_id, String content, String user_id){
        return commentController.makeComment(resource_id,content,user_id);
    }

    public Accusation accuseResource(String resource_id, String content, String user_id){
        return accusationController.makeAccusation(resource_id,content,user_id);
    }

}
