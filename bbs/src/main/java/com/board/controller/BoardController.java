package com.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardVO;
import com.board.domain.Page;
import com.board.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@Inject
	BoardService service;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void getList(Model model) throws Exception {
		List<BoardVO> list = null;
		list = service.list();
		
		System.out.println("title="+list.get(0).getTitle());
		
		model.addAttribute("list", list);
	}
	
	@RequestMapping(value="write", method=RequestMethod.GET)
	public void getWrite(Model model) throws Exception {
	
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
	
}
