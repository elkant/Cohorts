/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//The assumption is that all the validation string will be a list of indicators separated by the expected validation string

//1=2 means for all the age disagregations, element 1 should be eqaul to element 2
//1>2 means for all the disagregations, element 1 should be greater than element 2
//use , (comma) to separate various validations rules

var breakloop=false;

var appendstring="";

function loadValidation() {
    breakloop = false;

    $.ajax({
        url:'loadEligibleNotDoneForVlValidation',
        type:'post',
        dataType:'json',
        success:function (data) {

var call_save=true;

            if (data!==null)
            {
    //appendstring="";

                for (var as = 0; as < data.length; as++)
                {
                    runvalidation(data[as].validation, data[as].message, data[as].iscritical, data[as].section_name);
                    if (breakloop === true) {
                       call_save=false;
                       console.log(data[as].message);
                        break;
                    } else {
                     call_save=true;
                        

                    }

                }




            }
            else{
                //when no validation
                call_save=true;
                
            }

if(call_save){
    console.log('call save arrived at');
    save_data();
    
}
else {
    
      console.log('NO call save arrived at');
}

        }

    });

    
}


function runvalidation(valids, message, iscritical, sectionid) 
{ 
   if(iscritical==='1'){ iscritical="yes";  } 
   else if(iscritical==='0'){iscritical="no";} 
    
      if(valids.indexOf("<=")>=0){   lessOrEqual(valids,message,iscritical,sectionid);      }
 else if(valids.indexOf(">=")>=0){   greaterOrEqual(valids,message,iscritical,sectionid);   }
 else if(valids.indexOf("!=")>=0){   notEqual(valids,message,iscritical,sectionid);         }
 else if(valids.indexOf("<")>=0) {   less(valids,message,iscritical,sectionid);             }
 else if(valids.indexOf(">")>=0) {   greater(valids,message,iscritical,sectionid);          }

}



//raise an alarm if indicator 1 is not less than indicator 2

function less(valids, message, iscritical, sectionid) {
    //1<2
    //eg 1<2 means for all the age disagregations, indicator 1 should be less than indicator 2


     $("#fedback").html("");

    var val1, val2;

var columns = ["_9","_14","_19","24","_25","pbfw"];
var agearray = ["_9","_14","_19","24","_25","pbfw"];



    //bkd<kpt      bkd_9f    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = 1;
    for (var i = 0; i < columns.length; i++) {

        var valarray = valids.split("<");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk
var agegender = columns[i];
        var sx = agegender.charAt(agegender.length-1);
        var ag = agegender.replace("m").replace("f");
         if (sx === 'm') {
            sx = "Male";
        } else if (sx === 'f') {
            sx = "Female";
        }
        else {
            sx="Unknown";
            
        }

//         ag=agearray[i];

        if ((isNumber(val1) ) || ( isNumber(val2) )) {
            if (val1 < val2) {
                if (iscritical === 'yes') {
                    
                     $("#fedback").html(" <img style='width:4%;' src='images/stop.png'/> <b>" + message+"</b>");
                    //alert("For age disaggregation " +columns[i]+ "   , " + message);
                     savebutton_inactive(sectionid);
                     if (valarray[1].indexOf("+") === -1) {
                        //$("#" + columns[i] + "_" + valarray[1]).focus();
                    }

                    redborder(columns[i], valarray[0]);
                    redborder(columns[i], valarray[1]);
                breakloop=true;
                    break;
                    //end the loop so that the data entry person can make corrections

                } else {

                     // $("#msg" + sectionid).append("<b>For " + ag + " years " + sx + " , " + message + "</b><br/>");
                        $("<font color=\'red\''><b>" + message + "</b></font><br/>").insertBefore("#msg" + sectionid);
savebutton_active(sectionid);
                    yellowborder(columns[i], valarray[0],message);
                    yellowborder(columns[i], valarray[1],message);

                }
            } else {

                blackborder(columns[i], valarray[0]);
                blackborder(columns[i], valarray[1]);


            }


        }


        // }


    }



}//end of less than 




