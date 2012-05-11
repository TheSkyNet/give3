<%@include file="/WEB-INF/views/jspf/header.jsp" %>

<div>
You are purchasing ${item.name} for ${item.value} hours.
</div>
<div>
Your remaining balance will be ${user.balance - item.value} hours.
</div>
<div>
By clicking on "Finish" below, the following will happen:
</div>
<ol>
	<li>Give3.org will deduct the listed amount from your account balance hours</li>
	<li>An order will be generated and the item will be reserved for you</li>
	<li>Give3.org will send you an email with the order details for your records</li>
	<li>You may pick up the item at a distribution location</li>
</ol>

<!-- if you don't specify action="..." it posts to the url this page came from -->
<form:form method="post">
	<input type="submit" value="Finish" />
</form:form>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
