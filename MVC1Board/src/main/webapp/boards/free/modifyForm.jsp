<%@ page import="com.study.connection.boardDTO" %>
<%@ page import="com.study.connection.boardDAO" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int seq = Integer.parseInt(request.getParameter("seq"));
    String password = request.getParameter("password");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String name = request.getParameter("name");

    boardDAO dao = new boardDAO();
    boardDTO board = dao.getBoardBySeq(seq);

    System.out.println(password + board.getPassword() + title + content + name);
    System.out.println(board.toString());
    // 비밀번호 일치 검사
    if (!board.getPassword().equals(password)) {
        System.out.println("not equal password");
        // 추가적인 에러 처리 또는 리디렉션
    } else {
        board.setName(name);
        board.setTitle(title);
        board.setContent(content);
        board.setUpdateDate(new Date());

        boolean isUpdated = dao.updateBoard(board);
        if (isUpdated) {
            response.sendRedirect("view.jsp?seq=" + seq);
        } else {
            System.out.println("can't update");
        }
    }
%>