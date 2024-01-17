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
<style>

.label_clean {

text-align: center;
border-radius:0.25rem;
padding: 0.5rem  0.75rem ;
font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif;
font-size: 1rem;
margin-top: 8px;
background-color: #26252b;
color:white;
}
</style>
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
		<div class="container-login100" style="align-items:flex-start;">
			<div class="" style="width:90%;">
				<form action='login' class="login100-form validate-form">
					<!--<h3 style="text-align: center;"> Clinical Home</h3>-->
                                        <h5 style="background-color: #3c9ced;"  class="label_clean">Clinical IMIS</h5> 
                                        <!--<hr>-->
                                        <div class='col-md-12'>
                                            
                                                <div class="form-row col-md-12"> 
            
            
<div class='form-group col-md-4'>
    <hr/>
    <!--<span class="login100-form-avatar"> <a href='pmtct_ovc_main.jsp'>   <img src="images/pmtct_ovc.png" alt="welcome"></a></span>-->
    <span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=mother'>   <img src="images/pmtct_ovc.png" alt="welcome"></a></span>
    <div >
    <h5  class="label_clean">PMTCT OVC</h5> </div>
    </div>
<div class='form-group col-md-4'>
    <hr/>
    <span class="login100-form-avatar"> <a href='mortality_audit.jsp'>   <img src="images/mot_audit.png" alt="Mortality Audir"></a></span>
<h5  class="label_clean">Mortality Audit</h5> 
</div>
<div class='form-group col-md-4'>
    <hr/>
    <span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=cxca_pos'>   <img src="images/cxca_screen.png" alt="CXCA Screening Treatment"></a></span>
<h5  class="label_clean">Cervical Cancer Treatment</h5> 
</div>

                                               
                                                
                                                </div>
                                            
					
					
<div class="form-row col-md-12"> 
            
            

<div class='form-group col-md-4'>
    <hr/><span class="login100-form-avatar"> <a href='binti_shujaa.jsp?frm=binti_enrollment'>   <img src="images/binti_shujaa.png" alt="Binti Shujaa"></a></span>
<h5  class="label_clean">Binti Shujaa</h5> 
</div>
                                               
<div class='form-group col-md-4'>
    <hr/><span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=client_exit_form'>   <img src="images/client_exit.png" alt="Client Exit"></a></span>
<h5  class="label_clean">Client Exit Interview</h5> 
</div>
    
    
    <div class='form-group col-md-4'>
    <hr/><span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=hpv_vaccination'>   <img src="images/hpv.png" alt="HPV Vaccination"></a></span>
<h5  class="label_clean">HPV Vaccination Status</h5> 
</div>
          
                                                </div>
					
<div class="form-row col-md-12"> 
<div class='form-group col-md-4'>
<hr/><span class="login100-form-avatar"> <a href='ahd_main.jsp'>   <img src="images/ahd.png" alt="AHD"></a></span>
<h5  class="label_clean">AHD</h5> 
</div>
                                                    
                                                    
    
    
    <div class='form-group col-md-4'>
        <hr/><span class="login100-form-avatar"> <a href='hpdm_main.jsp'>   <img src="images/hpdm.png" alt="HPDM"></a></span>
<h5  class="label_clean">Diabetes & Hypertension Management</h5> 
</div>
     
    
<div class='form-group col-md-4'>
        <hr/><span class="login100-form-avatar"> <a href='otz_main.jsp'>   <img src="images/otz.png" alt="OTZ"></a></span>
<h5  class="label_clean">OTZ</h5> 
</div>

                                                   
                                               
                                                
                                                </div>
                                        
<div class="form-row col-md-12"> 

    <div class='form-group col-md-6'>
        <hr/><span class="login100-form-avatar"> <a href='hei_weekly_main.jsp'>   <img src="images/hei_weekly.png" alt="HEI"></a></span>
<h5  class="label_clean">Weekly HEI</h5> 
</div>

    
      <div class='form-group col-md-6'>
          <hr/><span class="login100-form-avatar"> <a href='see_main.jsp'>   <img src="images/see.png" alt="SEE"></a></span>
<h5  class="label_clean">Social Economic & Enviromental Factors affecting ROCs</h5> 
</div>
    
    
</div>
					

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