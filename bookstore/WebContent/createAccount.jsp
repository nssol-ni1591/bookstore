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
	<h2>�A�J�E���g�쐬</h2>

	<form action="CreateUserServlet" method="post">
		����: <input type="text" name="name" /><br>
		E-Mail: <input type="text" name="email" /><br>
		���O�C����: <input type="text" name="account" /><br>
		�p�X���[�h: <input type="password" name="passwd" /><br>
		�p�X���[�h(�m�F): <input type="password" name="passwd2" /><br>
		<input type="submit" value="�A�J�E���g�쐬" />
		<input type="reset" value="���Z�b�g"/>
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

	<a href="Login.jsp">�g�b�v��</a>
</body>
</html>