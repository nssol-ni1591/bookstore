<%@page import="bookstore.vbean.VCustomer"%>
<%@ page language="java" contentType="text/html; charset=Windows-31J" pageEncoding="windows-31j" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Map" %>
<%@page import="bookstore.vbean.VBook" %>
<%@page import="bookstore.vbean.VCheckout" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
</head>

<body>

	<center>
		<h2>�����m�F</h2>
	</center>

	<br><br>
	
	�ȉ��̂Ƃ���A�������󂯕t���܂����B
	<br>
	���i:
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
	<br>
	<br>
	<br>
	���q�l���
	<br>
	<br>
	<%
		VCustomer customer = (VCustomer)request.getAttribute("Customer");
	%>
	UserID:	<%= customer.getUid() %>	<br>
	����:	<%= customer.getName() %>	<br>
	E-Mail:	<%= customer.getEmail() %>	<br>

	<br>
	<a href="Login.jsp">�g�b�v�ɖ߂�</a>
</body>

</html>