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
        <title>UTJ Stocks Receipt Verification</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/select2.min.css">
        <link rel="shortcut icon" href="images/stocks_.png">
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
                    margin-left:20%;
                    margin-right:20%;
                }
            }

            @media screen and (min-width: 1200px) {
                #weeklydataform {
                    margin-left:20%;
                    margin-right:20%;
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

</style>

    </head>
    <body >
        <!-- header -->
        <div id="top-nav" class="navbar navbar-inverse navbar-static-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <!--<button id="toolid" style="float:left;color:white; text-align: center;" class="navbar-toggle btn btn-default" ></button>-->
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
                        <li><a  class=''  href='stock_index.jsp'><i class='glyphicon glyphicon-log-out'></i> Log out</a></li>
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
                        <div style="text-align: center;" class=" well col-md-12">USAID Tujenge Jamii Stocks Receipt verification</div>
                        
                        <!-- center left-->
                        <div class="col-md-12">

                            <!--</div>-->


                            <!--tabs-->
                            <div class="panel">
                                <ul class="nav nav-tabs " id="myTab">
                                    <li class="active newdata"><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-plus"></i>Data Entry</a></li>
                                  <li><a href="#searchdata" data-toggle="tab"> <i class="glyphicon glyphicon-search"></i> Edit Data</a></li> 
                                    
                                    <!--<li class="active editdata" style='display:none;' ><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-edit"></i> Edit Data</a></li>-->
                                    <li><a href="#reports"   id="reportsbutton" data-toggle="tab"> <i class="glyphicon glyphicon-stats"></i> Reports</a></li> 
                                   <!-- <li><a href="#export" data-toggle="tab"> <i class="glyphicon glyphicon-cloud-upload"></i> Data Export</a></li>-->
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active well col-md-12" id="dataentry">
                                        <!--Data entry code-->
                                        <div class="panel panel-default">

                                            <div class="panel-body" style="width:100%;">
                                                <form class="form form-vertical"  action="#" method="post" id="weeklydataform">
                                                    <table class='table table-striped table-bordered'  style=" width:100%" >

                                                        


                                                        





                      <!-------Data Management ---------->

                      
                      
                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Verification Date</label> 

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
                                                                        <input onchange="isdisplayindicators();" readonly data-date-end-date="0d" required type="text"  title="this is the reporting date"  class="form-control input-sm dates" name="reportingdate" id="reportingdate" autocomplete="off">
                                                                        <input type="hidden"  name ="rowid" id="rowid"  />
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

                                                        
                                                        
        <!--________________________________________________________________________________________________________________________-->
        
        
<!--        Name of Facility	Barwessa Health Centre
Commodity Description	TENOF/LAMIV/DOLUTEG 300/300/50MG TABLETS
Pack Size	90'S
Delivery note number	80052627
Batch Number	DV5020365-A
Delivery note Quantity	14
Quantity Received	14
Date Received 	
Contacts	0713-526970
Expiry date	30.11.2022
Comments	1st supply,15/7/2021

commodity
packsize
delnoteno
batchno
delnoteqty
qtyrec
datereceived
contacts
expdate
cmts

-->
 
<!___________________________________________________Delivery Note Number______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Delivery Note Number</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        
                                                                    <input type="text" onblur="CheckDuplicateNoteNumber();" required="true"     name="delnoteno" id="delnoteno" class="form-control input-sm" >
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>
                                                        
                          <!___________________________________________________Document date______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Document Date</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <input type="text" required="true" readonly data-date-end-date="0d"     name="docdate" id="docdate" class="form-control input-sm dates" >
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>    

        <!___________________________________________________Commodity Description______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6" colspan='2'>
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Commodity Description</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td class="col-xs-6" colspan="2">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                     <select required="true"   onchange="setPackSize();"   name="commodity" id="commodity" class="form-control" >
                                                                                                                   
                                                                     </select>
                                                                        <input type="hidden" name='id' id='id'>
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>

   <!___________________________________________________Pack size______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Pack Size</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                     <select required="true" readonly   onchange=""   name="packsize" id="packsize" class="form-control" >
                                                                                                                   
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>                                                     
                                                        

 <!___________________________________________________Batch Number______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Batch Number</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                     <input type="text" required="true"     name="batchno" id="batchno" class="form-control input-sm" >
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>                                                       
                                                        

                                                        
                                                        
                                                                                                                
 <!___________________________________________________Delivery Note Quantity______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Delivery Note Quantity</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <input type="number" required="true" onkeypress='return numbers(event);'     name="delnoteqty" id="delnoteqty" class="form-control input-sm" >
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>  
 
 <!___________________________________________________Quantity Received______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Quantity received</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <input type="number" required="true" onkeypress='return numbers(event);'    name="qtyrec" id="qtyrec" class="form-control" >
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>     
                                                        
                               
                                                        
                                                       
                                                        
                                                    
                                                         <!___________________________________________________Expiry Date______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Expiry date</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <input type="text" required="true" readonly   name="expdate" id="expdate" class="form-control input-sm dates" >
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>
                                                  
                                                          <!___________________________________________________Date Received______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Date received</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <input type="text" required="true" readonly data-date-end-date="0d"     name="daterec" id="daterec" class="form-control input-sm dates" >
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>
                                                        
                                                        
                                                            <!___________________________________________________Entry by ______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Received by</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <input type="text" required="true"    name="contact_name" id="contact_name" class="form-control input-sm" >
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>
                                                        
                                                        <!___________________________________________________Contacts______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Contacts</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <input type="tel" required="true" placeholder='eg 0712 111 222'   name="contacts" id="contacts" class="form-control input-sm" >
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>
                                                        
                                                        
                                                         <!___________________________________________________ Comments ______________________________________________________________________________________>

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label>Comments</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <textarea  required="true"    name="cmts" id="cmts" class="form-control input-sm" ></textarea>
                                                                                                                   
                                                                        
                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>
                                                        <tr>
                                                            <td colspan='2'><label onclick="save_data();" style='background-color:#66ee66 ; width:90px ;' class='btn btn-green btn-lg'>Save</label><label id='ujumbe'></label></td>
                                                        </tr>
                                                        
                                                    </table>
                                   
                                            
                                            
<!--commodity
packsize
delnoteno
batchno
delnoteqty
qtyrec
daterec
contacts
expdate
cmts

-->
                                            
                                          
                                                
                                        
                                         
                                            
                                            
                                    <!--__________________________________________________________________________________________________-->


                                                  

                                   </div>
                                                   
                                                </form>
                                            </div>
                                            <!--/panel content-->
                                        </div>
                                        <!--/panel-->


                                        <!--Data entry code-->

                                        
                                         <div class="tab-pane well" id="searchdata">

                                        <div id="edittable">

                                        </div>    
                                        <!--- Data export---->
                                    </div>
                                

                                    <div  class="tab-pane well" id="reports">
<form action="commoditiesreported" id="reportingForm">

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
                                                            
                                                            <option value='commoditiesreported'>1.Submitted Data</option>
                                                           
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
                                        
                                    </div>
                             
                                
                                        
                                    <div class="tab-pane well" id="export">


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
        <script type="text/javascript" src="gaps/sum_values.js"></script>
        <script type="text/javascript" src="gaps/validation.js"></script>



        <script type="text/javascript">


                                   $('.dates').datepicker({
                                       todayHighlight: true, clearBtn: true, autoclose: true, format: "yyyy-mm-dd"
                                   });



                                   $(document).ready(function () {
                                       $('dic').select2();
                                   });


                              
 function getFacilitiesJson(){       
   
       var ym=$("#period").val();
       
              $.ajax({
                    url:'loadStockSites',                            
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


 function getSections(){
       
   
       
       
              $.ajax({
                         url:'getParameterData',                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) {                        
                       
        var dat=data.sections;
        
        console.log(dat[0].section);
        var o="<option value=''>Select Option</option>";
                        
                        for(var a=0;a<dat.length;a++){
                            
                     
                          o+="<option value='"+dat[a].sectio_id+"'>"+dat[a].sectio+"</option>";   
                        }
                        
                   $("#section").html(o);
                   $(document).ready(function() {
                    $('#section').select2(); 
             
                                 } ); 
                        
                        
                    }});
   
   }

//getSections();


function CheckDuplicateNoteNumber(){
    var delno=$("#delnoteno").val();
    var com=$("#commodity").val();
    var fac=$("#facility").val();
    $.ajax({
   url:'checkNoteDuplicates?com='+com+"&delnote="+delno,                            
   type:'post',  
  dataType: 'json',  
  success: function(data) 
 {
 
 if(data.duplicate==='yes'){
  //go back to same data entry field
     
  $("#delnoteno").focus();
     
  $('#delnoteno').css('border-color','#ff0000');
  $('#ujumbe').html("<font color='red'>Delivery note has already been used</font>");
  setTimeout(refreshujumbe,2000);   
 }
 else 
 {     
  $('input').css('border-color','#337ab7'); 
 }
 
 }
    });
}



 function getCommodities(){
       
   var com=$("#commodity").val();
       
       
              $.ajax({
                         url:'loadComodities?com='+com,                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) 
                    {                      
                        
                       
        var dat=data;
        
      
        var o="<option value=''>Select Option</option>";
                        
                        for(var a=0;a<dat.length;a++)
                        {                           
                     
                          o+="<option data-com='"+dat[a].commodity+"' data-packs='"+dat[a].packs+"' value='"+dat[a].id+"'>"+dat[a].code+" , "+dat[a].packs+"</option>";   
                        }
                        
                   $("#commodity").html(o);
                   $(document).ready(function() {
                    $('#commodity').select2(); 
             
                                 } ); 
                        
                        
                    }});
   
   }
   
   getCommodities();
   
   function setPackSize(){
      
       
      var pack= $("#commodity").find(':selected').data("packs");
      
      var com= $("#commodity").find(':selected').data("com");
      
      var opt="<option value='"+pack+"'>"+pack+"</option>";
      
   $("#packsize").html(opt);
       
       
   }
   
   
 function getEnteredData(){
       
   var sec=$("#section").val();
       
       
              $.ajax({
                    url:'loadSavedData',                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) {                        
                       
        var dat=data.commodity;        
      console.log(dat);
      console.log(dat.length);
        var tbl="<table id='searchtable' class='table table-striped table-bordered' border='1px' ><thead><tr><th>Stock Details</th><th>Facility</th><th>Edit</th></tr></thead><tbody>";
                        
                        for(var a=0;a<dat.length;a++)
                        {                           
                     
                          tbl+="<tr><td>Commodity: <b>"+dat[a].commodity+"</b><br/>Delivery Note: <b>"+dat[a].delnoteno+"</b><br/>Batch no: <b>"+dat[a].batchno+"</b><br/>Quantity Received: <b>"+dat[a].qtyrec+"</b><br/>Entered By: <b>"+dat[a].contact_name+"</b><br/>Verification date: <b>"+dat[a].reportingdate+"</b><br/></td><td>"+dat[a].facilityname+"<br/></td><td><label style='background-color:#37acff;' class='btn btn-info' onclick='loadForm(\""+dat[a].id+"\")' ><b>Edit</b></label></td></tr>";   
                        }
                        tbl+="</tbody></table>"
                        
                   $("#edittable").html(tbl);
                   
                    
        
     
    var table = $('#searchtable').DataTable({"autoWidth": true,
              "paging": true,
              "pagingType": "full",
              "lengthChange": false,  
              "order": [[0,'desc']]});
 
    $('#searchtable tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
             $('#btnDeleteRow').hide();
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
             $('#btnDeleteRow').show();
        }
    } );
 
    $('#btnDeleteRow').click( function () {
        
     var tablerowid=table.$('tr.selected').attr('id');
        
        if(ConfirmDelete()===true){
        
        deletedata(tablerowid);
        
        table.row('.selected').remove().draw( false );
        
        
                        
                    }});
   
   }
   
   });
   
 }
