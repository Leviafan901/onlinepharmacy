<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<c:url var="login_url" value="/dir/login" />
	
	<fmt:bundle basename="i18n">
	    <fmt:message key="authentication.login" var="login"/>
	    <fmt:message key="authentication.header" var="authrization_header"/>
	    <fmt:message key="authentication.explanation" var="explanaition"/>
	    <fmt:message key="authentication.error" var="login_error_aut"/>
    </fmt:bundle>

<my:designPattern role="guest">
  <div class="wrapper">
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
				<c:if test="${not empty login_error}">
		    	  <span style="font-size: 10px; text-align: center;">${login_error_aut}</span>
		        </c:if>
			</div>
		</form>
	</div>
</my:designPattern>
