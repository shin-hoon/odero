<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<style type="text/css">
		.row {
			margin: 0 auto;
			width: 300px;
			text-align: center;
		}
	</style>
	<link rel="stylesheet" href="qnaBoard/qnaBoard.css" type="text/css">
</head>
<body>
	<div class="container" style="margin-top:30px;">
		<div class="row">
			<form method="post" action="qnaBoardDelete_ok.do">
			<table class="table aa" width="300">
				<tr class="aa" align="center">
					<td style="border-bottom:1px solid #ddd;">
						<div  style="width:300px;text-align: center;">
						<div>	
							<label for="pwd">삭제하기</label>		
						</div>
							<div class="form-inline" style="border-top:ridge;border-bottom:ridge; padding:10px;">
								<div class="form-group">
	      							<label for="pwd">Password:</label>
      								<input type="password" name="pwd" style="height:15px;width:150px;" 
      									class="form-control" id="pwd" placeholder="비밀번호를 입력하세요."
      								/>
      							</div>
      						</div>
      						<div style="margin-top:5px;">
      							<input type="hidden" name="no" value="${no}"/>
								<input type="submit" value="삭제하기" class="btn btn-sm table-th" />
								<input type="button" class="btn btn-sm table-th" 
									onclick="javascript:history.back();" value="뒤로가기"
								/>
							</div>
							</div>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>















