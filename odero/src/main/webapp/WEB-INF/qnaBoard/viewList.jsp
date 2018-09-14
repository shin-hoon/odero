<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="qnaBoard/qnaBoard.css" type="text/css">
	<!-- <script type="text/javascript">

	$(function(){
		$('#board').click(function(){
			$('#board_t').show();
			$('#boardReply_t').hide();
			$('#comment_t').hide();
		});
		
		$('#boardReply').click(function(){
			$('#board_t').hide();
			$('#boardReply_t').show();
			$('#comment_t').hide();
		});
		
		$('#comment').click(function(){
			$('#board_t').hide();
			$('#boardReply_t').hide();
			$('#comment_t').show();
		});
		
	
	});


	</script> -->
</head>
<body>
	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<table class="table" width="700">
				<tr style="border-bottom: solid;">
					<td class="text-left" style="border-top:none;">
						<c:if test="${sessionScope.m_id != null}">
							<a class="btn btn-sm btn-primary" href="qnaBoardView.do?who=board" id="board">등록한 게시글</a>
							<a class="btn btn-sm btn-primary" href="qnaBoardView.do?who=boardReply" id="boardReply">댓글 단 게시글</a>
							<a class="btn btn-sm btn-primary" href="qnaBoardView.do?who=comment" id="comment">등록한 댓글</a>
						</c:if>
					</td>
					<td class="text-right" style="border-top:none;">
						<c:if test="${sessionScope.m_id != null}">
							<a class="btn btn-sm btn-primary" href="qnaBoard.do">전체 게시물 보기</a>
						</c:if>
					</td>
				</tr>
			</table>
			
			<c:if test="${who == 'board' }">
			<table class="table table-hover" width="700" style="table-layout: fixed;" id="board_t">
				<tr class="table-th">
					<th class="text-center" width="10%">번호</th>
					<th class="text-center" width="60%">제목</th>
					<th class="text-center" width="20%">작성일</th>
					<th class="text-center" width="10%">조회수</th>
				</tr>
				<c:forEach var="vo" items="${list}">
				<c:if test="${sessionScope.m_id==vo.m_id}">
					<c:if test="${vo.subject != '삭제된 게시물 입니다.'}">
					<tr class="aa">
						<td class="text-center" width="10%">
							${vo.no}
						</td>
						<td class="text-left" width="60%">
							<a href="qnaBoardContent.do?no=${vo.no}&page=${vo.page}">${vo.subject}</a>
								<c:if test="${vo.count!=0}">
									[${vo.count}]
								</c:if>
						</td>
						<td class="text-center" width="20%">
							<fmt:formatDate	value="${vo.regdate}" pattern="yyyy-MM-dd"/>
						</td>
						<td class="text-center" width="10%">${vo.hit}</td>
					</tr>
					</c:if>
				</c:if>
				</c:forEach>
			</table>
			</c:if>
			
			
			<c:if test="${who == 'boardReply' }">
			<table class="table table-hover" width="700" style="table-layout: fixed;" id="boardReply_t">
				<tr class="table-th">
					<th class="text-center" width="10%">번호</th>
					<th class="text-center" width="45%">제목</th>
					<th class="text-center" width="15%">이름</th>
					<th class="text-center" width="20%">작성일</th>
					<th class="text-center" width="10%">조회수</th>
				</tr>
				<c:forEach var="vo" items="${list}">
				<c:if test="${sessionScope.m_id==vo.m_id}">
					<c:if test="${vo.subject != '삭제된 게시물 입니다.'}">
					<tr class="aa">
						<td class="text-center" width="10%">
							${vo.no}
						</td>
						<td class="text-left" width="45%">
							<a href="qnaBoardContent.do?no=${vo.no}&page=${vo.page}">${vo.subject}</a>
								<c:if test="${vo.count!=0}">
									[${vo.count}]
								</c:if>
						</td>
						<td class="text-center" width="15%" style="text-overflow : ellipsis;overflow : hidden;white-space:nowrap;">${vo.name}</td>
						<td class="text-center" width="20%">
							<fmt:formatDate	value="${vo.regdate}" pattern="yyyy-MM-dd"/>
						</td>
						<td class="text-center" width="10%">${vo.hit}</td>
					</tr>
					</c:if>
				</c:if>
				</c:forEach>
			</table>
			</c:if>
			
			
			<c:if test="${who == 'comment' }">
			<table class="table table-hover" width="700" style="table-layout: fixed;" id="comment_t">
				<tr class="table-th">
					<th class="text-center" width="80%">댓글</th>
					<th class="text-center" width="20%">작성일</th>
				</tr>
				<c:forEach var="vo" items="${commentList}">
				<c:if test="${sessionScope.m_id==vo.m_id}">
					<c:if test="${vo.msg != '삭제된 게시물 입니다.'}">
					<tr class="aa">
						<td class="text-left" width="80%" style="text-overflow : ellipsis;overflow : hidden;white-space:nowrap;">
							<a href="qnaBoardContent.do?no=${vo.bno}&page=${vo.page}">${vo.msg}</a>
						</td>
						<td class="text-center" width="20%">
							<fmt:formatDate	value="${vo.regdate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					</c:if>
				</c:if>
				</c:forEach>
			</table>
			</c:if>
		</div>
	</div>
	
	
	<div class="container text-center">
		<ul class="pagination">
       		<li><a href="qnaBoardView.do?who=${who}&page=${curpage<11?curpage:curpage-10}">◀◀</a></li>
       		<li><a href="qnaBoardView.do?who=${who}&page=${curpage<2?curpage:curpage-1}">◀</a></li>
       				
       		<fmt:parseNumber var="num1" value="${curpage/10}" integerOnly="true"/>
       		<c:set var="num1" value="${num1<=0?1:num1*10}"/>  
       		<c:forEach var="i"  begin="${num1}" end="${num1==1?num1+8:num1+9}">
       			<c:choose>
       				<c:when test="${i > totalpage }"></c:when>
       				<c:when test="${i==curpage}">
	    				<li class="active">
	    					<a href="qnaBoardView.do?who=${who}&page=${i}">	${i} </a>
	    				</li>
       				</c:when>
       				<c:when test="${i <= totalpage}">
       					<li>
       						<a href="qnaBoardView.do?who=${who}&page=${i}"> ${i} </a>
       					</li>
       				</c:when>
       			</c:choose>
       		</c:forEach>
       		<li><a href="qnaBoardView.do?who=${who}&page=${curpage<totalpage?curpage+1:curpage}">▶</a></li>
            <li><a href="qnaBoardView.do?who=${who}&page=${curpage<=totalpage-10?curpage+10:curpage}">▶▶</a></li>
		</ul>
	</div>
	
	
</body>
</html>