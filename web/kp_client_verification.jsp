<%-- 
    Document   : index
    Created on : Mar 17, 2016, 3:17:19 PM
    Author     : Emmanuel E
--%>

<%@page import="General.IdGenerator2"%>
<%@page import="hfr.getIndicators"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.sql.SQLException"%>
<%@page import="org.json.JSONArray"%>
<%@page import="db.dbConn"%>
<%@page import="hfr.getReportingDates"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>KP Clients Verification</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
                <!--<link href="css/dataTables.bootstrap.min.css" rel="stylesheet">-->
		<link href="css/bootstrap.css" rel="stylesheet">
                <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
                <link rel="stylesheet" href="css/select2.min.css">
                <link rel="shortcut icon" href="images/lab_manifest.png">
                <!--<link data-jsfiddle="common" rel="stylesheet" media="screen" href="css/handsontable.css">-->
<!--  <link data-jsfiddle="common" rel="stylesheet" media="screen" href="dist/pikaday/pikaday.css">-->
                  <link href="assets/css/dataTables.bootstrap.min.css" rel="stylesheet">
          <link href="assets/css/responsive.bootstrap.min.css" rel="stylesheet">
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="css/styles.css" rel="stylesheet">
		<link href="https://cdn.datatables.net/fixedheader/3.3.2/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
                
                                                <style type='text/css'>
input:focus {
    border-color: red;
    }
    .control-group .select2-container {
  position: relative;
  z-index: 2;
  float: left;
  width: 100%;
  margin-bottom: 0;
  display: table;
  table-layout: fixed;
}


@media screen and (min-width: 600px) and (max-width: 1199px)  {
  #weeklydataform {
    margin-left:15%;
    margin-right:15%;
  }
}

@media screen and (min-width: 1200px) {
  #weeklydataform {
    margin-left:10%;
    margin-right:10%;
  }
}


</style>
                
	</head>
	<body >
<!-- header -->
<div id="top-nav" class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            
        </div>
        <div class="navbar-collapse collapse">
                       
<%  dbConn conn = new dbConn();

String frm="";
String selectedform="kp_validation";
if(request.getParameter("frm")!=null){

    selectedform=request.getParameter("frm");
    
}

String getfrms="select distinct(Form) as fm, formname as fmn from internal_system.kp_client_indicators where Form='"+selectedform+"' ; ";

conn.rs=conn.st.executeQuery(getfrms);
while (conn.rs.next())
{
    String sele="";
    if(selectedform.equals(conn.rs.getString(1))){sele="selected";}

frm+="<option "+sele+" value='"+conn.rs.getString(1)+"' >"+conn.rs.getString(2)+"</option>";
    
}



if(conn.rs!=null){conn.rs.close();}
if(conn.st!=null){conn.st.close();}





%>
            
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
              
                    <ul id="g-account-menu" class="dropdown-menu" role="menu">
                        <li><a href="#">My Profile</a></li>
                    </ul>
                </li>
          
                
             
                 <li>
                  <a  title="Help" data-toggle="modal" href="#help">
                            <i class="glyphicon glyphicon-question-sign"></i>
                            Help
                        </a></li>
                              <li><a style="text-align: center;" href='kp_home.jsp'><i class="glyphicon glyphicon-home"></i>Home</a></li>
                              <li><a style="text-align: center;" href='kp_index.jsp'><i class="glyphicon glyphicon-log-out"></i> Logout</a></li>
            </ul>
        </div>
        
    </div>
    <!-- /container -->
    
</div>
<!-- /Header -->

<!-- Main -->
<div class="container-fluid">
    

    <div class="row">
        
        <!-- /col-3 -->
        <div class="col-sm-12">            
            
            <div class="col-sm-4">
            <a href='kp_client_verification.jsp?frm=kp_validation'><label class="btn btn-primary"><i class="glyphicon glyphicon-user"></i> Validate Clients Form</label></a>
            </div>
             <div class="col-sm-4">
            <a href='kp_client_verification.jsp?frm=kp_outreach_workers'><label class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>Outreach Worker</label></a>
            </div>
             <div class="col-sm-4">
            <a href='kp_client_verification.jsp?frm=kp_peer_educators'><label class="btn btn-primary"><i class="glyphicon glyphicon-plus"></i>Contact Person</label></a>
            </div>
        </div>
          <div class="row">
          <h5 class="btn-default col-md-12" style="text-align: center;color:blue;"><b>KP Case-Based Forms</b></h5>

            <div class="row">
                <!-- center left-->
                <div style="padding-left:10%;padding-right:10%;" class="col-md-12">
                    

                  

                    <div class="btn-group btn-group-justified">
                      
                        
                         
