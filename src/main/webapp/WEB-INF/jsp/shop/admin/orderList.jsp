<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="orderList">
	<h2>All orders</h2>

	<table id="productTable" class="table table-striped">
		<thead>
			<tr>
				<th>Address</th>
				<th>City</th>
				<th>Country</th>
				<th style="width: 120px">Delivery date</th>
				<th style="width: 120px">Order date</th>
				<th style="width: 120px">Postal code</th>
				<th style="width: 120px">State</th>
				<th style="width: 120px">Products</th>
				<th>Offer</th>	
				<th style="width: 120px">Price</th>
				<th style="width: 120px">Actions</th>			

			</tr>
		</thead>
		<tbody>


			<c:forEach items="${orders}" var="order">
				<tr>
					<td><c:out value="${order.address}" /></a></td>
					<td><c:out value="${order.city}" /></a></td>
					<td><c:out value="${order.country}" /></td>
					<td><c:out value="${order.deliveryDate}" /></td>
					<td><c:out value="${order.orderDate}" /></td>
					<td><c:out value="${order.postalCode}" /></td>
					<td><c:out value="${order.state}" /></td>
					<td><spring:url value="/shop/view/products/{orderId}"
							var="orderId">
							<spring:param name="orderId" value="${order.id}" />
						</spring:url> <a href="${fn:escapeXml(orderId)}"> See products</a></td>
						
					<td><c:out value="${order.offer}" /></td>		
					<td><c:out value="${order.priceOrder}" /></td>
					
					
						<td><spring:url value="/shop/admin/orders/deny/{orderId}"
							var="orderId">
							<spring:param name="orderId" value="${order.id}" />
						</spring:url> <a href="${fn:escapeXml(orderId)}"> Deny /</a>
						
						<spring:url value="/shop/admin/orders/confirm/{orderId}"
							var="orderId">
							<spring:param name="orderId" value="${order.id}" />
						</spring:url> <a href="${fn:escapeXml(orderId)}"> Confirm</a>
						
						<spring:url value="/shop/admin/orders/inProgress/{orderId}"
							var="orderId">
							<spring:param name="orderId" value="${order.id}" />
						</spring:url> <a href="${fn:escapeXml(orderId)}"> In Progress</a>
						
						</td>
					

				</tr>
				
			</c:forEach>
			
			<h3>Number of orders: ${ordersNumber}</h3>
			<%--  <h3>Hotel: ${hotel}</h3> --%>
		
		</tbody>
			
	</table>
	
	
</petclinic:layout>
	