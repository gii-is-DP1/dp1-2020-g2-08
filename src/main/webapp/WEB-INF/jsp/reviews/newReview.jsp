<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reviews">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#reviewDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            New Review
        </h2>
        <form:form modelAttribute="review"
                   class="form-horizontal" action="saveReview">
           <%--  <input type="hidden" name="id" value="${review.id}"/> --%>
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="Rating" name="stars"/>
                <petclinic:inputField label="Tittle" name="tittle"/>
                <petclinic:inputField label="Description" name="description"/>
                <petclinic:inputField label="Fecha " name="reviewDate"/>
                <div class="control-group">
                   
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                            <button class="btn btn-default" type="submit">Add review</button>
             
                </div>
            </div>
        </form:form>
        
      
    </jsp:body>
</petclinic:layout>
