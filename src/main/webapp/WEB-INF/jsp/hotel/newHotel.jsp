<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="new Hotel">

	<jsp:body>
        <h2>
			<c:if test="${hotel['new']}">New </c:if> Hotel  </h2>

<!--  Hay que enviar los datos de aforo,ocupadas, city-->
        

        <form:form modelAttribute="hotel" class="form-horizontal">
			
            <div class="form-group has-feedback">
                <petclinic:inputField label="Aforo" name="aforo" />
                <petclinic:inputField label="Ocupadas" name="ocupadas" />
                 <petclinic:inputField label="City" name="city" />          
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">                   
                  
                    <button class="btn btn-default" type="submit">Save Hotel</button>
                </div>
            </div>
        </form:form>

        <br />
        
    </jsp:body>

</petclinic:layout>
