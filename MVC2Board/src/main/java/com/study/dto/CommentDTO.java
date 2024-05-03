package com.study.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
public class CommentDTO {
    private int seq;
    private String content;
    private Date createDate;
    private int boardSeq;


}