<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="login_url" value="/dir/login" />

<html lang=en>
<head>
	<meta charset="UTF-8">
	
	<fmt:bundle basename="i18n">
	    <fmt:message key="authentication.login" var="login"/>
	    <fmt:message key="authentication.header" var="authrization_header"/>
	    <fmt:message key="authentication.explanation" var="explanaition"/>
	    <fmt:message key="login.error" var="login_error_aut"/>
    </fmt:bundle>

<style>
        <jsp:directive.include file="/WEB-INF/css/style.css"/>
</style>
<script>
        <jsp:directive.include file="/WEB-INF/js/dialog.js"/>
</script>
</head>
<body>

    <div>
        <a href="set-language?lang=en">EN</a>
        <p>|</p>
        <a href="set-language?lang=ru">RU</a>
    </div>
	<div id="wrapper">
		
		<form name="login-form" class="login-form" action="${login_url}" method="POST" >
			<div class="header">
				<h1>${authrization_header}</h1>
				<span>${explanaition}</span>
			</div>
			<div class="content">
				<input name="login" type="text" id="input_username" class="input username" placeholder="login" onfocus="this.value=''" required />
				<input name="password" type="password" id="input_password" class="input password" placeholder="password"
					onfocus="this.value=''" required />
			</div>
			<div class="footer">
				<input type="submit" value="${login}" class="button" />
			</div>
			<c:if test="${not empty login_error}">
		    	<p>${login_error_aut}</p>
		    </c:if>
		</form>
		
	</div>
	<div class="gradient"></div>
</body>
</html>