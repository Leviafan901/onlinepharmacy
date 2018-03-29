<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="logout_url" value="/dir/logout"/>
<c:url var="medicine_list" value="/dir/medicine-list"/>
<c:url var="order_list" value="/dir/order-list"/>
<c:url var="main_page" value="/dir/main" />

<html>
<head>
<meta charset="UTF-8">
<fmt:bundle basename="i18n">
	    <fmt:message key="navbar.hello" var="mainPage"/>
	    <fmt:message key="authentication.logout" var="logout"/>
	    <fmt:message key="order.order_successed" var="successed_info"/>
	    <fmt:message key="order.order_fail" var="fail_info"/>
</fmt:bundle>
<title>${mainPage}</title>

<style>
    <jsp:directive.include file="/WEB-INF/css/style.css"/>
</style>
</head>

<body>

  <ul id="menu">
	    <li><a href="${medicine_list}">MEDICINE LIST</a></li>
	    <li><a href="${order_list}">ORDERS</a></li>
	    <li><a href="#">REQUESTS</a></li>
	    <li><a href="${main_page}">ABOUT US</a></li>
	    <li>
		    <form action="${logout_url}" method="POST">
				<input type="submit" value="${logout}" class="button_logout" />
		    </form>
	    </li>
	    <li><a href="#">LANGUAGE</a>
	      <ul class="submenu">
	      <li><a href="set-language?lang=en">EN</a></li>
	      <li><a href="set-language?lang=ru">RU</a></li>
	      </ul>
	    </li>
    </ul>
    <div>
    	<c:if test="${not empty successed_message}">
			<p>${successed_info}</p>
		</c:if>
		<c:if test="${not empty fail_message}">
			<p>${fail_info}</p>
		</c:if>
    </div>
</body>
</html>