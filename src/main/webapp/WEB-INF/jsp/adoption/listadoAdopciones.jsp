<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>




<petclinic:layout pageName="Adoptions">
	<jsp:attribute name="customScript">
        <script>
									
								</script>
    </jsp:attribute>
	<jsp:body>
        <h2>
			 Choose one shelter to adopt
		</h2>
		
		<c:forEach items="${shelters}" var="shelter" varStatus="i">
		
		
		<h1>Shelter in ${shelter.city} </h1>
		
		<table id="sheltersTable" class="table table-striped">
        <thead>
        <tr>
           
            <th style="width: 50px;">Total number of adoptions</th>
            <th style="width: 50px;">Number of adoptions remainings</th>
           
            <th style="width: 50px;">Actions</th>
        </tr>
        </thead>
        <tbody>
        
            <tr>
              
               
                <td>
                    <c:out value="${numeroAdoptions[i.index]}" />
                </td>
                 <td>
                    <c:out value="${numeroAdoptionsremainings[i.index]}" />
                </td>
                 <td>
                   
							<spring:url value="/animals"
									var="nuevaAdopcion">
							
						</spring:url> <a href="${fn:escapeXml(nuevaAdopcion)}"> New Adoption</a></td>
                     
            </tr>
        
        </tbody>
    </table>
		
		
		
		
		
		
		</c:forEach>

        
    </jsp:body>

</petclinic:layout>
