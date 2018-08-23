package com.sist.masterBoard.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@Repository
public class MasterBoardDAO {
	@Autowired
	private  MasterBoardMapper mapper;

	// 일반 게시판
	public List<NoticeVO> MasterBoardList(Map map){
		return mapper.MasterBoardList(map);
	}
	public int MasterBoardToltalPage(){
		return mapper.MasterBoardToltalPage();
	}
	public int MasterBoardRowCount(){
		return mapper.MasterBoardRowCount();
	}
	public void MasterBoardInsert(NoticeVO vo){
		mapper.MasterBoardInsert(vo);
	}

	public NoticeVO MasterBoardContent(int no)	{
		mapper.MasterBoardHit(no);
		return mapper.MasterBoardContent(no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가
	@Transactional
	public void MasterBoardReplyInsert(NoticeVO vo){
		NoticeVO getVO = mapper.id_step_tab(vo.getNo());
		
		vo.setGroup_id(getVO.getGroup_id()); 
		vo.setGroup_step(getVO.getGroup_step()); 
		vo.setGroup_tab(getVO.getGroup_tab());
		vo.setPno(vo.getNo());
		
		mapper.groupUpdate(vo);
		mapper.MasterBoardReplyInsert(vo);
		mapper.depthUpdate(vo.getPno());
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 게시판&답글 업데이트 
	public NoticeVO MasterBoardUpdate(int no){
		return mapper.MasterBoardContent(no);
	}
	
	@Transactional
	public boolean MasterBoardUpdate_ok(NoticeVO vo){
		boolean bCheck=false;
		String pwd=mapper.boardGetPwd(vo.getNo());

		if(pwd.equals(vo.getPwd())) {
			bCheck=true;
			mapper.MasterBoardUpdate(vo);
		}

		return bCheck;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	@Transactional
	public boolean MasterBoardDelete_ok(int no,String pwd)  {
		boolean bCheck=false;
		NoticeVO getVO = mapper.pwd_root_depth(no);
		
		
		if(getVO.getPwd().equals(pwd))  {
			bCheck=true;
		
			if(getVO.getDepth() == 0) {
				mapper.MasterBoardDeleteReply(no);
				mapper.MasterBoardDelete(no);
			}
			else {
				mapper.delete_msg(no);
				mapper.MasterBoardDeleteReply(no);
			}
			
			mapper.delete_depth(getVO.getRoot());
			
		}
		return bCheck;
	}

	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 댓글 카운트
	public int contentReplyCount(int no) {
		return mapper.contentReplyCount(no);
	}
	
	
	//  Content 댓글 리스트
	public List<ReplyVO> contentReplyList(int no){
		return mapper.contentReplyList(no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글 추가
	public void contentReplyInsert(ReplyVO vo){
		mapper.contentReplyInsert(vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글의 댓글 추가
	@Transactional
	public void contentReplyNweInsert(ReplyVO vo){
		ReplyVO getVO = mapper.contentReply_id_step_tab(vo.getNo());

		vo.setGroup_id(getVO.getGroup_id()); 
		vo.setGroup_step(getVO.getGroup_step()); 
		vo.setGroup_tab(getVO.getGroup_tab());
		vo.setPno(vo.getNo());

		mapper.contentReply_groupUpdate(vo);
		mapper.contentReplyNewInsert(vo);
		mapper.contentReply_depthUpdate(vo.getPno());
	}



	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 게시판&답글 업데이트 
		
/*	public boolean contentReplyUpdate_ok(ReplyVO vo){
		boolean bCheck=false;
		String pwd=mapper.contentReplyGetPwd(vo.getNo());

		if(pwd.equals(vo.getPwd())) {
			bCheck=true;
			mapper.contentReplyUpdate(vo);
		}

		return bCheck;
	}*/
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 답글 삭제
	@Transactional
	public boolean contentReplyDelete_ok(ReplyVO vo)  {
		boolean bCheck=false;
		ReplyVO getVO = mapper.contentReply_pwd_root_depth(vo.getNo());


		if(getVO.getPwd().equals(vo.getPwd()))  {
			bCheck=true;

			if(getVO.getDepth() == 0) {
				mapper.contentReplyDelete(vo.getNo());
			}
			else {
				mapper.contentReply_delete_msg(vo.getNo());
			}
			
			mapper.contentReply_delete_depth(getVO.getRoot());
		}
			return bCheck;
	}
	
	
}


