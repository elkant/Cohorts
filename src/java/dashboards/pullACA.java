/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboards;

import db.dbConnDash;
import db.dbConnImis;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author EKaunda
 */
public class pullACA extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

      public String aca_dashboards(String startyearmonth, String endyearmonth,String facil) {

        try {
           
            
            //_______________________________OVERVIEW______________________________
            //We are pulling data from various hts datatables table using a stored procedure( hts_dashboards ) into dashboards table2
            //The  assumption here is that the stored procedure will come in structure similar to the one for table2
            //any column output in the hts_dashboards stored procedure should also exist in table2
            //columns are fetched dynamically and then the respective data insert using a loop into the table  
            //the insert code is excecuted at the end of the loop
            
            dbConnDash conndb = new dbConnDash();
            dbConnImis conn = new dbConnImis();

            String facilitiestable = "subpartnera";

            int count1 = 1;

            String insertqry = " REPLACE INTO dashboards.table3 SET ";

            // String qry1 = "call tb_dashboard('2015-10-01','2016-09-30','')";
            String qry1 = "call aca_dashboards(\"" + startyearmonth + "\",\"" + endyearmonth + "\",\"" + facilitiestable + "\",\"" + facil + "\")";
            
            conn.rs = conn.st.executeQuery(qry1);

            ResultSetMetaData metaData = conn.rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            ArrayList mycolumns1 = new ArrayList();

            while (conn.rs.next()) {

                if (count1 == 1) {
//headers only

                    for (int i = 1; i <= columnCount; i++) {

                        if (i < columnCount) {

                            mycolumns1.add(metaData.getColumnLabel(i));
                            //build column
                            insertqry += " `dashboards`.`table3`.`" + metaData.getColumnLabel(i) + "`=?, ";

                        } else {
                            
                 //last column we dont need a coma at the end of the column name
                 //also initialize a prepared statement at this level
                 
                            mycolumns1.add(metaData.getColumnLabel(i));
                          
                            insertqry += " `dashboards`.`table3`.`" + metaData.getColumnLabel(i) + "`=? ";
                            // valuesqry+=" ? )";
                            conndb.pst = conn.conn.prepareStatement(insertqry);

                        }

                    }//end of for loop
                    count1++;
                }//end of if
                //data rows     

                for (int a = 0; a < columnCount; a++) {

                    conndb.pst.setString(a + 1, conn.rs.getString(mycolumns1.get(a).toString()));

                    if (a == (columnCount - 1)) {
                        conndb.pst.executeUpdate();
                        System.out.println("" + conndb.pst);
                    }
                }

                count1++;
            }
            if(conn.rs!=null){conn.rs.close();}
            if(conn.st!=null){conn.st.close();}
            if(conn.conn!=null){conn.conn.close();}
            if(conndb.pst!=null){conndb.pst.close();}
            

        } catch (SQLException ex) {
            Logger.getLogger(pullHTS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Data pulled";
    }

    
}
