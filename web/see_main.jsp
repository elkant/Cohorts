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
		<title>Understanding RoCs</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
                <link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
		<link href="css/bootstrap.css" rel="stylesheet">
                <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
                <link rel="stylesheet" href="css/select2.min.css">
                <link rel="shortcut icon" href="images/see.png">
                <!--<link data-jsfiddle="common" rel="stylesheet" media="screen" href="css/handsontable.css">-->
<!--  <link data-jsfiddle="common" rel="stylesheet" media="screen" href="dist/pikaday/pikaday.css">-->
                  
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="css/styles.css" rel="stylesheet">
                
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
           <%if(session.getAttribute("kd_session")!=null){%><%} else {  response.sendRedirect("logout");}%>        
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
                       

            
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
              
                    <ul id="g-account-menu" class="dropdown-menu" role="menu">
                        <li><a href="#">My Profile</a></li>
                    </ul>
                </li>
          
                
                 <!--<li><a title="Add Widget" id="adduserbutton" data-toggle="modal" href="#userdetails"><i class="glyphicon glyphicon-user"></i><span id="usernamelabel"> Add Username</span></a></li>-->
                 <!--<li><a title="Add Widget" data-toggle="modal" style="display:none;" id="exportdataanchor2" href="#addWidgetModal"><i class="glyphicon glyphicon-cloud-upload"></i> Export Data</a></li>-->
                 <li>
                  <a  title="Help" data-toggle="modal" href="#help">
                            <i class="glyphicon glyphicon-question-sign"></i>
                            Help
                        </a></li>
                              <li><a style="text-align: center;" href='otz_index.jsp'><i class="glyphicon glyphicon-log-out"></i> Logout</a></li>
            </ul>
        </div>
        
    </div>
    <!-- /container -->
    
</div>
<!-- /Header -->

<!-- Main -->
<div class="container-fluid">
    
