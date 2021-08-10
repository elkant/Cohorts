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
        <title>Service Gaps</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/select2.min.css">
        <link rel="shortcut icon" href="images/rri.png">
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
                    <button id="toolid" style="float:left;color:white; text-align: center;" class="navbar-toggle btn btn-default" >&nbsp;Accounting for gaps and missed opportunities</button>
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
                        <li><a  class=''  href='rri_gaps_index.jsp'><i class='glyphicon glyphicon-log-out'></i> Log out</a></li>
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
                                    <li class="active newdata"><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-plus"></i>Accounting For Service Gaps</a></li>
                                 
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
 <div class="accordion" id="form1a_accordion" >
                                            
                                            
                                            <!--__________________________________________________________________________________________________-->
                                            
                                          
                                                
     <label>Select Facility and Period to load the data entry form</label>                                     
                                         
                                            
                                            
                                            <!--__________________________________________________________________________________________________-->


                                                  

                                                </div>
                                                   
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
                    url:'loadSitesWithGaps?ym='+ym,                            
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










                                   function save_data(dbname,sci) {



//___indicators to pull___

                                       var facil = "";
                                     
                                       var ym = "";
                           facil = $("#facility").val();
                                     
                           ym = $("#period").val();
                                     
              if (facil === '' || facil === 'Select facility') {

                                           alert("enter Facility");
              } 
                                       
      
        else if (ym === '') {

                                           alert("enter reporting period");

                                       } 
                                       
       // else if (runvalidation() === false) { } 
                                       else {
   $.ajax({
                                               url: 'loadindicators?scid='+sci,
                                               type: 'post',
                                               dataType: 'json',
                                               success: function (data) {

                                                   for (a = 0; a < data.length; a++) {
                                                       var isend = false;

                                                       var indicatorid = data[a].id;
                                                       var value = $("#" + indicatorid).val();
                                                      
                                                       var identifier = facil + "_" +ym + "_" + indicatorid;


                                                       //save the data
                       
                                                       var saveddata = {
                                                           id: identifier,
                                                           yearmonth: ym,
                                                           facility_id: facil,                                                          
                                                           indicator_id: indicatorid,
                                                           value: value,
                                                           week:new Date().getWeekNumber(),
                                                           userid:'909090'

                                                       };




                                                       if (a === parseInt(data.length) - 1) {
                                                           isend = true;
                                                         
                                                       }

                                                       exportData(saveddata, isend, dbname, sci);

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
                                         setTimeout(refreshujumbe,2000);
    
                                               }

                                           }
                                       });


                                   }

//call the function that displays the data

function refreshujumbe(eid){
    
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
                                       
                                     
            
       
                                       var ym = $("#period").val().trim();
                                      
                                       var fc = $("#facility").val().trim();
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




        </script>

    </body>
</html>
