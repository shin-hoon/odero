package com.sist.masterBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.masterBoard.dao.MasterBoardDAO;
import com.sist.masterBoard.dao.NoticeVO;
import com.sist.masterBoard.dao.ReplyVO;

@Controller
public class MasterBoardController {
	@Autowired
	private MasterBoardDAO dao;

	// 일반 게시판
	@RequestMapping("MasterBoard.do")
	public String MasterBoardList(String page, Model model){
		if(page==null) page = "1";

		int curpage = Integer.parseInt(page);
		int rowSize = 10;
		int start = (rowSize*curpage)-(rowSize-1);
		int end = rowSize*curpage;

		Map map = new HashMap();

		map.put("start", start);
		map.put("end", end);

		
		List<NoticeVO> list=dao.MasterBoardList(map);
		for(NoticeVO vo:list){
			vo.setCount(dao.contentReplyCount(vo.getNo()));
		}
		
		int totalpage = dao.MasterBoardToltalPage();
		int count = dao.MasterBoardRowCount();
		count = count-((curpage*10)-10);
		model.addAttribute("list",list);
		model.addAttribute("curpage",curpage);
		model.addAttribute("totalpage",totalpage);
		model.addAttribute("count",count);

		return "masterBoard/list";
	}
	
	@RequestMapping("MasterBoardInsert.do")
	public String MasterBoardInsert(){
		return "masterBoard/insert";
	}

	@RequestMapping("MasterBoardInsert_ok.do")
	public String MasterBoardInsert_ok(NoticeVO vo){
		dao.MasterBoardInsert(vo);
		return "redirect:MasterBoard.do";
	}
	
	@RequestMapping("MasterBoardContent.do")
	public String MasterBoardContent(int no,int page,Model model){
		NoticeVO vo = dao.MasterBoardContent(no);
		List<ReplyVO> list=dao.ContentReplyList(no);
		
		model.addAttribute("vo",vo);
		model.addAttribute("list", list);
		model.addAttribute("page",page);
		
		return "masterBoard/content";
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가 
	@RequestMapping("MasterBoardReply.do")
	public String MasterBoardReply(int page,int no,Model model){
		model.addAttribute("page",page);
		model.addAttribute("no",no);
		return "masterBoard/reply";
	}
	
	@RequestMapping("MasterBoardReply_ok.do")
	public String MasterBoardReply_ok(NoticeVO vo){
		dao.MasterBoardReplyInsert(vo);
		return "redirect:MasterBoard.do?page="+vo.getPage();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//	게시판&답글 업데이트
	@RequestMapping("MasterBoardUpdate.do")
	public String MasterBoardUpdate(int no,int page,Model model){
		NoticeVO vo=dao.MasterBoardContent(no);
		model.addAttribute("vo", vo);
		model.addAttribute("page",page);

		return "masterBoard/update";
	}

	@RequestMapping("MasterBoardUpdate_ok.do")
	public String update_ok(NoticeVO vo,int page,Model model){
		boolean bCheck=dao.MasterBoardUpdate_ok(vo);
		model.addAttribute("page",page);
		model.addAttribute("no",vo.getNo());
		model.addAttribute("bCheck",bCheck);

		return "masterBoard/update_ok";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	@RequestMapping("MasterBoardDelete.do")
	public String MasterBoardDelete(int no,Model model)	{
		model.addAttribute("no", no);
		return "masterBoard/delete";
	}


	@RequestMapping("MasterBoardDelete_ok.do")
	public String board_delete_ok(int no,String pwd,Model model){
		boolean bCheck=dao.MasterBoardDelete_ok(no, pwd);
		model.addAttribute("bCheck",bCheck);
		return "masterBoard/delete_ok";
	}

	
	
	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글 추가
	
	@RequestMapping("contentReplyInsert.do")
	public String contentReplyInsert(int page,ReplyVO vo) {
		dao.contentReplyInsert(vo);
		return "redirect:MasterBoardContent.do?no="+vo.getBno()+"&page="+page;
	}
	
	@RequestMapping("contentReplyNewInsert.do")
	public String contentReplyNewInsert(int page,ReplyVO vo) {
		dao.contentReplyInsert(vo);
		return "redirect:MasterBoardContent.do?no="+vo.getBno()+"&page="+page;
	}

}
