getEnteredData();




function loadForm(formid){
 
        
              $.ajax({
                         url:'loadSavedData?id='+formid,                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) 
                    {
                        
                    var dat=data.commodity;        
      console.log(dat);
$("#newdatabutton").click();
 $('#reportingdate').val(dat[0].reportingdate);                  
 $('#id').val(dat[0].id);                  
$('#commodity').val(dat[0].commodity);
$('#commodity').change();
$('#packsize').val(dat[0].packsize);
$('#delnoteno').val(dat[0].delnoteno);
$('#batchno').val(dat[0].batchno);
$('#delnoteqty').val(dat[0].delnoteqty);
$('#qtyrec').val(dat[0].qtyrec);
$('#daterec').val(dat[0].daterec);
$('#contacts').val(dat[0].contacts);
$('#expdate').val(dat[0].expdate);
$('#cmts').val(dat[0].cmts);
$('#facility').val(dat[0].facility);
$('#docdate').val(dat[0].docdate);
$('#contact_name').val(dat[0].contact_name);


                 $("#facility").select2();  
                 $("#commodity").select2();  
                   
        
                    }
                    } );                      
                       
    
    
    
}







function save_data() 
{
    
var id="";
var commodity="";
var packsize="";
var delnoteno="";
var batchno="";
var delnoteqty="";
var qtyrec="";
var daterec="";
var contacts="";
var expdate="";
var cmts="";
var facility="";
var docdate="";
var contact_name="";
var reportingdate="";


commodity=$('#commodity').val();
packsize=$('#packsize').val();
delnoteno=$('#delnoteno').val();
batchno=$('#batchno').val();
delnoteqty=$('#delnoteqty').val();
qtyrec=$('#qtyrec').val();
daterec=$('#daterec').val();
contacts=$('#contacts').val();
expdate=$('#expdate').val();
cmts=$('#cmts').val();
facility=$('#facility').val();
docdate=$('#docdate').val();
contact_name=$('#contact_name').val();
reportingdate=$('#reportingdate').val();

if($("#id").val()!=='')
{
id=$("#id").val(); 
}
else 
{    
    id=commodity+"_"+facility+"_"+delnoteno; 
}
                                      
         if (commodity ==='' || commodity === 'Select Option') { alert("Select Commodity");  $('#commodity').click();} 
    else if (facility === '' || facility === 'Select facility') { alert("Select Facility");    } 
    else if (delnoteno ==='') { alert("enter Delivery note number"); $('#delnoteno').focus();  } 
    else if (batchno === '') { alert("enter batch number");  $('#batchno').focus();}
    else if (delnoteqty === '') { alert("Enter Delivery note quantity"); $('#delnoteqty').focus(); } 
    else if (qtyrec === '') { alert("Enter Quantity received"); $('#qtyrec').focus(); }
    else if (daterec === '') { alert("Enter expiry Date"); $('#daterec').focus(); }
    else if (expdate === '') { alert("Enter Date received"); $('#expdate').focus(); }    
    else if (contact_name === '') { alert("Enter Your name"); $('#contact_name').focus(); }    
    else if (reportingdate === '') { alert("Enter Verfication date"); $('#reportingdate').focus(); }    
        else {
            
            var saveddata = {
id:id,
commodity:commodity,
packsize:packsize,
delnoteno:delnoteno,
batchno:batchno,
delnoteqty:delnoteqty,
qtyrec:qtyrec,
daterec:daterec,
contacts:contacts,
expdate:expdate,
cmts:cmts,
facility:facility,
contact_name:contact_name,
reportingdate:reportingdate,
docdate:docdate};
            
   $.ajax({url: 'saveStockData',
           type: 'post',
           dataType: 'html',
           data:saveddata,
           success: function (data) {
            if (1===1) 
            {               
                
         $('#ujumbe').html(data); 
         setTimeout(refreshujumbe,3000); 

                                                   }

                                               }
                                           });



                                       }






                                   }


                                   function exportData(data, isend, tbl,secid) {


                                       $.ajax({
                                           url: 'saveGapsData?tbl='+tbl,
                                           type: 'post',
                                           dataType: 'html',
                                           data: data,
                                           success: function (dat) {
                                               if (isend) {

                                                   console.log("Data saved Succesfully!");
                                                   $("#msg"+secid).html("<font color='green'><b>Data saved Succesfully!!!</b></font>");
                                              section_saved(secid);
                                         setTimeout(refreshujumbeandreload,2000);
    
                                               }

                                           }
                                       });


                                   }

