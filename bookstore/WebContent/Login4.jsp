<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>
<%@ page import="java.util.Map" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<title>���O�C��</title>
</head>
<style>
h2 {
  text-align: center;
}
</style>
<body>
	<h2>���O�C��</h2>

	<form action="LoginServlet4" method="post">
		���O�C����: <input type="text" name="account" /><br>
		�p�X���[�h: <input type="password" name="passwd" /><br>
		<input type="submit" value="���O�C��" />
		<input type="reset" value="���Z�b�g"/>

		<input type="hidden" name="jpaModule" value="<%= request.getParameter("jpaModule") %>"/>
		<input type="hidden" name="txType" value="<%= request.getParameter("txType") %>"/>
		<br>

		<% if (request.getAttribute("errors") != null) {
			@SuppressWarnings("unchecked")
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
