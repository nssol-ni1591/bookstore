<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>

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
	<%
		if (session == null) {
			application.getRequestDispatcher("sessionError.html").forward(request, response);
		}
		session.invalidate();
	%>
	<h2>���O�A�E�g���܂���</h2>

	<a href="index.jsp">�g�b�v��</a>

</body>

</html>