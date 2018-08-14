package com.sist.masterBoard.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface MasterBoardMapper {
	
	// 일반적인 게시판
	@Select("SELECT no,subject,name,regdate,hit,group_tab,num "
			+"FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
			+"FROM (SELECT no,subject,name,regdate,hit,group_tab "
			+"FROM masterNotice ORDER BY group_id DESC,group_step ASC)) "
			+"WHERE num BETWEEN #{start} AND #{end}")
	public List<NoticeVO> MasterBoardList(Map map);
	
	@Select("SELECT CEIL(COUNT(*)/10) FROM masterNotice")
	public int MasterBoardToltalPage();
	
	@Select("SELECT COUNT(*) FROM masterNotice")
	public int MasterBoardRowCount();
	
	
	@SelectKey(keyProperty="no",resultType=int.class,before=true,statement="SELECT NVL(MAX(no)+1,1) as no FROM masterNotice")
	@Insert("INSERT INTO masterNotice(no,name,subject,content,pwd,regdate,hit,group_id) "
			+"VALUES(#{no},#{name},#{subject},#{content},#{pwd},SYSDATE,0,(SELECT NVL(MAX(group_id)+1,1) FROM masterNotice))")
	public void MasterBoardInsert(NoticeVO vo);
	

	@Update("UPDATE masterNotice SET hit=hit+1 WHERE no=#{no}")
	public void MasterBoardHit(int no);
	
	@Select("SELECT no,name,subject,content,regdate FROM masterNotice WHERE no=#{no}")
	public NoticeVO MasterBoardContent(int no);
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 추가
	@Select("SELECT group_id,group_step,group_tab FROM masterNotice WHERE no=#{no}")
	public NoticeVO id_step_tab(int no);
	
	@Update("UPDATE masterNotice SET group_step=group_step+1 WHERE group_id=#{group_id} AND group_step>#{group_step}")
	public void groupUpdate(NoticeVO vo);
	
	@SelectKey(keyProperty="no",resultType=int.class,before=true,statement="SELECT NVL(MAX(no)+1,1) as no FROM masterNotice")
	@Insert("INSERT INTO masterNotice(no,name,subject,content,pwd,regdate,hit,group_id,group_step,group_tab,root) "
			+ "VALUES(#{no},#{name},#{subject},#{content},#{pwd},SYSDATE,0,#{group_id},#{group_step}+1,#{group_tab}+1,#{pno}) ")
	public void MasterBoardReplyInsert(NoticeVO vo);

	@Update("UPDATE masterNotice SET depth=depth+1 WHERE no=#{pno}")
	public void depthUpdate(int pno);

	/////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// 비밀번호 get
	@Select("SELECT pwd FROM masterNotice WHERE no=#{no}")
	public String boardGetPwd(int no);

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 게시판&답글 업데이트 
	@Update("UPDATE masterNotice SET name=#{name},subject=#{subject},content=#{content} WHERE no=#{no}")
	public void MasterBoardUpdate(NoticeVO vo);

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 답글 삭제
	
	@Select("SELECT pwd,root,depth FROM MasterNotice WHERE no=#{no}")
	public NoticeVO pwd_root_depth(int no);
	
	@Delete("DELETE FROM masterNotice WHERE no=#{no}")
	public void MasterBoardDelete(int no);
	
	@Update("UPDATE masterNotice SET subject='삭제된 게시물 입니다.',content='삭제된 게시물 입니다.' WHERE no=#{no}")
	public void delete_msg(int no);

	@Update("UPDATE masterNotice SET depth=depth-1 WHERE no=#{root}")
	public void delete_depth(int root);
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	// Content 댓글 추가
	// to_date(meet_start, 'YYYY-MM-DD HH24:MI') meet_start
	//TO_CHAR(to_date(meet_start, 'YYYY-MM-DD HH24:MI'),'YY-MM-DD')
    @Select("SELECT no,bno,name,msg,pwd,regdate,group_tab,num "
			+"FROM (SELECT no,bno,name,msg,pwd,regdate,group_tab,rownum as num "
			+"FROM (SELECT no,bno,name,msg,pwd,regdate,group_tab "
			+"FROM masterReply WHERE bno=#{bno} ORDER BY group_id DESC,group_step ASC)) ")
	public List<ReplyVO> ContentReplyList(int bno);
    
    
	@SelectKey(keyProperty="no",resultType=int.class,before=true,statement="SELECT NVL(MAX(no)+1,1) as no FROM masterReply")
	@Insert("INSERT INTO masterReply(no,bno,name,msg,pwd,regdate,group_id) "
			+"VALUES(#{no},#{bno},#{name},#{msg},#{pwd},SYSDATE,(SELECT NVL(MAX(group_id)+1,1) FROM masterReply))")
	public void contentReplyInsert(ReplyVO vo);
    
    
	@Select("SELECT COUNT(*) FROM masterReply WHERE bno=#{no}")
	public int contentReplyCount(int no);
	    
}




























