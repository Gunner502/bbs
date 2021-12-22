package com.board.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.MemberVO;
import com.board.service.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	@Inject
	private MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passEncoder;
	
	// 
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void getRegister(MemberVO vo) throws Exception {
	    
	}
	
	// 
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postRegister(MemberVO vo) throws Exception {
	    
		String rawPass = vo.getUserPass();
		String cryptPass = passEncoder.encode(rawPass);
		vo.setUserPass(cryptPass);
		
	    memberService.register(vo);
	    
	    return "redirect:/";
	}
	
	// 로그인
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(MemberVO vo, HttpServletRequest req, RedirectAttributes rttr) throws Exception {
		//logger.info("post login");

		HttpSession session = req.getSession();

		MemberVO login = memberService.login(vo);
		System.out.printf("db pass=%s\n", login.getUserPass());
		System.out.printf("input pass=%s\n", vo.getUserPass());
		
		boolean passMatch = passEncoder.matches(vo.getUserPass(), login.getUserPass());

		if ((login == null) || !passMatch) {
			System.out.println("username or pass is wrong\n");
			rttr.addFlashAttribute("msg", false);
			session.setAttribute("member", null);
		} else {
			System.out.printf("username=%s\n", login.getUserName());
			System.out.printf("userpass=%s\n", login.getUserPass());
			
			session.setAttribute("member", login);
			//rttr.addFlashAttribute("member", login);
		}

		return "redirect:/";
	}
	
	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception {
		//logger.info("get logout");

		session.invalidate();

		return "redirect:/";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(MemberVO vo) throws Exception {
	    
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(MemberVO vo, HttpSession session) throws Exception {
		
	    String rawPass = vo.getUserPass();
		String cryptPass = passEncoder.encode(rawPass);
		vo.setUserPass(cryptPass);
		
		System.out.printf("rawPass=%s\nencodedPass=%s\n", rawPass, cryptPass);
	    
	    memberService.modify(vo);
	    
	    session.invalidate();
	    
	    return "redirect:/";
	}
	
	@RequestMapping(value = "/withdrawal", method = RequestMethod.GET)
	public void getWithdrawal(MemberVO vo) throws Exception {
	    
	}
	
	@RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
	public String postWithdrawal(MemberVO vo, HttpSession session, RedirectAttributes rttr) throws Exception {
		
	    MemberVO svo = (MemberVO)session.getAttribute("member");
	    String oldPass = svo.getUserPass();
	    
	    String newPass = vo.getUserPass();
	    
	    System.out.printf("oldUserId=%s\n oldPass=%s\n newPass=%s\n", svo.getUserId(), oldPass, newPass);
	    
	    if(!passEncoder.matches(newPass, oldPass)) {
	    	rttr.addFlashAttribute("msg", false);
	    	return "redirect:/member/withdrawal";
	    }
	    
	    memberService.withdrawal(vo);
	    
	    session.invalidate();
	    
	    return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public int postIdCheck(HttpServletRequest req) throws Exception {
	    String userId = req.getParameter("userId");
	    MemberVO member = memberService.idCheck(userId);
	    
	    int isResult = 0;
	    if(member != null) {
	    	isResult = 1;
	    }
	    
	    return isResult;
	}
}
