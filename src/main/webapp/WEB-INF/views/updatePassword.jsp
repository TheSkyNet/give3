<%@include file="/WEB-INF/views/jspf/header.jsp" %>

    <h3>Reset Password</h3>
    A new password will be emailed to the email address for your account.
    Once you receive this password, you should change it immediately.
    
<form name='f' method='POST'>
<div class="form-container">

    <div class="labels">
        <div>Current Password:</div>
        <div>New Password:</div>
        <div>Repeat Password:</div>
    </div>
    <div class="values">
        <div><input name='oldPassword' type='password' value=''></div>
        <div><input name='newPassword' type='password' value=''></div>
        <div><input name='newPasswordCheck' type='password' value=''></div>
        <div><input type="submit" class="link-button" align="center" value="Submit"  ></div>
    </div>
        
</div>
</form>

<script type="text/javascript">

    // get called when the page loads
    $(function(){
        document.f.username.focus();
    });

</script>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
