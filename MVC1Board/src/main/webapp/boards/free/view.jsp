<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.connection.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.ArrayList" %>
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

<%
    String seqParam = request.getParameter("seq");
    boardDTO board = null;

    if (seqParam != null && !seqParam.isEmpty()) {
        try {
            int seq = Integer.parseInt(seqParam);
            boardDAO dao = new boardDAO();

            dao.increaseViewCnt(seq);
            board = dao.getBoardBySeq(seq);
        } catch(NumberFormatException e) {
            out.println("<p>잘못된 게시글 번호입니다.</p>");
        }
    }

    if (board != null) {
%>
<div class="header-row">
    <div>작성자: <%= board.getName() %></div>
    <div>등록일시: <%= board.getCreateDate() %> | 수정일시: <%= board.getUpdateDate() != null ? board.getUpdateDate() : "-" %></div>
</div>

<div class="title-row">
    <div>[<%= board.getCategoryType() %>] <%= board.getTitle() %></div>
    <div>조회수: <%= board.getViewCnt() %></div>
</div>
<hr>
<div class="content-box">
    <%= board.getContent() %>
</div>

<div>
    <%
        fileDAO fileDao = new fileDAO();
        List<fileDTO> fileList = new ArrayList<>(); // List<fileDTO> 객체 생성

        // List<File>에서 List<fileDTO>로 변환
        List<File> files = fileDao.findFilesByBoardSeq(board.getSeq());
        for (File file : files) {
            fileDTO fileDto = new fileDTO();
            fileDto.setRealName(file.getName());
            fileList.add(fileDto);
        }

        if (!fileList.isEmpty()) {
            for (fileDTO file : fileList) {

                String filePath = "C:\\mp\\file";

                String fileName = file.getRealName().replaceAll("[^a-zA-Z0-9.-]", "_");
    %>
    <p><a href="<%= filePath + File.separator + fileName %>" download="<%= file.getRealName() %>"><%= file.getRealName() %></a></p>
    <%
            }
        }
    %>
</div>

<div class="comment-box">
    <%
        commentDAO commentDao = new commentDAO();
        List<commentDTO> comments = commentDao.getCommentsByBoardSeq(board.getSeq());

        for (commentDTO comment : comments) {
    %>
    <div class="comment">
        <p>작성일: <%= comment.getCreateDate() %></p>
        <p><%= comment.getContent() %></p>
    </div>
    <% } %>

    <div class="comment-form">
        <form action="/boards/free/commentForm.jsp" method="get" accept-charset="UTF-8">
            <input type="hidden" name="boardSeq" value="<%= board.getSeq() %>" />
            <textarea name="commentContent" rows="3" required></textarea>
            <input type="submit" value="댓글 등록" />
        </form>
    </div>
</div>
<%
    } else {
        out.println("<p>해당 게시글을 찾을 수 없습니다.</p>");
    }
%>

<div class="button-group">
    <button onclick="location.href='list.jsp'">목록</button>
    <% if (board != null) { %>
    <button onclick="location.href='modify.jsp?seq=<%= board.getSeq() %>'">수정</button>
    <button onclick="location.href='delete.jsp?seq=<%= board.getSeq() %>'">삭제</button>
    <% } %>
</div>

</body>
</html>
