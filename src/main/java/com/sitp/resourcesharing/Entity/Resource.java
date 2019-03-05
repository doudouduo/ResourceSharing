package com.sitp.resourcesharing.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.util.Date;

@Document(collection = "Resource")
public class Resource {
    @Id
    private String _id;
    private String filepath;
    private String filename;
    private String author;
    private String domain;
    private String college;
    private String content;
    private Date create_date;
    private int download;
    private boolean onshelf;

    public Resource(){
        super();
        this.filepath="";
        this.filename="";
        this.author="";
        this.domain="";
        this.college="";
        this.content="";
        this.create_date=new Date();
        this.download=0;
        onshelf=true;
    }

    public Resource(String filepath,String filename,String author, String domain,String college,String content) {
        super();
        this.filepath=filepath;
        this.filename=filename;
        this.author=author;
        this.domain=domain;
        this.college=college;
        this.content=content;
        this.create_date=new Date();
        this.download=0;
        onshelf=true;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public boolean isOnshelf() {
        return onshelf;
    }

    public void setOnshelf(boolean onshelf) {
        this.onshelf = onshelf;
    }
}
