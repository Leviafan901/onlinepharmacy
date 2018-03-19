<%@ taglib prefix="img" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url var="account_url" value="/error"/>

<html>

<head>
    <title>403</title>
</head>
<body>
<div class="error">
    <div class="error-code m-b-10 m-t-20">403 FORBIDDEN <i class="fa fa-warning"></i></div>
    <h3 class="font-bold">The server understood the request but refuses to authorize it...</h3>

    <div class="error-desc">
        A server that wishes to make public why the request has been forbidden can describe that reason in the response payload (if any). <br/>


    </div>
</div>
</body>
</html>
