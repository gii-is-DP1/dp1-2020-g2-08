<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="clients">
	<h2>Clients List</h2>

	<table id="clientTable" class="table table-striped">
		<thead>
			<tr>
				<th>First name</th>
				<th>Last name</th>
				<th>Address</th>
				<th>City</th>
				<th>Email</th>
				<th>Nif</th>
				<th>Telephone</th>
				<th>Username</th>
				<th>Coupons</th>

			</tr>
		</thead>
		<tbody>


			<c:forEach items="${clients}" var="client">
				<tr>
					<td><c:out value="${client.firstName}" /></a></td>
					<td><c:out value="${client.lastName}" /></a></td>
					<td><c:out value="${client.address}" /></td>
					<td><c:out value="${client.city}" /></td>
					<td><c:out value="${client.email}" /></td>
					<td><c:out value="${client.nif}" /></td>
					<td><c:out value="${client.telephone}" /></td>
					<td><c:out value="${client.user.username}"/></td>
					<td><spring:url value="/shop/admin/coupons/{clientId}"
							var="clientId">
							<spring:param name="clientId" value="${client.id}" />
						</spring:url> <a href="${fn:escapeXml(clientId)}"> See coupons</a></td>


				</tr>








			
				
			</c:forEach>
			
			<h3>Number of clients: ${clientsNumber}</h3>

		
		</tbody>
			
	</table>
	
</petclinic:layout>
	