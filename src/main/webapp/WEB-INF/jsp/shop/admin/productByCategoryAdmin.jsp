<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="products">
	<h2>Pets List</h2>

	<table id="productTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 200px;">Category</th>
				<th>Price</th>
				<th style="width: 120px">In Offer</th>
				
				<sec:authorize access="hasAuthority('admin')">
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

						<td><spring:url value="/shop/admin/products/delete/{productId}"
							var="productId">
							<spring:param name="productId" value="${product.id}" />
						</spring:url> <a href="${fn:escapeXml(productId)}"> Delete</a><a> / <a/>
					<spring:url value="/shop/admin/products/edit/{productId}"
							var="productId">
							<spring:param name="productId" value="${product.id}" />
						</spring:url> <a href="${fn:escapeXml(productId)}"> Edit</a></td>
					
						








				</tr>
				
			</c:forEach>
			
			<h3>Number of products: ${productsNumber}</h3>
			
		
		</tbody>
			
	</table>
	<spring:url value="/shop/admin/products/Pets" var="Pets">
	</spring:url>
	<spring:url value="/shop/admin/products/Food" var="Food">
	</spring:url>
	<spring:url value="/shop/admin/products/Toys" var="Toys">
	</spring:url>
	<spring:url value="/shop/admin/products/Accessories" var="Accessories">
	</spring:url>
	<spring:url value="/shop/admin/products" var="back">
	</spring:url>
			
	<a class="btn btn-default" href="${fn:escapeXml(Pets)}">Pets </a>
			
	<a class="btn btn-default" href="${fn:escapeXml(Toys)}">Toys </a>
			
	<a class="btn btn-default" href="${fn:escapeXml(Food)}">Food </a>
		
	<a class="btn btn-default" href="${fn:escapeXml(Accessories)}">Accessories</a>
	
	<a class="btn btn-default" href="${fn:escapeXml(back)}">Return</a>
	
	

	
</petclinic:layout>
	