<!--                            
                            <a  class="btn btn-danger col-sm-3" id="exportdataanchor1" style="display:none;" title="Add Widget" data-toggle="modal" href="#addWidgetModal">
                                <i class="glyphicon glyphicon-cloud-upload"></i>
                                <br/>Export Data 
                                <span id="unexportedno" style="color:yellow;">(0 site )</span>
                            </a>
                            
                        
                        <a href="#" class="btn btn-primary col-sm-3">
                            <i class="glyphicon glyphicon-cog"></i>
                            <br> Settings
                        </a>
                        <a class="btn btn-info col-sm-4" title="Help" data-toggle="modal" href="#help">
                            <i class="glyphicon glyphicon-question-sign"></i>
                            <br> Help
                        </a>
                        <a class="btn btn-success col-sm-4" title="Reports"  href="hfrreports.jsp">
                            <i class="glyphicon glyphicon-question-sign"></i>
                            <br> Reports
                        </a>
                        -->
                    </div>

                    <hr>

<table class='table table-striped table-bordered'  style=" width:100%;border :3px solid #4b8df8;" >

                                                        


                                                        





                      <!-------Data Management ---------->

                      
                      
                                                        <tr>

                                                            
                                                            <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>LIP</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            
                                                            <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Reporting Month</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Facility</label> 

                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>


                                                        <tr >
                                                             <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <select style="display:none;" required="true"   onchange="getFacilitiesJson();isdisplayindicators();"   name="frmname" id="frmname" class="form-control" >
                                                                     <%=frm%>                                              
                                                                        </select>
                                                                        
                                                                        
                                                                        <select required="true"   onchange=""   name="lip" id="lip" class="form-control" >
                                                                            <%

                                                                                if (session.getAttribute("liplist") != null) {

                                                                                    out.println(session.getAttribute("liplist").toString());
                                                                                } else {
                                                                                    out.println("<option value=''>login to select LIP</option>");
                                                                                }
                                                                            %>                                          
                                                                        </select>
                                                                        
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>

                                                            <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        
                                                                      
                                                                        
                                                                        <select required="true"   onchange="getDicesJson();isdisplayindicators();"   name="period" id="period" class="form-control" >
                                                                                                                   
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </td>


                                                            <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                   
                                                                        
                                                                          <select required="true"  onchange="isdisplayindicators();"   name="facility" id="facility" class="form-control" >
                                                                            <option>Select DIC Name</option>

                                                                        </select>
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>




                                                    </table>
                   

                    <!--tabs-->
                    <div class="panel">
                        <ul class="nav nav-tabs " id="myTab">
                            <li class="active newdata"> <a href="#add<%=selectedform%>" id="add<%=selectedform%>button" data-toggle="tab">  <i class="glyphicon glyphicon-plus"> </i> <font color='green'><b>Data </b></font>  entry</a></li>                          
                           
                            <li class="newdata"> <a href="#edit<%=selectedform%>form" id="edit<%=selectedform%>button" data-toggle="tab">  <i class="glyphicon glyphicon-edit"> </i> <font color='green'><b>Edit</b></font>Records</a></li>
                           
                            <li><a href="#reports" style="" id="reportsbutton" data-toggle="tab"> <i class="glyphicon glyphicon-stats"></i>Reports</a></li> 
                            <!--<li><a href="#searchdata" data-toggle="tab"> <i class="glyphicon glyphicon-search"></i> Edit Data</a></li>--> 
                           <!-- <li><a href="#export" data-toggle="tab"> <i class="glyphicon glyphicon-cloud-upload"></i> Data Export</a></li>-->
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active well col-md-12" id="add<%=selectedform%>">
                                
                                
                              <!--Data entry code-->
                    <div class="panel panel-default">
                       
                        <div class="panel-body" style="width:100%;">
                            <form class="form form-vertical"  action="#" method="post" id="weeklydataform">
                              <table class='table table-striped table-bordered'  style=" width:100%;border :3px solid #4b8df8;" >




                      <!-------Data Management ---------->


                                                    </table>
                                
                                <!--<form id="addmothersform">-->
                                
                                         <table class='table table-striped table-bordered' id="dynamicindicators<%=selectedform%>" style="display:none;border :3px solid #4b8df8;padding:1px;" > 
                                   
                                <!------INDICATORS----->
                                           <tr><td><h3>Select Reporting Year, Month and Facility to Load data for.</h3></td></tr>                    
                                 
                                  
                                     </table>
                                <table class="table table-striped table-bordered">
                                       <tr><td colspan="3" class="col-xs-12">               
                                <div class="control-group col-xs-12">
                                        
                                   <br/>
                                    <div style="display:none;" class="controls savebuttons">
                                        <input type="input" onClick="loadClinicalValidation('loadClinicalValidation','<%=selectedform%>','kp_client_indicators');"  id='savebutton' value="Save Record"  style="margin-left: 0%;" class="btn-sm btn-success active">
                                            
                                     </div>
                                     <div class="controls">
                                        <button type="submit" id='updatebutton' onclick="loadClinicalValidation('loadClinicalValidation','<%=selectedform%>','kp_client_indicators');" style="margin-left: 0%;display:none;" class="btn-sm btn-info active">
                                            Update Record 
                                        </button>
                                    </div>                                   
                                    
                                </div>
                                        </td></tr>
                                        
                                </table>
                                
                                <div class="control-group col-xs-12" id="ujumbe"></div>
                                
                            </form>
                        </div>
                        <!--/panel content-->
                    </div>
                    <!--/panel-->

                              
                              <!--Data entry code-->
                            
                            </div>
                            
                           
                             
                            
                             <div class="tab-pane well" id="edit<%=selectedform%>form">
                                
                                <div id="editmainform_div">
                                    
