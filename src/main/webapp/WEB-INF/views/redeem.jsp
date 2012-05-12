<%@include file="/WEB-INF/views/jspf/header.jsp" %>

<c:if test="${!ajaxRequest}">
	<div id="formsContent">
		<h2>Submit Item</h2>
		<form:form id="form" method="post" modelAttribute="karmakash" cssClass="cleanform">
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
		  	<fieldset>
		  		<legend>Redeem Code</legend>
		  		<form:label path="code">
		  			Code <form:errors path="code" cssClass="error" />
		 		</form:label>
		  		<form:input path="code" />
		  	</fieldset>

			<p><button type="submit">Submit</button></p>
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
