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

<!--<html  manifest="cohortsv1.appcache">-->
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>KP Monthly Form</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
          <link rel="stylesheet" href="css/progress_bar.css">
        <link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/select2.min.css">
        <link rel="shortcut icon" href="Login_v6/images/kp.png">
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
            td {
               padding:5px; 
                
            }

            @media screen and (min-width: 600px) and (max-width: 1199px)  {
                #weeklydataform {
                    margin-left:10%;
                    margin-right:10%;
                }
            }

            @media screen and (min-width: 1200px) {
                #weeklydataform {
                    margin-left:20%;
                    margin-right:20%;
                }
            }

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

    </head>
    <body >
        <!-- header -->
        <div id="top-nav" class="navbar navbar-inverse navbar-static-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button id="toolid" style="float:left;color:white; text-align: center;" class="navbar-toggle btn btn-default" >&nbsp; KeyPop Data</button>
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
                             
                              <li><a style="text-align: center;" href='kp_home.jsp'><i class="glyphicon glyphicon-stats"></i>Home</a></li>
                              <li><a style="text-align: center;" href='kp_index.jsp'><i class="glyphicon glyphicon-log-out"></i> Logout</a></li>
            </ul>
        </div>

            </div>
            <!-- /container -->

        </div>
        <!-- /Header -->

        <!-- Main -->
        <div ng-app="kpapp" ng-controller="kpcont" class="container-fluid">

            <div class="row">

            </div>
            <div class="row">

                <!-- /col-3 -->
                <div class="col-sm-12">

                    <div class="row">
                        <!-- center left-->
                        <div class="col-md-12">




                            <!--tabs-->
                            <div class="panel">
                                <ul class="nav nav-tabs " id="myTab">
                                    <!--<li class="newdata"><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-plus"></i> Daily Form</a></li>-->
                                    <li ><a href="#monthlyform" id="monthlyformbtn" data-toggle="tab">  <i class="glyphicon glyphicon-download"></i>  Download KP Form</a></li>
                                    <li class="active"><a href="#monthlyformupload" id="monthlyformuploadbtn" data-toggle="tab">  <i class="glyphicon glyphicon-Upload"></i> Upload KP Form</a></li>
                                    <!--<li class="active editdata" style='display:none;' ><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-edit"></i> Edit Data</a></li>-->
                                    <li><a href="#reports"   id="reportsbutton" data-toggle="tab"> <i class="glyphicon glyphicon-stats"></i> Reports</a></li> 
                                    <!--<li><a href="#searchdata" data-toggle="tab"> <i class="glyphicon glyphicon-search"></i> Edit Data</a></li>--> 
                                    <!-- <li><a href="#export" data-toggle="tab"> <i class="glyphicon glyphicon-cloud-upload"></i> Data Export</a></li>-->
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane  well col-md-12" id="dataentry">
                                        <!--Data entry code-->
                                        <div class="panel panel-default">

                                            <div class="panel-body" style="width:100%;">
                                                <form class="form form-vertical"  action="#" method="post" id="weeklydataform">
                                                    <table class='table table-striped table-bordered'  style=" width:100%" >

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option> LIP </label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option> Reporting Date </label> 

                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>


                                                        <tr >

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
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


                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <input onchange="isdisplayindicators();" readonly data-date-end-date="0d" required type="text"  title="this is the reporting date"  class="form-control input-sm dates" name="reportingdate" id="reportingdate" autocomplete="off">
                                                                        <input type="hidden"  name ="rowid" id="rowid"  />
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>








                                                        <tr>
                                                            <td class="col-xs-12" colspan="2">

                                                                <div class="control-group">
                                                                    <label> <required-option></required-option>  DIC Name:</label>
                                                                    <div class="controls">
                                                                        <select required="true"  onchange="isdisplayindicators();"   name="dic" id="dic" class="form-control" >
                                                                            <option>Select DIC Name</option>

                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </td>

                                                        </tr>



                                                    </table>
                                                    <table class='table table-striped table-bordered' id="dynamicindicators" style="display:none;" > 

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
                                                                        <input type="input" onClick="validatedata();"  id='savebutton' value="SAVE"  style="margin-left: 0%;" class="btn-lg btn-success active">

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
                                    <div class="tab-pane well" id="monthlyform">
                                        
                                        <form action='getKPForm' method='post' class='form form-group'>

                                        <h5 class='well well-lg' style="text-align:center ; background-color: #449d44; padding:4px;color:white;"> 
                                            <b>Download Monthly Data Collection Form </b>
                                        </h5>
                                        <!--- Data export---->

                                        <table>
                                            <tr><td><b>Reporting Year:</b> <required-option></required-option> </td>
                                                <td><select required ng-model='year' style='padding-bottom:5px ;' id="year" name="year" class="form-control">
                                                        <option value=''>select year</option>
                                                        <option ng-repeat="mia in miaka" value='{{mia}}'>{{mia}}</option>

                                                    </select>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td><b>Reporting Month : <required-option></required-option></b> </td>
                                                <td><select required id="months" name="months" class="form-control">
                                                        <option value=''>select Month</option>

                                                        <option ng-repeat="mie in months" value='{{mie.id}}'>{{mie.val+" "+ ( year - mie.yearoffset )}}</option>

                                                    </select>
                                                </td>
                                            </tr>
                                            
                                            <tr>
                                                <td><b>DIC Name: <required-option></required-option></b></td>
                                                <td>
                                                <select required  multiple="true" class='form-control'  id='dic_name' name='dic_name'>
                                                <option value=''>Select DIC</option>
                                                </select>
                                                </td>
                                            </tr>
                                        
                                            <tr>
                                                <td></td>
                                            <td><input type='submit' value='Download Form' class='btn btn-success'></td>
                                            </tr>
                                        </table>
                                        
                                        </form>
                                    </div>
                            <!-----------------Monthly Form Upload------------------->                                            
                 <div class="tab-pane active well" id="monthlyformupload">
                  <div  class="portlet-body form" id="progress_area" >
                      
                     ...
                     <div class="progress"  style="height: 50px;">
                     <div class="progress-bar progress-bar-striped active" id="progess" role="progressbar" style="width: 10%;  padding-top: 5px; font-weight: 900;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                     </div>   
                  </div>
                          
                     <div id="upload_area">
                             <form action="uploadMonthlyForm" id="kpform" method="post" enctype="multipart/form-data" class="portlet-body form">
                   
                                 <hr>
                           <div  class="control-group col-xs-12" >
                              <label class="control-label col-xs-2">Select Form <required-option></required-option></label>
                              <div class="controls col-xs-10">
                                 <input onMouseOver='checksession();' accept=".xlsx" required type="file" name="file_name" multiple="true" id="upload" value="" class="textbox" required> 
                              </div>
                           </div> 
                        
                        
                           <div  class="control-group col-xs-12" >
                              <label class="control-label col-xs-6"></label>
                              <div class="controls col-xs-6">
                                  <button type="submit" id="run_upload" class="btn btn-success">Upload</button>
                              </div>
                           </div>
                                 
                          <br/> 
                          <br/> 
                      <hr/>      
                           <div class="form-actions">
                         
