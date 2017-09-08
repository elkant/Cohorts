<%-- 
    Document   : index
    Created on : Mar 17, 2016, 3:17:19 PM
    Author     : Emmanuel E
--%>

<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>PCA/ART Cohorts</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
                  <link href="css/dataTables.bootstrap.min.css" rel="stylesheet">
		<link href="css/bootstrap.css" rel="stylesheet">
                <link href="css/bootstrap-datepicker.min.css" rel="stylesheet">
                <link href="css/handsontable.full.css" rel="stylesheet">
                    <link rel="stylesheet" href="css/select2.min.css">
                    <link rel="shortcut icon" href="images/logo.png">
                  
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="css/styles.css" rel="stylesheet">
                
	</head>
	<body>
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
          
                
                 <li><a title="Add Widget" id="adduserbutton" data-toggle="modal" href="#userdetails"><i class="glyphicon glyphicon-user"></i><span id="usernamelabel"> Add Username</span></a></li>
                 <li><a title="Add Widget" data-toggle="modal" style="display:none;" id="exportdataanchor2" href="#addWidgetModal"><i class="glyphicon glyphicon-cloud-upload"></i> Export Data</a></li>
                 <li>
                  <a  title="Help" data-toggle="modal" href="#help">
                            <i class="glyphicon glyphicon-question-sign"></i>
                            Help
                        </a></li>
                              <li><a href="#" style="display:none;" onclick="closeapp();"><i class="glyphicon glyphicon-lock"></i> Exit</a></li>
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

            
            
         

            <div class="row">
                <!-- center left-->
                <div class="col-md-12 " >
                    

                  

                    <div class="btn-group btn-group-justified " style='background-color: #ddd;'>
                        <a href="#" id='refreshpage' class="btn btn-default col-sm-3">
                            <i class="glyphicon glyphicon-refresh"> </i> PMTCT/ART COHORT ANALYSIS REPORTING TOOL
                        </a>
                        
                         
                            
                          
                    </div>

                    <hr>


                   

                    <!--tabs-->
                    <div class="panel">
                        <ul class="nav nav-tabs " id="myTab">
                            <li class="active newdata"><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-plus"></i>COHORTS DATA</a></li>
                            <!--<li class="active editdata" style='display:none;' ><a href="#dataentry" id="newdatabutton" data-toggle="tab">  <i class="glyphicon glyphicon-edit"></i> Edit Data</a></li>-->
                            <li><a href="#reports" style="display:none;" id="reportsbutton" data-toggle="tab"> <i class="glyphicon glyphicon-stats"></i> Report</a></li> 
                            <!--<li><a href="#searchdata" data-toggle="tab"> <i class="glyphicon glyphicon-search"></i> View Data</a></li>--> 
                           <!-- <li><a href="#export" data-toggle="tab"> <i class="glyphicon glyphicon-cloud-upload"></i> Data Export</a></li>-->
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active well col-md-12" id="dataentry">
                                
                                
                              <!--Data entry code-->
                    <div class="panel panel-default">
                       
                        <div class="panel-body" style="width:100%;">
                            <form class="form form-vertical" action="#" method="post" id="weeklydataform" >
                                <table class='table table-striped table-bordered'  style=" width:100%" >
                                <tr><th style='text-align: center;'>Choose all the parameters below to fill the respective cohort</th></tr>
                                
                              
                                
                                
                                
                                <tr><td class="col-xs-12">
                                <div class="control-group">
                                    
                                    
                                      <!----------------------------->
                                    
                                    <label class="col-xs-1"><font color="red"><b>*</b></font> Cohort type </label>
                                    
                                    
                               
                                    <!--<label><font color="red"><b>*</b></font> Reporting Month </label>-->
                                    <div class="controls">
                                       <select style="width:140px;" onchange='funguacohort();'  name="cohortttype" id="cohortttype" class="form-control col-xs-2" >
                                            <option value="">Choose cohort type</option>
                                            <option value="art">ART</option>
                                            <option value="pmtct">PMTCT</option>
                                            
                                           
                                        </select>
                                        <input type="hidden"  name ="rowid" id="rowid"  />
                                    </div>
                                    
                                    
                                    <label class="col-xs-2"><font color="red"><b>*</b></font> Reporting Year </label>
                                    <div class="controls">
                                       <select  onchange='funguacohort();'  name="year" id="year" class="form-control col-xs-2" style="width:140px;" >
                                            <option value='' >Choose year</option>
                                            <%
                                                
                                                Calendar cal= Calendar.getInstance();
                                                int curyear=cal.get(Calendar.YEAR);
                                                
                                            for(int a=curyear-1;a<=curyear+1;a++){
                                             out.println("<option value='"+a+"'>"+a+"</option>");
                                                %>
                                            
                                            
                                            <%
                                            }
                                            
                                            %>
                                            
                                        </select>
                                        <input type="hidden"  name ="rowid" id="rowid"  />
                                    </div>
                                    
                                    <!----------------------------->
                                    
                                    <label class="col-xs-2"><font color="red"><b>*</b></font> Reporting Month </label>
                                    
                                    <div class="controls">
                                       <select onchange='funguacohort();' style="width:150px;"   name="month" id="month" class="form-control col-xs-2" >
                                            <option value=''>Choose  month</option>
                                            <option value="01">January</option>
                                            <option value="02">February</option>
                                            <option value="03">March</option>
                                            <option value="04">April</option>
                                            <option value="05">May</option>
                                            <option value="06">June</option>
                                            <option value="07">July</option>
                                            <option value="08">August</option>
                                            <option value="09">September</option>
                                            <option value="10">October</option>
                                            <option value="11">November</option>
                                            <option value="12">December</option>
                                           
                                        </select>
                                        <input type="hidden"  name ="rowid" id="rowid"  />
                                    </div>
                                    
                                    
                                    
                                    <label class="col-xs-1"><font color="red"><b>*</b></font> Facility </label>
                                    
                                    <div class="controls">
                                       <select onchange='funguacohort();' style="width:180px;"   name="facilityname" id="facilityname" class="form-control col-xs-2" >
                                            <option value=''>Choose facility</option>
                                                                                       
                                        </select>
                                        <input type="hidden"  name ="rowid" id="rowid"  />
                                    </div>
                                    
                                    
                                </div>
                                        </td></tr>
                                
                               
                                
                                  <tr><td class="col-xs-12">
                             
                                
                                
                              
                                
                                 
                                
                                
                                <div class="control-group" style="display:none;">
                                    <label>County:</label>
                                    <div class="controls">
                                        <select  name="county" id="county" class="form-control">
                                            <option>Select County</option>
                                             <option value="Baringo">Baringo</option>
                                             <option value="Kajiado">Kajiado</option>
                                             <option value="Laikipia">Laikipia</option>
                                             <option value="Nakuru">Nakuru</option>
                                             <option value="Narok">Narok</option>
                                            
                                        </select>
                                    </div>
                                </div>
                                
                                 <div style="display:none;" class="control-group">
                                    <label>Sub-County:</label>
                                    <div class="controls">
                                        <select  name="subcounty" id="subcounty" class="form-control">
                                            <option>Select Sub-county</option>
                                             <option value="Baringo">Baringo</option>
                                             <option value="Kajiado">Kajiado</option>
                                             <option value="Laikipia">Laikipia</option>
                                             <option value="Nakuru">Nakuru</option>
                                             <option value="Narok">Narok</option>
                                            
                                           
                                        </select>
                                    </div>
                                </div>
                                
                                          </td></tr>
                                        
                                   
                                  
                                    </table>
                                <div style='overflow: auto; position: relative;'>
                                         <table class='' id="dynamicindicators"  > 
                                   
                                <!------INDICATORS----->
                                 <tr  ><td class='col-xs-12'  >
                                 <div  >
                                     
                                     
<!--           <div id="hot" ></div>-->
<%--<%@include file="datasource.html" %>--%>

<div id="hot" class="htLeft handsontable">
<div class="ht_master handsontable innerBorderLeft" style="position: relative;">
<div style="position: relative; width: 100%; " class="wtHolder">
<div class="wtHider" style="width: 90.5vw; ">
<div class="wtSpreader" style="position: relative; top: 0px; left: 0px;">
<table class="htCore" id='tabledata'>


                                  

</table>

</div>
</div>
</div>
</div>
</div>

                                       </div>  
                                      </td>                               
                                  </tr>
                                  
                                   
                                       <tr><td  class="col-xs-12">               
                                <div class="control-group col-xs-12">
                                      
                                   <br/>
                                    <div class="controls">
                                        <button type="submit" id='savebutton' onclick="validateweeklydata();" style="margin-left: 40%; display:none;" class="btn-lg btn-success active">
                                            Save 
                                        </button>
                                     </div>
                                     <div class="controls">
                                        <button type="submit" id='updatebutton' onclick="updateweeklydata();" style="margin-left: 0%;display:none;" class="btn-lg btn-info active">
                                            UPDATE 
                                        </button>
                                    </div>
                                   
                                    
                                </div>
                                        </td></tr>
                                        
                                </table>
                                    </div>
                            </form>
                        </div>
                        <!--/panel content-->
                    </div>
                    <!--/panel-->

                              
                              <!--Data entry code-->
                            
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

<footer class="text-center" style='background-color: white;'> <img src='images/banner.PNG'    /> </footer>

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
                <p>This  system is created for aiding users in collecting data for the ART and PMTCT cohorts. One is expected to enter data per facility by month.</p>
                <h3>Indicators</h3>
                <p>The specific indicators that one should enter data for are;</p>
                <ul>

<li> A. Enrolled into cohort </li>
<li>B. Transfers In(T.I)</li>

<li>C. Transfers Out(T.O)</li>

