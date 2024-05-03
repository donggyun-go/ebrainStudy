package com.study.repository;

import com.study.dto.CommentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CommentDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/ebrainsoft_study";
    private String jdbcUsername = "ebsoft";
    private String jdbcPassword = "ebsoft";

    public CommentDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC 드라이버 로드 실패", e);
        }
    }
    public boolean addComment(CommentDTO comment) {
        boolean result = false;
        String sql = "INSERT INTO tb_comment (content, create_date, board_seq) VALUES (?, NOW(), ?)";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, comment.getContent());
            pstmt.setInt(2, comment.getBoardSeq());

            result = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public List<CommentDTO> getCommentsByBoardSeq(int boardSeq) {
        List<CommentDTO> comments = new ArrayList<>();
        String sql = "SELECT * FROM tb_comment WHERE board_seq = ? ORDER BY create_date DESC";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, boardSeq);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CommentDTO comment = new CommentDTO();
                comment.setSeq(rs.getInt("seq"));
                comment.setContent(rs.getString("content"));
                comment.setCreateDate(rs.getDate("create_date"));
                comment.setBoardSeq(rs.getInt("board_seq"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }
}
