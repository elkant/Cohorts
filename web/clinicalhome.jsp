<%@page import="java.util.HashMap"%>
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
                        
                          <%if(session.getAttribute("kd_session")!=null){%><%} else {  response.sendRedirect("logout");}%>  

</head>
<body>
	
	<div class="limiter">
		<div class="container-login100" style="align-items:flex-start;">
			<div class="" style="width:90%;">
				<form action='login' class="login100-form validate-form">
                                    <a href="logout">Log Out</a>
					<!--<h3 style="text-align: center;"> Clinical Home</h3>-->
                                        
                                        
                                        
                                        <!-----For USAID For Better Health Team---->
                                        <% 
                                            String utype="";
                                            
                                            if(session.getAttribute("kd_session")!=null){                                        
                                        
                                   HashMap<String, String> ut= new HashMap();
                                     ut=(HashMap)session.getAttribute("kd_session");                                     
                                     utype=ut.get("usertype");
                                            
                                        }
                                            
                                        if(utype.equals("U4BH")){
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        %>
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                                                            <h5 style="background-color: #3c9ced;"  class="label_clean">Clinical Forms</h5> 
                                        <!--<hr>-->
                                        <div class='col-md-12'>
                                            
                                                <div class="form-row col-md-12"> 
            
            
<div class='form-group col-md-12'>
    <hr/>
    <!--<span class="login100-form-avatar"> <a href='pmtct_ovc_main.jsp'>   <img src="images/pmtct_ovc.png" alt="welcome"></a></span>-->
    <span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=mother'>   <img src="images/pmtct_ovc.png" alt="welcome"></a></span>
    <div >
    <h5  class="label_clean">PMTCT OVC</h5> </div>
    </div>
    </div>
    </div>
              <%}
            



else  if(utype.equals("Binti")){
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        %>
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                        
                                                                            <h5 style="background-color: #3c9ced;"  class="label_clean">Clinical Forms</h5> 
                                        <!--<hr>-->
                                        <div class='col-md-12'>
                                            
                                                <div class="form-row col-md-12"> 
            
            
 <div class='form-group col-md-3'>
    <hr/><span class="login100-form-avatar"> <a href='binti_shujaa.jsp?frm=binti_enrollment'>   <img src="images/binti_shujaa.png" alt="Binti Shujaa"></a></span>
<h5  class="label_clean">Binti Shujaa</h5> 
</div>
    </div>
    </div>
              <%}


              
