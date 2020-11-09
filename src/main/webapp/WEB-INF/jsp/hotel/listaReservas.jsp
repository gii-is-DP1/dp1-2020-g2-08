<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="bookings">
	<h2>Bookings</h2>

	<table id="ownersTable" class="table table-striped">
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
			
			<h3>Aforo m�ximo del hotel: ${aforo}</h3>
			<h3>Ocupadas actualmente: ${ocupadas}</h3>
			<%--  <h3>Hotel: ${hotel}</h3> --%>
		
		</tbody>
			
	</table><spring:url value="/bookings/new" var="bookings">

			</spring:url>
			<a class="btn btn-default" href="${fn:escapeXml(bookings)}">Crear
				reserva</a>
</petclinic:layout>
	
