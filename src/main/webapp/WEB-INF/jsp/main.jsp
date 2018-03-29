<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:url var="logout_url" value="/dir/logout" />
<c:url var="medicine_list" value="/dir/medicine-list" />
<c:url var="order_list" value="/dir/order-list" />
<c:url var="main_page" value="/dir/main" />

<html>
<head>
<meta charset="UTF-8">
<fmt:bundle basename="i18n">
	    <fmt:message key="navbar.hello" var="mainPage"/>
	    <fmt:message key="authentication.logout" var="logout"/>
</fmt:bundle>
<title>${mainPage}</title>

<style>
    <jsp:directive.include file="/WEB-INF/css/style.css"/>
</style>
</head>

<body>
  <header class="header-inner-page-header">ONLINE_PHARMACY </header>

	<ul id="menu">
	    <li><a href="${medicine_list}">MEDICINE LIST</a></li>
	    <li><a href="${order_list}">ORDERS</a></li>
	    <li><a href="#">REQUESTS</a></li>
	    <li><a href="${main_page}">ABOUT US</a></li>
	    <li class="right_block">
		    <form action="${logout_url}" method="POST">
				<input type="submit" value="${logout}" class="button_logout" />
		    </form>
	    </li>
	    <li class="right_block"><a href="#">LANGUAGE</a>
	      <ul class="submenu">
	      <li><a href="set-language?lang=en">EN</a></li>
	      <li><a href="set-language?lang=ru">RU</a></li>
	      </ul>
	    </li>
    </ul>
<div>
  <h1 class="main_h">You are on the main page!</h1>
</div>
    <div id="footer" style="flex: 0 0 auto">
        <footer>
            <div style="margin-top:5px;">
                <p>Epam Systems © 2018, All Rights Reserved
                    <br>
                    <span>Web Design By: Alexe  Sosenkov</span></p>
            </div>
        </footer>
     </div>
</body>
</html>