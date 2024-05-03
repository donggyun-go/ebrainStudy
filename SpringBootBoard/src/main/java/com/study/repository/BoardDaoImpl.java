package com.study.repository;

import com.study.controller.BoardController;
import com.study.dto.BoardDTO;
import com.study.dto.PagingDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDaoImpl implements BoardDao {

    private static final Logger logger = LogManager.getLogger(BoardDaoImpl.class);
    private SqlSession sql;
    private static String namespace = "com.study.repository.BoardDao";

    @Override
    public List<BoardDTO> boardList() {
        return sql.selectList(namespace + ".boardList");
    }

    @Override
    public void createBoard(BoardDTO boardDTO) {
        sql.insert(namespace + ".createBoard");
    }

    @Override
    public BoardDTO findBoardBySeq(int seq) {
        return sql.selectOne(namespace + ".findBoardBySeq");
    }

    @Override
    public int countBoards() {
        return sql.selectOne(namespace + ".countBoards");
    }

    /**
     * 파라미터를 여러개 받는 방법중하나
     */
    @Override
    public List<BoardDTO> boardListPaging(@Param("offset") int offset, @Param("limit") int limit) {
        return sql.selectList(namespace + ".boardListPaging", Map.of("offset", offset, "limit", limit));
    }

    @Override
    public int countBoardsBySearch(PagingDTO paging) {
        return sql.selectOne(namespace + ".countBoardsBySearch", paging);
    }

    /**
     * TODO: Map으로 객체를 여러개 보내는 방법을 시도했으나 pagingDTO에 검색조건을 추가
     */
    public List<BoardDTO> boardListPagingWithSearch(PagingDTO paging) {
        logger.info("PagingDTO: {}", paging);
        return sql.selectList(namespace + ".boardListPagingWithSearch", paging );
    }

}
