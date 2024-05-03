package com.study.service;

import com.study.dto.FileDTO;

import java.util.List;
import java.util.Map;

public interface FileService {
    void saveFileList(List<Map<String, Object>> fileList);
    List<Map<String, Object>> getFilesByBoardSeq(int boardSeq);
}
