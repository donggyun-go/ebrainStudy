package com.study.connection;

import java.util.Date;

public class boardDTO {
    private int seq;
    private String name;
    private String password;
    private String categorySeq;
    private String title;
    private String content;
    private Date createDate;
    private Date updateDate;
    private int viewCnt;
    private String categoryType;

    public boardDTO() {
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategorySeq() {
        return categorySeq;
    }

    public void setCategorySeq(String categorySeq) {
        this.categorySeq = categorySeq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    @Override
    public String toString() {
        return "boardDTO{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", categorySeq='" + categorySeq + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", viewCnt=" + viewCnt +
                '}';
    }
}