<h5 id="ujumbe" style="color:green;margin-left: 0px;font-family: sans-serif;font"></h5>
                           </div>
                        </form> 
                               </div>
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
                          <thead> <tr> <th>County</th><th>Sub County</th><th>DIC</th><th>DIC Code</th><th>Calendar Year</th><th>Month</th><th>Year-Month</th><th>Program</th><th>Message</th><th>Age Group</th></tr></thead>
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

                               <div  class="tab-pane well" id="reports">
<form action="kp_monthly_report" id="reportingForm">

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
                                                            
                                                            <option value='kp_monthly_report'>1.Submitted Data</option>
                                                           
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
                                            <td class="col-xs-4">
                                                <div class="control-group">

                                                    <div class="controls">
                                                        <label ><b>Lip:</b><font color='red'><b>*</b></font></label>

                                                    </div> </div>
                                            </td>
                                            <td class="col-xs-4">
                                                <div class="controls">
                                                <select required="true"   onchange=""   name="liprpt" id="liprpt" class="form-control" >
                                                                            <%

                                                                                if (session.getAttribute("liplist") != null) {

                                                                                    out.println(session.getAttribute("liplist").toString());
                                                                                } else {
                                                                                    out.println("<option value=''>login to select LIP</option>");
                                                                                }
                                                                            %>                                          
                                                                        </select>
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
                                        
                                    </div
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
                        <p>This  application is created for aiding Local Implementing partners in collecting daily and Monthly KP data.</p>
                        <h3>Indicators</h3>
                        <p>The specific indicators that one should enter data for are;</p>
                        <ul>


                            <li>HTS_TST_INDEX</li>
                            <li>HTS_TST_MOBILE</li>
                            <li>HTS_TST_VCT</li>
                            <li>HTS_TST_OTHER</li>
                            <li>HTS_SELF</li>
                            <li>HTS_POS_INDEX</li>
                            <li>HTS_POS_MOBILE</li>
                            <li>HTS_POS_VCT</li>
                            <li>HTS_POS_OTHER</li>
                            <li>PREP_NEW</li>





                        </ul>
                        <h3> Organization Units</h3>
                        <p> Submit data per dice </p>
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
        <script type="text/javascript" src="js/angularoptions.js"></script>



        <script type="text/javascript">


                                   $('.dates').datepicker({
                                       todayHighlight: true, clearBtn: true, autoclose: true, format: "yyyy-mm-dd"
                                   });



                                   $(document).ready(function () {
                                       $('dic').select2();
                                   });


                                   function getDicsJson() {

                                       var lip = $("#lip").val();

                                      var dicoption="<option data-ward_name='' data-ward_id='' data-supported_kp='' value=''>select dic</option>";
                                       $.ajax({
                                           url: 'getDics?lip=' + lip,
                                           type: 'post',
                                           dataType: 'html',
                                           success: function (data) {
                                               $("#dic").html(dicoption+data);
                                               $("#dic_name").html(data);
                                                var select = document.getElementById('dic_name');
                                                   select.size = select.length;
                                               $(document).ready(function () {

                                                   $('#dic').select2();

                                               });


                                           }});

                                   }



                                   getDicsJson();









