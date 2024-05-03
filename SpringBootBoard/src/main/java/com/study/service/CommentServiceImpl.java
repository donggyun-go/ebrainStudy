package com.study.service;

import com.study.dto.CommentDTO;
import com.study.repository.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
    @Override
    public List<CommentDTO> getCommentsByBoardSeq(int seq) {
        return commentDao.getCommentsByBoardSeq(seq);
    }

    @Override
    public void createComment(CommentDTO comment) {
        commentDao.createComment(comment);
    }
}
