<%-- 
    Document   : error
    Created on : Nov 26, 2015, 11:53:09 AM
    Author     : alexander-ilkun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="internationalization.text" />

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/template/lib.jsp" />
        <title>Error</title>
    </head>
    <body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="/template/header.jsp" />

            <main class="mdl-layout__content center_verically">
                <h2>${not empty param.errorMessage ? param.errorMessage : errorMessage}</h2>
            </main>

            <jsp:include page="/template/footer.jsp" />
        </div>
    </body>
</html>