<!--    <div class="row">
         <label class="col-sm-2"></label>
        <a class='btn btn-default col-sm-1' style="text-align: center;" href='otz_index.jsp'><i class="glyphicon glyphicon-log-out"></i>Logout</a>
        <label class="col-sm-2"></label>
          <a class='btn btn-success col-sm-3' style="text-align: center;" href='aca_mca_reports.jsp'>Generate Reports</a>
           <label class="col-sm-2"></label>
    </div>-->
    <div class="row">
        
        <!-- /col-3 -->
        <div class="col-sm-12">

            
            
          
          <h5 class="btn btn-default col-md-12" style="text-align: center;color:blue;"><b>Social Economic and Environmental Factors affecting RoCs</b></h5>

            <div class="row">
                <!-- center left-->
                <div class="col-md-12">
                    

                  

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

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Reporting  Month</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Facility</label> 

                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>


                                                        <tr >

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <select required="true"   onchange="getFacilitiesJson();isdisplayindicators();"   name="period" id="period" class="form-control" >
                                                                                                                   
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </td>


                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                     <select required="true"   onchange="isdisplayindicators();"   name="facility" id="facility" class="form-control" >
                                                                                                                   
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>




                                                    </table>
                   

                    <!--tabs-->
                    <div class="panel">
                        <ul class="nav nav-tabs " id="myTab">
                            <li class="active newdata"> <a href="#addroc" id="addrocbutton" data-toggle="tab">  <i class="glyphicon glyphicon-plus"> </i> <font color='green'><b>Add </b></font> Patient(s)</a></li>
                            <li class="newdata"> <a href="#editroc" id="editrocbutton" data-toggle="tab">  <i class="glyphicon glyphicon-edit"> </i> <font color='green'><b>View</b></font> Patient(s)</a></li>
                          <!--<li><a href="#reports" style="" id="reportsbutton" data-toggle="tab"> <i class="glyphicon glyphicon-stats"></i>Reports</a></li>--> 
                            <!--<li><a href="#searchdata" data-toggle="tab"> <i class="glyphicon glyphicon-search"></i> Edit Data</a></li>--> 
                           <!-- <li><a href="#export" data-toggle="tab"> <i class="glyphicon glyphicon-cloud-upload"></i> Data Export</a></li>-->
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active well col-md-12" id="addroc">
                                
                                
                              <!--Data entry code-->
                    <div class="panel panel-default">
                       
                        <div class="panel-body" style="width:100%;">
                            <form class="form form-vertical"  action="#" method="post" id="weeklydataform">
                              <table class='table table-striped table-bordered'  style=" width:100%;border :3px solid #4b8df8;" >

                                                        


                                                        





                      <!-------Data Management ---------->

                      
                      
                                                      


                                                      




                                                    </table>
                                
                                <!--<form id="addrocsform">-->
                                
                                         <table class='table table-striped table-bordered' id="dynamicindicatorsroc" style="display:none;border :3px solid #4b8df8;padding:1px;" > 
                                   
                                <!------INDICATORS----->
                                           <tr><td><h3>Select Line listing Year, Month and Facility to Load data for.</h3></td></tr>                    
                                 
                                  
                                     </table>
                                <table class="table table-striped table-bordered">
                                       <tr><td colspan="3" class="col-xs-12">               
                                <div class="control-group col-xs-12">
                                        <div id='fedback' class="alert-info">Note: Please enter all the required data.</div>
                                   <br/>
                                    <div style="display:none;" class="controls savebuttons">
                                        <input type="input" onClick="getElementsToBeSaved('roc');"  id='savebutton' value="Save Data"  style="margin-left: 0%;" class="btn-sm btn-success active">
                                            
                                     </div>
                                     <div class="controls">
                                        <button type="submit" id='updatebutton' onclick="getElementsToBeSaved('roc');" style="margin-left: 0%;display:none;" class="btn-sm btn-info active">
                                            Update Client 
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
                            
                            <div class="tab-pane well" id="addbaby">
                                
                                
                               <!--- Heis---->
                               
                               <table class='table table-striped table-bordered' id="dynamicindicatorsbaby" style="display:none;border :3px solid #4b8df8;padding:1px;" > 
                                   
                                <!------INDICATORS----->
                                                               
                                  <tr><td><h3>Select Line listing Year, Month and Facility to Load data for.</h3></td></tr>                    
                                 
                                  
                                     </table>
                               
                               <div class="control-group col-xs-12">
                                        
                                   <br/>
                                    <div style="display:none;" class="controls savebuttons">
                                        <input type="input" onClick="getElementsToBeSaved('roc');"  id='savebutton' value="Add Baby"  style="margin-left: 0%;" class="btn-sm btn-success active">
                                            
                                     </div>
                                     <div class="controls">
                                        <button type="submit" id='updatebutton' onclick="getElementsToBeSaved('roc');" style="margin-left: 0%;display:none;" class="btn-sm btn-info active">
                                            Update Baby 
                                        </button>
                                    </div>                                   
                                    
                                </div>
                               
                            </div>
                             
                            
                             <div class="tab-pane well" id="editroc">
                                
                                <div id="editroc_div"></div>
                               <!--- Data export---->
                            </div>
      <div class="tab-pane well" id="editbaby">
                                
                               <div id="editbaby_div"></div>   
                               <!--- Data export---->
                            </div>
                            
<div class="tab-pane well" id="reports">
<form action="seeReports" id="reportingForm">

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
                                                            <option value='seeReports'>1.Tracker and Data Summary</option>
                                                           
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
                <p>This  application is created for aiding users in collecting data for Weekly High Frequency Reporting.</p>
                <h3>Indicators</h3>
                <p>The specific indicators that one should enter data for are;</p>
                <ul>


<li>HTS TST- No Tested for HIV	</li>														
<li>HTS TST POS		</li>													
<li>TX LINK</li>													
<li>TX NEW</li>													
<li>TX BTC</li>												
<li>PREP NEW</li>													
<li>VMMC CIRC</li>												




                </ul>
             <h3> Facilities</h3>
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
		<script src="js/scripts.js"></script>
                <script src="js/bootstrap-datepicker.min.js"></script>
                <script src="js/select2.min.js"></script>
                 <script src="js/pouchdb-4.0.1.js"></script>
                 <script type="text/javascript" src="js/datatables.min.js"></script>
                 <script type="text/javascript" src="pmtct_ovc/validation.js"></script>
  
