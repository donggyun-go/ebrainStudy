package com.study.connection;

import java.sql.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class fileDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/ebrainsoft_study";
    private String jdbcUsername = "ebsoft";
    private String jdbcPassword = "ebsoft";
    Connection con = null;
    Statement stmt = null;

    // 생성자에서 MySQL JDBC 드라이버를 로드합니다.
    public fileDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC 드라이버 로드 실패", e);
        }
    }

    // 파일 정보를 데이터베이스에 저장하는 메서드
    public boolean addFiles(fileDTO file) {
        String sql = "INSERT INTO tb_file (real_name, save_name, save_path, create_date, board_seq) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, file.getRealName());
            pstmt.setString(2, file.getSaveName());
            pstmt.setString(3, file.getSavePath());
            pstmt.setTimestamp(4, new java.sql.Timestamp(file.getCreateDate().getTime()));
            pstmt.setInt(5, file.getBoardSeq());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 파일을 게시물(boardSeq)에 따라 찾아서 리스트로 반환하는 메서드
    public List<File> findFilesByBoardSeq(int boardSeq) {
        List<File> fileList = new ArrayList<>();
        String sql = "SELECT save_name, save_path FROM tb_file WHERE board_seq = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, boardSeq);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String saveName = rs.getString("save_name");
                String savePath = rs.getString("save_path");

                // 파일 경로 생성
                String filePath = savePath + File.separator + saveName;
                File file = new File(filePath);

                // 파일이 존재하는지 확인하고 리스트에 추가
                if (file.exists()) {
                    fileList.add(file);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileList;
    }
}
