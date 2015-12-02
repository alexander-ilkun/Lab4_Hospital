<%-- 
    Document   : lib
    Created on : Nov 25, 2015, 3:01:48 PM
    Author     : alexander-ilkun
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="internationalization.text" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<c:url value="/css/material.indigo-blue.min.css" />">
<link rel="stylesheet" href="<c:url value="/css/icon.css" />">
<link rel="stylesheet" href="<c:url value="/css/custom.css" />">
<script src="<c:url value="/js/material.min.js" />"></script>
