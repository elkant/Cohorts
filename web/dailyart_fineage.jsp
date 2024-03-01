<%-- 
    Document   : index
    Created on : Mar 17, 2016, 3:17:19 PM
    Author     : Emmanuel E
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="General.IdGenerator2"%>
<%@page import="retention.ret_getIndicators"%>
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
        <title>Daily ART</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/select2.min.css">
        <link rel="shortcut icon" href="images/dailyart.png">
        <link href="assets/css/style.css" rel="stylesheet" />
        <!--<link data-jsfiddle="common" rel="stylesheet" media="screen" href="css/handsontable.css">-->
        <!--  <link data-jsfiddle="common" rel="stylesheet" media="screen" href="dist/pikaday/pikaday.css">-->

        <!--[if lt IE 9]>
                <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <link href="css/styles.css" rel="stylesheet">
        <link href="assets/css/metro.css" rel="stylesheet" />

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
                    margin-left:1%;
                    margin-right:1%;
                }
            }

            @media screen and (min-width: 1200px) {
                #weeklydataform {
                    margin-left:1%;
                    margin-right:1%;
                }
            }


        </style>
        <style>
            
            td{
 text-align: left ; 
 padding-left:1px;
 vertical-align:middle;
}
fieldset.formatter {
    border: 2px groove #0394ff !important;
   
    /*padding: 0 1.4em 1.4em 1.4em !important;*/
    margin: 0 0 1.5em 0 !important;
    -webkit-box-shadow:  0px 0px 0px 0px #000;
            box-shadow:  0px 0px 0px 0px #000;
   
}

legend.formatter {
    border: 0px groove #0394ff !important;
    margin: 0 0 0.0em 0 !important;
    -webkit-box-shadow:  0px 0px 0px 0px #000;
            box-shadow:  0px 0px 0px 0px #000;
    font-size: 1.2em !important;
    /*font-weight: bold !important;*/
    text-align: center !important;
    width:inherit; /* Or auto */
    padding:0 10px; /* To give a bit of padding on the left and right */
    border-bottom:none;
    margin-left:50px;

}
.data-cell{
    width: 85%;
    margin-top: 5%;
    margin-bottom: 5%;
}
table{
    width: 100%;
}
tr > .st{
border-width: 2px;    
}
.title{
    font-weight: bolder;
    margin-bottom: 130px;
}
.indicator{
    min-width: 100px;
    margin: 2px 2px 2px 2px;
}
input[type=text]{
    border-color: black;
    border-width: 0.5px;
}
input[readonly]{
    background-color: #aeb5ae;
}

.form-control {
    
    padding:1px 1px;
        font-size: 12px;
    
}

</style>

    </head>
    <body >
        <!-- header -->
        <div id="top-nav" class="navbar navbar-inverse navbar-static-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button id="toolid" style="float:left;color:white; text-align: center;" class="navbar-toggle btn btn-default" >&nbsp;Daily ART Report</button>
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
                        <li><a  class=''  href='ret_index.jsp'><i class='glyphicon glyphicon-log-out'></i> Log out</a></li>
                    </ul>
                </div>

            </div>
            <!-- /container -->

        </div>
        <!-- /Header -->

        <!-- Main -->
        <div ng-app="htsselfapp" ng-controller="htsselfcont" class="container-fluid">

            <div class="row">

            </div>
            <div class="row">

                <!-- /col-3 -->
                <div class="col-sm-12">

                    <div class="row">
                        <!-- center left-->
                        <div class="col-md-12">

                            <!--</div>-->


                            <!--tabs-->
                            <div class="panel">
                                
                                <div class='btn-default'><h4 style='text-align:center;padding:10px;'><b>Daily ART (V 10.0.1)</b></h4></div>
                                
                                <ul class="nav nav-tabs " id="myTab">
                                    <li class="active newdata"><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-plus"></i>Daily ART Data</a></li>
                                 
                                    <!--<li class="active editdata" style='display:none;' ><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-edit"></i> Edit Data</a></li>-->
                                    <!--<li><a href="#reports"  style="" id="reportsbutton" data-toggle="tab"> <i class="glyphicon glyphicon-stats"></i> Reports</a></li>--> 
                                    <li><a href="importpns.jsp"  style="background-color:orange;color:white;" id="dailyartExcelbutton" > <i class="glyphicon glyphicon-backward"></i>Other Surge Reports</a></li> 
                                    <!--<li><a href="#searchdata" data-toggle="tab"> <i class="glyphicon glyphicon-search"></i> Edit Data</a></li>--> 
                                    <!-- <li><a href="#export" data-toggle="tab"> <i class="glyphicon glyphicon-cloud-upload"></i> Data Export</a></li>-->
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active well col-md-12" id="dataentry">
                                        <!--Data entry code-->
                                        <div class="panel panel-default">

                                            <div class="panel-body" style="width:100%;">
                                                <form class="form form-vertical"  action="#" method="post" id="weeklydataform">
                                                    <table class='table table-striped table-bordered'  style=" width:100%" >

                                                        


                                                        