function greater(valids, message, iscritical, sectionid) {
    //1>2
    //eg 1>2 means for all the age disagregations, indicator 1 should be greater than indicator 2


    $("#fedback").html("");

    var val1, val2;

 var columns = ["_9","_14","_19","24","_25","pbfw"];
var agearray = ["_9","_14","_19","24","_25","pbfw"];




    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = 1;
    for (var i = 0; i < columns.length; i++) {

        var valarray = valids.split(">");



            //alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk
var agegender = columns[i];
        var sx = agegender.charAt(agegender.length-1);
        var ag = agegender.replace("m").replace("f");
         if (sx === 'm') {
            sx = "Male";
        } else if (sx === 'f') {
            sx = "Female";
        }
        else {
            sx="Unknown";
            
        }

//         ag=agearray[i];
//        if (agegender[1] === 'uk') {
//            ag = "Unknown";
//        } else {
//            ag = agegender[1];
//        }

        if ((isNumber(val1)) || ( isNumber(val2) )) {
            if (val1 > val2) {
                if (iscritical === 'yes') {
                   
                    $("#fedback").html(" <img style='width:4%;' src='images/stop.png'/>  <b> " + message+"</b>");
                    //alert("For age disaggregation " + ag + " years " + sx + " , " + message);
                    savebutton_inactive(sectionid);
                   
 if (valarray[1].indexOf("+") === -1) {
                        //$("#" + columns[i] + "_" + valarray[1]).focus();
                    }

                    redborder(columns[i], valarray[0],message);
                    redborder(columns[i], valarray[1],message);
                   breakloop=true;
                    break;
                    //end the loop so that the data entry person can make corrections

                } else {
    savebutton_active(sectionid);
                      //$("#msg" + sectionid).append("<b>* For " + ag + " years " + sx + " , " + message + "</b><br/>");
                      $("<label class='img'> <font color='red'><b>  " + message + "</b></font> </label>").insertBefore("#msg" + sectionid);
                    yellowborder(columns[i], valarray[0],message);
                    yellowborder(columns[i], valarray[1],message);


                }
            } else {

                blackborder(columns[i], valarray[0]);
                blackborder(columns[i], valarray[1]);


            }


        }


        // }


    }



}//end of greater than


function lessOrEqual(valids, message, iscritical, sectionid) {
    //1>2
    //eg 1>2 means for all the age disagregations, indicator 1 should be greater than indicator 2


    $("#fedback").html("");

    var val1, val2;

var columns = ["_9","_14","_19","24","_25","pbfw"];
var agearray = ["_9","_14","_19","24","_25","pbfw"];




    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = 1;
    for (var i = 0; i < columns.length; i++) {

        var valarray = valids.split("<=");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk
var agegender = columns[i];
        var sx = agegender.charAt(agegender.length-1);
        var ag = agegender.replace("m").replace("f");
         if (sx === 'm') {
            sx = "Male";
        } else if (sx === 'f') {
            sx = "Female";
        }
        else {
            sx="Unknown";
            
        }

//         ag=agearray[i];
//        if (agegender[1] === 'uk') {
//            ag = "Unknown";
//        } else {
//            ag = agegender[1];
//        }

        if ((isNumber(val1) ) || ( isNumber(val2) )) {
            if (val1 <= val2) {
                console.log(val1+"<="+val2);
                
                if (iscritical === 'yes') {
                    
                    
                     $("#fedback").html("<b> " + message+"</b>");
                    console.log("For age disaggregation " + ag + " years " + sx + " , " + message);
                    
 if (valarray[1].indexOf("+") === -1) {
                        //$("#" + columns[i] + "_" + valarray[1]).focus();
                    }
 savebutton_inactive(sectionid);
                    redborder(columns[i], valarray[0],message);
                    redborder(columns[i], valarray[1],message);
                     breakloop=true;
                    break;
                    //end the loop so that the data entry person can make corrections

                } else {

                   // $("#msg" + sectionid).append("<b/>* For " + ag + " years " + sx + " , " + message + "</b><br/>");
     $("<label class='img'> <font color='yellow'><b> " + message + "</b></font> </label>").insertBefore("#msg" + sectionid);
                    yellowborder(columns[i], valarray[0],message);
                    yellowborder(columns[i], valarray[1],message);
savebutton_active(sectionid);

                }
            } else {

                blackborder(columns[i], valarray[0]);
                blackborder(columns[i], valarray[1]);


            }


        }


        // }


    }



} //end of less or equal



