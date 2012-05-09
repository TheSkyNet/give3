<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
	<title>One 2 Many</title>
	<link type="text/css" href="/resources/application.css" rel=StyleSheet media=screen />
	<link href="<c:url value="/resources/form.css" />" rel="stylesheet"  type="text/css" />		
	<link href="<c:url value="/resources/jqueryui/1.8/themes/base/jquery.ui.all.css" />" rel="stylesheet" type="text/css"/>
	<link rel="shortcut icon" href="/resources/favicon.ico" type="image/x-icon" />
</head>
<body>

<%--
this could be set from the controller, or from here.
I'm choosing here to avoid duplicate code in controllers, also this is more view-related
--%>
<c:set var="authenticated" value="${false}"/>
<sec:authorize access="isAuthenticated()">
    <c:set var="authenticated" value="${true}"/>
</sec:authorize>

<c:set var="admin" value="${false}"/>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <c:set var="admin" value="${true}"/>
</sec:authorize>

<div class="header">
	<img src="/resources/images/logo.png" alt="logo" />
	<c:choose>
		<c:when test="${authenticated}" >
			<a href="/j_spring_security_logout" >logout</a>
	    	<span>Welcome, <sec:authentication property="principal.username"/></span>
		</c:when>
		<c:otherwise>
			<a href="/login">Login</a>
			<a href="/register">Register</a>
		</c:otherwise>
	</c:choose>
</div>

<div class="navigation" >
	<a href="/">Home</a>
	<a href="/donate">Donate</a>
	<a href="/volunteer">Volunteer</a>
	<a href="/item/listing">Buy</a>
	<a href="/about">About</a>
</div>

<c:if test="${authenticated}" >
	<div class="authenticated-navigation">
		<a href="/redeem">Redeem Coupons</a>
		<a href="/account">Account</a>
	</div>
</c:if>

<c:if test="${admin}" >
	<div class="admin-navigation">
		<a href="/item">Post Item</a>
		<a href="/users">User Management</a>
	</div>
</c:if>


<div id="main">

<div id="content" class="bordered" style="padding: 10px; min-height: 350px;">
