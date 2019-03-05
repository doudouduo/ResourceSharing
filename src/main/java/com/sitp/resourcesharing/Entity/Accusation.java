package com.sitp.resourcesharing.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Accusation {
    @Id
    @GeneratedValue
    private String accusation_id;
    private String reporter_id;
    private String resource_id;
    private String content;
    private boolean status;
    private Date create_date;


    public String getAccusation_id() {
        return accusation_id;
    }

    public void setAccusation_id(String accusation_id) {
        this.accusation_id = accusation_id;
    }

    public String getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(String reporter_id) {
        this.reporter_id = reporter_id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Accusation(String resource_id, String content, String author){
        this.resource_id=resource_id;
        this.content=content;
        this.reporter_id=author;
        this.create_date=new Date();
    }

    public Accusation(){}
}