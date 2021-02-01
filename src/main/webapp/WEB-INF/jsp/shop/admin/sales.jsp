<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="sales">
	
			
			<h3>Total Sales: ${sales}</h3>
			<h3>${products}</h3>
		
		<spring:url value="/shop/admin/sales/today" var="today">
		</spring:url>
		<a class="btn btn-default" href="${fn:escapeXml(today)}">Today sales </a>
		
		<spring:url value="/shop/admin/sales/total" var="total">
		</spring:url>
		<a class="btn btn-default" href="${fn:escapeXml(total)}">Total sales </a>
		
		<spring:url value="/shop/admin/sales/lastMonth" var="lastMonth">
		</spring:url>
		<a class="btn btn-default" href="${fn:escapeXml(lastMonth)}">Last month sales </a>
	
	
	
</petclinic:layout>
	