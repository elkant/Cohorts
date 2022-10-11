<%-- 
    Document   : index
    Created on : Mar 17, 2016, 3:17:19 PM
    Author     : Emmanuel E
--%>

<%@page import="upi.getUpiReportingDates"%>
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

<!--<html  manifest="cohortsv1.appcache">-->
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>UPI</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
                <link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
		<link href="css/bootstrap.css" rel="stylesheet">
                <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
                <link rel="stylesheet" href="css/select2.min.css">
                <link rel="shortcut icon" href="images/upi.png">
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
                              <li><a style="text-align: center;" href='upi_index.jsp'><i class="glyphicon glyphicon-log-out"></i> Logout</a></li>
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

             <a class='btn-warning btn' target="_blank" href="nupi_summary.txt" style="margin-left:40%;">Access Nupi Summary Query</a> 
            
          
          <h5 class="btn btn-default col-md-12" style="text-align: center;color:blue;"><b>UPI Weekly Reporting Module</b></h5>
        

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


                   

                    <!--tabs-->
                    <div class="panel">
                        <ul class="nav nav-tabs " id="myTab">
                            <li class="active newdata"><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-plus"></i>New Data</a></li>
                            <!--<li class="active editdata" style='display:none;' ><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-edit"></i> Edit Data</a></li>-->
                           <li><a href="#reports" style="" id="reportsbutton" data-toggle="tab"> <i class="glyphicon glyphicon-stats"></i>Reports</a></li> 
                            <!--<li><a href="#searchdata" data-toggle="tab"> <i class="glyphicon glyphicon-search"></i> Edit Data</a></li>--> 
                           <!-- <li><a href="#export" data-toggle="tab"> <i class="glyphicon glyphicon-cloud-upload"></i> Data Export</a></li>-->
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active well col-md-12" id="dataentry">
                                
                                
                              <!--Data entry code-->
                    <div class="panel panel-default">
                       
                        <div class="panel-body" style="width:100%;">
                            <form class="form form-vertical"  action="#" method="post" id="weeklydataform">
                              <table class='table table-striped table-bordered'  style=" width:100%;border :3px solid #4b8df8;" >

                                                        


                                                        





                      <!-------Data Management ---------->

                      
                      
                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Reporting Period</label> 

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
                                                                             <%
                                               getUpiReportingDates gd = new getUpiReportingDates();
                                               dbConn conn = new dbConn();

                                               try {
                                                   JSONArray ja = gd.getRepDates(conn, "2022-08-23");

                                                   out.println(gd.toHtmlDates(ja));

                                               } catch (SQLException ex) {
                                                   Logger.getLogger(getReportingDates.class.getName()).log(Level.SEVERE, null, ex);
                                               }

                                           %>                                       
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
                                         <table class='table table-striped table-bordered' id="dynamicindicators" style="display:none;border :3px solid #4b8df8;" > 
                                   
                                <!------INDICATORS----->
                                 <tr ><td class='col-xs-12' colspan='3'>
                                 <div class='control-group'>
                                     
                                    
                                    
                                    
                                    
                                  </div></td>
                                 </tr>                                 
                                 
                                  
                                     </table>
                                <table class="table table-striped table-bordered">
                                       <tr><td colspan="3" class="col-xs-12">               
                                <div class="control-group col-xs-12">
                                        <div id='fedback' class="alert-info">Note: Please enter all the required data.</div>
                                   <br/>
                                    <div class="controls">
                                        <input type="input" onClick="loadValidation();"  id='savebutton' value="SAVE"  style="margin-left: 0%;" class="btn-lg btn-success active">
                                            
                                     </div>
                                     <div class="controls">
                                        <button type="submit" id='updatebutton' onclick="loadValidation();" style="margin-left: 0%;display:none;" class="btn-lg btn-info active">
                                            UPDATE 
                                        </button>
                                    </div>
                                   
                                    
                                </div>
                                        </td></tr>
                                        
                                </table>
                            </form>
                        </div>
                        <!--/panel content-->
                    </div>
                    <!--/panel-->

                              
                              <!--Data entry code-->
                            
                            </div>
                                                       <div class="tab-pane well" id="reports">
<form action="upiReports" id="reportingForm">

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
                                                            <option value='upiReports'>1.Tracker and Data Summary</option>
                                                           
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
                 <script type="text/javascript" src="upi/validation.js"></script>
  
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
                    url:'loadEmrSites',                            
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
    


   //added 201605 
    var progressbarstoskip=[];
    var allindicatorsarray=[];
    var allcommentsarray=[];
    var allprogressbar_hiddentext_array=[];
     

//createdynamicinputs();

function sumofindicators(sourceindicators,destinationindicator){
    var sourceindicatorsarray=sourceindicators.split("@");
  
   
    var destinationelement=destinationindicator;
    var total=0;
    for(b=0;b<sourceindicatorsarray.length;b++){
        //check if there
        var indiic=sourceindicatorsarray[b].replace("_minus_","");
        var originalindic=sourceindicatorsarray[b];
        
        
        if($("#"+indiic).val()!==''){
            //remove negative
            //if(originalindic.indexOf("_minus_") >==0){
            if(originalindic.indexOf("_minus_")>=0){
               //has negative 
                total=parseInt(total)-parseInt($("#"+indiic).val()); 
            }
            else {
            
      total=parseInt(total)+parseInt($("#"+indiic).val());
      
            }
        
            $("#"+destinationelement).val(total);
            }
            $("#"+destinationelement).val(total);                                   
        }
                                              
                                             
    
}



