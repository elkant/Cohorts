<%-- 
    Document   : logout
    Created on : May 13, 2015, 1:01:08 PM
    Author     : Geofrey Nyabuto
--%>
<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server

    if ((session.getAttribute("level")==null) || (session.getAttribute("userid")==null)) {
        response.sendRedirect("index.jsp");
    } 
       else{
%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>log out</title>
    </head>
    <body>
          <%
  session.invalidate();
                response.sendRedirect("index.jsp");
            }
          
%>

    </body>
</html>
