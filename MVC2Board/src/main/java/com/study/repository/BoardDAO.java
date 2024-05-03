package com.study.repository;

import com.study.dto.BoardDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

    private static final Logger logger = LogManager.getLogger(BoardDAO.class);

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ebrainsoft_study";
    private final String jdbcUsername = "ebsoft";
    private final String jdbcPassword = "ebsoft";
    public BoardDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC 드라이버 로드 실패", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    /**
     * ResultSet에서 데이터를 읽어 BoardDTO 객체를 생성하는 메서드
     * tb_category를 join하여 tb_type을 가져와야함
     */
    private BoardDTO createBoardDTO(ResultSet rs) throws SQLException {
        BoardDTO board = new BoardDTO();
        board.setSeq(rs.getInt("seq"));
        board.setCategorySeq(rs.getString("category_seq"));
        board.setName(rs.getString("name"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
        board.setCreateDate(rs.getTimestamp("create_date"));
        board.setViewCnt(rs.getInt("view_cnt"));
        board.setUpdateDate(rs.getTimestamp("update_date"));
        board.setCategoryType(rs.getString("type"));
        return board;
    }

    /**
     *     검색 조건 문자열 생성 메서드
     */
    private String getSearchCondition(BoardDTO search) {
        if (search != null && search.getSearchType() != null
                && !search.getSearchType().isEmpty()
                && !search.getSearchType().equals("all")) {

            switch (search.getSearchType()) {
                case "title": return "title LIKE ?";
                case "name": return "name LIKE ?";
                case "content": return "content LIKE ?";
            }
        }
        return "";
    }

    public List<BoardDTO> getBoardsByPageAndSearch(int currentPage, int postsPerPage, BoardDTO search) {
        List<BoardDTO> boards = new ArrayList<>();
        String sql = "SELECT b.*, c.type FROM tb_board b INNER JOIN tb_category c ON b.category_seq = c.seq";

        String searchCondition = getSearchCondition(search);
        if (!searchCondition.isEmpty()) {
            sql += " WHERE " + searchCondition;
        }
        sql += " ORDER BY seq DESC LIMIT ?, ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int startRow = (currentPage - 1) * postsPerPage;
            if (search != null && search.getSearchType() != null && !search.getSearchType().isEmpty() && !search.getSearchType().equals("all")) {
                pstmt.setString(1, "%" + search.getKeyword() + "%");
                pstmt.setInt(2, startRow);
                pstmt.setInt(3, postsPerPage);
            } else {
                pstmt.setInt(1, startRow);
                pstmt.setInt(2, postsPerPage);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                boards.add(createBoardDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return boards;
    }
    public boolean addBoard(BoardDTO board) {
        String sql = "INSERT INTO tb_board (category_seq, name, password, title, content, create_date) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, board.getCategorySeq());
            pstmt.setString(2, board.getName());
            pstmt.setString(3, board.getPassword());
            pstmt.setString(4, board.getTitle());
            pstmt.setString(5, board.getContent());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BoardDTO getBoardBySeq(int seq) {
        BoardDTO board = null;

        String sql = "SELECT tb_board.*, tb_category.type FROM tb_board " +
                "LEFT JOIN tb_category ON tb_board.category_seq = tb_category.seq " +
                "WHERE tb_board.seq = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seq);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    board = createBoardDTO(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return board;
    }

    public boolean updateBoard(BoardDTO board) {
        boolean result = false;
        String sql = "UPDATE tb_board SET name = ?, title = ?, content = ?, update_date = NOW()  WHERE seq = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, board.getName());
            pstmt.setString(2, board.getTitle());
            pstmt.setString(3, board.getContent());
            pstmt.setInt(4, board.getSeq());

            result = pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Casecade를 이용하여 boardSeq값을 참조하는 Comment를 자동삭제
     */
    public boolean deleteBoard(int seq, String password) {
        String sql = "DELETE FROM tb_board WHERE seq = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seq);
            pstmt.setString(2, password);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void increaseViewCnt(int seq) {
        String sql = "UPDATE tb_board SET view_cnt = view_cnt + 1 WHERE seq = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seq);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTotalPosts(BoardDTO search) {
        int totalPosts = 0;
        String sql = "SELECT COUNT(*) FROM tb_board";

        String searchCondition = getSearchCondition(search);
        if (!searchCondition.isEmpty()) {
            sql += " WHERE " + searchCondition;
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (!searchCondition.isEmpty()) {
                pstmt.setString(1, "%" + search.getKeyword() + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalPosts = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalPosts;
    }
}
