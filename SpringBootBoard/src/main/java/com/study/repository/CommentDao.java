package com.study.repository;

import com.study.dto.CommentDTO;

import java.util.List;

public interface CommentDao {
    List<CommentDTO> getCommentsByBoardSeq(int seq);

    void createComment(CommentDTO comment);
}
