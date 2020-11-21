<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="products">


	<spring:url value="/shop/admin/products/pets" var="pets">
	</spring:url>
	<spring:url value="/shop/admin/products/food" var="food">
	</spring:url>
	<spring:url value="/shop/admin/products/toys" var="toys">
	</spring:url>
	<spring:url value="/shop/admin/products/accessories" var="accessories">
	</spring:url>
	
			
	
	<a class="btn btn-default" href="${fn:escapeXml(pets)}">Pets </a>

	<a class="btn btn-default" href="${fn:escapeXml(toys)}">Toys </a>

	<a class="btn btn-default" href="${fn:escapeXml(food)}">Food </a>

	<a class="btn btn-default" href="${fn:escapeXml(accessories)}">Accessories</a>

	
</petclinic:layout>
	
