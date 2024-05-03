package com.study.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
public class BoardDTO {
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

    private String searchType;
    private String keyword;

    public BoardDTO() {
    }

}

