package com.study.controller;

import com.study.dto.BoardDTO;
import com.study.dto.CommentDTO;
import com.study.dto.PagingDTO;
import com.study.service.BoardService;
import com.study.service.CommentService;
import com.study.service.FileService;
import com.study.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 게시글에 관한 컨트롤러
 *
 */
@Controller
@RequestMapping("/board/free")
public class BoardController {

    private static final Logger logger = LogManager.getLogger(BoardController.class);
    private final BoardService boardService;
    private final CommentService commentService;
    private final FileUtils fileUtils;
    private final FileService fileService;

    @Autowired
    public BoardController(BoardService boardService, CommentService commentService, FileUtils fileUtils, FileService fileService) {
        this.boardService = boardService;
        this.commentService = commentService;
        this.fileUtils = fileUtils;
        this.fileService = fileService;
    }

    /**
     * 게시글리스트
     * @param paging 검색과 페이징처리하는 DTO
     * @param page 현재 페이지 파라미터
     * @return 페이징과 검색조건에 해당하는 게시글 리스트 리턴
     */
    @GetMapping("/list")
    public String boardList(@ModelAttribute PagingDTO paging,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model) {
        logger.info("Search Parameters: {}", paging);
        logger.info("Page: {}", page);

        paging.setCurrentPage(page);

        boolean isSearch = paging.getSearchType() != null &&
                paging.getKeyword() != null &&
                !paging.getKeyword().isEmpty();

        List<BoardDTO> boards;
        if (isSearch) {
            paging.setTotalPosts(boardService.countBoardsBySearch(paging));
            boards = boardService.boardListPagingWithSearch(paging);
        } else {
            paging.setTotalPosts(boardService.countBoards());
            boards = boardService.boardListPaging(paging);
        }

        paging.calculatePaging();
        model.addAttribute("boards", boards);
        model.addAttribute("paging", paging);

        return "list";
    }
    /**
     * 게시글 작성 페이지
     */
    @GetMapping("/write")
    public String write(){
        return "write";
    }

    /**
     * 게시글 작성 페이지의 Form을 받아
     * 게시글, 파일첨부 파라미터를 가져온다
     */
    @PostMapping("/writeForm")
    public String createBoard(@ModelAttribute BoardDTO boardDTO, MultipartHttpServletRequest mpRequest) throws Exception {
        boardService.createBoard(boardDTO);
        List<Map<String, Object>> fileList = fileUtils.parseInsertFileInfo(boardDTO, mpRequest);
        logger.info("File List: {}", fileList);
        if (!fileList.isEmpty()) {
            fileService.saveFileList(fileList);
        }
        return "redirect:/board/free/list";
    }

    /**
     * 게시글 상세보기
     * 해당 게시글의 SEQ값을 찾아 SEQ값을 가지는 게시글, 댓글, 파일을 불러옴
     */
    @GetMapping("/{seq}")
    public String findBoardAndCommentsBySeq(@PathVariable("seq") int seq, Model model) {
        BoardDTO board = boardService.getBoardBySeq(seq);
        List<CommentDTO> comments = commentService.getCommentsByBoardSeq(seq);
        List<Map<String, Object>> files = fileService.getFilesByBoardSeq(seq);

        logger.info("Files: {}", files);

        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        model.addAttribute("files", files);
        return "view";
    }

}
