
<%@page import="db.dbConn"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- BEGIN HEAD -->
<head>
   <meta charset="utf-8" />
   <title>Upload Daily Triangulation</title>
   <link rel="shortcut icon" href="images/logo.png"/>
   <meta content="width=device-width, initial-scale=1.0" name="viewport" />
   <meta content="" name="description" />
   <meta content="" name="author" />
   <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
   
   <link href="assets/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" />
   <link href="assets/bootstrap-fileupload/bootstrap-fileupload.css" rel="stylesheet" />
   <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
   <link href="assets/css/style.css" rel="stylesheet" />
   <link href="assets/css/style_responsive.css" rel="stylesheet" />
   <link href="assets/css/style_default.css" rel="stylesheet" id="style_color" />
  
   <link rel="stylesheet" type="text/css" href="assets/bootstrap-datepicker/css/datepicker.css" />
  
   <link rel="stylesheet" href="assets/data-tables/DT_bootstrap.css" />
   
     <link rel="stylesheet" href="css/progress_bar.css">
<link rel="stylesheet" href="css/animate.css">



                
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
            <h1 style="text-align:center;font-size: 50px;color:white;padding-bottom:16px ;font-weight: bolder;">Daily Triangulation Form</h1><br/>
            
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
                    <a class='btn-warning btn' href="pns/daily_data_triangulation_template_v100.xlsx" style="margin-left:40%;">Download Daily Triangulation Template</a> 
<!--                    Internal System-->
                  </h3>
                 
                  
                  <ul class="breadcrumb">
                     <li style="width: 900px;">
                        <i class="icon-upload"></i>
                        <a href="#" style="margin-left:40%;">Upload Daily Triangulation Data.</a> 
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
                        <h4><i class="icon-reorder"></i> Ensure that the Data Triangulation excel template name ends with <b>.xlsx</b> and that all sheets with data have MFLCode</h4>
                       
                     </div>
                     <div class="portlet-body form">
                        <!-- BEGIN FORM-->
                        <form action="importdailyTriangulation" method="post" enctype="multipart/form-data" class="form-horizontal" >
                       
                             <div  class="portlet-body form" id="progress_area" hidden="true">
                     <div class="progress"  style="height: 35px;">
                         <div class="progress-bar progress-bar-striped active" id="progess" role="progressbar" style="width: 0%;  padding-top: 10px; font-weight: 900;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                      </div>   
                  </div> 
                            
                            <div class="control-group">
                              <label class="control-label"><b>Select Date</b><font color='red'><b>*</b></font></label>
                              <div class="controls">
                                  <input readonly required type="text" title="This is the date the data is being uploaded" value="<%if (session.getAttribute("weekstart") != null) {out.println(session.getAttribute("weekstart")); }%>" class="form-control input-lg tarehe" name="weekstart" autocomplete="off" id="weekstart">
                              </div>
                           </div>
                            
                            
                           <div class="control-group" style="display:none;">
                              <label class="control-label"><b>Week End date:</b> <br/><i>(Auto filled)</i>:<font color='red'><b>*</b></font></label>
                              <div class="controls">
                                  <input readonly  required type="text" title="this is the date that the week ended" value="<%if (session.getAttribute("weekend") != null) {out.println(session.getAttribute("weekend")); }%>" class="form-control input-lg" name="weekend" id="weekend" autocomplete="off">
                              </div>
                           </div>
                            
                            
                             <div class="control-group">
                              <label class="control-label"><b>Select Excel file(s) To Upload</b><font color='red'><b>*</b></font><br/><i>(Allows Multiple files)</i></label>
                              <div class="controls">
                                  <input accept=".xlsx" required type="file" name="file_name" multiple="true" id="upload" value="" class="textbox" required>  
                              </div>
                             </div>
                          
                           
                        <br><br><br><br>



                         
                          
                        <table style="width: 100%;">
                           <tr><td class="col-xs-2">
                            <div class="form-actions">
                              <button id="uploadbutton" class='btn btn-success btn-large' type="submit" class="btn blue">Upload File(s)</button>

                           </div>
                                   </td>
                                   
                                   <td class="col-xs-10">
                           <div class="form-actions">
                             
                         
                              
  <!--<label id="generaterpt" class="btn green" onclick="getReport();">Generate report</label>-->
                          

                         
                           </div>
                                   </td>
                            </tr> 
                         </table>
                        <img src='images/ajax_loader.gif' alt='loading' style="display:none; margin-left:30% ;" class='loading'/>
                                        
                        <div style="font-size:16px;" class="form-actions" id="matokeo">
                        <div class="form-actions">
                            
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
     <h4 class="portlet-title" style="text-align: center;color:black;"> &copy;USAID Tujenge Jamii <%=year%>. Host Name :<b><i> <%=conn.dbsetup[0]%></i></b> &nbsp;   Database Name :<i> <%=conn.dbsetup[1]%></i></h4>
      <div class="span pull-right">
         <span class="go-top"><i class="icon-angle-up"></i></span>
      </div>
   </div>
   <!-- END FOOTER -->
   <!-- BEGIN JAVASCRIPTS -->    
   <!-- Load javascripts at bottom, this will reduce page load time -->
   
