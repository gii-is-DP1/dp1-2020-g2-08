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
        		  dateFormat : 'yy/mm/dd',
				    beforeShowDay: function(date) {
				    	
				      if (${restriccion}) {
				        return [false, "CSSclass", "disabled"];
				      } else {
				        return [true, '', ''];
				      }
				    }
				  });
										
											  $("#endDate").datepicker({
												  dateFormat : 'yy/mm/dd',
											    beforeShowDay: function(date) {
											    	
											      if (${restriccion} ) {
											        return [false, "CSSclass", "disabled"];
											      } else {
											        return [true, '', ''];
											      }
											    }
											  });
											});
										
									
									
									
									
								</script>
    </jsp:attribute>
	<jsp:body>
        <h2>
			<c:if test="${booking['new']}">New </c:if> Booking for <c:out
				value="${pets[0].owner.firstName} ${pets[0].owner.lastName}" /> in <c:out value=" ${hotel.city}"/></h2>

<!--  Hay que enviar los datos de owner, pet, fecha inicio y fecha fin-->
        

        <form:form modelAttribute="booking" class="form-horizontal">
			 <input type="hidden" name="ownerId" value="${owner.id}" />
			 <input type="hidden" name="hotel" value="${hotel.id}" />
            <div class="form-group has-feedback">
                <petclinic:inputField label="Start Date"
					name="startDate" />
                <petclinic:inputField label="End Date" name="endDate" />
                
                <spring:bind path="pet">  
                  <label for="pet">Choose your pet:</label>
                        <select name="pet">
   						 <c:forEach items="${pets}" var="pet">  
   						 <option value="${pet.id}">${pet.name} 	
						
						
						
						</c:forEach>
    </select>
					<div class="${cssGroup}">
          <c:if test="${status.error}">
                <span
								class="glyphicon glyphicon-remove form-control-feedback"
								aria-hidden="true"></span>
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
					</div>
				</spring:bind>
		
				
            <br> 
               
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
