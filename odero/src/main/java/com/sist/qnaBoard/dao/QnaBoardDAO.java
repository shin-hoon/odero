package com.sist.qnaBoard.dao;

import java.util.HashMap;
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
		sst.selectList("qnaBoardList",map);
		return (List<QnaBoardVO>)map.get("qnaBoardList");
	}
	public int qnaBoardToltalPage(){
		Map countMap = new HashMap();
		countMap.put("who","total");
		countMap.put("no","");
		sst.selectOne("qnaBoardCount",countMap);
		
		return Integer.parseInt(countMap.get("count").toString());
	}
	
	public int qnaBoardRowCount(){
		Map countMap = new HashMap();
		countMap.put("who", "row");
		countMap.put("no","");
		sst.selectOne("qnaBoardCount",countMap);
		
		return Integer.parseInt(countMap.get("count").toString());
	}
	
	// 댓글 카운트
	public int qnaCommentCount(int no) {
		Map countMap = new HashMap();
		countMap.put("who", "comment");
		countMap.put("no", no);
		sst.selectOne("qnaBoardCount",countMap);
		return Integer.parseInt(countMap.get("count").toString());
	}
		
	public void qnaBoardInsert(QnaBoardVO vo){
		sst.insert("qnaBoardInsert",vo);
	}

	public QnaBoardVO qnaBoardContent(int no)	{
		QnaBoardVO vo = new QnaBoardVO();
		vo.setNo(no);
		sst.selectOne("qnaBoardContent", vo);
		return vo;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가
	@Transactional
	public void qnaBoardReplyInsert(QnaBoardVO vo){
		sst.insert("qnaBoardReplyInsert",vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 게시판&답글 업데이트 
	public QnaBoardVO qnaBoardUpdateValue(int no){
		QnaBoardVO vo = new QnaBoardVO();
		vo.setNo(no);
		vo.setCount(1);
		sst.selectOne("qnaBoardContent",vo);
		return vo;
	}
	
	@Transactional
	public void qnaBoardUpdate(QnaBoardVO vo){
		sst.update("qnaBoardUpdate",vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	@Transactional
	public void qnaBoardDelete(int no)  {
		sst.delete("qnaBoardDelete",no);
	}

	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//  Content 댓글 리스트
	public List<QnaBoardCommentVO> qnaCommentList(int no){
		Map map = new HashMap();
		map.put("bno", no);
		sst.selectList("qnaCommentList",map);
		return (List<QnaBoardCommentVO>)map.get("qnaCommentList");
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글 추가
	public void qnaCommentInsert(QnaBoardCommentVO vo){
		sst.insert("qnaCommentInsert",vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글의 댓글 추가
	@Transactional
	public void qnaCommentNewInsert(QnaBoardCommentVO vo){
		sst.insert("qnaCommentNewInsert",vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 게시판&답글 업데이트 
	public void qnaCommentUpdate(QnaBoardCommentVO vo){
		sst.update("qnaCommentUpdate",vo);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// 답글 삭제
	@Transactional
	public void qnaCommentDelete(int no)  {
			sst.delete("qnaCommentDelete",no);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<QnaBoardVO> qnaBoardViewList(Map map){
		sst.selectList("qnaBoardViewList",map);
		return (List<QnaBoardVO>)map.get("qnaBoardViewList");
	}
	
	public List<QnaBoardCommentVO> qnaBoardCommentViewList(Map map){
		sst.selectList("qnaBoardCommentViewList",map);
		return (List<QnaBoardCommentVO>)map.get("qnaBoardCommentViewList");
	}
	
	
}






