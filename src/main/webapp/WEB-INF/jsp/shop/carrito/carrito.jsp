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
							<form th:action="@{/vender/quitar/} + ${iterador.index}"
								method="post">
								<button type="submit" class="btn btn-danger">delete</button>
							</form>
						</td>
</tr>
					</c:forEach>

				
				
				
			</tbody>
		</table>
        </div>
	
	
</petclinic:layout>