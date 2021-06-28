/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package General;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;


//import java.time.*; 
//import java.time.Month; 
//import java.time.temporal.ChronoField;

/**
 *
 * @author Geofrey Nyabuto
 */
public class IdGenerator2 {
    //    CURRENT DATE
 Calendar cal = Calendar.getInstance();
int year=cal.get(Calendar.YEAR);
int prevmonth=cal.get(Calendar.MONTH);
int month=cal.get(Calendar.MONTH)+1;
int date=cal.get(Calendar.DATE);
int hour = cal.get(Calendar.HOUR_OF_DAY);
int min=cal.get(Calendar.MINUTE);
int sec=cal.get(Calendar.SECOND);
int micro=cal.get(Calendar.MILLISECOND);

String yr,mnth,dater,hr,mn,sc,action="";
String date2="";
Timestamp now;
String monthText="";
Random random = new Random();
 long fraction = (long) ((98725 - 219 ) * random.nextDouble());
 
    public String CreatedOn(){
       
String full_date2=hour+""+min+""+sec+""+micro;
String db=Double.toString(fraction);
String tableID=(db+""+full_date2).replace(".", ""); 
 String id=year+"_"+month+"_"+date+"_"+hour+"_"+min+"_"+sec+"_"+micro;       
       return  id;
    }
    public String CreatedOnTime(){
       
String full_date2=hour+""+min+""+sec+""+micro;
String db=Double.toString(fraction);
String tableID=(db+""+full_date2).replace(".", ""); 
 String id=year+"_"+month+"_"+date+"_"+hour+"_"+min;       
       return  id;
    }
    
    public String timestamp(){
java.util.Date date= new java.util.Date();
now=new Timestamp(date.getTime());
         String timestamp=now+"";
       return  timestamp;
    }
    
     public String toDay()
    {
        if(date<10){
    date2="0"+date;
}
  if(date>=10){
    date2=""+date;
}
  String mn=""+month;
         if(month<10)
         {
    mn="0"+month;
}
  
   String full_date=year+"-"+mn+"-"+date2;     
   return full_date;
    }
       
  public String current_id(){
       
String full_date2=hour+""+min+""+sec+""+micro;
String db=Double.toString(fraction);
String tableID=(db+""+full_date2).replace(".", ""); 
 String id=year+"_"+month+"_"+date+"_"+hour+"_"+min+"_"+sec+"_"+micro;       
       return  tableID;
    }
  
  public String CurrentMonth(){
      if(month<10){
       monthText="0"+month;   
      }
      else{
        monthText=""+month;  
      }
      return monthText;
  }
  
  public int currentYear(){
      return year;
  }
  
   public String LastMonthEndDate(){
       
       int mwaka=currentYear();
       
if(prevmonth==0){mwaka=mwaka-1;}     

String last_reporting_month=ReportingEndDate(prevmonth, year);

       return  mwaka+"-"+last_reporting_month;
    }
    
   
    public String LastMonthStartDate(){
       
       int mwaka=currentYear();
       
if(prevmonth==0){mwaka=mwaka-1;}     

String last_reporting_month=ReportingStartDate(prevmonth, year);

       return  mwaka+"-"+last_reporting_month;
    }
   
  public String ReportingEndDate(int prevmont, int Year){
  
      String month_dates;
      
      String mwezini=""+prevmont;
      
      if(prevmont==0){mwezini="12";}
      else if(prevmont>=1 && prevmont<=9){mwezini="0"+mwezini;}
      
      
   
        month_dates=""+getNumberOfDays(year,new Integer(mwezini)); 
        
 
  
     return mwezini+"-"+month_dates;
  }
  
  
  public String ReportingStartDate(int prevmont, int Year){
  
     
      
      String mwezini=""+prevmont;
      
      if(prevmont==0){mwezini="12";}
      else if(prevmont>=1 && prevmont<=9){mwezini="0"+mwezini;}
      
      
  
     return mwezini+"-01";
  }
  
  
  
  public int getNumberOfDays(int Year, int monthid ){
  
      int number_Of_DaysInMonth = 0; 
        String MonthOfName = "Unknown";

        System.out.print("Input a month number: ");
        int month = monthid;

        System.out.print("Input a year: ");
        int year = Year;

        switch (month) {
            case 1:
                MonthOfName = "January";
                number_Of_DaysInMonth = 31;
                break;
            case 2:
                MonthOfName = "February";
                if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
                    number_Of_DaysInMonth = 29;
                } else {
                    number_Of_DaysInMonth = 28;
                }
                break;
            case 3:
                MonthOfName = "March";
                number_Of_DaysInMonth = 31;
                break;
            case 4:
                MonthOfName = "April";
                number_Of_DaysInMonth = 30;
                break;
            case 5:
                MonthOfName = "May";
                number_Of_DaysInMonth = 31;
                break;
            case 6:
                MonthOfName = "June";
                number_Of_DaysInMonth = 30;
                break;
            case 7:
                MonthOfName = "July";
                number_Of_DaysInMonth = 31;
                break;
            case 8:
                MonthOfName = "August";
                number_Of_DaysInMonth = 31;
                break;
            case 9:
                MonthOfName = "September";
                number_Of_DaysInMonth = 30;
                break;
            case 10:
                MonthOfName = "October";
                number_Of_DaysInMonth = 31;
                break;
            case 11:
                MonthOfName = "November";
                number_Of_DaysInMonth = 30;
                break;
            case 12:
                MonthOfName = "December";
                number_Of_DaysInMonth = 31;
        }
        System.out.print(MonthOfName + " " + year + " has " + number_Of_DaysInMonth + " days\n");
    

     return number_Of_DaysInMonth; 
  
  }
  
  
  
}
