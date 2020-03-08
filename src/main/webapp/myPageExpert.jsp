<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="myPageExpert.">
    <!DOCTYPE html>
    <html lang="${sessionScope.locale}">

    <head>
        <meta charset="UTF-8">
        <title>RenaissanceHotel</title>
        <link rel="stylesheet" href="resources/css/myPage.css">
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
            <div class="header__item headerButton">
                <a href="#"><fmt:message key="news"/></a>
            </div>
            <div class="header__item headerButton">
                <a href="#"><fmt:message key="about_us"/></a>
            </div>
            <div class="header__item headerButton">
                <form action="hotel" method="post">
                    <button id="home"><fmt:message key="home"/></button>
                    <input type="hidden" name="form" value="photo"/>
                </form>
            </div>
            <div class="header__item headerButton">
                <form action="hotel" method="post" class="form-container-locale">
                    <input type="submit" value="ru" name="ruLanguage">
                    <span class="menu-link">/</span>
                    <input type="submit" value="en" name="enLanguage">
                    <input type="hidden" name="form" value="locale_form"/>
                </form>
            </div>
        </div>
        <div class="header__section">
            <div class="header__item headerButton">
                <form action="hotel" method="post" class="form__room">
                    <button id="orderBtn"><fmt:message key="order"/></button>
                    <input type="hidden" name="form" value="rooms"/>
                </form>
            </div>
        </div>
        <div class="header__section">
            <div class="header__item headerButton">
                <a href="myPage.jsp">${sessionScope.client.lastName}</a>
            </div>
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

        <%--<div id="roomModal" class="modal">--%>
        <%--    <div class="form-container">--%>
        <%--        <form action="hotel" method="post">--%>
        <%--            <span class="roomClose">&times;</span>--%>
        <%--            <h1>Номера</h1>--%>
        <%--            <div class="form-field submit-field">--%>
        <%--                <input type="submit" value="Выйти">--%>
        <%--                <input type="hidden" name="form" value="back"/>--%>
        <%--            </div>--%>
        <%--        </form>--%>
        <%--    </div>--%>
        <%--</div>--%>

    <main>
        <div class="sideBar">
            <div class="sideBar__section">
                <div class="sideBar__item">
                    <a href=""><fmt:message key="my_page"/></a>
                </div>
                <form action="hotel" method="post" class="form__room">
                    <button id="roomBtn"><fmt:message key="rooms"/></button>
                    <input type="hidden" name="form" value="rooms"/>
                </form>
                <div class="sideBar__item">
                    <form action="hotel" method="post">
                        <button id="convenience"><fmt:message key="convenience"/></button>
                        <input type="hidden" name="form" value="convenience"/>
                    </form>
                </div>
                <div class="sideBar__item">
                    <form action="hotel" method="post">
                        <button id="photo"><fmt:message key="photo"/></button>
                        <input type="hidden" name="form" value="photo"/>
                    </form>
                </div>
            </div>
        </div>

        <div class="form-container-data">
            <form action="hotel" method="post">
                <h1><fmt:message key="change_login"/>:</h1>
                <div class="form-field input-right">
                    <label for="email"><fmt:message key="change_login"/>:</label>
                    <input type="email" name="email" id="email" placeholder="Email...">
                    <input type="hidden" name="form" value="change_mail"/>
                </div>
                <div class="form-field submit-field">
                    <input type="submit" value="Change">
                </div>
            </form>
        </div>
        <div class="form-container-data">
            <form action="hotel" method="post">
                <h1><fmt:message key="change_password"/>:</h1>
                <div class="form-field input-right">
                    <label for="password"><fmt:message key="change_password"/>:</label>
                    <input type="password" name="password" id="password" placeholder="Password...">
                    <input type="hidden" name="form" value="change_pass"/>
                </div>
                <div class="form-field submit-field">
                    <input type="submit" value="Change">
                </div>
            </form>
        </div>

        <div class="form-container-data">
            <div class="table">
                <h1>Заказы</h1>
                <table class="table-bordered">
                    <tr>
                        <th>First day</th>
                        <th>Last day</th>
                    </tr>
                    <c:if test="${requestScope.orders != null}">
                        <c:forEach var="order" items="${requestScope.orders}">
                            <tr>
                                <td>${order.firstday}</td>
                                <td>${order.lastday}</td>
                                <td>
<%--                                    <div class="form-container-data">--%>
                                        <form action="hotel" method="post">
                                            <div class="form-field submit-field">
                                                <input type="submit" value="Cancel order">
                                                <input type="hidden" value="${order.firstday}">
                                                <input type="hidden" value="${order.lastday}">
                                                <input type="hidden" name="form" value="cancel">
                                            </div>
                                        </form>
<%--                                    </div>--%>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
            </div>
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
    <script src="resources/js/clientPage.js"></script>
    <script src="resources/js/order.js"></script>
    <script src="resources/js/signOut.js"></script>
    <script src="resources/js/room.js"></script>
    </body>

    </html>
</fmt:bundle>