<%
String sects="";

dbConn conn= new dbConn();

String qr="select GROUP_CONCAT(DISTINCT(category)) as Category from der_rri.dailyart_indicators;";

conn.rs=conn.st.executeQuery(qr);




while (conn.rs.next()){

sects=conn.rs.getString(1);

}

ArrayList ar= new ArrayList();

String sectsArr[]=sects.split(",");
String so="";
for(int a=0;a<sectsArr.length;a++){
    String selected="";
   
    if(!ar.contains(sectsArr[a])){
         ar.add(sectsArr[a]);
       // if(sectsArr[a].equals("5_CCC & PMTCT TB Screening (ICF)")){selected="selected";} 
         
so+="<option "+selected+"  value='"+sectsArr[a]+"'>"+sectsArr[a]+"</option>";
    }
}


%>



                      <!-------Data Management ---------->

                      
                      
                                                        <tr>

                                                            <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Reporting Date</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Service Delivery Point</label> 

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
                                                                        
                                                                         <input onchange="isdisplayindicators();" readonly data-date-end-date="0d" required type="text"  title="this is the reporting date"  class="form-control input-sm dates" name="period" id="period" autocomplete="off">                                         
                                                                      
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                     <select required="true"   onchange="isdisplayindicators();"   name="sdp" id="sdp" class="form-control" >
                                                                                       <%=so%>                            
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </td>


                                                            <td class="col-xs-4">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                     <select required="true"   onchange="isdisplayindicators();"   name="facility" id="facility" class="form-control" >
                                                                                                                   
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>




                                                    </table>
 <div class="accordion" id="form1a_accordion" >
                                            
                                            
                                            <!--__________________________________________________________________________________________________-->
                                            
                                          
                                                
     <label>Select Facility and Period to load the data entry form</label>                                     
                                         
                                            
                                            
                                            <!--__________________________________________________________________________________________________-->


                                                  

                                                </div>
                                                    
                                                     <table class="table table-striped table-bordered">
                                                        <tr><td colspan="3" class="col-xs-12">               
                                                                <div class="control-group col-xs-12">
                                                                    <div id='fedback' class="alert-info">Note: Please enter all the required data.</div>
                                                                    <br/>
                                                                    <div class="controls">
                                                                        <input readonly="true" type="input" onClick="validatedata();"  id='savebutton' value="SAVE"  style="margin-left: 0%;" class="btn-lg btn-success active">

                                                                    </div>
                                                                    <div class="controls">
                                                                        <button type="submit" id='updatebutton' onclick="updateweeklydata();" style="margin-left: 0%;display:none;" class="btn-lg btn-info active">
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
<form action="retreported" id="reportingForm">

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
                                                            <option value='retreported'>1.Reported Data</option>
                                                            <option value='retentiontracker'>2.missing and Submitted Reports</option>
                                                           
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
                        <form action="" id="userform" method="post">
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
                        <p>This  application is created for aiding in accounting for service gaps per facility.</p>
                        <h3>Indicators</h3>
                        <p>The specific indicators that one should account for are;</p>
                        <ul>


                            <li>HIV + Patients not linked to treatment</li>
                            <li>Index Testing contacts eligible for testing and not tested</li>
                            <li>CXCA_SCREEN Positive mothers not started or not treated</li>
                            <li>Accounting For Net Loss</li>
                            <li>STFs</li>
                        </ul>
                        <h3> Organization Units</h3>
                        <p> Submit data per facility </p>
                        <h3> Data edits</h3>
                        <p> Users can continuously update already submitted data for a specific period as more interventions happen</p>
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
        <script type="text/javascript" src="js/angular.js"></script>
        <script type="text/javascript" src="js/angularoptions_htsself.js"></script>
        <script type="text/javascript" src="dailyart/sum_values.js"></script>
        <script type="text/javascript" src="dailyart/validation.js"></script>



        <script type="text/javascript">


                                   $('.dates').datepicker({
                                       todayHighlight: true, clearBtn: true, autoclose: true, format: "yyyy-mm-dd"
                                   });



                                   $(document).ready(function () {
                                       $('dic').select2();
                                   });


                              
 function getFacilitiesJson(){
       
   
      
       
              $.ajax({
                    url:'loadAllActiveSites',                            
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




                                   function save_data() {
                                       
var facility_mfl_code="";
var period="";
var sdp="";
sdp=$("#sdp").val();  


var sdparr=sdp.split("_");

var sc=sdparr[0];


facility_mfl_code=$("#facility").val();
//organisationunitid=$("#facilityname").find(":selected").data('datimid');
//hosi=$("#facilityname").find(":selected").data("facil");

period=$("#period").val();        
      
        
        $.ajax({
                    url:'dailyart_getIndicators',                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data){
                        
                       for(a=0;a<data.length;a++){
              var isend=false;             
                          
var numericid=data[a].id;
var indicatorid=data[a].indicators_id;//has an underscore before the emenet id
var der_1f=$("#"+indicatorid+"_der_1f").val();
var der_1m=$("#"+indicatorid+"_der_1m").val();
var der_4f=$("#"+indicatorid+"_der_4f").val();
var der_4m=$("#"+indicatorid+"_der_4m").val();
var der_9f=$("#"+indicatorid+"_der_9f").val();
var der_9m=$("#"+indicatorid+"_der_9m").val();
var der_14f=$("#"+indicatorid+"_der_14f").val();
var der_14m=$("#"+indicatorid+"_der_14m").val();
var der_19f=$("#"+indicatorid+"_der_19f").val();
var der_19m=$("#"+indicatorid+"_der_19m").val();
var der_24f=$("#"+indicatorid+"_der_24f").val();
var der_24m=$("#"+indicatorid+"_der_24m").val();
var der_29f=$("#"+indicatorid+"_der_29f").val();
var der_29m=$("#"+indicatorid+"_der_29m").val();
var der_34f=$("#"+indicatorid+"_der_34f").val();
var der_34m=$("#"+indicatorid+"_der_34m").val();
var der_39f=$("#"+indicatorid+"_der_39f").val();
var der_39m=$("#"+indicatorid+"_der_39m").val();
var der_44f=$("#"+indicatorid+"_der_44f").val();
var der_44m=$("#"+indicatorid+"_der_44m").val();
var der_49f=$("#"+indicatorid+"_der_49f").val();
var der_49m=$("#"+indicatorid+"_der_49m").val();
var der_50f=$("#"+indicatorid+"_der_50f").val();
var der_50m=$("#"+indicatorid+"_der_50m").val();
var value=$("#"+indicatorid+"_value").val();

 

//var identifier=period+"_"+facility_mfl_code+"_"+indicatorid+"_"+sc;
       var identifier = period.replaceAll("-","_")+"_"+facility_mfl_code+indicatorid+"_"+sc;
    console.log("Id is"+identifier); 
      

        
           //save the data
           
           var saveddata={
               id:identifier,
               date:period,
               facility:facility_mfl_code,
               indicatorid:numericid,
               sdp:sc,
              der_1f:der_1f,
der_1m:der_1m,
der_4f:der_4f,
der_4m:der_4m,
der_9f:der_9f,
der_9m:der_9m,
der_14f:der_14f,
der_14m:der_14m,
der_19f:der_19f,
der_19m:der_19m,
der_24f:der_24f,
der_24m:der_24m,
der_29f:der_29f,
der_29m:der_29m,
der_34f:der_34f,
der_34m:der_34m,
der_39f:der_39f,
der_39m:der_39m,
der_44f:der_44f,
der_44m:der_44m,
der_49f:der_49f,
der_49m:der_49m,
der_50f:der_50f,
der_50m:der_50m,
value:value,

              userid:''              
           };
           
           
           
           
           if(a===parseInt(data.length)-1){
               isend=true;
               
           }
           //only save where there are values
           if(value!==""){
           exportData(saveddata,isend);
       }
                           
                       } 
                   
                    }
                });

}


 function exportData(data, isend, tbl,secid) {
$("#msg"+secid).html("<img style='height:150px;'  src='images/please_wait.gif'/>");

                                       $.ajax({
                                           url: 'saveDailyArt?tbl='+tbl,
                                           type: 'post',
                                           dataType: 'html',
                                           data: data,
                                           success: function (dat) {
                                               if (isend) {

                                                   console.log("Data saved Succesfully!");
                                                   $("#msg"+secid).html("<font color='green'><b>Data saved Succesfully!!!</b></font>");
                                              section_saved(secid);
                                         setTimeout(refreshujumbe,2000);
    
                                               }

                                           }
                                       });


                                   }

//call the function that displays the data

function refreshujumbe(){
    
    $(".feedback").html("");
    
}

//==================function to import data

// $('#exportbutton').on('click',function() {
                                   $("#exportbutton").prop("disabled", false);
                                   $(this).removeClass('btn-lg btn-default').addClass('btn btn-success');
//});



                                   function numbers(evt) {
                                       var charCode = (evt.which) ? evt.which : event.keyCode
                                       if (charCode > 31 && (charCode < 48 || charCode > 57))
                                           return false;
                                       return true;
                                   }

//Codes to prevent default form submission

                                   $("#userform").submit(function (e) {
                                       return false;
                                   });

                                   $("#weeklydataform").submit(function (e) {
                                       return true;
                                   });
                                   $("#exportdataform").submit(function (e) {
                                       return false;
                                   });

                                   $("#reportsform").submit(function (e) {
                                       return false;
                                   });

                                   $('input').css('border-color', '#337ab7');


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








                                   $('#dataentry').on('keydown', 'input, select, textarea', function (e) {
                                       var self = $(this)
                                               , form = self.parents('form:eq(0)')
                                               , focusable
                                               , next
                                               ;
                                       if (e.keyCode === 13) {
                                           focusable = form.find('input,a,select,button,textarea').filter(':visible');
                                           next = focusable.eq(focusable.index(this) + 1);
                                           if (next.length) {
                                               next.focus();
                                           } else {
                                               form.submit();
                                           }
                                           return false;
                                       }
                                   });

                                   function isdisplayindicators()
                                   {
                                       
                                     
            
       
                                       var ym = $("#period").val();
                                      
                                       var fc = $("#facility").val();
                                       
                                       var sdp="";
                                       sdp=$("#sdp").val(); 
//    console.log("_"+fc+"vs"+dt);
                                       if ( sdp !=='' && ym !== '' && fc !== 'Select facility' && fc !== ''&& fc !==null )
                                       {
                                           // display facility name
                                           
                                           
                                           
                                           $("#form1a_accordion").show();


 $("#form1a_accordion").html("<div style='text-align:center;'><img style='height:250px;'  src='images/please_wait.gif'/></div>");

console.log("facility:"+fc);
console.log("yearmonth:"+ym);
                                           //now load the data
                                           $.ajax({
                                               url: 'dailyart_getIndicators?ym=' + ym + "&fc=" + fc+"&sdp="+sdp,
                                               type: 'post',
                                               dataType: 'html',
                                               success: function (data)
                                               {

                                                   $("#form1a_accordion").html(data);


                                               }});



                                       } else
                                       {
                                           $("#form1a_accordion").hide();
                                           //        
                                       }


                                   }

Date.prototype.getWeekNumber = function(){
  var d = new Date(Date.UTC(this.getFullYear(), this.getMonth(), this.getDate()));
  var dayNum = d.getUTCDay() || 7;
  d.setUTCDate(d.getUTCDate() + 4 - dayNum);
  var yearStart = new Date(Date.UTC(d.getUTCFullYear(),0,1));
  return Math.ceil((((d - yearStart) / 86400000) + 1)/7)
};

function checkFormAction (){
    
  $('#reportingForm').attr('action', $("#report").val());  
    
}



function validatedata(){
    


//___indicators to pull___


var daterange="";
var hosi="";
var facility_mfl_code="";
var period="";

facility_mfl_code=$("#facility").val();
//organisationunitid=$("#facilityname").find(":selected").data('datimid');
//hosi=$("#facilityname").find(":selected").data("facil");

period=$("#period").val();
var sdp=$("#sdp").val();
//eddate=$("#reportingdate").val();
//stdate=$("#reportingdate").find(':selected').data('sdate');


//console.log("datimid:"+organisationunitid+"\n edate:"+eddate+"\n stdate:"+stdate+"\n daterange:"+daterange+"\n hospital:"+hosi+"\n mflcode"+facility_mfl_code+"\n ward"+ward);
//<option data-sdate="2019-09-24" data-edate="2019-09-30" data-wk="20" value="2019-09-30">2019-09-24 to 2019-09-30 (Week 20) </option>

//this should happen in a loop


if(facility_mfl_code==='' || facility_mfl_code==='Select facility' ){
    
  alert("enter facility name");  
    
}


else if(period===''  )
{
   alert("select reporting period");  
    
}

else if(sdp===''  )
{
   alert("select reporting Section");  
    
}
    
    else {
    loadValidation();
    //load validation will call save data


}





    
}


function exportData( data, isend){
    
    
    $.ajax({
                    url:'saveDailyArt',                            
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


 function showtoday() {



                                       var currentdate = new Date();

                                       var mn = "" + (currentdate.getMonth() + 1);
                                       var dt = "" + currentdate.getDate();
                                       var hr = "" + currentdate.getHours();
                                       var min = "" + currentdate.getMinutes();
                                       var sc = "" + currentdate.getSeconds();
                                       if (mn.length === 1) {
                                           mn = '0' + mn;
                                       }
                                       if (dt.length === 1) {
                                           dt = '0' + dt;
                                       }
                                       if (hr.length === 1) {
                                           hr = '0' + hr;
                                       }
                                       if (min.length === 1) {
                                           min = '0' + min;
                                       }
                                       if (sc.length === 1) {
                                           sc = '0' + sc;
                                       }


                                       var leo = "" + currentdate.getFullYear() + "-" + mn + "-" + dt;

                                       $("#period").val(leo);

                                       return leo;
                                   }
                                   showtoday();

        </script>

    </body>
</html>
