<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="adminPage.">
    <!DOCTYPE html>
    <html lang="${sessionScope.locale}">

    <head>
        <meta charset="UTF-8">
        <title>Administration</title>
        <link rel="stylesheet" href="../../resources/css/admin.css">
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    </head>

    <body>

    <c:set var="locale" value="${sessionScope.locale}"/>
    <c:if test="${sessionScope.locale == null}">
        <c:set var="locale" value="en"/>
    </c:if>

    <div class="header">
        <div class="header__section">
            <div class="header_item headerlogo">
                RENAISSANCE
            </div>
        </div>
        <div class="header__section">
            <div class="header__item headerButton">
                <form action="hotel" method="post" class="form-container-locale">
                    <input type="submit" value="ru" name="ruLanguage">
                    <span class="menu-link">/</span>
                    <input type="submit" value="en" name="enLanguage">
                    <input type="hidden" name="form" value="locale_form"/>
                </form>
            </div>
            <div class="header__item headerButton">
                Administration
            </div>
        </div>
        <div class="header__section">
            <div class="header__item headerButton">
                <button id="outBtn"><fmt:message key="logout"/></button>
            </div>
        </div>
    </div>

    <div id="outModal" class="modal">
        <div class="form-container">
            <form action="hotel" method="post">
                <span class="closebtn">&times;</span>
                <h1>Do you really want to leave?</h1>
                <div class="form-field submit-field">
                    <input type="submit" value="Sign out">
                    <input type="hidden" name="form" value="back"/>
                </div>
            </form>
        </div>
    </div>

    <main>
        <div class="sideBar">
            <div class="sideBar__section">
                <div class="sideBar__item">
                    <form action="hotel" method="post" class="form__room">
                        <button id="myBtn"><fmt:message key="client"/></button>
                        <input type="hidden" name="form" value="list_client"/>
                    </form>
                </div>
                <div class="sideBar__item">
                    <a href="#"><fmt:message key="passport"/></a>
                </div>
                <div class="sideBar__item">
                    <form action="hotel" method="post" class="form__room">
                        <button id="roomBtn"><fmt:message key="order"/></button>
                        <input type="hidden" name="form" value="list_order"/>
                    </form>
                </div>
                <div class="sideBar__item">
                    <form action="hotel" method="post" class="form__room">
                        <button id="blackBtn"><fmt:message key="black_list"/></button>
                        <input type="hidden" name="form" value="black_list"/>
                    </form>
                </div>
            </div>
        </div>

        <div class="table">
            <table class="table-bordered">
                <tr>
                    <th><fmt:message key="client_id"/></th>
                    <th><fmt:message key="last_name"/></th>
                    <th><fmt:message key="name"/></th>
                    <th><fmt:message key="patronymic"/></th>
                    <th><fmt:message key="country"/></th>
                    <th><fmt:message key="date_of_birth"/></th>
                    <th><fmt:message key="sex"/></th>
                    <th><fmt:message key="identification_no"/></th>
                    <th><fmt:message key="passport_no"/></th>
                </tr>
                <c:if test="${requestScope.passports != null}">
                    <c:forEach var="passport" items="${requestScope.passports}">
                        <tr>
                            <td>${passport.passportId}</td>
                            <td>${passport.lastName}</td>
                            <td>${passport.name}</td>
                            <td>${passport.patronymic}</td>
                            <td>${passport.country}</td>
                            <td>${passport.dateOfBirth}</td>
                            <td>${passport.sex}</td>
                            <td>${passport.identificationNo}</td>
                            <td>${passport.passportNo}</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </div>
    </main>

    <div class="page__row">
        <div class="page_call">
            <footer class="footer">
                <div class="footer__copyright">
                    Tarasiuk Leontiy &copy; 2019<br/>
                    <a href="#" class="footer__link">Renaissance.ru</a>
                </div>
            </footer>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js"
            type="text/javascript"></script>
    <script src="../../resources/js/signOut.js"></script>
    </body>

    </html>
</fmt:bundle>