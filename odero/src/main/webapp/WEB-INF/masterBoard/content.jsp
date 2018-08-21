<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="masterBoard/masterBoard.css" type="text/css">
	<style type="text/css">
		body{
			margin: 0px auto;
		}
		.row {
			margin: 0 auto;
			width: 750px;
		}
		.pre {
			padding : 6px;
			word-break : break-all;
		}
		.textarea{
			resize: none;
			width:500px;
		}
		input[type="text"]{
		7	width:100px;
			height : 20px;
		}
		*{
			font-size: 17px;
		}
		.td_a{
			font-size: 12px;
		}
		.td_a a{
			text-decoration: none;
			cursor: pointer;
			text-shadow: 0 1px 0 rgba(255, 255, 255, .75);
			font-size: 12px;
		}
		.content_td{
			border:solid 1px #e7ebf5;
			border-left:none;
			border-right:none; 
			border-bottom : none;
			word-break: break-all;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			var i=0;
			var u=0;
			
			$('.reply_reply').click(function(){
				var no=$(this).attr("value");
				if(i==0){
					$(this).text("취소");
					$('#in'+no).show();
					i=1;
				}
				else{
					$(this).text("댓글");
					$('#in'+no).hide();
					i=0;
				}
				if(u==1){
					$('.reply_update').text("수정");
					$('#up'+no).hide();
					u=0;
				}
			});
			$('.reply_update').click(function(){
				var no=$(this).attr("value");
				if(u==0){
					$(this).text("취소");
					$('#up'+no).show();
					u=1;
				}
				else{
					$(this).text("수정");
					$('#up'+no).hide();
					u=0;
				}
				if(i==1){
					$('.reply_reply').text("댓글");
					$('#in'+no).hide();
					i=0;
				}
			});
		});
	</script>
</head>
<body>
	<div class="container" style="margin-top:30px;">
		<div class="row" style="text-align: center;">
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
							<a href="MasterBoardReply.do?no=${vo.no}&page=${page}" class="btn btn-sm table-th">답변</a>
							<a href="MasterBoardUpdate.do?no=${vo.no}&page=${page}" class="btn btn-sm table-th">수정</a>
							<a href="MasterBoardDelete.do?no=${vo.no}&page=${page}" class="btn btn-sm table-th">삭제</a>
							<a href="MasterBoard.do" class="btn btn-sm table-th">목록</a>
						</td>
					</tr>
				</table>
		</div>
	</div>
	


	<div class="row" >
		<table class="table"  style="width:780px;border:1px ridge #ddd;">
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
									${rvo.name}  (<fmt:formatDate value="${rvo.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/>)${tab}
								</span>&nbsp;&nbsp; 
								<jsp:useBean id="now" class="java.util.Date" />
									<fmt:formatDate	var="today" value="${now}" pattern="yyyy-MM-dd"/>
									<fmt:formatDate	var="yesterday" value="${rvo.regdate}" pattern="yyyy-MM-dd"/>
									<c:if test="${rvo.msg != '삭제된 게시물 입니다.'}">
										<c:if test="${today==yesterday}">
											<b style="color:red;background-color:black">new</b>
										</c:if>
									</c:if>
						 </div>
					</td>
					<td class="td_a" style="width:15%;border:none;" align="right">
						<a class="reply_update" value="${rvo.no}">수정</a>│
						<a href="ContentReplyDelete.do?no=${rvo.no}&bno=${vo.no}&page=${page}">삭제</a>│
           				<a class="reply_reply" value="${rvo.no}">댓글</a>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="border:none;">
						<c:choose>
							<c:when test="${rvo.group_tab > 0}">
									<p class="pre" style="margin-left:${tab+5}px;background-color:#f5f5f5;">${rvo.msg}</p>
						 	</c:when>
						 	<c:otherwise><p class="pre table-th">${rvo.msg}</p></c:otherwise>
						 </c:choose>
					</td>
				</tr>
				
				<tr id="in${rvo.no}" style="display: none">
					<td colspan="2">
						<div class="pre" style="margin-left:20px">
							<form method="post" action="contentReplyNewInsert.do">
									<div class="form-inline">
										<div class="form-group">
	      									<label for="id">Name:</label>
      										<input type="text" name="name" style="height:15px;width:110px;" class="form-control" id="email" placeholder="Enter name">
										</div>
										<div class="form-group">
		      								<label for="pwd">Password:</label>
      										<input type="password" name="pwd" style="height:15px;width:110px;" class="form-control" id="pwd" placeholder="Enter password">
											<input type="submit" value="댓글달기" class="btn btn-sm table-th"/>
      									</div>
      								</div>
						
								<input type="hidden" name="bno" value="${vo.no}"/>
								<input type="hidden" name="no" value="${rvo.no}"/>
								<input type="hidden" name="page" value="${page}"/>
								<textarea name="msg" style="height:150px;" class="form-control textarea" id="comment"></textarea>
								&nbsp;
						</div>
							</form>
					</td>
				</tr>
				
				<tr id="up${rvo.no }" style="display: none" class="aa">
					<td colspan="2">
						<div class="pre" style="margin-left:20px">
							<form method="post" action="contentReplyUpdate.do">
								<div class="form-inline">
									<div class="form-group">
	      								<label for="id">Name:</label>
      									<input type="text" name="name" value="${rvo.name}" style="height:15px;width:110px;" class="form-control" id="email" placeholder="Enter name">
									</div>
									<div class="form-group">
	      								<label for="pwd">Password:</label>
      									<input type="password" name="pwd" style="height:15px;width:110px;" class="form-control" id="pwd" placeholder="Enter password">
										<input type="submit" value="수정하기" class="btn btn-sm table-th"/>
      								</div>
      							</div>
								<input type="hidden" name="bno" value="${vo.no}">
								<input type="hidden" name="no" value="${rvo.no}">
								<input type="hidden" name="page" value="${page}"/>
								<textarea name="msg" style="height:150px;" class="form-control textarea" id="comment">${rvo.msg }</textarea>
								&nbsp;
							</form>
						</div>
					</td>
				</tr>
			</c:forEach>
			<tr class="aa">
				<td colspan="2">
					<form method=post action="contentReplyInsert.do">
						<div class="form-inline">
							<div class="form-group">
	      						<label for="id">Name:</label>
      							<input type="text" name="name" style="height:15px;width:110px;" class="form-control" id="email" placeholder="Enter name">
							</div>
							<div class="form-group">
	      						<label for="pwd">Password:</label>
      							<input type="password" name="pwd" style="height:15px;width:110px;" class="form-control" id="pwd" placeholder="Enter password">
								<input type="submit" value="댓글달기" class="btn btn-sm table-th"/>
      						</div>
      					</div>
      					 <div class="form-group">
							<input type="hidden" name="bno" value="${vo.no}"/>
							<input type="hidden" name="page" value="${page}"/>
							<textarea name="msg" style="height:150px;" class="form-control textarea" id="comment"></textarea>
							</div>
					</form>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>