<!--  <script data-jsfiddle="common" src="dist/pikaday/pikaday.js"></script>
  <script data-jsfiddle="common" src="dist/moment/moment.js"></script>
  <script data-jsfiddle="common" src="dist/zeroclipboard/ZeroClipboard.js"></script>
  <script data-jsfiddle="common" src="dist/numbro/numbro.js"></script>
  <script data-jsfiddle="common" src="dist/numbro/languages.js"></script>-->
  <!--<script data-jsfiddle="common" src="js/handsontable.js"></script>-->
                 
<!--                   <script type="text/javascript" charset="utf-8" src="cordova-1.5.0.js"></script>  -->
                 <script>
                    
                    
                         $('.dates').datepicker({
                             todayHighlight: true, daysOfWeekDisabled: "0,6",clearBtn: true, autoclose: true,format: "yyyy-mm-dd",
     });
                 </script>

                 <script type="text/javascript">
  $(document).ready(function(){
  $('facilityname').select2();    
  });   
                 </script>


<script>
   
  var user="aphiaplus_pca";  
//load data from the cloud server 
//


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
 
   getFacilitiesJson();
 
        
    //------------------------------------------------------------------------
    //
    //


    //
    //------------------------------------------------------------------------
    
    
//prepare form data

//===================================================INSERT WEEKLY DATA===================================

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


//===========================================EMPTY WEEKLY DATA FIELDS AFTER INSERT============================================================

function clearweeklyfields()
{
   // $("#facilityname").val("");
   //No facility name should have an underscore since its a special key
   
//$("#startdate").val("");   
//$("#enddate").val("");

for(b=0;b<allindicatorsarray.length;b++){
    
  $("#"+allindicatorsarray[b]).val("");  
    
} 

    
}



var dbdata="";

//===================================================VIEW WEEKLY DATA============================================================
//a function to select a few search data that should appear in a data table
function selectsearchdata()
{
    
  

    
    
    
    
    
    
    //read data from the db
    
  	  
    
    
}

//call the function that displays the data

function appendtabledata( dbdata ){
    
     $("#searchtablediv").html("<table id='searchtable' class='table table-striped table-bordered'><thead><tr><th>week <br/>beginning </th><th>Facility</th><th>Edit</th></tr></thead><tbody>"+dbdata+"</tbody></table>");
         
	   $(document).ready(function() {
                
          $('#searchtable').DataTable({              
              "autoWidth": true,
              "paging": true,
              "pagingType": "full",
              "lengthChange": false,                     
          });
            
                                     } ); 
    
                                                          }

 selectsearchdata();

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











function closeapp() 
{
      //navigator.app.exitApp();   // Closes the new window
}


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

 <script data-jsfiddle="example1">
                var
                 
                  container = $('example1'),
                  exampleConsole = $('example1console'),
                  autosave = $('autosave'),
                  load = $('load'),
                  save = $('save'),
                  autosaveNotification,
                  hot;

//                hot = new Handsontable(container, {
//                  startRows: 8,
//                  startCols: 6,
//                  rowHeaders: true,
//                  colHeaders: true,
//                  minSpareRows: 1,
//                  contextMenu: true,
//                  afterChange: function (change, source) {
//                    if (source === 'loadData') {
//                      return; //don't save this change
//                    }
//                    if (!autosave.checked) {
//                      return;
//                    }
//                    clearTimeout(autosaveNotification);
//                    ajax('json/save.json', 'GET', JSON.stringify({data: change}), function (data) {
//                      exampleConsole.innerText  = 'Autosaved (' + change.length + ' ' + 'cell' + (change.length > 1 ? 's' : '') + ')';
//                      autosaveNotification = setTimeout(function() {
//                        exampleConsole.innerText ='Changes will be autosaved';
//                      }, 1000);
//                    });
//                  }
//                });

//                Handsontable.Dom.addEvent(load, 'click', function() {
//                  ajax('json/load.json', 'GET', '', function(res) {
//                    var data = JSON.parse(res.response);
//
//                    hot.loadData(data.data);
//                    exampleConsole.innerText = 'Data loaded';
//                  });
//                });

