<%@ tag pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="role" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="footer" fragment="true" %>

<c:url var="logout_url" value="/dir/logout" />
<c:url var="medicine_list" value="/dir/medicine-list" />
<c:url var="order_list" value="/dir/order-list" />
<c:url var="main_page" value="/dir/main" />
<c:url var="prescription_list" value="/dir/prescription-list"/>
<c:url var="request_list" value="/dir/request-list"/>
<c:url var="background" value="/static/images/pills.jpg"/>
<c:url var="header_img" value="/static/images/header4-1.jpg"/>

<html lang=ru>
<head>
    <meta charset="UTF-8">

    <fmt:bundle basename="i18n">
        <fmt:message key="authentication.login" var="login"/>
        <fmt:message key="authentication.logout" var="logout"/>
	    <fmt:message key="authentication.header" var="authrization_header"/>
	    <fmt:message key="authentication.explanation" var="explanaition"/>
	    <fmt:message key="login.error" var="login_error_aut"/>
        <fmt:message key="navbar.hello" var="mainPage"/>
	    <fmt:message key="menu.medicine_list" var="medicines"/>
	    <fmt:message key="menu.order_list" var="orders"/>
	    <fmt:message key="menu.prescription_list" var="prescriptions"/>
	    <fmt:message key="menu.request_list" var="requests"/>
	    <fmt:message key="menu.language" var="language"/>
	    <fmt:message key="menu.contacts" var="about_us"/>
    </fmt:bundle>

    <style>
        <jsp:directive.include file="/WEB-INF/css/style.css"/>
        body {
	        background-image: url("${background}");
        }
        div#header {
            background-image: url("${header_img}"); 
        }
	</style>
	<script>
	    <jsp:directive.include file="/WEB-INF/js/dialog.js"/>
	</script>

    <title>Online Pharmacy</title>
</head>

<body>
  <div id="header">
      <header class="header-inner-page-header">ONLINE PHARMACY</header>
  </div>

    <c:if test="${role.equals('CLIENT')}">
        <ul id="menu">
		    <li><a href="${medicine_list}">${medicines}</a></li>
		    <li><a href="${order_list}">${orders}</a></li>
		    <li><a href="${prescription_list}">${prescriptions}</a></li>
		    <li><a href="${request_list}">${requests}</a></li>
		    <li><a href="${main_page}">${about_us}</a></li>
		    <li style="float: right;"><a href="#">${language}</a>
		      <ul class="submenu">
		      <li><a href="set-language?lang=en">EN</a></li>
		      <li><a href="set-language?lang=ru">RU</a></li>
		      </ul>
		    </li>
		    <li class="right_block">
			    <form action="${logout_url}" method="POST">
					<input type="submit" value="${logout}" class="button_logout" />
			    </form>
		    </li>
        </ul>
    </c:if>
    <c:if test="${role.equals('ADMIN')}">
        <nav>
        <ul id="menu">
            <li><a href="${medicine_list}">${medicines}</a></li>
		    <li><a href="${order_list}">${orders}</a></li>
		    <li><a href="${main_page}">${about_us}</a></li>
		    <li style="float: right;"><a href="#">${language}</a>
		      <ul class="submenu">
		      <li><a href="set-language?lang=en">EN</a></li>
		      <li><a href="set-language?lang=ru">RU</a></li>
		      </ul>
		    </li>
		    <li class="right_block">
			    <form action="${logout_url}" method="POST">
					<input type="submit" value="${logout}" class="button_logout" />
			    </form>
		    </li>
        </ul>
        </nav>
    </c:if>
    <c:if test="${role.equals('guest')}">
        <nav>
        <ul id="menu">
		    <li style="float: right;"><a href="#">${language}</a>
		      <ul class="submenu">
		      <li><a href="set-language?lang=en">EN</a></li>
		      <li><a href="set-language?lang=ru">RU</a></li>
		      </ul>
		    </li>
	  </ul>
        </nav>
    </c:if>

<div id="body" style="height: 100%">
    <jsp:doBody/>

        <footer>
        <div id="footer">
            <div style="margin-top: 15px;">
                <p>Epam Systems © 2018, All Rights Reserved
                    <br>
                    <span>Web Design By: Alexei  Sosenkov</span></p>
            </div>
        </div>
        </footer>
</div>

</body>
</html>