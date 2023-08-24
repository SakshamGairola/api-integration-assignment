<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>Login</title>
		<style>
			table,
			th,
			td {
				border: 1px solid;
			}
		</style>
	</head>

	<body>

	    <a type="button" href="/get-customer-details">Add Customer</a>
	    <h2 style="margin-left: 50px; display:inline-block">Customer List</h2>
	    <hr>
		<table>
			<thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Address</th>
					<th>City</th>
					<th>State</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allCustomers}" var="customer">
					<tr>
						<td>${customer.first_name}</td>
						<td>${customer.last_name}</td>
						<td>${customer.address}</td>
						<td>${customer.city}</td>
						<td>${customer.state}</td>
						<td>${customer.email}</td>
						<td>${customer.phone}</td>
						<td>
							<a
								type="button"
								href="/update-customer-details?uuid=${customer.uuid}"
								>Update</a
							>
							<a
								type="button"
								href="/delete-customer?uuid=${customer.uuid}"
								>Delete</a
							>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>
