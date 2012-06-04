<%@include file="/WEB-INF/views/jspf/header.jsp" %>

Your Info

<form:form name="f" method="post"> 
<div class="form-container" style="margin:0;">
	<div class="labels">
		<div>Username</div>
		<div>Password</div>
		<div>Balance</div>
	</div>
	<div class="values">
		<div><input type="text" value="${user.username}" disabled="true" /></div>
<%-- 		<div><input type="text" value="${user.email}" name="emailAddress"/></div> --%>
		<div><a href="/account/password/update">Update</a></div>
		<div><input type="text" value="${user.balance}" disabled="true" /></div>
<!-- 		<div><button class="link-button" type="submit">Update</button></form></div> -->
	</div>
</div>
</form:form>


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
