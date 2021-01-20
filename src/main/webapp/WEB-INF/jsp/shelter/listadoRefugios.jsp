<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="refugios">
    <h2>Shelters</h2>


    <table id="sheltersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Aforo</th>
            <th style="width: 200px;">Ocupacion</th>
            <th>City</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${refugios}" var="shelter" varStatus="i">
            <tr>
                <td>
                    <c:out value="${shelter.aforo}"/>
                </td>
                <td>
                    <c:out value="${shelter.ocupadas}"/>
                </td>
                <td>
                    <c:out value="${shelter.city}"/>
                </td>
                
                
                
                
                
                
          
                <td><spring:url value="/shelter/delete/${shelter.id}"
							var="borrarRefugio">
							
						</spring:url> <a href="${fn:escapeXml(borrarShelter)}"> Delete</a></td>
      

                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
