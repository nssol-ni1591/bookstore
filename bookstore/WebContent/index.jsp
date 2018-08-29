<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>
<%@ page import="java.util.Map" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
</head>

<body>

	<center>
		<h2>ログイン</h2>
	</center>

	<form action="LoginServlet" method="post">
		ログイン名: <input type="text" name="account" /><br>
		パスワード: <input type="password" name="passwd" /><br>
		<input type="submit" value="ログイン" />
		<input type="reset" value="リセット"/>
		<br>

		<% if (request.getAttribute("errors") != null) {
			Map<String, String> errors = (Map<String, String>)request.getAttribute("errors"); %>
			<br>
			<font color="red">
				<%= errors.get("illegallogin") %>
			</font>
			<br>
		<% } %>

		<br>
		アカウントを作成していない人は
			<a href="createAccount.jsp">こちら</a>
	
	</form>
</body>
</html>
