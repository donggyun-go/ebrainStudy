package com.study.controller;

import com.study.dto.BoardDTO;
import com.study.dto.PagingDTO;
import com.study.repository.BoardDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 게시글 리스트를 보여주고 검색과 페이징요청을 처리하는 Servlet
 * TODO: Servlet구성을 이렇게 하는게 맞는지, 예외처리에 대한 고민
 */
@WebServlet("/boards/free/list")
public class BoardListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int currentPage = 1;
        String currentPageStr = request.getParameter("page");

        if (currentPageStr != null && !currentPageStr.isEmpty()) {
            currentPage = Integer.parseInt(currentPageStr);
        }

        String searchType = request.getParameter("searchType");
        String keyword = request.getParameter("keyword");

        BoardDAO boardDAO = new BoardDAO();

        BoardDTO search = new BoardDTO();
        search.setSearchType(searchType);
        search.setKeyword(keyword);

        int totalPosts = boardDAO.getTotalPosts(search);
        PagingDTO paging = new PagingDTO();
        paging.setCurrentPage(currentPage);
        paging.setTotalPosts(totalPosts);
        paging.calculatePaging();

        List<BoardDTO> boardList = boardDAO.getBoardsByPageAndSearch(currentPage, paging.getPostsPerPage(), search);

        request.setAttribute("boardList", boardList);
        request.setAttribute("paging", paging);
        request.setAttribute("searchType", searchType);
        request.setAttribute("keyword", keyword);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/boards/free/list.jsp");
        dispatcher.forward(request, response);
    }
}
