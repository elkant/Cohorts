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


function loadValidation(form_name,section_id) {
    breakloop = false;

    $.ajax({
        url: 'loadValidation?sectionid=' + section_id,
        type: 'post',
        dataType: 'json',
        success: function (data) {

var call_save=true;

            if (data!==null)
            {
    //appendstring="";

                for (var as = 0; as < data.length; as++)
                {
                    runvalidation(data[as].validation, data[as].message, data[as].iscritical, data[as].sectionid);
                    if (breakloop === true) {
                       call_save=false;
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
    
    save_data(form_name,section_id);
    
}

        }

    });

    
}


function runvalidation(valids, message, iscritical, sectionid) 
{ 
   if(iscritical==='1'){iscritical="yes";} 
   else if(iscritical==='0'){iscritical="no";    } 
    
      if(valids.indexOf("<=")>=0){   lessOrEqual(valids,message,iscritical,sectionid);  }
 else if(valids.indexOf(">=")>=0){   greaterOrEqual(valids,message,iscritical,sectionid);   }
 else if(valids.indexOf("=")>=0){   Equal(valids,message,iscritical,sectionid);  }
 else if(valids.indexOf("<")>=0){   less(valids,message,iscritical,sectionid);  }
 else if(valids.indexOf(">")>=0){   greater(valids,message,iscritical,sectionid);  }

}



//raise an alarm if indicator 1 is not less than indicator 2

function less(valids, message, iscritical, sectionid) {
    //1<2
    //eg 1<2 means for all the age disagregations, indicator 1 should be less than indicator 2


    $("#msg" + sectionid).html("");

    var val1, val2;

    var columns = ["total"];
    var agearray = ["total"];



    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = 1;
    for (var i = 0; i < num_elems; i++) {

        var valarray = valids.split("<");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk

        var agegender = columns[i].split("_");
        var sx = "";
        var ag = "";
        if (agegender[0] === 'm') {
            sx = "Male";
        } else if (agegender[0] === 'f') {
            sx = "Female";
        }

        ag=agearray[i];

//        if (agegender[1] === 'uk') {
//            ag = "Unknown";
//        } else {
//            ag = agegender[1];
//        }

        if ((isNumber(val1) &&  val1 > 0) || ( isNumber(val2) && val2 > 0)) {
            if (val1 >= val2) {
                if (iscritical === 'yes') {
                    
                    $("#msg" + sectionid).html(" <img style='width:4%;' src='images/stop.png'/> <b>For " + ag + " years " + sx + " , " + message+"</b>");
                   // alert("For age disaggregation " + ag + " years " + sx + " , " + message);
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
                        $(" <font color='red'><b> For " + ag + " years " + sx + " , " + message + "</b></font> <br/>").insertBefore("#msg" + sectionid);
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


    $("#msg" + sectionid).html("");

    var val1, val2;

  var columns = ["total"];
    var agearray = ["total"];


    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = columns.length;
    for (var i = 0; i < num_elems; i++) {

        var valarray = valids.split(">");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk

        var agegender = columns[i].split("_");
        var sx = "";
        var ag = "";
        if (agegender[0] === 'm') {
            sx = "Male";
        } else if (agegender[0] === 'f') {
            sx = "Female";
        }

         ag=agearray[i];

//        if (agegender[1] === 'uk') {
//            ag = "Unknown";
//        } else {
//            ag = agegender[1];
//        }

        if ((isNumber(val1) &&  val1 > 0) || ( isNumber(val2) && val2 > 0)) {
            if (val1 <= val2) {
                if (iscritical === 'yes') {
                   
                    $("#msg" + sectionid).html(" <img style='width:4%;' src='images/stop.png'/>  <b>For " + ag + " years " + sx + " , " + message+"</b>");
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
                      $("<label class='img'> <font color='red'><b> For " + ag + " years " + sx + " , " + message + "</b></font> </label>").insertBefore("#msg" + sectionid);
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


    $("#msg" + sectionid).html("");

    var val1, val2;

    var columns = ["m_uk", "f_uk", "m_1", "f_1", "m_4", "f_4", "m_9", "f_9", "m_14", "f_14", "m_19", "f_19", "m_24", "f_24", "m_29", "f_29", "m_34", "f_34", "m_39", "f_39", "m_44", "f_44", "m_49", "f_49", "m_50", "f_50"];
 var agearray = ["unknown", "unknown", "<1", "<1", "1-4", "1-4", "5-9", "5-9", "10-14", "10-14", "15-19", "15-19", "20-24", "20-24", "25-29", "25-29", "30-34", "30-34", "35-39", "35-39", "40-44", "40-44", "45-49", "45-49", "50+", "50+"];



    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = columns.length;
    for (var i = 0; i < num_elems; i++) {

        var valarray = valids.split("<=");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk

        var agegender = columns[i].split("_");
        var sx = "";
        var ag = "";
        if (agegender[0] === 'm') {
            sx = "Male";
        } else if (agegender[0] === 'f') {
            sx = "Female";
        }

         ag=agearray[i];

//        if (agegender[1] === 'uk') {
//            ag = "Unknown";
//        } else {
//            ag = agegender[1];
//        }

        if ((isNumber(val1) &&  val1 > 0) || ( isNumber(val2) && val2 > 0)) {
            if (val1 > val2) {
                if (iscritical === 'yes') {
                    
                    
                    $("#msg" + sectionid).html(" <img style='width:4%;' src='images/stop.png'/> <b>For " + ag + " years " + sx + " , " + message+"</b>");
                   // alert("For age disaggregation " + ag + " years " + sx + " , " + message);
                    
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
     $("<label class='img'> <font color='red'><b> For " + ag + " years " + sx + " , " + message + "</b></font> </label>").insertBefore("#msg" + sectionid);
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

 
 
    $("#msg" + sectionid).html("");

    var val1, val2;

    var columns = ["m_uk", "f_uk", "m_1", "f_1", "m_4", "f_4", "m_9", "f_9", "m_14", "f_14", "m_19", "f_19", "m_24", "f_24", "m_29", "f_29", "m_34", "f_34", "m_39", "f_39", "m_44", "f_44", "m_49", "f_49", "m_50", "f_50"];
   var agearray = ["unknown", "unknown", "<1", "<1", "1-4", "1-4", "5-9", "5-9", "10-14", "10-14", "15-19", "15-19", "20-24", "20-24", "25-29", "25-29", "30-34", "30-34", "35-39", "35-39", "40-44", "40-44", "45-49", "45-49", "50+", "50+"];



    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = columns.length;
    for (var i = 0; i < num_elems; i++) {

        var valarray = valids.split(">=");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk



        var agegender = columns[i].split("_");
        var sx = "";
        var ag = "";
        if (agegender[0] === 'm') {
            sx = "Male";
        } else if (agegender[0] === 'f') {
            sx = "Female";
        }

          ag=agearray[i];

//        if (agegender[1] === 'uk') {
//            ag = "Unknown";
//        } else {
//            ag = agegender[1];
//        }

        if ((isNumber(val1) &&  val1 > 0) || ( isNumber(val2) && val2 > 0)) {
            
            
            if (val1 < val2) {
                console.log("val1:"+val1+" val2:"+val2);
                
                if (iscritical === 'yes') {
                 
                    $("#msg" + sectionid).html(" <img style='width:4%;' src='images/stop.png'/>   <b>For " + ag + " years " + sx + " , " + message+"</b>");
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

appendstring+=" * <b> For " + ag + " years " + sx + " , " + message + "</b> <br/>";


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


function Equal(valids, message, iscritical, sectionid) {
    //1>2
    //eg 1>2 means for all the age disagregations, indicator 1 should be greater than indicator 2


    $("#msg" + sectionid).html("");

    var val1, val2;

    var columns = ["m_uk", "f_uk", "m_1", "f_1", "m_4", "f_4", "m_9", "f_9", "m_14", "f_14", "m_19", "f_19", "m_24", "f_24", "m_29", "f_29", "m_34", "f_34", "m_39", "f_39", "m_44", "f_44", "m_49", "f_49", "m_50", "f_50"];
    var agearray = ["unknown", "unknown", "<1", "<1", "1-4", "1-4", "5-9", "5-9", "10-14", "10-14", "15-19", "15-19", "20-24", "20-24", "25-29", "25-29", "30-34", "30-34", "35-39", "35-39", "40-44", "40-44", "45-49", "45-49", "50+", "50+"];



    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = columns.length;
    for (var i = 0; i < num_elems; i++) {

        var valarray = valids.split("=");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk

        var agegender = columns[i].split("_");
        var sx = "";
        var ag = "";
        if (agegender[0] === 'm') {
            sx = "Male";
        } else if (agegender[0] === 'f') {
            sx = "Female";
        }

        ag=agearray[i];

//        if (agegender[1] === 'uk') {
//            ag = "Unknown";
//        } else {
//            ag = agegender[1];
//        }

        if ((isNumber(val1) &&  val1 > 0) || ( isNumber(val2) && val2 > 0)) {
            if ((val1 < val2) || (val1 > val2)) {
                if (iscritical === 'yes') {
                    
                    $("#msg" + sectionid).html("<img style='width:4%;' src='images/stop.png'/>  <b>For " + ag + " years " + sx + " , " + message+"</b>");
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
    $("<label class='img'> <font color='red'><b> For " + ag + " years " + sx + " , " + message + "</b></font> </label>").insertBefore("#msg" + sectionid);
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

    var valsarr = vals.split("+");

    var totl = 0;
 console.log("length ya multisum"+valsarr.length);
    for (var v = 0; v < valsarr.length; v++)
    {
        if(document.getElementById(valsarr[v])!==null){
         console.log("val sai ni "+valsarr[v]);
        var val = document.getElementById(valsarr[v]).value;
        if (isNumber(val))
        {

            totl = totl + parseInt(val);
        }
        }
    }

    return totl;

}




function redborder(age, elem,msg) {

    var valsarr = elem.split("+");



    for (var v = 0; v < valsarr.length; v++)
    {
        if(document.getElementById(age + "_" + valsarr[v])!==null){
        $("#" + age + "_" + valsarr[v]).css('border-color', '#ff0000');
        $("#" + age + "_" + valsarr[v]).css('background', '#ff0000');
         $("#" + age + "_" + valsarr[v]).prop('title',msg);
     }
           

    }

}

function yellowborder(age, elem,msg) {

    var valsarr = elem.split("+");



    for (var v = 0; v < valsarr.length; v++)
    {
        if(document.getElementById(age + "_" + valsarr[v])!==null){
        $("#" + age + "_" + valsarr[v]).css('border-color', '#fc7044');
        $("#" + age + "_" + valsarr[v]).css('background', '#fc7044');
        $("#" + age + "_" + valsarr[v]).prop('title',msg);
    }
    

    }

}


function blackborder(age, elem) {

    var valsarr = elem.split("+");



    for (var v = 0; v < valsarr.length; v++)
    {
        if(document.getElementById(age + "_" + valsarr[v])!==null){
        $("#" + age + "_" + valsarr[v]).css('border-color', '#000000');
        //skip blank elements
        if($("#" + age + "_" + valsarr[v]).val()!==''){
        $("#" + age + "_" + valsarr[v]).css('background', '#fae512');
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
        
        
        //.btn.blue:hover, .btn.blue:focus, .btn.blue:active, .btn.blue.active,
        
  
}

function savebutton_inactive(sectionid) {
   
      
        $("#validate_" + sectionid).html('Not Saved');
         $("#validate_"+sectionid).removeClass('blue');
         $("#validate_" + sectionid).css('background', '#ff0000');
         $("#validate_" + sectionid).css('color', '#ffffff');
  
}