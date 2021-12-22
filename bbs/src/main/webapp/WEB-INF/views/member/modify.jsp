<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>게시판</title>
</head>
<body>

	<form role="form" method="post" autocomplete="off">
		<!-- <p>
			<label for="userId">아이디</label> <input type="text" id="userId"
				name="userId" />
		</p> -->
		<p>
			<label for="userName">닉네임</label> <input type="text" id="userName" name="userName" 
				value="${member.userName }" readonly="readonly" />
		</p>		
		
		<p>
			<label for="userPass">새로운 패스워드</label> <input type="password"
				id="userPass" name="userPass" />
		</p>

		<p>
			<button type="submit" id="submit">회원정보 수정</button>
		</p>
		<p>
			<a href="/">처음으로</a>
		</p>

	</form>
</body>
</html>