//call the function that displays the data

function refreshujumbe(){
    
    $("#ujumbe").html("");
    
}
function refreshujumbeandreload(){
    
    $("#ujumbe").html("");
    window.location.reload();
    
}



function clearentryfields()
{
   // $("#facilityname").val("");
   //No facility name should have an underscore since its a special key
   
//$("#startdate").val("");   
$('#commodity').val("");
$('#packsize').val("");
$('#delnoteno').val("");
$('#batchno').val("");
$('#delnoteqty').val("");
$('#qtyrec').val("");
$('#daterec').val("");
$('#contacts').val("");
$('#expdate').val("");
$('#cmts').val("");
$('#facility').val("");
$('#docdate').val("");
$('#contact_name').val("");
$('#reportingdate').val("");
$('#id').val("");

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
                                       
                                     
            
       
                                       var ym = $("#reportingdate").val();
                                      
                                       var fc = $("#facility").val();
//    console.log("_"+fc+"vs"+dt);
                                       if ( ym !== '' && fc !== 'Select facility' && fc !== '')
                                       {
                                           // display facility name
                                           $("#form1a_accordion").show();


                                           //now load the data
                                           $.ajax({
                                               url: 'loadindicators?ym=' + ym + "&fc=" + fc,
                                               type: 'post',
                                               dataType: 'html',
                                               success: function (data)
                                               {

                                                  // $("#form1a_accordion").html(data);


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

        </script>

    </body>
</html>
