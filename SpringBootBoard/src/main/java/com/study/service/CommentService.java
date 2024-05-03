package com.study.service;

import com.study.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentsByBoardSeq(int seq);
    void createComment(CommentDTO comment);
}
