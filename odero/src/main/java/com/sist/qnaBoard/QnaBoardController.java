package com.sist.qnaBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.qnaBoard.dao.QnaBoardCommentVO;
import com.sist.qnaBoard.dao.QnaBoardVO;
import com.sist.qnaBoard.dao.QnaBoardDAO;
import com.sist.qnaBoard.dao.QnaBoardVO;

@Controller
public class QnaBoardController {
	@Autowired
	private QnaBoardDAO dao;
	
	@RequestMapping("qnaBoard.do")
	public String qnaBoardList(String page,Model model) {
		if(page==null) page = "1";

		int curpage = Integer.parseInt(page);
		int rowSize = 10;
		int start = (rowSize*curpage)-(rowSize-1);
		int end = rowSize*curpage;

		Map map = new HashMap();

		map.put("start", start);
		map.put("end", end);

		
		List<QnaBoardVO> list=dao.qnaBoardList(map);
		
		for(QnaBoardVO vo:list){
			vo.setCount(dao.contentCommentCount(vo.getNo()));
			
			int length = vo.getSubject().length();
			
			if(length >=20)
				vo.setSubject(vo.getSubject().substring(0,15)+"···");
		}
		
		
		
		int totalpage = dao.qnaBoardToltalPage();
		int count = dao.qnaBoardRowCount();
		count = count-((curpage*10)-10);
		model.addAttribute("list",list);
		model.addAttribute("curpage",curpage);
		model.addAttribute("totalpage",totalpage);
		model.addAttribute("count",count);

		
		return "qnaBoard/list";
	}
	
	
	@RequestMapping("qnaBoardInsert.do")
	public String qnaBoardInsert(){
		return "qnaBoard/insert";
	}

	@RequestMapping("qnaBoardInsert_ok.do")
	public String qnaBoardInsert_ok(QnaBoardVO vo){
		dao.qnaBoardInsert(vo);
		return "redirect:qnaBoard.do";
	}
	
	@RequestMapping("qnaBoardContent.do")
	public String qnaBoardContent(int no,int page,Model model){
		QnaBoardVO vo = dao.qnaBoardContent(no);
		List<QnaBoardCommentVO> list=dao.contentCommentList(no);
		
		model.addAttribute("vo",vo);
		model.addAttribute("list", list);
		model.addAttribute("page",page);
		
		return "qnaBoard/content";
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가 
	@RequestMapping("qnaBoardReply.do")
	public String qnaBoardReply(int page,int no,Model model){
		model.addAttribute("page",page);
		model.addAttribute("no",no);
		return "qnaBoard/reply";
	}
	
	@RequestMapping("qnaBoardReply_ok.do")
	public String qnaBoardReply_ok(QnaBoardVO vo){
		dao.qnaBoardReplyInsert(vo);
		return "redirect:qnaBoard.do?page="+vo.getPage();
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//	게시판&답글 업데이트
	@RequestMapping("qnaBoardUpdate.do")
	public String qnaBoardUpdate(int no,int page,Model model){
		QnaBoardVO vo=dao.qnaBoardContent(no);
		model.addAttribute("vo", vo);
		model.addAttribute("page",page);

		return "qnaBoard/update";
	}

	@RequestMapping("qnaBoardUpdate_ok.do")
	public String update_ok(QnaBoardVO vo,int page,Model model){
		boolean bCheck=dao.qnaBoardUpdate_ok(vo);
		model.addAttribute("page",page);
		model.addAttribute("no",vo.getNo());
		model.addAttribute("bCheck",bCheck);

		return "qnaBoard/update_ok";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	@RequestMapping("qnaBoardDelete.do")
	public String qnaBoardDelete(int no,Model model)	{
		model.addAttribute("no", no);
		return "qnaBoard/delete";
	}


	@RequestMapping("qnaBoardDelete_ok.do")
	public String board_delete_ok(int no,String pwd,Model model){
		boolean bCheck=dao.qnaBoardDelete_ok(no, pwd);
		model.addAttribute("bCheck",bCheck);
		return "qnaBoard/delete_ok";
	}

	
	
	
	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// content 댓글 추가
	
	@RequestMapping("qnaContentCommentInsert.do")
	public String contentCommentInsert(QnaBoardCommentVO vo) {
		dao.contentCommentInsert(vo);
		return "redirect:qnaBoardContent.do?no="+vo.getBno()+"&page="+vo.getPage();
	}
	@RequestMapping("qnaContentCommentNewInsert.do")
	public String contentCommentNewInsert(QnaBoardCommentVO vo) {
		dao.contentCommentNweInsert(vo);
		return "redirect:qnaBoardContent.do?no="+vo.getBno()+"&page="+vo.getPage();
	}

	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	//	Content&댓글 업데이트
	@ResponseBody
	@RequestMapping("qnaContentCommentUpdate.do")
	public String contentCommentUpdate_ok(QnaBoardCommentVO vo,Model model){
		String data = dao.contentCommentUpdate(vo);
		return data;
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// content 댓글 삭제
	@ResponseBody
	@RequestMapping("qnaContentCommentDelete_ok.do")
	public String ContentCommentDelete_ok(QnaBoardCommentVO vo,Model model){
		String data = dao.contentCommentDelete_ok(vo);
		return data;
	}
}