<li>D. Net Cohort(A+B-C)</li>

<li>E. Defaulters</li>

<li>F. Lost to follow up (LTFU)</li>

<li>G. Reported Dead</li>

<li>H. Stopped</li>

<li>I. Alive and Active on Treatment</li>

<li>J. Viral Load Collected</li>

<li>K. Virally suppressed (VL<1000)</li>

<li>L. % Retained(I/D*100) </li>






                </ul>
             <h3>Data Validation</h3>
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
                 <script src="js/handsontable.full.js"></script>
                 <script type="text/javascript" src="js/datatables.min.js"></script>
                 
                 <script data-jsfiddle="common" src="dist/pikaday/pikaday.js"></script>
  <script data-jsfiddle="common" src="dist/moment/moment.js"></script>
  <script data-jsfiddle="common" src="dist/zeroclipboard/ZeroClipboard.js"></script>
  <script data-jsfiddle="common" src="dist/numbro/numbro.js"></script>
  <script data-jsfiddle="common" src="dist/numbro/languages.js"></script>
                 
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
   
              $.ajax({
                         url:'http://104.45.29.195:8080/aphiaplus_moi/showfacils',                            
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) {
                   if(data!=='aphiaplus_undefined'){
                 //alert(data[0].facility_name);    
                     for(var i=0;i<data.length;i++){
                         
                       // console.log(data[i].facility_name) 
                         //now add the facilities into a table
                         addfacilities(data[i].mflcode,data[i].county,data[i].subcounty,data[i].facility_name,data[i].longitude,data[i].latitude,data[i].sitetype);
                         updatefacilities(data[i].mflcode,data[i].county,data[i].subcounty,data[i].facility_name,data[i].longitude,data[i].latitude,data[i].sitetype);
                         
                     }
                       
                   }
        
                    }
                        
                         });
   
    
    //PouchDB.debug.enable('*');
    //PouchDB.debug.disable();
    var userdb = new PouchDB('userdetailspca');
var remoteCouch = false;
var userdetails;

//receive the artist, song title and lyrics text
function adduser(username,county) {
   userdetails = {
        _id: 'aphiaplus_pca', //this is static since we cant have two users using the same phone
	username:username,
        county:county,
        completed: false
  };
  userdb.put(userdetails, function callback(err, result) {
    if (!err) {
      console.log('facilities added succesfully');
    }
    
    setTimeout(delayedrefresh,1500);
  });
}	


  function updateuser(){
   //alert("save called");   
   var usern=$("#username").val();   
   var cnty=$("#usercounty").val();
   if(usern===''){alert("Enter Username");}
   else 
   {
       
    adduser(usern,cnty); 
   showuser('aphiaplus_pca',usern,cnty);   
       
   }
   
  }  
    
   function showuser(aphiaplus,updateduser,updatedcounty){

//doc.username=username;
  
  // update user
 // return db.put(doc); //continue from here


	var counties=["Baringo","Kajiado","Laikipia","Nakuru","Narok"];
	userdb.get(aphiaplus).then(function (doc) {
            //
            if(updateduser!==''){
doc.username=updateduser;  
doc.county=updatedcounty;  
// update user
return userdb.put(doc); //continue from here
            }         
            if(doc.username!==''){
 $("#username").val(doc.username);
 $("#usernamelabel").html(" Hi "+doc.username);
 user=doc.username;
            }
            //alert(doc.county);  
   var cntselect="<option value=''>Select County Supporting</option>";
            for(var s=0;s<counties.length;s++){
             if(counties[s]===doc.county){
                 // console.log(counties[s]+"_"+doc.county+"_");
             
        cntselect+="<option selected value='"+counties[s]+"'>"+counties[s]+"</option>";
             } else {
                   cntselect+="<option  value='"+counties[s]+"'>"+counties[s]+"</option>";
               
                 
             }  
                
            }
 $("#usercounty").html(cntselect);
 
});
	}
     showuser('aphiaplus_pca','','');
   function loaduser(){
   //alert("save called");   
   var user=$("#username").val();   
   var cnty=$("#usercounty").val();   
   adduser(user,cnty);   
  }  
      
    
//This is a document to save all facilities for offline use 
var db = new PouchDB('facilitiespca');
var remoteCouch = false;
var receiveddata;

//add facility details here
function addfacilities(mflcode,county,subcounty,facility,longitude,latitude,sitetype) {
   receiveddata = {
        _id: mflcode,
	facility:facility,
        county:county,
	subcounty:subcounty,
        latitude:latitude,
        longitude:longitude,
        sitetype:sitetype,
        completed: false
  };
  db.put(receiveddata, function callback(err, result) {
    if (!err) {
      console.log('facilities added succesfully');
    }
  });
}	

function updatefacilities(mflcode,county,subcounty,facility,longitude,latitude,sitetype) {
 
 db.get(mflcode).then(function (doc) {
        
  //doc.age = 4;
 
   if(mflcode!=='null' && mflcode!=='' ){
    doc._id=mflcode;
    doc.facility=facility;
        doc.county=county;
	doc.subcounty=subcounty;
        doc.latitude=latitude;
        doc.longitude=longitude;
        doc.sitetype=sitetype;
   //alert('called');
  // put them back
  return db.put(doc);
   }
});
 
 
}


//a function to load facilities data from the 


function showfacils(){
    
    var cnt=0;
    
    var facilities="<option value=''>Select Facility</option>";
    
  db.allDocs({include_docs: true, descending: true}).then( function(doc) { 
 
      cnt++;
	   //console.log(doc);
	   for(a=0;a<
                   
                
                
                doc.total_rows;a++){
	   var dat={};
	   dat=doc.rows[a];
	      //console.log(dat.doc.title);
              //how to reference each column 
              
              //dat.doc._id
		  var num=parseInt(a)+1;
		 facilities+="<option value='"+dat.doc._id+"_"+dat.doc.facility+"'>"+dat.doc.facility+"</option>";
          	    } //end of for loop
	 
	  	  
			
           $("#facilityname").html(facilities); 	  
		 
			
			
	    $(document).ready(function() {
            //$('#lyricstable').DataTable();
              $('#facilityname').select2(); 
                                 } );
			
  }).catch(function (err){console.log(err)});
 
        
       // if(facilities==="<option value=''>Select Facility</option>"){
    
  //facilities="<option value=''>Please connect to internet to download facilities</option>";  
    
//}
        
}//



//showfacils
showfacils();

//========================SAVE TARGETS============================
//========================SAVE TARGETS============================

var targetsdb = new PouchDB('targets');
var remoteCouch = false;
var receivedtargets;




//=========================SAVE WEEKLY DATA========================

 
//This is a document to save all tables 
var weeklydatadb = new PouchDB('weekly_data');
var remoteCouch = false;
var weeklydata;

//receive the artist, song title and lyrics text
function insertweeklydata(id,facility,startdate,enddate, user, syncstatus) {
   
        weeklydata = {
        _id: id, //made of startdate_enddate_facilitymfl
	facility:facility,
      
        enrolled_cohort_kp:enrolled_cohort_kp,
enrolled_cohort_np:enrolled_cohort_np,
enrolled_cohort_total:enrolled_cohort_total,
transfers_in_kp:transfers_in_kp,
transfers_in_np:transfers_in_np,
transfers_in_total:transfers_in_total,
transfers_out_kp:transfers_out_kp,
transfers_out_np:transfers_out_np,
transfers_out_total:transfers_out_total,
netcohort_kp:transfers_out_total,
netcohort_np:netcohort_np,
netcohort_total:netcohort_total,
defaulters_kp:defaulters_kp,
defaulters_np:defaulters_np,
defaulters_total:defaulters_total,
ltfu_kp:ltfu_kp,
ltfu_np:ltfu_np,
ltfu_total:ltfu_total,
reported_dead_kp:reported_dead_kp,
reported_dead_np:reported_dead_np,
reported_dead_total:reported_dead_total,
stopped_kp:stopped_kp,
stopped_np:stopped_np,
stopped_total:stopped_total,
alive_active_treat_kp:alive_active_treat_kp,
alive_active_treat_np:alive_active_treat_np,
alive_active_treat_total:alive_active_treat_total,
viralload_kp:viralload_kp,
viralload_np:viralload_np,
viralload_total:viralload_total,
suppressed_kp:suppressed_kp,
suppressed_np:suppressed_np,
suppressed_total:suppressed_total,
retained_kp:retained_kp,
retained_np:retained_np,
retained_total:retained_total,
cohort:cohort,
year:year,
month:month,
cohort:cohort,
        timestamp:timestamp,
        user:user,
        syncstatus:syncstatus,        
        completed: false
  };
  weeklydatadb.put(weeklydata, function callback(err, result) {
    if (!err) {
      console.log('weekly data added succesfully');
      
    }
  });
}	



//prepare form data

//===================================================INSERT WEEKLY DATA===================================

  var facility=null;

 var month=null;   
 var year=null;
    
   var enrolled_cohort_kp=null;
 var enrolled_cohort_np=null;
 var enrolled_cohort_total=null;
 var transfers_in_kp=null;
 var transfers_in_np=null;
 var transfers_in_total=null;
 var transfers_out_kp=null;
 var transfers_out_np=null;
 var transfers_out_total=null;
 var netcohort_kp=null;
 var netcohort_np=null;
 var netcohort_total=null;
 var defaulters_kp=null;
 var defaulters_np=null;
 var defaulters_total=null;
 var ltfu_kp=null;
 var ltfu_np=null;
 var ltfu_total=null;
 var reported_dead_kp=null;
 var reported_dead_np=null;
 var reported_dead_total=null;
 var stopped_kp=null;
 var stopped_np=null;
 var stopped_total=null;
 var alive_active_treat_kp=null;
 var alive_active_treat_np=null;
 var alive_active_treat_total=null;
 var viralload_kp=null;
 var viralload_np=null;
 var viralload_total=null;
 var suppressed_kp=null;
 var suppressed_np=null;
 var suppressed_total=null;
 var retained_kp=null;
 var retained_np=null;
 var retained_total=null;
 var cohort=null;
 var facility=null;
 var year=null;
 var month=null;
 var cohort=null;

   //added 201605 
    var progressbarstoskip=[];
    var allindicatorsarray=[];
    var allcommentsarray=[];
    var allprogressbar_hiddentext_array=[];
     
