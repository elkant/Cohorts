





<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <script src="assets/js/jquery-1.8.3.min.js"></script>  
        <title></title>
        
        <script>
            
            
            
   jQuery(document).ready(function() {       
         // initiate layout and plugins
       
    $("ul li").on("click", function() {
      $("ul li").removeClass("active");
      $(this).addClass("active");
    });
   });
            
        </script>
        
    </head>
    <body>










<ul>
     
     <br/>
     <br/>
     
            <li>
               <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
               <div class="sidebar-toggler hidden-phone"></div>
               <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
            </li>
            <li>
            
            </li>
            <li class="start " style="background-color: #4b8df8;">
               <a href="index.jsp">
             <span class="title" ><b><%if(session.getAttribute("userid")!=null){out.println("Hi "+session.getAttribute("fullname").toString());} else {out.println("<a style='color:green;' href='clinicalhome.jsp'><i class='icon-plus'></i><b>Home</b></a>");}%></b></span>
               </a>
                </li>
                <br/>
                 <br/>
                 <br/>
                 <br/>
                  <br/>
                  
                 <br/>
                
                <%
                   // if(session.getAttribute("userid")!=null){
                     if(1==2){   
                        
    %>
                <li class="active" style="border-top: 1px solid #e2e2e2 !important;">
                <a href="index.jsp">
               <i class="icon-home"></i> 
               <span class="title">MCA/ACA Data Entry</span>
               </a>
            </li>
           
           <li class="has-sub ">
               <a href="#">
               <i class="icon-signin"></i>
               <span class="title">Excel Uploads</span>
               <span class="arrow "></span>
               </a>
               <ul class="sub">
    
     <%if(1==1){%> 
  <li><a href='importpmtctrri.jsp'><i class='icon-plus'></i>PMTCT RRI ( Weekly )</a></li>
  <li><a href='importDtriangulation.jsp'><i class='icon-plus'></i>Daily Data Triangulation</a></li>
  <li><a href="importpns.jsp"><i class="icon-plus"></i>Upload PNS</a></li>
  <li><a href='MissingReports.jsp'><i class='icon-stop'></i>Surge Missing Reports</a></li>
  <li ><a  target='_blank' href='https://usaidtujengejamii.org:8443/htslive/livesummary.jsp'><i class='icon-list'></i>RRI Live Summary</a></li>
  <li ><a style='' href='importDtriangulation.jsp'><i class='icon-plus'></i>Daily Data Triangulation</a></li>
  <li ><a target='_blank' style='' href='https://usaidtujengejamii.org:8443/InternalSystem//fmattupload.jsp'><i class='icon-plus'></i>Daily Fmatt Upload</a></li>
  <li ><a href='importpns.jsp'><i class='icon-plus'></i>Daily PNS Data Upload</a></li>
  <li ><a href='importder.jsp'><i class='icon-plus'></i>Daily ART Data Upload</a></li>
  <li ><a target='_blank'  href='https://usaidtujengejamii.org:8443/Cohorts/upi_main.jsp'><i class='icon-plus'></i>Weekly UPI verification status</a></li> 
  <li ><a href='importcovid.jsp'><i class='icon-plus'></i>Covid 19 Data Upload</a></li>
  <li ><a href='pnsreport.jsp'><i class='icon-list'></i>PNS Reports</a></li>
  <li ><a href='surgereports.jsp'><i class='icon-list'></i>Surge Reports and Tracker</a></li>
  <li ><a href='MissingReports.jsp'><i class='icon-stop'></i>View Missing Reports</a></li>
                 <%}%>
               </ul>
            </li>
            
            
            
            <li class="has-sub ">
               <a href="#">
               <i class="icon-signin"></i>
               <span class="title">Reports</span>
               <span class="arrow "></span>
               </a>
               <ul class="sub">
    
     <%if(1==1){%> 
                 <li><a href="pnsreport.jsp"><i class="icon-plus"></i>PNS reports</a></li>
                 <li><a href='surgereports.jsp'><i class='icon-list'></i>Surge Reports and Tracker</a></li>
                 <li><a href='MissingReports.jsp'><i class='icon-stop'></i>View Missing Reports</a></li>
                
                 <%}%>
               </ul>
            </li>  
             
            
           
            
           
            <li class="">
               <a href="logout.jsp">
               <i class="icon-user"></i> 
               <span class="title">Log Out</span>
               </a>
            </li>
            
         <%  } 
else {
  
  out.println("  <li><a style='color:green;' href='dailyart.jsp'><i class='icon-plus'></i><b>Daily ART Web</b></a></li>");
  out.println("  <li><a style='' href='importpmtctrri.jsp'><i class='icon-plus'></i>PMTCT RRI ( Weekly )</a></li>");
  out.println("<li ><a  target='_blank' href='https://usaidtujengejamii.org:8443/htslive/livesummary.jsp'><i class='icon-list'></i>RRI Live Summary</a></li>");
  out.println("<li ><a style='' href='importDtriangulation.jsp'><i class='icon-plus'></i>Daily Data Triangulation</a></li>");
  out.println("<li ><a target='_blank' style='' href='https://usaidtujengejamii.org:8443/InternalSystem//fmattupload.jsp'><i class='icon-plus'></i>Daily Fmatt Upload</a></li>");
  out.println("<li ><a href='importpns.jsp'><i class='icon-plus'></i>Daily PNS Data Upload</a></li>");
  out.println("<li ><a href='importder.jsp'><i class='icon-plus'></i>Daily ART Data Upload</a></li>");
out.println("<li ><a target='_blank'  href='https://usaidtujengejamii.org:8443/Cohorts/upi_main.jsp'><i class='icon-plus'></i>Weekly UPI verification status</a></li>"); 
//  out.println("<li ><a href='importcovid.jsp'><i class='icon-plus'></i>Covid 19 Data Upload</a></li>");
//  out.println("<li ><a href='pnsreport.jsp'><i class='icon-list'></i>PNS Reports</a></li>");
  out.println("<li ><a href='surgereports.jsp'><i class='icon-list'></i>Surge Reports and Tracker</a></li>");
  out.println("<li ><a href='MissingReports.jsp'><i class='icon-stop'></i>View Missing Reports</a></li>");
  out.println("<li ><a href='clinicalhome.jsp'><i class='icon-stop'></i>Home</a></li>");
     } %>
            
         </ul>
      
    </body>
</html>
