





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
             <span class="title" ><b><%if(session.getAttribute("userid")!=null){out.println("Hi "+session.getAttribute("fullname").toString());} else {out.println("Sign in");}%></b></span>
               </a>
                </li>
                <br/>
                 <br/>
                 <br/>
                 <br/>
                  <br/>
                  
                 <br/>
                <%if(session.getAttribute("userid")!=null){%>
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
                 <li ><a href="importpns.jsp"><i class="icon-plus"></i>Upload PNS</a></li>
                 <li ><a href="importart.jsp"><i class="icon-edit"></i>Upload ACA</a></li>
                 <li ><a href="importpmtct.jsp"><i class="icon-plus"></i>Upload MCA</a></li>
                 <li ><a href="importstf.jsp"><i class="icon-lock"></i>Upload STF Defaulters</a></li>                  
                 <li ><a href="importdefaulter.jsp"><i class="icon-hospital"></i>Upload New Defaulter</a></li>
                
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
                 <li ><a href="pnsreport.jsp"><i class="icon-plus"></i>PNS reports</a></li>
                 <li ><a href="aca_mca_reports.jsp"><i class="icon-edit"></i>ACA/MCA Reports</a></li>
                 <li ><a href="stf_newdefaulter_reports.jsp"><i class="icon-plus"></i>STF/New Defaulter Reports</a></li>
                 
                
                 <%}%>
               </ul>
            </li>  
             
            
           
            
           
            <li class="">
               <a href="logout.jsp">
               <i class="icon-user"></i> 
               <span class="title">Log Out</span>
               </a>
            </li>
            
         <%  } else {

  out.println("<li ><a href='importpns.jsp'><i class='icon-plus'></i>PNS Data Upload</a></li>");
  out.println("<li ><a href='pnsreport.jsp'><i class='icon-list'></i>PNS reports</a></li>");
   
   } %>
            
         </ul>
      
    </body>
</html>
