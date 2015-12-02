<%-- 
    Document   : header
    Created on : Nov 25, 2015, 11:17:28 AM
    Author     : alexander-ilkun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/WEB-INF/tlds/security.tld" prefix="security"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="internationalization.text" />

<header class="mdl-layout__header">
    <div class="mdl-layout__header-row" style="padding-left:20px">
        <!-- Title -->
        <span class="mdl-layout-title">
            <a class="mdl-navigation__link" style="font-size:large" href="<c:url value="/index.jsp" />">
                <fmt:message key="header.projectname" />
            </a>
        </span>
        <!-- Add spacer, to align navigation to the right -->
        <div class="mdl-layout-spacer"></div>
        <!-- Navigation. We hide it in small screens. -->
        <nav class="mdl-navigation mdl-layout--large-screen-only">
            <security:isAuthenticated value="false">
                <a class="mdl-navigation__link" href="<c:url value="/login.jsp" />"><fmt:message key="header.navigation.login" /></a>
                <a class="mdl-navigation__link" href="<c:url value="/register.jsp" />"><fmt:message key="header.navigation.register" /></a>
            </security:isAuthenticated>
            <security:hasRole roles="ROLE_DOCTOR,ROLE_NURSE">
                <a class="mdl-navigation__link" href="<c:url value="/patients.jsp" />"><fmt:message key="header.navigation.patients" /></a>
            </security:hasRole>
            <security:isAuthenticated value="true">
                <a class="mdl-navigation__link" href="<c:url value="/Controller?command=logout" />"><fmt:message key="header.navigation.logout" /></a>
            </security:isAuthenticated>
        </nav>
    </div>
</header>