<%-- 
    Document   : loadTBExcel
    Created on : Jul 27, 2015, 2:41:29 PM
    Author     : Maureen
--%>




<%@page import="db.dbConn"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<!-- BEGIN HEAD -->
<head>
   <meta charset="utf-8" />
   <title>Upload PMTCT Cohort  </title>
   <link rel="shortcut icon" href="images/logo.png"/>
   <meta content="width=device-width, initial-scale=1.0" name="viewport" />
   <meta content="" name="description" />
   <meta content="" name="author" />
   <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
   <link href="assets/css/metro.css" rel="stylesheet" />
   <link href="assets/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" />
   <link href="assets/bootstrap-fileupload/bootstrap-fileupload.css" rel="stylesheet" />
   <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
   <link href="assets/css/style.css" rel="stylesheet" />
   <link href="assets/css/style_responsive.css" rel="stylesheet" />
   <link href="assets/css/style_default.css" rel="stylesheet" id="style_color" />




                
                <style>
                    
                    [data-notify="progressbar"] {
	margin-bottom: 0px;
	position: absolute;
	bottom: 0px;
	left: 0px;
	width: 100%;
	height: 5px;
}
                    
                </style>
                
                
 
                
       <%if(session.getAttribute("kd_session")!=null){%><%} else {  response.sendRedirect("logout");}%>          
  
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="fixed-top">
   <!-- BEGIN HEADER -->
   <div class="header navbar navbar-inverse navbar-fixed-top">
      <!-- BEGIN TOP NAVIGATION BAR -->
      <div class="navbar-inner">
         <div class="container-fluid">
            <!-- BEGIN LOGO -->
            <h1 style="text-align:center;font-size: 50px;color:white;padding-bottom:16px ;font-weight: bolder;">PMTCT Cohorts</h1><br/>
            
            <!-- END LOGO -->
            <!-- BEGIN RESPONSIVE MENU TOGGLER -->
            <a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
            <img src="assets/img/menu-toggler.png" alt="" />
            </a>          
                      
            <ul class="nav pull-right">
          
            </ul>
            <!-- END TOP NAVIGATION MENU --> 
         </div>
      </div>
      <!-- END TOP NAVIGATION BAR -->
   </div>
   <!-- END HEADER -->
   <!-- BEGIN CONTAINER -->
   <div class="page-container row-fluid">
      <!-- BEGIN SIDEBAR -->
      <div class="page-sidebar nav-collapse collapse">
         <!-- BEGIN SIDEBAR MENU -->         
         <%@include file="menu.jsp" %>
         <!-- END SIDEBAR MENU -->
      </div>
      <!-- END SIDEBAR -->
      <!-- BEGIN PAGE -->  
      <div class="page-content">
         <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
         <div id="portlet-config" class="modal hide">
            <div class="modal-header">
               <button data-dismiss="modal" class="close" type="button"></button>
               <h3>portlet Settings</h3>
            </div>
            <div class="modal-body">
               <p>Here will be a configuration form</p>
            </div>
         </div>
         <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
         <!-- BEGIN PAGE CONTAINER-->
         <div class="container-fluid">
            <!-- BEGIN PAGE HEADER-->   
            <div class="row-fluid">
               <div class="span12">
                  <!-- BEGIN STYLE CUSTOMIZER -->
               
                  <!-- END BEGIN STYLE CUSTOMIZER -->   
                  <h3 class="page-title" style="text-align: center;">
                    
