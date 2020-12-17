<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clients">
    <h2>
        <c:if test="${client['new']}">New </c:if> Client
    </h2>
    <form:form modelAttribute="client" class="form-horizontal" id="add-client-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="First Name" name="firstName"/>
            <petclinic:inputField label="Last Name" name="lastName"/>
            <petclinic:inputField label="Address" name="address"/>
            <petclinic:inputField label="City" name="city"/>
            <petclinic:inputField label="NIF" name="nif"/>
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Telephone" name="telephone"/>
            <petclinic:inputField label="Username" name="nameuser"/>
            <petclinic:inputField label="Password" name="pass"/> 
           <%-- <label for="username"> Username: </label> --%>
           <%-- <input type="text" label="Username" name="username" required/></br> --%>
          <%--  <label for="password"> Password: </label> --%>
          <%--  <input type="password" label="Password" name="password" required/> --%>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${client['new']}">
                        <button class="btn btn-default" type="submit">Add Client</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Client</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
