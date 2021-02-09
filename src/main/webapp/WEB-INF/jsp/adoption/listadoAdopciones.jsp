<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adopciones">
    <h2>Adoptions</h2>


    <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr>
         <th style="width: 200px;" valign = "middle">Owner </th>
         <th valign = "middle">Pet</th>
          <th valign = "middle">Animal</th>
           <th style="width: 100px;" valign = "middle">Date</th>
            
            
         
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adopciones}" var="adoption" varStatus="i">
            <tr>
             <td valign = "middle">
                    <c:out value="${adoption.owner.firstName}  "/>
                    <c:out value="${adoption.owner.lastName}  "/>
                </td>       
                   <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Name</dt>
                        <dd>
                            <c:out value="${adoption.pet.name}" />
                        </dd>
                        <dt>Birth Date</dt>
                        <dd>
                            <petclinic:localDate date="${adoption.pet.birthDate}" pattern="yyyy-MM-dd" />
                        </dd>
                        <dt>Type</dt>
                        <dd>
                            <c:out value="${adoption.pet.type.name}" />
                        </dd>
                    </dl>
                </td>
                   <td valign="middle">
                    <dl class="dl-horizontal">
                        <dt>Name</dt>
                        <dd>
                            <c:out value="${adoption.animal.name}" />
                        </dd>
                        <dt>Birth Date</dt>
                        <dd>
                            <petclinic:localDate date="${adoption.animal.birthDate}" pattern="yyyy-MM-dd" />
                        </dd>
                        <dt>Type</dt>
                        <dd>
                            <c:out value="${adoption.animal.type.name}" />
                        </dd>
                    </dl>
                </td>
                <td>
                    <c:out value="${adoption.date}"/>
                </td>
       
         

                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