</div>
                               <!--- Data export---->
                            </div>
     
                            
<div class="tab-pane well" id="reports">
<form action="MortalityClinicalReports" id="reportingForm">

                                        <!--Dashboard code-->

   
                                                    <% IdGenerator2 ig = new IdGenerator2();%>
                                                  


                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4>Reports Download</h4></div>
                                            <div class="panel-body">
                                                <form id="reportsform">
                                                    <table class='table table-striped table-bordered' id="reportstable" >

                                        <tr >

                                            <td class="col-xs-4">
                                                <div class="control-group">

                                                    <div class="controls">
                                                        <label><b>Select Report</b><font color="green"></font></label> 

                                                    </div>
                                                </div>
                                            </td>

                                            <td >
                                                <div class="control-group">

                                                    <div class="controls">
                                                        <select class="form-control input-sm" onchange="checkFormAction();"   name='report' id='report' >
                                                            <option value='MortalityAuditReports'>1.Tracker and Data Summary</option>
                                                           
                                                            <!--<option value='hts_self_reports'>6.HTS Self</option>-->

                                                        </select>
                                                    </div>
                                                </div>
                                            </td>

                                        </tr>


                                        <tr >
                                            <td class="col-xs-4">
                                                <div class="control-group">

                                                    <div class="controls">
                                                        <label ><b>Start date:</b><font color='red'><b>*</b></font></label>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="col-xs-4">
                                                <div class="controls">
                                                    <input data-date-end-date="0d" required type="text" title="this is the date that the week started" value="<%=ig.LastMonthStartDate()%>" class="form-control input-sm dates" name="startdate" autocomplete="off" id="startdate">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr >
                                            <td class="col-xs-4">
                                                <div class="control-group">

                                                    <div class="controls">
                                                        <label ><b>End date:</b><font color='red'><b>*</b></font></label>

                                                    </div> </div>
                                            </td>
                                            <td class="col-xs-4">
                                                <div class="controls">
                                                    <input data-date-end-date="0d" required type="text"  title="this is the date that the week ended" value="<%=ig.LastMonthEndDate()%>" class="form-control input-sm dates" name="enddate" id="enddate" autocomplete="off"/>
                                                </div>
                                                </div>
                                            </td>
                                        </tr>
                                       <tr >
                                       <td colspan="2"> <div class="form-actions">


                                                    <input type="submit" id="generaterpt" class="btn green" value="Generate report" />



                                                </div>
                                        </td>
                                         </tr>                                           

