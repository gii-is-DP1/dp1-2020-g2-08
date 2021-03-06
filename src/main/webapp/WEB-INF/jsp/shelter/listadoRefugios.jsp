<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="refugios">
    <h2>Shelters</h2>


    <table id="sheltersTable" class="table table-striped">
        <thead>
        <tr>
        <th>City</th>
        <th >Occupation</th>
            <th>Capacity </th>
            <th>Oldest animal </th>
            
            
            <th>Actions</th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${refugios}" var="shelter" varStatus="i">
                <c:if test="${shelter.adopted=='false'}">
            <tr>
             <td>
                    <c:out value="${shelter.city}"/>
                </td>
                 <td>
                    <c:out value="${shelter.animals.size()}"/>
                </td>
                <td>
                    <c:out value="${shelter.aforo}"/>
                </td>
               
                
                <td>
                
                <spring:url value="/shelter/animals/${shelter.id}/animal/${shelter.animalMasViejo}"
							var="verAnimal">
							
						</spring:url> <a href="${fn:escapeXml(verAnimal)}"> ${shelter.animalMasViejo}</a>
                
                    
                </td>
                
                
                
                
          
                <td><spring:url value="/shelter/delete/${shelter.id}"
							var="borrarShelter">
							
						</spring:url> <a href="${fn:escapeXml(borrarShelter)}"> Delete</a></td>
				
				<sec:authorize access="hasAuthority('adminShelter')">
    <td><spring:url value="/shelter/animals/${shelter.id}/animal/new"
							var="initCreationForm">
							
						</spring:url> <a href="${fn:escapeXml(initCreationForm)}"> New animal</a></td></sec:authorize>
               
      

                
            </tr>
             </c:if>
        </c:forEach>
        
        </tbody>
    </table>
</petclinic:layout>
