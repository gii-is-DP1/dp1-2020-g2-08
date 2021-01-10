<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>




<petclinic:layout pageName="Bookings">
	<jsp:attribute name="customScript">
        <script>
									
								</script>
    </jsp:attribute>
	<jsp:body>
        <h2>
			 Choose one hotel to book
		</h2>
		
		<c:forEach items="${hoteles}" var="hotel" varStatus="i">
		
		
		<h1>Hotel in ${hotel.city} </h1>
		
		<table id="hotelsTable" class="table table-striped">
        <thead>
        <tr>
           
            <th style="width: 50px;">Rating</th>
            <th style="width: 50px;">Total number of bookings</th>
            <th style="width: 50px;">Number of reviews</th>
           
            <th style="width: 50px;">Actions</th>
        </tr>
        </thead>
        <tbody>
        
            <tr>
              
                <td>
                
                    <c:out value="${valoracionMedia[i.index]}" />
                </td>
                <td>
                    <c:out value="${numeroBookings[i.index]}" />
                </td>
                 <td>
                    <c:out value="${numeroReviews[i.index]}" />
                </td>
                 <td>
                   
							<spring:url value="/hotel/booking/new/${hotel.id}"
									var="nuevaReserva">
							
						</spring:url> <a href="${fn:escapeXml(nuevaReserva)}"> New booking</a></td>
                     
            </tr>
        
        </tbody>
    </table>
		
		
		
		
		
		
		</c:forEach>

        
    </jsp:body>

</petclinic:layout>
