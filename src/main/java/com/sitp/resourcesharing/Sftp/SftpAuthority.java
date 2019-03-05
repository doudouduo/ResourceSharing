package com.sitp.resourcesharing.Sftp;

import lombok.Data;

@Data
public class SftpAuthority {
    private String host;         // 服务器ip或者主机名
      private int port;            // sftp端口
      private String user;         // sftp使用的用户
     private String password;     // 账户密码
     private String privateKey;   // 私钥文件名
     private String passphrase;   // 私钥密钥

     public SftpAuthority(String user, String host, int port) {
         this.host = host;
         this.port = port;
         this.user = user;
     }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }
}
