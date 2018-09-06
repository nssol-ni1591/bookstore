<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>
<%@page import="java.util.Map"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
</head>
<style>
h2 {
  text-align: center;
}
</style>
<body>
	<h2>アカウント作成</h2>

	<form action="CreateUserServlet" method="post">
		氏名: <input type="text" name="name" /><br>
		E-Mail: <input type="text" name="email" /><br>
		ログイン名: <input type="text" name="account" /><br>
		パスワード: <input type="password" name="passwd" /><br>
		パスワード(確認): <input type="password" name="passwd2" /><br>
		<input type="submit" value="アカウント作成" />
		<input type="reset" value="リセット"/>
	</form>

	<% if (request.getAttribute("message") != null) { %>
		<br>
		<font color="red">
			request.getAttribute("message").get("illegalcreateuser")
		</font>
		<br>
	<% } %>

	<% if (request.getAttribute("errors") != null) {
		Map<String, String> errors = (Map<String, String>)request.getAttribute("errors");
		%>
		<br>
		<font color="red">
			<%= errors.get("illegalcreateuser") %>
		</font>
		<br>
	<% } %>

	<a href="Login.jsp">トップへ</a>
</body>
</html>