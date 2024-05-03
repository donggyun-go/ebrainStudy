package com.study.service;

import com.study.dto.BoardDTO;
import com.study.dto.PagingDTO;
import com.study.repository.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    @Autowired
    public BoardServiceImpl(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public List<BoardDTO> boardList() {
        return boardDao.boardList();
    }

    @Override
    public void createBoard(BoardDTO boardDTO) {
        boardDao.createBoard(boardDTO);
    }

    /**
     * seq값에 해당하는 게시글 찾는 메서드
     */
    @Override
    public BoardDTO getBoardBySeq(int seq) {
        return boardDao.findBoardBySeq(seq);
    }

    @Override
    public int countBoards() {
        return boardDao.countBoards();
    }

    /**
     * 페이징 처리 확인
     */
    @Override
    public List<BoardDTO> boardListPaging(PagingDTO paging) {
        int offset = (paging.getCurrentPage() - 1) * paging.getPostsPerPage();
        int limit = paging.getPostsPerPage();
        return boardDao.boardListPaging(offset, limit);
    }

    /**
     * 검색조건에 따른 게시글 개수 출력
     * 페이징의 계산식에 이용
     * @param paging pagingDTO에 검색조건 필드값을 넣었음
     */
    @Override
    public int countBoardsBySearch(PagingDTO paging) {
        return boardDao.countBoardsBySearch(paging);
    }

    /**
     * 기존에 offset과 limit값을 각각 파라미터로 받아 계산했으나
     * pagingDTO에 필드값에 지정하고 함수로 계산식을 정의
     */
    @Override
    public List<BoardDTO> boardListPagingWithSearch(PagingDTO paging) {
        paging.calculateOffsetAndLimit();
        return boardDao.boardListPagingWithSearch(paging);
    }


}
