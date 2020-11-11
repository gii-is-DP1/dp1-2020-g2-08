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
				<th style="width: 120px">Owner</th>
				<th style="width: 200px;">StartDate</th>
				<th>EndDate</th>



			</tr>
		</thead>
		<tbody>


			<c:forEach items="${bookings}" var="booking">
				<tr>
					<td><c:out value="${booking.pet}" /></a></td>
					<td><c:out value="${booking.owner.firstName}" /></td>
					<td><c:out value="${booking.startDate}" /></a></td>
					<td><c:out value="${booking.endDate}" /></td>

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
				<th style="width: 200px;">Review date</th>
				<th>Stars</th>
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
	<spring:url value="/hotel/new" var="bookings">

	</spring:url>
	<%-- <a class="btn btn-default" href="${fn:escapeXml(bookings)}">Crear
		reserva</a> --%>


	<spring:url value="/hotel/review" var="review">

	</spring:url>
	<a class="btn btn-default" href="${fn:escapeXml(review)}">Write
		review </a>
</petclinic:layout>

