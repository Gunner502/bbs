package com.board.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardVO;
import com.board.domain.Page;
import com.board.domain.ReplyVO;
import com.board.service.BoardService;
import com.board.service.ReplyService;

@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@Inject
	private BoardService service;
	
	@Inject
	private ReplyService replyService;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void getList(Model model) throws Exception {
		List<BoardVO> list = null;
		list = service.list();
		
		System.out.println("title="+list.get(0).getTitle());
		
		model.addAttribute("list", list);
	}
	
	@RequestMapping(value="write", method=RequestMethod.GET)
	public void getWrite(HttpSession session, Model model) throws Exception {
		Object loginInfo = session.getAttribute("member");

		if(loginInfo == null) {
			System.out.println("loginError\n");
			model.addAttribute("msg", "loginError");
		}
	}
	
	// 게시물 작성
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public String postWirte(BoardVO vo) throws Exception {
	  service.write(vo);
	  
	  return "redirect:/board/list";
	}
	
	@RequestMapping(value="view", method=RequestMethod.GET)
	public void getView(@RequestParam("bno") int bno, Model model) throws Exception {
		model.addAttribute("view", service.view(bno));
		
		// 댓글 조회
		List<ReplyVO> reply = null;
		reply = replyService.list(bno);
		model.addAttribute("reply", reply);
	}
	
	// 게시물 수정
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public void getModify(@RequestParam("bno") int bno, Model model) throws Exception {
		model.addAttribute("view", service.view(bno));
	}
	
	// 게시물 수정
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo) throws Exception {

	 service.modify(vo);
	   
	 return "redirect:/board/view?bno=" + vo.getBno();
	}
	
	// 게시물 삭제
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("bno") int bno) throws Exception {
		service.delete(bno);
		return "redirect:/board/list";
	}
	
	// 게시물 목록 + 페이징 추가
	@RequestMapping(value = "listPage", method = RequestMethod.GET)
	public void getListPage(Model model, @RequestParam("num") int num) throws Exception {
		Page page = new Page();
		
		int cnt = service.count();
		page.setNum(num);
		page.setCount(cnt);
		
		/*
		 * // 한페이지에 출력할 건수 int postNum = 10;
		 * 
		 * // 출력할 게시물 int displayPost = (num-1)*postNum;
		 * 
		 * //하단페이징 번호 갯수 int pageNum = (int)Math.ceil((double)cnt/postNum);
		 * 
		 * // 한번에 표시할 페이징 번호의 갯수 int pageNum_cnt = 10;
		 * 
		 * // 표시되는 페이지 번호 중 마지막 번호 int endPageNum = (int)(Math.ceil((double)num /
		 * (double)pageNum_cnt) * pageNum_cnt);
		 * 
		 * // 표시되는 페이지 번호 중 첫번째 번호 int startPageNum = endPageNum - (pageNum_cnt - 1);
		 * 
		 * // 마지막 번호 재계산 int endPageNum_tmp = (int)(Math.ceil((double)cnt /
		 * (double)pageNum_cnt));
		 * 
		 * if(endPageNum > endPageNum_tmp) { endPageNum = endPageNum_tmp; }
		 * 
		 * boolean prev = startPageNum == 1 ? false : true; boolean next = endPageNum *
		 * pageNum_cnt >= cnt ? false : true;
		 */
		
		 List<BoardVO> list = null; 
		 list = service.listPage(page.getDisplayPost(), page.getPostNum());
		 
		 model.addAttribute("list", list);
		/*
		 * model.addAttribute("pageNum", page.getPageNum());
		 * 
		 * // 시작 및 끝 번호 model.addAttribute("startPageNum", page.getStartPageNum());
		 * model.addAttribute("endPageNum", page.getEndPageNum());
		 * 
		 * // 이전 및 다음 model.addAttribute("prev", page.isPrev());
		 * model.addAttribute("next", page.isNext());
		 */
		 
		 model.addAttribute("page", page);
		 
		// 현재 페이지
		 model.addAttribute("select", num);
	}
	
	// 게시물 목록 + 페이징 추가 + 검색
	@RequestMapping(value = "listPageSearch", method = RequestMethod.GET)
	public void getListPageSearch(Model model, @RequestParam("num") int num, 
			@RequestParam(value="searchType", required=false, defaultValue="title") String searchType, 
			@RequestParam(value="keyword", required=false, defaultValue="") String keyword) throws Exception {
		Page page = new Page();

		//int cnt = service.count();
		//page.setCount(cnt);
		page.setNum(num);		
		page.setCount(service.searchCount(searchType, keyword));
		
		// 검색 타입과 검색어
		page.setSearchType(searchType);
		page.setKeyword(keyword);

		List<BoardVO> list = null;
		//list = service.listPage(page.getDisplayPost(), page.getPostNum());
		list = service.listPageSearch(page.getDisplayPost(), page.getPostNum(), searchType, keyword);

		model.addAttribute("list", list);
		
		model.addAttribute("page", page);

		// 현재 페이지
		model.addAttribute("select", num);
		//model.addAttribute("searchType", searchType);
		//model.addAttribute("keyword", keyword);
	}
	
}