function createdynamicinputs(){
    
    
     $(document).ready(function(){
         
       
   
         $.getJSON("indicators.json",function(result){
             var table="";
             var row1="";
             var row2="";
             var count=1;
             for(a=0;a< result.length; a++){
                 
             var indicatorname=result[a].IndicatorName;    
             var indicatorid=result[a].IndicatorID;
             var label=result[a].Age;
             var level=result[a].Level;
             var inputtype=result[a].datainputtype;
             var minimum=result[a].Min;
             var maximum=result[a].Max;
             var onblur="";
             if(result[a].onblur!==null){
                 onblur=result[a].onblur;
             }
             var onkeypress=result[a].onkeypress;
             var tdclass=result[a].tdclass;
             var isrequired=result[a].Required;
             var isnewrow=result[a].newrow;
             var readonlyvar=result[a].readonly;
             var isreadonly="";
             allindicatorsarray.push(indicatorid);
              var tabindex="";
             if(readonlyvar==="TRUE"){
                 
               isreadonly="readonly";
               tabindex=" tabindex='-1' ";
             }
             var colspan=result[a].colspan;
            
             if(label==='Total' && tdclass==='col-xs-4' ){  }
             if(isnewrow===1)
             {
               
                 row1="<tr> <td class='col-xs-12' colspan='3'> <div class='control-group'> <label> <font color='red'> <b> * </b> </font> "+indicatorname+" </label> </div> </td> </tr>";
                 count++;
                 row2+=row1;
             }
             else{
                 
                 row1="";
             }
              if(isnewrow===1 && count===2)
             {
             row2+=" <tr> ";
              }
              else  if(isnewrow===1 && count > 2 && count<result.length){
              row2+=" </tr> <tr> ";    
                  
              }
              
              
              row2+="<td class='"+tdclass+"' colspan='"+colspan+"' > <div class='control-group' > <label> "+label+" </label> <div class='controls'> <input onkeypress='return numbers(event);' "+isreadonly+"  "+tabindex+" onblur=\""+onblur+"\" type='"+inputtype+"' min ='"+minimum+"' max='"+maximum+"'  name='"+indicatorid+"' id='"+indicatorid+"' class='form-control'> </div> </div> </td> ";
            //IndicatorID	Age	IndicatorName	Level	datainputtype	Min	Max	onblur	onkeypress	Class	Required
    
     
     //now do an appending
                 
             }
             row2+=" </tr> ";   
            
            //alert(row2);
             //$("#dynamicindicators").html(row2);
             
    // alert(result[0].IndicatorName);
    });// ned of input field loading
    
    
    //progress bar report section
    
        
         
  });   //end of checking if document is ready
    
    
}

createdynamicinputs();


function sumofindicators(sourceindicators,destinationindicator){
    var sourceindicatorsarray=sourceindicators.split("@");
  
   
    var destinationelement=destinationindicator;
    var total=0;
    for(b=0;b<sourceindicatorsarray.length;b++){
        //check if there
        if($("#"+sourceindicatorsarray[b]).val()!==''){
      total=parseInt(total)+parseInt($("#"+sourceindicatorsarray[b]).val()); 
        
            $("#"+destinationelement).val(total);
            }
                                               
        }
                                              
                                             
    
}



//=========================================set targets================================

function settargets(facilitymfl){
 //not in use for now      
}







function validateweeklydata(){
    //receive all the fields from the weekly data from
   facility=$("#facilityname").val();
      enrolled_cohort_kp=$("#enrolled_cohort_kp").val();
   enrolled_cohort_np=$("#enrolled_cohort_np").val();
   enrolled_cohort_total=$("#enrolled_cohort_total").val();
   transfers_in_kp=$("#transfers_in_kp").val();
   transfers_in_np=$("#transfers_in_np").val();
   transfers_in_total=$("#transfers_in_total").val();
   transfers_out_kp=$("#transfers_out_kp").val();
   transfers_out_np=$("#transfers_out_np").val();
   transfers_out_total=$("#transfers_out_total").val();
   netcohort_kp=$("#netcohort_kp").val();
   netcohort_np=$("#netcohort_np").val();
   netcohort_total=$("#netcohort_total").val();
   defaulters_kp=$("#defaulters_kp").val();
   defaulters_np=$("#defaulters_np").val();
   defaulters_total=$("#defaulters_total").val();
   ltfu_kp=$("#ltfu_kp").val();
   ltfu_np=$("#ltfu_np").val();
   ltfu_total=$("#ltfu_total").val();
   reported_dead_kp=$("#reported_dead_kp").val();
   reported_dead_np=$("#reported_dead_np").val();
   reported_dead_total=$("#reported_dead_total").val();
   stopped_kp=$("#stopped_kp").val();
   stopped_np=$("#stopped_np").val();
   stopped_total=$("#stopped_total").val();
   alive_active_treat_kp=$("#alive_active_treat_kp").val();
   alive_active_treat_np=$("#alive_active_treat_np").val();
   alive_active_treat_total=$("#alive_active_treat_total").val();
   viralload_kp=$("#viralload_kp").val();
   viralload_np=$("#viralload_np").val();
  viralload_total =$("#viralload_total").val();
   suppressed_kp=$("#suppressed_kp").val();
   suppressed_np=$("#suppressed_np").val();
   suppressed_total=$("#suppressed_total").val();
  retained_kp =$("#retained_kp").val();
   retained_np=$("#retained_np").val();
  retained_total =$("#retained_total").val();
   cohort=$("#cohort").val();
   facility=$("#facility").val();
   year=$("#year").val();
   month=$("#month").val();
   cohort=$("#cohort").val();
 
   //No facility name should have an underscore since its a special key
  
    var user=$("#username").val(); 
    var timestamp = $.now();
    
    if(user===''){user='aphiaplus';}
    
    var syncstatus='No';  
    
     var id=null;
          
     if(facility==='')
     {         
  
   alert('Select facility');
   //$("#facilityname select:first").focus();
   
   $("#facilityname").css('border-color','red');
    //$("select:first").focus();
     }
     //startdate
     else if (year==='')
     {
         
     alert('Select year');
   //$("#year").focus();    
     }    
   //end date
      else if (month==='')
     {
         
     alert('Select month');
  // $("#month").focus();    
     } 
     
    
     //====added 201601==============================


    
  //=============================
       else if( enrolled_cohort_kp==='')
       {
         
   alert('Enter # Enrolled into cohort KP ');
   $("#enrolled_cohort_kp").focus();    
     
       }
       
       
  //=============================
       else if( enrolled_cohort_np==='')
       {
         
   alert('Enter #  Enrolled into cohort NP ');
   $("#enrolled_cohort_np").focus();    
     
       }
       
  //=============================
       else if( transfers_in_kp==='')
       {
         
   alert('Enter # Transfers in KP ');
   $("#transfers_in_kp").focus();    
     
       }
   //-------------
       else if( transfers_in_np==='')
       {
         
   alert('Enter # Transfers in NP ');
   $("#transfers_in_np").focus();    
     
       }
      
   //--------------
   
   else if( transfers_out_kp==='')
       {
         
   alert('Enter # Transfers out KP ');
   $("#transfers_out_kp").focus();    
     
       }
   //------------------------
   
   else if( transfers_out_np==='')
       {
         
   alert('Enter # Transfers Out NP ');
   $("#transfers_out_np").focus();    
     
       }
   //--------------------
   else if( netcohort_kp==='')
       {
         
   alert('Enter # Net Cohort KP ');
   $("#netcohort_kp").focus();    
     
       }
   
   //------------------
   else if( netcohort_np==='')
       {
         
   alert('Enter # Net Cohort NP ');
   $("#netcohort_np").focus();    
     
       }
   //------------------
      






   //------------------------
   
   else if( defaulters_kp==='')
       {
         
   alert('Enter # Defaulters KP ');
   $("#defaulters_kp").focus();    
     
       }
   //--------------------
   else if(defaulters_np==='')
       {
         
   alert('Enter # Defaulters NP  ');
   $("#defaulters_np").focus();    
     
       }
   
   //------------------
   else if( ltfu_kp==='')
       {
         
   alert('Enter # Lost to follow up KP ');
   $("#ltfu_kp").focus();    
     
       }
   //------------------
       
   //------------------------
   
   else if( ltfu_np==='')
       {
         
   alert('Enter # Lost to follow up NP');
   $("#ltfu_np").focus();    
     
       }
   //--------------------
   else if( reported_dead_kp==='')
       {
         
   alert('Enter # Reported Dead KP');
   $("#reported_dead_kp").focus();    
     
       }
   
   //------------------
   else if( reported_dead_np==='')
       {
         
   alert('Enter # Reported Dead NP ');
   $("#reported_dead_np").focus();    
     
       }
   //------------------
       





   //------------------------
   
   else if(stopped_kp==='')
       {
         
   alert('Enter # stopped KP  ');
   $("#stopped_kp").focus();    
     
       }
   //--------------------
   else if(stopped_np==='')
       {
         
   alert('Enter # Stopped NP ');
   $("#stopped_np").focus();    
     
       }
   
   //------------------
   else if( alive_active_treat_kp==='')
       {
         
   alert('Enter # Alive and Active on treatment KP ');
   $("#alive_active_treat_kp").focus();    
     
       }
   //------------------
       
   //------------------------
   
   else if( alive_active_treat_np==='')
       {
         
   alert('Enter # Alive and Active on treatment NP ');
   $("#alive_active_treat_np").focus();    
     
       }
   //--------------------
   else if( viralload_kp==='')
       {
         
   alert('Enter # Viral Load Collected KP ');
   $("#viralload_kp").focus();    
     
       }
   





   //------------------
   else if( viralload_np==='')
       {
         
   alert('Enter # Viral Load Collected NP  ');
   $("#viralload_np").focus();    
     
       }
   //------------------
       
   //------------------------
   
   else if( suppressed_kp==='')
       {
         
   alert('Enter # Virally suppressed KP ');
   $("#suppressed_kp").focus();    
     
       }
   //--------------------
   else if( suppressed_np==='')
       {
         
   alert('Enter # Virally suppressed NP ');
   $("#suppressed_np").focus();    
     
       }
   
   //------------------
   else if( retained_kp==='')
       {
         
   alert('Enter # Retained KP ');
   $("#retained_kp").focus();    
     
       }
   //------------------
      
       else if( retained_np==='')
       {
         
   alert('Enter # Retained NP');
   $("#retained_np").focus();    
     
       }
  //----------------------    
     
  //----------------------    
       else if( cohort==='')
       {
         
   alert('Select Cohort');
   //$("#cohort").focus();    
     
       }
  //----------------------  
  

 
       else {
     var facilitynameandmfl=facility.split("_");        
     var startd=startdate.replace('-','');      
     var startd=startd.replace('-','');      
     var endd=enddate.replace('-','');      
     var endd=endd.replace('-','');      
      id=facilitynameandmfl[0]+"_"+startd+"_"+endd;
      //this should not be cleared
      $("#rowid").val(id);
      
     var facilityname=facilitynameandmfl[1];
            //save data to the db
          insertweeklydata(id,facilityname,startdate,enddate, hiv_pos_target_child,hiv_pos_target_adult,hiv_pos_target_total,hiv_pos_child,hiv_pos_adult,hiv_pos_total,new_care_child,new_care_adult,new_care_total,new_art_target_child,new_art_target_adult,new_art_target_total,started_art_child,started_art_adult,started_art_total,viral_load_target_child,viral_load_target_adult,viral_load_target_total,viral_load_done_child,viral_load_done_adult,viral_load_done_total,ipt_target_child,ipt_target_adult,ipt_target_total,ipt_child,ipt_adult,ipt_total,testing_target_child,testing_target_adult,testing_target_total,test_child,test_adult,test_total ,pmtct_hiv_pos_target,pmtct_hiv_pos,eid_target,eid_done, viral_load_mothers_target,viral_load_mothers_done,timestamp,user, syncstatus) ;

clearweeklyfields();

 //selectsearchdata();
$("#message").show();
$("#actiondone").html("Data Saved Successfully");
//call the function that loads entered data
//$("#message").hide().delay(800).fade(400);


console.log('weekly data entered');
//open reports tab
 //$('#reportsbutton').click();
 
 
//setTimeout(delayedrefresh,2000);
 // delayedrefresh
       }
       
    
}


