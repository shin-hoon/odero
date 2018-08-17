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
	.row {
		margin: 0 auto;
		width: 750px;
	}
	</style>
	<script type="text/javascript">
		$(function(){
			var i=0;
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
			});
			var u=0;
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
			});
		});
	</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<center>
				<h3>공사중</h3>
				<table class="table" width="600">
					<tr class="aa">
						<td width="20%" class="text-center success">번호</td>
						<td width="30%" class="text-center">${vo.no}</td>
						<td width="20%" class="text-center success">작성일</td>
						<td width="30%" class="text-center">
							<fmt:formatDate value="${vo.regdate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr class="aa">
						<td width="20%" class="text-center success">이름</td>
						<td width="30%" class="text-center">${vo.name}</td>
						<td width="20%" class="text-center success">조회수</td>
						<td width="30%" class="text-center">${vo.hit}</td>
					</tr>
					<tr class="aa">
						<td width="20%" class="text-center success">제목</td>
						<td colspan="3" class="text-center">${vo.subject}</td>
					</tr>
					<tr class="aa">
						<td colspan="4" style="border:solid 1px #e7ebf5">
						<pre style="border:none;background-color:white;">${vo.content}</pre>
						</td>
					</tr>
				</table>
			</center>
		</div>
	</div>
	
	
	<div class="row">
		<table class="table" width="600">
			<tr class="aa">
				<td colspan="4" class="text-right">
					<a href="MasterBoardReply.do?no=${vo.no}&page=${page}" class="btn btn-sm btn-success">답변</a>
					<a href="MasterBoardUpdate.do?no=${vo.no}&page=${page}" class="btn btn-sm btn-success">수정</a>
					<a href="MasterBoardDelete.do?no=${vo.no}&page=${page}" class="btn btn-sm btn-primary">삭제</a>
					<a href="MasterBoard.do" class="btn btn-sm btn-info">목록</a>
				</td>
			</tr>
		</table>
	</div>



	<div class="row">
		<table id="table" width="600">
			<c:forEach var="rvo" items="${list}">
				<tr>
					<td align=left>
						<c:if test="${rvo.group_tab > 0}">
							<c:forEach var="i" begin="1" end="${rvo.group_tab+1}">
								<span style="margin-left:${5*i}px"></span>
							</c:forEach>
							<span>└</span>
						</c:if>
						<span>${rvo.name}(<fmt:formatDate value="${rvo.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/>)</span>&nbsp;&nbsp; 
						<jsp:useBean id="now" class="java.util.Date" />
							<fmt:formatDate	var="today" value="${now}" pattern="yyyy-MM-dd"/>
							<fmt:formatDate	var="yesterday" value="${rvo.regdate}" pattern="yyyy-MM-dd"/>
							<c:if test="${rvo.msg != '삭제된 게시물 입니다.'}">
								<c:if test="${today==yesterday}">
									<b style="color:red;background-color:black">new</b>
								</c:if>
							</c:if>
						 <br>
						<c:if test="${rvo.group_tab > 0}">
							<c:forEach var="i" begin="1" end="${rvo.group_tab+1}">
								<span style="margin-left:${5*i}px"></span>
							</c:forEach>
							<span>└</span>
						</c:if>
						<span>
							<pre>${rvo.msg}</pre>
						</span>
					</td>
					<td align=right>
						└<a class="reply_update" value="${rvo.no}">수정</a>
						└<a href="reply_delete.do?no=${rvo.no}&bno=${vo.no}">삭제</a>
           				└<a class="reply_reply" value="${rvo.no}">댓글</a>
					</td>
				</tr>
				
				<tr id="in${rvo.no}" style="display: none">
					<td colspan="2">
						<form method=post action="reply_reply_insert.do">
							<input type="hidden" name=bno value="${vo.no}"/>
							<input type="hidden" name=pno value="${rvo.no}"/>
							└<textarea rows="3" cols="80" style="float: left" name="msg"></textarea>
							&nbsp;
							<input type=submit value="댓글달기" style="height: 50px"/>
						</form>
					</td>
				</tr>
				
				<tr id="up${rvo.no }" style="display: none">
					<td colspan="2">
						<form method=post action="contentReplyUpdate.do">
							<input type="hidden" name=bno value="${vo.no}">
							<input type="hidden" name=no value="${rvo.no}">
							└<textarea rows="3" cols="80" style="float: left" name="msg">${rvo.msg }</textarea>
							&nbsp;
							<input type=submit value="수정하기" style="height: 50px"/>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>

		<table id="table_content" width="600">
			<tr>
				<td>
					<form method=post action="contentReplyInsert.do">
						이름:<input type="text" name="name" value=""/>
						비밀번호:<input type="text" name="pwd" value=""/>
						<input type="hidden" name="bno" value="${vo.no}"/>
						<input type="hidden" name="page" value="${page}"/>
						<textarea rows="3" cols="70" name="msg" style="resize: none;"></textarea>
						&nbsp;
						<input type="submit" value="댓글달기" style="height: 50px"/>
					</form>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>

















