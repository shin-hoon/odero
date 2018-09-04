package com.sist.freeBoard.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

public interface FreeBoardMapper {
	
	// 일반적인 게시판
	/*@SelectKey(statementType="CALLABLE" , resultType="")*/
	/*@Select ("{CALL freeBoardList(#{start ,jdbcType=INTEGER,mode=IN}, #{end,jdbcType=INTEGER,mode=IN},#{key, jdbcType=CURSOR, mode=OUT, javaType=java.sql.ResultSet, resultType=NoticeVO} )} ") 
	@Options (statementType = StatementType.CALLABLE) */
	@Select("SELECT no,m_id,subject,name,regdate,hit,group_tab,num "
			+"FROM (SELECT no,m_id,subject,name,regdate,hit,group_tab,rownum as num "
			+"FROM (SELECT no,m_id,subject,name,regdate,hit,group_tab "
			+"FROM freeBoard ORDER BY group_id DESC,group_step ASC)) "
			+"WHERE num BETWEEN #{start} AND #{end}")
	public List<FreeBoardVO> freeBoardList(Map map);
	
	@Select("SELECT CEIL(COUNT(*)/10) FROM freeBoard")
	public int freeBoardToltalPage();
	
	@Select("SELECT COUNT(*) FROM freeBoard")
	public int freeBoardRowCount();


	
	@Update("UPDATE freeBoard SET hit=hit+1 WHERE no=#{no}")
	public void freeBoardHit(int no);
	
	@Select("SELECT no,m_id,name,subject,content,regdate,hit FROM freeBoard WHERE no=#{no}")
	public FreeBoardVO freeBoardContent(int no);
	
	
	
	@SelectKey(keyProperty="no",resultType=int.class,before=true,statement="SELECT NVL(MAX(no)+1,1) as no FROM freeBoard")
	@Insert("INSERT INTO freeBoard(no,m_id,name,subject,content,regdate,hit,group_id) "
			+"VALUES(#{no},#{m_id},#{name},#{subject},#{content},SYSDATE,0,(SELECT NVL(MAX(group_id)+1,1) FROM freeBoard))")
	public void freeBoardInsert(FreeBoardVO vo);
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가
	@Select("SELECT group_id,group_step,group_tab FROM freeBoard WHERE no=#{no}")
	public FreeBoardVO id_step_tab(int no);
	
	@Update("UPDATE freeBoard SET group_step=group_step+1 WHERE group_id=#{group_id} AND group_step>#{group_step}")
	public void groupUpdate(FreeBoardVO vo);
	
	@SelectKey(keyProperty="no",resultType=int.class,before=true,statement="SELECT NVL(MAX(no)+1,1) as no FROM freeBoard")
	@Insert("INSERT INTO freeBoard(no,m_id,name,subject,content,regdate,hit,group_id,group_step,group_tab,root) "
			+ "VALUES(#{no},#{m_id},#{name},#{subject},#{content},SYSDATE,0,#{group_id},#{group_step}+1,#{group_tab}+1,#{pno}) ")
	public void freeBoardReplyInsert(FreeBoardVO vo);

	@Update("UPDATE freeBoard SET depth=depth+1 WHERE no=#{pno}")
	public void depthUpdate(int pno);

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 게시판&답글 업데이트 
	@Update("UPDATE freeBoard SET subject=#{subject},content=#{content} WHERE no=#{no}")
	public void freeBoardUpdate(FreeBoardVO vo);

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	
	@Select("SELECT root,depth FROM freeBoard WHERE no=#{no}")
	public FreeBoardVO root_depth(int no);
	
	@Delete("DELETE FROM freeBoard WHERE no=#{no}")
	public void freeBoardDelete(int no);
	@Delete("DELETE FROM freeBoardComment WHERE bno=#{no}")
	public void freeBoardDeleteComment(int no);
	
	@Update("UPDATE freeBoard SET subject='삭제된 게시물 입니다.',content='삭제된 게시물 입니다.' WHERE no=#{no}")
	public void delete_msg(int no);

	@Update("UPDATE freeBoard SET depth=depth-1 WHERE no=#{root}")
	public void delete_depth(int root);
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 리플 카운트
	@Select("SELECT COUNT(*) FROM freeBoardComment WHERE bno=#{no}")
	public int freeCommentCount(int no);
	
	
	// Content 댓글 리스트
	// to_date(meet_start, 'YYYY-MM-DD HH24:MI') meet_start
	//TO_CHAR(to_date(meet_start, 'YYYY-MM-DD HH24:MI'),'YY-MM-DD')
    @Select("SELECT no,bno,m_id,name,msg,regdate,group_tab,num "
			+"FROM (SELECT no,bno,m_id,name,msg,regdate,group_tab,rownum as num "
			+"FROM (SELECT no,bno,m_id,name,msg,regdate,group_tab "
			+"FROM freeBoardComment WHERE bno=#{bno} ORDER BY group_id ASC,group_step ASC)) ")
	public List<FreeBoardCommentVO> freeCommentList(int bno);
    
    
	@SelectKey(keyProperty="no",resultType=int.class,before=true,statement="SELECT NVL(MAX(no)+1,1) as no FROM freeBoardComment")
	@Insert("INSERT INTO freeBoardComment(no,bno,m_id,name,msg,regdate,group_id) "
			+"VALUES(#{no},#{bno},#{m_id},#{name},#{msg},SYSDATE,(SELECT NVL(MAX(group_id)+1,1) FROM freeBoardComment))")
	public void freeCommentInsert(FreeBoardCommentVO vo);
    
    
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// content 리플 추가
	@Select("SELECT group_id,group_step,group_tab FROM freeBoardComment WHERE no=#{no}")
	public FreeBoardCommentVO freeComment_id_step_tab(int no);
	
	@Update("UPDATE freeBoardComment SET group_step=group_step+1 WHERE group_id=#{group_id} AND group_step>#{group_step}")
	public void freeComment_groupUpdate(FreeBoardCommentVO vo);
	
	@SelectKey(keyProperty="no",resultType=int.class,before=true,statement="SELECT NVL(MAX(no)+1,1) as no FROM freeBoardComment")
	@Insert("INSERT INTO freeBoardComment(no,bno,m_id,name,msg,regdate,group_id,group_step,group_tab,root) "
			+ "VALUES(#{no},#{bno},#{m_id},#{name},#{msg},SYSDATE,#{group_id},#{group_step}+1,#{group_tab}+1,#{pno}) ")
	public void freeCommentNewInsert(FreeBoardCommentVO vo);

	@Update("UPDATE freeBoardComment SET depth=depth+1 WHERE no=#{pno}")
	public void freeComment_depthUpdate(int pno);


	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// content 게시판&답글 업데이트 
	@Update("UPDATE freeBoardComment SET msg=#{msg} WHERE no=#{no}")
	public void freeCommentUpdate(FreeBoardCommentVO vo);
	
	

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// content 댓글삭제
	
	@Select("SELECT root,depth FROM freeBoardComment WHERE no=#{no}")
	public FreeBoardCommentVO freeComment_root_depth(int no);
	
	@Delete("DELETE FROM freeBoardComment WHERE no=#{no}")
	public void freeCommentDelete(int no);
	
	@Update("UPDATE freeBoardComment SET msg='삭제된 게시물 입니다.' WHERE no=#{no}")
	public void freeComment_delete_msg(int no);

	@Update("UPDATE freeBoardComment SET depth=depth-1 WHERE no=#{root}")
	public void freeComment_delete_depth(int root);
}




























