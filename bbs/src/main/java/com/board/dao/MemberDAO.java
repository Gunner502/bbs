package com.board.dao;

import com.board.domain.MemberVO;

public interface MemberDAO {
	// 댓글 조회
	public MemberVO login(MemberVO vo) throws Exception;

	// 댓글 조회
	public void register(MemberVO vo) throws Exception;

	// 댓글 수정
	public void modify(MemberVO vo) throws Exception;

	// 댓글 삭제
	public void withdrawal(MemberVO vo) throws Exception;
	
	public MemberVO idCheck(String userId) throws Exception;
}
