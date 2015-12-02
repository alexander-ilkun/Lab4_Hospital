<%-- 
    Document   : index.jsp
    Created on : Nov 25, 2015, 3:09:50 PM
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
        <link rel="stylesheet" href="<c:url value="/css/selectfield.css" />">
        <script src="<c:url value="/js/selectfield.js" />"></script>
        <title>Patient detail</title>
    </head>
    <body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="/template/header.jsp" />

            <main class="mdl-layout__content">

                <div class="mdl-grid center_verically">

                    <div class="mdl-cell mdl-cell--4-col">
                        <!-- Square card -->

                        <div class="demo-card-square mdl-card mdl-shadow--2dp">
                            <div class="mdl-card__title mdl-card--expand">
                                <h2 class="mdl-card__title-text"><fmt:message key="patient.information" /></h2>
                            </div>

                            <div class="mdl-card__actions mdl-card--border">
                                <fmt:message key="patient.name" /> ${patient.name}
                            </div>
                            <div class="mdl-card__actions mdl-card--border">
                                <fmt:message key="patient.discharged" /> ${patient.discharged}
                            </div>
                            <div class="mdl-card__actions mdl-card--border">
                                <fmt:message key="patient.diagnosis" />
                            </div>
                            <div class="mdl-card__supporting-text">
                                ${patient.diagnosis}
                            </div>
                        </div>
                    </div>
                    <div class="mdl-cell mdl-cell--4-col">
                        <security:hasRole roles="ROLE_DOCTOR">
                            <div class="demo-card-square mdl-card mdl-shadow--2dp" style="padding:20px;">
                                <form action="<c:url value="/Controller" />" method="POST">
                                    <div class="mdl-textfield mdl-js-textfield">
                                        <textarea class="mdl-textfield__input" type="text" name="finalDiagnosis" rows= "5" id="finalDiagnosis" style="resize:none;" required></textarea>
                                        <label class="mdl-textfield__label" for="finalDiagnosis"><fmt:message key="patient.finaldiagnosis" /></label>
                                    </div>
                                    <input type="hidden" name="command" value="discharge" />
                                    <input type="hidden" name="patientId" value="${patient.id}" />   
                                    <br>
                                    <center>
                                        <input name="submit" type="submit" value="<fmt:message key="patient.discharge.button" />" class="btn mdl-button mdl-js-button mdl-button--raised mdl-button--accent mdl-js-ripple-effect" style="min-width:100%" 
                                               <c:if test="${patient.discharged eq true}">
                                                   disabled
                                               </c:if>
                                               />
                                    </center>
                                </form>
                            </div>
                        </security:hasRole>
                    </div>
                    <div class="mdl-cell mdl-cell--4-col">
                        <security:hasRole roles="ROLE_DOCTOR">
                            <div class="demo-card-square mdl-card mdl-shadow--2dp" style="padding:16px;">
                                <form action="<c:url value="/Controller" />" method="POST">
                                    <div class="mdl-select mdl-js-select mdl-select--floating-label">
                                        <select class="mdl-select__input" id="prescriptionId" name="prescriptionId" required>
                                            <option value=""></option>
                                            <c:forEach items="${prescriptions}" var="prescription">
                                                <option value="${prescription.id}">${prescription.name}</option>
                                            </c:forEach>
                                        </select>
                                        <label class="mdl-select__label" for="prescriptionId"><fmt:message key="patient.prescriptiontype" /></label>
                                    </div>
                                    <div class="mdl-textfield mdl-js-textfield">
                                        <textarea class="mdl-textfield__input" type="text" name="description" rows= "2" id="description" style="resize:none;" required></textarea>
                                        <label class="mdl-textfield__label" for="description"><fmt:message key="patient.prescriptiondescription" /></label>
                                    </div>
                                    <input type="hidden" name="command" value="addConcretePrescription" />
                                    <input type="hidden" name="patientId" value="${patient.id}" />
                                    <br>
                                    <center>
                                        <input type="submit" value="<fmt:message key="patient.prescription.button" />" 
                                               class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" 
                                               style="min-width:100%;" 

                                               <c:if test="${patient.discharged eq true}">
                                                   disabled
                                               </c:if>
                                               />
                                    </center>
                                </form>
                            </div>
                        </security:hasRole>
                    </div>


                </div>

                <div class="mdl-grid">
                    <div class="mdl-cell mdl-cell--12-col">
                        <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="min-width:100%;">
                            <thead>
                                <tr>
                                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patient.table.id" /></th>
                                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patient.table.prescription" /></th>
                                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patient.table.description" /></th>
                                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patient.table.performed" /></th>
                                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patient.table.performedby" /></th>
                                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="patient.table.action" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${concretePrescriptions}" var="concretePrescription">
                                <form action="<c:url value="/Controller" />" method="POST">
                                    <tr style="height:60px;">
                                    <a href="patient-detail.jsp"></a>
                                    <td class="mdl-data-table__cell--non-numeric">${concretePrescription.id}</td>
                                    <td class="mdl-data-table__cell--non-numeric">${concretePrescription.prescription.name}</td>
                                    <td class="mdl-data-table__cell--non-numeric">${concretePrescription.description}</td>
                                    <td class="mdl-data-table__cell--non-numeric">${concretePrescription.performed}</td>
                                    <td class="mdl-data-table__cell--non-numeric">${concretePrescription.performer.name}</td>
                                    <td class="mdl-data-table__cell--non-numeric">
                                    <center>
                                        <input type="hidden" name="command" value="performPrescription" />   
                                        <input type="hidden" name="conPrescId" value="${concretePrescription.id}" /> 
                                        <input type="hidden" name="patientId" value="${patient.id}" />   
                                        <input type="submit" value="<fmt:message key="patient.table.button" />" 
                                               class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" 
                                               style="min-width:100%;" 
                                               <c:choose>
                                                   <c:when test="${concretePrescription.performed eq true || patient.discharged eq true}">
                                                       disabled
                                                   </c:when>    
                                                   <c:otherwise>
                                                       <c:if test="${concretePrescription.prescription.name eq 'OPERATION'}">
                                                           <security:hasRole roles="ROLE_NURSE">
                                                               disabled
                                                           </security:hasRole>
                                                       </c:if>
                                                   </c:otherwise>
                                               </c:choose>

                                               />
                                    </center>
                                    </td>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>


            </main>

            <jsp:include page="/template/footer.jsp" />

        </div>
    </body>
</html>
