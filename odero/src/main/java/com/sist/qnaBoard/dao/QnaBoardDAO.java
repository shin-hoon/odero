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
	public int qnaCommentCount(int no) {
		return sst.selectOne("qnaCommentCount",no);
	}
	
	
	//  Content 댓글 리스트
	public List<QnaBoardCommentVO> qnaCommentList(int no){
		return sst.selectList("qnaCommentList",no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글 추가
	public void qnaCommentInsert(QnaBoardCommentVO vo){
		sst.insert("qnaCommentInsert",vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글의 댓글 추가
	@Transactional
	public void qnaCommentNweInsert(QnaBoardCommentVO vo){
		QnaBoardCommentVO getVO = sst.selectOne("qnaComment_id_step_tab",vo.getNo());

		vo.setGroup_id(getVO.getGroup_id()); 
		vo.setGroup_step(getVO.getGroup_step()); 
		vo.setGroup_tab(getVO.getGroup_tab());
		vo.setPno(vo.getNo());

		sst.update("qnaComment_groupUpdate",vo);
		sst.insert("qnaCommentNewInsert",vo);
		sst.update("qnaComment_depthUpdate",vo.getPno());
	}



	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 게시판&답글 업데이트 
	public String qnaCommentUpdate(QnaBoardCommentVO vo){
		String data;
		String pwd = sst.selectOne("qnaCommentGetPwd",vo.getNo());
		
		if(pwd.equals(vo.getPwd())) {
			sst.update("qnaCommentUpdate",vo);
			data = "<script>alert(\"수정완료\");location.reload();</script>";
		}
		else data = "실패";
		
		return data;
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 답글 삭제
	@Transactional
	public String qnaCommentDelete_ok(QnaBoardCommentVO vo)  {
		String data;
		QnaBoardCommentVO getVO = sst.selectOne("qnaComment_pwd_root_depth",vo.getNo());


		if(getVO.getPwd().equals(vo.getPwd()))  {
			data = "<script>alert(\"삭제되었습니다.\");location.reload();</script>";

			if(getVO.getDepth() == 0) {
				sst.delete("qnaCommentDelete",vo.getNo());
			}
			else {
				sst.update("qnaComment_delete_msg",vo.getNo());
			}
			
			sst.delete("qnaComment_delete_depth",getVO.getRoot());
		}
		else data = "실패";
		
		return data;
	}
}