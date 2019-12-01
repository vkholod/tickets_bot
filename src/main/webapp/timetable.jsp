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
        <center>
        <table border="1" cellpadding="5" cellspacing="0">
            <tr>
                <th>Outbound Flight</th>
                <th>Return Flight</th>
                <th>Total Price</th>
            </tr>
            <c:forEach items="${flight_pairs}" var="flight_pair">
            <tr>
                <td><c:out value="${flight_pair.outboundFlight.toString()}"/></td>
                <td><c:out value="${flight_pair.returnFlight.toString()}"/></td>
                <td><c:out value="${flight_pair.totalPrice()}"/></td>
            </tr>
            </c:forEach>
        </table>
        </center>
    </body>
</html>