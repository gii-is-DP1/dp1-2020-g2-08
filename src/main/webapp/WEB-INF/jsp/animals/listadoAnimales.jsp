<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<script>

function dateDiffInDays() {
	var now = LocalDate().now;
	var date1=new Date('December 21, 2019');
    // round to the nearest whole number
    var res = Math.round((now-date1)/(1000*60*60*24));
    document.write('Voy a ser un genio en JavaScript');
}
var daysDiff=dateDiffInDays(new Date('December 21, 2019')); //4 days till XMAS
</script>

<petclinic:layout pageName="refugios">
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
                    <c:out value="${animal.name}"/>
                </td>
                <td>
                    <c:out value="${animal.type}"/>
                </td>
                <td>
                    <c:out value="${animal.birthDate}"/>
                </td>
                 <td> <a onclick="dateDiffInDays()"> ver dias</p></a>
                    <c:out value="${animal.shelterDate}"/>
                </td>
        </tr></c:forEach>
        </tbody>
    </table>
							
							
							
						</c:forEach><br><br>
</petclinic:layout>
