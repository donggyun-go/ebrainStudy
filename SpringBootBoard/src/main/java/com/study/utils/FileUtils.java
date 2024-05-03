package com.study.utils;

import com.study.controller.BoardController;
import com.study.dto.BoardDTO;
import com.study.dto.FileDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 파일에 관한 CRUD utils
 */
@Component("fileUtils")
public class FileUtils {
    private static final Logger logger = LogManager.getLogger(BoardController.class);
    private static final String filePath = "C:\\Users\\Godong\\img\\";

    public List<Map<String, Object>> parseInsertFileInfo(BoardDTO boardDto,
                                                         MultipartHttpServletRequest mpRequest) throws Exception {

        List<MultipartFile> fileList = mpRequest.getFiles("file[]");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        int boardSeq = boardDto.getSeq();
        Date regDate = boardDto.getCreateDate();

        File file = new File(filePath);
        if (file.exists() == false) {
            file.mkdirs();
        }

        for (MultipartFile multipartFile : fileList) {
            if (!multipartFile.isEmpty()) {
                String originalFileName = multipartFile.getOriginalFilename();
                String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String storedFileName = getRandomString() + originalFileExtension;

                file = new File(filePath + storedFileName);
                multipartFile.transferTo(file);
                Map<String, Object> listMap = new HashMap<String, Object>();
                listMap.put("realName", originalFileName);
                listMap.put("saveName", storedFileName);
                listMap.put("createDate", regDate);
                listMap.put("savePath", filePath);
                listMap.put("boardSeq", boardSeq);
                list.add(listMap);
            }
        }

        return list;
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String getFilePath() {
        return filePath;
    }

    public void deleteFile(String storedFileName) {
        String fullPath = filePath + storedFileName;
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }
    }
}