function clickreportstab(){
   
 $('#reportsbutton').click();
}


//===========================================EMPTY WEEKLY DATA FIELDS AFTER INSERT============================================================

function clearweeklyfields()
{
   // $("#facilityname").val("");
   //No facility name should have an underscore since its a special key
   
$("#startdate").val("");   
$("#enddate").val("");

for(b=0;b<allindicatorsarray.length;b++){
    
  $("#"+allindicatorsarray[b]).val("");  
    
} 

    
}

function clearcmtsandprcent(){
    
       //clear progress bar hidden fields too
   
  for(b=0;b<allprogressbar_hiddentext_array.length;b++){
    
  $("#"+allprogressbar_hiddentext_array[b]).val("");  
    
} 
       
       //comnts
 
     for(b=0;b<allcommentsarray.length;b++){
    
  $("#"+allcommentsarray[b]).val("");  
    
                                            }//end of for loop 
    
}

var dbdata="";

//===================================================VIEW WEEKLY DATA============================================================
//a function to select a few search data that should appear in a data table
function selectsearchdata()
{
    
    
    
    //rread from weekly data db
    

  
    
  weeklydatadb.allDocs({include_docs: true, ascending: true}).then( function(doc) { 
 
     
	   //console.log(doc);
	   for(b=0;b<doc.total_rows;b++){
	   var dat={};
	   dat=doc.rows[b];
	      //console.log(dat.doc.facility);
              //how to reference each column 
              //alert(dat.doc.startdate);
              //dat.doc._id
	     
		 //dbdata+="<tr><td> "+dat.doc.startdate+" </td><td>"+dat.doc.syncstatus+"</td><td>"+dat.doc.facility+"</td><td><button class='btn-info' onclick='loadsavedweekelydata(\""+dat.doc._id+"\",\""+dat.doc.facility+"\")'>Edit</button></td></tr>";
		 dbdata+="<tr><td> "+dat.doc.startdate+" </td><td>"+dat.doc.facility+"</td><td><button class='btn-info' onclick='loadsavedweekelydata(\""+dat.doc._id+"\",\""+dat.doc.facility+"\",\"no\")'>Edit</button></td></tr>";
          	    } //end of for loop
                    
	appendtabledata(dbdata);
			
  }).catch(function (err){console.log(err)});
    

    
    
    
    
    
    
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


function loadsavedweekelydata(id,facility,openreportstab ){
    $("#reportsbutton").show();
    
  //  alert(id);
 //load from weekly db where id is as selected   
  var mflanddates=id.split('_');
        	
	weeklydatadb.get(id).then(function (doc) {
    var rowid=id;    
    //populate div with respective content
    $("#rowid").val(id);
    $("#facilityname").val(mflanddates[0]+"_"+facility);
    //$('select#facilityname').find("option[value='"+mflanddates[0]+"_"+facility+"']").prop('selected', true); 
     $("#startdate").val(doc.startdate);   
     $("#enddate").val(doc.enddate); 
     
          

 $("#hiv_pos_target_child").val(doc.hiv_pos_target_child);
 $("#hiv_pos_target_adult").val(doc.hiv_pos_target_adult);
 $("#hiv_pos_target_total").val(doc.hiv_pos_target_total);
 $("#hiv_pos_child").val(doc.hiv_pos_child);
 $("#hiv_pos_adult").val(doc.hiv_pos_adult);
 $("#hiv_pos_total").val(doc.hiv_pos_total);
 $("#new_care_child").val(doc.new_care_child);
 $("#new_care_adult").val(doc.new_care_adult);
 $("#new_care_total").val(doc.new_care_total);
 $("#new_art_target_child").val(doc.new_art_target_child);
 $("#new_art_target_adult").val(doc.new_art_target_adult);
 $("#new_art_target_total").val(doc.new_art_target_total);
 $("#started_art_child").val(doc.started_art_child);
 $("#started_art_adult").val(doc.started_art_adult);
 $("#started_art_total").val(doc.started_art_total);
 $("#viral_load_target_child").val(doc.viral_load_target_child);
 $("#viral_load_target_adult").val(doc.viral_load_target_adult);
 $("#viral_load_target_total").val(doc.viral_load_target_total);
 $("#viral_load_done_child").val(doc.viral_load_done_child);
 $("#viral_load_done_adult").val(doc.viral_load_done_adult);
 $("#viral_load_done_total").val(doc.viral_load_done_total);
 $("#ipt_target_child").val(doc.ipt_target_child);
 $("#ipt_target_adult").val(doc.ipt_target_adult);
 $("#ipt_target_total").val(doc.ipt_target_total);
 $("#ipt_child").val(doc.ipt_child);
 $("#ipt_adult").val(doc.ipt_adult);
 $("#ipt_total").val(doc.ipt_total);
 $("#testing_target_child").val(doc.testing_target_child);
 $("#testing_target_adult").val(doc.testing_target_adult);
 $("#testing_target_total").val(doc.testing_target_total);
 $("#test_child").val(doc.test_child);
 $("#test_adult").val(doc.test_adult);
 $("#test_total").val(doc.test_total);
  
   $("#pmtct_hiv_pos_target").val(doc.pmtct_hiv_pos_target);
   $("#pmtct_hiv_pos").val(doc.pmtct_hiv_pos);
   $("#eid_target").val(doc.eid_target);
   $("#eid_done").val(doc.eid_done);
   $("#viral_load_mothers_target").val(doc.viral_load_mothers_target);
   $("#viral_load_mothers_done").val(doc.viral_load_mothers_done);
   
   
   
     $('#facilityname').select2(); 
     //load all percentages
     loadallpercents();
     //==========LOAD COMMENTS HERE ALSO=======
 $("#hiv_pos_yield_cmts").val(doc.hiv_pos_yield_cmts);
 $("#hiv_pos_care_cmts").val(doc.hiv_pos_care_cmts);
 $("#started_art_cmts").val(doc.started_art_cmts);
 $("#viral_test_cmts").val(doc.viral_test_cmts);
 $("#ipt_done_cmts").val(doc.ipt_done_cmts);
 $("#tested_cmts").val(doc.tested_cmts);
 $("#pmtct_hiv_pos_cmts").val(doc.pmtct_hiv_pos_cmts);
 $("#eid_done_cmts").val(doc.eid_done_cmts);
 $("#viral_load_mothers_cmts").val(doc.viral_load_mothers_cmts);
     
    
     
     //hide the tabular header of + Enter Data and unhide the edit data 
     
    
     //do the vice versa on saving the edited fields
    
        //$(".editdata").show();
     $("#savebutton").hide();
     $("#updatebutton").show();
        
 $('#newdatabutton').html("<i class='glyphicon glyphicon-edit'></i>Edit Data");

  //if this is a request to show the unentered comments
  //open the reports tab and focus on the first unentered comment
    if(openreportstab.indexOf("yes")!==-1){
        
      var contentarray=openreportstab.split("@"); 
      //alert(contentarray[1]);
     
      clickreportstab();
 //     document.getElementById(contentarray[1]).focus();
 $("#"+contentarray[1]).focus();
               }
               else {
                $('#newdatabutton').click();    
                   
               }
  
});


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


function updateweeklydata()
{
 //this id will be used to update the entered data
   var id=$("#rowid").val();
           //receive all the fields from the weekly data from
    facility=$("#facilityname").val();
   //No facility name should have an underscore since its a special key
   
     startdate=$("#startdate").val();   
     enddate=$("#enddate").val();
     
        
hiv_pos_target_child=$("#hiv_pos_target_child").val();
hiv_pos_target_adult=$("#hiv_pos_target_adult").val();
hiv_pos_target_total=$("#hiv_pos_target_total").val();
hiv_pos_child=$("#hiv_pos_child").val();
hiv_pos_adult=$("#hiv_pos_adult").val();
hiv_pos_total=$("#hiv_pos_total").val();
new_care_child=$("#new_care_child").val();
new_care_adult=$("#new_care_adult").val();
new_care_total=$("#new_care_total").val();
new_art_target_child=$("#new_art_target_child").val();
new_art_target_adult=$("#new_art_target_adult").val();
new_art_target_total=$("#new_art_target_total").val();
started_art_child=$("#started_art_child").val();
started_art_adult=$("#started_art_adult").val();
started_art_total=$("#started_art_total").val();
viral_load_target_child=$("#viral_load_target_child").val();
viral_load_target_adult=$("#viral_load_target_adult").val();
viral_load_target_total=$("#viral_load_target_total").val();
viral_load_done_child=$("#viral_load_done_child").val();
viral_load_done_adult=$("#viral_load_done_adult").val();
viral_load_done_total=$("#viral_load_done_total").val();
ipt_target_child=$("#ipt_target_child").val();
ipt_target_adult=$("#ipt_target_adult").val();
ipt_target_total=$("#ipt_target_total").val();
ipt_child=$("#ipt_child").val();
ipt_adult=$("#ipt_adult").val();
ipt_total=$("#ipt_total").val();
testing_target_child=$("#testing_target_child").val();
testing_target_adult=$("#testing_target_adult").val();
testing_target_total=$("#testing_target_total").val();
test_child=$("#test_child").val();
test_adult=$("#test_adult").val();
test_total=$("#test_total").val();
   
    pmtct_hiv_pos_target=$("#pmtct_hiv_pos_target").val();
    pmtct_hiv_pos=$("#pmtct_hiv_pos").val();
    eid_target=$("#eid_target").val();
    eid_done=$("#eid_done").val();
    viral_load_mothers_target=$("#viral_load_mothers_target").val();
    viral_load_mothers_done=$("#viral_load_mothers_done").val();
    
    //alert(Date.parse(startdate));
    
    var user=$("#username").val(); 
    var timestamp = $.now();
    if(user===''){user='aphiaplus';}
    
    var syncstatus='No';  
    
  
          
     if(facility==='')
     {         
  
   alert('Select facility');
   //$("#facilityname").focus();
   
     }
     //startdate
     else if (startdate==='')
     {
         
     alert('Select week begining date');
   $("#startdate").focus();    
     }    
   //end date
      else if (enddate==='')
     {
         
     alert('Select week ending date');
   $("#enddate").focus();    
     }
      else  if(Date.parse(startdate) > Date.parse(enddate)){
                    alert("Start date cannot be greater than end date.");   
                    $("#enddate").focus();  
                }
      //inpatient_adm  
         
     
     
        
        
   //====added 201601==============================
       
    
  
             else if(hiv_pos_target_child==='')
      {
         
   alert('Enter # HIV positive target Child');
   $("#hiv_pos_target_child").focus();    
     
       }
   
          else if(hiv_pos_target_adult==='')
      {
         
   alert('Enter # HIV positive target Adult');
   $("#hiv_pos_target_adult").focus();    
     
       }   
     
//# HIV positive[cumulative]

        else if(hiv_pos_child==='')
      {
         
   alert('Enter # HIV positive[cumulative] Child');
   $("#hiv_pos_child").focus();    
     
       }
   
          else if(hiv_pos_adult==='')
      {
         
   alert('Enter # HIV positive[cumulative] Adult');
   $("#hiv_pos_adult").focus();    
     
       } 




      else if(new_care_child==='')
      {
         
   alert('Enter # Number new on care [cumulative] Child');
   $("#new_care_child").focus();    
     
       }
   
          else if(new_care_adult==='')
      {
         
   alert('Enter # Number new on care [cumulative] Adult');
   $("#new_care_adult").focus();    
     
       }

//# TARGET NEW ON ART
 else if(new_art_target_child==='')
      {
         
   alert('Enter # Target new on ART Child');
   $("#new_art_target_child").focus();    
     
       }
   
          else if(new_art_target_adult==='')
      {
         
   alert('Enter # Target new on ART Adult');
   $("#new_art_target_adult").focus();    
     
       }


//STARTED ON ART

 else if(started_art_child==='')
      {
         
   alert('Enter # Started on ART [cumulative] Child');
   $("#started_art_child").focus();    
     
       }
   
          else if(started_art_adult==='')
      {
         
   alert('Enter #  Started on ART [cumulative] Adult');
   $("#started_art_adult").focus();    
     
       }


//VIRAL LOAD TARGET


 else if(viral_load_target_child==='')
      {
         
   alert('Enter # Viral load target [current on ART for Dec 2015] Child');
   $("#viral_load_target_child").focus();    
     
       }
   
          else if(viral_load_target_adult==='')
      {
         
   alert('Enter # Viral load target [current on ART for Dec 2015] Adult');
   $("#viral_load_target_adult").focus();    
     
       }


//viral done

 else if(viral_load_done_child==='')
      {
         
   alert('Enter # Viral load done [cumulative] Child');
   $("#viral_load_done_child").focus();    
     
       }
   
          else if(viral_load_done_adult==='')
      {
         
   alert('Enter # Viral load done [cumulative] Adult');
   $("#viral_load_done_adult").focus();    
     
       }



 else if(ipt_target_child==='')
      {
         
   alert('Enter # IPT target [current on Care for Dec 2015] Child');
   $("#ipt_target_child").focus();    
     
       }
   
          else if(ipt_target_adult==='')
      {
         
   alert('Enter # IPT target [current on Care for Dec 2015] Adult');
   $("#ipt_target_adult").focus();    
     
       }




 else if(ipt_child==='')
      {
         
   alert('Enter # IPT [cumulative] Child');
   $("#ipt_child").focus();    
     
       }
   
          else if(ipt_adult==='')
      {
         
   alert('Enter # IPT [cumulative] Adult');
   $("#ipt_adult").focus();    
     
       }

//Testing target [Target/IPD/OPD work load]
 else if(testing_target_child==='')
      {
         
   alert('Enter # Testing target [Target/IPD/OPD work load] Adult');
   $("#testing_target_child").focus();    
     
       }
   
          else if(testing_target_adult==='')
      {
         
   alert('Enter # Testing target [Target/IPD/OPD work load] Adult');
   $("#testing_target_adult").focus();    
     
       }

//# Tests done [IPD/OPD/VCT]
        
    else if(test_child==='')
      {
         
   alert('Enter # Tests done [IPD/OPD/VCT] Adult');
   $("#test_child").focus();    
     
       }
   
          else if(test_adult==='')
      {
         
   alert('Enter # Tests done [IPD/OPD/VCT] Adult');
   $("#test_adult").focus();    
     
       }     
          
        
        
        
        
        
       //,,,,,
       
       //# PMTCT HIV positive yield target
             else if(pmtct_hiv_pos_target==='')
      {
         
   alert('Enter # PMTCT HIV positive yield target');
   $("#pmtct_hiv_pos_target").focus();    
     
       }
       //# PMTCT HIV positive yield
             else if(pmtct_hiv_pos==='')
      {
         
   alert('Enter # PMTCT HIV positive yield');
   $("#pmtct_hiv_pos").focus();    
     
       }
       //# EID target
             else if(eid_target==='')
      {
         
   alert('Enter # EID target ');
   $("#eid_target").focus();    
     
       }
       //eid_done
       
             else if(eid_done==='')
      {
         
   alert('Enter # EID Done ');
   $("#eid_done").focus();    
     
       }
       //viral_load_mothers_target
             else if(viral_load_mothers_target==='')
      {
         
   alert('Enter # viral load tests for mothers target');
   $("#viral_load_mothers_target").focus();    
     
       }
       
        //viral_load_mothers_done
             else if(viral_load_mothers_done==='')
      {
         
   alert('Enter # viral load tests done for mothers');
   $("#viral_load_mothers_done").focus();    
     
       }
       
       
       
       else {
     var facilitynameandmfl=facility.split("_");        
     var startd=startdate.replace('-','');      
     var startd=startd.replace('-','');      
     var endd=enddate.replace('-','');      
     var endd=endd.replace('-','');      
         
     var facilityname=facilitynameandmfl[1];
            //save data to the db
          saveweeklyupdates(id,facilityname,startdate,enddate,hiv_pos_target_child,hiv_pos_target_adult,hiv_pos_target_total,hiv_pos_child,hiv_pos_adult,hiv_pos_total,new_care_child,new_care_adult,new_care_total,new_art_target_child,new_art_target_adult,new_art_target_total,started_art_child,started_art_adult,started_art_total,viral_load_target_child,viral_load_target_adult,viral_load_target_total,viral_load_done_child,viral_load_done_adult,viral_load_done_total,ipt_target_child,ipt_target_adult,ipt_target_total,ipt_child,ipt_adult,ipt_total,testing_target_child,testing_target_adult,testing_target_total,test_child,test_adult,test_total,pmtct_hiv_pos_target,pmtct_hiv_pos,eid_target,eid_done, viral_load_mothers_target,viral_load_mothers_done,timestamp,user, syncstatus) ;


//call refressh code here
clearweeklyfields();
 //selectsearchdata();
$("#message").show();
$("#actiondone").html("Data Updated Successfully");
//call the function that loads entered data

console.log('weekly data updated');

$('#reportsbutton').click();
$('#inpatient_uptake_cmts').focus();
//setTimeout(delayedrefresh,1800);
       }
       
    
}
   
function saveweeklyupdates(id,facilityname,startdate,enddate,hiv_pos_target_child,hiv_pos_target_adult,hiv_pos_target_total,hiv_pos_child,hiv_pos_adult,hiv_pos_total,new_care_child,new_care_adult,new_care_total,new_art_target_child,new_art_target_adult,new_art_target_total,started_art_child,started_art_adult,started_art_total,viral_load_target_child,viral_load_target_adult,viral_load_target_total,viral_load_done_child,viral_load_done_adult,viral_load_done_total,ipt_target_child,ipt_target_adult,ipt_target_total,ipt_child,ipt_adult,ipt_total,testing_target_child,testing_target_adult,testing_target_total,test_child,test_adult,test_total,pmtct_hiv_pos_target,pmtct_hiv_pos,eid_target,eid_done, viral_load_mothers_target,viral_load_mothers_done,timestamp,user, syncstatus) {
 
 weeklydatadb.get(id).then(function (doc) {
        
  //doc.age = 4;
 //alert(id);
   if(id!=='null' && id!=='' ){
        //doc._id=id;
        doc.facility=facilityname;
        doc.startdate=startdate;
	doc.enddate=enddate;
        
       
             
doc.hiv_pos_target_child=hiv_pos_target_child;
doc.hiv_pos_target_adult=hiv_pos_target_adult;
doc.hiv_pos_target_total=hiv_pos_target_total;
doc.hiv_pos_child=hiv_pos_child;
doc.hiv_pos_adult=hiv_pos_adult;
doc.hiv_pos_total=hiv_pos_total;
doc.new_care_child=new_care_child;
doc.new_care_adult=new_care_adult;
doc.new_care_total=new_care_total;
doc.new_art_target_child=new_art_target_child;
doc.new_art_target_adult=new_art_target_adult;
doc.new_art_target_total=new_art_target_total;
doc.started_art_child=started_art_child;
doc.started_art_adult=started_art_adult;
doc.started_art_total=started_art_total;
doc.viral_load_target_child=viral_load_target_child;
doc.viral_load_target_adult=viral_load_target_adult;
doc.viral_load_target_total=viral_load_target_total;
doc.viral_load_done_child=viral_load_done_child;
doc.viral_load_done_adult=viral_load_done_adult;
doc.viral_load_done_total=viral_load_done_total;
doc.ipt_target_child=ipt_target_child;
doc.ipt_target_adult=ipt_target_adult;
doc.ipt_target_total=ipt_target_total;
doc.ipt_child=ipt_child;
doc.ipt_adult=ipt_adult;
doc.ipt_total=ipt_total;
doc.testing_target_child=testing_target_child;
doc.testing_target_adult=testing_target_adult;
doc.testing_target_total=testing_target_total;
doc.test_child=test_child;
doc.test_adult=test_adult;
doc.test_total=test_total;
       
doc.pmtct_hiv_pos_target=pmtct_hiv_pos_target;
doc.pmtct_hiv_pos=pmtct_hiv_pos;
doc.eid_target=eid_target;
doc.eid_done=eid_done;
doc.viral_load_mothers_target=viral_load_mothers_target;
doc.viral_load_mothers_done=viral_load_mothers_done;
  
            //new percents
            
doc.hiv_pos_yield_perc_child=$("#hiv_pos_yield_perc_child_in").val();
doc.hiv_pos_yield_perc_adult=$("#hiv_pos_yield_perc_adult_in").val();
doc.hiv_pos_yield_perc_all=$("#hiv_pos_yield_perc_all_in").val();
doc.hiv_pos_care_perc_child=$("#hiv_pos_care_perc_child_in").val();
doc.hiv_pos_care_perc_adult=$("#hiv_pos_care_perc_adult_in").val();
doc.hiv_pos_care_perc_all=$("#hiv_pos_care_perc_all_in").val();
doc.started_art_perc_child=$("#started_art_perc_child_in").val();
doc.started_art_perc_adult=$("#started_art_perc_adult_in").val();
doc.started_art_perc_all=$("#started_art_perc_all_in").val();
doc.viral_test_perc_child=$("#viral_test_perc_child_in").val();
doc.viral_test_perc_adult=$("#viral_test_perc_adult_in").val();
doc.viral_test_perc_all=$("#viral_test_perc_all_in").val();
doc.ipt_done_perc_child=$("#ipt_done_perc_child_in").val();
doc.ipt_done_perc_adult=$("#ipt_done_perc_adult_in").val();
doc.ipt_done_perc_all=$("#ipt_done_perc_all_in").val();
doc.tested_perc_child=$("#tested_perc_child_in").val();
doc.tested_perc_adult=$("#tested_perc_adult_in").val();
doc.tested_perc_all=$("#tested_perc_all_in").val();
doc.pmtct_hiv_pos_perc=$("#pmtct_hiv_pos_perc_in").val();
doc.eid_done_perc=$("#eid_done_perc_in").val();
doc.viral_load_mothers_perc=$("#viral_load_mothers_perc_in").val();
            
            
        doc.timestamp=timestamp;
        doc.user=user;
        doc.syncstatus=syncstatus; 
   //alert('called');
  // put them back
  return weeklydatadb.put(doc);
   }
});
 
 
} 
    
  
//==================function to import data

// $('#exportbutton').on('click',function() {
    $("#exportbutton").prop("disabled",false);
     $(this).removeClass('btn-lg btn-default').addClass('btn btn-success');
//});


function importdata(){
    
   // $('#exportbutton').on('click',function() {
    $("#exportbutton").prop("disabled",true);
     $("#exportbutton").removeClass('btn-lg btn-success').addClass('btn btn-default');

                //read db files that have not been synced
    
  $("#exportbutton").hide();
  $("#exportmsg").show();
   $("#exportresponse").append("<b><font color='orange'>Exporting data.. please wait response.</b><br/>");
  
  weeklydatadb.allDocs({include_docs: true, descending: true}).then( function(doc) { 
 
      //read where sync is 0
	  
	   for(c=0;c<doc.total_rows;c++){
               $("#exportbutton").hide();
               $("#exportmsg").show();
               //a variable to check if all comments are added for percents below 80 percent and not amongest the indicators that can be skipped.
            var skipexporting=0;  
            var missingcomment="";
	   var dat={};
	   dat=doc.rows[c];
	     // console.log(dat.doc.facility);
              //how to reference each column 
              
              //dat.doc._id
		  var num=parseInt(c)-1;
	var missingcommentid="";
        if(dat.doc.syncstatus==="No" || dat.doc.syncstatus==="0" || dat.doc.syncstatus==="no")
                        {
            //now do an export via ajax
            console.log(" Exporting data for "+dat.doc.facility+" percent= "+dat.doc.eid_done_perc+"  and comments ="+dat.doc.eid_done_cmts+"*");
            //
           if(parseInt((dat.doc.hiv_pos_care_perc_child)< 90  || (dat.doc.hiv_pos_care_perc_adult)< 90 || (dat.doc.hiv_pos_care_perc_all)< 90 ) && dat.doc.hiv_pos_care_cmts==="" ){missingcommentid="@hiv_pos_care_cmts";skipexporting++; missingcomment+="percentage HIV positive enrolled on care <br/>";}
            else if((parseInt(dat.doc.started_art_perc_child)< 90  || parseInt(dat.doc.started_art_perc_adult)< 90 || parseInt(dat.doc.started_art_perc_all)< 90 ) && dat.doc.started_art_cmts==="" ){missingcommentid="@started_art_cmts";skipexporting++; missingcomment+="percentage of target started on ART <br/>";}
            else if((parseInt(dat.doc.viral_test_perc_child)< 90  || parseInt(dat.doc.viral_test_perc_adult)< 90 || parseInt(dat.doc.viral_test_perc_all)< 90 ) && dat.doc.viral_test_cmts==="" ){missingcommentid="@viral_test_cmts";skipexporting++; missingcomment+="percentage viral load tests done against target <br/>";}
            else if((parseInt(dat.doc.ipt_done_perc_child)< 90  || parseInt(dat.doc.ipt_done_perc_adult)< 90 || parseInt(dat.doc.ipt_done_perc_all)< 90 ) && dat.doc.ipt_done_cmts==="" ){missingcommentid="@ipt_done_cmts";skipexporting++; missingcomment+="percentage IPT done against target <br/>";}
            else if((parseInt(dat.doc.tested_perc_child)< 90  || parseInt(dat.doc.tested_perc_adult)< 90 || parseInt(dat.doc.tested_perc_all)< 90 ) && dat.doc.tested_cmts==="" ){missingcommentid="@tested_cmts"; skipexporting++; missingcomment+="percentage Tested against target <br/> ";}
            else if(parseInt(dat.doc.eid_done_perc)< 90  && dat.doc.eid_done_cmts==="" ){missingcommentid="@eid_done_cmts";skipexporting++; missingcomment+="percentage EID done test done against target <br/>";}
            else if(parseInt((dat.doc.viral_load_mothers_perc)< 90 ) && dat.doc.viral_load_mothers_cmts==="" ){ missingcommentid="@viral_load_mothers_cmts"; skipexporting++; missingcomment=+"percentage viral load tests done for mothers against target <br/>";}
            
           
              var hrf=" <button class='btn-lg button-info' data-dismiss='modal' onclick=\"loadsavedweekelydata('"+dat.doc._id+"','"+dat.doc.facility+"','yes"+missingcommentid+"'); \"> Enter Comments</button>";
           




            
            
    
            
        
        
        if(skipexporting===0){
            
           
        
            //url:'http://104.45.29.195:8080/aphiaplus_moi/importweeklydata',
            
             $.ajax({
                         url:'http://104.45.29.195:8080/aphiaplus_moi/importweeklydata',                            
                    type:'post', 
data:{id:dat.doc._id,
facilityname:dat.doc.facility,
startdate:dat.doc.startdate,
enddate:dat.doc.enddate,        
                   
hiv_pos_target_child:dat.doc.hiv_pos_target_child,
hiv_pos_target_adult:dat.doc.hiv_pos_target_adult,
hiv_pos_target_total:dat.doc.hiv_pos_target_total,
hiv_pos_child:dat.doc.hiv_pos_child,
hiv_pos_adult:dat.doc.hiv_pos_adult,
hiv_pos_total:dat.doc.hiv_pos_total,
new_care_child:dat.doc.new_care_child,
new_care_adult:dat.doc.new_care_adult,
new_care_total:dat.doc.new_care_total,
new_art_target_child:dat.doc.new_art_target_child,
new_art_target_adult:dat.doc.new_art_target_adult,
new_art_target_total:dat.doc.new_art_target_total,
started_art_child:dat.doc.started_art_child,
started_art_adult:dat.doc.started_art_adult,
started_art_total:dat.doc.started_art_total,
viral_load_target_child:dat.doc.viral_load_target_child,
viral_load_target_adult:dat.doc.viral_load_target_adult,
viral_load_target_total:dat.doc.viral_load_target_total,
viral_load_done_child:dat.doc.viral_load_done_child,
viral_load_done_adult:dat.doc.viral_load_done_adult,
viral_load_done_total:dat.doc.viral_load_done_total,
ipt_target_child:dat.doc.ipt_target_child,
ipt_target_adult:dat.doc.ipt_target_adult,
ipt_target_total:dat.doc.ipt_target_total,
ipt_child:dat.doc.ipt_child,
ipt_adult:dat.doc.ipt_adult,
ipt_total:dat.doc.ipt_total,
testing_target_child:dat.doc.testing_target_child,
testing_target_adult:dat.doc.testing_target_adult,
testing_target_total:dat.doc.testing_target_total,
test_child:dat.doc.test_child,
test_adult:dat.doc.test_adult,
test_total:dat.doc.test_total,        
        
pmtct_hiv_pos_target:dat.doc.pmtct_hiv_pos_target,
pmtct_hiv_pos:dat.doc.pmtct_hiv_pos,
eid_target:dat.doc.eid_target,
eid_done:dat.doc.eid_done,
viral_load_mothers_target:dat.doc.viral_load_mothers_target,
viral_load_mothers_done:dat.doc.viral_load_mothers_done,
        
//percents

hiv_pos_yield_perc_child:dat.doc.hiv_pos_yield_perc_child,
hiv_pos_yield_perc_adult:dat.doc.hiv_pos_yield_perc_adult,
hiv_pos_yield_perc_all:dat.doc.hiv_pos_yield_perc_all,
hiv_pos_care_perc_child:dat.doc.hiv_pos_care_perc_child,
hiv_pos_care_perc_adult:dat.doc.hiv_pos_care_perc_adult,
hiv_pos_care_perc_all:dat.doc.hiv_pos_care_perc_all,
started_art_perc_child:dat.doc.started_art_perc_child,
started_art_perc_adult:dat.doc.started_art_perc_adult,
started_art_perc_all:dat.doc.started_art_perc_all,
viral_test_perc_child:dat.doc.viral_test_perc_child,
viral_test_perc_adult:dat.doc.viral_test_perc_adult,
viral_test_perc_all:dat.doc.viral_test_perc_all,
ipt_done_perc_child:dat.doc.ipt_done_perc_child,
ipt_done_perc_adult:dat.doc.ipt_done_perc_adult,
ipt_done_perc_all:dat.doc.ipt_done_perc_all,
tested_perc_child:dat.doc.tested_perc_child,
tested_perc_adult:dat.doc.tested_perc_adult,
tested_perc_all:dat.doc.tested_perc_all,
pmtct_hiv_pos_perc:dat.doc.pmtct_hiv_pos_perc,
eid_done_perc:dat.doc.eid_done_perc,
viral_load_mothers_perc:dat.doc.viral_load_mothers_perc,
     //comments
          hiv_pos_yield_cmts:dat.doc.hiv_pos_yield_cmts,
          hiv_pos_care_cmts:dat.doc.hiv_pos_care_cmts,
          started_art_cmts:dat.doc.started_art_cmts,
          viral_test_cmts:dat.doc.viral_test_cmts,
          ipt_done_cmts:dat.doc.ipt_done_cmts,
         tested_cmts:dat.doc.tested_cmts,
          pmtct_hiv_pos_cmts:dat.doc.pmtct_hiv_pos_cmts,
          eid_done_cmts:dat.doc.eid_done_cmts,
          viral_load_mothers_cmts:dat.doc.viral_load_mothers_cmts,
         
                  
          timestamp:dat.doc.timestamp,
          user:dat.doc.user },
          dataType: 'html',  
                    success: function(data) {
                        console.log(data);
                        $("#exportresponse").append("<b>*"+data+'</b><br/>');
                         updatesyncstatus(dat.doc._id);
                   //doc.syncstatus="Yes"; 
   //alert('called');
  // put them back
                   // return weeklydatadb.put(doc);   
        
                                        }
                        
                         });
            
                        }//end of if skipp exporting === 0
            else {
                
                
                $("#exportresponse").append("<br/><b>NOTE:</b><font color='red'> Data for <b>"+dat.doc.facility+"</b> not uploaded due to missing comment(s) on section <b><i>"+missingcomment+"</i></b></font> "+hrf+" <br/>");
            }
                        }
        
                 
                 
                 //if its last loop show
                 if(c===parseInt(doc.total_rows)-1){
                  $("#exportbutton").show();
                 $("#exportmsg").hide();
                  //$("#exportresponse").append("<b><font color='orange'>Exporting Completed.</b>");
  
                      }
                     
            
                      
          	    } //end of for loop
	 
	
		
  }).catch(function (err){console.log(err)});
          
  //$("#exportbutton").show();
  //$("#exportmsg").hide();	
   //refresh number of uninmported sites.
   //unsynceddata()
        
        
}



function updatesyncstatus(id){
   
weeklydatadb.get(id).then(function (doc) {
 doc.syncstatus='Yes';
 return weeklydatadb.put(doc);
   
});

	
    
}//end of function





function unsynceddata(){
    
    
    weeklydatadb.allDocs({include_docs: true, descending: true}).then( function(doc) { 
 
          
      //read where sync is 0
	  var cnt1=0;
	   for(c=0;c<doc.total_rows;c++){
               
                var dat={};
	   dat=doc.rows[c];
               if(dat.doc.syncstatus==="No"||dat.doc.syncstatus==="0"||dat.doc.syncstatus==="no")
                        {
                            
                         cnt1++;   
                            
                        }
               var displaytext=cnt1+" sites";
               if(cnt1===1){
                   
                    displaytext=cnt1+" site";
               }
              
          	                    } //end of for loop
	 
	$("#unexportedno").html("<br/>( "+displaytext+" )");
        if(cnt1>0){
            $("#exportdataanchor1").show();
            $("#exportdataanchor2").show();
            
        }
		
  }).catch(function (err){console.log(err)});
          
  //$("#exportbutton").show();
  //$("#exportmsg").hide();	
    
    
    
}//end of function


unsynceddata();


function closeapp() 
{
      //navigator.app.exitApp();   // Closes the new window
}


</script>
<script>


//========================================201605 custom percentage calculator======================================


//function fillprogressbar(numer,denomin,progressbarid,datavalueid,cmts){
function percentageindicators(numer,denomin,datavalueid){
    
        //console.log(datavalueid+" "+datavalueid.indexOf("adult"));
       // console.log(" "+cmts);
  
        
        
    var numerator=$("#"+numer).val();
    var denominator=$("#"+denomin).val();
      if(denominator!==''&& numerator!==''){  
    //console.log(denomin+" "+denominator);
    //console.log(numer+" "+numerator);
    var numeratordenominatorvalues=numerator+"/"+denominator;
    var perc="0";
    if(denominator==='0'){
        
        perc=0;
        
                        }
    else if(denominator===''|| numerator===''){
        
        perc=0;
    }
    else {
        
        perc=Math.round((parseInt(numerator)/parseInt(denominator))*100);
        
      
    }
     savepercents(perc,datavalueid);
    //edit graph for display
    //if den and num are not blanks
    if(numerator!=='' && denominator!=='')
    {
  
    $("#"+datavalueid).val(perc);
    
    
   
     if(perc>=90){
        
     //$("#"+datavalueid).css('background-color','green');
     
                }
                   
 else if(perc < 90){
     
       // $("#"+datavalueid).css('background-color','red');  
      
  
                  }
                
                    
     }
    
    
    }
}//end of function





//===================This function excecutes all percent calculation functions on loading and editin module  
function loadallpercents(){
    for(c=0;c<allindicatorsarray.length;c++){
      
            if(allindicatorsarray[c].indexOf("total")===-1){
                
            //call the functions in the onblur method
           // $("#"+allindicatorsarray[c]).trigger("blur");
            $("#"+allindicatorsarray[c]).blur();
          
                                                            }
        
        
                                            }
    
    
    
    
}





//a function to save comments after a user enters them


function savecomments(commentname){

 $("#finishbutton").prop('disabled',true);
  var id=$("#rowid").val();
  var comments=$("#"+commentname).val();
 
 console.log(comments);
 
   weeklydatadb.get(id).then(function (doc) {
  
   //if(comments!==''){
       
       if(commentname==='hiv_pos_yield_cmts'){
        doc.hiv_pos_yield_cmts=comments;   
       }
       else if (commentname==='hiv_pos_care_cmts'){
           doc.hiv_pos_care_cmts=comments;
                }

            else if (commentname==='started_art_cmts'){
           doc.started_art_cmts=comments;
                }
                
                else if (commentname==='viral_test_cmts'){
           doc.viral_test_cmts=comments;
                }
                
                else if (commentname==='ipt_done_cmts'){
           doc.ipt_done_cmts=comments;
                }
                
                else if (commentname==='tested_cmts'){
           doc.tested_cmts=comments;
                }
                
                else if (commentname==='pmtct_hiv_pos_cmts'){
           doc.pmtct_hiv_pos_cmts=comments;
                }
                
                else if (commentname==='eid_done_cmts'){
           doc.eid_done_cmts=comments;
                }
                
                else if (commentname==='viral_load_mothers_cmts'){
           doc.viral_load_mothers_cmts=comments;
                }
  

    

  return weeklydatadb.put(doc);
   //}
  
    
});
//updatesyncstatus(id);    


   
    
}// end of save comments function

function disablefinish(){
    
     $("#finishbutton").prop('disabled',true);
    // alert('disable');
     //setTimeout(enablefinish(),2000);
}

function enablefinish(){
    
     $("#finishbutton").prop('disabled',false);
}

function savepercents(percent,percentname){
 
 
  var id=$("#rowid").val();
  
  var percentagevalue=percent;
 
 
   //weeklydatadb.get(id).then(function (doc) {
  
   if(percent!=='' && percent!=='NaN'){
     // alert(percent+" "+percentname);
     
        //doc.inpatient_uptake_perc=percentagevalue;
        $("#"+percentname).val(percentagevalue);
      
       
   }
  //});

    
}// end of save comments function



function isuseradded(){
   var cnt=0;
        var username="";
    
     //if(username===''){    
    
//}
        
userdb.allDocs({include_docs:true,ascending: true}).then(function (doc) 
{
    
    if(doc.total_rows===0){
        
        $('#adduserbutton').click()
    }

 username=doc.username;

});
  
}

isuseradded();



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
    return false;
});
$("#exportdataform").submit(function(e){
    return false;
});

