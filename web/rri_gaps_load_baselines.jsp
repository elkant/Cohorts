<%-- 
    Document   : loadTBExcel
    Created on : Jul 27, 2015, 2:41:29 PM
    Author     : Maureen
--%>





<%@page import="General.IdGenerator2"%>
<%@page import="db.dbConn"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<!-- BEGIN HEAD -->
<head>
   <meta charset="utf-8" />
   <title>Refresh aps</title>
   <link rel="shortcut icon" href="images/imis.png"/>
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
   <link rel="stylesheet" type="text/css" href="assets/gritter/css/jquery.gritter.css" />
   <link rel="stylesheet" type="text/css" href="assets/chosen-bootstrap/chosen/chosen.css" />
   <link rel="stylesheet" type="text/css" href="assets/jquery-tags-input/jquery.tagsinput.css" />
   <link rel="stylesheet" type="text/css" href="assets/clockface/css/clockface.css" />
   <link rel="stylesheet" type="text/css" href="assets/bootstrap-wysihtml5/bootstrap-wysihtml5.css" />
   <link rel="stylesheet" type="text/css" href="assets/bootstrap-datepicker/css/datepicker1.css" />
   <link rel="stylesheet" type="text/css" href="assets/bootstrap-timepicker/compiled/timepicker.css" />
   <link rel="stylesheet" type="text/css" href="assets/bootstrap-colorpicker/css/colorpicker.css" />
   <link rel="stylesheet" href="assets/bootstrap-toggle-buttons/static/stylesheets/bootstrap-toggle-buttons.css" />
   <link rel="stylesheet" href="assets/data-tables/DT_bootstrap.css" />
   <link rel="stylesheet" type="text/css" href="assets/bootstrap-daterangepicker/daterangepicker.css" />
   <link rel="stylesheet" type="text/css" href="assets/uniform/css/uniform.default.css" />
<link rel="stylesheet" href="select2/css/select2.css">
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
            <h2 style="text-align:center;font-size: 30px;color:white;padding-bottom:16px ;font-weight: bolder;">RRI Gaps Baseline Refresh</h2><br/>
            
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
                        <a href="#" style="margin-left:40%;">Send data to gaps Baseline table</a> 
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
                        <h4><i class="icon-reorder"></i><a class='btn btn-default' href='rri_gaps_main.jsp'>Data entry</a></h4>
                       
                     </div>
                     <div class="portlet-body form">
                        <!-- BEGIN FORM-->
                        <form action="importpns" method="post" enctype="multipart/form-data" class="form-horizontal" >
                       
                            <div class="control-group">
                              <label class="control-label">Baseline Start date:<font color='red'><b>*</b></font></label>
                              <div class="controls">
                                  <% IdGenerator2 ig = new IdGenerator2(); %>
                                  <input required type="text" title="this is the date that the baseline started" value="<%=ig.LastMonthStartDate()%>" class="form-control input-lg tarehe" name="sd" id="sd" autocomplete="off">
                              </div>
                           </div>
                        
                         <div class="control-group">
                              <label class="control-label">Baseline End date:<font color='red'><b>*</b></font></label>
                              <div class="controls">
                                
                                  <input required type="text" title="this is the date that the baseline ended" value="<%=ig.LastMonthEndDate()%>" class="form-control input-lg tarehe" name="ed" id="ed" autocomplete="off">
                              </div>
                           </div>
                              
                              <div class="control-group">
                              <label class="control-label">Reporting End date:<font color='red'><b>*</b></font></label>
                              <div class="controls">
                                
                                  <input required type="text" title="this is the date that the report will be monitored under" value="<%=ig.LastMonthEndDate()%>" class="form-control input-lg tarehe" name="rd" id="rd" autocomplete="off">
                              </div>
                           </div>
                            
                              
                              <div class="control-group" >
                              <label class="control-label">Specify Output:<font color='red'><b>*</b></font></label>
                              <div class="controls">
                                  <select required type="text" title="" onchange='selectoutput();'  class="form-control input-lg" name="outiput" id="outiput" >
                                      
                                     
                                       <option value='internal_system.sp_rri_gaps_baselines_txnew'>1. Refresh TX_NEW gaps</option>
                                       <option value='internal_system.sp_rri_gaps_pns_untested_contacts'>2. Refresh Index Contacts Not Tested</option>
                                       <option value='internal_system.sp_rri_gaps_cxca_unlinked'>3. Refresh CXCA</option>                                       
                                       <option value='internal_system.sp_rri_gaps_baselines_netloss'>4. Refresh NetLoss gaps</option>
                                       <option value='internal_system.sp_rri_gaps_stfs'>5. Refresh STFs</option>
                                      
                                             
                                      </select>
                              </div>
                           </div>
                              
                              
                              
                            
                              
                            