//=========================================Run validation================================

                                   function runvalidation() {

                                       var retv = true;

                                       //hts tsts > HTS Pos

                                       //HTS Pos > HTS Link

                                       //HTS_TST
                                       //HTS_TST_POS
                                       //TX_LINK

                                       var validationsid = ["HTS_TST_INDEX@HTS_POS_INDEX", "HTS_TST_MOBILE@HTS_POS_MOBILE", "HTS_TST_VCT@HTS_POS_VCT", "HTS_TST_OTHER@HTS_POS_OTHER"];

                                       var agedis = ["fsw","msm"];
                                       var agedis_detailed = ["Total"];

                                       for (var b = 0; b < validationsid.length; b++) {
                                           for (var a = 0; a < agedis.length; a++)
                                           {

                                               var indicab = validationsid[b].split("@");



                                               var elem_a = $("#" + indicab[0] + "_" + agedis[a]).val();
                                               var elem_b = $("#" + indicab[1] + "_" + agedis[a]).val();
                                               console.log("#" + indicab[0] + "_" + agedis[a] + " is " + elem_a);
                                               console.log("#" + indicab[1] + "_" + agedis[a] + " is " + elem_b);


                                               if (elem_a === "") {
                                                   elem_a = 0;
                                               }
                                               if (elem_b === "") {
                                                   elem_b = 0;
                                               }

                                               if (elem_a !== "" && elem_b !== "") {

                                                   elem_a = parseInt(elem_a);
                                                   elem_b = parseInt(elem_b);

                                                   if (elem_b > elem_a) {
                                                       retv = false;

                                                       alert(" Data for " + indicab[1] + " " + agedis_detailed[a] + " cannot be more than " + indicab[0] + " " + agedis_detailed[a]);
                                                       $("#" + indicab[0] + "_" + agedis[a]).css('background-color', 'red');
                                                       $("#" + indicab[1] + "_" + agedis[a]).css('background-color', 'red');
                                                       break;
                                                   }



                                               }

                                           }

                                           if (retv === false)
                                           {

                                               break;

                                           }
                                       }

                                       return retv;
                                   }









                                   function validatedata() {



//___indicators to pull___

                                       var organisationunitid = "";
                                       var eddate = "";
                                       var stdate = "";
                                       var daterange = "";
                                       var dic = "";

                                       var ward = "";
                                       var ward_name = "";


//dic=$("#dic").find(":selected").data('datimid');
                                       dic = $("#dic").val();
                                       ward = $("#dic").find(":selected").data("ward_id");
                                       ward_name = $("#dic").find(":selected").data("ward_name");
                                       eddate = $("#reportingdate").val();


//this should happen in a loop


                                       if (dic === '' || dic === 'Select dic') {

                                           alert("enter DIC");

                                       } else if (eddate === '') {

                                           alert("enter reporting date");

                                       } else if (runvalidation() === false)
                                       {




                                       } else {




                                           $.ajax({
                                               url: 'getKPIndicators',
                                               type: 'post',
                                               dataType: 'json',
                                               success: function (data) {

                                                   for (a = 0; a < data.length; a++) {
                                                       var isend = false;

                                                       var indicatorid = data[a].id;
                                                       var msm = $("#" + indicatorid + "_msm").val();
                                                       var fsw = $("#" + indicatorid + "_fsw").val();

                                                       var identifier = dic + "_" + eddate + "_" + indicatorid;


                                                       //save the data

                                                       var saveddata = {
                                                           id: identifier,
                                                           ward: ward,
                                                           date: eddate,
                                                           dic: "" + dic,
                                                           indicator: indicatorid,
                                                           fsw: fsw,
                                                           msm: msm

                                                       };




                                                       if (a === parseInt(data.length) - 1) {
                                                           isend = true;

                                                       }

                                                       exportData(saveddata, isend);

                                                   }

                                               }
                                           });



                                       }






                                   }


                                   function exportData(data, isend) {


                                       $.ajax({
                                           url: 'saveKpdaily',
                                           type: 'post',
                                           dataType: 'html',
                                           data: data,
                                           success: function (dat) {
                                               if (isend) {

                                                   console.log("Data saved Succesfully!");
                                                   $("#fedback").html("<font color='green'><h3>Data saved Succesfully!</h3></f>");
                                           setTimeout(refreshujumbe,2000);
                                  
                                               }

                                           }
                                       });


                                   }

