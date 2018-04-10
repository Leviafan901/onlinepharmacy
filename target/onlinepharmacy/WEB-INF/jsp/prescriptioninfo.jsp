<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:designPattern role="${sessionScope.role}">
    <div class="wrapper">
    	<c:if test="${not empty successed_message}">
			<p>New prescription is successfully created!</p>
		</c:if>
		<c:if test="${not empty fail_message}">
			<p>Failed to create new prescription!</p>
		</c:if>
    </div>
</my:designPattern>