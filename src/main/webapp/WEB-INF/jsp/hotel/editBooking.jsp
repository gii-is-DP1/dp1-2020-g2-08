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
											dateFormat : 'yy/MM/dd'
										});
										$("#endDate").datepicker({
											dateFormat : 'yy/MM/dd'
										});
									});
								</script>
    </jsp:attribute>
	<jsp:body>
        <h2>New booking for <c:out value="${pets[0].owner.firstName} ${pets[0].owner.lastName}" /> </h2>

        

        <form:form modelAttribute="booking" class="form-horizontal"
			action="../save/${ownerId}">
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
    </select>
                     
               
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
