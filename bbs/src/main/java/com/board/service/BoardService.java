package com.board.service;

import java.util.List;

import com.board.domain.BoardVO;

public interface BoardService {
	public List<BoardVO> list() throws Exception; 
	public void write(BoardVO vo) throws Exception;
	public BoardVO view(int bno) throws Exception;
	public void modify(BoardVO vo) throws Exception;
	public void delete(int bno) throws Exception;
	// 게시물 총 갯수
	public int count() throws Exception;
	// 게시물 목록 + 페이징
	public List<BoardVO> listPage(int displayPost, int postNum) throws Exception;
}
