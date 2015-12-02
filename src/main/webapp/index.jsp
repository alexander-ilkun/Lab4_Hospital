<%-- 
    Document   : index.jsp
    Created on : Nov 25, 2015, 3:09:50 PM
    Author     : alexander-ilkun
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <title>Index</title>
    </head>
    <body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="/template/header.jsp" />

            <main class="mdl-layout__content center_verically">

                <div class="mdl-grid center_verically">

                    <div class="mdl-cell mdl-cell--5-col">
                        <h2><fmt:message key="index.heading1" />
                            <br>
                            <fmt:message key="index.heading2" /></h2>
                        <h4><fmt:message key="index.heading3" /></h4>
                    </div>
                    <div class="mdl-cell mdl-cell--7-col">
                        <img src="<c:url value="/img/logo.png" />" />
                    </div>

                </div>




            </main>

            <jsp:include page="/template/footer.jsp" />
        </div>
    </body>
</html>