<!--                    Internal System-->
                  </h3>
                  
                  
                  
                  
                  
                  <ul class="breadcrumb">
                     <li style="width: 900px;">
                        <i class="icon-home"></i>
                        <a href="#" style="margin-left:40%;">Upload PMTCT Cohort excel file.</a> 
                        <!--<span class="icon-angle-right"></span>-->
                     </li>
           
                  </ul>
               </div>
            </div>
            <!-- END PAGE HEADER-->
            <!-- BEGIN PAGE CONTENT-->
            <div class="row-fluid">
               <div class="span12">
                  <!-- BEGIN SAMPLE FORM PORTLET-->   
                  <div class="portlet box blue">
                     <div class="portlet-title">
                        <h4><i class="icon-reorder"></i></h4>
                       
                     </div>
                     <div class="portlet-body form">
                        <!-- BEGIN FORM-->
                        <form action="importpmtct" method="post" enctype="multipart/form-data" class="form-horizontal" >
                       
                            
                          
                            <input type="file" name="file_name" id="upload" value="" multiple="" class="textbox" required>   
                        <br><br><br><br>



                         
                           <div class="form-actions">
                              <button type="submit" class="btn blue">Upload MCA Excel.</button>

                           </div>
                        <div class="form-actions" id="matokeo">
                            
                        </div>
                        </form>
                        <!-- END FORM-->           
                     </div>
                  </div>
                  <!-- END SAMPLE FORM PORTLET-->
               </div>
            </div>
       
          
         
          
           
         
          
            <!-- END PAGE CONTENT-->         
         </div>
         <!-- END PAGE CONTAINER-->
      </div>
      <!-- END PAGE -->  
   </div>
   <!-- END CONTAINER -->
   <!-- BEGIN FOOTER -->
    <div class="footer">
       <%

              Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);       
%>
     <% dbConn conn= new dbConn(); %>  
     <h4 class="portlet-title" style="text-align: center;color:black;"> &copy; HSDSA | USAID <%=year%>. Host Name :<b><i> <%=conn.dbsetup[0]%></i></b> &nbsp;   Database Name :<i> <%=conn.dbsetup[1]%></i></h4>
      <div class="span pull-right">
         <span class="go-top"><i class="icon-angle-up"></i></span>
      </div>
   </div>
   <!-- END FOOTER -->
   <!-- BEGIN JAVASCRIPTS -->    
   <!-- Load javascripts at bottom, this will reduce page load time -->
   
<script src="assets/js/jquery-1.8.3.min.js"></script>
   

<script type="text/javascript" src="assets/js/bootstrap-notify.js"></script>


      
   
   
   <script src="assets/bootstrap/js/bootstrap.min.js"></script>   
   <script type="text/javascript" src="assets/bootstrap-fileupload/bootstrap-fileupload.js"></script>
   <script src="assets/js/jquery.blockui.js"></script>
   <!--[if lt IE 9]>
   <script src="assets/js/excanvas.js"></script>
   <script src="assets/js/respond.js"></script>
   <![endif]-->

   <script src="assets/js/app.js"></script>  
 
  
     

<script > 
                
</script>



                  
 <%if (session.getAttribute("uploadedpmtct") != null) { %>
                                <script type="text/javascript"> 
                    
                    
                    $("#matokeo").html('<%=session.getAttribute("uploadedpmtct")%>');
                         
      $.notify(
      {
  message:'<%=session.getAttribute("uploadedpmtct")%>'},
      {
	icon_type: 'image'
      }, 
      {
	offset: {
		x: 600,
		y: 300
	}
       }
       
            ); 
                    
                </script>
                
                <%
                //session.removeAttribute("uploadedpmtct");
                            }

                        %>


   
 <%if (session.getAttribute("resp1") != null) { %>
                                <script type="text/javascript"> 
                    
                    
                    
                         
      $.notify(
      {icon: "images/validated.jpg", 
  message:'<%=session.getAttribute("resp1")%>'},
      {
	icon_type: 'image'
      }, 
      {
	offset: {
		x: 600,
		y: 300
	}
       }
       
            ); 
    
    
     $.notify(
      {icon: "images/validated.jpg", 
  message:'<%=session.getAttribute("resp")%>'},
      {
	icon_type: 'image'
      }, 
      {
	offset: {
		x: 600,
		y: 300
	}
       }
       
            ); 
    
                    
                </script>
                
                <%
                session.removeAttribute("resp1");
                session.removeAttribute("resp");
                            }

                        %>
     

  
   
   <!-- END JAVASCRIPTS -->   
</body>
<!-- END BODY -->
</html>


