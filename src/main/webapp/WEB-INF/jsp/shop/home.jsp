<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="products">


	
	<spring:url value="/shop/products/Pets" var="pets">
	</spring:url>
	<spring:url value="/shop/products/Food" var="food">
	</spring:url>
	<spring:url value="/shop/products/Toys" var="toys">
	</spring:url>
	<spring:url value="/shop/products/Accessories" var="accessories">
	</spring:url>
	
	<sec:authorize access="hasAuthority('client')">
	
		<spring:url value="/shop/carrito" var="cart"></spring:url>
				
		<a class="btn btn-default" href="${fn:escapeXml(cart)}"> Cart </a>
		
	</sec: authorize>
	
	<a class="btn btn-default" href="${fn:escapeXml(pets)}">Pets </a>

	<a class="btn btn-default" href="${fn:escapeXml(toys)}">Toys </a>

	<a class="btn btn-default" href="${fn:escapeXml(food)}">Food </a>

	<a class="btn btn-default" href="${fn:escapeXml(accessories)}">Accessories</a>

	
</petclinic:layout>
	