$("#reportsform").submit(function(e){
    return false;
});

 //$('input').css('border-color','#337ab7');


//prevent numbers scrolling

$('form').on('focus', 'input[type=number]', function (e) {
  $(this).on('mousewheel.disableScroll', function (e) {
    e.preventDefault()
  })
});
$('form').on('blur', 'input[type=number]', function (e) {
  $(this).off('mousewheel.disableScroll')
});

//PCA


function getData() {
  return [["Month", "3 Months", "", "", "6 Months", "", "", "9 Months", "", "", "12 Months", "", "", "24 Months", "", ""],
    ["indicator", "KP", "NP", "Total", "KP", "NP", "Total", "KP", "NP", "Total", "KP", "NP", "Total", "KP", "NP", "Total"],
    ["A.Enrolled into cohort","","","","","","","","","","","","","","",""],
    ["B.Transfers In(T.I)","","","","","","","","","","","","","","",""],
    ["C.Transfers Out(T.O)","","","","","","","","","","","","","","",""],
    ["D.Net Cohort(A+B-C)", "", "", "","", "", "","", "", "","", "", "","", "", ""],
    ["E.Defaulters", "", "", "","", "", "","", "", "","", "", "","", "", ""],
    ["F.Lost to follow up (LTFU)", "", "", "","", "", "","", "", "","", "", "","", "", ""],
    ["G.Reported Dead", "", "", "","", "", "","", "", "","", "", "","", "", ""],
    ["H.Stopped", "", "", "","", "", "","", "", "","", "", "","", "", ""],
    ["I.Alive and Active on Treatment", "", "", "","", "", "","", "", "","", "", "","", "", ""],
    ["J.Viral Load Collected", "", "", "","", "", "","", "", "","", "", "","", "", ""],
    ["K.Virally suppressed (VL<1000)", "", "", "","", "", "","", "", "","", "", "","", "", ""],
    ["L.percentage Retained(I/D*100)", "", "", "","", "", "","", "", "","", "", "","", "", ""]];
}

