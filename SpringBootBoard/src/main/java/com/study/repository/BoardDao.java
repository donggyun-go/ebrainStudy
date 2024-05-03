package com.study.repository;

import com.study.dto.BoardDTO;
import com.study.dto.PagingDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardDao {
    List<BoardDTO> boardList();

    void createBoard(BoardDTO boardDTO);

    BoardDTO findBoardBySeq(int seq);

    int countBoards();

    List<BoardDTO> boardListPaging(@Param("offset") int offset, @Param("limit") int limit);

    int countBoardsBySearch(PagingDTO paging);

    List<BoardDTO> boardListPagingWithSearch(PagingDTO paging);
}
