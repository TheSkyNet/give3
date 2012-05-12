<%@include file="/WEB-INF/views/jspf/header.jsp" %>

<div>
Your Info

${user.username}
</div>

<div>
Your Balance

${user.balance}
</div>

<div>
Your Purchases
<table>
	<tr class="header">
		<td>Order Number</td>
		<td>Date</td>
		<td>Status</td>
	</tr>
	<c:forEach var="order" items="${orders}" varStatus="status">
		<tr>
			<td>${order.id}</td>
			<td>${order.date}</td>
			<td>${order.status}</td>
		</tr>
	</c:forEach>
</table>
</div>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
