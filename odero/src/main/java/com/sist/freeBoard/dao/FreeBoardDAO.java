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
	public FreeBoardVO freeBoardUpdate(int no){
		return mapper.freeBoardContent(no);
	}
	
	@Transactional
	public boolean freeBoardUpdate_ok(FreeBoardVO vo){
		boolean bCheck=false;
		String pwd=mapper.boardGetPwd(vo.getNo());

		if(pwd.equals(vo.getPwd())) {
			bCheck=true;
			mapper.freeBoardUpdate(vo);
		}

		return bCheck;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	@Transactional
	public boolean freeBoardDelete_ok(int no,String pwd)  {
		boolean bCheck=false;
		FreeBoardVO getVO = mapper.pwd_root_depth(no);
		
		
		if(getVO.getPwd().equals(pwd))  {
			bCheck=true;
		
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
		return bCheck;
	}

	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 댓글 카운트
	public int contentCommentCount(int no) {
		return mapper.contentCommentCount(no);
	}
	
	
	//  Content 댓글 리스트
	public List<FreeBoardCommentVO> contentCommentList(int no){
		return mapper.contentCommentList(no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글 추가
	public void contentCommentInsert(FreeBoardCommentVO vo){
		mapper.contentCommentInsert(vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글의 댓글 추가
	@Transactional
	public void contentCommentNweInsert(FreeBoardCommentVO vo){
		FreeBoardCommentVO getVO = mapper.contentComment_id_step_tab(vo.getNo());

		vo.setGroup_id(getVO.getGroup_id()); 
		vo.setGroup_step(getVO.getGroup_step()); 
		vo.setGroup_tab(getVO.getGroup_tab());
		vo.setPno(vo.getNo());

		mapper.contentComment_groupUpdate(vo);
		mapper.contentCommentNewInsert(vo);
		mapper.contentComment_depthUpdate(vo.getPno());
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
	/*@Transactional
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
	*/
	
	public String contentCommentUpdate(FreeBoardCommentVO vo){
		String data;
		
		String pwd=mapper.contentCommentGetPwd(vo.getNo());

		if(pwd.equals(vo.getPwd())) {
			mapper.contentCommentUpdate(vo);
			data = "<script>alert(\"수정완료\");location.reload();</script>";
					
		}
		else {
			data = "실패";
		}
		return data;
	}
	
	
	@Transactional
	public String contentCommentDelete_ok(FreeBoardCommentVO vo)  {
		String data;
		
		FreeBoardCommentVO getVO = mapper.contentComment_pwd_root_depth(vo.getNo());


		if(getVO.getPwd().equals(vo.getPwd()))  {
			data = "<script>alert(\"삭제되었습니다.\");location.reload();</script>";

			if(getVO.getDepth() == 0) {
				mapper.contentCommentDelete(vo.getNo());
			}
			else {
				mapper.contentComment_delete_msg(vo.getNo());
			}
			
			mapper.contentComment_delete_depth(getVO.getRoot());
		}
		else data = "실패";
		
		return data;
	}
}


