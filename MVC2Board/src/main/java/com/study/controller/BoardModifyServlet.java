package com.study.controller;

import com.study.dto.BoardDTO;
import com.study.repository.BoardDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;


/**
 * 수정 기능 Servlet
 * Get으로 해당 Seq값에 게시글을 찾아와 detail.jsp에 값을 넘겨주고
 * Post로 수정 Form에서 받아온 파라미터 값으로 Board객체의 값을 변경해 DB에 저장
 */
@WebServlet("/boards/free/modify")
public class BoardModifyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int seq = Integer.parseInt(request.getParameter("seq"));
        BoardDAO dao = new BoardDAO();
        BoardDTO board = dao.getBoardBySeq(seq);

        if (board != null) {
            request.setAttribute("board", board);
            request.getRequestDispatcher("/boards/free/modify.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int seq = Integer.parseInt(request.getParameter("seq"));
        String password = request.getParameter("password");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String name = request.getParameter("name");

        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        String searchType = request.getParameter("searchType");
        String keyword = request.getParameter("keyword");

        BoardDAO dao = new BoardDAO();
        BoardDTO board = dao.getBoardBySeq(seq);

        if (board != null && board.getPassword().equals(password)) {
            board.setName(name);
            board.setTitle(title);
            board.setContent(content);
            board.setUpdateDate(new Date());

            boolean isUpdated = dao.updateBoard(board);
            if (isUpdated) {
                String redirectURL = "/boards/free/list?page=" + currentPage;
                if (searchType != null && !searchType.isEmpty() && keyword != null) {
                    redirectURL += "&searchType=" + URLEncoder.encode(searchType, "UTF-8") + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
                }
                response.sendRedirect(redirectURL);
            }
        }
    }
}
