<%@page import="java.util.Calendar"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Hypertension and Diabetes Data Summary Tool</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
<link rel="icon" type="image/png" href="images/hpdm.png"/>
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="Login_v6/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login_v6/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login_v6/fonts/iconic/css/material-design-iconic-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login_v6/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="Login_v6/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login_v6/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login_v6/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="Login_v6/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="Login_v6/css/util.css">
	<link rel="stylesheet" type="text/css" href="Login_v6/css/main.css">
<!--===============================================================================================-->

      <%if (session.getAttribute("hpdm_login") != null) { %>
                                <script type="text/javascript"> 
                    
                    var n = noty({text: '<%=session.getAttribute("hpdm_login")%>',
                        layout: 'center',
                        type: 'Success'});
                    
                </script> <%
                session.removeAttribute("hpdm_login");
                            }

                        %>

</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-t-3 p-b-20">
				<form action='hpdm_login' class="login100-form validate-form">
					<h3 style="text-align: center;">Hypertension and Diabetes Data Summary Tool</h3>
                                        <hr>
					<span class="login100-form-avatar">
                                            <img src="images/hpdm.png" alt="welcome">
					</span>
					

					<div class="wrap-input100 validate-input m-b-50" data-validate="Enter Access Code">
						<input class="input100" type="password" name="codeaccess">
						<span class="focus-input100" data-placeholder="Enter Acess Code"></span>
					</div>

					<div class="container-login100-form-btn">
						<button class="login100-form-btn" style="background-color: #2196f3;">
							Proceed
						</button>
					</div>
<%
Calendar cal = Calendar.getInstance();
int year= cal.get(Calendar.YEAR);              

%>
					 
				</form>
			</div>
		</div>
<p align="center" style=" font-size: 18px;"> &copy  Usaid Tujenge Jamii | USAID <%=year%></p>
	</div>
	

	<div id="dropDownSelect1"></div>
	
<!--===============================================================================================-->
	<script src="Login_v6/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="Login_v6/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="Login_v6/vendor/bootstrap/js/popper.js"></script>
	<script src="Login_v6/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="Login_v6/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="Login_v6/vendor/daterangepicker/moment.min.js"></script>
	<script src="Login_v6/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="Login_v6/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="Login_v6/js/main.js"></script>
 <script type="text/javascript" src="js/noty/jquery.noty.js"></script>
<script type="text/javascript" src="js/noty/layouts/top.js"></script>
<script type="text/javascript" src="js/noty/layouts/center.js"></script>
<script type="text/javascript" src="js/noty/themes/default.js"></script>

</body>
</html>