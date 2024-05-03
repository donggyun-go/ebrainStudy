package com.study.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class boardDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/ebrainsoft_study";
    private final String jdbcUsername = "ebsoft";
    private final String jdbcPassword = "ebsoft";

    public boardDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC 드라이버 로드 실패", e);
        }
    }

    // boardDTO 객체의 데이터를 데이터베이스에 저장하는 메서드
    public int addBoard(boardDTO board) {
        int generatedSeq = 0;
        String sql = "INSERT INTO tb_board (category_seq, name, password, title, content, create_date) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, board.getCategorySeq());
            pstmt.setString(2, board.getName());
            pstmt.setString(3, board.getPassword());
            pstmt.setString(4, board.getTitle());
            pstmt.setString(5, board.getContent());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedSeq = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedSeq;
    }
    public List<boardDTO> getAllBoards() {
        List<boardDTO> boards = new ArrayList<>();
        String sql = "SELECT * FROM tb_board";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                boardDTO board = new boardDTO();
                board.setSeq(rs.getInt("seq"));
                board.setCategorySeq(rs.getString("category_seq"));
                board.setName(rs.getString("name"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setCreateDate(rs.getTimestamp("create_date"));
                board.setViewCnt(rs.getInt("view_cnt"));
                board.setUpdateDate(rs.getTimestamp("update_date"));
                boards.add(board);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return boards;
    }

    // 특정 seq 값을 가진 게시글을 조회하는 메서드
    public boardDTO getBoardBySeq(int seq) {
        boardDTO board = null;

        String sql = "SELECT tb_board.*, tb_category.type AS categoryType FROM tb_board " +
                "LEFT JOIN tb_category ON tb_board.category_seq = tb_category.seq " +
                "WHERE tb_board.seq = ?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seq);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                board = new boardDTO();
                board.setSeq(rs.getInt("seq"));
                board.setCategoryType(rs.getString("categoryType"));
                board.setName(rs.getString("name"));
                board.setPassword(rs.getString("password"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setCreateDate(rs.getTimestamp("create_date"));
                board.setViewCnt(rs.getInt("view_cnt"));
                board.setUpdateDate(rs.getTimestamp("update_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return board;
    }

    public boolean updateBoard(boardDTO board) {
        boolean result = false;
        String sql = "UPDATE tb_board SET name = ?, title = ?, content = ?, update_date = NOW()  WHERE seq = ?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
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
    public boolean deleteBoard(int seq, String password) throws SQLException {
        String sql = "DELETE FROM tb_board WHERE seq = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seq);
            pstmt.setString(2, password);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public void increaseViewCnt(int seq) {
        String sql = "UPDATE tb_board SET view_cnt = view_cnt + 1 WHERE seq = ?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seq);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<boardDTO> getBoards(int currentPage, int postsPerPage, String searchType, String keyword, String startDate, String endDate) {
        List<boardDTO> boards = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT tb_board.*, tb_category.type AS categoryType FROM tb_board ");
        sql.append("LEFT JOIN tb_category ON tb_board.category_seq = tb_category.seq ");

        List<String> conditions = new ArrayList<>();
        if (searchType != null && !searchType.equals("all") && keyword != null && !keyword.isEmpty()) {
            switch (searchType) {
                case "title":
                    conditions.add("title LIKE ?");
                    break;
                case "name":
                    conditions.add("name LIKE ?");
                    break;
                case "content":
                    conditions.add("content LIKE ?");
                    break;
            }
        }
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            conditions.add("create_date BETWEEN ? AND ?");
        }

        if (!conditions.isEmpty()) {
            sql.append("WHERE ");
            sql.append(String.join(" AND ", conditions));
        }

        int offset = (currentPage - 1) * postsPerPage;
        sql.append(" ORDER BY seq DESC LIMIT ?, ?");

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            boolean hasKeyword = searchType != null && !searchType.equals("all") && keyword != null && !keyword.isEmpty();
            if (hasKeyword) {
                pstmt.setString(index++, "%" + keyword + "%");
            }

            if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
                pstmt.setString(index++, startDate);
                pstmt.setString(index++, endDate);
            }
            pstmt.setInt(index++, offset);
            pstmt.setInt(index, postsPerPage);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                boardDTO board = new boardDTO();
                board.setSeq(rs.getInt("seq"));
                board.setCategorySeq(rs.getString("category_seq"));
                board.setName(rs.getString("name"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setCreateDate(rs.getTimestamp("create_date"));
                board.setViewCnt(rs.getInt("view_cnt"));
                board.setUpdateDate(rs.getTimestamp("update_date"));
                board.setCategoryType(rs.getString("categoryType"));
                boards.add(board);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return boards;
    }


    public int getTotalPosts(String searchType, String keyword, String startDate, String endDate) {
        int totalPosts = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM tb_board ");

        // 검색 조건에 따른 SQL 쿼리 추가
        List<String> conditions = new ArrayList<>();
        if (searchType != null && !searchType.equals("all") && keyword != null && !keyword.isEmpty()) {
            switch (searchType) {
                case "title":
                    conditions.add("title LIKE ?");
                    break;
                case "name":
                    conditions.add("name LIKE ?");
                    break;
                case "content":
                    conditions.add("content LIKE ?");
                    break;
            }
        }
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            conditions.add("create_date BETWEEN ? AND ?");
        }

        if (!conditions.isEmpty()) {
            sql.append("WHERE ");
            sql.append(String.join(" AND ", conditions));
        }

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (searchType != null && !searchType.equals("all") && keyword != null && !keyword.isEmpty()) {
                pstmt.setString(index++, "%" + keyword + "%");
            }
            if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
                pstmt.setString(index++, startDate);
                pstmt.setString(index, endDate);
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
