<%@include file="/WEB-INF/views/jspf/header.jsp" %>

<table>
	<tr class="header">
		<td>Order Number</td>
		<td>Date Ordered</td>
		<td>Customer</td>
	</tr>
	<c:forEach var="order" items="${orders}" varStatus="status">
		<tr>
			<td>${order.id}</td>
			<td><fmt:formatDate pattern="MMM dd" value="${order.date}" /></td>
			<td>${order.user.username}</td>
			<td><form method="post" action="/fulfillment/fulfill/${order.id}"><button class="link-button" type="submit">Fulfill</button></form></td>
		</tr>
	</c:forEach>
</table>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
