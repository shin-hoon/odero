package com.sist.freeBoard.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@Repository
public class FreeBoardDAO {
	@Autowired
	private  FreeBoardMapper mapper;

	// 일반 게시판
	public List<FreeBoardVO> freeBoardList(Map map){
		return mapper.freeBoardList(map);
	}
	public int freeBoardToltalPage(){
		return mapper.freeBoardToltalPage();
	}
	public int freeBoardRowCount(){
		return mapper.freeBoardRowCount();
	}
	public void freeBoardInsert(FreeBoardVO vo){
		mapper.freeBoardInsert(vo);
	}

	public FreeBoardVO freeBoardContent(int no)	{
		mapper.freeBoardHit(no);
		return mapper.freeBoardContent(no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가
	@Transactional
	public void freeBoardReplyInsert(FreeBoardVO vo){
		FreeBoardVO getVO = mapper.id_step_tab(vo.getNo());
		
		vo.setGroup_id(getVO.getGroup_id()); 
		vo.setGroup_step(getVO.getGroup_step()); 
		vo.setGroup_tab(getVO.getGroup_tab());
		vo.setPno(vo.getNo());
		
		mapper.groupUpdate(vo);
		mapper.freeBoardReplyInsert(vo);
		mapper.depthUpdate(vo.getPno());
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 게시판&답글 업데이트 
	public FreeBoardVO freeBoardUpdateValue(int no){
		return mapper.freeBoardContent(no);
	}
	
	@Transactional
	public void freeBoardUpdate(FreeBoardVO vo){
		mapper.freeBoardUpdate(vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	public void freeBoardDelete(int no)  {
		FreeBoardVO getVO = mapper.root_depth(no);
		
		if(getVO.getDepth() == 0) {
			mapper.freeBoardDeleteComment(no);
			mapper.freeBoardDelete(no);
		}
		else {
			mapper.delete_msg(no);
			mapper.freeBoardDeleteComment(no);
		}

		mapper.delete_depth(getVO.getRoot());
	}

	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 댓글 카운트
	public int freeCommentCount(int no) {
		return mapper.freeCommentCount(no);
	}
	
	
	//  Content 댓글 리스트
	public List<FreeBoardCommentVO> freeCommentList(int no){
		return mapper.freeCommentList(no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글 추가
	public void freeCommentInsert(FreeBoardCommentVO vo){
		mapper.freeCommentInsert(vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글의 댓글 추가
	@Transactional
	public void freeCommentNweInsert(FreeBoardCommentVO vo){
		FreeBoardCommentVO getVO = mapper.freeComment_id_step_tab(vo.getNo());

		vo.setGroup_id(getVO.getGroup_id()); 
		vo.setGroup_step(getVO.getGroup_step()); 
		vo.setGroup_tab(getVO.getGroup_tab());
		vo.setPno(vo.getNo());

		mapper.freeComment_groupUpdate(vo);
		mapper.freeCommentNewInsert(vo);
		mapper.freeComment_depthUpdate(vo.getPno());
	}



	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 게시판 댓글 업데이트
	public void freeCommentUpdate(FreeBoardCommentVO vo){
		mapper.freeCommentUpdate(vo);
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 게시판 댓글 삭제
	public String freeCommentDelete(int no)  {
		FreeBoardCommentVO getVO = mapper.freeComment_root_depth(no);

		if(getVO.getDepth() == 0) {
			mapper.freeCommentDelete(no);
		}
		else {
			mapper.freeComment_delete_msg(no);
		}

		mapper.freeComment_delete_depth(getVO.getRoot());
		
		return "";
	}
}