<script src="assets/js/jquery-1.8.3.min.js"></script>


 <script type="text/javascript" src="js/jquery.fileDownload.js"></script>
      
      
   <script src="assets/bootstrap/js/bootstrap.min.js"></script>   
  
   <!-- ie8 fixes -->
   <!--[if lt IE 9]>
   <script src="assets/js/excanvas.js"></script>
   <script src="assets/js/respond.js"></script>
   <![endif]-->
  

   <script type="text/javascript" src="assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
  
   <script src="assets/js/app.js"></script>  

  
     

<script > 
                
</script>

<script>
      
      
      function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();
    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
}
      
   function addDays(date, days) {
  var result = new Date(date);
  result.setDate(result.getDate() + days);
  return formatDate(result);
}
  
//      daysOfWeekDisabled: "0,1,2,3,4,6",
      $(".tarehe").datepicker({
    startDate: "2019-05-13",
    endDate: "now()",
    clearBtn: true,
    format: "yyyy-mm-dd"
}).on('changeDate', function(ev){
    $(this).datepicker('hide');
    var mk=$("#weekstart").val();
//    var mk=addDays($("#weekstart").val(),6);
    //alert(mk);
    $("#weekend").val(mk);
});
      
      
      
     
function getReport(){
    
    
    var exelstart=$("#weekstart").val();
    var exelend=$("#weekend").val();
  
        
        if (exelstart==='')
     {
         
     alert('Select report begining date');
   $("#startdaterpt").focus();    
     }    
   //end date
      else if (exelend==='')
     {
         
     alert('Select report ending date');
   $("#enddaterpt").focus();    
     } 
     
      else  if(Date.parse(exelstart) > Date.parse(exelend)){
                    alert(" Report Start date cannot be greater than end date.");   
                    $("#enddaterpt").focus();  
                }
                else {
                    //call the report generation page
                 downloadrpt(exelstart,exelend) ;  
                    
                }
        
    
}



  function downloadrpt(startdate,enddate){
      
                $('.loading').show();
                $('#generaterpt').hide();
               
                //?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos
             
                var ur="pnsreports?startdate=" + startdate + "&enddate=" + enddate;
 console.log(ur);
                $.fileDownload(ur).done(function () { $('.loading').hide(); $('#generaterpt').show(); $('#generaterpt').html("<i class='glyphicon glyphicon-ok'></i> Report Generated"); }).fail(function () { alert('Report generation failed, kindly try again!'); $('.loading').hide(); $('#generaterpt').show(); });
 
                //$('.loading').hide();
            }

      
   </script>

                  
 <%if (session.getAttribute("uploadedpns") != null) { %>
                                <script type="text/javascript"> 
                    
                    
$("#matokeo").html('<%=session.getAttribute("uploadedpns")%>');
                         
      $.notify(
      {
  message:'<%=session.getAttribute("uploadedpns")%>'},
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
                //session.removeAttribute("uploadedart");
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
     

  
<script > 
     $(document).ready(function(){
        $("#progress_area").hide();
        $("#upload_area").show();
         
    $("form").submit(function(){
        $("#progress_area").show();
        $("#upload_area").hide();
        $("#uploadbutton").hide();
//        alert("data submitted");
     setInterval(function() {
      load_records();
      }, 100);  
    });
     });
     
     function load_records(){
         
         
             $.ajax({
        url:'checkstatus?load_type=dailytriangulation',
        type:"post",
        dataType:"json",
        success:function(response){
//            alert("called");
var per_value = response.count;
var message = "["+per_value+"%] Complete "+response.message+" Records Uploaded";

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
    if(per_value>=90){
     $("#progess").addClass('progress-bar-success'); 
     $("#progess").removeClass('progress-bar-info'); 
     $("#progess").removeClass('progress-bar-warning');   
     $("#progess").removeClass('progress-bar-danger');  
    }
    if(per_value=100){
     $("#progess").addClass('progress-bar-default'); 
     $("#progess").removeClass('progress-bar-success'); 
     $("#progess").removeClass('progress-bar-info'); 
     $("#progess").removeClass('progress-bar-warning');   
     $("#progess").removeClass('progress-bar-danger');  
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
   
 <%if (session.getAttribute("uploadeddt") != null) { %>
   <script type="text/javascript"> 
                    
       
  $("#matokeo").html('<%=session.getAttribute("uploadeddt")%>');
      
                    
                </script>
                
                <%
//                session.removeAttribute("uploadedart");
                            }

                        %>
     

   <script>
   </script>
   
   <!-- END JAVASCRIPTS -->   
</body>
<!-- END BODY -->
</html>


