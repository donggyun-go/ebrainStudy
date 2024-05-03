package com.study.service;

import com.study.dto.BoardDTO;
import com.study.dto.PagingDTO;

import java.util.List;

public interface BoardService {

    List<BoardDTO> boardList();

    void createBoard(BoardDTO boardDTO);

    BoardDTO getBoardBySeq(int seq);

    int countBoards();

    List<BoardDTO> boardListPaging(PagingDTO paging);

    int countBoardsBySearch(PagingDTO paging);

    List<BoardDTO> boardListPagingWithSearch(PagingDTO paging);
}