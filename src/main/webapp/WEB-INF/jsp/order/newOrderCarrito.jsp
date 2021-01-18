
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
	<h1>Your order summary</h1><br>
          <div class="table-">
          
            <table id="productTable" class="table table-striped">
                <thead>
                <tr>
                    <th>Name</th>
                    
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                     
                   
                </tr>
                </thead>
			<tbody>
				
					<c:forEach items="${carrito}" var="carrito" varStatus="i">
					<tr>

						<td>${carrito.name}</td>
						<td>${carrito.price}</td>
						<td>${carrito.cantidad}</td>
						<td>${carrito.total}</td>
						
						
</tr>
					</c:forEach>

				
				
				
			</tbody>
		</table>
        </div>
        <form:form modelAttribute="order" class="form-horizontal">
             
            <div class="form-group has-feedback">
                
               <h1>Add your facturation data</h1><br>
                
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
						
						
    </select>
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
                
                   <%--  <petclinic:selectField name="coupon" label="Coupon " names="${coupons}" size="2"/> --%>
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

