package com.sitp.resourcesharing.Entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DownloadPK implements Serializable {
    private String user_id;
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

    public DownloadPK(String user_id,String resource_id){
        this.user_id=user_id;
        this.resource_id=resource_id;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((user_id == null) ? 0 : user_id.hashCode());
        result = PRIME * result + ((resource_id == null) ? 0 : resource_id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }

        final DownloadPK other = (DownloadPK) obj;
        if(user_id == null){
            if(other.user_id != null){
                return false;
            }
        }else if(!user_id.equals(other.user_id)){
            return false;
        }
        if(resource_id == null){
            if(other.resource_id != null){
                return false;
            }
        }else if(!resource_id.equals(other.resource_id)){
            return false;
        }
        return true;
    }

    DownloadPK(){}
}