</table>
                                                </form>
                                            </div>
                                            <!--/panel-body-->
                                        </div>
                                        <!--/panel-->



                                        <!--Reports entry code-->

</form>
                                    </div>
                            <div class="tab-pane well" id="export">
                                
                                
                               <!--- Data export---->
                            </div>
                            
                            <div class="tab-pane well" id="searchdata">
                                
                                <div id="searchtablediv">
                                  
                                </div>    
                               <!--- Data export---->
                            </div>
                            
                            
                            </div>
                        </div>

                    </div>
                    <!--/tabs-->

                </div>
                <!--/col-->
               <div id='fedback' class="alert-info">Note: Please enter all the required data.</div>
                <!--/col-span-6-->

            </div>
            <!--/row-->

           


            
        </div>
        <!--/col-span-9-->
    </div>

<!-- /Main -->

<footer class="text-center"> &copy; Usaid Tujenge Jamii | USAID </footer>

<div class="modal" id="addWidgetModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" id="refr1" aria-hidden="true">×</button>
                <h4 class="modal-title">Data Export</h4>
            </div>
            <div class="modal-body">
                <form id="exportdataform">
              <button class=" btn-lg btn-success" style="text-align: center;" id="exportbutton" onclick="importdata();"> Export Data</button>
              <button class=" btn-lg btn-info" style="display:none;text-align: center;"  id="exportmsg" > Exporting Data..</button>
              <p id="exportresponse"> </p>
              </form>
            </div>
            <div class="modal-footer">
                <a href="#" data-dismiss="modal" class="btn" id="refr">Close</a>
              
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dalog -->
</div>
<!-- /.modal -->

<div class="modal" id="userdetails">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" id="saveuserbtn" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">User Details</h4>
            </div>
            <div class="modal-body">
                <form action="#" id="userform" method="post">
                 <div class="control-group">
                                    <label><font color="red"><b>*</b></font>  User Name</label>
                                    <div class="controls">
                                        <input type="text" size="14"   required name="username" id="username" class="form-control" >
                                    </div>
                                </div> 
                    
                     <div class="control-group" >
                                    <label>County Supporting:</label>
                                    <div class="controls">
                                        <select  name="usercounty" id="usercounty" style="width:100%;" class="form-control">
                                            <option>Select County</option>
                                             <option value="Baringo">Baringo</option>
                                             <option value="Kajiado">Kajiado</option>
                                             <option value="Laikipia">Laikipia</option>
                                             <option value="Nakuru">Nakuru</option>
                                             <option value="Narok">Narok</option>
                                            
                                           
                                        </select>
                                    </div>
                                </div>
                    
                                <div class="control-group">
                                    <label></label>
                                    <div class="controls">
                                        <button onclick=" updateuser();"  type="submit" style="margin-left: 50%;" data-dismiss="modal" class="btn-lg btn-success active">
                                            SAVE
                                        </button>
                                    </div>
                                </div>   
                    
                </form>
            </div>
            <div class="modal-footer">
                <a href="#" data-dismiss="modal" class="btn">Close</a>
              
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dalog -->
</div>


<div class="modal" id="help">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Help</h4>
            </div>
            <div class="modal-body">
                <p>This  application is created for aiding users in collecting data for KP Clients Verification purpose only.</p>
                <h3>Indicators</h3>
               
                <ul>


													
