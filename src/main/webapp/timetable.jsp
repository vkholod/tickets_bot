<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<% List flight_pairs = (List)session.getAttribute("flight_pairs");%>

<html>
    <head>

    </head>
    <body>
        <h2>Timetable</h2>
        <table border="1">
            <tr>
                <th>Direction</th>
                <th>Date and time</th>
                <th>Price</th>
                <th>Direction</th>
                <th>Date and time</th>
                <th>Price</th>
                <th>Total Price</th>
            </tr>
            <c:forEach items="${flight_pairs}" var="flight_pair">
            <tr>
                <td><c:out value="${flight_pair.outboundFlight.departureStation}"/></td>
                <td><c:out value="${flight_pair.outboundFlight.arrivalStation}"/></td>
                <td><c:out value="${flight_pair.outboundFlight.price.amount}"/></td>
                <td><c:out value="${flight_pair.returnFlight.departureStation}"/></td>
                <td><c:out value="${flight_pair.returnFlight.arrivalStation}"/></td>
                <td><c:out value="${flight_pair.returnFlight.price.amount}"/></td>
                <td><c:out value="${flight_pair.totalPrice}"/></td>
            </tr>
            </c:forEach>
        </table>
    </body>
</html>