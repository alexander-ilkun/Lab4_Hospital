<%-- 
    Document   : error
    Created on : Nov 26, 2015, 11:53:09 AM
    Author     : alexander-ilkun
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/WEB-INF/tlds/security.tld" prefix="security"%>
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
        <title>Patients</title>
    </head>
    <body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="/template/header.jsp" />

            <main class="mdl-layout__content">

                <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="min-width:100%;">
                    <thead>
                        <tr>
                            <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patients.table.id" /></th>
                            <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patients.table.name" /></th>
                            <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patients.table.discharged" /></th>
                            <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patients.table.action" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${patients}" var="patient">
                        <form action="<c:url value="/patient-detail.jsp?id=" />" >
                            <tr style="height:60px;">
                                <td class="mdl-data-table__cell--non-numeric">${patient.id}</td>
                                <td class="mdl-data-table__cell--non-numeric">${patient.name}</td>
                                <td class="mdl-data-table__cell--non-numeric">${patient.discharged}</td>
                                <td class="mdl-data-table__cell--non-numeric">
                                    <input type="hidden" name="id" value="${patient.id}" />
                                    <input type="submit" value="<fmt:message key="patients.table.button" />" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" style="min-width:100%;"/>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                    </tbody>
                </table>
                <security:hasRole roles="ROLE_DOCTOR">
                    <center>
                        <div class="mdl-card mdl-shadow--2dp center_verically" style="margin-top:20px;padding:20px;min-width:100%;">
                            <form action="<c:url value="/Controller" />" method="POST">
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <input type="text" name="name" class="mdl-textfield__input" id="name" required/>
                                    <label class="mdl-textfield__label" for="name"><fmt:message key="patients.name" /></label>
                                </div>
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                    <input type="text" name="diagnosis" class="mdl-textfield__input" id="diagnosis" required/>
                                    <label class="mdl-textfield__label" for="diagnosis"><fmt:message key="patients.diagnosis" /></label>
                                </div>
                                <input type="hidden" name="command" value="addPatient" />
                                <input name="submit" type="submit" value="<fmt:message key="patients.button" />" class="btn mdl-button mdl-js-button mdl-button--raised mdl-button--accent mdl-js-ripple-effect" />
                            </form>
                        </div>
                    </center>
                </security:hasRole>
            </main>

            <jsp:include page="/template/footer.jsp" />

        </div>
    </body>
</html>

