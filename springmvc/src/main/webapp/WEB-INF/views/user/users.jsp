<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hi Users!</h1>
	<table>
	<tr><td>1</td></tr>
	<tr><td>2</td></tr>
		<c:forEach var="user" items="${users}" varStatus="status">
			<tr>
				<td>${user.userId}</td>
			</tr>
		</c:forEach>
	</table>
	<form action="users" method="post">
		<div class="button">
			<button type="submit">create user</button>
		</div>
	</form>
</body>
</html>
