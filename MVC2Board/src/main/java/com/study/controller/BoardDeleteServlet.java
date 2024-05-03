package com.study.controller;

import com.study.repository.BoardDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/boards/free/delete")
public class BoardDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String seq = request.getParameter("seq");
        if (seq != null && !seq.isEmpty()) {
            request.getRequestDispatcher("/boards/free/delete.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int seq = Integer.parseInt(request.getParameter("seq"));
        String password = request.getParameter("password");
        String currentPage = request.getParameter("currentPage");
        String searchType = request.getParameter("searchType");
        String keyword = request.getParameter("keyword");

        BoardDAO dao = new BoardDAO();
        if (dao.deleteBoard(seq, password)) {
            String redirectUrl = "list?page=" + currentPage;
            if (searchType != null && keyword != null) {
                redirectUrl += "&searchType=" + searchType + "&keyword=" + java.net.URLEncoder.encode(keyword, "UTF-8");
            }
            response.sendRedirect(redirectUrl);
        }
    }
}