<!--<li>VMMC CIRC</li>												-->




                </ul>
             <h3> Data is reported by Facility type </h3>
                 <p> You are only able to report data for non - surge / Non priority site</p>
              </div>
            <div class="modal-footer">
                <a href="#" data-dismiss="modal" class="btn">Close</a>
               
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dalog -->
</div>




	<!-- script references -->
        <script src="js/jquery-1.9.1.js"></script>
        
		<script src="js/bootstrap.js"></script>
                <!--<script type="text/javascript" src="assets/js/dataTables.bootstrap.min.js"></script>--> 
		<script src="js/scripts.js"></script>
                <script src="js/bootstrap-datepicker.min.js"></script>
                <script src="js/select2.min.js"></script>
                 <script src="js/pouchdb-4.0.1.js"></script>
                 <!--<script type="text/javascript" src="js/datatables.min.js"></script>-->
                 <script type="text/javascript" src="clinical_forms//validation.js"></script>
                 <script type="text/javascript" src="assets/js/jquery.dataTables_1.3.min.js"></script> 
                 <script type="text/javascript" src="assets/js/dataTables.responsive.min.js"></script>
                 <script type="text/javascript" src="https://cdn.datatables.net/fixedheader/3.3.2/js/dataTables.fixedHeader.min.js"></script>

                 
<!--                   <script type="text/javascript" charset="utf-8" src="cordova-1.5.0.js"></script>  -->
                 <script>
                     var _frm=$("#frmname").val();
                    
                         $('.dates').datepicker({
                             todayHighlight: true,clearBtn: true, autoclose: true,format: "yyyy-mm-dd",
     });
                 </script>

                 <script type="text/javascript">
  $(document).ready(function(){
  $('facilityname').select2();    
  
     _frm=$("#frmname").val();
  });   
                 </script>


<script>
  

 function getFacilitiesJson(){
       
     
       
       
              $.ajax({
                    url:'loadActiveSites',                            
                    type:'post',  
                    dataType: 'html',  
                    success: function(data)
                    {
                     $("#facility").html(data);
                   $(document).ready(function() {
          
              $('#facility').select2(); 
             
                                 } ); 
                        
                        
                    }});
   
   }
 
  // getFacilitiesJson();
 
   
   
   
   
      function getDicsJson() {

                                       var lip = $("#lip").val();

                                      var dicoption="<option data-ward_name='' data-ward_id='' data-supported_kp='' value=''>select dic</option>";
                                       $.ajax({
                                           url: 'getDics?lip=' + lip,
                                           type: 'post',
                                           dataType: 'html',
                                           success: function (data) {
                                               $("#facility").html(dicoption+data);
                                              
                                               
                                               $(document).ready(function () {

                                                   $('#dic').select2();
isdisplayindicators();
                                               });


                                           }});

                                   }



                                   getDicsJson();
   
   
   
  var facility=null;

 var month=null;   
 var year=null;
    


//createdynamicinputs();







function refreshujumbe(){
    
  $("#fedback").html(""); 
 //refreshPage();
    
}

function validatefacility(){
    
    var returnval=true;
    
    var facil=$("#facilityname").val();  
         
        
         
     if(facil===''|| facil==='Select Facility' )
     {         
  
   alert('Select facility');
   
     $("#facilityname").focus();   
    returnval=false;
     }
     
      
     return returnval; 
    
}

function clickreportstab(){
   
 $('#reportsbutton').click();
}







function showreports(){
    
     $("#reportsbutton").show();
}



$("#refreshpage" ).click(function() 
{
    window.location.reload();
   
});

$("#refr" ).click(function() 
{
    window.location.reload();
   // clearweeklyfields();
});


$("#refr1" ).click(function() 
{
    window.location.reload();
   // clearweeklyfields();
});

function delayedrefresh()
{
      window.location.reload();
    clearweeklyfields();
}


//==================function to import data

// $('#exportbutton').on('click',function() {
    $("#exportbutton").prop("disabled",false);
     $(this).removeClass('btn-lg btn-default').addClass('btn btn-success');
//});










</script>
<script>


//========================================201605 custom percentage calculator======================================





//a function to save comments after a user enters them


function disablefinish(){
    
     $("#finishbutton").prop('disabled',true);
    // alert('disable');
     //setTimeout(enablefinish(),2000);
}

function enablefinish(){
    
     $("#finishbutton").prop('disabled',false);
}




   function numbers(evt){
var charCode=(evt.which) ? evt.which : event.keyCode
if(charCode > 31 && (charCode < 48 || charCode>57))
return false;
return true;
}

