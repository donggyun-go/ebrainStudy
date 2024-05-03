package com.study.repository;

import com.study.dto.CommentDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    private SqlSession sql;
    private static String namespace = "com.study.repository.CommentDao";


    @Override
    public List<CommentDTO> getCommentsByBoardSeq(int seq) {
        return sql.selectList(namespace + ".getCommentsByBoardSeq", seq);
    }

    @Override
    public void createComment(CommentDTO comment) {
        sql.insert(namespace + ".createComment", comment);
    }
}
