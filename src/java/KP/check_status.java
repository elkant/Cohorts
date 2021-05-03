/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KP;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 * @author Ekaunda
 */
public class check_status extends HttpServlet {
String load_type,message;
int pos=0;
    HttpSession session;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        session = request.getSession();
        
        String refresh_page="false";
        
        try {
         load_type = request.getParameter("load_type");
            JSONObject obj = new JSONObject();
         pos = 0;
         message = "Uknown Excel Loads";
    
        
         
          if(load_type.equalsIgnoreCase("kpform1a"))
         {
              if(session.getAttribute("kpform1a")!=null){
             message = session.getAttribute("kpform1a").toString();
             if(isNumeric(session.getAttribute("kpform1a_count").toString())){
             pos = Integer.parseInt(session.getAttribute("kpform1a_count").toString());
             }
            }
             
              if(session.getAttribute("kpref_form1a")!=null){
                  refresh_page=session.getAttribute("kpref_form1a").toString();
                  //session.setAttribute("message", " <img src=\"images/failed.png\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b id=\"notify\">ERROR: Form Completed with a Validation Error. Check the errors sheet on the Data Quality Download</b> ");
              session.removeAttribute("kpref_form1a");
              
              }
              
         }
         
         obj.put("count", pos);
         obj.put("message", message);
         obj.put("refreshpage", refresh_page);
         
            out.println(obj);
          //  System.out.println("Status"+obj);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

public boolean isNumeric(String s) {  
    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
}
}
