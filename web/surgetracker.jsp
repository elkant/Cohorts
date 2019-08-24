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
   <title>Surge Tracker</title>
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
   <link rel="stylesheet" href="css/select2.min.css">
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
            <h1 style="text-align:center;font-size: 50px;color:white;padding-bottom:16px ;font-weight: bolder;">Surge Tracker</h1><br/>
            
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
                        <a href="#" style="margin-left:40%;">Generate Tracker excel file.</a> 
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
                        <form action="surge_tracker" method="post" enctype="multipart/form-data" class="form-horizontal" >
                       
                             <table>
                            <tr class="col-xs-8">
                                 <td class="col-xs-4">
                            <div class="control-group">
                                   
                              <div class="controls">
                              <label ><b>Week Start date:</b><font color='red'><b>*</b></font></label>
                              </div>
                              </div>
                              </td>
                              <td class="col-xs-4">
                              <div class="controls">
                                  <input required type="text" title="this is the date that the week started" value="2019-05-13" class="form-control input-lg tarehe" name="weekstart" autocomplete="off" id="weekstart">
                              </div>
                           </td>
                           </tr>
                            <tr class="col-xs-8">
                      <td class="col-xs-4">
                            <div class="control-group">
                                   
                             <div class="controls">
                              <label ><b>Week End date:</b><font color='red'><b>*</b></font></label>
                              
                              </div> </div>
                      </td>
                      <td class="col-xs-4">
                              <div class="controls">
                                  <input required type="text" value='' title="this is the date that the week ended" value="<%if (session.getAttribute("weekend") != null) {out.println(session.getAttribute("weekend")); }%>" class="form-control input-lg tarehe" name="weekend" id="weekend" autocomplete="off">
                              </div>
                           </div>
                              </td>
                            </tr>
                           
 <tr class="col-xs-8">
     <td class="col-xs-4">
                                <div class="control-group">
                                   
                                    <div class="controls">
                                        <label><b>Select County</b><font color="green"><i><br/> (Optional)</i></font> </label>
                                        
                                        </div>
                                        </div>
</td>
                                       



<td class="col-xs-4">
                                <div class="control-group">
                                   
                                    <div class="controls">
                                        <select onchange='patasubcounty()' name='county' id='county' >
                                             <option value="">optional</option>
                                             <option value="Baringo">Baringo</option>
                                             <option value="Kajiado">Kajiado</option>
                                             <option value="Laikipia">Laikipia</option>
                                             <option value="Nakuru">Nakuru</option>
                                             <option value="Narok">Narok</option>                                           
                                             <option value="Samburu">Samburu</option>                                           
                                             <option value="Turkana">Turkana</option> 
                                  
                                  </select>
                                        
                                    </div>
                                </div>
                                        </td>



                                
                                         
                                        
</tr>

           
 <tr class="col-xs-8">
     
            <td class="col-xs-4">
                                <div class="control-group">
                                  
                                    <div class="controls">
                                        <label><b>Select Sub-county</b><font color="green"><i><br/> (Optional)</i></font></label> 
                                        
                                        </div>
                                        </div>
                                        </td>
                                
                                <td class="col-xs-4">
                                <div class="control-group">
                                   
                                    <div class="controls">
                                      <select multiple='true' onchange='patafacility();' name='subcounty' id='subcounty' >
                                      
                                             
                                      </select>
                                    </div>
                                </div>
                                        </td>
                                
    </tr>
    
               
 <tr class="col-xs-8">
     
            <td class="col-xs-4">
                                <div class="control-group">
                                  
                                    <div class="controls">
                                        <label><b>Select Facility</b><font color="green"> <br/><i>Optional </i></font></label> 
                                        
                                        </div>
                                        </div>
                                        </td>
                                
                                <td class="col-xs-4">
                                <div class="control-group">
                                   
                                    <div class="controls">
                                      <select multiple='true' name='facility' id='facility' >
                                      <option value="">Optional</option>
                                             
                                      </select>
                                    </div>
                                </div>
                                        </td>
                                
    </tr>
                               
                            
                          
                           
                  