//Codes to prevent default form submission

$("#userform").submit(function(e){
    return false;
});

$("#weeklydataform").submit(function(e){
    return true;
});
$("#exportdataform").submit(function(e){
    return false;
});

$("#reportsform").submit(function(e){
    return false;
});

 $('input').css('border-color','#337ab7');


//prevent numbers scrolling

$('form').on('focus', 'input[type=number]', function (e) {
  $(this).on('mousewheel.disableScroll', function (e) {
    e.preventDefault();
  });
});
$('form').on('blur', 'input[type=number]', function (e) {
  $(this).off('mousewheel.disableScroll');
});



//additional changes




</script>

 <script>
               

                
                
               
   


function getPeriod(){
       
   var sec=$("#section").val();
       
       
              $.ajax({
                         url:'getParameterData?per=yes',                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) {                        
                       
        var dat=data.periods;
        
      
        var o="";
                        
                        for(var a=0;a<dat.length;a++)
                        {                           
                     
                          o+="<option value='"+dat[a].id+"'>"+dat[a].year+" "+dat[a].month+"</option>";   
                        }
                        
                   $("#period").html(o);
                   $(document).ready(function() {
                    $('#period').select2(); 
             
                                 } ); 
                        
                        
                    }});
   
   }
   

getPeriod();



//a function to disable or enable hidden elements


function isdisplayindicators()
{ 
    var dt=$("#period").val();
   
 
   
    var fc=$("#facility").val().trim();
//    console.log("_"+fc+"vs"+dt);
    if( dt!=='' && fc!=='Select Facility'&& fc!=='')
    {        
    // display facility name
    $("#dynamicindicators"+_frm).show();    
     
            var mainformwhere="Form='"+_frm+"'";
           
            
            
            //also load the edit fields
           
          
             loadEdits(_frm,'editmainform_div');
            
            
            //now load the data
          $.ajax({
                    url:'loadKpClientIndicators?dt='+dt+"&fc="+fc+"&wr="+mainformwhere,                            
                    type:'post',  
                    dataType: 'html',  
                    success: function(data) 
                    {
                        
                       
                        $(".savebuttons").show();
                        
                   $("#dynamicindicators"+_frm).html(data); 
                    setuuid('id');
                         $('.dates').datepicker({
                             todayHighlight: true,clearBtn: true, autoclose: true,format: "yyyy-mm-dd",
     });
     
     

                        
                        
                    }});    
          
           
            
            
    }
    else 
    {
    $("#dynamicindicators"+_frm).hide();
   
      $(".savebuttons").hide();
    //        
    }
    
    
}


function loadExistingClient(clientid,frm)
{ 
    
    //once the edit form is clicked, the assumption is that the add section will be loaded
    $('#add'+frm+"button").click();
    
    var dt=$("#period").val();
   
    var fc=$("#facility").val().trim();
//    console.log("_"+fc+"vs"+dt);
    if( dt!=='' && fc!=='Select Facility'&& fc!=='')
    {        
    // display facility name
    $("#dynamicindicators"+_frm).show();    
     
            var frmwhere="Form='"+frm+"'";
            
            
            
           
            
            //now load the data
          $.ajax({
                    url:'loadKpClientIndicators',                            
                    type:'post',  
                    data:{
                        dt:dt,
                        fc:fc,
                        wr:frmwhere,
                    pid:clientid},
                    dataType: 'html',  
                    success: function(data) 
                    {
                   $(".savebuttons").show();
                   $("#dynamicindicators"+frm).html(data); 
                         $('.dates').datepicker({
                             todayHighlight: true,clearBtn: true, autoclose: true,format: "yyyy-mm-dd",
     });
     //cal a trigger to refresh the forms so that hidden forms with data can unhide
     getElementsToBeRefreshed(frm);
   
                        
                        
                    }});    
             
           
            
            
    }
    else 
    {
    $("#dynamicindicators"+_frm).hide();
  
    //        
    }
    
    
}

