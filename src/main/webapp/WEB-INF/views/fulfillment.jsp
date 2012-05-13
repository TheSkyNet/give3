<%@include file="/WEB-INF/views/jspf/header.jsp" %>

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
			<td><a class="link-button">Fulfill</a></td>
		</tr>
	</c:forEach>
</table>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
