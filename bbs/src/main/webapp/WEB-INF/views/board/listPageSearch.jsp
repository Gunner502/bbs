<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시물 목록</title>
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
		<h2>글 목록</h2>
		
		<table class="table table-hover">
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성일</th>
					<th>작성자</th>
					<th>조회수</th>
				</tr>
			</thead>
	
			<tbody>
				<c:forEach items="${list}" var="list">
					<tr>
						<td>${list.bno}</td>
						<td><a href="/board/view?bno=${list.bno}">${list.title}</a></td>
						<td><fmt:formatDate value="${list.regDate}" pattern="yyyy-MM-dd" /></td>
						<td>${list.writer}</td>
						<td>${list.viewCnt}</td>
					</tr>
				</c:forEach>
			</tbody>
	
		</table>
		
		<div class="search row">
			<div class="col-xs-2 col-sm-2">
				<select name="searchType" class="form-control">
					<option value="title"
						<c:if test="${page.searchType eq 'title'}">selected</c:if>>제목</option>
					<option value="content"
						<c:if test="${page.searchType eq 'content'}">selected</c:if>>내용</option>
					<option value="title_content"
						<c:if test="${page.searchType eq 'title_content'}">selected</c:if>>제목+내용</option>
					<option value="writer"
						<c:if test="${page.searchType eq 'writer'}">selected</c:if>>작성자</option>
				</select>
			</div>
			
			<div class="col-xs-10 col-sm-10">
				<div class="input-group">
					<input type="text" name="keyword" value="${page.keyword}" class="form-control"/>
					<span class="input-group-btn">
						<button type="button" id="searchBtn" class="btn btn-default">검색</button>
					</span>
				</div>
			</div>
			
			<script>
				document.getElementById("searchBtn").onclick = function() {
		
					let searchType = document.getElementsByName("searchType")[0].value;
					let keyword = document.getElementsByName("keyword")[0].value;
		
					console.log(searchType)
					console.log(keyword)
					location.href = "/board/listPageSearch?num=1" + "&searchType=" + searchType + "&keyword=" + keyword;
				};
			</script>
		</div>
		
		<div class="col-md-offset-3">
			<ul class="pagination">
				<c:if test="${page.prev}">
					<li><a href="/board/listPageSearch?num=${page.startPageNum - 1}${page.searchTypeKeyword}">이전</a></li>				
				</c:if>
		
				<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}"
					var="num">
					<li <c:out value="${select == num ? 'class=active' : ''}"/>>
						<a href="/board/listPageSearch?num=${num}${page.searchTypeKeyword}">${num}</a>
						<%-- <c:if test="${select != num}">
							<a href="/board/listPageSearch?num=${num}${page.searchTypeKeyword}">${num}</a>
						</c:if> 
						<c:if test="${select == num}">
							${num}
						</c:if> --%>
					</li>
					
				</c:forEach>
		
				<c:if test="${page.next}">
					<li><a
						href="/board/listPageSearch?num=${page.endPageNum + 1}${page.searchTypeKeyword}">다음</a>
					</li>
				</c:if>
			</ul>
		</div>
	</section>
	
	<footer>
		<%@include file="../include/footer.jsp" %>
	</footer>
</div>
</body>
</html>