</table>
                         
                          
                        <table style="width: 100%;">
                           <tr>
                              
                                   
                                   <td class="col-xs-8">
                           <div class="form-actions">
                            
                              
  <label id="generaterpt" class="btn green" onclick="getReport();">Generate report</label>
                          

                         
                           </div>
                                   </td>
                            </tr> 
                         </table>
                        <img src='images/ajax_loader.gif' alt='loading' style="display:none; margin-left:30% ;" class='loading'/>
                                        
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
       <%

              Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);       
%>
     <% dbConn conn= new dbConn(); %>  
     <h4 class="portlet-title" style="text-align: center;color:black;"> &copy; Afya Nyota Ya Bonde | USAID <%=year%>.<b><i> </i></b> &nbsp;   </i></h4>
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
   <script src="js/select2.min.js"></script>
  
     

<script > 
                
</script>

<script>
      
   
  
      
      $(".tarehe").datepicker({
    clearBtn: true,
    format: "yyyy-mm-dd",
    endDate: "now()"
}).on('changeDate', function(ev){
    $(this).datepicker('hide');
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
   $("#startdaterpt").focus();    
     } 
     
      else  if(Date.parse(exelstart) > Date.parse(exelend))
                {
                    alert(" Report Start date cannot be greater than end date.");   
                    $("#weekstart").focus();  
                }
                else {
                    //call the report generation page
                 downloadrpt(exelstart,exelend) ;  
                    
                }
        
    
}



  function downloadrpt(startdate,enddate){
      
      
       var county=$("#county").val();
    var subcounty=$("#subcounty").val();
    var facility=$("#facility").val();
      
      
                $('.loading').show();
                $('#generaterpt').hide();
                
                
                if(county===null){county="";}
                if(subcounty===null){subcounty="";}
                if(facility===null){facility="";}
                
               
                //?startdate=" + startdate + "&enddate=" + enddate + "&cbos=" + cbos
             
                var ur="surge_tracker?startdate=" + startdate + "&enddate=" + enddate+"&county="+county+"&subcounty="+subcounty+"&facility="+facility;
                console.log(ur);
                $.fileDownload(ur).done(function () { $('.loading').hide(); $('#generaterpt').show(); $('#generaterpt').html("<i class='glyphicon glyphicon-ok'></i> Report Generated"); }).fail(function () { alert('Report generation failed, kindly try again!'); $('.loading').hide(); $('#generaterpt').show(); });
 
                //$('.loading').hide();
            }


 function patasubcounty(){
           
           
           
        
        var county=document.getElementById("county").value;
        $.ajax({
            url:'getsubcounty?county='+county,
            type:'post',
            dataType:'html',
            success:function (data)
            {
                $("#subcounty").html(data.replace("<option value=''>Select sub-county</option>",""));
                var select = document.getElementById('subcounty');
                    select.size = select.length;
                
              //  App.init();   
            }
            
            
        });
      
    }

      
      
      
    
 function patafacility(){
           
           
           
        
        var subcounty=document.getElementById("subcounty").value;
        $.ajax({
            url:'getfacility?subcounty='+subcounty,
            type:'post',
            dataType:'html',
            success:function (data)
            {
                $("#facility").html(data.replace("<option value=''>Select facility</option>",""));
                var select = document.getElementById('facility');
                    select.size = select.length;
             $('#facility').select2();  
            }
            
            
        });
      
    }  
    
    
    function patafacilitieszote()
    {
        
       
        $.ajax({
            url:'getfacilityzote',
            type:'post',
            dataType:'html',
            success:function (data)
            {
                $("#facility").html(data.replace("<option value=''>Select facility</option>",""));
                var select = document.getElementById('facility');
                    select.size = select.length;
             $('#facility').select2();  
            }
            
            
        });
      
    }
    
      patafacilitieszote();
      
      
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
     

  
   
   <!-- END JAVASCRIPTS -->   
</body>
<!-- END BODY -->
</html>


