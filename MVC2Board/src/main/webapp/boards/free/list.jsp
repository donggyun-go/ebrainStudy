<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>자유 게시판 - 목록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 8px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        td:nth-child(2) {
            text-align: left;
        }

        .pagination {
            text-align: center;
            margin-top: 20px;
        }

        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: black;
        }

        .pagination a.active {
            font-weight: bold;
        }

        .search-form {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 20px;
        }

        .search-form input[type="date"],
        .search-form select,
        .search-form input[type="text"] {
            margin-right: 10px;
        }

        .register-button {
            position: fixed;
            bottom: 20px;
            right: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }

        .register-button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<h1>자유 게시판 - 목록</h1>

<form action="/boards/free/list" method="get">
    <div class="search-form">
        등록일
        <input type="date" name="startDate"/>
        ~
        <input type="date" name="endDate"/>
        <select name="searchType">
            <option value="all" selected>전체 카테고리</option>
            <option value="title">제목</option>
            <option value="name">작성자</option>
            <option value="content">내용</option>
        </select>
        <input type="text" name="keyword"/>
        <input type="submit" value="검색"/>
    </div>
</form>
<br/>

<table>
    <tr>
        <th>카테고리</th>
        <th>제목</th>
        <th>작성자</th>
        <th>조회수</th>
        <th>등록일시</th>
        <th>수정일시</th>
    </tr>
    <c:forEach var="board" items="${boardList}">
        <tr>
            <td>${board.categoryType}</td>
            <td><a href="/boards/free/detail?seq=${board.seq}">${board.title}</a></td>
            <td>${board.name}</td>
            <td>${board.viewCnt}</td>
            <td><fmt:formatDate value="${board.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
                <c:if test="${board.updateDate != null}">
                    <fmt:formatDate value="${board.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </c:if>
                <c:if test="${board.updateDate == null}">
                    -
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<br/>

<div class="pagination">
    <c:if test="${paging.prev}">
        <a href="${pageContext.request.contextPath}/boards/free/list?page=${paging.startPage-1}&searchType=${param.searchType}&keyword=${param.keyword}">&laquo;</a>
    </c:if>
    <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="pageNum">
        <a href="${pageContext.request.contextPath}/boards/free/list?page=${pageNum}&searchType=${param.searchType}&keyword=${param.keyword}" class="${pageNum == paging.currentPage ? 'active' : ''}">${pageNum}</a>
    </c:forEach>
    <c:if test="${paging.next}">
        <a href="${pageContext.request.contextPath}/boards/free/list?page=${paging.endPage+1}&searchType=${param.searchType}&keyword=${param.keyword}">&raquo;</a>
    </c:if>
</div>
<button class="register-button" onclick="location.href='write'">글 등록</button>
</body>
</html>
