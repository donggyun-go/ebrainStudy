<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
  <title>게시판 - 수정</title>
  <style>
    .form-table {
      width: 100%;
      border-collapse: collapse;
    }
    .form-table th, .form-table td {
      border: 1px solid black;
      padding: 8px;
      text-align: left;
    }
    .form-table th {
      width: 20%;
    }
    .form-table td {
      width: 80%;
    }
    .button-group {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }
  </style>
</head>
<body>

<h2 style="text-align:center;">게시판 - 수정</h2>
<script>
  function validateForm() {
    var name = document.getElementsByName("name")[0].value;
    var title = document.getElementsByName("title")[0].value;
    var content = document.getElementsByName("content")[0].value;
    var inputPassword = document.getElementById("inputPassword").value;

    if (name.length < 3 || name.length > 5) {
      alert("작성자 이름은 3글자 이상, 5글자 미만이어야 합니다.");
      return false;
    }

    if (title.length < 4 || title.length > 100) {
      alert("제목은 4글자 이상, 100글자 미만이어야 합니다.");
      return false;
    }

    if (content.length < 4 || content.length > 2000) {
      alert("내용은 4글자 이상, 2000글자 미만이어야 합니다.");
      return false;
    }

    var inputPasswordHash = sha256(inputPassword);
    if (inputPasswordHash !== actualPasswordHash) {
      alert("비밀번호가 틀렸습니다.");
      return false;
    }

    return true;
  }
</script>
<form action="/boards/free/modify" method="post" onsubmit="return validateForm()">
  <input type="hidden" name="seq" value="${board.seq}">
  <table class="form-table">
    <tr>
      <th>카테고리</th>
      <td>${board.categorySeq}</td>
    </tr>
    <tr>
      <th>등록일시</th>
      <td><fmt:formatDate value="${board.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    </tr>
    <tr>
      <th>수정일시</th>
      <td><c:choose><c:when test="${board.updateDate != null}"><fmt:formatDate value="${board.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></c:when><c:otherwise>-</c:otherwise></c:choose></td>
    </tr>
    <tr>
      <th>조회수</th>
      <td>${board.viewCnt}</td>
    </tr>
    <tr>
      <th>작성자</th>
      <td><input type="text" name="name" value="${board.name}" required></td>
    </tr>
    <tr>
      <th>비밀번호 확인</th>
      <td><input type="password" id="inputPassword" name="password" placeholder="비밀번호" required></td>
    </tr>
    <tr>
      <th>제목</th>
      <td><input type="text" name="title" value="${board.title}" required></td>
    </tr>
    <tr>
      <th>내용</th>
      <td><textarea name="content" rows="5" required>${board.content}</textarea></td>
    </tr>
    <tr>
      <th>파일 첨부</th>
      <td>
        <input type="file" name="file1"><br>
        <input type="file" name="file2"><br>
        <input type="file" name="file3">
      </td>
    </tr>
  </table>
  <div class="button-group">
    <input type="button" value="취소" onclick="location.href='list'">
    <input type="submit" value="저장">
  </div>
</form>
</body>
</html>