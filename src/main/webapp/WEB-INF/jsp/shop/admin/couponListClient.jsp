<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="products">
	<h2>Coupons avaible for the client</h2>

	<table id="productTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Discount(%)</th>
				<th style="width: 200px;">Expire Date</th>
				<th>Actions</th>

			</tr>
		</thead>
		<tbody>

			<c:forEach items="${couponsClient}" var="coupon1">
				<tr>
					<td><c:out value="${coupon1.discount}" /></a></td>

					<td><c:out value="${coupon1.expireDate}" /></a></td>
				
					<sec:authorize access="hasAuthority('admin')">
					
					<td><spring:url value="/shop/admin/clients/${client.id}/removeCoupon/${coupon1.id}"
							var="couponId">
						
						</spring:url> <a href="${fn:escapeXml(couponId)}"> Delete for client</a>
											
					</sec:authorize>	
					
				</tr>
				
			</c:forEach>
			</tbody></table>
			
			<h2>All Coupons </h2>

	<table id="productTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Discount(%)</th>
				<th style="width: 200px;">Expire Date</th>
				<th>Actions</th>

			</tr>
		</thead>
		<tbody>

			<c:forEach items="${coupons}" var="coupon">
				<tr>
					<td><c:out value="${coupon.discount}" /></a></td>

					<td><c:out value="${coupon.expireDate}" /></a></td>
				
					<sec:authorize access="hasAuthority('admin')">
					
					<td><spring:url value="/shop/admin/clients/${client.id}/addCoupon/${coupon.id}"
							var="couponId">
						</spring:url> <a href="${fn:escapeXml(couponId)}"> Add to this client</a>
											
					</sec:authorize>	
					
				</tr>
				
			</c:forEach>
			</tbody></table>
			
	
</petclinic:layout>
	
