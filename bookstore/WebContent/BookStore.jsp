<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Map" %>
<%@page import="bookstore.vbean.VBook" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J" />
</head>
<style>
h2 {
  text-align: center;
}
</style>
<body>
	<h2>���i�ꗗ</h2>
	<br>
	<form method="post" action="SearchServlet">
		����: 
		<input type="text" name="keyword" />
		<input type="submit" value="����" />
	</form>
	<% if (request.getAttribute("errors") != null) {
		Map<String, String> errors = (Map<String, String>)request.getAttribute("errors"); %>
		<br>
		<font color="red">
			<%= errors.get("productalart") %>
		</font>
		<br>
		<br>
	<% } %>

	<form method="post" action="AddToCartServlet">
 	<table border="1">

	<% List<VBook> list = (List<VBook>)session.getAttribute("ProductListView");
	  if (list == null) {
		// Do nothing		  
	  }
	  else {
		  Iterator<VBook> it = list.iterator();
		  while (it.hasNext()) {
			  VBook item = it.next();
	%>

		<tr>
			<th rowspan="2">
				<input type="checkbox" name="selecteditems" value="<%= item.getIsbn() %>"
					<% if (item.isSelected()) { %>
						checked 
					<% } %>
				/>
			</th>
			<td colspan="3">
				<%= item.getTitle() %>
			</td>
		</tr>
		<tr>
			<td>
				<%= item.getAuthor() %>
			</td>
			<td>
				<%= item.getPublisher() %>
			</td>
			<td>
				<%= item.getPrice() %> �~
			</td>
		</tr>

		<% } %>
	<% } %>
	</table>	
	<br>

	<input type="submit" value="�J�[�g�ɒǉ�" />
	</form>

	<form method="post" action="CheckoutServlet">
		<input type="submit" value="���i�w��" />
	</form>

	<form method="post" action="Logout.jsp">
		<input type="submit" value="logout" />
	</form>
</body>
</html>
