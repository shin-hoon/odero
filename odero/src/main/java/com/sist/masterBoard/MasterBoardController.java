package com.sist.masterBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

		/*Map map = new HashMap();

		map.put("start", start);
		map.put("end", end);
		map.put("key", "");
*/
		NoticeVO setVO = new NoticeVO();
		
		List<NoticeVO> list=dao.MasterBoardList(setVO);
		
		for(NoticeVO vo:list){
			vo.setCount(dao.contentReplyCount(vo.getNo()));
			
			int length = vo.getSubject().length();
			
			if(length >=20)
				vo.setSubject(vo.getSubject().substring(0,15)+"···");
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
		List<ReplyVO> list=dao.contentReplyList(no);
		
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
	
	// content 댓글 추가
	
	@RequestMapping("contentReplyInsert.do")
	public String contentReplyInsert(ReplyVO vo) {
		dao.contentReplyInsert(vo);
		return "redirect:MasterBoardContent.do?no="+vo.getBno()+"&page="+vo.getPage();
	}
	@RequestMapping("contentReplyNewInsert.do")
	public String contentReplyNewInsert(ReplyVO vo) {
		dao.contentReplyNweInsert(vo);
		return "redirect:MasterBoardContent.do?no="+vo.getBno()+"&page="+vo.getPage();
	}

	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	//	Content&댓글 업데이트
	@ResponseBody
	@RequestMapping("contentReplyUpdate.do")
	public String contentReplyUpdate_ok(ReplyVO vo,Model model){
		String data = dao.contentReplyUpdate(vo);
		/*boolean bCheck=dao.contentReplyUpdate_ok(vo);
		model.addAttribute("page",vo.getPage());
		model.addAttribute("no",vo.getBno());
		model.addAttribute("bCheck",bCheck);
		
		return "masterBoard/contentReplyUpdate_ok";*/
		return data;
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// content 댓글 삭제
	@ResponseBody
	@RequestMapping("contentReplyDelete_ok.do")
	public String ContentReplyDelete_ok(ReplyVO vo,Model model){
		String data = dao.contentReplyDelete_ok(vo);
		/*model.addAttribute("bCheck",bCheck);
		model.addAttribute("no",vo.getBno());
		model.addAttribute("page",vo.getPage());
		return "masterBoard/contentReplyDelete_ok";*/
		return data;
	}
}

