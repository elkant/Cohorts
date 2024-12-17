<%@page import="java.util.Calendar"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>IMIS Clinical</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
<link rel="icon" type="image/png" href="images/imis_clinical.png"/>
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

<%

String ac="";
String p="";
String rd="";



if(request.getParameter("p")!=null){
    p=request.getParameter("p");
    ac=request.getParameter("ac");
    rd=request.getParameter("rd");
}
%>


<%if(session.getAttribute("kd_session")!=null){
         
        if(request.getParameter("p")!=null){} else {
        response.sendRedirect("clinicalhome.jsp"); 
        }
     
     
     %><%} else {  }%>   


               
</head>
<body>
    
    
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-t-3 p-b-20">
				<form action='facility_login' class="login100-form validate-form">
                                    
                                    <span class="login100-form-avatar">
                                            <img  src="images/imis_clinical.png" alt="welcome">
					</span>
					<br/>
					<h3 style="text-align: center;background-color: #2196f3;padding:5px;color:white;border-radius: 6px;">IMIS Clinical</h3>
                                        <hr>
					

					<div class="wrap-input100 validate-input m-b-50" data-validate="Email address">
                                            <input value="<%=ac%>" class="input100" type="text" name="username" id="username">
						<span class="focus-input100" data-placeholder="Email address"></span>
					</div>
                                        <div class="wrap-input100 validate-input m-b-50" data-validate="Enter Password">
						<input  value="<%=p%>" class="input100" type="password" name="password">
						<span class="focus-input100" data-placeholder="Enter Password"></span>
					</div>
      <input type="hidden" name="ipv4ad" id="ipv4ad"/>
					<div class="container-login100-form-btn">
						<button id="lgn" class="login100-form-btn" style="background-color: #2196f3;">
							Login
						</button>
					</div>
                                        
                                        <br/>
                                        <div id="ujumbe"></div>
                                        <hr>
                                        <div class="container-login100-form-btn">
						<b><a href="clinical_registration.jsp" ><font color='green'>Create user account</font></a></b>
					</div>
                                        
                                        
                                        <input type="hidden" name="ipv4ad" id="ipv4ad"/>
                                        
                                        <input type='hidden' name='rd' value='<%=rd%>'/>
                                        
<%
Calendar cal = Calendar.getInstance();
int year= cal.get(Calendar.YEAR);              

%>
					 
				</form>
			</div>
		</div>
<p align="center" style=" font-size: 18px;"> &copy  USAID Tujenge Jamii | USAID <%=year%></p>
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


<script>
    

if($("#username")!==''){
    
    $('#lgn').click();
}
    
    
    
    
    
    
</script>

<%if (session.getAttribute("login") != null) { %>
     <script type="text/javascript"> 
    $('#ujumbe').html("<%=session.getAttribute("login")%>");
    </script> 
    <% session.removeAttribute("login");}
  

                        %>
    

                        
                         <script type="text/javascript">
    
    $.getJSON('https://api.ipify.org?format=json', function(data) {
    console.log("Client's IP address is: " + data.ip);
    $("#ipv4ad").val(data.ip);
    
});
    
    </script>
       
     
                        
</body>
</html>