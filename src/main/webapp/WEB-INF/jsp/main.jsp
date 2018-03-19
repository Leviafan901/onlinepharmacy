<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="logout_url" value="/dir/logout"/>

<fmt:bundle basename="i18n">
	    <fmt:message key="navbar.hello" var="mainPage"/>
	    <fmt:message key="authentication.logout" var="logout"/>
</fmt:bundle>

<html>
<head>
<meta charset="UTF-8">
<fmt:bundle basename="i18n">
	    <fmt:message key="navbar.hello" var="mainPage"/>
	    <fmt:message key="authentication.logout" var="logout"/>
</fmt:bundle>
<title>${mainPage}</title>
</head>

<body>

<div>
        <a href="set-language?lang=en">EN</a>
        <p>|</p>
        <a href="set-language?lang=ru">RU</a>
        </div>
	<h1>${mainPage}</h1>
	<form action="${logout_url}" class="button_header" method="POST">
		<input type="submit" value="${logout}" />
	</form>
</body>
</html>