// Instead of creating a new Handsontable instance with the container element passed as an argument,
// you can simply call .handsontable method on a jQuery DOM object.
//var $container = $("#hot");
//
//$container.handsontable({
//  data: funguacohort(),
//  startRows: 14,
//  startCols: 16,
//  minRows: 5,
//  minCols: 5,
//  maxRows: 14,
//  maxCols: 23,
//  rowHeaders: false,
//  colHeaders: false,
//  fixedColumnsLeft: 1,
//  minSpareRows: 1,
//   manualColumnResize: true, 
// allowInsertRow: false,
// allowInsertColumn: false,
// allowRemoveRow: false,
// allowRemoveColumn: false,
// allowMergeCells: false,
//  className: "htLeft",  
//
//  mergeCells: [
//    {row: 0, col: 1, rowspan: 1, colspan: 3}
//    ,{row: 0, col: 4, rowspan: 1, colspan: 3}
//    ,{row: 0, col: 7, rowspan: 1, colspan: 3}
//    ,{row: 0, col: 10, rowspan: 1, colspan: 3}
//    ,{row: 0, col: 13, rowspan: 1, colspan: 3}
//    ,{row: 0, col: 16, rowspan: 1, colspan: 3}
//    ,{row: 0, col: 19, rowspan: 1, colspan: 3}
//    ,{row: 0, col: 22, rowspan: 1, colspan: 3}
//  ],
//  contextMenu: true
//});
//
//// This way, you can access Handsontable api methods by passing their names as an argument, e.g.:
//var hotInstance = $("#hot").handsontable('getInstance');
//
//hotInstance.updateSettings({
//  cells: function (row, col, prop) {
//    var cellProperties = {};
// var readonlyz=["Adults","Children","Total","3 Months","6 Months","9 Months","12 Months","24 Months","36 Months","48 Months","60 Months","indicator","Month","A. Enrolled into cohort","B. Transfers In(T.I)","C. Transfers Out(T.O)","D. Net Cohort(A+B-C)","E. Defaulters","F. Lost to follow up (LTFU)","G. Reported Dead","H. Stopped","I. Alive and Active on Treatment","J. Viral Load Collected","K. Virally suppressed (VL<1000)","L. % Retained(I/D*100)","KP","NP"];
//    for (a=0;a<readonlyz.length;a++){
//        
//        if (hotInstance.getData()[row][prop] === readonlyz[a]) {
//      cellProperties.readOnly = true;
//    }
//      
//    }
//    return cellProperties;
//  }
//});
//


