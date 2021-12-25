<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시물 조회</title>
	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
	<header>
		<%@include file="../include/header.jsp" %>
	</header>
	<nav>
		<%@ include file="../include/nav.jsp"%>
	</nav>
	
	<section>
	
		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">글 제목</label>
			<div class="col-sm-10">
				<input type="text" id="title" name="title" class="form-control" value="${view.title}" readonly="readonly" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="content" class="col-sm-2 control-label">글 내용</label>
			<div class="col-sm-10">
				<textarea id="content" name="content" class="form-control" readonly="readonly">${view.content}</textarea>
			</div>
		</div>

		<div class="form-group">
			<label for="writer" class="col-sm-2 control-label">작성자</label>
			<div class="col-sm-10">
				<input type="text" id="writer" name="writer" class="form-control" value="${view.writer}" readonly="readonly" />
			</div>
		</div> 	
	
		<div>
			<a href="/board/modify?bno=${view.bno}">게시물 수정</a>, <a href="/board/delete?bno=${view.bno}">게시물 삭제</a>
		</div>
		
		<!-- 댓글시작 -->
		<hr />
	
		<ul>
			
	
			<c:forEach items="${reply}" var="reply">
				<li>
					<div>
						<p>${reply.writer}/ <fmt:formatDate value="${reply.regDate}" pattern="yyyy-MM-dd" /></p>
						<p>${reply.content }</p>
					</div>
				</li>
			</c:forEach>
		</ul>
	
		<div>
	
			<form method="post" action="/reply/write" class="form-horizontal">
	
				<div class="form-group">
					<label for="writer" class="col-sm-2 control-label">작성자</label> 
					<div class="col-sm-10">
						<input type="text" id="writer" name="writer" class="form-control">
					</div>
				</div>
				
				<div class="form-group">
					<label for="content" class="col-sm-2 control-label">댓글 내용</label> 
					<div class="col-sm-10">
						<textarea id="content" name="content" class="form-control"></textarea>
					</div>
				</div>
				
				<div class="form-group">
					<input type="hidden" name="bno" value="${view.bno}">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="repSubmit btn btn-success">작성</button>
					</div>
				</div>
			</form>
	
		</div>
		<!-- 댓글종료 -->
	</section>
</div>
</body>
</html>