<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="clientPage.">
    <!DOCTYPE html>
    <html lang="${sessionScope.locale}">

    <head>
        <meta charset="UTF-8">
        <title>RenaissanceHotel</title>
        <link rel="stylesheet" href="resources/css/clientPage.css">
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
                <h1>
                    Do you really want to leave?</h1>
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
                        <button id="myBtn"><fmt:message key="my_page"/></button>
                        <input type="hidden" name="form" value="my_page"/>
                    </form>
                </div>
                <div class="sideBar__item">
                    <form action="hotel" method="post" class="form__room">
                        <button id="roomBtn"><fmt:message key="rooms"/></button>
                        <input type="hidden" name="form" value="rooms"/>
                    </form>
                </div>
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

        <div class="wrap">
            <h1>RENAISSANCE</h1>
            <div class="slider">
                <button onclick="prev()">&laquo;</button>
                <div class="scroller">
                    <ul class="items">
                        <li class="item" style="background-image: url(resources/img/01.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/02.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/03.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/04.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/05.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/20.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/21.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/22.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/10.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/11.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/12.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/13.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/14.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/15.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/16.jpg);"></li>
                        <li class="item" style="background-image: url(resources/img/17.jpg);"></li>
                    </ul>
                </div>
                <button onclick="next()">&raquo;</button>
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