function getElementsToBeSaved(formname)
{ 
    
    
    //First do validation checks then save
    
    var dt=$("#period").val();
   
    var fc=$("#facility").val().trim();
      
 
            //now load the data
          $.ajax({
                    url:'loadKpClientIndicators?fm='+formname,                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) 
                    {
                        var dt = data;
 
 
 console.log('Save '+formname+' Elements'+dt);
 //no call the save function and pass the list of variables
          
          saveFormDetails(dt,formname);
                        
                    }});    
         
           
            
          
    
}
function getElementsToBeRefreshed(formname)
{ 
    
    
    //First do validation checks then save
    
   
 
            //now load the data
          $.ajax({
                    url:'loadKpClientIndicators?fm='+formname,                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) 
                    {
                        var dt = data;
 
 
 console.log('Refresh '+formname+' Elements'+dt);
 //no call the save function and pass the list of variables
            for(var i=0;i<dt.length;i++){
 
 var elementid=dt[i].element_id; 
 
  triggerElementChange(elementid);
 
 }
         
                        
                    }});    
         
           
            
          
    
}



function saveFormDetails(de,frm)
{




 var pid=$("#"+de[0].client_identifier_field).val(); 
    
    
    console.log("You are requested to save "+de);    
    
    //First Delete any entered data
    

  
 
    //First Delete any entered data
     var issaveready=true;
if(1===2){$("#fedback").html("<font color='red'><h3>Please enter Client Name</h3></f>");   $("#client_name").focus();}

else {
    
     $.ajax({
                    url:'deletePatientRecordsKPClientForm',                            
                    type:'post',  
                    data:{pid:pid},
                    dataType: 'html',  
                    success: function(fdbk) 
                    {
             
             if(fdbk.trim()==='success')
             {
             
                 var last_save_status="";
               for(var i=0;i<de.length;i++){
         
            
                
                       
                       //"id","facility_id","linelisting_month","patient_id","indicator_id","value","encounter_id","user_id","","is_locked"
                             console.log("at saving point"+de[i].element_id);
                             
   var dt=$("#period").val();
   var fc=$("#facility").val().trim();
   var tid=uuidv4();
   var elementid=de[i].element_id; 
   var lbl=de[i].label; 
   var val=$("#"+de[i].element_id).val(); 
  
 
              if($("#"+elementid).is(":visible"))
              {
               //check if is element is required
               if($("#"+elementid).prop('required'))
               {
    if(val===''){
                  issaveready=false;
                  
   $("#fedback").html("<font color='red'><h3>Please specify "+lbl+"</h3></f>");  $("#"+elementid).focus();   $("#"+elementid).css('border-color', '#FF0000'); 
   dltptquietly(pid);
   
              }     
                   
               }
               
              }    
             
             //First delete the existing Record in the database then after deletion, proceed and save
             //
             
             
        if(issaveready){
             
               $.ajax({
                    url:'saveKpClientdata',                            
                    type:'post',  
                    data:{id:tid,
                        facility_id:fc,
                        linelisting_month:dt,
                        patient_id:pid,
                        indicator_id:elementid,
                        value:val,
                        encounter_id:pid,
                        user_id:"not specified",
                        is_locked:"1",Form:frm},
                    dataType: 'html',  
                    success: function(dat) 
                    {
                        
  last_save_status=dat;
  
   
  
 
                    },
        error: function (xhr, ajaxOptions, thrownError) {
        alert(xhr.status);
        alert(thrownError);
      }
                
            
            }); 
           
           
           if(i===parseInt(de.length)-1){
            
            
            //refresh page
            
            console.log("Data saved Succesfully!"+last_save_status);
                           $("#fedback").html("<font color='green'><h3>Data saved Succesfully</h3></f>");
                           //getElementsToBeReset(frm);
                           isdisplayindicators();
                             setTimeout(refreshujumbe,4000);
            
               
           }
           
                }
                else {
                    //stop the loop
                    console.log("Save loop stopped");
                    break;
                    
                }
                     } //end of for loop
    
             
            }
            else {
             console.log("records not saved successfully");   
                
                }
                     
    } ,
    
    error: function (xhr, ajaxOptions, thrownError) {
        alert(xhr.status);
        alert(thrownError);
      }




});
    
    
    }
  
}


 function getElementsToBeReset(formname)
{ 
    
    
    //First do validation checks then save
    
   
 
            //now load the data
          $.ajax({
                    url:'loadKpClientIndicators?fm='+formname,                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) 
                    {
                        var dt = data;
 
 
 console.log('Refresh '+formname+' Elements'+dt);
 //no call the save function and pass the list of variables
            for(var i=0;i<dt.length;i++){
 
 var elementid=dt[i].element_id; 
 
  resetElement(elementid);
  
  
  //after the 
  
 
 }
         
                        
                    }});    
         
           
            
          
    
}
  


