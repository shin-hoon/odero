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
	
	// 댓글 추가
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
	
	// 게시판&댓글 업데이트 
	@Update("UPDATE masterNotice SET name=#{name},subject=#{subject},content=#{content} WHERE no=#{no}")
	public void MasterBoardUpdate(NoticeVO vo);

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 댓글 삭제
	
	@Select("SELECT pwd,root,depth FROM MasterNotice WHERE no=#{no}")
	public NoticeVO pwd_root_depth(int no);
	
	@Delete("DELETE FROM masterNotice WHERE no=#{no}")
	public void MasterBoardDelete(int no);
	
	@Update("UPDATE masterNotice SET subject='삭제된 게시물 입니다.',content='삭제된 게시물 입니다.' WHERE no=#{no}")
	public void delete_msg(int no);

	@Update("UPDATE masterNotice SET depth=depth-1 WHERE no=#{root}")
	public void delete_depth(int root);
	
	
	
	
	
	
	
	
	
	
	
	
	
    @Select("SELECT no,name,msg,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') as dbday,TO_CHAR(regdate,'YYYY-MM-DD') as dbday2,group_tab,num "
			+"FROM (SELECT no,name,msg,regdate,group_tab,rownum as num "
			+"FROM (SELECT no,name,msg,regdate,group_tab "
			+"FROM masterReply WHERE bno=#{bno} ORDER BY group_id DESC,group_step ASC)) "
			+"WHERE num BETWEEN #{start} AND #{end}")
	public List<ReplyVO> replyListData(Map map);
    

	@SelectKey(keyProperty="no",resultType=int.class,before=true,statement="SELECT NVL(MAX(no)+1,1) as no FROM masterReply")
	@Insert("INSERT INTO masterReply(no,bno,id,name,msg,group_id) "
			+"VALUES(#{no},#{bno},#{name},#{msg},"
			+"(SELECT NVL(MAX(group_id)+1,1) FROM masterReply))")
	public void replyNewInsert(Map map);

	@Select("SELECT group_id,group_step,group_tab "
			+"FROM masterReply "
			+" WHERE no=#{no} ")
	public ReplyVO replyGetParentInfo(int no);

	@Update("UPDATE masterReply SET "
			+"group_step=group_step+1 "
			+"WHERE group_id=#{group_id} AND group_step>#{group_step}")
	public void replyStepIncrement(ReplyVO vo);
	
	
	@SelectKey(keyProperty="no",resultType=int.class,before=true,statement="SELECT NVL(MAX(no)+1,1) as no FROM masterReply")
	@Insert("INSERT INTO masterReply VALUES(#{no},#{bno},#{id}, "
			+"#{name},#{msg},SYSDATE,#{group_id},"
			+"#{group_step},#{group_tab},#{root},0)")
	public void replyRepyInsert(ReplyVO vo);
	
	@Update("UPDATE masterReply SET  "
			+"depth=depth+1 "
			+"WHERE no=#{no}")
	public void replyDepthIncrement(int no);

	
	
	
}




























