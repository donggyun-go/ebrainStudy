<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="com.study.connection.boardDAO" %>
<%@ page import="com.study.connection.boardDTO" %>
<%@ page import="com.study.connection.fileDAO" %>
<%@ page import="com.study.connection.fileDTO" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 초기화
    String categorySeq = null, name = null, password = null, passwordChk = null, title = null, content = null;
    boolean isValid = true;
    String errorMessage = "";
    int boardSeq = -1;
    List<FileItem> formItems = null;

    // 파일 저장 경로 설정
    String realPath = "C:\\mp\\file";
    int maxSize = 10 * 1024 * 1024;

    // 파일 업로드 요청인지 확인
    if (ServletFileUpload.isMultipartContent(request)) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(maxSize);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            // 요청에서 데이터 추출
            formItems = upload.parseRequest(request);

            for (FileItem item : formItems) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString("UTF-8");

                    // 폼 데이터 처리
                    switch (fieldName) {
                        case "categorySeq":
                            categorySeq = fieldValue;
                            break;
                        case "name":
                            name = fieldValue;
                            break;
                        case "password":
                            password = fieldValue;
                            break;
                        case "passwordChk":
                            passwordChk = fieldValue;
                            break;
                        case "title":
                            title = fieldValue;
                            break;
                        case "content":
                            content = fieldValue;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            isValid = false;
            errorMessage += "<p>파일 업로드 중 오류가 발생했습니다.</p>";
        }
    }

    if (isValid) {
        // 게시글 객체 생성 및 데이터 설정
        boardDTO board = new boardDTO();
        board.setCategorySeq(categorySeq);
        board.setName(name);
        board.setPassword(password);
        board.setTitle(title);
        board.setContent(content);

        // 게시글 저장
        boardDAO dao = new boardDAO();
        boardSeq = dao.addBoard(board);

        System.out.println(boardSeq);
        // 게시글 저장 성공 시 파일 처리
        if (boardSeq > 0) {
            fileDAO fileDao = new fileDAO();

            for (FileItem item : formItems) {
                if (!item.isFormField() && item.getSize() > 0) {
                    String fileName = new File(item.getName()).getName();
                    // 파일 이름을 올바르게 처리하여 저장
                    String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
                    File file = new File(realPath, encodedFileName);
                    item.write(file);

                    // 파일 정보 저장
                    fileDTO fileDto = new fileDTO();
                    fileDto.setRealName(encodedFileName);
                    fileDto.setSaveName(encodedFileName);
                    fileDto.setSavePath(realPath);
                    fileDto.setCreateDate(new Date());
                    fileDto.setBoardSeq(boardSeq);
                    fileDao.addFiles(fileDto);
                }
            }
            // 게시글 목록으로 리다이렉트
            response.sendRedirect("/boards/free/list.jsp");
        } else {
            out.println("<p>게시글 추가에 실패했습니다.</p>");
        }
    } else {
        out.println(errorMessage);
    }
%>
