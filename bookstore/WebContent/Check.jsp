<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="bookstore.vbean.VBook" %>
<%@page import="bookstore.vbean.VCheckout" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<title>�w�����i</title>
</head>

<body>

	<center>
	<h2>�w�����i</h2>
	</center>

	<br><br>
	
	�ȉ����w�����鏤�i�ƍ��v�ł��B
	<br>
	<table border="1">

	<%
		VCheckout ItemsToBuy = (VCheckout)session.getAttribute("ItemsToBuy");
		List<VBook> selecteditems = ItemsToBuy.getSelecteditems();
		Iterator<VBook> it = selecteditems.iterator();
		while (it.hasNext()) {
			VBook item = it.next();
			if (item.isSelected()) { %>
		<tr>
			<td>
				<%= item.getTitle() %>
			</td>
			<td>
				<%= item.getAuthor() %>
			</td>
		</tr>
		<tr>
			<td>
				<%= item.getPublisher() %>
			</td>
			<td>
				<%= item.getPrice() %>
			</td>
		</tr>
		<% } %>
	<% } %>
	</table>
	<br>
	<br>
	���v: <%= ItemsToBuy.getTotal() %> �~

	<form method="post" action="OrderServlet">
		<input type="submit" value="��������" />
	</form>

	<form method="post" action="SearchServlet">
		<input type="hidden" name="keyword" />
		<input type="submit" value="�߂�" />
	</form>

</body>
</html>