else {
              
              
              %>                          
                                        
                                        
                                        
                                         <h5 style="background-color: #3c9ced;"  class="label_clean">Daily Forms</h5>                                        
                                         
                                         
                                         
                                         <div class="col-md-12">
                                             
                                              <div class="form-row col-md-12"> 
            
            

<div class='form-group col-md-3'>
    <hr/>
    <span class="login100-form-avatar">
        <a target="_blank" href="https://usaidtujengejamii.org:8443/Cohorts/dailyart.jsp" >
            <img  src="images/icons/tx.png"/></a>
    </span>
<h5  class="label_clean">Daily ART</h5> 
</div>
<div class='form-group col-md-3'>
    <hr/>
    <span class="login100-form-avatar"> 
      <a target="_blank" href="https://usaidtujengejamii.org:8443/Cohorts/importDtriangulation.jsp" class="btn btn-light">
                            <img  title="daily Data Triangulation"  src="images/icons/verification.png"/></a>
    
    </span>
<h5  class="label_clean">Data Triangulation</h5> 
</div>

                                         

                                                  
<div class='form-group col-md-3'>
<hr/><span class="login100-form-avatar"> 
    <a target="_blank" href="https://usaidtujengejamii.org:8443/Cohorts/importpns.jsp" class="btn btn-light">
                            <img  src="images/pns.png"/></a>
</span>
<h5  class="label_clean">Daily PNS</h5> 
</div>
                                                  <div class='form-group col-md-3'>
    <hr/>
    <!--<span class="login100-form-avatar"> <a href='pmtct_ovc_main.jsp'>   <img src="images/pmtct_ovc.png" alt="welcome"></a></span>-->
    <span class="login100-form-avatar"> 
       <a target="_blank" href="https://usaidtujengejamii.org:8443/htsrri/index.jsp?p=909090" class="btn btn-light">
                            <img   src="images/htsrri.png"/></a>
    
    </span>
    <div >
    <h5  class="label_clean">HTS RRI Offline</h5> </div>
    </div>
                                               
                                                
                                                </div>
                                             
                                             </div>
                                         
                                        
                                        <h5 style="background-color: #3c9ced;"  class="label_clean">Clinical Forms</h5> 
                                        <!--<hr>-->
                                        <div class='col-md-12'>
                                            
                                                <div class="form-row col-md-12"> 
            
            
<div class='form-group col-md-3'>
    <hr/>
    <!--<span class="login100-form-avatar"> <a href='pmtct_ovc_main.jsp'>   <img src="images/pmtct_ovc.png" alt="welcome"></a></span>-->
    <span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=mother'>   <img src="images/pmtct_ovc.png" alt="welcome"></a></span>
    <div >
    <h5  class="label_clean">PMTCT OVC</h5> </div>
    </div>
<div class='form-group col-md-3'>
    <hr/>
    <span class="login100-form-avatar"> <a href='mortality_audit.jsp'>   <img src="images/mot_audit.png" alt="Mortality Audir"></a></span>
<h5  class="label_clean">Mortality Audit</h5> 
</div>
<div class='form-group col-md-3'>
    <hr/>
    <span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=cxca_pos'>   <img src="images/cxca_screen.png" alt="CXCA Screening Treatment"></a></span>
<h5  class="label_clean">Cervical Cancer Treatment</h5> 
</div>

                                         
    <div class='form-group col-md-3'>
<hr/><span class="login100-form-avatar"> <a href='ahd_main.jsp'>   <img src="images/ahd.png" alt="AHD"></a></span>
<h5  class="label_clean">AHD</h5> 
</div>
                                               
                                                
                                                </div>
                                            
					
					
<div class="form-row col-md-12"> 
            

                                               
<div class='form-group col-md-3'>
    <hr/><span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=client_exit_form'>   <img src="images/client_exit.png" alt="Client Exit"></a></span>
<h5  class="label_clean">Client Exit Interview</h5> 
</div>
    
    
    <div class='form-group col-md-3'>
    <hr/><span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=hpv_vaccination'>   <img src="images/hpv.png" alt="HPV Vaccination"></a></span>
<h5  class="label_clean">HPV Vaccination Status</h5> 
</div>
    
    <div class='form-group col-md-3'>
    <hr/><span class="login100-form-avatar"> <a href='binti_shujaa.jsp?frm=binti_enrollment'>   <img src="images/binti_shujaa.png" alt="Binti Shujaa"></a></span>
<h5  class="label_clean">Binti Shujaa</h5> 
</div>
    
    <div class='form-group col-md-3'>
        <hr/><span class="login100-form-avatar"> <a href='otz_main.jsp'>   <img src="images/otz.png" alt="OTZ"></a></span>
<h5  class="label_clean">OTZ</h5> 
</div>

    
                                                </div>
					
<div class="form-row col-md-12"> 

                                                    
                                                    
    
    
    <div class='form-group col-md-3'>
        <hr/><span class="login100-form-avatar"> <a href='hpdm_main.jsp'>   <img src="images/hpdm.png" alt="HPDM"></a></span>
<h5  class="label_clean">Diabetes & Hypertension Management</h5> 
</div>
     
     <div class='form-group col-md-3'>
          <hr/><span class="login100-form-avatar"> <a href='see_main.jsp'>   <img src="images/see.png" alt="SEE"></a></span>
<h5  class="label_clean">Social Economic & Environmental Factors affecting ROCs</h5> 
</div>


         <div class='form-group col-md-3'>
          <hr/><span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=hei_audit'>   <img src="images/heiaudit.png" alt="Hei Audit"></a></span>
<h5  class="label_clean">HEI Audit</h5> 
</div>      
    
    
    <div class='form-group col-md-3'>
        <hr/><span class="login100-form-avatar"> <a href='https://usaidtujengejamii.org:8443/InternalSystem/emr_index.jsp?c=909090'>   <img src="images/emr_status.png" alt="EMR Status"></a></span>
<h5  class="label_clean">EMR Status</h5> 
</div>
                                               
                                                
                                                </div>
                                            
                                            
                                            <div class="form-row col-md-12"> 

                                                    
                                                    
    
    
    <div class='form-group col-md-3'>
        <hr/><span class="login100-form-avatar"> <a href='surgereports.jsp'>   <img src="images/icons/reports.png" alt="Surge Reports"></a></span>
<h5  class="label_clean">Surge Reports & Tracker</h5> 
</div>
     
     <div class='form-group col-md-3'>
          <hr/><span class="login100-form-avatar"> <a href='hfr.jsp'>   <img src="images/hfr.png" alt="HFR"></a></span>
<h5  class="label_clean">High Frequency Reporting (HFR)</h5> 
</div>


         <div class='form-group col-md-3'>
          <hr/><span class="login100-form-avatar"> <a href='rri_gaps_main.jsp'>   <img src="images/rri.png" alt="Service Gaps"></a></span>
<h5  class="label_clean">Service Gaps</h5> 
</div>      
    
    
    <div class='form-group col-md-3'>
         <hr/><span class="login100-form-avatar"> <a href='retention.jsp'>   <img src="images/retention.png" alt="Retention Audit"></a></span>
<h5  class="label_clean">Retention Audit</h5> 
</div>
                                               
                                                
                                                </div>
                                            
                                                               <div class="form-row col-md-12"> 

                                                    
                                                    
    
    
    <div class='form-group col-md-3'>
        <hr/><span class="login100-form-avatar"> <a href='clinicalforms.jsp?frm=intergration_feedback'>   <img src="images/see_1.png" alt="Surge Reports"></a></span>
<h5  class="label_clean">Intergration feedback Form</h5> 
</div>
                                                                   
                                                                     
    <div class='form-group col-md-3'>
        <hr/><span class="login100-form-avatar"> <a href='me_matrix_main.jsp'>   <img src="images/me_matrix.png" alt="M&E Matrix"></a></span>
        <h5  class="label_clean">M&E Matrix</h5> 
    </div>
                                            
                                                </div>
                                            
                                            
                                        
                                            
                                            
                                            
                                            <div class="form-row col-md-12"> 
<!--
    <div class='form-group col-md-4'>
         <hr/><span class="login100-form-avatar"> <a href='hei_weekly_main.jsp'>   <img src="images/hei_weekly.png" alt="HEI"></a></span>
<h5  class="label_clean">Weekly HEI</h5> 
</div>-->

                                                

                                                
    
 
    
    
</div>
                                            
                                            
                                            
					

                                            </div>
                                        
                                        
                                        
                                        
                                        <%
                                            }
                                        
                                        %>
                                        
                                        
                                        
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



<script > 
     $(document).ready(function(){
       
         
    
     setInterval(function() {
      load_records();
      }, 1000);  
    
     });
     
     function load_records(){
             $.ajax({
        url:'issessionactive',
        type:"post",
        dataType:"html",
        success:function(dt){
           // console.log(dt);
if(dt.trim()==='true'){} else {

window.location.href = "logout.jsp";


}         
        }, 
        error: function(jqXHR, textStatus, errorThrown) {
       //error in loading upload status
       $("#status").html(errorThrown);
            }
  });
     }
</script>


</body>
</html>