<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>
<%@ page import="java.util.Map" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
</head>

<body>

	<center>
		<h2>���O�C��</h2>
	</center>

	<form action="LoginServlet" method="post">
		���O�C����: <input type="text" name="account" /><br>
		�p�X���[�h: <input type="password" name="passwd" /><br>
		<input type="submit" value="���O�C��" />
		<input type="reset" value="���Z�b�g"/>
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
		�A�J�E���g���쐬���Ă��Ȃ��l��
			<a href="createAccount.jsp">������</a>
	
	</form>
</body>
</html>
