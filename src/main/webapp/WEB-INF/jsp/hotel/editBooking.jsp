<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="Bookings">
	<jsp:attribute name="customScript">
        <script>
									$(function() {
										$("#startDate").datepicker({
											dateFormat : 'yy/mm/dd'
										});
										$("#endDate").datepicker({
											dateFormat : 'yy/mm/dd'
										});
									});
								</script>
    </jsp:attribute>
	<jsp:body>
        <h2><c:if test="${booking['new']}">New </c:if> Booking for <c:out value="${pets[0].owner.firstName} ${pets[0].owner.lastName}" /> </h2>

<!--  Hay que enviar los datos de owner, pet, fecha inicio y fecha fin-->
        

        <form:form modelAttribute="booking" class="form-horizontal"
			action="/hotel/booking/save/${ownerId}">
			
            <div class="form-group has-feedback">
                <petclinic:inputField label="Start Date"
					name="startDate" />
                <petclinic:inputField label="End Date" name="endDate" />
             
                 <%--    <petclinic:selectField label="Pet" name="pet" names="${pets}" size="2" /> --%>
                 
                 <label for="pet">Choose your pet:</label>
                        <select name="pet">
   						 <c:forEach items="${pets}" var="pet">  
   						 <option value="${pet.id}">${pet.name} 
							
					</c:forEach>
    </select> <br><br><br>
                     <label for="hotel">Choose your Hotel:</label>
                        <select name="hotel">
   						 <c:forEach items="${hoteles}" var="hotel">  
   						 <option value="${hotel.id}">${hotel.city} 
							
					</c:forEach>  
    </select><br>
               
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">                   
                  
                    <button class="btn btn-default" type="submit">Save booking</button>
                </div>
            </div>
        </form:form>

        <br />
        
    </jsp:body>

</petclinic:layout>
