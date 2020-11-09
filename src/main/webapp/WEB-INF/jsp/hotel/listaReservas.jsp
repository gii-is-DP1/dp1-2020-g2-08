<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="bookings">
	<h1>Bookings</h1>

	<table id="bookingsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Pet</th>
				<th style="width: 200px;">StartDate</th>
				<th>EndDate</th>
				<th style="width: 120px">Owner</th>
				<th>Acciones</th>

			</tr>
		</thead>
		<tbody>


			<c:forEach items="${bookings}" var="booking">
				<tr>
					<td><c:out value="${booking.pet}" /></a></td>

					<td><c:out value="${booking.startDate}" /></a></td>
					<td><c:out value="${booking.endDate}" /></td>
					<td><c:out value="${booking.owner.firstName}" /></td>

					<td><spring:url value="/bookings/delete/{bookingId}"
							var="bookingId">
							<spring:param name="bookingId" value="${booking.id}" />
						</spring:url> <a href="${fn:escapeXml(bookingId)}"> Eliminar</a></td>








				</tr>

			</c:forEach>

			<h3>Aforo máximo del hotel: ${aforo}</h3>
			<h3>Ocupadas actualmente: ${ocupadas}</h3>
			<%--  <h3>Hotel: ${hotel}</h3> --%>

		</tbody>

	</table>
<h1>Reviews</h1>
	<table id="reviewsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Tittle</th>

				<th style="width: 120px">Description</th>
				<th>Review date</th>
				<th style="width: 200px;">Stars</th>
				<th></th>

			</tr>
		</thead>
		<tbody>


			<c:forEach items="${reviews}" var="review">
				<tr>
					<td><c:out value="${review.tittle}" /></a></td>

					<td><c:out value="${review.description}" /></a></td>
					<td><c:out value="${review.reviewDate}" /></td>
					<td><c:out value="${review.stars}" /></td>
</tr>

			</c:forEach>

			

		</tbody>

	</table>
	<spring:url value="/bookings/new" var="bookings">

	</spring:url>
	<a class="btn btn-default" href="${fn:escapeXml(bookings)}">Crear
		reserva</a>


	<spring:url value="/bookings/review" var="review">

	</spring:url>
	<a class="btn btn-default" href="${fn:escapeXml(review)}">Escribir
		reseña </a>
</petclinic:layout>

