package com.sist.freeBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
			vo.setCount(dao.freeCommentCount(vo.getNo()));
			
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
	
	@RequestMapping("freeBoardContent.do")
	public String freeBoardContent(int no,int page,Model model){
		FreeBoardVO vo = dao.freeBoardContent(no);
		List<FreeBoardCommentVO> list=dao.freeCommentList(no);
		
		model.addAttribute("vo",vo);
		model.addAttribute("list", list);
		model.addAttribute("page",page);
		
		return "freeBoard/content";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 게시글 추가
	@RequestMapping("freeBoardInsert.do")
	public String freeBoardInsert(){
		return "freeBoard/insert";
	}

	@RequestMapping("freeBoardInsert_ok.do")
	public String freeBoardInsert_ok(FreeBoardVO vo,HttpSession session){
		vo.setM_id((String)session.getAttribute("m_id"));
		vo.setName((String)session.getAttribute("m_name"));
		dao.freeBoardInsert(vo);
		return "redirect:freeBoard.do";
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가 
	@RequestMapping("freeBoardReply.do")
	public String freeBoardReply(int no,int page,Model model){
		model.addAttribute("no",no);
		model.addAttribute("page",page);
		return "freeBoard/reply";
	}
	
	@RequestMapping("freeBoardReply_ok.do")
	public String freeBoardReply_ok(FreeBoardVO vo,HttpSession session){
		vo.setM_id((String)session.getAttribute("m_id"));
		vo.setName((String)session.getAttribute("m_name"));
		dao.freeBoardReplyInsert(vo);
		return "redirect:freeBoard.do?page="+vo.getPage();
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//	게시판&답글 업데이트
	@RequestMapping("freeBoardUpdate.do")
	public String freeBoardUpdate(int no,int page,Model model){
		FreeBoardVO vo=dao.freeBoardUpdateValue(no);
		model.addAttribute("vo", vo);
		model.addAttribute("page",page);
		return "freeBoard/update";
	}

	@RequestMapping("freeBoardUpdate_ok.do")
	public String update_ok(FreeBoardVO vo,int page,Model model){
		dao.freeBoardUpdate(vo);
		model.addAttribute("page",page);
		return "redirect:freeBoardContent.do?page="+vo.getPage()+"&no="+vo.getNo();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	@RequestMapping("freeBoardDelete.do")
	public void freeBoardDelete(String no,Model model)	{
		dao.freeBoardDelete(Integer.parseInt(no));
	}

	

	/////////////////////////////////////////////////////////////////////////////////////////////////////	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// content 댓글 추가
	@ResponseBody	
	@RequestMapping("freeCommentInsert.do")
	public String freeCommentInsert(FreeBoardCommentVO vo,HttpSession session) {
		vo.setM_id((String)session.getAttribute("m_id"));
		vo.setName((String)session.getAttribute("m_name"));
		dao.freeCommentInsert(vo);
		return "<script>location.reload();</script>";
	}
	
	// content 댓글의 댓글 추가
	@ResponseBody
	@RequestMapping("freeCommentNewInsert.do")
	public String freeCommentNewInsert(FreeBoardCommentVO vo,HttpSession session) {
		vo.setM_id((String)session.getAttribute("m_id"));
		vo.setName((String)session.getAttribute("m_name"));
		dao.freeCommentNweInsert(vo);
		return "<script>location.reload();</script>";
	}

	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	//	Content&댓글 업데이트
	@ResponseBody
	@RequestMapping("freeCommentUpdate.do")
	public String freeCommentUpdate(FreeBoardCommentVO vo,Model model){
		dao.freeCommentUpdate(vo);
		return "<script>alert(\"수정 되었습니다.\");location.reload();</script>";
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// content 댓글 삭제
	@ResponseBody
	@RequestMapping("freeCommentDelete.do")
	public String freeCommentDelete(FreeBoardCommentVO vo,Model model){
		dao.freeCommentDelete(vo);
		return "<script>alert(\"삭제 되었습니다.\");location.reload();</script>";
	}
}








