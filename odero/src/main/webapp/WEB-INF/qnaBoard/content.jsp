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
	<style type="text/css">
		.pre {
			padding : 6px;
			/* word-break : break-all; */   /*  p 태그용  */
			white-space: pre-wrap;		/*   pre 태그용 */ 
			border:none;
			/*
				글자길면 .... 로 자르기  block 태그만 가능
				 text-overflow : ellipsis;  말줄임표
				 overflow : hidden; 
				 white-space:nowrap;    줄바꿈 무시
			*/
		}
	</style>
	<script type="text/javascript" src="qnaBoard/qnaBoard.js" charset="utf-8"></script>
</head>
<body>
	<div class="container" style="margin-top:30px;">
		<div class="row">
				<table class="table" style="width:780px;table-layout: fixed;">
					<tr style="background: rgb(222, 235, 247);">
						<th class="aa text-center" width="100%" colspan="4">
							 <P>${vo.subject}</P> 
						</th>
					</tr>
					<tr class="aa">
						<td width="20%" class="text-center" style="background: rgb(247, 260, 272);">번호</td>
						<td width="30%" class="text-center">${vo.no}</td>
						<td width="20%" class="text-center" style="background: rgb(247, 260, 272);">작성일</td>
						<td width="30%" class="text-center">
							<fmt:formatDate value="${vo.regdate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr class="aa">
						<td width="20%" class="text-center" style="background: rgb(247, 260, 272);">이름</td>
						<td width="30%" class="text-center">${vo.name}</td>
						<td width="20%" class="text-center" style="background: rgb(247, 260, 272);">조회수</td>
						<td width="30%" class="text-center">${vo.hit}</td>
					</tr>
					<tr class="aa">
						<td width="20%" class="text-center" style="background: rgb(247, 260, 272);">제목</td>
						<td colspan="3" class="text-center"> <P>${vo.subject}</P> </td>
					</tr>
					<tr class="aa">
						<td colspan="4" class="content_td">
							<pre style="border:none;background-color:white;word-break:break-all;">${vo.content}</pre>
						</td>
					</tr>
					<tr class="aa">
						<td colspan="4" class="text-right">
							<c:if test="${sessionScope.m_id==null}">
								<a onclick="alert('로그인이 필요합니다.');" class="btn btn-sm table-th">답변</a>
							</c:if>
							<c:if test="${sessionScope.m_id!=null}">
								<a href="qnaBoardReply.do?no=${vo.no}&page=${page}" class="btn btn-sm table-th">답변</a>
							</c:if>
							<c:if test="${sessionScope.m_id==vo.m_id}">
								<a href="qnaBoardUpdate.do?no=${vo.no}&page=${page}" class="btn btn-sm table-th">수정</a>
								<a data-no="${vo.no}" data-page="${page}" id="qnaDelete" class="btn btn-sm table-th">삭제</a>
							</c:if>
							<c:if test="${who == null }">
								<a href="qnaBoard.do?page=${page}" class="btn btn-sm table-th">목록</a>
							</c:if>
							<c:if test="${who != null }">
								<a href="qnaBoardView.do?who=${who}&page=${page}" class="btn btn-sm table-th">목록</a>
							</c:if>
						</td>
					</tr>
				</table>
		</div>
	</div>
	


