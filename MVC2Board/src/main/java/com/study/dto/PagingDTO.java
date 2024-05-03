package com.study.dto;

import lombok.Data;

@Data
public class PagingDTO {
    private int postsPerPage = 10; // 한 페이지당 게시글 수
    private int displayPageNum = 10; // 화면에 표시될 페이지 번호의 개수
    private int totalPosts; // 총 게시글 수
    private int totalPages; // 총 페이지 수
    private int currentPage; // 현재 페이지 번호
    private int startPage; // 시작 페이지 번호
    private int endPage; // 마지막 페이지 번호
    private boolean prev; // 이전 페이지 링크 존재 여부
    private boolean next; // 다음 페이지 링크 존재 여부

    /**
     *
     */
    public void calculatePaging() {
        totalPages = (totalPosts - 1) / postsPerPage + 1;
        endPage = ((currentPage - 1) / displayPageNum + 1) * displayPageNum;
        if(totalPages < endPage) {
            endPage = totalPages;
        }
        startPage = ((currentPage - 1) / displayPageNum) * displayPageNum + 1;

        prev = startPage != 1;
        next = endPage < totalPages;
    }
}