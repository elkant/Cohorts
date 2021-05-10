<%-- 
    Document   : form1a
    Created on : Nov 19, 2018, 8:50:03 AM
    Author     : EKaunda
--%>

<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<!-- BEGIN HEAD -->
<head>
   <meta charset="utf-8" />
   <title>DER</title>
   
     <script src="assets/js/jquery-1.8.3.min.js"></script>    
     <link rel="shortcut icon" href="images/index.JPG"/>
   <meta content="width=device-width, initial-scale=1.0" name="viewport" />
   <meta content="" name="description" />
   <meta content="" name="author" />
   <link href="bootstrap4/css/bootstrap.css" rel="stylesheet" />
   <!--<link href="assets/bootstrap/css/bootstrap.css" rel="stylesheet" />-->
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
   <link rel="stylesheet" href="assets/bootstrap-toggle-buttons/static/stylesheets/bootstrap-toggle-buttons.css" />
   <link rel="stylesheet" href="assets/data-tables/DT_bootstrap.css" />
   <link rel="stylesheet" type="text/css" href="assets/bootstrap-daterangepicker/daterangepicker.css" />
   <link rel="stylesheet" type="text/css" href="assets/uniform/css/uniform.default.css" />
 
<link rel="stylesheet" href="select2/css/select2.css"/>
   
   <script type="text/javascript" src="js/noty/jquery.noty.js"></script>
<script type="text/javascript" src="js/noty/layouts/top.js"></script>
<script type="text/javascript" src="js/noty/layouts/center.js"></script>
<script type="text/javascript" src="js/noty/themes/default.js"></script>

<link rel="stylesheet" href="js/jquery.restable.css"/>

 <style>
fieldset.formatter {
    border: 2px groove black !important;
   
    /*padding: 0 1.4em 1.4em 1.4em !important;*/
    margin: 0 0 1.5em 0 !important;
    -webkit-box-shadow:  0px 0px 0px 0px #000;
            box-shadow:  0px 0px 0px 0px #000;
   
}

