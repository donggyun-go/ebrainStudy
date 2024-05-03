<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>게시글 삭제</title>
    <script>
        function cancel() {
            var seq = '${param.seq}';
            window.location.href = 'detail?seq=' + seq;
        }
    </script>
</head>
<body>
<h2>게시글 삭제</h2>
<form action="/boards/free/delete" method="post">
    <input type="hidden" name="currentPage" value="${param.page}">
    <input type="hidden" name="searchType" value="${param.searchType}">
    <input type="hidden" name="keyword" value="${param.keyword}">
    <input type="hidden" name="seq" value="${param.seq}">
    <input type="password" name="password" placeholder="비밀번호" required>
    <input type="button" value="취소" onclick="cancel()">
    <input type="submit" value="확인">
</form>
</body>
</html>
