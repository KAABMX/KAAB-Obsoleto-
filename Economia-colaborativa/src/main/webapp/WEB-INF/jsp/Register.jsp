<%-- 
    Document   : Register
    Created on : Sep 11, 2018, 1:26:47 PM
    Author     : juan
--%>
<jsp:include page='header.jsp'>
    <jsp:param name="title" value="Registro"/>
</jsp:include>
<div class="container white z-depth-2">
	<ul class="tabs teal">
		<li class="tab col s3"><a class="white-text active" href="#login">login</a></li>
		<li class="tab col s3"><a class="white-text" href="#register">register</a></li>
	</ul>
	<div id="login" class="col s12">
		<form class="col s12">
			<div class="form-container">
				<h3 class="teal-text">Hello</h3>
				<div class="row">
					<div class="input-field col s12">
						<input id="email" type="email" class="validate">
						<label for="email">Email</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="password" type="password" class="validate">
						<label for="password">Password</label>
					</div>
				</div>
				<br>
				<center>
					<button class="btn waves-effect waves-light teal" type="submit" name="action">Connect</button>
					<br>
					<br>
					<a href="">Forgotten password?</a>
				</center>
			</div>
		</form>
	</div>
	<div id="register" class="col s12">
		<form class="col s12">
			<div class="form-container">
				<h3 class="teal-text">Welcome</h3>
				<div class="row">
					<div class="input-field col s6">
						<input id="last_name" type="text" class="validate">
						<label for="last_name">First Name</label>
					</div>
					<div class="input-field col s6">
						<input id="last_name" type="text" class="validate">
						<label for="last_name">Last Name</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="email" type="email" class="validate">
						<label for="email">Email</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="email-confirm" type="email" class="validate">
						<label for="email-confirm">Email Confirmation</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="password" type="password" class="validate">
						<label for="password">Password</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="password-confirm" type="password" class="validate">
						<label for="password-confirm">Password Confirmation</label>
					</div>
				</div>
				<center>
					<button class="btn waves-effect waves-light teal" type="submit" name="action">Submit</button>
				</center>
			</div>
		</form>
	</div>
</div>
<%@include file='footer.jsp'%>