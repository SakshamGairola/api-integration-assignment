<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Login</title>
  </head>

  <body>
    <h3>List Without Iteration :</h3>
    ${customerList}
    <h3>List With Iteration :</h3>
    <ul>
 
        <c:forEach var="customer" items="${customerList}">
            <li>${customer.getFirst_name}</li>
        </c:forEach>
 
    </ul>
  </body>
</html>