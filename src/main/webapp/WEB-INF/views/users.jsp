<%@include file="/WEB-INF/views/jspf/header.jsp" %>


<table cellspacing="0" frame="void" rules="rows">
	<tr class="header">
		<th>Name</th>
		<th>Email</th>
		<th>Balance</th>
	</tr>
	<c:forEach var="user" items="${users}" varStatus="status">
		<tr>
			<td>${user.username}</td>
			<td>${user.email}</td>
			<td>${user.balance}</td>
		</tr>
	</c:forEach>
</table>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
