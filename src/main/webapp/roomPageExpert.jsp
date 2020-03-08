<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="pagecontent" prefix="roomPage.">
    <!DOCTYPE html>
    <html lang="${sessionScope.locale}">

    <head>
        <meta charset="UTF-8">
        <title>RenaissanceHotel</title>
        <link rel="stylesheet" href="resources/css/roomPage.css">
        <link rel="stylesheet" href="resources/css/img.css">
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
                <a href="#"><fmt:message key="order"/></a>
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
                    <input class="reg-form" type="hidden" name="form" value="back"/>
                </div>
            </form>
        </div>
    </div>

    <div id="dateModal" class="modal">
        <div class="form-container">
            <form action="hotel" method="post">
                <span class="closeDate">&times;</span>
                <h1><fmt:message key="date"/></h1>
                <div class="form-field input-right">
                    <label for="first_day"><fmt:message key="first_day"/></label>
                    <input type="date" name="first_day" id="first_day" value="" min="">
                </div>
                <div class="form-field input-right">
                    <label for="last_day"><fmt:message key="last_day"/></label>
                    <input type="date" name="last_day" id="last_day" value="">
                </div>
                <div class="form-field submit-field">
                    <input type="submit" value="Search">
                    <input type="hidden" name="form" value="date"/>
                </div>
            </form>
        </div>
    </div>

    <div id="oneModal" class="modal">
        <div class="form-container">
            <form action="hotel" method="post">
                <span class="oneclose">&times;</span>
                <h1><fmt:message key="order"/></h1>
                <div class="form-field input-right">
                    <label for="firstd"><fmt:message key="first_day"/></label>
                    <input type="date" name="first_day" id="firstd">
                </div>
                <div class="form-field input-right">
                    <label for="lastd"><fmt:message key="last_day"/></label>
                    <input type="date" name="last_day" id="lastd">
                </div>
                <div class="form-field submit-field">
                    <input type="submit" value="Send">
                    <input type="hidden" id="numberid" name="number">
                    <input type="hidden" id="price_room" name="price">
                    <input type="hidden" name="form" value="order">
                </div>
            </form>
        </div>
            <%--    <div class="form-field">--%>
            <%--        <button id="click" title="Общая стоимсоть за проживание" class="calc">Расчитать стоимость</button>--%>
            <%--        <div id="result" class="result">--%>
            <%--            <label>Общая стоимсоть за проживание:</label>--%>
            <%--        </div>--%>
            <%--    </div>--%>
    </div>

    <main>
        <div class="sideBar">
            <div class="sideBar__section">
                <form action="hotel" method="post" class="form__room">
                    <button id="myBtn"><fmt:message key="my_page"/></button>
                    <input type="hidden" name="form" value="my_page"/>
                </form>
                <div class="sideBar__item">
                    <a href="#"><fmt:message key="rooms"/></a>
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

        <div class="table">
            <table class="table-bordered">
                <tr>
                    <th>Type number</th>
                    <th>Number of persons</th>
                    <th>Price</th>
                    <th>Description</th>
                    <th></th>
                    <th>
                        <div class="header__section">
                            <div class="headerButton">
                                <button id="dateBtn"><fmt:message key="date"/></button>
                            </div>
                        </div>
                    </th>
                </tr>
                <c:if test="${requestScope.rooms != null}">
                    <c:forEach var="room" items="${requestScope.rooms}" varStatus="count">
                        <tr>
                            <td>${room.comfort} ${room.placement}</td>
                            <td>${room.person}</td>
                            <td>${room.price} BYN</td>
                            <td>${room.description}</td>
                            <td><img src="${room.path}" alt=""></td>
                            <td>
                                <button id="button_order${count.count}" class="choice_button"><fmt:message
                                        key="choose"/></button>
                                <input type="hidden" id="number" name="number" value="${room.id}"/>
                                <input type="hidden" id="price" name="price" value="${room.price}"/>
                            </td>
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
    <script src="resources/js/orderExpert.js"></script>
    <script src="resources/js/signOut.js"></script>
    <script src="resources/js/date.js"></script>
    <script src="resources/js/calculate.js"></script>

    </body>

    </html>
</fmt:bundle>