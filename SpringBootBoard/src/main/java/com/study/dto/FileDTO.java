package com.study.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
public class FileDTO {
    private int seq;
    private String realName;
    private String saveName;
    private String savePath;
    private Date createDate;
    private int boardSeq;


}
