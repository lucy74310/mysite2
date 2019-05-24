<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@page import="com.cafe24.mysite.vo.BoardVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>

</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath}/board" method="post">
					<input type="text" id="kwd" name="kwd" value="${kwd }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>	
					
					<c:set var="count" value="${fn:length(list) }"/>
					<c:forEach items="${list }" var="vo" varStatus="status"> 
					<tr>
						<c:choose>
							<c:when test="${vo.del == 1 }">
								<td>${(paging.totalPosts - paging.fromPost)-status.index+del }</td>
								<td style="text-align:left; padding-left:${10*(vo.depth-1)}px">
									<c:if test="${vo.depth > 1 }">
									<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png'>
									</c:if>
									삭제된 글입니다.
								</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:when>
							<c:otherwise>
								<td>${(paging.totalPosts - paging.fromPost)-status.index+del }</td>
								<td style="text-align:left; padding-left:${10*(vo.depth-1)}px">
									<c:if test="${vo.depth > 1 }">
									<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png'>
									</c:if>
									<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no}" >${vo.title }</a>
								</td>
								<td>${vo.userName }</td>
								<td>${vo.hit }</td>
								<td>${vo.regDate}</td>
								<td>
									<c:if test="${authUser.no == vo.userNo }">
										<a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no}/${vo.groupNo}/${vo.orderNo}"  class="del">
											<img src="${pageContext.servletContext.contextPath }/assets/images/recycle.png">
										</a>
									</c:if>
								</td>	
							
							</c:otherwise>
						</c:choose>
						
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${paging.preButtonActive == true }">
								<li><a href="${pageContext.servletContext.contextPath }/board?page=${paging.pageBlockBeginPage-1}">◀</a></li>
							</c:when>
							<c:otherwise>
								<li>◀</li>
							</c:otherwise>
						</c:choose>
						<c:forEach begin="${paging.pageBlockBeginPage }" end="${paging.pageBlockEndPage }" step="1" var="page" varStatus="status">
							<c:choose>
								<c:when test="${page == paging.nowPage }">
									<li class="selected"><a href="${pageContext.servletContext.contextPath }/board?page=${page}">${page}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.servletContext.contextPath }/board?page=${page}&kwd=${kwd}">${page}</a></li>
								</c:otherwise>
							</c:choose>
							
						</c:forEach>
						
						<c:choose>
							<c:when test="${paging.nextButtonActive == true }">
								<li><a href="${pageContext.servletContext.contextPath}/board?page=${paging.pageBlockEndPage+1}">▶</a></li>
							</c:when>
							<c:otherwise>
								<li>▶</li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				
				<div class="bottom">
					<c:choose>
						<c:when test="${empty authUser }">
							<a href="" id="new-book1">글쓰기</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.servletContext.contextPath}/board/write/new" id="new-book">글쓰기</a>
						</c:otherwise>							
					</c:choose>
					
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
	
	<script>
		$('#new-book1').click(function(){
			alert("로그인이 필요한 서비스 입니다.")
			window.location.href= "${pageContext.servletContext.contextPath}/user/login";
		});
		
		
	</script>
	
</body>
</html>