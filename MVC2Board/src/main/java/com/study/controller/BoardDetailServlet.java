package com.study.controller;

import com.study.dto.BoardDTO;
import com.study.dto.CommentDTO;
import com.study.repository.BoardDAO;
import com.study.repository.CommentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Get으로 게시글의 Seq값에 해당하는 글, 댓글을 불러오고 조회수 증가
 * Post로 댓글을 등록하게되면 게시글 Seq값에 해당하는 댓글을 DB에 저장
 */
@WebServlet("/boards/free/detail")
public class BoardDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String seqParam = request.getParameter("seq");
        int seq = Integer.parseInt(seqParam);
        BoardDAO boardDao = new BoardDAO();
        CommentDAO commentDao = new CommentDAO();

        boardDao.increaseViewCnt(seq);
        BoardDTO board = boardDao.getBoardBySeq(seq);
        List<CommentDTO> comments = commentDao.getCommentsByBoardSeq(seq);

        request.setAttribute("board", board);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("/boards/free/view.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int boardSeq = Integer.parseInt(request.getParameter("boardSeq"));
        String content = request.getParameter("commentContent");

        CommentDTO comment = new CommentDTO();
        comment.setBoardSeq(boardSeq);
        comment.setContent(content);

        CommentDAO commentDao = new CommentDAO();
        boolean isSaved = commentDao.addComment(comment);

        if (isSaved) {
            response.sendRedirect("/boards/free/detail?seq=" + boardSeq);
        }
    }
}
