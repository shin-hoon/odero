package com.sist.qnaBoard.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class QnaBoardDAO {
	@Autowired
	private SqlSessionTemplate sst;
	
	
	// 일반 게시판
	public List<QnaBoardVO> qnaBoardList(Map map){
		return sst.selectList("qnaBoardList",map);
	}
	
	public int qnaBoardToltalPage(){
		return sst.selectOne("qnaBoardToltalPage");
	}
	public int qnaBoardRowCount(){
		return sst.selectOne("qnaBoardRowCount");
	}
	public void qnaBoardInsert(QnaBoardVO vo){
		sst.insert("qnaBoardInsert",vo);
	}

	public QnaBoardVO qnaBoardContent(int no)	{
		sst.update("qnaBoardHit",no);
		return sst.selectOne("qnaBoardContent", no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가
	@Transactional
	public void qnaBoardReplyInsert(QnaBoardVO vo){
		QnaBoardVO getVO = sst.selectOne("qna_id_step_tab",vo.getNo());
		
		vo.setGroup_id(getVO.getGroup_id()); 
		vo.setGroup_step(getVO.getGroup_step()); 
		vo.setGroup_tab(getVO.getGroup_tab());
		vo.setPno(vo.getNo());
		
		sst.update("qna_groupUpdate",vo);
		sst.insert("qnaBoardReplyInsert",vo);
		sst.update("qna_depthUpdate",vo.getPno());
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 게시판&답글 업데이트 
	public QnaBoardVO qnaBoardUpdate(int no){
		return sst.selectOne("qnaBoardContent",no);
	}
	
	@Transactional
	public boolean qnaBoardUpdate_ok(QnaBoardVO vo){
		boolean bCheck=false;
		String pwd=sst.selectOne("qna_boardGetPwd",vo.getNo());

		if(pwd.equals(vo.getPwd())) {
			bCheck=true;
			sst.update("qnaBoardUpdate",vo);
		}

		return bCheck;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	@Transactional
	public boolean qnaBoardDelete_ok(int no,String pwd)  {
		boolean bCheck=false;
		QnaBoardVO getVO = sst.selectOne("qna_pwd_root_depth",no);
		
		
		if(getVO.getPwd().equals(pwd))  {
			bCheck=true;
		
			if(getVO.getDepth() == 0) {
				sst.delete("qnaBoardDeleteComment",no);
				sst.delete("qnaBoardDelete",no);
			}
			else {
				sst.update("qna_delete_msg",no);
				sst.delete("qnaBoardDeleteComment",no);
			}
			sst.delete("qna_delete_depth",getVO.getRoot());
		}
		return bCheck;
	}

	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 댓글 카운트
	public int qnaContentCommentCount(int no) {
		return sst.selectOne("qnaContentCommentCount",no);
	}
	
	
	//  Content 댓글 리스트
	public List<QnaBoardCommentVO> qnaContentCommentList(int no){
		return sst.selectList("qnaContentCommentList",no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글 추가
	public void qnaContentCommentInsert(QnaBoardCommentVO vo){
		sst.insert("qnaContentCommentInsert",vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글의 댓글 추가
	@Transactional
	public void qnaContentCommentNweInsert(QnaBoardCommentVO vo){
		QnaBoardCommentVO getVO = sst.selectOne("qnaContentComment_id_step_tab",vo.getNo());

		vo.setGroup_id(getVO.getGroup_id()); 
		vo.setGroup_step(getVO.getGroup_step()); 
		vo.setGroup_tab(getVO.getGroup_tab());
		vo.setPno(vo.getNo());

		sst.update("qnaContentComment_groupUpdate",vo);
		sst.insert("qnaContentCommentNewInsert",vo);
		sst.update("qnaContentComment_depthUpdate",vo.getPno());
	}



	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 게시판&답글 업데이트 
	public String qnaContentCommentUpdate(QnaBoardCommentVO vo){
		String data;
		String pwd = sst.selectOne("qnaContentCommentGetPwd",vo.getNo());
		
		if(pwd.equals(vo.getPwd())) {
			sst.update("qnaContentCommentUpdate",vo);
			data = "<script>alert(\"수정완료\");location.reload();</script>";
		}
		else {
			data = "실패";
		}
		return data;
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 답글 삭제
	@Transactional
	public String qnaContentCommentDelete_ok(QnaBoardCommentVO vo)  {
		String data;
		QnaBoardCommentVO getVO = sst.selectOne("qnaContentComment_pwd_root_depth",vo.getNo());


		if(getVO.getPwd().equals(vo.getPwd()))  {
			data = "<script>alert(\"삭제되었습니다.\");location.reload();</script>";

			if(getVO.getDepth() == 0) {
				sst.delete("qnaContentCommentDelete",vo.getNo());
			}
			else {
				sst.update("qnaContentComment_delete_msg",vo.getNo());
			}
			
			sst.delete("qnaContentComment_delete_depth",getVO.getRoot());
		}
		else data = "실패";
		
		return data;
	}
}