<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>



<petclinic:layout pageName="refugios">



 <h1>More time at all shelters: ${masViejo.name} (${masViejo.diasEnRefugio} days)</h2><br> 



    <h2>All our animals for adoptions</h2><br>
    
<c:forEach items="${shelters}" var="shelter" varStatus="i">
<h2> ${shelter.city}'s shelter</h2>
    
							<table id="sheltersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Type</th>
            <th style="width: 200px;">Birthdate</th>
            <th style="width: 200px;">Days at shelter</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${shelter.animals}" var="animal"
							varStatus="i">
							
        <tr>
        
        <td>
                
                <spring:url value="/shelter/animals/${shelter.id}/animal/${animal.name}"
							var="verAnimal">
							
						</spring:url> <a href="${fn:escapeXml(verAnimal)}"> ${animal.name}</a>
                
                    
                </td>
                
                <td>
                    <c:out value="${animal.type}"/>
                </td>
                <td>
                    <c:out value="${animal.birthDate}"/>
                </td>
                 <td>
                    <c:out value="${animal.diasEnRefugio}"/>
                </td>
        </tr></c:forEach>
        </tbody>
    </table>
							
							
							
						</c:forEach><br><br>
						
</petclinic:layout>
