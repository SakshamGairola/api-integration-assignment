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
      <form:form method="POST" action="/loginget" modelAttribute="user">
            <span>Login Page</span> <br />
            <form:input name="username" type="text" placeholder="login id" autofocus="true" path="userID" /> <br
            />
            <form:input name="password" type="text" placeholder="Password" path="userPassword" /> <br />
            <button type="submit">Log In</button>
        </form:form>
    </div>
  </body>
</html>