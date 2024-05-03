package com.study.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BoardDTO {
    private int seq;
    private String name;
    private String password;
    private int categorySeq;
    private String title;
    private String content;
    private Date createDate;
    private Date updateDate;
    private int viewCnt;
    private String categoryType;

    public BoardDTO() {
    }
}