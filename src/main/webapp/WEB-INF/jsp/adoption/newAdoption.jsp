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
            New Adoption
        </h2>
        <form:form modelAttribute="animal"
                   class="form-horizontal">
            <input type="hidden" name="id" value="${animal.id}"/>
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Owner</label>
                    <div class="col-sm-10">
                        <c:out value="${owner.firstName} ${owner.lastName}"/>
                    </div>
                </div>
                <petclinic:inputField label="Name" name="name"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-default" type="submit">Update Pet</button>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
