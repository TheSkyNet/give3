<%@include file="/WEB-INF/views/jspf/header.jsp" %>



<!-- if you don't specify action="..." it posts to the url this page came from -->
<form:form name="f" class="donate-form" method="post"> 
<div class="description">Let us know what you have, and we'll coordinate a pick up!</div>
<div class="description">We accept: consumer electronics: iphones, ipods, cell phones, laptops, desktops, and small appliances such as toaster ovens and smaller microwaves.
Electronics must be less than three years old, small appliances may be older but must be in good condition.</div>
	<div class = "description">Enter your donation information here and we will contact you.</div>
	<div><span>Name:</span><input type="text" name="donerName" size="30" /></div>
	<div><span>Email:</span><input type="text" name="emailAddress" size="30" /></div>
	<div><span>Item:</span><input type="text" name="itemName" size="30" /></div>
	<div><span>Description:</span><input type="text" name="itemDescription" size="30" /></div>
	<div><span>Condition:</span><input type="text" name="itemCondition" size="30" /></div>
	<div><span></span><input type="submit" value="Submit" /></div>
</form:form>

<script type="text/javascript">

    // get called when the page loads
    $(function(){
        document.f.donerName.focus();
    });

</script>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
