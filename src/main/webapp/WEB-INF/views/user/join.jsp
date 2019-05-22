<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css"	rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>

$(function() {
	$('#email').change(function() {
		$('#check-button').show();
		$('#check-image').hide();
	});
	$('#check-button').click(function() {
		console.log('check button click');
		
		var email = $('#email').val();
		
		if(email == '') {
			return;
		}
		
		/* ajax 통신 */
		
		console.log(email);
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath}/user/api/checkemail?email=" + email,
			type : "get", // method - get이냐 post냐 (post 이면 파라미터 내용을 data에 적어주면 된다) 
			dataType : "json", // json으로 달라는 의미
			data : "",
			success : function(response ) { // 콜백함수 
				//console.log(response);
				//console.log(response.result); // success 면 통신 성공
				if(response.result != "success") {
					console.error(response);
					//console.error(response.message);
					return;
				}
				
				if(response.data == true) {
					alert("이미 존재하는 이메일 입니다. \n다른 이메일을 사용해 주세요.")
					$('#email').focus();
					$('#email').val("");
					
					
					return; 
				}
				
				$('#check-button').hide();
				$('#check-image').show();
				
				
			
			},
			error : function(xhr, error) { // 
				console.error("error: " + error);
			}
			
		});
		
		
	});
});

</script>
<script>
	console.log("hello world");
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form:form 
					modelAttribute="userVo"
					id="join-form" 
					name="joinForm" 
					method="post" 
					action="${pageContext.servletContext.contextPath }/user/join">
					
					<label class="block-label" for="name">이름</label> 
					<input id="name" name="name" type="text" value="">
					<spring:hasBindErrors name="userVo">
					    <c:if test="${errors.hasFieldErrors('name') }">
					    	<p style="font-weight:bold; color:red; text-align:left; padding:0;">
								<strong>
							         <spring:message 
									     code="${errors.getFieldError( 'name' ).codes[0] }" 				     
									     text="${errors.getFieldError( 'name' ).defaultMessage }" />
						       	</strong>
					       	</p>
					   </c:if>
					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label> 
					<form:input path="email"/>
					<input type="button" id="check-button" value="중복체크"> 
					<img style="display:none" id="check-image" src="${pageContext.servletContext.contextPath}/assets/images/check.png">
						
						
					<%-- <form:errors path="email" style="font-weight:bold; color:red; text-align:left; padding:0;" /> --%>
					<p style="font-weight:bold; color:#f00; text-align:left; padding:0;">
						<form:errors path="email" />
					</p>
					<label class="block-label">패스워드</label> 
					<form:password path="password"/>

					<fieldset>
						<legend>성별</legend>
						<label>여</label> 
						<form:radiobutton path="gender" value="female" checked="checked"/> 
						<label>남</label> 
						<form:radiobutton path="gender" value="male"/>
					</fieldset>

					<fieldset>
						<legend>약관동의</legend>
						<!-- userVo에 agreeProv 변수 추가해줘야 함 -->
						<%-- <form:checkbox path="agreeProv" value="ture"/> --%>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>

					<input type="submit" value="가입하기">

				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>