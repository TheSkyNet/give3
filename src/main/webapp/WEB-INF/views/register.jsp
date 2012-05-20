
<%@include file="/WEB-INF/views/jspf/header.jsp" %>

<script type="text/javascript" src="/resources/js/application.md5.js"></script>
<script type="text/javascript" src="/resources/js/md5.js"></script>

<script type="text/javascript">
    function updatePasswordEncoding()
    {
        updatePasswordEncodingById('unencodedPassword', 'encodedPassword');
    }
</script>

<form:form name="f" modelAttribute="person" method="post" class="button-themed" action="/register">
<div class="form-container">
    <h3>Register</h3>
    <div class="labels">
        <div>Username:</div>
        <div>Password:</div>
        <div>Repeat Password:</div>
    </div>
    <div class="values">
        <div><form:input name="j_username" path="username" /></div>
        <div><input id="unencodedPassword" name="unencodedPassword" type="password"  onmouseup="updatePasswordEncoding();" onkeyup="updatePasswordEncoding();" /></div>
        <div><input type="password"></div>
        <form:password style="display: none;" id="encodedPassword" path="password"></form:password>
        <div><input type="submit" class="submit-button" align="center" value="Submit"  >
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