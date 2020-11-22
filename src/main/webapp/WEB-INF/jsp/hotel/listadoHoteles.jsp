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
        <c:forEach items="${hoteles}" var="hotel">
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
                   
                </td> <td>
                    
                </td>
                
                
               <%--  <td>:
                    <c:forEach var="booking" items="${hotel.bookings}">
                     Owner:   <c:out value="${booking.owner.firstName} "/> Pet: <c:out value="${review.pet.name} "/>
                    </c:forEach>
                </td> --%>
                <%-- <td>
                    <c:forEach var="review" items="${hotel.reviews}">
                     Stars:   <c:out value="${review.stars} "/> Owner: <c:out value="${review.owner.firstName} "/>
                    </c:forEach>
                </td> --%>
                <td><spring:url value="/hotel/delete/${hotel.id}"
							var="borrarHotel">
							
						</spring:url> <a href="${fn:escapeXml(borrarHotel)}"> Delete</a></td>
      

                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
