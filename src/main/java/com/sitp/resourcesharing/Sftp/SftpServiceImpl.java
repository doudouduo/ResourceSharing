package com.sitp.resourcesharing.Sftp;

import org.springframework.stereotype.Service;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;


@Slf4j
@Service(value = "sftpService")
public class SftpServiceImpl implements SftpService {
    private Session session;
      private Channel channel;
//      private ChannelSftp channelSftp;
    @Override
     public ChannelSftp createChannel(SftpAuthority sftpAuthority) {
        ChannelSftp channelSftp=null;
        try {
            JSch jSch = new JSch();
            session = jSch.getSession(sftpAuthority.getUser(), sftpAuthority.getHost(), sftpAuthority.getPort());

            if (sftpAuthority.getPassword() != null) {
                // 使用用户名密码创建SSH
                session.setPassword(sftpAuthority.getPassword());
            } else if (sftpAuthority.getPrivateKey() != null) {
                // 使用公私钥创建SSH
                jSch.addIdentity(sftpAuthority.getPrivateKey(), sftpAuthority.getPassphrase());
            }

            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");  // 主动接收ECDSA key fingerprint，不进行HostKeyChecking
            session.setConfig(properties);
            session.setTimeout(0);  // 设置超时时间为无穷大
            session.connect(); // 通过session建立连接

            channel = session.openChannel("sftp"); // 打开SFTP通道
            channel.connect();
            while (!channel.isConnected())channel.connect();
            channelSftp = (ChannelSftp) channel;
//            System.out.println(channelSftp.isConnected());
        } catch (JSchException e) {
            System.out.println("create sftp channel failed!");
            System.out.println(e.getLocalizedMessage()+"\n"+e.getMessage());
        }
        return channelSftp;
    }

    @Override
    public void closeChannel() {
        if (channel != null) {
            channel.disconnect();
        }

        if (session != null) {
            session.disconnect();
        }
    }

    @Override
    public boolean uploadFile(ChannelSftp channelSftp,SftpAuthority sftpAuthority, String src, String dst) {
        if (channelSftp == null) {
            System.out.println("need create channelSftp before upload file");
            return false;
        }

        if (channelSftp.isClosed()) {
            channelSftp=createChannel(sftpAuthority); // 如果被关闭则应重新创建
        }

        try {
            System.out.println(channelSftp.isConnected());
            System.out.println(channelSftp.isClosed());
            channelSftp.put(src, dst, ChannelSftp.OVERWRITE);
            System.out.println("sftp upload file success! src: "+src+", dst: "+dst);
            return true;
        } catch (SftpException e) {
            System.out.println("sftp upload file failed! src: "+src+", dst: "+dst);
            return false;
        }
    }

    @Override
    public boolean removeFile(SftpAuthority sftpAuthority, String dst) {
//        if (channelSftp == null) {
//            System.out.println("need create channelSftp before remove file");
//            return false;
//        }
//
//        if (channelSftp.isClosed()) {
//            createChannel(sftpAuthority); // 如果被关闭则应重新创建
//        }
//
//        try {
//            channelSftp.rm(dst);
//            System.out.println("sftp remove file success! dst: {}");
//            return true;
//        } catch (SftpException e) {
//            System.out.println("sftp remove file failed! dst: {}");
//            return false;
//        }
        return false;
    }
}
