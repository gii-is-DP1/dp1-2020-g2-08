<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="products">
	<h2>Coupons List</h2>

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
					
					<td><spring:url value="/shop/admin/coupons/delete/{couponId}"
							var="couponId">
							<spring:param name="couponId" value="${coupon.id}" />
						</spring:url> <a href="${fn:escapeXml(couponId)}"> Delete</a>
					
						
					</sec:authorize>	
						








				</tr>
				
			</c:forEach>
			
			
	</tbody></table><sec:authorize access="hasAuthority('admin')">
					
					<spring:url value="/shop/admin/coupons/add"
							var="addCoupon">
							
						</spring:url> <a class="btn btn-success" href="${fn:escapeXml(addCoupon)}"> New coupon</a>
					
						
					</sec:authorize>	
	
	
</petclinic:layout>