//call the function that displays the data


//==================function to import data

// $('#exportbutton').on('click',function() {
                                   $("#exportbutton").prop("disabled", false);
                                   $(this).removeClass('btn-lg btn-default').addClass('btn btn-success');
//});

function refreshujumbe(){
    
  $("#fedback").html("");   
    
}


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

                                       $("#reportingdate").val(leo);

                                       return leo;
                                   }
                                   showtoday();









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
                                       var dt = $("#reportingdate").val();

                                       var fc = $("#dic").val().trim();
//    console.log("_"+fc+"vs"+dt);
                                       if (dt !== '' && fc !== 'Select dic' && fc !== '')
                                       {
                                           // display facility name
                                           $("#dynamicindicators").show();


                                           //now load the data
                                           $.ajax({
                                               url: 'getKPIndicators?dt=' + dt + "&fc=" + fc,
                                               type: 'post',
                                               dataType: 'html',
                                               success: function (data)
                                               {

                                                   $("#dynamicindicators").html(data);


                                               }});



                                       } else
                                       {
                                           $("#dynamicindicators").hide();
                                           //        
                                       }


                                   }



        </script>

        
   
  
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
         
    $("#kpform").submit(function(){
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
        url:'check_status?load_type=kpform1a',
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
     
     
     
     function checkFormAction (){
    
  $('#reportingForm').attr('action', $("#report").val());  
    
}
     
</script>
 
     
        
        
    </body>
</html>
