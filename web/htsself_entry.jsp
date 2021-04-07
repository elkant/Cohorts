<%-- 
    Document   : index
    Created on : Mar 17, 2016, 3:17:19 PM
    Author     : Emmanuel E
--%>

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
        <title>HTS Self</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/select2.min.css">
        <link rel="shortcut icon" href="images/htsself.png">
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
                    margin-left:20%;
                    margin-right:20%;
                }
            }

            @media screen and (min-width: 1200px) {
                #weeklydataform {
                    margin-left:30%;
                    margin-right:30%;
                }
            }


        </style>

    </head>
    <body >
        <!-- header -->
        <div id="top-nav" class="navbar navbar-inverse navbar-static-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button id="toolid" style="float:left;color:white; text-align: center;" class="navbar-toggle btn btn-default" >&nbsp;Monthly HTS Self</button>
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
                        <li><a  class=''  href='htsself_index.jsp'><i class='glyphicon glyphicon-log-out'></i> Log out</a></li>
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
                                <ul class="nav nav-tabs " id="myTab">
                                    <li class="active newdata"><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-plus"></i> Monthly Data</a></li>
                                 
                                    <!--<li class="active editdata" style='display:none;' ><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-edit"></i> Edit Data</a></li>-->
                                    <li><a href="#reports"  style="display:none;" id="reportsbutton" data-toggle="tab"> <i class="glyphicon glyphicon-stats"></i> Report</a></li> 
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

                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Section</label> 

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Modality</label> 

                                                                    </div>
                                                                </div>
                                                            </td>


                                                        </tr>


                                                        <tr >

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <select required="true"   onchange="getModalities();"   name="section" id="section" class="form-control" >
                                                                                                                   
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </td>


                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                     <select required="true"   onchange="isdisplayindicators();"   name="modality" id="modality" class="form-control" >
                                                                                                                   
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>





                      <!-------Data Management ---------->

                      
                      
                                                        <tr>

                                                            <td class="col-xs-6">
                                                                <div class="control-group">

                                                                    <div class="controls">
                                                                        <label><required-option></required-option>Period</label> 

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
                                                                        <select required="true"   onchange="isdisplayindicators();"   name="period" id="period" class="form-control" >
                                                                                                                   
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
                             

                                    <div class="tab-pane well" id="reports">


                                        <!--Dashboard code-->




                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4>Dashboard</h4></div>
                                            <div class="panel-body">
                                                <form id="reportsform">
                                                    <table class='table table-striped table-bordered' id="reportstable" >

                                                    </table>
                                                </form>
                                            </div>
                                            <!--/panel-body-->
                                        </div>
                                        <!--/panel-->



                                        <!--Reports entry code-->


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

        <footer class="text-center"> &copy; Afya Nyota Ya Bonde | USAID </footer>

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
                        <p>This  application is created for aiding in collection of monthly data.</p>
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
                        <p> Submit data per facility </p>
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



        <script type="text/javascript">


                                   $('.dates').datepicker({
                                       todayHighlight: true, clearBtn: true, autoclose: true, format: "yyyy-mm-dd"
                                   });



                                   $(document).ready(function () {
                                       $('dic').select2();
                                   });


                              
 function getFacilitiesJson(){
       
   
       
       
              $.ajax({
                         url:'loadActiveSites',                            
                    type:'post',  
                    dataType: 'html',  
                    success: function(data) {
                        
                        
                        
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

getSections();






 function getModalities(){
       
   var sec=$("#section").val();
       
       
              $.ajax({
                         url:'getParameterData?mod='+sec+"&sec="+sec,                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) {                        
                       
        var dat=data.modalities;
        
      
        var o="<option value=''>Select Option</option>";
                        
                        for(var a=0;a<dat.length;a++)
                        {                           
                     
                          o+="<option value='"+dat[a].modality_id+"'>"+dat[a].modality+"</option>";   
                        }
                        
                   $("#modality").html(o);
                   $(document).ready(function() {
                    $('#modality').select2(); 
             
                                 } ); 
                        
                        
                    }});
   
   }
   
   
   
   
   
   
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

//=========================================Run validation================================

                                   function runvalidation() {

                                       var retv = true;

//                                      As_Kits
//NonAs_Kits
//As_Res
//NonAs_Res
//As_Neg
//NonAs_Neg
//As_Pos
//NonAs_Pos
//As_Ref
//NonAs_Ref
//As_Con_Pos
//NonAs_Con_Pos
//As_Linked
//NonAs_Linked


                                       var validationsid = ["As_Kits@As_Res", "NonAs_Kits@NonAs_Res", "As_Res@As_Neg","NonAs_Res@NonAs_Neg", "As_Res@As_Pos","NonAs_Res@NonAs_Pos", "As_Pos@As_Ref", "NonAs_Pos@NonAs_Ref", "As_Pos@As_Con_Pos", "NonAs_Pos@NonAs_Con_Pos", "As_Pos@As_Linked", "NonAs_Pos@NonAs_Linked"];

                                       var agedis = ["total"];
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

                                       var facil = "";
                                       var modid = "";
                                       var ym = "";
                                     
                                     


//dic=$("#dic").find(":selected").data('datimid');
                                       facil = $("#facility").val();
                                       modid = $("#modality").val();
                                       ym = $("#period").val();
                                     


//this should happen in a loop


                                       if (facil === '' || facil === 'Select facility') {

                                           alert("enter Facility");

                                       } 
                                       
        else if (modid === '') {

                                           alert("enter Reporting Modality");

                                       }
        else if (ym === '') {

                                           alert("enter reporting period");

                                       } else if (runvalidation() === false)
                                       {




                                       } else {




                                           $.ajax({
                                               url: 'getHtsSelfIndicators',
                                               type: 'post',
                                               dataType: 'json',
                                               success: function (data) {

                                                   for (a = 0; a < data.length; a++) {
                                                       var isend = false;

                                                       var indicatorid = data[a].id;
                                                       var value = $("#" + indicatorid + "_total").val();
                                                      
                                                       var identifier = facil + "_" +ym + "_"+ modid + "_" + indicatorid;


                                                       //save the data
                       
                                                       var saveddata = {
                                                           id: identifier,
                                                           yearmonth: ym,
                                                           facility: facil,                                                          
                                                           indicatorid: indicatorid,
                                                           value: value,
                                                           modality: modid

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
                                           url: 'saveHtsSelfData',
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

function refreshujumbe(){
    
    $("#fedback").html("");
    
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
                                       
                                     
            
       
                                       var ym = $("#period").val().trim();
                                       var mod = $("#modality").val().trim();
                                       var fc = $("#facility").val().trim();
//    console.log("_"+fc+"vs"+dt);
                                       if (mod !== '' && ym !== '' && fc !== 'Select facility' && fc !== '')
                                       {
                                           // display facility name
                                           $("#dynamicindicators").show();


                                           //now load the data
                                           $.ajax({
                                               url: 'getHtsSelfIndicators?ym=' + ym + "&fc=" + fc+"&mod="+mod,
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

    </body>
</html>
