package com.study.connection;

import java.util.Date;

public class fileDTO {
    private int seq;
    private String realName;
    private String saveName;
    private String savePath;
    private Date createDate;
    private int boardSeq;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getBoardSeq() {
        return boardSeq;
    }

    public void setBoardSeq(int boardSeq) {
        this.boardSeq = boardSeq;
    }

    @Override
    public String toString() {
        return "fileDTO{" +
                "seq=" + seq +
                ", realName='" + realName + '\'' +
                ", saveName='" + saveName + '\'' +
                ", savePath='" + savePath + '\'' +
                ", createDate=" + createDate +
                ", boardSeq=" + boardSeq +
                '}';
    }
}
