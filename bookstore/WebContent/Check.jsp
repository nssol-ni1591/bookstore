<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="bookstore.vbean.VBook" %>
<%@page import="bookstore.vbean.VCheckout" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<title>購入商品</title>
</head>

<body>

	<center>
	<h2>購入商品</h2>
	</center>

	<br><br>
	
	以下が購入する商品と合計です。
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
	合計: <%= ItemsToBuy.getTotal() %> 円

	<form method="post" action="OrderServlet">
		<input type="submit" value="注文する" />
	</form>

	<form method="post" action="SearchServlet">
		<input type="hidden" name="keyword" />
		<input type="submit" value="戻る" />
	</form>

</body>
</html>
