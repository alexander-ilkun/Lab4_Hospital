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
        <link rel="stylesheet" href="<c:url value="/css/selectfield.css" />">
        <script src="<c:url value="/js/selectfield.js" />"></script>
        <title>Register</title>
    </head>
    <body class="mdl-demo mdl-color--grey-100 mdl-color-text--grey-700 mdl-base">
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <jsp:include page="/template/header.jsp" />


            <main class="mdl-layout__content center_verically">

                <center>    
                    <div class="mdl-card mdl-shadow--2dp" style="padding:20px;">
                        <form action="<c:url value="/Controller" />" method="POST">
                            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                <input type="text" name="fullName" class="mdl-textfield__input" id="fullName" required/>
                                <label class="mdl-textfield__label" for="fullNname"><fmt:message key="register.fullname" /></label>
                            </div>
                            <div class="mdl-select mdl-js-select mdl-select--floating-label">
                                <select class="mdl-select__input" id="roleName" name="roleName" required>
                                    <option value=""></option>
                                    <option value="ROLE_DOCTOR">Doctor</option>
                                    <option value="ROLE_NURSE">Nurse</option>
                                </select>
                                <label class="mdl-select__label" for="professsion"><fmt:message key="register.profession" /></label>
                            </div>
                            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                <input type="text" name="login" class="mdl-textfield__input" id="login" required/>
                                <label class="mdl-textfield__label" for="login"><fmt:message key="register.login" /></label>
                            </div>
                            <br>
                            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                                <input type="password" name="password" class="mdl-textfield__input" id="password" required/>
                                <label class="mdl-textfield__label" for="password"><fmt:message key="register.password" /></label>
                            </div>
                            <input type="hidden" name="command" value="register" />
                            <br>
                            <input name="submit" type="submit" value="<fmt:message key="register.button" />" class="btn mdl-button mdl-js-button mdl-button--raised mdl-button--accent mdl-js-ripple-effect" />
                        </form>
                    </div>
                </center>
            </main>

            <jsp:include page="/template/footer.jsp" />
        </div>
    </body>
</html>
