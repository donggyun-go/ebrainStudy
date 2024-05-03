<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.connection.boardDAO" %>
<%@ page import="com.study.connection.boardDTO" %>
<%@ page import="java.net.URLEncoder" %>
<%
    String searchType = request.getParameter("searchType");
    String keyword = request.getParameter("keyword");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
    int postsPerPage = 10;
    int pagesToShow = 10;
    boardDAO dao = new boardDAO();

    int totalPosts = dao.getTotalPosts(searchType, keyword, startDate, endDate);
    int totalPages = (int) Math.ceil((double) totalPosts / postsPerPage);

    // 시작 페이지와 끝 페이지 계산
    int startPage = ((currentPage - 1) / pagesToShow) * pagesToShow + 1;
    int endPage = startPage + pagesToShow - 1;
    if (endPage > totalPages) {
        endPage = totalPages;
    }

    // 쿼리 파라미터 문자열 생성
    String queryParams = "";
    if (searchType != null && !searchType.isEmpty()) queryParams += "&searchType=" + URLEncoder.encode(searchType, "UTF-8");
    if (keyword != null && !keyword.isEmpty()) queryParams += "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
    if (startDate != null && !startDate.isEmpty()) queryParams += "&startDate=" + startDate;
    if (endDate != null && !endDate.isEmpty()) queryParams += "&endDate=" + endDate;
%>
<!DOCTYPE html>
<html>
<head>
    <title>자유 게시판 - 목록</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
            text-align: center; /* 모든 칼럼을 가운데 정렬 */
        }
        th {
            background-color: #f2f2f2;
            border-bottom: 2px solid black; /* th와 td 구분 선을 굵게 */
        }
        td {
            border-bottom: 1px solid black; /* 가로줄만 표시 */
        }
        td:nth-child(2) {
            text-align: left; /* 제목 칼럼만 왼쪽 정렬 */
            width: 40%; /* 제목 칼럼의 너비를 더 넓게 설정 */
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
            position: fixed; /* 고정 위치 */
            bottom: 20px; /* 아래에서 20px */
            right: 20px; /* 오른쪽에서 20px */
        }
    </style>
</head>
<body>

<h1>자유 게시판 - 목록</h1>

<form action="/boards/free/list.jsp" method="get">
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
<div>
    총 <strong><%= totalPosts %></strong> 건
</div>
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
    <%
        List<boardDTO> boardList;
        boardList = dao.getBoards(currentPage, postsPerPage, searchType, keyword, startDate, endDate); // getBoards 메서드에 페이징 및 검색 로직 적용 필요

        for (boardDTO board : boardList) {
            String displayTitle = board.getTitle();
            if (displayTitle.length() > 20) {
                displayTitle = displayTitle.substring(0, 20) + "...";
            }
    %>
    <tr>
        <td><%= board.getCategoryType() %></td>
        <td><a href="view.jsp?seq=<%= board.getSeq() %>"><%= displayTitle %></a></td>
        <td><%= board.getName() %></td>
        <td><%= board.getViewCnt() %></td>
        <td><%= board.getCreateDate() %></td>
        <td><%= board.getUpdateDate() != null ? board.getUpdateDate() : "-" %></td>
    </tr>
    <% } %>
</table>

<br/>

<div class="pagination">
    <%
        for (int i = startPage; i <= endPage; i++) {
            String pageLink = "list.jsp?page=" + i + queryParams;
            if (i == currentPage) {
                out.print("<a href='" + pageLink + "' class='active'>" + i + "</a> ");
            } else {
                out.print("<a href='" + pageLink + "'>" + i + "</a> ");
            }
        }
    %>
</div>
<button class="register-button" onclick="location.href='write.jsp'">글 등록</button>
</body>
</html>