legend.formatter {
    border: 0px groove black !important;
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
    width: 100%;
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
td{
 text-align: center ;   
}
</style>
<script type="text/javascript" src="js/fas/sum_values.js"></script>
<script type="text/javascript" src="js/fas/validation.js"></script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body onkeydown="if (event.keyCode===13) {event.keyCode=9; return event.keyCode }">
    
  <!-------------------------------------------dialog box for unvalidated facils-------------------------------------------------->  
  
    
    
   <!-- BEGIN HEADER -->
   <div class="header navbar navbar-inverse navbar-fixed-top">
      <!-- BEGIN TOP NAVIGATION BAR -->
      <div class="navbar-inner">
         <div class="container-fluid">
            <!-- BEGIN LOGO -->
                      <div class="control-group">
                             <div style="float:right;"> 
                                 
                                

                                    <font color="white" size="3px"><b>Date: </b></font>  
                               
                              
                                    
                                   
                                   <font color="white" size="3px" margin-left="3px"><b>Facility : </b></font>
                              
                                 <select onchange="updatefacilsession();" style="width:200px;color:black;" data-placeholder="Facility" required class="chosen-with-diselect span6" tabindex="-1"  id="facility" name="facility">
                                    <option value=""></option>
                                 </select>
                             
                             </div>
                              
                           </div>
            <!-- END LOGO -->
            <!-- BEGIN RESPONSIVE MENU TOGGLER -->
            <a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
            <img src="assets/img/menu-toggler.png" alt="" />
            </a>          
            <!-- END RESPONSIVE MENU TOGGLER -->            
            <!-- BEGIN TOP NAVIGATION MENU -->              
            <ul class="nav pull-left">
              
               <li class="dropdown user">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                 
                  <span class="username">Welcome</span>
                  <i class="icon-angle-down"></i>
                  </a>
                  <ul class="dropdown-menu">
                     <li><a href="editProfile.jsp"><i class="icon-user"></i>User Profile</a></li>
                   
                     <li class="divider"></li>
                     <li><a href="logout.jsp"><i class="icon-key"></i> Log Out</a></li>
                  </ul>
               </li>
               <!-- END USER LOGIN DROPDOWN -->
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
      <div class="page-sidebar nav-collapse collapse show scroller">
         <!-- BEGIN SIDEBAR MENU -->         
       <%@include file="menu.jsp"%>
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
                    
             
                  </h3>
                  
               </div>
            </div>
            <!-- END PAGE HEADER-->
            <!-- BEGIN PAGE CONTENT-->
            <div class="row-fluid" style="">
                <div class="span12" style="">
                  <!-- BEGIN SAMPLE FORM PORTLET-->   
                  <div class="portlet box " style="">
                     <div class="portlet-title">          
                     <div class="portlet-body form">
                        <!-- BEGIN FORM-->
          
                        <!--<div class="accordion" style="overflow-x:scroll;">-->
                        <!--style="overflow-x:scroll;height:80vh;"-->  
                        <div class="accordion"id="form1a_accordion">
                            <div style="color:#000000; font-size: 30px; font-weight: 300; text-align: center; margin-top: 10%;"> Please wait, we are loading Der ... <img src="images/utube.gif"></div>
                       </div>   
                       <!--</div>-->   
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
       
       &copy; Usaid Tujenge Jamii | USAID <%=year%>.
<!--      <div class="span pull-right">
         <span class="go-top"><i class="icon-angle-up"></i></span>
      </div>-->
   </div>
   <!-- END FOOTER -->
   <!-- BEGIN JAVASCRIPTS -->    
   <!-- Load javascripts at bottom, this will reduce page load time -->

   <script src="assets/breakpoints/breakpoints.js"></script>       
   <script src="bootstrap4/js/bootstrap.js"></script>   
        <!--<script src="assets/bootstrap/js/bootstrap.js"></script>   -->
   <script type="text/javascript" src="assets/bootstrap-fileupload/bootstrap-fileupload.js"></script>
   <script src="assets/js/jquery.blockui.js"></script>
   <script src="assets/js/jquery.cookie.js"></script>
   <!-- ie8 fixes -->
   <!--[if lt IE 9]>
   <script src="assets/js/excanvas.js"></script>
   <script src="assets/js/respond.js"></script>
   <![endif]-->
   <script type="text/javascript" src="assets/chosen-bootstrap/chosen/chosen.jquery.min.js"></script>
   <script type="text/javascript" src="assets/uniform/jquery.uniform.min.js"></script>
   <script type="text/javascript" src="assets/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script> 
   <script type="text/javascript" src="assets/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
   <script type="text/javascript" src="assets/jquery-tags-input/jquery.tagsinput.min.js"></script>
   <script type="text/javascript" src="assets/bootstrap-toggle-buttons/static/js/jquery.toggle.buttons.js"></script>
   <script type="text/javascript" src="assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
   <script type="text/javascript" src="assets/clockface/js/clockface.js"></script>
   <script type="text/javascript" src="assets/bootstrap-daterangepicker/date.js"></script>
   <script type="text/javascript" src="assets/bootstrap-daterangepicker/daterangepicker.js"></script> 
   <script type="text/javascript" src="assets/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>  
   <script type="text/javascript" src="assets/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
   <script src="assets/js/app.js"></script>   
    <script src="select2/js/select2.js"></script>
    <script src="js/notify.js"></script>
    <script type="text/javascript" src="js/jquery.restable.js"></script>
    <script type="text/javascript" src="js/jquery.fixedheadertable.min.js"></script>
   <script>
       
      jQuery(document).ready(function() { 
         // initiate layout and plugins
         
      jQuery(window).unload(function() {
        return "Bye now!";
        });


         load_data();
    $.ajax({
url:'loadFacilities',
type:'post',
dataType:'html',
success:function (data){
       $("#facility").html(data);
     
        $('#facility').select2();   
}

}); 
         
 
  
$.ajax({
    url:'loadYear',
    type:'post',
    dataType:'html',
    success:function (data){
         $("#year").html(data);
         loadmonths();
        
                           }
    
    
});
//               
     });
      
      

function save_data(form_name,section_id){
    //console.log(loadValidation(section_id));
    
   
    
    var url_save = "save_data";
    
     $("#validate_"+section_id).notify(
        'Please wait, data is saving ...',
        {position:"top",
        autoHide: false,
        className: 'info'
});
//    var url_load = "load_form1a";
    var form_data = $('#'+form_name).serialize();
    $.post(url_save,form_data , function(output) {
        var response = JSON.parse(output);
        var code = response.code;
        var message = response.message;
        var output_type="";
        if(code==0){
          output_type="error";  
        }
        else if(code==1){
         output_type="success"; 
         section_saved(section_id);
        }
        else{
        output_type="warning";    
        }
        $("#validate_"+section_id).notify(
        message,
        {position:"top",
        autoHide: true,
        autoHideDelay: 10000,
        className: output_type, //error for error options
        globalPosition: 'top left'
});
    });
    
        
}

    function load_data(){
        var url = "loadder";
         $.ajax({
            url:url,
            type:'post',
            dataType:'html',
            success:function (data){
                 $("#form1a_accordion").html(data);
                 $(window).ready(function () {
    //$.ReStable();
     //$('.responsive_table').ReStable();
     
     // $('.responsive_table').fixedHeaderTable();
     
});
            }
        }); 
    }
function loadmonths(){
      
      var yr=document.getElementById("year").value;
//      alert(yr);
              $.ajax({
url:'loadMonth?year='+yr,
type:'post',
dataType:'html',
success:function (data){
    $("#month").html(data);
//    if($("#month").val('')){
//        
//    }
// location.reload();
    
       //document.getElementById("month").innerHTML=data;
      // App.init();       
}


});  
      
      
      }
      

 function sendtosessionyear(){
      
      var yr=document.getElementById("year").value;
  
     
    
              $.ajax({
url:'monthyearsession?year='+yr,
type:'post',
dataType:'html',
success:function (data){
//    $("#month").html(data);     
     loadmonths(); 
     location.reload();
       //document.getElementById("month").innerHTML=data;
      // App.init();  
        
}


});  
 }	  
	  

        function sendtosessionmonth(){
      
    
      var month=document.getElementById("month").value;
    
  
              $.ajax({
url:'monthyearsession?month='+month,
type:'post',
dataType:'html',
success:function (data){
//    $("#month").html(data);     
      location.reload();
       //document.getElementById("month").innerHTML=data;
      // App.init();     
}


});  
      
      
      }
	  
             
             //AUTOUPDATING FUNCTION
           
           String.prototype.endsWith = function(suffix) {
    return this.match(suffix+"$") == suffix;
};
             
             function autosave(col){
//            var achieved=document.getElementById(col).value;
         document.getElementById("newform").innerHTML="<font color='red'><b>Form Not Validated.<img width='20px' height='20px' src='images/notValidated.jpg' style='margin-left:10px;'></b></font>"; 
         $("#"+col).css({'background-color' : '#CCFFCC'});         
             }
           
             
          
            function numbers(evt){
var charCode=(evt.which) ? evt.which : event.keyCode
if(charCode > 31 && (charCode < 48 || charCode>57)){
return false;
}

else{
return true;
}
}
          
      
      //a function to update the selected session
      
      function updatefacilsession(){
          
        var facil=document.getElementById("facility").value;
        $.ajax({
url:'updatefacilitysession?facil='+facil,
type:'post',
dataType:'html',
success:function (data){      
    location.reload();
    //  $("#"+col).css({'background-color' : '#CCFFCC'});
                       }
             
             });    
          
          
          
      }
 
 
  $('body').on('keydown', 'input, select, textarea', function(e) {
var self = $(this)
  , form = self.parents('form:eq(0)')
  , focusable
  , next
  , prev
  ;

if (e.shiftKey) {
 if (e.keyCode == 13) {
     focusable =   form.find(':input:visible,a,select,button,textarea').filter(':not([readonly])');
     prev = focusable.eq(focusable.index(this)-1); 

     if (prev.length) {
        prev.focus();
        prev.select();
     } else {
        form.submit();
    }
  }
}
  else
if (e.keyCode == 13) {
    focusable = form.find(':input:visible,a,select,button,textarea').filter(':not([readonly])');
    next = focusable.eq(focusable.index(this)+1);
    if (next.length ) {
      //  if ( next.is('[readonly]') ) {}
      
        next.focus();
        next.select();
        
    } else {
        form.submit();
    }
    return false;
}
});
 
 
     
function loadfacils(){
      var subcounty=document.getElementById("subcounty").value;  
                    $.ajax({
url:'loadFacilities?subcounty='+subcounty,
type:'post',
dataType:'html',
success:function (data){
       $("#facility").html(data);
         if(document.getElementById("facility").value!==''){
      updatefacilsession();
      
     
      }  
      $('#facility').select2();  
         // $("#facility").chosen();
       
       
}


}); 
         
         
        }
      
 
 
    function loadcounty(){
        
        
        $.ajax({
            url:'loadCounty',
            type:'post',
            dataType:'html',
            success:function (data){
                $("#county").html(data);
                loadsubcounty();
              //  App.init();   
            }
            
            
        });
        
    }
    
    
       function loadsubcounty(){
        
        var county=document.getElementById("county").value;
        $.ajax({
            url:'loadSubcounty?county='+county,
            type:'post',
            dataType:'html',
            success:function (data)
            {
                $("#subcounty").html(data);
              //  App.init();   
            }
            
            
        });
        
    }
 
 loadcounty(); 
 
 
 
      
   </script>
   <script>
       
       
   </script>
   
   
   
   <!-- END JAVASCRIPTS -->   
</body>
<!-- END BODY -->
</html>

