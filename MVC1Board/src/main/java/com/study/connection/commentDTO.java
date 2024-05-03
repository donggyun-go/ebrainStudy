package com.study.connection;

import java.util.Date;

public class commentDTO {
    private int seq;
    private String content;
    private Date createDate;
    private int boardSeq;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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

    public int getBoardSeq() {
        return boardSeq;
    }

    public void setBoardSeq(int boardSeq) {
        this.boardSeq = boardSeq;
    }

    @Override
    public String toString() {
        return "commentDTO{" +
                "seq=" + seq +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", boardSeq=" + boardSeq +
                '}';
    }
}