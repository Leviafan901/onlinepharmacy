<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="logout_url" value="/dir/logout"/>
<head>
<title>Main page</title>
</head>

<body>
	<h1>You get main page</h1>
	<form action="${logout_url}" class="button_header" method="POST">
		<input type="submit" value="ВЫЙТИ" />
	</form>
</body>
</html>