package com.sist.qnaBoard.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QnaBoardDAO {
	@Autowired
	private SqlSessionTemplate sst;
	// 일반 게시판
		public List<QnaBoardVO> qnaBoardList(Map map){
			return sst.selectList("qnaBoardList",map);
		}
}
