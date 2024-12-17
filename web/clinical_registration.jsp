<%@page import="General.IdGenerator"%>
<%@page import="java.util.Calendar"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>IMIS Clinical Modules Access</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
<link rel="icon" type="image/png" href="images/pmtct_ovc.png"/>
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

      <%if (session.getAttribute("pmtct_ovc_register") != null) { %>
                                <script type="text/javascript"> 
                    
                    alert('<%=session.getAttribute("pmtct_ovc_register")%>');
                        
                    
                </script> <%
                session.removeAttribute("pmtct_ovc_register");
                
                
                %>
                               
                 <script type="text/javascript"> 
                     
                window.location.href = "index.jsp";     
                     
                </script>
                
                <%
                
                            }

                        %>

</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-t-3 p-b-20">
				<form action='registeruser' class="login100-form validate-form">
                                    
                                  
                                    <hr>
					<h4 style="text-align: center;">Create User Account</h4>
                                        <hr>
<!--					<span class="login100-form-avatar">
                                            <img src="images/pmtct_ovc.png" alt="welcome" style='width:110px;'>
					</span>-->
                                        <%  IdGenerator dp= new IdGenerator(); String nm=""+dp.getRandNo(1,100000); %>
<input required="true" value="<%=nm%>" type='hidden' class='form-control' id='userid' name='userid'  placeholder='Enter id'/>
<input required="true" value="827ccb0eea8a706c4c34a16891f84e7b" type='hidden' class='form-control' id='password' name='password'  placeholder='Enter Password'/>
<input required="true" value="Clinical IMIS" type='hidden' class='form-control' id='assigned_modules' name='assigned_modules'  placeholder='Enter Level'/>
<input required="true" value="1" type='hidden' class='form-control' id='level' name='level'  placeholder='Enter Level'/>
                                        <div class="wrap-input100 validate-input m-b-50" data-validate="Full Namer">
						<input  required='true' class="input100" type="text" maxlength='100' name="fullname">
						<span class="focus-input100" data-placeholder="*Full Name"></span>
					</div>

<div class="wrap-input100 validate-input m-b-50" data-validate="User Type">
    <select  required='true' class="input100" type="text" maxlength='100' name="usertype">
        <option value="">Specify User Type</option>
        <option value="Facility Based">Facility Based Staff</option>
        <option value="U4BH">USAID 4BH Staff</option>
        <option value="Roving Staff">UTJ Roving Staff</option>
        <option value="Binti">Binti Shujaa</option>
        
    </select>
						
					</div>
                                        
					<div class="wrap-input100 validate-input m-b-50" data-validate="Phone number">
						<input pattern='[0-9]{10,10}' required='true' class="input100" type="text" maxlength='10' id='phone' minlength='10' name="phone">
						<span class="focus-input100" data-placeholder="*Phone Number"></span>
					</div>
                                        <div class="wrap-input100 validate-input m-b-50" data-validate="*Email adress">
						<input required='true' class="input100" onChange='checkSimilarity();' type="email" name="email" id='email'>
						<span class="focus-input100" data-placeholder="*Email address"></span>
					</div>
                                         <div class="wrap-input100 validate-input m-b-50" data-validate="Repeat your email">
						<input required='true' class="input100" onChange='checkSimilarity();' type="email" id='email1' name="email1">
						<span class="focus-input100" data-placeholder="*Repeat email address"></span>
					</div>
                                        <div class="wrap-input100  m-b-50" data-validate="*Select Supported Site">
						
						<span class="focus-input100" data-placeholder="*Select Supported Site(s)"></span>
					</div>
                                        <div class="wrap-input100 validate-input m-b-50" data-validate="Supported Site">
                                           
						<select multiple required='true' class="input100"  name="facility_id" id='facility_id'><option value=''>Select Facility</option></select>
						
					</div>
                                        
                                        <span id='msg'></span>

					<div class="container-login100-form-btn">
						<button style='display:none;' id='registerbtn' class="login100-form-btn" style="background-color: #2196f3;">
							Create account
						</button>
					</div>
                                        <br/>
                                        
                                          <div class="container-login100-form-btn">
						<a href='index.jsp'   class="login100-form-btn" style="background-color: #2196f3;">
							Back to Login Page
						</a>
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

<script>
    
    
    function getFacilitiesJson(){
       
           
       
              $.ajax({
                    url:'loadActiveSites',                            
                    type:'post',  
                    dataType: 'html',  
                    success: function(data)
                    {
                     $("#facility_id").html(data);
                   $(document).ready(function() {
          
              $('#facility_id').select2(); 
             
                                 } ); 
                        
                        
                    }
                });
   
   }
  
 
   getFacilitiesJson();
   
   
   
   function checkSimilarity(){
      
       
       var ml1=$("#email").val();
       var ml2=$("#email1").val();
        
       console.log("Checking for similarity");
       console.log("Checking for similarity"+ml1.indexOf("@"));
       
       if(ml1.indexOf("@")>=0){
       
       if(ml1!==ml2){
          
           $("#registerbtn").hide();
           $("#msg").html("<font color='red'>Email addresses do not match</font>");
           
       }
       else {
            $("#registerbtn").show(); 
             $("#msg").html("");
             checkemailRegistration();
            
       }
   }
   else {
        $("#msg").html("<font color='red'>Enter a valid email address</font>");
          $("#registerbtn").hide();
       
   }
       
   }
   
   
     function checkemailRegistration(){
       
     
       var eml=$("#email").val();
       
              $.ajax({
                    url:'checkduplicateemail?eml='+eml,                            
                    type:'post',  
                    dataType: 'html',  
                    success: function(data)
                    {
                        
                        if(data.trim()==='true'){
                     $("#registerbtn").hide();
                     
                     $("#msg").html("<font color='red'>An account using this email account has already been registered. Please check your email for Login instructions</font>");
                 }
                 
                 else {
                     $("#registerbtn").show();
                     $("#msg").html("");
                 }
                  
                        
                        
                    }//end od succes
                });
   
   }
    
    </script>

</body>
</html>