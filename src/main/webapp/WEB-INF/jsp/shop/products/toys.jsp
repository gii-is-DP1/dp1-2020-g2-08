<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="products">
	<h2>Toys List</h2>

	<table id="productTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 200px;">Category</th>
				<th>Price</th>
				<th style="width: 120px">In Offer</th>
				<th>Actions</th>

			</tr>
		</thead>
		<tbody>


			<c:forEach items="${product}" var="product">
				<tr>
					<td><c:out value="${product.name}" /></a></td>

					<td><c:out value="${product.category}" /></a></td>
					<td><c:out value="${product.price}" /></td>
					<td><c:out value="${product.inOffer}" /></td>

					<td><spring:url value=""
							var="productId">
							<spring:param name="productId" value="${product.id}" />
						</spring:url> <a href="${fn:escapeXml(productId)}"> Add to cart</a>
					
						








				</tr>
				
			</c:forEach>
			
			<h3>Number of products: ${productsNumber}</h3>
			
		
		</tbody>
			
	</table>
	<spring:url value="/shop/products/pets" var="pets">
	</spring:url>
	<spring:url value="/shop/products/food" var="food">
	</spring:url>
	<spring:url value="/shop/products/toys" var="toys">
	</spring:url>
	<spring:url value="/shop/products/accessories" var="accessories">
	</spring:url>
	<spring:url value="/shop" var="back">
	</spring:url>
			
	
	<a class="btn btn-default" href="${fn:escapeXml(pets)}">Pets </a>
			


	<a class="btn btn-default" href="${fn:escapeXml(toys)}">Toys </a>
			
	<a class="btn btn-default" href="${fn:escapeXml(food)}">Food </a>
		
	<a class="btn btn-default" href="${fn:escapeXml(accessories)}">Accessories</a>
	
	<a class="btn btn-default" href="${fn:escapeXml(back)}">Return</a>
	
</petclinic:layout>