<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<petclinic:layout pageName="Products">
<jsp:attribute name="customScript">
        <script>
									$(function() {
										$("#expireDate").datepicker({
											dateFormat : 'yy/mm/dd'
										});
									});
								</script>
    </jsp:attribute>
	<jsp:body>
        <h2>New coupon</h2>

                	

        <form:form modelAttribute="coupon" class="form-horizontal"
			action="save">
            <div class="form-group has-feedback">
            	  <petclinic:inputField label="Discount(%)" name="discount" />
               
                <petclinic:inputField label="Expire date "
					name="expireDate" />
                  
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                   
                    
                   
                  
                    <button class="btn btn-default" type="submit">Save coupon</button>
                    

                </div>
            </div>
        </form:form>
        
        
        

        <br />
        
   
        
    </jsp:body>

</petclinic:layout>