//                Handsontable.Dom.addEvent(save, 'click', function() {
//                  // save all cell's data
//                  ajax('json/save.json', 'GET', JSON.stringify({data: hot.getData()}), function (res) {
//                    var response = JSON.parse(res.response);
//
//                    if (response.result === 'ok') {
//                      exampleConsole.innerText = 'Data saved';
//                    }
//                    else {
//                      exampleConsole.innerText = 'Save error';
//                    }
//                  });
//                });

//                Handsontable.Dom.addEvent(autosave, 'click', function() {
//                  if (autosave.checked) {
//                    exampleConsole.innerText = 'Changes will be autosaved';
//                  }
//                  else {
//                    exampleConsole.innerText ='Changes will not be autosaved';
//                  }
//                });
                
                
               
   


function getPeriod(){
       
   var sec=$("#section").val();
       
       
              $.ajax({
                         url:'getParameterData?per=yes',                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) {                        
                       
        var dat=data.periods;
        
      
        var o="<option value=''>Select Seriod</option>";
                        
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
    $("#dynamicindicatorsroc").show();    
     
            var motherwhere="Form='roc'";
           
            
            
            //also load the edit fields
           
           
             loadEdits('roc','editroc_div');
            
            
            //now load the data
          $.ajax({
                    url:'loadSeeIndicators?dt='+dt+"&fc="+fc+"&wr="+motherwhere,                            
                    type:'post',  
                    dataType: 'html',  
                    success: function(data) 
                    {
                        
                       
                        $(".savebuttons").show();
                        
                   $("#dynamicindicatorsroc").html(data); 
                    setuuid('id');
                         $('.dates').datepicker({
                             todayHighlight: true, daysOfWeekDisabled: "0,6",clearBtn: true, autoclose: true,format: "yyyy-mm-dd",
                            });
     //loadLocationOptionsFromDb('loadcts','county');
     //loadLocationOptionsFromDb('loadsbct','subcounty');
     //loadLocationOptionsFromDb('loadwrd','ward');
     

                        
                        
                    }});    
            
           
            
            
    }
    else 
    {
    $("#dynamicindicatorsroc").hide();
    
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
    $("#dynamicindicatorsroc").show();    
     
            var frmwhere="Form='"+frm+"'";
            
            
            
           
            
            //now load the data
          $.ajax({
                    url:'loadSeeIndicators',                            
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
                             todayHighlight: true, daysOfWeekDisabled: "0,6",clearBtn: true, autoclose: true,format: "yyyy-mm-dd",
     });
     
     // loadLocationOptionsFromDb('loadcts','county');
     //loadLocationOptionsFromDb('loadsbct','subcounty');
     //loadLocationOptionsFromDb('loadwrd','ward');
   
                        
                        
                    }});    
             
           
            
            
    }
    else 
    {
    $("#dynamicindicatorsroc").hide();
    
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
                    url:'loadSeeIndicators?fm='+formname,                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) 
                    {
                        var dt = data;
 
 
 console.log('Save '+formname+' Elements'+dt);
 //no call the save function and pass the list of variables
          
          saveFormDetails(dt);
                        
                    }});    
         
           
            
          
    
}



