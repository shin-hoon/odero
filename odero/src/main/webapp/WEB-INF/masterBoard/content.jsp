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
		.textarea{
			resize: none;
			width:500px;
		}
		*{
			line-height: 20px;
			font-size: 15px;
			font-weight: 100;
			font-family: 'Jeju Gothic';
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
			var d=0;
			
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
				if(d==1){
					$('.reply_delete').text("삭제");
					$('#del'+no).hide();
					d=0;
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
				if(d==1){
					$('.reply_delete').text("삭제");
					$('#del'+no).hide();
					d=0;
				}
			});

			
			$('.reply_delete').click(function(){
				var no=$(this).attr("value");
				if(d==0){
					$(this).text("취소");
					$('#del'+no).show();
					d=1;
				}
				else{
					$(this).text("삭제");
					$('#del'+no).hide();
					d=0;
				}
				if(u==1){
					$('.reply_update').text("수정");
					$('#up'+no).hide();
					u=0;
				}
				if(i==1){
					$('.reply_reply').text("댓글");
					$('#in'+no).hide();
					i=0;
				}
			});
			
			
			$('#btnSubmit').click(function(){
				var name = $('#submit-name').val();
				var pwd = $('#submit-pwd').val();
				var content = $('#submit-content').val();
				
				if(name.trim() == ""){
					alert("이름을 입력하세요");
					$('#submit-name').focus();
					return false;
				}
				if(pwd.trim() == ""){
					alert("비밀번호를 입력하세요");
					$('#submit-pwd').focus();
					return false;
				}
				if(content.trim() == ""){
					alert("내용을 작성하세요");
					$('#submit-content').focus();
					return false;
				}
				$('#submit').submit();
			}); 
			
			
			
			$('.replySubmit').click(function(){
				var no = $(this).attr("data-no");
				var who = $(this).attr("data-who");
				var dp = $(this).attr("data-pwd");
				var dn = $(this).attr("data-name");
				var dc = $(this).attr("data-content");
				var pwd,name,content;
				
				if(who == "d-del"){
					pwd = $("#"+dp+no).val();
					if(pwd.trim() == ""){
						alert("비밀번호를 입력하세요");
						$("#"+dp+no).focus();
						return false;
					}
				}
				else if(who == "u-up" ||  who == "i-in"){
					pwd = $("#"+dp+no).val();
					name = $("#"+dn+no).val();
					content = $("#"+dc+no).val();
				
				
					if(name.trim() == ""){
						alert("이름을 입력하세요");
						$("#"+dn+no).focus();
						return false;
					}
					else if(pwd.trim() == ""){
						alert("비밀번호를 입력하세요");
						$("#"+dp+no).focus();
						return false;
					}
					else if(content.trim() == ""){
						alert("내용을 작성하세요");
						$("#"+dc+no).focus();
						return false;
					} 
				}
				
				$('#'+who+no).submit();  
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
							<a class="reply_update" value="${rvo.no}">수정</a>│
							<a class="reply_delete" value="${rvo.no}">삭제</a>│
           					<a class="reply_reply" value="${rvo.no}">댓글</a>
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
						<form id="i-in${rvo.no}" method="post" action="contentReplyNewInsert.do">
							<div style="margin-left:20px">
									<div class="form-inline">
										<div class="form-group">
	      									<label for="id">Name:</label>
      										<input type="text" name="name" style="height:15px;width:110px;" 
      											class="form-control" id="i-name${rvo.no}" placeholder="Enter name"
      										/>
										</div>
										<div class="form-group">
		      								<label for="pwd">Password:</label>
      										<input type="password" name="pwd" style="height:15px;width:110px;" 
      											class="form-control" id="i-pwd${rvo.no}" placeholder="Enter password"
      										/>
											<input type="submit" data-no="${rvo.no}" data-who="i-in" data-pwd="i-pwd" 
												data-name="i-name" data-content="i-content" 
												value="댓글달기" class="btn btn-sm table-th replySubmit"
											/>
      									</div>
      								</div>
								<input type="hidden" name="bno" value="${vo.no}"/>
								<input type="hidden" name="no" value="${rvo.no}"/>
								<input type="hidden" name="page" value="${page}"/>
								<textarea name="msg" style="height:150px;"
									class="form-control textarea" id="i-content${rvo.no}">
								</textarea>&nbsp;
							</div>
						</form>
					</td>
				</tr>
				
				<tr id="up${rvo.no }" style="display: none" class="aa">
					<td colspan="2" style="border-bottom:1px solid #ddd;">
						<div style="margin-left:20px;">
							<form id="u-up${rvo.no}" method="post" action="contentReplyUpdate.do">
								<div class="form-inline">
									<div class="form-group">
	      								<label for="id">Name:</label>
      									<input type="text" name="name" value="${rvo.name}" style="height:15px;width:110px;" 
      										class="form-control" id="u-name${rvo.no}" placeholder="Enter name"
      									/>
									</div>
									<div class="form-group">
	      								<label for="pwd">Password:</label>
      									<input type="password" name="pwd" style="height:15px;width:110px;" 
      										class="form-control" id="u-pwd${rvo.no}" placeholder="Enter password"
      									/>
										<input type="submit" data-no="${rvo.no}" data-who="u-up" 
											data-pwd="u-pwd" data-name="u-name" data-content="u-content"
											value="수정하기" class="btn btn-sm table-th replySubmit"
										/>
      								</div>
      							</div>
								<textarea name="msg" style="height:150px;" class="form-control textarea" id="u-content${rvo.no}">
									${rvo.msg }
								</textarea>
								&nbsp;
								<input type="hidden" name="bno" value="${vo.no}">
								<input type="hidden" name="no" value="${rvo.no}">
								<input type="hidden" name="page" value="${page}"/>
							</form>
						</div>
					</td>
				</tr>
				
				<tr id="del${rvo.no}" class="aa" align="center" style="display:none;">
					<td colspan="2" style="border-bottom:1px solid #ddd;">
						<div  style="width:300px;text-align: center;">
							<form id="d-del${rvo.no}" method="post" action="contentReplyDelete_ok.do">
								<div>	
									<label for="pwd">댓글삭제</label>		
								</div>
								<div class="form-inline" style="border-top:ridge;border-bottom:ridge; padding:10px;">
									<div class="form-group">
	      								<label for="pwd">Password:</label>
      									<input type="password" name="pwd" style="height:15px;width:110px;" 
      										class="form-control" id="d-pwd${rvo.no}" placeholder="Enter password"
      									/>
      								</div>
      							</div>
      							<div>
									<input type="submit" data-no="${rvo.no}" data-who="d-del" data-pwd="d-pwd" 
										value="삭제하기" class="btn btn-sm table-th replySubmit" style="margin-top:5px;"
									/>
								</div>
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
					<form id="submit" method=post action="contentReplyInsert.do">
						<div class="form-inline">
							<div class="form-group">
	      						<label for="id">Name:</label>
      							<input type="text" name="name" style="height:15px;width:110px;" 
      								class="form-control" id="submit-name" placeholder="Enter name"
      							/>
							</div>
							<div class="form-group">
	      						<label for="pwd">Password:</label>
      							<input type="password" name="pwd" style="height:15px;width:110px;" 
      								class="form-control" id="submit-pwd" placeholder="Enter password"
      							/>
								<input type="submit" id="btnSubmit" value="댓글달기" class="btn btn-sm table-th"/>
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
</body>
</html>