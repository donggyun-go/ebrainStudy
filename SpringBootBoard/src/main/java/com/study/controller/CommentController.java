package com.study.controller;

import com.study.dto.CommentDTO;
import com.study.service.CommentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board/free")
public class CommentController {

    private static final Logger logger = LogManager.getLogger(CommentController.class);
    private final CommentServiceImpl commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 작성 form의 파라미터들을 받아와 저장하는 메서드 실행
     * 해당 게시글 번호로 리다이렉트
     */
    @PostMapping("/comment")
    public String createComment(CommentDTO comment){
        commentService.createComment(comment);
        return "redirect:/board/free/" + comment.getBoardSeq();
    }

}
