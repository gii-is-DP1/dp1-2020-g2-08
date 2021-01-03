<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es" xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="master">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="java.util.*" session="true"%>
<petclinic:layout pageName="products">
	<h2>Carrito</h2>

	  <h1 >Total: ${total}</h1>
	
        <div class="table-">
            <table id="productTable" class="table table-striped">
                <thead>
                <tr>
                    <th>Name</th>
                    
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                     <th>Offer</th>
                    <th>Action</th>
                </tr>
                </thead>
			<tbody>
				
					<c:forEach items="${carrito}" var="carrito" varStatus="i">
					<tr>

						<td>${carrito.name}</td>
						<td>${carrito.price}</td>
						<td>${carrito.cantidad}</td>
						<td>${carrito.total}</td>
						<td>${carrito.inOffer}</td>
						<td>
						<spring:url value="/shop/carrito/remove/${i.index}" var="delete"></spring:url>
				
				<a class="btn btn-danger" href="${fn:escapeXml(delete)}"> Delete </a>
							<%-- <form th:action="@{/shop/carrito/remove/} + ${i.index}"
								method="post">
								<button type="submit" class="btn btn-danger">delete</button>
							</form> --%>
						</td>
</tr>
					</c:forEach>

				
				
			</tbody>
		</table>
        </div>
        
        
				<spring:url value="/shop/carrito/complete" var="complete"></spring:url>
				
				<a class="btn btn-success" href="${fn:escapeXml(complete)}"> Complete order </a>
				
				<spring:url value="/shop/carrito/reset" var="reset"></spring:url>
				
				<a class="btn btn-danger" href="${fn:escapeXml(reset)}"> Remove all </a>
	
	
</petclinic:layout>