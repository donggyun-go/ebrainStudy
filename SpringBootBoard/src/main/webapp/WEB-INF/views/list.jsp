<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../static/list.css">
    <title>자유 게시판 - 목록</title>
</head>
<body>

<h1>자유 게시판 - 목록</h1>

<form action="/board/free/list" method="get">
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
    <input type="hidden" name="page" value="${paging.currentPage}"/>
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
    <c:forEach var="board" items="${boards}">
        <tr>
            <td>${board.categoryType}</td>
            <td><a href="/board/free/${board.seq}">${board.title}</a></td>
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
    <!-- 이전 페이지 링크 -->
    <c:if test="${paging.prev}">
        <a href="<c:url value='/board/free/list'>
            <c:param name='page' value='${paging.currentPage - 1}'/>
            <c:param name="startDate" value="${paging.startDate}"/>
            <c:param name="endDate" value="${paging.endDate}"/>
            <c:param name="searchType" value="${paging.searchType}"/>
            <c:param name="keyword" value="${paging.keyword}"/>
        </c:url>">&lt;</a>
    </c:if>

    <!-- 페이지 번호 링크 -->
    <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="pageNum">
        <c:choose>
            <c:when test="${pageNum == paging.currentPage}">
                <span class="current">${pageNum}</span>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/board/free/list'>
                    <c:param name='page' value='${pageNum}'/>
                    <c:param name="startDate" value="${paging.startDate}"/>
                    <c:param name="endDate" value="${paging.endDate}"/>
                    <c:param name="searchType" value="${paging.searchType}"/>
                    <c:param name="keyword" value="${paging.keyword}"/>
                </c:url>">${pageNum}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <!-- 다음 페이지 링크 -->
    <c:if test="${paging.next}">
        <a href="<c:url value='/board/free/list'>
            <c:param name='page' value='${paging.currentPage + 1}'/>
            <c:param name="startDate" value="${paging.startDate}"/>
            <c:param name="endDate" value="${paging.endDate}"/>
            <c:param name="searchType" value="${paging.searchType}"/>
            <c:param name="keyword" value="${paging.keyword}"/>
        </c:url>">&gt;</a>
    </c:if>
</div>

<button class="register-button" onclick="location.href='write'">글 등록</button>
</body>
</html>
