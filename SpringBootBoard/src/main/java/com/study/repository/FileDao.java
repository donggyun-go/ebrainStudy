package com.study.repository;

import com.study.dto.FileDTO;

import java.util.List;
import java.util.Map;

public interface FileDao {
    void insertFile(Map<String, Object> fileMap);
    List<Map<String, Object>> findFilesByBoardSeq(int boardSeq);
}
