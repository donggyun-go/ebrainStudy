package com.study.service;

import com.study.controller.BoardController;
import com.study.dto.FileDTO;
import com.study.repository.FileDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService{

    private static final Logger logger = LogManager.getLogger(BoardController.class);

    @Autowired
    private FileDao fileDao;

    /**
     * 파일 저장 메서드
     * 파일리스트를 받아와 각각 파일을 insertFile메서드 실행
     */
    @Override
    public void saveFileList(List<Map<String, Object>> fileList) {
        for (Map<String, Object> fileMap : fileList) {
            logger.info("Saving file: {}", fileMap);
            fileDao.insertFile(fileMap);
        }
    }

    /**
     * seq값에 해당하는 파일리스트를 가져온다.
     */
    @Override
    public List<Map<String, Object>> getFilesByBoardSeq(int boardSeq) {
        return fileDao.findFilesByBoardSeq(boardSeq);
    }
}
