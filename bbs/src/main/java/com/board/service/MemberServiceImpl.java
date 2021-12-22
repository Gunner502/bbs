package com.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.dao.MemberDAO;
import com.board.domain.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	private MemberDAO dao;


	@Override
	public void register(MemberVO vo) throws Exception {
	    dao.register(vo);
	}


	@Override
	public MemberVO login(MemberVO vo) throws Exception {
		
		return dao.login(vo);
	}


	@Override
	public void modify(MemberVO vo) throws Exception {
		dao.modify(vo);
		
	}


	@Override
	public void withdrawal(MemberVO vo) throws Exception {
		dao.withdrawal(vo);
		
	}


	@Override
	public MemberVO idCheck(String userId) throws Exception {
		// TODO Auto-generated method stub
		return dao.idCheck(userId);
	}
}
