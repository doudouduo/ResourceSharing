package com.sitp.resourcesharing.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(DownloadPK.class)
public class Download {
    @Id
    @Column(length = 20)
    private String user_id;
    @Id
    @Column(length = 50)
    private String resource_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public Download(){

    }
}
