/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import db.dbConn;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author EKaunda
 */
public class loadcohort extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        
        
        String cohort="pmtct";
        String yearstring="";
        String monthstring="";
        String mflcode="";
        String facildetails="";
        String month3,month6,month9,month12,month24,month36,month48,month60;
        String chosenmonth="month3";
        
        
        
        String pmtctformulaes[]={"sumofindicators(\"kp_3m-1@np_3m-1\",\"tl_3m-1\");","sumofindicators(\"kp_3m-1@np_3m-1\",\"tl_3m-1\");","sumofindicators(\"kp_3m-1@np_3m-1\",\"tl_3m-1\");"
                                ,"sumofindicators(\"kp_6m-1@np_6m-1\",\"tl_6m-1\");","sumofindicators(\"kp_6m-1@np_6m-1\",\"tl_6m-1\");","sumofindicators(\"kp_6m-1@np_6m-1\",\"tl_6m-1\");"
                                ,"sumofindicators(\"kp_9m-1@np_9m-1\",\"tl_9m-1\");","sumofindicators(\"kp_9m-1@np_9m-1\",\"tl_9m-1\");","sumofindicators(\"kp_9m-1@np_9m-1\",\"tl_9m-1\");"
                                ,"sumofindicators(\"kp_12m-1@np_12m-1\",\"tl_12m-1\");","sumofindicators(\"kp_12m-1@np_12m-1\",\"tl_12m-1\");","sumofindicators(\"kp_12m-1@np_12m-1\",\"tl_12m-1\");"
                                ,"sumofindicators(\"kp_24m-1@np_24m-1\",\"tl_24m-1\");","sumofindicators(\"kp_24m-1@np_24m-1\",\"tl_24m-1\");","sumofindicators(\"kp_24m-1@np_24m-1\",\"tl_24m-1\");"
                                
                                ,"sumofindicators(\"kp_3m-2@np_3m-2\",\"tl_3m-2\");","sumofindicators(\"kp_3m-2@np_3m-2\",\"tl_3m-2\");","sumofindicators(\"kp_3m-2@np_3m-2\",\"tl_3m-2\");"
                                ,"sumofindicators(\"kp_6m-2@np_6m-2\",\"tl_6m-2\");","sumofindicators(\"kp_6m-2@np_6m-2\",\"tl_6m-2\");","sumofindicators(\"kp_6m-2@np_6m-2\",\"tl_6m-2\");"
                                ,"sumofindicators(\"kp_9m-2@np_9m-2\",\"tl_9m-2\");","sumofindicators(\"kp_9m-2@np_9m-2\",\"tl_9m-2\");","sumofindicators(\"kp_9m-2@np_9m-2\",\"tl_9m-2\");"
                                ,"sumofindicators(\"kp_12m-2@np_12m-2\",\"tl_12m-2\");","sumofindicators(\"kp_12m-2@np_12m-2\",\"tl_12m-2\");","sumofindicators(\"kp_12m-2@np_12m-2\",\"tl_12m-2\");"
                                ,"sumofindicators(\"kp_24m-2@np_24m-2\",\"tl_24m-2\");","sumofindicators(\"kp_24m-2@np_24m-2\",\"tl_24m-2\");","sumofindicators(\"kp_24m-2@np_24m-2\",\"tl_24m-2\");"
                                 
                                ,"sumofindicators(\"kp_3m-3@np_3m-3\",\"tl_3m-3\");","sumofindicators(\"kp_3m-3@np_3m-3\",\"tl_3m-3\");","sumofindicators(\"kp_3m-3@np_3m-3\",\"tl_3m-3\");"
                                ,"sumofindicators(\"kp_6m-3@np_6m-3\",\"tl_6m-3\");","sumofindicators(\"kp_6m-3@np_6m-3\",\"tl_6m-3\");","sumofindicators(\"kp_6m-3@np_6m-3\",\"tl_6m-3\");"
                                ,"sumofindicators(\"kp_9m-3@np_9m-3\",\"tl_9m-3\");","sumofindicators(\"kp_9m-3@np_9m-3\",\"tl_9m-3\");","sumofindicators(\"kp_9m-3@np_9m-3\",\"tl_9m-3\");"
                                ,"sumofindicators(\"kp_12m-3@np_12m-3\",\"tl_12m-3\");","sumofindicators(\"kp_12m-3@np_12m-3\",\"tl_12m-3\");","sumofindicators(\"kp_12m-3@np_12m-3\",\"tl_12m-3\");"
                                ,"sumofindicators(\"kp_24m-3@np_24m-3\",\"tl_24m-3\");","sumofindicators(\"kp_24m-3@np_24m-3\",\"tl_24m-3\");","sumofindicators(\"kp_24m-3@np_24m-3\",\"tl_24m-3\");"
                                 
                                ,"sumofindicators(\"kp_3m-4@np_3m-4\",\"tl_3m-4\");","sumofindicators(\"kp_3m-4@np_3m-4\",\"tl_3m-4\");","sumofindicators(\"kp_3m-4@np_3m-4\",\"tl_3m-4\");"
                                ,"sumofindicators(\"kp_6m-4@np_6m-4\",\"tl_6m-4\");","sumofindicators(\"kp_6m-4@np_6m-4\",\"tl_6m-4\");","sumofindicators(\"kp_6m-4@np_6m-4\",\"tl_6m-4\");"
                                ,"sumofindicators(\"kp_9m-4@np_9m-4\",\"tl_9m-4\");","sumofindicators(\"kp_9m-4@np_9m-4\",\"tl_9m-4\");","sumofindicators(\"kp_9m-4@np_9m-4\",\"tl_9m-4\");"
                                ,"sumofindicators(\"kp_12m-4@np_12m-4\",\"tl_12m-4\");","sumofindicators(\"kp_12m-4@np_12m-4\",\"tl_12m-4\");","sumofindicators(\"kp_12m-4@np_12m-4\",\"tl_12m-4\");"
                                ,"sumofindicators(\"kp_24m-4@np_24m-4\",\"tl_24m-4\");","sumofindicators(\"kp_24m-4@np_24m-4\",\"tl_24m-4\");","sumofindicators(\"kp_24m-4@np_24m-4\",\"tl_24m-4\");"
                                 
                                ,"sumofindicators(\"kp_3m-5@np_3m-5\",\"tl_3m-5\");","sumofindicators(\"kp_3m-5@np_3m-5\",\"tl_3m-5\");","sumofindicators(\"kp_3m-5@np_3m-5\",\"tl_3m-5\");"
                                ,"sumofindicators(\"kp_6m-5@np_6m-5\",\"tl_6m-5\");","sumofindicators(\"kp_6m-5@np_6m-5\",\"tl_6m-5\");","sumofindicators(\"kp_6m-5@np_6m-5\",\"tl_6m-5\");"
                                ,"sumofindicators(\"kp_9m-5@np_9m-5\",\"tl_9m-5\");","sumofindicators(\"kp_9m-5@np_9m-5\",\"tl_9m-5\");","sumofindicators(\"kp_9m-5@np_9m-5\",\"tl_9m-5\");"
                                ,"sumofindicators(\"kp_12m-5@np_12m-5\",\"tl_12m-5\");","sumofindicators(\"kp_12m-5@np_12m-5\",\"tl_12m-5\");","sumofindicators(\"kp_12m-5@np_12m-5\",\"tl_12m-5\");"
                                ,"sumofindicators(\"kp_24m-5@np_24m-5\",\"tl_24m-5\");","sumofindicators(\"kp_24m-5@np_24m-5\",\"tl_24m-5\");","sumofindicators(\"kp_24m-5@np_24m-5\",\"tl_24m-5\");"
                                 
                                ,"sumofindicators(\"kp_3m-6@np_3m-6\",\"tl_3m-6\");","sumofindicators(\"kp_3m-6@np_3m-6\",\"tl_3m-6\");","sumofindicators(\"kp_3m-6@np_3m-6\",\"tl_3m-6\");"
                                ,"sumofindicators(\"kp_6m-6@np_6m-6\",\"tl_6m-6\");","sumofindicators(\"kp_6m-6@np_6m-6\",\"tl_6m-6\");","sumofindicators(\"kp_6m-6@np_6m-6\",\"tl_6m-6\");"
                                ,"sumofindicators(\"kp_9m-6@np_9m-6\",\"tl_9m-6\");","sumofindicators(\"kp_9m-6@np_9m-6\",\"tl_9m-6\");","sumofindicators(\"kp_9m-6@np_9m-6\",\"tl_9m-6\");"
                                ,"sumofindicators(\"kp_12m-6@np_12m-6\",\"tl_12m-6\");","sumofindicators(\"kp_12m-6@np_12m-6\",\"tl_12m-6\");","sumofindicators(\"kp_12m-6@np_12m-6\",\"tl_12m-6\");"
                                ,"sumofindicators(\"kp_24m-6@np_24m-6\",\"tl_24m-6\");","sumofindicators(\"kp_24m-6@np_24m-6\",\"tl_24m-6\");","sumofindicators(\"kp_24m-6@np_24m-6\",\"tl_24m-6\");"
                                 
                                ,"sumofindicators(\"kp_3m-7@np_3m-7\",\"tl_3m-7\");","sumofindicators(\"kp_3m-7@np_3m-7\",\"tl_3m-7\");","sumofindicators(\"kp_3m-7@np_3m-7\",\"tl_3m-7\");"
                                ,"sumofindicators(\"kp_6m-7@np_6m-7\",\"tl_6m-7\");","sumofindicators(\"kp_6m-7@np_6m-7\",\"tl_6m-7\");","sumofindicators(\"kp_6m-7@np_6m-7\",\"tl_6m-7\");"
                                ,"sumofindicators(\"kp_9m-7@np_9m-7\",\"tl_9m-7\");","sumofindicators(\"kp_9m-7@np_9m-7\",\"tl_9m-7\");","sumofindicators(\"kp_9m-7@np_9m-7\",\"tl_9m-7\");"
                                ,"sumofindicators(\"kp_12m-7@np_12m-7\",\"tl_12m-7\");","sumofindicators(\"kp_12m-7@np_12m-7\",\"tl_12m-7\");","sumofindicators(\"kp_12m-7@np_12m-7\",\"tl_12m-7\");"
                                ,"sumofindicators(\"kp_24m-7@np_24m-7\",\"tl_24m-7\");","sumofindicators(\"kp_24m-7@np_24m-7\",\"tl_24m-7\");","sumofindicators(\"kp_24m-7@np_24m-7\",\"tl_24m-7\");"
                                 
                                ,"sumofindicators(\"kp_3m-8@np_3m-8\",\"tl_3m-8\");","sumofindicators(\"kp_3m-8@np_3m-8\",\"tl_3m-8\");","sumofindicators(\"kp_3m-8@np_3m-8\",\"tl_3m-8\");"
                                ,"sumofindicators(\"kp_6m-8@np_6m-8\",\"tl_6m-8\");","sumofindicators(\"kp_6m-8@np_6m-8\",\"tl_6m-8\");","sumofindicators(\"kp_6m-8@np_6m-8\",\"tl_6m-8\");"
                                ,"sumofindicators(\"kp_9m-8@np_9m-8\",\"tl_9m-8\");","sumofindicators(\"kp_9m-8@np_9m-8\",\"tl_9m-8\");","sumofindicators(\"kp_9m-8@np_9m-8\",\"tl_9m-8\");"
                                ,"sumofindicators(\"kp_12m-8@np_12m-8\",\"tl_12m-8\");","sumofindicators(\"kp_12m-8@np_12m-8\",\"tl_12m-8\");","sumofindicators(\"kp_12m-8@np_12m-8\",\"tl_12m-8\");"
                                ,"sumofindicators(\"kp_24m-8@np_24m-8\",\"tl_24m-8\");","sumofindicators(\"kp_24m-8@np_24m-8\",\"tl_24m-8\");","sumofindicators(\"kp_24m-8@np_24m-8\",\"tl_24m-8\");"
                                 
                                ,"sumofindicators(\"kp_3m-9@np_3m-9\",\"tl_3m-9\");","sumofindicators(\"kp_3m-9@np_3m-9\",\"tl_3m-9\");","sumofindicators(\"kp_3m-9@np_3m-9\",\"tl_3m-9\");"
                                ,"sumofindicators(\"kp_6m-9@np_6m-9\",\"tl_6m-9\");","sumofindicators(\"kp_6m-9@np_6m-9\",\"tl_6m-9\");","sumofindicators(\"kp_6m-9@np_6m-9\",\"tl_6m-9\");"
                                ,"sumofindicators(\"kp_9m-9@np_9m-9\",\"tl_9m-9\");","sumofindicators(\"kp_9m-9@np_9m-9\",\"tl_9m-9\");","sumofindicators(\"kp_9m-9@np_9m-9\",\"tl_9m-9\");"
                                ,"sumofindicators(\"kp_12m-9@np_12m-9\",\"tl_12m-9\");","sumofindicators(\"kp_12m-9@np_12m-9\",\"tl_12m-9\");","sumofindicators(\"kp_12m-9@np_12m-9\",\"tl_12m-9\");"
                                ,"sumofindicators(\"kp_24m-9@np_24m-9\",\"tl_24m-9\");","sumofindicators(\"kp_24m-9@np_24m-9\",\"tl_24m-9\");","sumofindicators(\"kp_24m-9@np_24m-9\",\"tl_24m-9\");"
                                 
                                ,"sumofindicators(\"kp_3m-10@np_3m-10\",\"tl_3m-10\");","sumofindicators(\"kp_3m-10@np_3m-10\",\"tl_3m-10\");","sumofindicators(\"kp_3m-10@np_3m-10\",\"tl_3m-10\");"
                                ,"sumofindicators(\"kp_6m-10@np_6m-10\",\"tl_6m-10\");","sumofindicators(\"kp_6m-10@np_6m-10\",\"tl_6m-10\");","sumofindicators(\"kp_6m-10@np_6m-10\",\"tl_6m-10\");"
                                ,"sumofindicators(\"kp_9m-10@np_9m-10\",\"tl_9m-10\");","sumofindicators(\"kp_9m-10@np_9m-10\",\"tl_9m-10\");","sumofindicators(\"kp_9m-10@np_9m-10\",\"tl_9m-10\");"
                                ,"sumofindicators(\"kp_12m-10@np_12m-10\",\"tl_12m-10\");","sumofindicators(\"kp_12m-10@np_12m-10\",\"tl_12m-10\");","sumofindicators(\"kp_12m-10@np_12m-10\",\"tl_12m-10\");"
                                ,"sumofindicators(\"kp_24m-10@np_24m-10\",\"tl_24m-10\");","sumofindicators(\"kp_24m-10@np_24m-10\",\"tl_24m-10\");","sumofindicators(\"kp_24m-10@np_24m-10\",\"tl_24m-10\");"
                                 
                                ,"sumofindicators(\"kp_3m-11@np_3m-11\",\"tl_3m-11\");","sumofindicators(\"kp_3m-11@np_3m-11\",\"tl_3m-11\");","sumofindicators(\"kp_3m-11@np_3m-11\",\"tl_3m-11\");"
                                ,"sumofindicators(\"kp_6m-11@np_6m-11\",\"tl_6m-11\");","sumofindicators(\"kp_6m-11@np_6m-11\",\"tl_6m-11\");","sumofindicators(\"kp_6m-11@np_6m-11\",\"tl_6m-11\");"
                                ,"sumofindicators(\"kp_9m-11@np_9m-11\",\"tl_9m-11\");","sumofindicators(\"kp_9m-11@np_9m-11\",\"tl_9m-11\");","sumofindicators(\"kp_9m-11@np_9m-11\",\"tl_9m-11\");"
                                ,"sumofindicators(\"kp_12m-11@np_12m-11\",\"tl_12m-11\");","sumofindicators(\"kp_12m-11@np_12m-11\",\"tl_12m-11\");","sumofindicators(\"kp_12m-11@np_12m-11\",\"tl_12m-11\");"
                                ,"sumofindicators(\"kp_24m-11@np_24m-11\",\"tl_24m-11\");","sumofindicators(\"kp_24m-11@np_24m-11\",\"tl_24m-11\");","sumofindicators(\"kp_24m-11@np_24m-11\",\"tl_24m-11\");"
                                 
                                ,"sumofindicators(\"kp_3m-12@np_3m-12\",\"tl_3m-12\");","sumofindicators(\"kp_3m-12@np_3m-12\",\"tl_3m-12\");","sumofindicators(\"kp_3m-12@np_3m-12\",\"tl_3m-12\");"
                                ,"sumofindicators(\"kp_6m-12@np_6m-12\",\"tl_6m-12\");","sumofindicators(\"kp_6m-12@np_6m-12\",\"tl_6m-12\");","sumofindicators(\"kp_6m-12@np_6m-12\",\"tl_6m-12\");"
                                ,"sumofindicators(\"kp_9m-12@np_9m-12\",\"tl_9m-12\");","sumofindicators(\"kp_9m-12@np_9m-12\",\"tl_9m-12\");","sumofindicators(\"kp_9m-12@np_9m-12\",\"tl_9m-12\");"
                                ,"sumofindicators(\"kp_12m-12@np_12m-12\",\"tl_12m-12\");","sumofindicators(\"kp_12m-12@np_12m-12\",\"tl_12m-12\");","sumofindicators(\"kp_12m-12@np_12m-12\",\"tl_12m-12\");"
                                ,"sumofindicators(\"kp_24m-12@np_24m-12\",\"tl_24m-12\");","sumofindicators(\"kp_24m-12@np_24m-12\",\"tl_24m-12\");","sumofindicators(\"kp_24m-12@np_24m-12\",\"tl_24m-12\");"
                                 
        };
        
        
      
        
        
        month3=month6=month9=month12=month24=month36=month48=month60="";
        
        if(request.getParameter("month")!=null){
        monthstring=request.getParameter("month");
        }
        if(request.getParameter("year")!=null){
        yearstring=request.getParameter("year");
        }
        
        
        if(request.getParameter("cohort")!=null){
        cohort=request.getParameter("cohort");
        }
        if(request.getParameter("facility")!=null){
         facildetails=request.getParameter("facility");
         String hosi[]=facildetails.split("_");
           mflcode=hosi[0];
        }
       
        System.out.println("Received"+yearstring+monthstring);
        
        String startmonth="";
        
        String pmtcth[]={"kp_3m","np_3m","tl_3m","kp_6m","np_6m","tl_6m","kp_9m","np_9m","tl_9m","kp_12m","np_12m","tl_12m","kp_24m","np_24m","tl_24m"};
        String basicmonthar[]={"3","6","9","12","24","36","48","60"};
        
        String arth[]={"adult_3m","child_3m","tl_3m","adult_6m","child_6m","tl_6m","adult_9m","child_9m","tl_9m","adult_12m","child_12m","tl_12m","adult_24m","child_24m","tl_24m","adult_36m","child_36m","tl_36m","adult_48m","child_48m","tl_48m","adult_60m","child_60m","tl_60m"};

        int readonlys[]={0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1};
        
      int columncount=0;
      
      if(!monthstring.equals("") && !yearstring.equals("")){
          
     month3="<b>"+getpreviousmonth(new Integer(yearstring),new Integer(monthstring), -3)+"</b>";
     month6="<b>"+getpreviousmonth(new Integer(yearstring),new Integer(monthstring), -6)+"</b>";
     month9="<b>"+getpreviousmonth(new Integer(yearstring),new Integer(monthstring), -9)+"</b>";
     month12="<b>"+getpreviousmonth(new Integer(yearstring),new Integer(monthstring), -12)+"</b>";
     month24="<b>"+getpreviousmonth(new Integer(yearstring),new Integer(monthstring), -24)+"</b>";
     month36="<b>"+getpreviousmonth(new Integer(yearstring),new Integer(monthstring), -36)+"</b>";
     month48="<b>"+getpreviousmonth(new Integer(yearstring),new Integer(monthstring), -48)+"</b>";
     month60="<b>"+getpreviousmonth(new Integer(yearstring),new Integer(monthstring), -60)+"</b>";
     
       System.out.println("Testiii  sent "+yearstring+""+monthstring+" received back "+month3);    
      
      }
      
      
         JSONObject jsonobj = new JSONObject();
         
         JSONArray arr= new JSONArray();
         JSONArray subarr= new JSONArray();
         
         String cohorttable="<colgroup > <col style='width: 214px;'>";
         
    

         
          String cohortheader="";
         
         if(cohort.equalsIgnoreCase("pmtct")){
             columncount=15;
            
            
             cohortheader="<tr><td class='htLeft '>Month</td><td class='htCenter' rowspan='1' colspan='3'>3 Months <br/>"+month3+"</td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter ' rowspan='1' colspan='3'>6 Months <br/>"+month6+"</td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter' rowspan='1' colspan='3'>9 Months <br/>"+month9+"</td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter' rowspan='1' colspan='3'>12 Months <br/>"+month12+"</td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter' rowspan='1' colspan='3'>24 Months <br/>"+month24+"</td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td></tr>";
             cohortheader+="<tr><td class='htLeft '>indicator</td><td class='htCenter '>KP</td><td class='htCenter '>NP</td><td class='htCenter '>Total</td><td class='htCenter '>KP</td><td class='htCenter '>NP</td><td class='htCenter '>Total</td><td class='htCenter '>KP</td><td class='htCenter '>NP</td><td class='htCenter '>Total</td><td class='htCenter '>KP</td><td class='htCenter '>NP</td><td class='htCenter '>Total</td><td class='htCenter '>KP</td><td class='htCenter '>NP</td><td class='htCenter '>Total</td></tr>";
             
                     

            for(int a=0;a<columncount;a++){
            
               cohorttable+="<col style='width: 68px;'>"; 
            
            }
             
             
             //add header 2
            

         }//end of PMTCT Cohorts
         else if(cohort.equalsIgnoreCase("art")) {
             
             
             cohortheader+="<tr><td class='htLeft '>Month</td><td class='htCenter' rowspan='1' colspan='3'>3 Months <br/>"+month3+" </td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter ' rowspan='1' colspan='3'>6 Months <br/>"+month6+" </td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter' rowspan='1' colspan='3'>9 Months <br/>"+month9+" </td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter' rowspan='1' colspan='3'>12 Months <br/>"+month12+" </td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter' rowspan='1' colspan='3'>24 Months <br/>"+month24+" </td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter' rowspan='1' colspan='3'>36 Months <br/>"+month36+" </td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter' rowspan='1' colspan='3'>48 Months <br/>"+month48+" </td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td><td class='htCenter' rowspan='1' colspan='3'>60 Months <br/>"+month60+" </td><td class='htCenter' style='display: none;'></td><td class='htCenter' style='display: none;'></td></tr>";
             cohortheader+="<tr><td class='htLeft '>indicator</td><td class='htCenter '>Adults</td><td class='htCenter '>Children</td><td class='htCenter '>Total</td><td class='htCenter '>Adults</td><td class='htCenter '>Children</td><td class='htCenter '>Total</td><td class='htCenter '>Adults</td><td class='htCenter '>Children</td><td class='htCenter '>Total</td><td class='htCenter '>Adults</td><td class='htCenter '>Children</td><td class='htCenter '>Total</td><td class='htCenter '>Adults</td><td class='htCenter '>Children</td><td class='htCenter '>Total</td><td class='htCenter '>Adults</td><td class='htCenter '>Children</td><td class='htCenter '>Total</td><td class='htCenter '>Adults</td><td class='htCenter '>Children</td><td class='htCenter '>Total</td><td class='htCenter '>Adults</td><td class='htCenter'>Children</td><td class='htCenter'>Total</td></tr>";
             
             
             columncount=24;
             
        for(int a=0;a<columncount;a++){
            
               cohorttable+="<col style='width: 68px;'>"; 
            
            }
          
            
             
   }
         cohorttable+=" </colgroup><thead></thead><tbody>";
           ArrayList pmtctformulas_ar= new ArrayList();
            if(cohort.equalsIgnoreCase("pmtct")){
           int dur=3;
          for(int b=1;b<=12;b++){
           for(int c=0;c<5;c++){
           for(int a=0;a<3;a++){
          
           pmtctformulas_ar.add("sumofindicators(\"kp_"+basicmonthar[c]+"m-"+b+"@np_"+basicmonthar[c]+"m-"+b+"\",\"tl_"+basicmonthar[c]+"m-"+b+"\");");
           
           System.out.println("sumofindicators(\"kp_"+basicmonthar[c]+"m-"+b+"@np_"+basicmonthar[c]+"m-"+b+"\",\"tl_"+basicmonthar[c]+"m-"+b+"\");");
              // System.out.println("sumofindicators(\"kp_"+dur+"m-"+b+"@np_"+dur+"m-"+b+"\",\"tl_"+dur+"m-"+b+"\");");
         
          }}
           dur=dur+3;
          //}
           
           }
          
    }
           ArrayList artformulas_ar= new ArrayList();
           
           
            if(cohort.equalsIgnoreCase("art")){
           int dur1=3;
          for(int b=13;b<=24;b++){
           for(int c=0;c<8;c++){
           for(int a=0;a<3;a++){
          
           artformulas_ar.add("sumofindicators(\"child_"+basicmonthar[c]+"m-"+b+"@adult_"+basicmonthar[c]+"m-"+b+"\",\"tl_"+basicmonthar[c]+"m-"+b+"\");");
             System.out.println("sumofindicators(\"child_"+basicmonthar[c]+"m-"+b+"@adult_"+basicmonthar[c]+"m-"+b+"\",\"tl_"+basicmonthar[c]+"m-"+b+"\");");
              
          }}
         
          //}
           
           }
                   }
         
            dbConn conn= new dbConn();
            
            String tabledata="<tr>";
            
            String primarydata="select indicator,indicators_id from indicators where cohort='"+cohort+"'"; 
        
            System.out.println("_"+primarydata);
            conn.rs=conn.st.executeQuery(primarydata);
            
            String indicators="";
            int arrcnt=0;
           while(conn.rs.next()) {
               tabledata+="<td class='htLeft'>"+conn.rs.getString(1)+"</td>";
               
               //System.out.println(""+conn.rs.getString(1));
               
              
                
                for (int a = 0; a < columncount; a++) {
                    String elementid="";
                    String additionalfun="";
                    if(cohort.equalsIgnoreCase("pmtct")){
                    additionalfun= pmtctformulas_ar.get(arrcnt).toString();
                    arrcnt++;
                    elementid=pmtcth[a];
                    }
                    else if(cohort.equalsIgnoreCase("art")){
                     additionalfun= artformulas_ar.get(arrcnt).toString();
                    elementid=arth[a];
                     arrcnt++;
                    }
                    String indicatorid=conn.rs.getString("indicators_id");
                    String id=yearstring+"_"+monthstring+"_"+mflcode+"_"+indicatorid;
                    String isreadonly="";
                    
                    if(readonlys[a]==1){
                    isreadonly="readonly  tabindex='-1'";
                    }
                    else { isreadonly=" ";
                    }
                    
                 tabledata+="<td class='htLeft'><input "+isreadonly+" type='text' name='"+elementid+"-"+indicatorid+"'  id='"+elementid+"-"+indicatorid+"' onblur='autosave(\""+elementid+"-"+indicatorid+"\",\""+id+"\");"+additionalfun+"' class=' htCenter' style='width:63px;' ></td>";
                 
             }
             
                tabledata+="</tr>";
                
                                }
           
            
               
           String completetable=cohorttable+cohortheader+"</tbody>"+tabledata;
                                  
        
        try (PrintWriter out = response.getWriter()) {
            
                
                //System.out.println(jsonobj);
                
                out.println(completetable);
           
          
        }
        if(conn.rs!=null){conn.rs.close();}
            if(conn.st!=null){conn.st.close();}
            if(conn.connect!=null){conn.connect.close();}
           // if(conndb.pst!=null){conndb.pst.close();}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(loadcohort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(loadcohort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public String getpreviousmonth(int currentyear, int currentmonth,int deductvalue){
    
        String returnedyearmonth="190000";
       
        if(currentyear>=1900){
         
        deductvalue=deductvalue-1;     //since different months have different end dates, instead use fisrt of everymonth and deduct 1 month   
                     
Calendar c = Calendar.getInstance(); 
c.setTime(new Date(currentyear-1900,currentmonth,1)); 
c.add(Calendar.MONTH, deductvalue);
            System.out.println(c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1));

       int mwezi= (c.get(Calendar.MONTH)+1);
       if (mwezi<10){
       
       mwezi=new Integer("0"+mwezi);
       }
            
       returnedyearmonth=""+getmonthname(""+mwezi).substring(0, 3)+" "+c.get(Calendar.YEAR);     
        
       //returnedyearmonth=returnedyearmonth.substring(0, 4)+" "+ getmonthname(returnedyearmonth.substring(4));
        
       
        }
        
       
        
    
        return returnedyearmonth;
        
    }
    
    
    
    //=============================getimisyearmonth===================================
    
    
      public String getyearmonth(int currentyear, int currentmonth,int deductvalue){
    
        String returnedyearmonth="190000";
       
        if(currentyear>=1900){
         
        deductvalue=deductvalue-1;  //since different months have different end dates, instead use fisrt of everymonth and deduct 1 month   
                     
Calendar c = Calendar.getInstance(); 
c.setTime(new Date(currentyear-1900,currentmonth,1)); 
c.add(Calendar.MONTH, deductvalue);
           

       int mwezi= (c.get(Calendar.MONTH)+1);
       if (mwezi<10){
       
       mwezi=new Integer("0"+mwezi);
       }
            
       returnedyearmonth=""+c.get(Calendar.YEAR)+"_"+mwezi;     
        
       //returnedyearmonth=returnedyearmonth.substring(0, 4)+" "+ getmonthname(returnedyearmonth.substring(4));
        
       
        }
        
       
        
    
        return returnedyearmonth;
        
    }
    
    //=============================end of getimisyearmonth===================================
    
    //get yearmonth 6 digits value
      
       
      public String getyearmonthkey(int currentyear, int currentmonth,int deductvalue){
    
        String returnedyearmonth="190000";
       
        if(currentyear>=1900){
         
        deductvalue=deductvalue-1;  //since different months have different end dates, instead use fisrt of everymonth and deduct 1 month   
                     
Calendar c = Calendar.getInstance(); 
c.setTime(new Date(currentyear-1900,currentmonth,1)); 
c.add(Calendar.MONTH, deductvalue);
           

       int mwezi= (c.get(Calendar.MONTH)+1);
       String mn=""+mwezi;
       if (mwezi<10){
       
       mn="0"+mwezi;
       }
            
       returnedyearmonth=""+c.get(Calendar.YEAR)+""+mn;     
        
       //returnedyearmonth=returnedyearmonth.substring(0, 4)+" "+ getmonthname(returnedyearmonth.substring(4));
        
       
        }
        
       
        
    
        return returnedyearmonth;
        
    }
    
    
    
    
    public String getmonthname(String month_no){
    
        String mwezi="nomonth";
    
        if(month_no.equals("1") || month_no.equals("01")){ mwezi="January"; }
        if(month_no.equals("2") || month_no.equals("02")){ mwezi="February"; }
        if(month_no.equals("3") || month_no.equals("03")){ mwezi="March"; }
        if(month_no.equals("4") || month_no.equals("04")){ mwezi="April"; }
        if(month_no.equals("5") || month_no.equals("05")){ mwezi="May"; }
        if(month_no.equals("6") || month_no.equals("06")){ mwezi="June"; }
        if(month_no.equals("7") || month_no.equals("07")){ mwezi="July"; }
        if(month_no.equals("8") || month_no.equals("08")){ mwezi="August"; }
        if(month_no.equals("9") || month_no.equals("09")){ mwezi="September"; }
        if( month_no.equals("10")){ mwezi="October"; }
        if( month_no.equals("11")){ mwezi="November"; }
        if( month_no.equals("12")){ mwezi="December"; }
    
    
        
     return mwezi;   
    }
    
    
    
    
    
}
