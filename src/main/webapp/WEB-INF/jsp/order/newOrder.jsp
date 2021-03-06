<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="orders">
	<jsp:attribute name="customScript">
     <script>
     function unselect(){
    	  document.querySelectorAll('[name=coupon]').forEach((x) => x.checked=false);
    	}
     </script>
    </jsp:attribute>
	<jsp:body>
         <h2>
            Buy product <br><br>
        </h2>
        <div>
           <h3> Product Summary</h3>
           <p>  Name: ${product.name}</p><br>
           <p>  Price: ${product.price} euros</p><br>
           <p> Category: ${product.category}</p><br>
        </div>
        <form:form modelAttribute="order" class="form-horizontal">
              <h1>Add your facturation data</h1><br>
            <div class="form-group has-feedback">
                
               
                
                <petclinic:inputField label="Address" name="address" />
                <petclinic:inputField label="City"
					name="city" />
                <petclinic:inputField label="Country"
					name="country" />
					<petclinic:inputField label="Postal Code" name="postalCode" />
                <br>
                <div class="control-group">
                   <spring:bind path="coupon">  
                  <label for="coupon">Your coupons:</label><br>
                    
   						 <c:forEach items="${coupons}" var="coupon">  
   						 <input type="radio" name="coupon" value="${coupon.id}">${coupon.discount}% discount	<br>
   						
						
						
						
						</c:forEach>
						<a id="unselect" onclick="unselect()">Dont use</a>
						
    <div class="${cssGroup}">
          <c:if test="${status.error}">
                <span
								class="glyphicon glyphicon-remove form-control-feedback"
								aria-hidden="true"></span>
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
					</div>
				</spring:bind>
                </div>
               
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                            <button class="btn btn-default"
						type="submit">Buy now</button>
             
                </div>
            </div>
  
        </form:form>
        
      
    </jsp:body>
</petclinic:layout>

