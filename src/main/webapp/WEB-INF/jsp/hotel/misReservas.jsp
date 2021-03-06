<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="bookings">
	<h1><c:out value="${owner.firstName} ${owner.lastName}'s" /> bookings</h1>

	<table id="bookingsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Pet</th>
				<th style="width: 200px;">StartDate</th>
				<th>EndDate</th>
				<th style="width: 120px">Owner</th>
				<th>City</th>
				<th>Actions</th>
				<th></th>

			</tr>
		</thead>
		<tbody>


			<c:forEach items="${bookings}" var="booking">
				<tr>
					<td><c:out value="${booking.pet}" /></a></td>

					<td><c:out value="${booking.startDate}" /></a></td>
					<td><c:out value="${booking.endDate}" /></td>
					<td><c:out value="${booking.owner.firstName}" /></td>
					<td><c:out value="${booking.hotel.city}" /></td>

					<td><spring:url value="/hotel/booking/delete/${booking.id}/${ownerId}"
							var="bookingId">
							
						</spring:url> <a href="${fn:escapeXml(bookingId)}"> Delete</a></td>
						
						<sec:authorize access="hasAuthority('owner')">
						<td><spring:url value="/hotel/booking/edit/${booking.id}"
							var="editar">
							
						</spring:url> <a href="${fn:escapeXml(editar)}"> Edit</a></td></sec:authorize>
				</tr>

			</c:forEach>

		</tbody>

	</table>


	<spring:url value="/hotel/booking/new" var="bookings">

	</spring:url>
	<a class="btn btn-default" href="${fn:escapeXml(bookings)}">New
		booking</a>



</petclinic:layout>

