<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Add/Update</title>
  </head>

  <body>
      <form:form method="POST" action="/update-customer" modelAttribute="customer">
            <span>Customer Details</span> <br />
            <hr>
            <form:input style="display: none;" value="${customer.uuid}" path="uuid" />
            <form:input name="first_name" type="text" placeholder="first_name" autofocus="true"
            path="first_name" />
             <br
            />
            <form:input name="last_name" type="text" placeholder="last_name" path="last_name" /> <br />

            <form:input name="street" type="text" placeholder="street" autofocus="true" path="street" /> <br
            />
            <form:input name="address" type="text" placeholder="address" path="address" /> <br />

            <form:input name="city" type="text" placeholder="city" autofocus="true" path="city" /> <br
            />
            <form:input name="state" type="text" placeholder="state" path="state" /> <br />

            <form:input name="email" type="text" placeholder="email" autofocus="true" path="email" /> <br
            />
            <form:input name="phone" type="text" placeholder="phone" path="phone" /> <br />

            <button type="submit">Submit</button>
      </form:form>
  </body>
</html>