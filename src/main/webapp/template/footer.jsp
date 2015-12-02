<%-- 
    Document   : footer
    Created on : Nov 25, 2015, 11:17:51 AM
    Author     : alexander-ilkun
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="internationalization.text" />
<footer class="mdl-mini-footer">
    <div class="mdl-mini-footer--bottom-section">
        <div class="mdl-logo">
            <fmt:message key="footer.projectname" />
        </div>
        <ul class="mdl-mini-footer__link-list">
            <li>
                <form>
                    <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-1">
                        <input type="radio" id="option-1" class="mdl-radio__button" onchange="submit()"
                               name="language" value="en" ${language == 'en' ? 'checked' : ''}>
                        <span class="mdl-radio__label">EN</span>
                    </label>
                    <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-2">
                        <input type="radio" id="option-2" class="mdl-radio__button" onchange="submit()"
                               name="language" value="ua" ${language == 'ua' ? 'checked' : ''}>
                        <span class="mdl-radio__label">UA</span>
                    </label>
                </form>
            </li>
            <li>&copy; <a href="http://vk.com/id66366673"><fmt:message key="footer.creator" /></a>, 2015</li>
        </ul>
    </div>
</footer>