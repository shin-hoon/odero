package com.sist.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
import com.sist.place.dao.*;

@Controller
public class PlaceController {
	@Autowired
	private PlaceDAO dao;
	@Autowired
	private P_ReplyDAO p_dao;
	
	@RequestMapping("p_detail.do")
	public String place_detail(int p_no, Model model) {
		
		PlaceVO vo = dao.placeDetailData(p_no);
		List<P_ReplyVO> r_list = p_dao.p_replyListData(p_no);
		// keyword split
		String str_kw = vo.getP_keyword();
		String[] skeyword = str_kw.split(",");

		// img split
		String str_img = vo.getP_img();
		String[] simg = str_img.split(",");
		
		model.addAttribute("backimg", simg[0]);
		model.addAttribute("r_list", r_list);
		model.addAttribute("skeyword", skeyword);
		model.addAttribute("simg", simg);
		model.addAttribute("vo", vo);
		return "place/p_detail";
	}
	
	// 댓글
	@RequestMapping("p_reply_insert.do")
	public String p_replyInsert() {
		return "place/p_reply";
	}
	@RequestMapping("p_reply_insert_ok.do")
	public String p_replyInsert_ok() {
		return "redirect:p_detail.do";
	}
	 
	@RequestMapping("p_list.do")
	public String placeListData(String page, Model model) {
		
		if(page==null)
			page="1";
		int curpage= Integer.parseInt(page);
		int rowsize=8;
		int start=(rowsize*curpage)-(rowsize-1);
		int end= rowsize * curpage;
		
		Map map=new HashMap();
		map.put("start", start);
		map.put("end", end);
		
		int totalpage = dao.placeTotalList();
		
		List<PlaceVO> list =dao.placeListData(map);
		model.addAttribute("list",list);
		model.addAttribute("curpage",curpage);
		model.addAttribute("totalpage",totalpage);
		return "place/p_list";
	}
	
	@RequestMapping("select.do")
	public String select(SelectVO vo, String play, String food, Model model)
	{
		System.out.println(play);
		System.out.println(food);
		return"place/select";
	}
}