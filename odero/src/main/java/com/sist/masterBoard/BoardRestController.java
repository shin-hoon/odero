package com.sist.masterBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.masterBoard.dao.MasterBoardMapper;
import com.sist.masterBoard.dao.ReplyVO;

@RestController
public class BoardRestController {
	@Autowired
	private  MasterBoardMapper mapper;
	
	@RequestMapping("contentReplyUpdate.do")
	public String contentReplyUpdate(ReplyVO vo){
		String data;
		
		String pwd=mapper.contentReplyGetPwd(vo.getNo());

		if(pwd.equals(vo.getPwd())) {
			mapper.contentReplyUpdate(vo);
			data = "<script>alert(\"수정완료\");location.reload();</script>";
					
		}
		else {
			data = "실패";
		}
		return data;
	}
}
