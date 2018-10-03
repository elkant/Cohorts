/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author EKaunda
 */
public class getCohortMonths extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            String data="";
            Calendar cl= Calendar.getInstance();
            
            String year=""+(cl.get(Calendar.YEAR));
            String monthstring=""+(cl.get(Calendar.MONTH)+1);
            String cohorttype="art";
            if(request.getParameter("ct")!=null && !request.getParameter("ct").equals("") ){
            cohorttype=request.getParameter("ct");
            }
             if(request.getParameter("yr")!=null && !request.getParameter("yr").equals("")){
            year=request.getParameter("yr");
        }
              if(request.getParameter("mn")!=null && !request.getParameter("mn").equals("") && !request.getParameter("mn").equals("Select Month")){
            monthstring=request.getParameter("mn");
              }
            
            loadcohort lc= new loadcohort();
            
data="<option value=''>Select Month</option>"+
"<option value='3m'>3m. Cohort ("+lc.getpreviousmonth(new Integer(year),new Integer(monthstring), -3)+") </option>"+
"<option value='6m'>6m. Cohort ("+lc.getpreviousmonth(new Integer(year),new Integer(monthstring), -6)+")</option>"+
//"<option value='9m'>9m. Cohort ("+lc.getpreviousmonth(new Integer(year),new Integer(monthstring), -9)+") </option>"+
"<option value='12m'>12m. Cohort ("+lc.getpreviousmonth(new Integer(year),new Integer(monthstring), -12)+") </option>"+
"<option value='24m'>24m. Cohort ("+lc.getpreviousmonth(new Integer(year),new Integer(monthstring), -24)+") </option>";

if(cohorttype.equalsIgnoreCase("art")){
data+="<option value='36m'>36m. Cohort("+lc.getpreviousmonth(new Integer(year),new Integer(monthstring), -36)+")</option>";
//"<option value='48m'>48m. Cohort("+lc.getpreviousmonth(new Integer(year),new Integer(monthstring), -48)+")</option>"+
//+"<option value='60m'>60m. Cohort("+lc.getpreviousmonth(new Integer(year),new Integer(monthstring), -60)+")</option>";
}     
          
            
            out.println(data);
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

}