<!--                             <div class="control-group">
                              <label class="control-label">Excel file<font color='red'><b>*</b></font></label>
                              <div class="controls">
                                  <input required type="file" name="file_name" id="upload" value="" class="textbox" required>  
                              </div>
                           </div>-->
                          
                           
                        <br><br><br><br>



                         
                          
                        <table style="width: 100%;">
                           <tr>
<!--                               <td class="col-xs-2">
                            <div class="form-actions">
                              <button type="submit" class="btn blue">Generate Report Excel.</button>

                           </div>
                                   </td>-->
                                   
                                   <td class="col-xs-10">
                           <div class="form-actions">
                             
                         
                              
  <label id="generaterpt" class="btn green" onclick="getReport();">Refresh</label>
                          

                         
                           </div>
                                   </td>
                            </tr> 
                         </table>
                        <img src='images/ajax_loader.gif' alt='loading' style="display:none; margin-left:30% ;" class='loading'/>
                        <div id='ujumbe'></div>
                        <div class="form-actions" id="matokeo">
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
    
     
     <h4 class="portlet-title" style="text-align: center;color:black;"> &copy; USAID Tujenge Jamii | USAID  Host Name :<b><i> </i></b> &nbsp;   Database Name :<i> </i></h4>
      <div class="span pull-right">
         <span class="go-top"><i class="icon-angle-up"></i></span>
      </div>
   </div>
   <!-- END FOOTER -->
   <!-- BEGIN JAVASCRIPTS -->    
   <!-- Load javascripts at bottom, this will reduce page load time -->
   
<script src="assets/js/jquery-1.8.3.min.js"></script>
   

<script type="text/javascript" src="js/bootstrap-notify.js"></script>

 <script type="text/javascript" src="js/jquery.fileDownload.js"></script>
      
   
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
  
     

<script > 
                
</script>

<script>
      
   
  
      
      $(".tarehe").datepicker({
    clearBtn: true,format: "yyyy-mm-dd"
}).on('changeDate', function(ev){
    $(this).datepicker('hide');
});
      
      
      
     
function getReport()
{
    
    
 
   var sd=$("#sd").val();
   var ed=$("#ed").val();
   var rd=$("#rd").val();
  
        
         
   //end date
       if (sd==='')
     {
         
     alert('Select report start date');
   $("#sd").focus();    
     } 
     
      else   if (ed==='')
     {
         
     alert('Select report end date');
   $("#ed").focus();    
     } 
          else   if (rd==='')
     {
         
     alert('Select reporting date');
   $("#rd").focus();    
     }          
                  
                
                else {
                    //call the report generation page
                 downloadrpt(sd,ed,rd) ;  
                    
                }
        
    
}



  function downloadrpt(sd,ed,rd){
      
                $('.loading').show();
                $('#generaterpt').hide();
               var urel=$("#outiput").val();
                //?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos
             
                var ur="refresh_rri_baselines?sp="+urel+"&sd=" + sd+"&ed=" + ed+"&rd=" + rd;
 console.log(ur);
 
 $.ajax({
     url: ur,
     dataType: 'html',
     data: '',
     success: function (data) {
         $('.loading').hide(); 
         $('#generaterpt').show();
         $("#ujumbe").html(data);
         //$('#generaterpt').html("<i class='glyphicon glyphicon-ok'></i> Report Generated");
                        
                    }
 })
 
                //$.fileDownload(ur).done(function () { $('.loading').hide(); $('#generaterpt').show(); $('#generaterpt').html("<i class='glyphicon glyphicon-ok'></i> Report Generated"); }).fail(function () { alert('Report generation failed, kindly try again!'); $('.loading').hide(); $('#generaterpt').show(); });
 
                //$('.loading').hide();
            }




function selectoutput(){
    
    
    var outputii=$("#outiput").val();
    
   
    
}
selectoutput();
      
   </script>

                  
 

     

  
   
   <!-- END JAVASCRIPTS -->   
</body>
<!-- END BODY -->
</html>


