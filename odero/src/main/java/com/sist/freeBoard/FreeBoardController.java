package com.sist.freeBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.freeBoard.dao.FreeBoardDAO;
import com.sist.freeBoard.dao.FreeBoardVO;
import com.sist.freeBoard.dao.FreeBoardCommentVO;


@Controller
public class FreeBoardController {
	@Autowired
	private FreeBoardDAO dao;

	// 일반 게시판
	@RequestMapping("freeBoard.do")
	public String freeBoardList(String page, Model model){
		if(page==null) page = "1";

		int curpage = Integer.parseInt(page);
		int rowSize = 10;
		int start = (rowSize*curpage)-(rowSize-1);
		int end = rowSize*curpage;

		Map map = new HashMap();

		map.put("start", start);
		map.put("end", end);

		
		List<FreeBoardVO> list=dao.freeBoardList(map);
		
		for(FreeBoardVO vo:list){
			vo.setCount(dao.contentCommentCount(vo.getNo()));
			
			int length = vo.getSubject().length();
			
			if(length >=20)
				vo.setSubject(vo.getSubject().substring(0,15)+"···");
		}
		
		
		
		int totalpage = dao.freeBoardToltalPage();
		int count = dao.freeBoardRowCount();
		count = count-((curpage*10)-10);
		model.addAttribute("list",list);
		model.addAttribute("curpage",curpage);
		model.addAttribute("totalpage",totalpage);
		model.addAttribute("count",count);

		return "freeBoard/list";
	}
	
	@RequestMapping("freeBoardInsert.do")
	public String freeBoardInsert(){
		return "freeBoard/insert";
	}

	@RequestMapping("freeBoardInsert_ok.do")
	public String freeBoardInsert_ok(FreeBoardVO vo){
		dao.freeBoardInsert(vo);
		return "redirect:freeBoard.do";
	}
	
	@RequestMapping("freeBoardContent.do")
	public String freeBoardContent(int no,int page,Model model){
		FreeBoardVO vo = dao.freeBoardContent(no);
		List<FreeBoardCommentVO> list=dao.contentCommentList(no);
		
		model.addAttribute("vo",vo);
		model.addAttribute("list", list);
		model.addAttribute("page",page);
		
		return "freeBoard/content";
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가 
	@RequestMapping("freeBoardReply.do")
	public String freeBoardReply(int page,int no,Model model){
		model.addAttribute("page",page);
		model.addAttribute("no",no);
		return "freeBoard/reply";
	}
	
	@RequestMapping("freeBoardReply_ok.do")
	public String freeBoardReply_ok(FreeBoardVO vo){
		dao.freeBoardReplyInsert(vo);
		return "redirect:freeBoard.do?page="+vo.getPage();
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//	게시판&답글 업데이트
	@RequestMapping("freeBoardUpdate.do")
	public String freeBoardUpdate(int no,int page,Model model){
		FreeBoardVO vo=dao.freeBoardContent(no);
		model.addAttribute("vo", vo);
		model.addAttribute("page",page);

		return "freeBoard/update";
	}

	@RequestMapping("freeBoardUpdate_ok.do")
	public String update_ok(FreeBoardVO vo,int page,Model model){
		boolean bCheck=dao.freeBoardUpdate_ok(vo);
		model.addAttribute("page",page);
		model.addAttribute("no",vo.getNo());
		model.addAttribute("bCheck",bCheck);

		return "freeBoard/update_ok";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	@RequestMapping("freeBoardDelete.do")
	public String freeBoardDelete(int no,Model model)	{
		model.addAttribute("no", no);
		return "freeBoard/delete";
	}


	@RequestMapping("freeBoardDelete_ok.do")
	public String board_delete_ok(int no,String pwd,Model model){
		boolean bCheck=dao.freeBoardDelete_ok(no, pwd);
		model.addAttribute("bCheck",bCheck);
		return "freeBoard/delete_ok";
	}

	
	
	
	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// content 댓글 추가
	
	@RequestMapping("contentCommentInsert.do")
	public String contentCommentInsert(FreeBoardCommentVO vo) {
		dao.contentCommentInsert(vo);
		return "redirect:freeBoardContent.do?no="+vo.getBno()+"&page="+vo.getPage();
	}
	@RequestMapping("contentCommentNewInsert.do")
	public String contentCommentNewInsert(FreeBoardCommentVO vo) {
		dao.contentCommentNweInsert(vo);
		return "redirect:freeBoardContent.do?no="+vo.getBno()+"&page="+vo.getPage();
	}

	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	//	Content&댓글 업데이트
	@ResponseBody
	@RequestMapping("contentCommentUpdate.do")
	public String contentCommentUpdate_ok(FreeBoardCommentVO vo,Model model){
		String data = dao.contentCommentUpdate(vo);
		/*boolean bCheck=dao.contentReplyUpdate_ok(vo);
		model.addAttribute("page",vo.getPage());
		model.addAttribute("no",vo.getBno());
		model.addAttribute("bCheck",bCheck);
		
		return "freeBoard/contentReplyUpdate_ok";*/
		return data;
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// content 댓글 삭제
	@ResponseBody
	@RequestMapping("contentCommentDelete_ok.do")
	public String ContentCommentDelete_ok(FreeBoardCommentVO vo,Model model){
		String data = dao.contentCommentDelete_ok(vo);
		/*model.addAttribute("bCheck",bCheck);
		model.addAttribute("no",vo.getBno());
		model.addAttribute("page",vo.getPage());
		return "freeBoard/contentReplyDelete_ok";*/
		return data;
	}
}

