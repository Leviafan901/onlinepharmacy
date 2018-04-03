<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<fmt:bundle basename="i18n">
  <fmt:message key="navbar.hello" var="mainPage"/>
  <fmt:message key="main.content" var="mainContent"/>
</fmt:bundle>

<my:designPattern role="${sessionScope.role}">
  <div>
    <h1 class="main_h">${mainContent}</h1>
  </div>
</my:designPattern>
    