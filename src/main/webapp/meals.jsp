<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Meals</title>
    <style>
        table,td,tr {
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
    <h1>Meals</h1>
    <table style="width:60%">
        <c:forEach var="meal" items="${requestScope.MEALS}" varStatus="status">
            <c:set var="colorcond" value="${meal.excess ? 'red' : 'black'}"/>
            <tr style="color:${colorcond}">
                <td><fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm"
                                   var="parsedDateTime" type="both" />
                    <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }" var="parsedDate"/>
                    <c:out value="${parsedDate}"/>
                </td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
