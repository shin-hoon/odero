<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="masterBoard/masterBoard.css" type="text/css">
<script type="text/javascript" src="masterBoard/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<style>
.row{
	width:1200px;
	text-align: center;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<h3>새글</h3>
			<form id="formSubmit" method="post" action="MasterBoardInsert_ok.do">
			<table class="table aa">
				<tr>
					<td width=15% class="text-right">제목</td>
					<td width=85%>
						<input type="text" name="subject" style="height:15px;width:370px;" 
      						class="form-control" id="subject" placeholder="제목을 입력하세요."
      					/>
					</td>
				</tr>
				<tr>
					<td width=15% class="text-right">이름</td>
					<td width=85%>
						<input type="text" name="name" style="height:15px;width:370px;" 
      						class="form-control" id="name" placeholder="이름을 입력하세요."
      					/>
					</td>
				</tr>
				<tr>
					<td width=15% class="text-right">내용</td>
					<td width=85%>
						<textarea name="content" id="ir1" rows="10" cols="100"
							style="width: 966px; height: 412px; display: none;"
							class="form-control">
						</textarea>
					</td>
				</tr>
				<tr>
					<td width=15% class="text-right">비밀번호</td>
					<td width="85%" align="left">
						<input type="password" name="pwd" style="height:15px;width:160px;" 
      						class="form-control" id="pwd" placeholder="비밀번호를 입력하세요."
      					/>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type="button" onclick="submitContents(this);" value="글쓰기" class="btn btn-sm table-th" />
						<input type="button" value="취소 " class="btn btn-sm table-th" onclick="javascript:history.back()">
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
<script type="text/javascript" src="masterBoard/masterBoard.js" charset="utf-8"></script>


</body>
</html>