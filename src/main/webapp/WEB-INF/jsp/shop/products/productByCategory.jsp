<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<petclinic:layout pageName="products">
	<h2>Pets List</h2>

	<table id="productTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 200px;">Category</th>
				<th style="width: 200px;">Price</th>
				<th style="width: 120px">In Offer</th>
				
				<sec:authorize access="hasAuthority('client')">
					<th>Actions</th>
				</sec:authorize>
				

			</tr>
		</thead>
		<tbody>


			<c:forEach items="${product}" var="product">
				<tr>
					<td><c:out value="${product.name}" /></a></td>

					<td><c:out value="${product.category}" /></a></td>
					<td><c:out value="${product.price}" /></td>
					<td><c:out value="${product.inOffer}" /></td>

					<sec:authorize access="hasAuthority('client')">	<td><spring:url value="/shop/add/{productId}"
								var="productId">
								<spring:param name="productId" value="${product.id}" />
							</spring:url> <a href="${fn:escapeXml(productId)}"> Add to cart</a>
											
						</td>		</sec:authorize>	

				</tr>
				
			</c:forEach>
			
			<h3>Number of products: ${productsNumber}</h3>
			
		
		</tbody>
			
	</table>
	<spring:url value="/shop/products/Pets" var="Pets">
	</spring:url>
	<spring:url value="/shop/products/Food" var="Food">
	</spring:url>
	<spring:url value="/shop/products/Toys" var="Toys">
	</spring:url>
	<spring:url value="/shop/products/Accessories" var="Accessories">
	</spring:url>
	<spring:url value="/shop" var="back">
	</spring:url>
			
	<a class="btn btn-default" href="${fn:escapeXml(Pets)}">Pets </a>
			
	<a class="btn btn-default" href="${fn:escapeXml(Toys)}">Toys </a>
			
	<a class="btn btn-default" href="${fn:escapeXml(Food)}">Food </a>
		
	<a class="btn btn-default" href="${fn:escapeXml(Accessories)}">Accessories</a>
	
	<a class="btn btn-default" href="${fn:escapeXml(back)}">Return</a>
	
	

	
</petclinic:layout>
	