function greaterOrEqual(valids, message, iscritical, sectionid) {
    //1>2
    //eg 1>2 means for all the age disagregations, indicator 1 should be greater than indicator 2

 
 
     $("#fedback").html("");

    var val1, val2;

var columns = ["_9","_14","_19","24","_25","pbfw"];
var agearray = ["_9","_14","_19","24","_25","pbfw"];




    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = 1;
    for (var i = 0; i < columns.length; i++) {

        var valarray = valids.split(">=");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk
var agegender = columns[i];
        var sx = agegender.charAt(agegender.length-1);
        var ag = agegender.replace("m").replace("f");
         if (sx === 'm') {
            sx = "Male";
        } else if (sx === 'f') {
            sx = "Female";
        }
        else {
            sx="Unknown";
            
        }

//         ag=agearray[i];
//        if (agegender[1] === 'uk') {
//            ag = "Unknown";
//        } else {
//            ag = agegender[1];
//        }

        if ((isNumber(val1) ) || ( isNumber(val2) )) {
            
            
            if (val1 >= val2) {
                console.log("val1:"+val1+" val2:"+val2);
                
                if (iscritical === 'yes') {
                 
                     $("#fedback").html(" <img style='width:4%;' src='images/stop.png'/>   " + message+"</b>");
                    //alert("For age disaggregation " + ag + " years " + sx + " , " + message);
                    savebutton_inactive(sectionid);
                     if (valarray[1].indexOf("+") === -1) {
                        //$("#" + columns[i] + "_" + valarray[1]).focus();
                    }
                     
                    redborder(columns[i], valarray[0],message);
                    redborder(columns[i], valarray[1],message);
                    
                     savebutton_inactive(sectionid);
                    
               breakloop=true;
                    break;
                    //end the loop so that the data entry person can make corrections

                } else {

appendstring+=" * <b>  " + message + "</b> <br/>";


                     $("#msg" + sectionid).html(appendstring+"<br/>");
//                     $("<label class='img'> <font color='red'><b> For " + ag + " years " + sx + " , " + message + "</b></font> </label>").insertBefore("#msg" + sectionid);
//                         $("<label class='img' style='color:red;'><b> For " + ag + " years " + sx + " , " + message + "</b></label>").insertBefore("#msg" + sectionid);
                  
                    savebutton_active(sectionid);
                    
                    yellowborder(columns[i], valarray[0],message);
                    yellowborder(columns[i], valarray[1],message);

                }
            } else {

 

                blackborder(columns[i], valarray[0]);
                blackborder(columns[i], valarray[1]);


            }


        }else {
            
            console.log("no values");
            
        }


        // }


    }



} //end of greater or equal


function notEqual(valids, message, iscritical, sectionid) {
    //1>2
    //eg 1>2 means for all the age disagregations, indicator 1 should be greater than indicator 2


     $("#fedback").html("");

    var val1, val2;

var columns = ["_9","_14","_19","24","_25","pbfw"];
var agearray = ["_9","_14","_19","24","_25","pbfw"];




    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = 1;
    for (var i = 0; i < columns.length; i++) {

        var valarray = valids.split("!=");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk
var agegender = columns[i];
        var sx = agegender.charAt(agegender.length-1);
        var ag = agegender.replace("m").replace("f");
         if (sx === 'm') {
            sx = "Male";
        } else if (sx === 'f') {
            sx = "Female";
        }
        else {
            sx="Unknown";
            
        }

//         ag=agearray[i];
//        if (agegender[1] === 'uk') {
//            ag = "Unknown";
//        } else {
//            ag = agegender[1];
//        }

        if ((isNumber(val1) ) || ( isNumber(val2) )) {
            if ((val1 < val2) || (val1 > val2)) {
                if (iscritical === 'yes') {
                    
                     $("#fedback").html("<img style='width:4%;' src='images/stop.png'/>  <b> " + message+"</b>");
                    //alert("For age disaggregation " + ag + " years " + sx + " , " + message);
                    
                    if (valarray[1].indexOf("+") === -1) {
                        //$("#" + columns[i] + "_" + valarray[1]).focus();
                    }
           
                    savebutton_inactive(sectionid);
           
                    redborder(columns[i], valarray[0],message);
                    redborder(columns[i], valarray[1],message);
                    breakloop=true;
                    break;
                    //end the loop so that the data entry person can make corrections

                } else {

                    //$("#msg" + sectionid).append("<b> * For " + ag + " years " + sx + " , " + message + "</b><br/>");
    $("<label class='img'> <font color='red'><b> " + message + "</b></font> </label>").insertBefore("#msg" + sectionid);
    savebutton_active(sectionid);

                }
            } else {

                blackborder(columns[i], valarray[0]);
                blackborder(columns[i], valarray[1]);


            }


        }


        // }


    }



}


