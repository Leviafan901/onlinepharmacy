<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
	    <fmt:message key="orders.order_successed" var="successed_info"/>
	    <fmt:message key="orders.order_fail" var="fail_info"/>
</fmt:bundle>

<my:designPattern role="${sessionScope.role}">
    <div class="wrapper">
    	<c:if test="${not empty successed_message}">
			<p>${successed_info}</p>
		</c:if>
		<c:if test="${not empty fail_message}">
			<p>${fail_info}</p>
		</c:if>
    </div>
</my:designPattern>