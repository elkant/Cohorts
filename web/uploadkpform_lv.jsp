


<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<!-- BEGIN HEAD -->
<head>
   <meta charset="utf-8" />
   <title>Upload KP F1A</title>
     <link rel="shortcut icon" href="Login_v6/images/kp.png"/>
   <meta content="width=device-width, initial-scale=1.0" name="viewport" />
   <meta content="" name="description" />
   <meta content="" name="author" />
   <link rel="stylesheet" href="css/progress_bar.css">
   <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
   <link href="assets/css/metro.css" rel="stylesheet" />
   <link href="assets/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" />
   <link href="assets/bootstrap-fileupload/bootstrap-fileupload.css" rel="stylesheet" />
   
 
   <link rel="stylesheet" href="assets/data-tables/DT_bootstrap.css" />
 

<style>
#notify {
  position: relative;
  /*text-transform: uppercase;*/
  letter-spacing: 2px;
  font-weight: 900;
  text-decoration: none;
  color: red;
  font-size: 23px;
  display: inline-block;
}
    </style>
    
    <style>
                    
                    [data-notify="progressbar"] {
	margin-bottom: 0px;
	position: absolute;
	bottom: 0px;
	left: 0px;
	width: 100%;
	height: 5px;
}
       div.scrollmenu {
    overflow: auto;
    white-space: nowrap;
}  
tr>td {
  padding-bottom: 1em;
  padding-right: 3em;
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
            <h1 style="text-align:center;font-size: 50px;color:white;padding-bottom:16px ;font-weight: bolder;">KP Form</h1><br/>
            
            <!-- END LOGO -->
            <!-- BEGIN RESPONSIVE MENU TOGGLER -->
            <a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
            <img src="assets/img/menu-toggler.png" alt="" />
            </a>          
            <!-- END RESPONSIVE MENU TOGGLER -->            
            <!-- BEGIN TOP NAVIGATION MENU -->              
            <ul class="nav pull-right">
              
               <!-- END NOTIFICATION DROPDOWN -->
               <!-- BEGIN INBOX DROPDOWN -->
             
             
               <!-- END TODO DROPDOWN -->
               <!-- BEGIN USER LOGIN DROPDOWN -->
     
               
               <!-- END USER LOGIN DROPDOWN -->
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
                     <li>
                        <i class="icon-home"></i>
                        <a href="#" style="margin-left:40%;"></a> 
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
                        <h4><i class="icon-reorder"></i>  UPLOAD KP Form 1A V 1.0.1 (.xlsx)</h4>
                     </div>
                      
                  <div  class="portlet-body form" id="progress_area" hidden="true">
                     <div class="progress"  style="height: 35px;">
                     <div class="progress-bar progress-bar-striped active" id="progess" role="progressbar" style="width: 0%;  padding-top: 10px; font-weight: 900;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                     </div>   
                  </div>
                     <div id="upload_area" class="portlet-body form">
                        <!-- BEGIN FORM-->
                        <form action="uploadf1a" id="formActions" method="post" enctype="multipart/form-data" class="form-horizontal">
                        <!--<form action="fas_trial" id="formActions" method="post" enctype="multipart/form-data" class="form-horizontal">-->
                          
                        <div class="control-group " ></div>
                           <div  class="control-group col-lg-12" >
                              <label class="control-label col-lg-6">Select Filled Form1A excel file<font color='red'><b>*</b></font></label>
                              <div class="controls col-lg-6">
                                 <input onMouseOver='checksession();' accept=".xlsx" required type="file" name="file_name" multiple="true" id="upload" value="" class="textbox" required> 
                              </div>
                           </div>  
                           <div class="control-group" style="display:none;">
                              <label class="control-label">Skip Errors <font color='red'><b>?</b></font></label>
                              <div class="controls">
                                  <input value="1" data-placeholder="Reporting Month" type="checkbox" class="span6 m-wrap" tabindex="-1"  id="generateoutput" name="generateoutput" style="width: 400px;"/>
                                    
                              </div>
                           </div>  
                            
                           <div class="form-actions">
                              <button type="submit" id="run_upload" class="btn blue">Upload</button>
<!--                              <button type="button" class="btn">Cancel</button>-->
<h5 id="ujumbe" style="color:green;margin-left: 160px;font-family: sans-serif;font">Note:Ensure by the time of uploading, you have corrected all the displayed errors in the excel template. 
    <br/><b>Excel file(s) with Errors will not go through the upload process </b>.</h5>
                           </div>
                        </form>
                        <!-- END FORM-->  
                      </div>
                  </div>
                  <!-- END SAMPLE FORM PORTLET-->
                  <div id="table_output">
                      <div>
                          <div style="font-weight: bolder; color: red;" id="message">
                              <% if(session.getAttribute("message")!=null){
                              out.println(session.getAttribute("message").toString());
                              session.removeAttribute("message");
                              }%>
                              </div>
                          <% if(session.getAttribute("warnings")!=null){
                              if(!session.getAttribute("warnings").toString().equals("")){%>
                          <br>
                          <div>
                              <div style="text-align: center; font-size: 30px; font-family: bolder; text-decoration: underline;">Early Warning Indicators: Data Quality Issues</div>
                      <div style="text-align: right;">
                          <button id="generate_output" class="btn-info btn-sm" style="background: "><b>Convert to Excel(.xls)</b></button>
                      </div>
                      <table id="table_warning" class="table table-striped table-bordered table-advance table-hover">
                          <thead> <tr> <th>County</th><th>Sub County</th><th>Health Facility</th><th>MFL Code</th><th>Calendar Year</th><th>Month</th><th>Year-Month</th><th>Program</th><th>Message</th><th>Age Group</th></tr></thead>
                      <tbody id="warnings_details">
               <%
               out.println(session.getAttribute("warnings").toString());
               session.removeAttribute("warnings");
                   %>
                    
                      </tbody>
                </table>
               </div>
                   <%}
}%>
                   </div>
                  </div>
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
       
       &copy; Usaid Tujenge Jamii | USAID <%=year%>.
      <div class="span pull-right">
         <span class="go-top"><i class="icon-angle-up"></i></span>
      </div>
   </div>
   <!-- END FOOTER -->
   <!-- BEGIN JAVASCRIPTS -->    
   <!-- Load javascripts at bottom, this will reduce page load time -->
   <script src="assets/js/jquery-1.8.3.min.js"></script>    
   <script type="text/javascript" src="assets/ckeditor/ckeditor.js"></script>  
   <script src="assets/breakpoints/breakpoints.js"></script>       
   <script src="assets/bootstrap/js/bootstrap.min.js"></script>   
   <script type="text/javascript" src="assets/bootstrap-fileupload/bootstrap-fileupload.js"></script>
   <script src="assets/js/jquery.blockui.js"></script>
   <script src="assets/js/jquery.cookie.js"></script>
   <!-- ie8 fixes -->
   <!--[if lt IE 9]>
   <script src="assets/js/excanvas.js"></script>
   <script src="assets/js/respond.js"></script>
   <![endif]-->
   <script type="text/javascript" src="assets/chosen-bootstrap/chosen/chosen.jquery.min.js"></script>
   <script type="text/javascript" src="assets/uniform/jquery.uniform.min.js"></script>
   <script type="text/javascript" src="assets/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script> 
   <script type="text/javascript" src="assets/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
   <script type="text/javascript" src="assets/jquery-tags-input/jquery.tagsinput.min.js"></script>
   <script type="text/javascript" src="assets/bootstrap-toggle-buttons/static/js/jquery.toggle.buttons.js"></script>
   <script type="text/javascript" src="assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
   <script type="text/javascript" src="assets/clockface/js/clockface.js"></script>
   <script type="text/javascript" src="assets/bootstrap-daterangepicker/date.js"></script>
   <script type="text/javascript" src="assets/bootstrap-daterangepicker/daterangepicker.js"></script> 
   <script type="text/javascript" src="assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>  
   <script type="text/javascript" src="assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
   <script src="assets/js/app.js"></script>  
   <script src="select2/js/select2.js"></script>
  
   <script type="text/javascript" src="js/jquery.fileDownload.js"></script>

   
  
   <script>
  
      
   
      
      function checksession(){
          
                     $.ajax({
url:'IsSessionActive',
type:'post',
dataType:'html',
success:function (data){
console.log(""+data);   

if(data.trim()==='No Active session'){
    //log user out
  window.location='kp_index.jsp';  
    
}

    
       //document.getElementById("month").innerHTML=data;
      // App.init();  
        
}


});   
          
          
          
      }
      
   </script>
   
   
<script > 
     $(document).ready(function(){
        $("#progress_area").hide();
        $("#upload_area").show();
         
    $("form").submit(function(){
        $("#progress_area").show();
        $("#upload_area").hide();
//        alert("data submitted");
     setInterval(function() {
      load_records();
      }, 100);  
    });
     });
      $("#ujumbe").html("Note:Ensure by the time of uploading, you have corrected all the displayed errors in the excel template.");
     function load_records()
     {
             $.ajax({
        url:'check_status?load_type=form1a',
        type:"post",
        dataType:"json",
        success:function(response){
//            alert("called");
var per_value = response.count;
var message = "["+per_value+"%] "+response.message+"";

    $("#progess").html(message);
    $("#progess").css({'width':per_value+"%"}); 

    if(per_value<30){
     $("#progess").addClass('progress-bar-danger');  
     $("#progess").removeClass('progress-bar-success'); 
    }
    if(per_value>=30 && per_value<60){
     $("#progess").addClass('progress-bar-warning');   
     $("#progess").removeClass('progress-bar-danger');   
    }
    if(per_value>=60 && per_value<80){
     $("#progess").addClass('progress-bar-info'); 
     $("#progess").removeClass('progress-bar-warning');   
     $("#progess").removeClass('progress-bar-danger');  
    }
    if(per_value>=90)
    {
     $("#progess").addClass('progress-bar-success'); 
     $("#progess").removeClass('progress-bar-info'); 
     $("#progess").removeClass('progress-bar-warning');   
     $("#progess").removeClass('progress-bar-danger');  
    }
   
    
    if(response.refreshpage==='yes')
    {
      $("#upload_area").show(); 
      $("#progress_area").hide();
       
      $("#ujumbe").html("<font color='red'>Form Completed with a Validation Error. Check the errors sheet on the Data Quality Download</font>");
      $("#progess").html("Form Completed with a Validation Error. Check the errors sheet on the Data Quality Download");
      $("#message").html("Form Upload Completed with a Validation Error. Check the errors sheet on the Data Quality Download");
      $("#progess").css({'width':"99%"});
      $("#progess").addClass('progress-bar-danger');  
     //$("#progess").removeClass('progress-bar-success');        
    }
    
    $("#status").html(response);
    
    
        }, 
        error: function(jqXHR, textStatus, errorThrown) {
       //error in loading upload status
       $("#status").html(errorThrown);
            }
  });
     }
</script>
 
     


   
   <!-- END JAVASCRIPTS -->   
</body>
<!-- END BODY -->
</html>