function saveFormDetails(de)
{




 var pid=$("#"+de[0].client_identifier_field).val(); 
    
    
    console.log("You are requested to save "+de);    
    
    var cccno=$("#ccc_no").val();
    var cname=$("#client_name").val();
    var ag=$("#age").val();
    var pn=$("#phoneno").val().trim();
    //First Delete any entered data
    
if(cname===''){$("#fedback").html("<font color='red'><h3>Please enter Client Name</h3></f>");   $("#client_name").focus();}
if(cname.trim().length<4){$("#fedback").html("<font color='red'><h3>Please enter full Client Name</h3></f>");   $("#client_name").focus();}
else if($("#ccc_no").val()===''){$("#fedback").html("<font color='red'><h3>Please enter Patient CCC Number</h3></f>");  $("#ccc_no").focus();}
else if(cccno.length!==10){$("#fedback").html("<font color='red'><h3>Please enter a 10 digit CCC number</h3></f>");  $("#ccc_no").focus();}
else if ($("#dob").val()===''){$("#fedback").html("<font color='red'><h3>Please enter Patient Date of Birth</h3></f>");  $("#dob").focus();}
else if ($("#sex").val()===''){$("#fedback").html("<font color='red'><h3>Please Specify Patient Sex</h3></f>");  $("#sex").focus();}
else if (ag>19){$("#fedback").html("<font color='red'><h3>The required data is for Patients below 19 Yrs Only</h3></f>");  $("#dob").focus();}
else if (pn>0 && pn.length!==10){$("#fedback").html("<font color='red'><h3>Please enter 10 digit phone number in 07XX XXX XXX format</h3></f>");  $("#phoneno").focus();}

else if ($("#county").val()===''){$("#fedback").html("<font color='red'><h3>Please specify County of residence</h3></f>");  $("#county").focus();}
else if ($("#subcounty").val()===''){$("#fedback").html("<font color='red'><h3>Please specify Sub-County of residence</h3></f>");  $("#subcounty").focus();}
else if ($("#ward").val()===''){$("#fedback").html("<font color='red'><h3>Please specify ward</h3></f>");  $("#ward").focus();}
else if ($("#village").val()===''){$("#fedback").html("<font color='red'><h3>Please specify Village of residence</h3></f>");  $("#village").focus();}


else if ($("#economic_issue").val()===''){$("#fedback").html("<font color='red'><h3>Please specify economic issue</h3></f>");  $("#economic_issue").focus();}
else if ($("#social_issue").val()===''){$("#fedback").html("<font color='red'><h3>Please specify social Issues</h3></f>");  $("#social_issue").focus();}
else if($("#environmental_issue").val()==='' ){$("#fedback").html("<font color='red'><h3>Please specify environmenta issue</h3></f>");  $("#environmental_issue").focus();}
else {
    
     $.ajax({
                    url:'deleteSeeRecords',                            
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
   var val=$("#"+de[i].element_id).val(); 

  
             
//        console.log(de[i].element_id+"  dawa yake ni  "+val+": si array?="+Array.isArray(val));     
             //First delete the existing Record in the database then after deletion, proceed and save
             //
         //if(Array.isArray(val)){    val=JSON.stringify(val); }
             
        
             
               $.ajax({
                    url:'save_see',                            
                    type:'post',  
                    data:{id:tid,
                        facility_id:fc,
                        linelisting_month:dt,
                        patient_id:pid,
                        indicator_id:elementid,
                        value:val,
                        encounter_id:pid,
                        user_id:"not specified",
                        is_locked:"1"},
                    dataType: 'html',  
                    success: function(dat) 
                    {
                        
  last_save_status=dat;
  //after saving every field, reset it
   $('#'+elementid).val("");
  
 
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
             setTimeout(refreshujumbe,3000);
            //resetForm('roc');
               
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
                    url:'seeDataPulls',                            
                    type:'post',  
                   
                    dataType: 'html',  
                    data:{act:"showedits",
                         fac:fc,
                         fm:formtoload,
                         table_docker:elementtoappend},
                    success: function(data) 
                    {
                        var dt = data;
                        
     
//<label class='btn btn-success'>Edit</label>          
$("#"+elementtoappend).html(""+dt);
         
	  
                
          $('#searchtable_'+elementtoappend).DataTable({              
              "autoWidth": true,
              "paging": true,
              "pagingType": "full",
              "lengthChange": false                    
          });
            
           
          
          //$("#mother_id").html(dt);
                        
                    }});    
         
           
            
          
    
}
    
    
}



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


function resetForm(frm){
    
    $('#'+frm)[0].reset();
    isdisplayindicators();
    
}





function loadLocationOptionsFromDb(act,dest)
{
var ct=$("#county").val();
var sbct=$("#subcounty").val();
//now load the data
$.ajax({
url:'seeDataPulls',                            
type:'post',  
dataType: 'html',  
data:{act:act,
sel_ct:ct,
sel_sbct:sbct},
success: function(data) 
{

$("#"+dest).html(data);

}}); 

}




function showOVCElements(){
    
    
    var ag=$("#age").val();
    
    
    if(ag<=15){
        
        


        $(".is_ovc").show();
        $(".is_from_chh").show();
        
    }
    else {
        
        $(".is_ovc").hide();
        $(".is_from_chh").hide();
    }
    
}


              </script>

	</body>
</html>
