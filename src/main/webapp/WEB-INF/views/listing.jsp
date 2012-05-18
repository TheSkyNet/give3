<%@include file="/WEB-INF/views/jspf/header.jsp" %>
<c:if test="${!ajaxRequest}">

<button <c:if test="${!prevEnabled}">disabled="true"</c:if> onclick="window.location.href='/item/listing?start=${prevStart}&size=${size}'">previous</button>
<button <c:if test="${!nextEnabled}">disabled="true"</c:if> onclick="window.location.href='/item/listing?start=${nextStart}&size=${size}'">next</button>
Showing ${start+1} to ${pageEnd} of ${total}
<div class="listing" >
<table cellspacing="0" frame="void" rules="rows">
	<tr class="header">
		<th>Name</th>
		<th style="width: 100px; text-align: center;">Value</th>
		<c:if test="${admin}" ><th style="text-align: center;">Edit</th></c:if>
		<th style="width: 120px; text-align: center;"></th>
	</tr>
	<c:forEach var="item" items="${items}" varStatus="status">
		<tr>
			<td><div style="text-align: left;">${item.name}</div></td>
			<td><div>${item.value}</div></td>
			<c:if test="${admin}" ><td><a href="/item/${item.id}">Edit Item</a></td></c:if>
			<td><a class="link-button" href="/item/buy/${item.id}">Buy Now</a></td>
		</tr>
	</c:forEach>
</table>
</div>
	
</c:if>
<%@include file="/WEB-INF/views/jspf/footer.jsp" %>


