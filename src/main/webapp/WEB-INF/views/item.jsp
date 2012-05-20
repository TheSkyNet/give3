<%@include file="/WEB-INF/views/jspf/header.jsp" %>

<c:if test="${!ajaxRequest}">
	<div id="formsContent">
		<form:form style="width: 100%;" id="form" method="post" modelAttribute="item">
			<div>
		  		<c:if test="${not empty message}">
					<div id="message" class="success">${message}</div>	
		  		</c:if>
		  		<s:bind path="*">
		  			<c:if test="${status.error}">
				  		<div id="message" class="error">Form has errors</div>
		  			</c:if>
		  		</s:bind>
			</div>

	  		<form:label path="name">
	  			Name <form:errors path="name" cssClass="error" />
	 		</form:label>
	  		<form:input path="name" />
	  		<form:label path="description">
	  			Description <form:errors path="description" cssClass="error" />
	 		</form:label>
	  		<form:textarea style="max-width: 740px; width: 740px;" path="description" />
	  		
	  		<form:label path="value">
	  			Value <form:errors path="value" cssClass="error" />
	 		</form:label>
	  		<form:input path="value" />

			<a class="link-button" href="/item/buy/${item.id}">Buy Now</a>

			<c:if test="${admin}"><p><button type="submit">Update</button></p></c:if>
			
		</form:form>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#form").submit(function() {  
					$.post($(this).attr("action"), $(this).serialize(), function(html) {
						$("#formsContent").replaceWith(html);
						$('html, body').animate({ scrollTop: $("#message").offset().top }, 500);
					});
					return false;  
				});
			});
		</script>
	</div>
</c:if>
<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