//=========================================set targets================================

//function runvalidation(){
//    
//    var retv=true;
//    
//    //hts tsts > HTS Pos
//    
//    //HTS Pos > HTS Link
//    
//    //HTS_TST
//    //HTS_TST_POS
//    //TX_LINK
//    
//       var validationsid=["HTS_TST@HTS_TST_POS","HTS_TST_POS@TX_LINK"];
// 
//       var agedis=["bl19_Male","bl19_Female","ttl"];
//       var agedis_detailed=["< 19 Male","< 19 Female","Total"];
//    
//    for( var b=0;b<validationsid.length;b++){
//    for( var a=0;a<agedis.length;a++)
//    {
//        
//    var indicab=validationsid[b].split("@");
//        
//      
//        
//    var elem_a=$("#"+indicab[0]+"_"+agedis[a]).val();  
//    var elem_b=$("#"+indicab[1]+"_"+agedis[a]).val();  
//     console.log("#"+indicab[0]+"_"+agedis[a]+" is "+elem_a);
//     console.log("#"+indicab[1]+"_"+agedis[a]+" is "+elem_b);
//    
//    
//    if(elem_a===""){elem_a=0;}
//    if(elem_b===""){elem_b=0;}
//            
//            if(elem_a!=="" && elem_b!==""){
//                
//                elem_a=parseInt(elem_a);
//                elem_b=parseInt(elem_b);
//                
//                if(elem_b>elem_a) {
//                    retv=false;
//                    
//                    alert(" Data for "+indicab[1]+" "+agedis_detailed[a]+" cannot be more than "+indicab[0]+" "+agedis_detailed[a]);
//                    $("#"+indicab[0]+"_"+agedis[a]).css('background-color','red');
//                    $("#"+indicab[1]+"_"+agedis[a]).css('background-color','red');
//                    break;
//                                  }
//                                 
//                
//                
//                                  }
//                                            
//    }
//    
//     if(retv===false)
//                                  {
//                                      
//                                  break;    
//                                      
//                                  }
//}
//    
// return retv;   
//}
//    








function save_data(){
    


//___indicators to pull___

var id="";
var dater="";
var facility="";
var indicatorid="";
var bl15="";
var ab15="";
var ttl="";
var userid="";

facility=$("#facility").val();

dater=$("#period").val();



//console.log("datimid:"+organisationunitid+"\n edate:"+eddate+"\n stdate:"+stdate+"\n daterange:"+daterange+"\n hospital:"+hosi+"\n mflcode"+facility_mfl_code+"\n ward"+ward);
//<option data-sdate="2019-09-24" data-edate="2019-09-30" data-wk="20" value="2019-09-30">2019-09-24 to 2019-09-30 (Week 20) </option>

//this should happen in a loop


if(facility==='' || facility==='Select facility' ){
    
  alert("enter facility name");  
    
}


else if(dater===''  ){
    
   alert("Select Reporting Month");  
    
}

// else if(runvalidation()===false)
//    {       
//        
//        
//        
//    }
    
    else {
    



$.ajax({
                    url:'getUpiIndicators',                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data){
                        
                       for(a=0;a<data.length;a++){
              var isend=false;             
                          
var indicatorid=data[a].id;
var bl15v=$("#"+indicatorid+"_bl15").val();
var ab15v=$("#"+indicatorid+"_ab15").val();
var ttl=$("#"+indicatorid+"_ttl").val();
var identifier=facility+"_"+dater+"_"+indicatorid;
        
        
      

        
           //save the data
           
           var saveddata={
               id:identifier,              
               date:dater,
               facility:""+facility,
               indicatorid:indicatorid,
              bl15:bl15v,
               ab15:ab15v,
               ttl:ttl,
               userid:'909090'
               
           };
           
           
           
           
           if(a===parseInt(data.length)-1){
               isend=true;
               
           }
           
           exportData(saveddata,isend);
                           
                       } 
                   
                    }
                });



}





    
}


function exportData( data, isend){
    
    
    $.ajax({
                    url:'saveupi',                            
                    type:'post',  
                    dataType: 'html',
                    data:data ,
                    success: function(dat) {
                       if(isend){
                           
                           console.log("Data saved Succesfully!");
                           $("#fedback").html("<font color='green'><h3>Data saved Succesfully!</h3></f>");
                             setTimeout(refreshujumbe,2000);
                       } 
                        
                    }
                });
    
    
}


function refreshujumbe(){
    
  $("#fedback").html("");   
    
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
    clearweeklyfields();
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
   

//getPeriod();



//a function to disable or enable hidden elements


function isdisplayindicators()
{ 
    var dt=$("#period").val();
   
    var fc=$("#facility").val().trim();
//    console.log("_"+fc+"vs"+dt);
    if( dt!=='' && fc!=='Select Facility'&& fc!=='')
    {        
    // display facility name
    $("#dynamicindicators").show();    
     
            
            //now load the data
          $.ajax({
                    url:'getUpiIndicators?dt='+dt+"&fc="+fc,                            
                    type:'post',  
                    dataType: 'html',  
                    success: function(data) 
                    {
                        
                   $("#dynamicindicators").html(data); 
                        
                        
                    }});    
           
            
            
    }
    else 
    {
    $("#dynamicindicators").hide();
    //        
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



              </script>

	</body>
</html>
