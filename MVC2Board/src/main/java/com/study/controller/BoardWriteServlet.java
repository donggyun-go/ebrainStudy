package com.study.controller;

import com.study.dto.BoardDTO;
import com.study.repository.BoardDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 게시글 작성(wirte.jsp) 컨트롤러
 * get방식으로 write.jsp를 보여주고
 * post방식으로 form의 value값을 받아와 board객체에 parameter들을 setter를 이용하여
 * board객체를 매개변수로 board.addBoard메서드를 실행
 * 성공시 list.jsp로 리다이렉트, 실패시 error 처리
 * TODO: 에러처리에 대한 방법을 생각해야함
 */
@WebServlet("/boards/free/write")
public class BoardWriteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/boards/free/write.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String categorySeq = request.getParameter("categorySeq");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        BoardDTO board = new BoardDTO();
        board.setCategorySeq(categorySeq);
        board.setName(name);
        board.setPassword(password);
        board.setTitle(title);
        board.setContent(content);

        BoardDAO dao = new BoardDAO();
        boolean isAdded = dao.addBoard(board);

        if (isAdded) {
            response.sendRedirect("/boards/free/list");
        } else {
            response.sendRedirect("/boards/free/write?error=true");
        }
    }
}
