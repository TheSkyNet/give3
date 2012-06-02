
<%@include file="/WEB-INF/views/jspf/header.jsp" %>

<script type="text/javascript">
    function checkPassword()
    {
    	var username = $('#username').get(0);
        var passwordField = $('#password').get(0);
        var repeatPasswordField = $('#repeatPassword').get(0);
        var submitButton = $('#submitButton').get(0);
        var empty = passwordField.value.length === 0 || repeatPasswordField.value.length === 0 || username.value.length === 0;
        var passwordMismatch = (passwordField.value !== repeatPasswordField.value);
        submitButton.setAttribute('disabled', passwordMismatch);
        if(passwordMismatch || empty) {
        	$('#submitButton').addClass('disabled');
        	submitButton.setAttribute('disabled', passwordMismatch);
        }
       	else {
        	$('#submitButton').removeClass('disabled');
        	$('#submitButton').removeAttr('disabled');
       	}
    }
</script>

<form:form name="f" modelAttribute="person" method="post" class="button-themed" action="/register">
<div class="form-container">
    <h3>Register</h3>
    <div class="labels">
        <div>Username (email):</div>
        <div>Password:</div>
        <div>Repeat Password:</div>
    </div>
<!--     onmouseup="checkPassword();" -->
    <div class="values">
        <div><form:input id="username" name="j_username" path="username" onkeyup="checkPassword();" /></div>
        <div><form:password id="password" path="password"   onkeyup="checkPassword();"></form:password></div>
        <div><input id="repeatPassword" type="password" onkeyup="checkPassword();" /></div>
        <div><input id="submitButton" type="submit" class="link-button disabled" align="center" value="Submit" disabled="true" >
    </div>
    </div>
    <div class="errors">
        <div><form:errors path="username" /></div>
        <div></div>
        <div></div>        
    </div>
</div>

</form:form>

<script type="text/javascript">

    // get called when the page loads
    $(function(){
        document.f.username.focus();
    });

</script>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>