//Below code will render one row ,similarly apply the loop to modify multiple row.


funguacohort();

function funguacohort(){    
    
    var cohortname=$("#cohortttype").val();
    var cohortyear=$("#year").val();
    var cohortmonth=$("#month").val();
    var facil=$("#facilityname").val();
    
    if(cohortyear==='' ){
        
     $("#year").focus();    
        
    }
    else  if(cohortmonth==='' ){
        
     $("#month").focus();   
        
    }
    else   if(cohortname==='' ){
        
     $("#cohortttype").focus();   
        
    }
    else   if(facil==='' ){
        
     $("#cohortttype").focus();   
        
    }
 
  else if(cohortname!=='' && cohortyear!=='' && cohortmonth!=='' && facil!==''){
  
  
  
   $.ajax({
                         url:'loadcohort?cohort='+cohortname+'&year='+cohortyear+'&month='+cohortmonth+"&facility="+facil,                            
                    type:'post',  
                    dataType: 'html',  
                    success: function(data) {
                        console.log(data);
                        
                        
                        $("#tabledata").html(data);
                        $("#savebutton").show();
                        
                        
                        
//                        var rows = $('#hot').handsontable('countColumns');
//        for(i = 0; i < rows-1; i++){
//            $('#hot').handsontable('setDataAtCell', 3,i, i+1);
//           // $('#hot').handsontable('setDataAtCell', row,column, value);
//                                   }
                         
                        
                    }});
                
        }//end of if
    
}








function autosave(elementi,uniqueid){
    
      
           var val=$("#"+elementi).val();           
            var cohortiname=$("#cohortttype").val(); 
            
        
    
    if(val!=='' && cohortiname!==''){
        
        console.log(elementi+"  after iis  "+uniqueid);
        
            $.ajax({
url:'save?col='+elementi+'&id='+uniqueid+'&value='+val+"&cohort="+cohortiname,
type:'post',
dataType:'html',
success:function (data){
   if(data.trim()!=="success"){
//     $("#"+col).css({'background-color' : 'red'});
        }
    else{
        
   
 
      $("#"+elementi).css({'background-color' : '#CCFFCC'});
     
   
}
}
             
             });
         }
         
        
    }



</script>

	</body>
</html>
