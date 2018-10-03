/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loaders;

import db.dbConn;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author EKaunda
 */
public class loadmonth extends HttpServlet {

    HttpSession session;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           // JSONObject ob = new JSONObject();
           
           session=request.getSession();
           
            Calendar cal = Calendar.getInstance();
            int curyear = cal.get(Calendar.YEAR);
            int curmonth = cal.get(Calendar.MONTH) + 1;
            int passedyear=curyear;
            
            if(request.getParameter("yr")!=null && !request.getParameter("yr").equals("")){
             passedyear=new Integer(request.getParameter("yr"));
            }
            String currselected = "";
           
           if(session.getAttribute("mn")!=null){
           
           currselected=session.getAttribute("mn").toString();
           
           }
       String allpath = getServletContext().getRealPath("/months.json");    
           
JSONParser parser = new JSONParser(); 
JSONArray a = (JSONArray) parser.parse(new FileReader(allpath));

String mwezi="<option value=''>Select Month</option>";

            for (Object o : a) {
                JSONObject monthsobj = (JSONObject) o;

                String monthid = monthsobj.get("monthid").toString();
                String monthname = monthsobj.get("monthname").toString();
                String monthnumber = monthsobj.get("monthnumber").toString();

                if (curyear == passedyear) {

                    if (currselected.equals(monthnumber)) {
                        mwezi += "<option selected value='" + monthnumber + "'>" + monthname + "</option>";
                    } else {

                        if (new Integer(monthid) <= (curmonth-1)) {
                            mwezi += "<option  value='" + monthnumber + "'>" + monthname + "</option>";

                        } else {

                            mwezi += "<option disabled  value='" + monthnumber + "'>" + monthname + "</option>";
                        }

                    }

                } else {

                    if (currselected.equals(monthnumber)) {
                        mwezi += "<option selected value='" + monthnumber + "'>" + monthname + "</option>";
                    } else {

                        mwezi += "<option  value='" + monthnumber + "'>" + monthname + "</option>";

                    }

                }

            }// end of for object
            
            
            
            out.println(mwezi);
        } catch (ParseException ex) {
            Logger.getLogger(loadmonth.class.getName()).log(Level.SEVERE, null, ex);
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
