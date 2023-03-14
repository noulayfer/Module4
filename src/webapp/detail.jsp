<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.time.LocalDateTime" %>
    <%@ page import="java.time.ZoneId" %>
    <%@ page import="java.util.Date" %>
    <%@ page import="com.fedorenko.model.Detail" %>
 <%
     Detail detail = (Detail)request.getAttribute("detail");
     LocalDateTime localDateTimeObject = detail.getDateTime();
     Date date = Date.from(localDateTimeObject.atZone(ZoneId.systemDefault()).toInstant());
 %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detail Statistic</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Detail Statistic</h1>
    <table>
        <tr>
         <td>Detail was created:</td>
         <td><fmt:formatDate value="<%= date %>" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        </tr>
        <tr>
            <td>Seconds took to create detail:</td>
            <td>${detail.getSeconds()}</td>
        </tr>
        <tr>
            <td>Gas that produced by the 1-st robot:</td>
            <td>${detail.getAmountOfGas()} halons</td>
        </tr>
         <tr>
            <td>Amount of broken microschemas:</td>
            <td>${detail.getCountOfBrokenMicroSchemas()}</td>
         </tr>
        	<form method="GET" action="stats">
              <button type="submit" name="action" value="stats" class="my-button">Back to total statistic</button>
              </form>
              <form method="GET" action="index.html">
              <button type="submit" name="home" value="index.html" class="my-button">Back to home page</button>
             </form>
    </table>
</body>
</html>