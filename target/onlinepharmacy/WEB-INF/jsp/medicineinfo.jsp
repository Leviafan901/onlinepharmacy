<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:designPattern role="ADMIN">
    <div class="wrapper">
    	<c:if test="${not empty successed_message}">
			<p>You update medicine successfully!</p>
		</c:if>
		<c:if test="${not empty fail_message}">
			<p>Can't update medicine!</p>
		</c:if>
		<c:if test="${not empty successed_creation_message}">
			<p>You create medicine successfully!</p>
		</c:if>
		<c:if test="${not empty fail_creation_message}">
			<p>Can't create medicine!</p>
		</c:if>
    </div>
</my:designPattern>