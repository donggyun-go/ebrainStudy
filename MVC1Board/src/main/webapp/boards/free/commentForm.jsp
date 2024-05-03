<%@ page import="com.study.connection.commentDTO" %>
<%@ page import="com.study.connection.commentDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String content = request.getParameter("commentContent");
  int boardSeq = Integer.parseInt(request.getParameter("boardSeq"));

  System.out.println(boardSeq + content);

  commentDTO comment = new commentDTO();
  comment.setContent(content);
  comment.setBoardSeq(boardSeq);
  commentDAO dao = new commentDAO();
  boolean isSaved = dao.addComment(comment);

  if (isSaved) {
    response.sendRedirect("/boards/free/view.jsp?seq="+boardSeq);
  } else {
    System.out.println("<p>댓글 추가에 실패했습니다.</p>");
  }
%>