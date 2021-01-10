package com.blog.api.model;


import com.blog.api.enums.ComemntModuleType;
import com.blog.api.enums.PublishState;
import com.blog.api.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.util.Date;


@Table
@Entity
public class Comment extends BaseModel {


    public ComemntModuleType getComemntModuleType() {
        return comemntModuleType;
    }

    public void setComemntModuleType(ComemntModuleType comemntModuleType) {
        this.comemntModuleType = comemntModuleType;
    }

    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(long quoteId) {
        this.quoteId = quoteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSysUserCreated() {
        return isSysUserCreated;
    }

    public void setSysUserCreated(boolean sysUserCreated) {
        isSysUserCreated = sysUserCreated;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public PublishState getPublishState() {
        return publishState;
    }

    public void setPublishState(PublishState publishState) {
        this.publishState = publishState;
    }

    public String getIp_location() {
        return ip_location;
    }

    public void setIp_location(String ip_location) {
        this.ip_location = ip_location;
    }

    //所属模块类型
    private ComemntModuleType comemntModuleType;

    //模块id
    private long moduleId ;

    //决定在哪一层
    private long parentId ;

    //决定是评论的回复 还是回复的回复
    private long quoteId ;

    private String content ;

    private String author ;

    private String email ;

    private boolean isSysUserCreated ;

    private long userId ;


    private String site ;

    private boolean isTop ;

    private boolean isDirty ;

    private boolean isPrivate ;

    private int likes ;

    private String ip ;

    private PublishState publishState ;

    private String ip_location ;


}
