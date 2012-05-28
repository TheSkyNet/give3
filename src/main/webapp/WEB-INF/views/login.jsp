<%@include file="/WEB-INF/views/jspf/header.jsp" %>

<form name='f' action='/j_spring_security_check' method='POST'>
<div class="form-container">
    <h3>Login with Username and Password </h3>
    <a style="font-size: 11px; display: block; height: 30px;" href="/account/password/reset" >(forgot your password?)</a>
    
    <div class="labels">
        <div>Username:</div>
        <div>Password:</div>
        <div>Remember me</div>
    </div>
    <div class="values">
        <div><input type='text' name='j_username' value=''></div>
        <div><input type='password' name='j_password'/></div>
        <div><input type='checkbox' name='_spring_security_remember_me'/></div>
        <div><input type="submit" class="link-button" align="center" value="Submit"  >
    </div>
    </div>
    <div class="errors">
        <div></div>
        <div></div>
        <div><c:if test="${ param['login_error'] == 1 }"><div style="color: red; font-weight: bold;">Login failed.</div></c:if></div>        
    </div>
        
</div>
</form>

<script type="text/javascript">

    // get called when the page loads
    $(function(){
        document.f.j_username.focus();
    });

</script>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
