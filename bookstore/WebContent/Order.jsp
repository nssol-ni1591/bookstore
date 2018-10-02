<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Map" %>
<%@page import="bookstore.vbean.VBook" %>
<%@page import="bookstore.vbean.VCheckout" %>
<%@page import="bookstore.vbean.VCustomer"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<title>注文確定</title>
</head>
<style>
h2 {
  text-align: center;
}
</style>
<body>
	<h2>注文確認</h2>
	<br><br>
	
	以下のとおり、注文を受け付けました。
	<br>
	商品:
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
	<br>
	<br>
	<br>
	お客様情報
	<br>
	<br>
	<%
		VCustomer customer = (VCustomer)request.getAttribute("Customer");
	%>
	UserID:	<%= customer.getUid() %>	<br>
	氏名:	<%= customer.getName() %>	<br>
	E-Mail:	<%= customer.getEmail() %>	<br>

	<br>
	<a href="Login.jsp">トップに戻る</a>
</body>

</html>