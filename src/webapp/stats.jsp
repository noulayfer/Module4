<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Total Statistics</title>
	<link rel="stylesheet" href="styles.css">
</head>
<body>
	<h1>Total Statistics</h1>
	<p>Amount of products made: ${amount_of_details}</p>
	<p>Amount of broken microschemas: ${amount_of_broken_schemas}</p>
	<p>Total amount of gas extracted: ${total_amount_of_gas}</p>
	<form method="GET" action="stats">
	<button type="submit" name="action" value="stats" class="my-button">Get statistic by id</button>
	  <select name="id">
        <c:forEach var="id" items="${ids}">
          <option value="${id}"><a href="${pageContext.request.contextPath}/stats?id=${id}">${id}</a></option>
        </c:forEach>
      </select>
      </form>
      <form method="GET" action="index.html">
      <button type="submit" name="action" value="index.html" class="my-button">Get back to main menu</button>
      </form>
</body>
</html>

