<%@ page session="true" trimDirectiveWhitespaces="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" type="text/css" href="stars.css">
<petclinic:layout pageName="reviews">
	<jsp:attribute name="customScript">
        <script>
									$(function() {
										$("#reviewDate").datepicker({
											dateFormat : 'yy/mm/dd'
										});
									});
								</script>
    </jsp:attribute>
	<jsp:body>
        <h2>
            New Review
        </h2>
        
       
        <form:form modelAttribute="review" class="form-horizontal"
			action="/hotel/review/saveReview/${ownerId}">
             
            <div class="form-group has-feedback">
                
               <%--  <petclinic:inputField label="Rating" name="stars" /> --%>
                
               
                <petclinic:inputField label="Tittle" name="tittle" />
                <petclinic:inputField label="Description"
					name="description" />
                <petclinic:inputField label="Date of booking "
					name="reviewDate" />
                <br>
                <label for="hotel"> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                         Hotel's booking</label>
                        <select name="hotel">
   						 <c:forEach items="${hoteles}" var="hotel">  
   						 <option value="${hotel.id}">${hotel.city} 
							
					
					
					</c:forEach>  
    </select>   
                <div class="control-group"><br> 	
                             <div class="rate"><h3> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Stars</h3>
    <input type="radio" id="star5" name="stars" value="5" required="required"/>
    <label for="star5" title="text">5 stars</label>
    <input type="radio" id="star4" name="stars" value="4"required="required" />
    <label for="star4" title="text">4 stars</label>
    <input type="radio" id="star3" name="stars" value="3"required="required" />
    <label for="star3" title="text">3 stars</label>
    <input type="radio" id="star2" name="stars" value="2" required="required"/>
    <label for="star2" title="text">2 stars</label>
    <input type="radio" id="star1" name="stars" value="1" required="required"/>
    <label for="star1" title="text">1 star</label>
  </div>  
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    
                            <button class="btn btn-default"
						type="submit">Add review</button>
             
                </div>
            </div>
  
        </form:form>
        
      
    </jsp:body>
</petclinic:layout>

<style>


.rate {
    float: left;
    height: 46px;
    padding: 0 5px;
}
.rate:not(:checked) > input {
    position:absolute;
    top:-9999px;
}
.rate:not(:checked) > label {
    float:right;
    width:20px;;
    overflow:hidden;
    white-space:nowrap;
    cursor:pointer;
    font-size:30px;
    color:#ccc;
}
.rate:not(:checked) > label:before {
    content: '';
}
.rate > input:checked ~ label {
    color: #ffc700;    
}
.rate:not(:checked) > label:hover,
.rate:not(:checked) > label:hover ~ label {
    color: #deb217;  
}
.rate > input:checked + label:hover,
.rate > input:checked + label:hover ~ label,
.rate > input:checked ~ label:hover,
.rate > input:checked ~ label:hover ~ label,
.rate > label:hover ~ input:checked ~ label {
    color: #c59b08;
}
</style>
