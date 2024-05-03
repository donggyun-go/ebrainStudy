<%@ page import="com.study.connection.boardDTO" %>
<%@ page import="com.study.connection.boardDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>게시판 - 등록</title>
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
        .password-mismatch {
            color: red;
        }
        .button-group {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
    </style>
    <script>
        function validatePassword() {
            var password = document.getElementsByName("password")[0].value;
            var passwordChk = document.getElementsByName("passwordChk")[0].value;

            // 비밀번호 일치 검증
            if (password != passwordChk) {
                document.getElementById("passwordMismatchMessage").innerHTML = "비밀번호가 다릅니다.";
                return false;
            }

            // 작성자 검증
            var name = document.getElementsByName("name")[0].value;
            if (name.length < 3 || name.length > 5) {
                alert("작성자 이름은 3글자 이상, 5글자 미만이어야 합니다.");
                return false;
            }

            // 비밀번호 검증
            if (password.length < 4 || password.length > 16 || !password.match(/[a-zA-Z]/) || !password.match(/\d/) || !password.match(/[!@#$%^&*]/)) {
                alert("비밀번호는 4글자 이상, 16글자 미만이며, 영문, 숫자, 특수문자를 포함해야 합니다.");
                return false;
            }

            // 제목 검증
            var title = document.getElementsByName("title")[0].value;
            if (title.length < 4 || title.length > 100) {
                alert("제목은 4글자 이상, 100글자 미만이어야 합니다.");
                return false;
            }

            // 내용 검증
            var content = document.getElementsByName("content")[0].value;
            if (content.length < 4 || content.length > 2000) {
                alert("내용은 4글자 이상, 2000글자 미만이어야 합니다.");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>

<h2>게시판 - 등록</h2>

<form action="/boards/free/writeForm.jsp" method="post" enctype="multipart/form-data" onsubmit="return validatePassword()">
    <table class="form-table">
        <tr>
            <th>카테고리</th>
            <td>
                <select name="categorySeq">
                    <option value="1">JAVA</option>
                    <option value="2">Javascript</option>
                    <option value="3">Database</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><input type="text" name="name" required></td>
        </tr>
        <tr>
            <th>비밀번호</th>
            <td>
                <input type="password" name="password" placeholder="비밀번호" required>
                <input type="password" name="passwordChk" placeholder="비밀번호 확인" required>
                <div id="passwordMismatchMessage" class="password-mismatch"></div>
            </td>
        </tr>
        <tr>
            <th>제목</th>
            <td><input type="text" name="title" required></td>
        </tr>
        <tr>
            <th>내용</th>
            <td><textarea name="content" rows="5" required></textarea></td>
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
        <input type="button" value="취소" onclick="location.href='/'">
        <input type="submit" value="저장">
    </div>
</form>
</body>
</html>
