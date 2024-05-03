<%@ page import="com.study.connection.boardDAO" %>
<%@ page import="com.study.connection.boardDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>게시글 삭제</title>
    <script>
        function cancel() {
            var seq = '<%= request.getParameter("seq") %>';
            window.location.href = 'view.jsp?seq=' + seq;
        }
    </script>
</head>
<body>
<h2>게시글 삭제</h2>
<%
    int seq = Integer.parseInt(request.getParameter("seq"));
    String password = request.getParameter("password");

    boardDAO dao = new boardDAO();
    try {
        if (dao.deleteBoard(seq, password)) {
            response.sendRedirect("list.jsp");
        } else {
            out.print("<script>alert('비밀번호가 틀렸습니다.'); history.back();</script>");
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.print("<script>alert('오류가 발생했습니다. 다시 시도해주세요.'); history.back();</script>");
    }
%>
<form action="delete.jsp" method="get">
    <input type="hidden" name="seq" value="<%= request.getParameter("seq") %>">
    <input type="password" name="password" placeholder="비밀번호" required>
    <input type="button" value="취소" onclick="cancel()">
    <input type="submit" value="확인">
</form>
</body>
</html>
