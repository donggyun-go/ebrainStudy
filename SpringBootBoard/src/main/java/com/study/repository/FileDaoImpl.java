package com.study.repository;

import com.study.dto.FileDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FileDaoImpl implements FileDao {

    @Autowired
    private SqlSession sql;

    private static final String NAMESPACE = "com.study.repository.FileDao";

    public void insertFile(Map<String, Object> fileMap) {
        sql.insert(NAMESPACE + ".insertFile", fileMap);
    }

    @Override
    public List<Map<String, Object>> findFilesByBoardSeq(int boardSeq) {
        return sql.selectList(NAMESPACE + ".findFilesByBoardSeq", boardSeq);
    }

}
