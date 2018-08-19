package com.sist.masterBoard.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sist.masterBoard.dao.*;

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
	public boolean MasterBoardDelete_ok(int no,String pwd)  {
		boolean bCheck=false;
		NoticeVO getVO = mapper.pwd_root_depth(no);
		
		
		if(getVO.getPwd().equals(pwd))  {
			bCheck=true;
		
			if(getVO.getDepth() == 0) {
				mapper.MasterBoardDelete(no);
			}
			else {
				mapper.delete_msg(no);
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
	public List<ReplyVO> ContentReplyList(int no){
		return mapper.ContentReplyList(no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글 추가
	public void contentReplyInsert(ReplyVO vo){
		mapper.contentReplyInsert(vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가
		public void contentReplyNweInsert(ReplyVO vo){
			ReplyVO getVO = mapper.contentReply_id_step_tab(vo.getNo());
			
			vo.setGroup_id(getVO.getGroup_id()); 
			vo.setGroup_step(getVO.getGroup_step()); 
			vo.setGroup_tab(getVO.getGroup_tab());
			vo.setPno(vo.getNo());

			mapper.contentReply_groupUpdate(vo);
			mapper.contentReplyNewInsert(vo);
			mapper.contentReply_depthUpdate(vo.getNo());
		}


	
	
	
}
