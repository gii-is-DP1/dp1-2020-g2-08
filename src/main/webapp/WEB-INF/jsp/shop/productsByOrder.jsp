<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="products">
	<h2>Products List</h2>

	<table id="productTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 200px;">Quantity</th>
				<th>Price</th>
				<th>Rate</th>
			</tr>
		</thead>
		<tbody>


			<c:forEach items="${products}" var="product">
				
				<tr>
					<td><c:out value="${product.nombre}" /></td>
					<td><c:out value="${product.cantidad}" /></td>
					<td><c:out value="${product.precio}" /></td>
					<td><spring:url value="/shop/products/review/{productId}"
							var="productId">
							<spring:param name="productId" value="${product.id}" />
						</spring:url> <a href="${fn:escapeXml(productId)}"> Review</a>
    				
    				
    				
      			</div>			
      			</form:form>
      
					</td> 
				</tr>
				
			</c:forEach>
			
			<h3>Number of products: ${productsNumber}</h3>
		
		
		</tbody>
		
	</table>

	
</petclinic:layout>
	
