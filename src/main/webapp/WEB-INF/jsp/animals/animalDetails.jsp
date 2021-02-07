<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="Animal details">
    <h2>Animal Details</h2><br><br><br>

    

<div class="clearfix">
  <div class="column menu">
   <spring:url value="/resources/images/pets.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${animal.imageUrl}"/>
  </div>

  <div class="column content">
     <h2>${animal.name}</h2>
         <p>${animal.description}</p >
        
   <br>
    <button class="button-success">Adopt</button>
    
    
                
                <spring:url value="/shelter/adoption/new"
							var="initAdoptionForm">
							
						</spring:url> <a href="${fn:escapeXml(initAdoptionForm)}"> ${animal.name}</a>
                
                    
                
    
  </div>
</div>

<div class="footer">
   <h2>Details</h2>
       <li>Bith date: ${animal.birthDate}</li> 
       <li>Sex: ${animal.sex}</li> 
       <li>Days at shelter: ${animal.diasEnRefugio}</li> 
       <li>Type: ${animal.type}</li> 
       <li>Date of admission: ${animal.shelterDate}</li> 
</div>
    
   
    
</petclinic:layout>

<style>
* {
  box-sizing: border-box;
}

 .footer {
 border: 1px solid black;
  background-color: #B1B6B3;
  color: white;
  padding: 15px;
}

.column {
  float: left;
  padding: 15px;
}

.clearfix::after {
  content: "";
  clear: both;
  display: table;
}

.menu {
  width: 25%;
}

.content {
  width: 75%;
}

</style>