<div class="replylist">
	<div class="row" >
		<table class="table"  style="width:780px;border:1px ridge #ddd;table-layout: fixed;">
			<c:forEach var="rvo" items="${list}">
				<tr>
					<td align="left" style="width:85%;border:none;">
						<div>
							<c:set var="tab" value="0"/>
							<c:if test="${rvo.group_tab > 0}">
								<c:forEach var="i" begin="1" end="${rvo.group_tab}">
									<c:set var="tab" value="${(10*i)}"/>
								</c:forEach>
								<span style="margin-left:${tab}px">└</span>
							</c:if>
								<span>
									${rvo.name}  (<fmt:formatDate value="${rvo.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/>)
								</span>&nbsp;&nbsp; 
								<jsp:useBean id="now" class="java.util.Date" />
									<fmt:formatDate	var="today" value="${now}" pattern="yyyy-MM-dd"/>
									<fmt:formatDate	var="yesterday" value="${rvo.regdate}" pattern="yyyy-MM-dd"/>
									<c:if test="${rvo.msg != '삭제된 게시물 입니다.'}">
										<c:if test="${today==yesterday}">
											<b style="color:red;">new</b>
										</c:if>
									</c:if>
						 </div>
					</td>
					<td class="td_a" style="width:15%;border:none;" align="right">
						<c:if test="${rvo.msg != '삭제된 게시물 입니다.'}">
							<c:if test="${sessionScope.m_id==rvo.m_id}">
									<a class="reply_update" value="${rvo.no}">수정</a>│
									<a class="reply_delete" value="${rvo.no}">삭제</a>│
							</c:if>
							<c:if test="${sessionScope.m_id==null}">
								<a onclick="alert('로그인이 필요합니다');" value="${rvo.no}">댓글</a>
							</c:if>
							<c:if test="${sessionScope.m_id!=null}">
								<a class="reply_reply" value="${rvo.no}">댓글</a>
							</c:if>
						</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="border:none;">
						<c:choose>
							<c:when test="${rvo.group_tab > 0}">
								<c:if test="${rvo.msg != '삭제된 게시물 입니다.'}">
									<pre class="pre" style="margin-left:${tab}px;background-color:#f5f5f5;">${rvo.msg}</pre>
								</c:if>
								<c:if test="${rvo.msg == '삭제된 게시물 입니다.'}">
									<pre class="pre" style="margin-left:${tab}px;background-color:#f5f5f5;color:red;">${rvo.msg}</pre>
								</c:if>
						 	</c:when>
						 	<c:otherwise>
						 		<c:if test="${rvo.msg != '삭제된 게시물 입니다.'}">
									<pre class="pre table-th">${rvo.msg}</pre>
								</c:if>
								<c:if test="${rvo.msg == '삭제된 게시물 입니다.'}">
									<pre class="pre table-th" style="color:red;">${rvo.msg}</pre>
								</c:if>
							</c:otherwise>
						 </c:choose>
					</td>
				</tr>
				
				<tr id="in${rvo.no}" style="display: none">
					<td colspan="2" style="border-bottom:1px solid #ddd;">
						<form id="i-in${rvo.no}" method="post" action="qnaCommentNewInsert.do">
							<div style="margin-left:20px">
									<div class="form-inline">
										<div class="form-group">
	      									<label for="id">Name:</label>
      										<input type="text" name="name" style="height:15px;width:110px;" value="${rvo.name}" 
      											class="form-control" id="i-name${rvo.no}" data-mname="${sessionScope.m_name}" disabled="disabled" placeholder="Enter name"
      										/>
										</div>
										<div class="form-group">
											<input type="button" data-no="${rvo.no}" data-who="i-in" data-content="i-content" 
												value="댓글달기" class="btn btn-sm table-th replySubmit"
											/>
      									</div>
      								</div>
								<input type="hidden" name="bno" value="${vo.no}"/>
								<input type="hidden" name="no" value="${rvo.no}"/>
								<input type="hidden" name="page" value="${page}"/>
								<textarea name="msg" style="height:150px;"
									class="form-control textarea" id="i-content${rvo.no}"></textarea>
							</div>
						</form>
					</td>
				</tr>
				
				<tr id="up${rvo.no }" style="display: none" class="aa">
					<td colspan="2" style="border-bottom:1px solid #ddd;">
						<div style="margin-left:20px;">
							<form id="u-up${rvo.no}" method="post" action="qnaCommentUpdate.do"> 
								<div class="form-inline">
									<div class="form-group">
	      								<label for="id">Name:</label>
      									<input type="text" name="name" value="${rvo.name}" style="height:15px;width:110px;" 
      										class="form-control" id="u-name${rvo.no}" disabled="disabled" placeholder="Enter name"
      									/>
									</div>
									<div class="form-group">
										<input type="button" data-no="${rvo.no}" data-who="u-up" data-content="u-content"
											value="수정하기" class="btn btn-sm table-th replySubmit"
										/>
      								</div>
      							</div>
								<textarea name="msg" style="height:150px;" 
									class="form-control textarea" id="u-content${rvo.no}">${rvo.msg}</textarea>
								<input type="hidden" name="bno" value="${vo.no}">
								<input type="hidden" name="no" value="${rvo.no}">
								<input type="hidden" name="page" value="${page}"/>
							</form>
						</div>
					</td>
				</tr>
			</c:forEach>
			
			
			<tr class="aa">
				<td colspan="2">
					<form id="submit" method=post action="qnaCommentInsert.do">
						<div class="form-inline">
							<div class="form-group">
	      						<label for="id">Name:</label>
      							<input type="text" name="name" style="height:15px;width:110px;" value="${sessionScope.m_name}"
      								class="form-control" id="submit-name" disabled="disabled" placeholder="Enter name"
      							/>
							</div>
							<div class="form-group">
								<c:if test="${sessionScope.m_id == null}">
									<input type="button" onclick="alert('로그인이 필요합니다.')" value="댓글달기" class="btn btn-sm table-th"/>
								</c:if>
								<c:if test="${sessionScope.m_id != null}">
									<input type="button" id="btnSubmit" value="댓글달기" class="btn btn-sm table-th"/>
								</c:if>
      						</div>
      					</div>
							<textarea name="msg" style="height:150px;" class="form-control textarea" id="submit-content"></textarea>
							<input type="hidden" name="bno" value="${vo.no}"/>
							<input type="hidden" name="page" value="${page}"/>
					</form>
				</td>
			</tr>
		</table>
		</div>
	</div>
</body>
</html>