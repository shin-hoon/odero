package com.sist.qnaBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