function multisum(vals, age) {
console.log("vals ni::"+vals);
    var valsarr = vals.split("+");

    var totl = 0;
 console.log("valsarray::"+valsarr);
 
    for (var v = 0; v < valsarr.length; v++)
    {
        if(document.getElementById(valsarr[v]+"_"+age)!==null){
        
        var val = document.getElementById(valsarr[v]+"_"+age).value;
        
        console.log("multisum picked value::"+val);
        
        if (isNumber(val))
        {

            totl = totl + parseInt(val);
             
        }
        }
        else {
            if (isNumber(valsarr[v]))
        {

            totl = totl + parseInt(valsarr[v]);
            console.log("value for "+valsarr[v]+" is "+valsarr[v]);
        } 
            
        }
    }

    return totl;

}




function redborder(age, elem,msg) {

    var valsarr = elem.split("+");



    for (var v = 0; v < valsarr.length; v++)
    {
        if(document.getElementById(valsarr[v] + "_" +age)!==null){
        $("#" + valsarr[v] + "_" +age ).css('border-color', '#ff0000');
        $("#" + valsarr[v] + "_" +age).css('background', '#ff0000');
        $("#" + valsarr[v] + "_" +age).css('color', '#000000');
         $("#" + valsarr[v] + "_" +age).prop('title',msg);
         $("#" + valsarr[v] + "_" +age).focus();
     }
           

    }

}

function yellowborder(age, elem,msg) {

    var valsarr = elem.split("+");



    for (var v = 0; v < valsarr.length; v++)
    {
        if(document.getElementById(valsarr[v] + "_" +age)!==null){
        $("#" + valsarr[v] + "_" +age).css('border-color', '#fc7044');
        $("#" + valsarr[v] + "_" +age).css('background', '#fc7044');
        $("#" + valsarr[v] + "_" +age).prop('title',msg);
    }
    

    }

}


function blackborder(age, elem) {

    var valsarr = elem.split("+");



    for (var v = 0; v < valsarr.length; v++)
    {
        if(document.getElementById(valsarr[v] + "_" +age)!==null){
        $("#" +valsarr[v] + "_" +age).css('border-color', '#000000');
        //skip blank elements
        if($("#" + valsarr[v] + "_" +age).val()!==''){
        $("#" + valsarr[v] + "_" +age).css('background', '#ffffff');
                                                       }
                                                   }
        
    }



}


function savebutton_active(sectionid) {
   var saverpt=$("#validate_"+sectionid).attr("data-save_"+sectionid);
   console.log(saverpt);
        $("#validate_" + sectionid).css('background', '#4d90fe');
        $("#validate_" + sectionid).html(saverpt);
        $("#validate_" + sectionid).addClass('btn blue');
        
        $("#validate_"+sectionid).show();
            $("#msg"+sectionid).html("");
        //.btn.blue:hover, .btn.blue:focus, .btn.blue:active, .btn.blue.active,
        
  
}

function savebutton_inactive(sectionid) {
   
      
        $("#validate_" + sectionid).html('Not Saved');
         $("#validate_"+sectionid).removeClass('blue');
         $("#validate_" + sectionid).css('background', '#ff0000');
         $("#validate_" + sectionid).css('color', '#ffffff');
          $("#validate_"+sectionid).hide();
          $("#msg"+sectionid).html("Validation Error!. Please correct to save entries");
  
}

function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}