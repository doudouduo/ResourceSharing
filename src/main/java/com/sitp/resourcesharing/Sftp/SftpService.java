package com.sitp.resourcesharing.Sftp;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.stereotype.Component;

@Component
public interface SftpService {
    public ChannelSftp createChannel(SftpAuthority sftpAuthority);
    public void closeChannel();
    public boolean uploadFile(ChannelSftp channelSftp,SftpAuthority sftpAuthority, String src, String dst);
    public boolean removeFile(SftpAuthority sftpAuthority, String dst);
}
