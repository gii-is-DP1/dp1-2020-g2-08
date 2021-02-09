		
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%--> <!--  glyphicon-tree-conifer -->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/find"
					title="find owners">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Find owners</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'vets'}" url="/vets"
					title="veterinarians">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					<span>Veterinarians</span>
				</petclinic:menuItem>


				<div class="nav navbar-nav navbar-right">


					<li class="dropdown"><a href="/hotel" class="dropdown-toggle"
						data-toggle="dropdown"> <span
							class="glyphicon glyphicon glyphicon-calendar"></span> <span>Hotel</span>

							<ul class="dropdown-menu">
								<li><a href="/hotel">All bookings</a></li>
								<li><a href="/hotel/myBookings">My bookings</a></li>
								<li><a href="/hotel/booking/new">New booking</a></li>
								
								<sec:authorize access="hasAuthority('admin')">
								<li><a href="/hotel/listadoHoteles">All Hotels</a></li>
		<li><a href="/hotel/new">New Hotel</a></li>
	</sec:authorize>
								

							</ul>
				</div>

				
						
						<div class="nav navbar-nav navbar-right">


							<li class="dropdown"><a href="/shop" class="dropdown-toggle"
								data-toggle="dropdown"> <span
									class="glyphicon glyphicon glyphicon-shopping-cart"></span> <span>Shop</span>

									<ul class="dropdown-menu">
										<li><a href="/shop">Shop</a></li>
										<sec:authorize access="hasAuthority('adminShop')">
										<li><a href="/shop/admin/products">Products</a></li>
										<li><a href="/shop/admin/sales/total">Sales</a></li>
										<li><a href="/shop/admin/clients">Clients</a></li>
										<li><a href="/shop/admin/coupons">Coupons</a></li>
										<li><a href="/shop/admin/orders">Orders</a></li>
										
										
										
										</sec:authorize>
									</ul>
						</div>
						
						<div class="nav navbar-nav navbar-right">


					<li class="dropdown"><a href="/hotel" class="dropdown-toggle"
						data-toggle="dropdown"> <span
							class="glyphicon glyphicon glyphicon-tree-conifer"></span> <span>Shelter</span>

							<ul class="dropdown-menu">
							<sec:authorize access="hasAuthority('adminShelter')">	<li><a href="/shelter/listadoRefugios">All shelters</a></li>
								<li><a href="/shelter/new">New Shelter</a></li>
								<li><a href="/shelter/animals/adoption/">Adoptions list</a></li></sec:authorize>
								<li><a href="/shelter/animals">Animals</a></li>
								
									

							</ul>
				</div>
								
				
				
				




				<%-- <petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem> --%>

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					
					<li class="dropdown"><a href="/shop" class="dropdown-toggle"
								data-toggle="dropdown">	 <span
									class="glyphicon glyphicon"></span> <span>Register</span>

									<ul class="dropdown-menu">
										<li><a href="/users/new">Register owner</a></li>	
										<li><a href="/users/new/client">Register shop client list</a></li>
									</ul>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
											<sec:authorize access="hasAuthority('client')">
											<p class="text-left">
												<a href="<c:url value="/shop/myOrders" />"
													class="btn btn-primary btn-block btn-sm">My orders</a>
											</p>
											</sec:authorize>
										</div>
										
	
										
										
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('client')">
					<petclinic:menuItem active="${name eq 'cart'}" url="/shop/carrito">
					<span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
				</petclinic:menuItem></sec:authorize>
									
									</ul>
			</ul>
		</div>



	</div>
</nav>
