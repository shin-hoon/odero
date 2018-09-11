package com.sist.qnaBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.qnaBoard.dao.QnaBoardCommentVO;
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
			vo.setCount(dao.qnaCommentCount(vo.getNo()));
			
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
	
	
	@RequestMapping("qnaBoardContent.do")
	public String qnaBoardContent(int no,int page,Model model){
		QnaBoardVO vo = dao.qnaBoardContent(no);
		List<QnaBoardCommentVO> list=dao.qnaCommentList(no);
		
		model.addAttribute("vo",vo);
		model.addAttribute("list", list);
		model.addAttribute("page",page);
		
		return "qnaBoard/content";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 게시글 추가
	@RequestMapping("qnaBoardInsert.do")
	public String qnaBoardInsert(){
		return "qnaBoard/insert";
	}

	@RequestMapping("qnaBoardInsert_ok.do")
	public String qnaBoardInsert_ok(QnaBoardVO vo,HttpSession session){
		vo.setM_id((String)session.getAttribute("m_id"));
		vo.setName((String)session.getAttribute("m_name"));
		dao.qnaBoardInsert(vo);
		return "redirect:qnaBoard.do";
	}
	

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가 
	@RequestMapping("qnaBoardReply.do")
	public String qnaBoardReply(int no,int page,Model model){
		model.addAttribute("no",no);
		model.addAttribute("page",page);
		return "qnaBoard/reply";
	}
	
	@RequestMapping("qnaBoardReply_ok.do")
	public String qnaBoardReply_ok(QnaBoardVO vo,HttpSession session){
		vo.setM_id((String)session.getAttribute("m_id"));
		vo.setName((String)session.getAttribute("m_name"));
		dao.qnaBoardReplyInsert(vo);
		return "redirect:qnaBoard.do?page="+vo.getPage();
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//	게시판&답글 업데이트
	@RequestMapping("qnaBoardUpdate.do")
	public String qnaBoardUpdate(int no,int page,Model model){
		QnaBoardVO vo=dao.qnaBoardUpdateValue(no);
		model.addAttribute("vo", vo);
		model.addAttribute("page",page);
		return "qnaBoard/update";
	}

	@RequestMapping("qnaBoardUpdate_ok.do")
	public String update(QnaBoardVO vo,Model model){
		dao.qnaBoardUpdate(vo);
		model.addAttribute("page",vo.getPage());
		return "redirect:qnaBoardContent.do?page="+vo.getPage()+"&no="+vo.getNo();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	@ResponseBody
	@RequestMapping("qnaBoardDelete.do")
	public void qnaBoardDelete(int no)	{
		dao.qnaBoardDelete(no);
	}

	

	/////////////////////////////////////////////////////////////////////////////////////////////////////	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// content 댓글 추가
	
	@ResponseBody
	@RequestMapping("qnaCommentInsert.do")
	public String qnaCommentInsert(QnaBoardCommentVO vo,HttpSession session) {
		vo.setM_id((String)session.getAttribute("m_id"));
		vo.setName((String)session.getAttribute("m_name"));
		dao.qnaCommentInsert(vo);
		return "<script>location.reload();</script>";
	}
	
	// content 댓글의 댓글 추가
	
	@ResponseBody
	@RequestMapping("qnaCommentNewInsert.do")
	public String qnaCommentNewInsert(QnaBoardCommentVO vo,HttpSession session) {
		vo.setM_id((String)session.getAttribute("m_id"));
		vo.setName((String)session.getAttribute("m_name"));
		dao.qnaCommentNewInsert(vo);
		return "<script>location.reload();</script>";
	}

	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	//	Content&댓글 업데이트
	@ResponseBody
	@RequestMapping("qnaCommentUpdate.do")
	public String qnaCommentUpdate(QnaBoardCommentVO vo,Model model){
		dao.qnaCommentUpdate(vo);
		return "<script>alert(\"수정 되었습니다.\");location.reload();</script>";
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// content 댓글 삭제
	@ResponseBody
	@RequestMapping("qnaCommentDelete.do")
	public String qnaCommentDelete(int no,Model model){
		dao.qnaCommentDelete(no);
		return "<script>alert(\"삭제 되었습니다.\");location.reload();</script>";
	}





	@RequestMapping("qnaBoardView.do")
	public String qnaBoardViewList(String page,Model model) {
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
			vo.setCount(dao.qnaCommentCount(vo.getNo()));

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

		return "qnaBoard/viewList";
	}
}