$('#dataentry').on('keydown', 'input, select, textarea', function(e) {
    var self = $(this)
      , form = self.parents('form:eq(0)')
      , focusable
      , next
      ;
    if (e.keyCode == 13) {
        focusable = form.find('input,a,select,button,textarea').filter(':visible');
        next = focusable.eq(focusable.index(this)+1);
        if (next.length) {
            next.focus();
        } else {
            form.submit();
        }
        return false;
    }
});






//showedits

function loadEdits(formtoload,elementtoappend){
    
    //loadmtrs_sel_val,act=loadmothers,fac
    
    { 
    
    
    //First do validation checks then save
    
//    var dt=$("#period").val();
   
    var fc=$("#facility").val().trim();
      
  //now load the data
          $.ajax({
                    url:'dataPulls',                            
                    type:'post',  
                   
                    dataType: 'html',  
                    data:{act:"showKPEdits",
                         fac:fc,
                         fm:formtoload,
                         table_docker:elementtoappend},
                    success: function(data) 
                    {
                        var dt = data;
                        
     
//<label class='btn btn-success'>Edit</label>          
$("#"+elementtoappend).html(""+dt);
         
	  
                
          var table=$('#searchtable_'+elementtoappend).DataTable({              
              "autoWidth": true,
              "paging": true,
              "pagingType": "full",
              "lengthChange": true,
              "responsive":true,
          "order": [[0,'desc']]});
            
           new $.fn.dataTable.FixedHeader( table );
          
          //$("#mother_id").html(dt);
                        
                    }});    
         
           
            
          
    
}
    
    
}

//loadEdits('mot_audit','editmainform_div');

function ShowAge(dob,dest) 
{
    
    var sikuyakuzaliwa=$("#"+dob).val(); 
    
    console.log("called at Age"+ sikuyakuzaliwa);
    if(sikuyakuzaliwa!=='')
    {
    var today = new Date();
    var birthDate = new Date(sikuyakuzaliwa);
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) 
    {
        age--;
    }
    
    $("#"+dest).val(age);
    
        
    
    return age;
}
}

function uuidv4() {
  return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
    (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
  );
}

function setuuid(id){
    
    $("#"+id).val(uuidv4());
}


function refreshPage(){
    
      window.location.reload();
    
}







function dltpt(pid){
       var result = confirm("Are you sure you want to delete this patient?");
if (result) {
      $.ajax({
                    url:'deletePatientRecordsKPClientForm',                            
                    type:'post',  
                    data:{pid:pid},
                    dataType: 'html',  
                    success: function(fdbk) 
                    {
                      //after success in deletion, refresh tables
            
             loadEdits('<%=selectedform%>','editmainform_div');
                        
                        
                    }
                });
                }
    
}


function dltptquietly(pid){
     
      $.ajax({
                    url:'deletePatientRecordsKPClientForm',                            
                    type:'post',  
                    data:{pid:pid},
                    dataType: 'html',  
                    success: function(fdbk) 
                    {
                      //after success in deletion, refresh tables
            
             loadEdits('<%=selectedform%>','editmainform_div');
                        
                        
                    }
                });
                
    
}


function triggerElementChange(el){
    
    
    $("#"+el).trigger("change");
    
}



function resetElement(el)
{
    
    
    $("#"+el).val("");
    
}

function test(){
    
    
    alert((($('#death_date').val()).replace('-','')).substring(0,6));
    //alert((('2022-05-25').replace('-','')).substring(0,6));
  
    
}

//test();
              </script>

	</body>
</html>
