package com.board.service;

import java.util.List;

import com.board.domain.MemberVO;

public interface MemberService {
	
	public MemberVO login(MemberVO vo) throws Exception;
	// 댓글 조회
	public void register(MemberVO vo) throws Exception;

	public void modify(MemberVO vo) throws Exception;
	public void withdrawal(MemberVO vo) throws Exception;
	public MemberVO idCheck(String userId) throws Exception;
}
