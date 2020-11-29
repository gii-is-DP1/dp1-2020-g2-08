<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="hoteles">
    <h2>Hotels</h2>


    <table id="hotelsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Aforo</th>
            <th style="width: 200px;">Ocupacion</th>
            <th>City</th>
            <th style="width: 120px">Bookings</th>
            <th>Reviews</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${hoteles}" var="hotel" varStatus="i">
            <tr>
                <td>
                    <c:out value="${hotel.aforo}"/>
                </td>
                <td>
                    <c:out value="${hotel.ocupadas}"/>
                </td>
                <td>
                    <c:out value="${hotel.city}"/>
                </td>
                 <td>
                    <c:out value="${numBookings[i.index]}"/>
                </td>
                 <td>
                    <c:out value="${numReviews[i.index]}"/>
                </td>
                
                
                
                
                
                
          
                <td><spring:url value="/hotel/delete/${hotel.id}"
							var="borrarHotel">
							
						</spring:url> <a href="${fn:escapeXml(borrarHotel)}"> Delete</a></td>
      

                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
