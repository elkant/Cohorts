<%@page import="java.util.Calendar"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>KP Home Page</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
<link rel="icon" type="image/png" href="Login_v6/images/kp.png"/>
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

      <%if (session.getAttribute("otz_login") != null) { %>
                                <script type="text/javascript"> 
                    
                    var n = noty({text: '<%=session.getAttribute("otz_login")%>',
                        layout: 'center',
                        type: 'Success'});
                    
                </script> <%
                session.removeAttribute("otz_login");
                            }

                        %>

</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-t-3 p-b-20">
				<form action='otz_login' class="login100-form validate-form">
					<h3 style="text-align: center;">Select the Next Section Below to proceed</h3>
                                        <hr>
<!--					<span class="login100-form-avatar">
                                            <img src="images/otz.png" alt="welcome">
					</span>-->
					

					<%if (session.getAttribute("level") != null) { %>
 <!---M&E Admins only-->
  <%if(session.getAttribute("level").toString().equals("1")){%>
                                        
					<div class="container-login100-form-btn">
						<a href='kp_main.jsp' class="login100-form-btn" style="background-color: #2196f3;">
							Monthly Data - Form 1a
						</a>
                                           
					</div>
  
  <br/>
  
                                       <div class="container-login100-form-btn">
						<a href='miclif.jsp' class="login100-form-btn" style="background-color: #2196f3;">
							Monthly Data - Micare & Lift Up
						</a>
                                           
					</div>

<br/>
 <%}%>
 <!---M&E Admins and hts counsellors-->
 <%if(session.getAttribute("level").toString().equals("1")|| session.getAttribute("level").toString().equals("3")){%>
<div class="container-login100-form-btn">
						
                                            <a href='kp_daily_main.jsp' class="login100-form-btn" style="background-color: #2196f3;">
							Daily Data Section
						</a>
</div>

<br/>
<%}%>
<!---M&E Admins only-->
 <%if(session.getAttribute("level").toString().equals("1")){%>
                                        <div class="container-login100-form-btn">
						
                                            <a href='kp_verification.jsp' class="login100-form-btn" style="background-color: #2196f3;">
							KP_PREV Verification Summary
						</a>
					</div>

<br/>
<%}%>
<!---M&E Admins and Outreach workers-->

 <%if(session.getAttribute("level").toString().equals("1")|| session.getAttribute("level").toString().equals("4")){%>
                                        <div class="container-login100-form-btn">
						
                                            <a href='kp_client_verification.jsp' class="login100-form-btn" style="background-color: #2196f3;">
							KP_PREV Verification Clients List
						</a>
					</div>
<%}%>
                                       <%}%>


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