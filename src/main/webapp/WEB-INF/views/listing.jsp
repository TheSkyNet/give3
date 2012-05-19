<%@include file="/WEB-INF/views/jspf/header.jsp" %>
<c:if test="${!ajaxRequest}">

<button <c:if test="${!prevEnabled}">disabled="true"</c:if> onclick="window.location.href='/item/listing?start=${prevStart}&size=${size}'">previous</button>
<button <c:if test="${!nextEnabled}">disabled="true"</c:if> onclick="window.location.href='/item/listing?start=${nextStart}&size=${size}'">next</button>
Showing ${start+1} to ${pageEnd} of ${total}
<div class="listing" >
<table cellspacing="0" frame="void" rules="rows">
	<tr class="header">
		<th>Name</th>
		<th style="width: 100px;">Value</th>
		<th style="width: 120px; text-align: center;"></th>
	</tr>
	<c:forEach var="item" items="${items}" varStatus="status">
		<tr>
			<td><a class="item" href="/item/${item.id}">${item.name}</a></td>
			<td>${item.value}</td>
			<td><a class="link-button" href="/item/buy/${item.id}">Buy Now</a></td>
		</tr>
	</c:forEach>
</table>
</div>
	
</c:if>
<%@include file="/WEB-INF/views/jspf/footer.jsp" %>


