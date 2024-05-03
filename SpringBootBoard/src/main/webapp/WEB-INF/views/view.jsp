<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>게시판 - 보기</title>
    <style>
        .content-box {
            margin-bottom: 20px;
            border: 1px solid #ddd;
            padding: 10px;
        }
        .header-row, .title-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }
        .comment-box {
            background-color: #f0f0f0;
            border: 1px solid #ddd;
            margin-bottom: 10px;
            padding: 10px;
        }
        .comment {
            border-bottom: 1px solid #ddd;
            padding: 5px;
        }
        .comment:last-child {
            border-bottom: none;
        }
        .comment-form {
            display: flex;
            margin-top: 10px;
        }
        .comment-form textarea {
            flex-grow: 1;
            margin-right: 10px;
        }
        .button-group {
            text-align: center; +
        }

        .button-group button {
            margin: 0 10px;
        }
    </style>

</head>
<body>

<h1>게시판 - 보기</h1>

<c:choose>
    <c:when test="${not empty board}">
        <div class="header-row">
            <div>작성자: ${board.name}</div>
            <div>등록일시: <fmt:formatDate value="${board.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                | 수정일시: <c:if test="${board.updateDate != null}"><fmt:formatDate value="${board.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></c:if>
                <c:if test="${board.updateDate == null}">-</c:if>
            </div>
        </div>

        <div class="title-row">
            <div>[${board.categoryType}] ${board.title}</div>
            <div>조회수: ${board.viewCnt}</div>
        </div>
        <hr>
        <div class="content-box">
                ${board.content}
        </div>
        <div class="file-form">
            <h3>첨부 파일</h3>
            <c:if test="${not empty files}">
                <ul>
                    <c:forEach var="file" items="${files}">
                        <li>
                            <a href="/download?filename=${fn:escapeXml(file['save_name'])}">${file['real_name']}</a>
                            (업로드 날짜: <fmt:formatDate value="${file['create_date']}" pattern="yyyy-MM-dd"/>)
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${empty files}">
                <p>첨부된 파일이 없습니다.</p>
            </c:if>
        </div>

        <div class="comment-box">
            <c:forEach var="comment" items="${comments}">
                <div class="comment">
                    <p>작성일: <fmt:formatDate value="${comment.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
                    <p>${comment.content}</p>
                </div>
            </c:forEach>
            <div class="comment-form">
                <form action="/board/free/comment" method="post">
                    <input type="hidden" name="boardSeq" value="${board.seq}" />
                    <textarea name="content" rows="3" required></textarea>
                    <input type="submit" value="댓글 등록" />
                </form>
            </div>
        </div>
    </c:when>
</c:choose>

<div class="button-group">
    <button onclick="location.href='list'">목록</button>
    <button onclick="location.href='modify?seq=${board.seq}&currentPage=${param.page}&searchType=${param.searchType}&keyword=${param.keyword}'">수정</button>
    <button onclick="location.href='delete?seq=${board.seq}&currentPage=${param.page}&searchType=${param.searchType}&keyword=${param.keyword}'">삭제</button>
</div>

</body>
</html>
