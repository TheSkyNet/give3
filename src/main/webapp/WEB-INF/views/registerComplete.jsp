<%@include file="/WEB-INF/views/jspf/header.jsp" %>

<div style="margin: 15px; font-weight: bold; text-align:center;" >
    Your account has been created.
</div>

<form name='f' action='/j_spring_security_check' method='POST'>
    
    <div style="text-align: center; margin-top: 10px; margin-left: auto; margin-right: auto; width: 150px;">
        <input class="button-cell" name="submit" type="submit" value="Click here" />
    </div>
    
    <div style="display: none;">
        <input type='text' name='j_username' value="${username}">
        <input type='password' name='j_password' value="${password}"/>
        <input type='checkbox' name='_spring_security_remember_me' value="true"/>
        <form:password id="encodedPassword" path="password"></form:password>
        <form:hidden path="username"></form:hidden>
    </div>
    
</form>

<%@include file="/WEB-INF/views/jspf/footer.jsp" %>
