<%@include file="/WEB-INF/views/jspf/header.jsp" %>
<c:if test="${!ajaxRequest}">

<button <c:if test="${!prevEnabled}">disabled="true"</c:if> onclick="window.location.href='/item/listing?start=${prevStart}&size=${size}'">previous</button>
<button <c:if test="${!nextEnabled}">disabled="true"</c:if> onclick="window.location.href='/item/listing?start=${nextStart}&size=${size}'">next</button>
Showing ${start+1} to ${pageEnd} of ${total}
<div class="listing" >
<table cellspacing="0" frame="void" rules="rows">
	<tr class="header">
		<td>Name</td>
		<td>Description</td>
		<td>Value</td>
		<td><c:if test="${admin}" >Edit</c:if></td>
		<td>Buy</td>
	</tr>
	<c:forEach var="item" items="${items}" varStatus="status">
		<tr>
			<td>${item.name}</td>
			<td>${item.description}</td>
			<td>${item.value}</td>
			<td><c:if test="${admin}" ><a href="/item/${item.id}">Edit Item</a></c:if></td>
			<td><a class="link-button buy" href="/item/buy/${item.id}">Buy Now</a></td>
		</tr>
	</c:forEach>
</table>
</div>
	
</c:if>
<%@include file="/WEB-INF/views/jspf/footer.jsp" %>


