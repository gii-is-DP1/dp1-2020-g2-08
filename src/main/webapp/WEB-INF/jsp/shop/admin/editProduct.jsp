<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Products">
    <jsp:body>
        <h2>Product</h2>

        

            <form:form modelAttribute="product" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Price" name="price"/>
                <label for="category">Category:</label>
                        <select name="category">
   						 	<c:forEach items="${categories}" var="category">  
   						 		<option value="${category}">${category} 						
						 	</c:forEach>  
   						 </select></br>
                <label for="inOffer">In Offer:</label>
                        <select name="inOffer">
   						 	<c:forEach items="${offers}" var="inOffer">  
   						 		<option value="${inOffer}">${inOffer} 						
						 	</c:forEach>  
   						 </select>   
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                   
                    <input type="hidden" name="shop_id" value="1"/>
                  
                    <button class="btn btn-default" type="submit">Update product</button>
                </div>
            </div>
        </form:form>

        <br/>
        
    </jsp:body>

</petclinic:layout>
