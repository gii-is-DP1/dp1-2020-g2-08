<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="refugios">



 <h1>More time at all shelters: ${masViejo.name} (${masViejo.diasEnRefugio} days)</h2><br> 



    <h2>All our animals for adoptions</h2><br>
    
<c:forEach items="${shelters}" var="shelter" varStatus="i">
<c:if test="${shelter.adopted=='false'}">
<h2> ${shelter.city}'s shelter</h2>
    
							<table id="sheltersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Type</th>
            <th style="width: 200px;">Birthdate</th>
            <th style="width: 200px;">Days at shelter</th>
          <sec:authorize access="hasAuthority('adminShelter')">  <th style="width: 200px;">Actions</th>
            <th style="width: 200px;"></th></sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${shelter.animals}" var="animal"
							varStatus="i">
							
							<c:choose>
    <c:when test="${animal.state=='avaible'}">
       
    
							
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
                
              <sec:authorize access="hasAuthority('adminShelter')">  <td><spring:url value="/shelter/animals/${animal.id}/delete/"
							var="borrarAnimal">
							
						</spring:url> <a href="${fn:escapeXml(borrarAnimal)}"> Delete</a></td></sec:authorize>
				
			<sec:authorize access="hasAuthority('adminShelter')">	<td><spring:url value="/shelter/animals/${animal.id}/edit/"
							var="initUpdateForm">
							
						</spring:url> <a href="${fn:escapeXml(initUpdateForm)}"> Edit</a></td></sec:authorize>
        </tr>
        
        
        </c:when>    
    <c:otherwise>
        
        <br />
    </c:otherwise>
</c:choose>
        
        
        
        </c:forEach>
        </tbody>
    </table>
							
							
							</c:if>
						</c:forEach><br><br>
						
</petclinic:layout>
