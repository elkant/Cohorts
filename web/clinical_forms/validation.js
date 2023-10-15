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

function loadClinicalValidation(validation_servelet,fm,indics_tbl) {
    breakloop = false;

    $.ajax({
        url:validation_servelet,
        type:'post',
        dataType:'json',
        data: {fm:fm,
        tbl:indics_tbl},
        
        success:function (data) {
console.log("Vaalidation"+data);
var call_save=true;

            if (data!==null)
            {
    //appendstring="";

                for (var as = 0; as < data.length; as++)
                {
                    runvalidation(data[as].validation, data[as].message, data[as].iscritical, data[as].section_name, data[as].highlight_fields);
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
    
    //console.log('call save arrived at');
    //save_data();
    getElementsToBeSaved(fm);
}
else {
    
      //console.log('NO call save arrived at');
}

        }

    });

    
}


function runvalidation(valids, message, iscritical, sectionid,highlightfields) 
{ 
   if(iscritical==='1'){ iscritical="yes";  } 
   else if(iscritical==='0'){iscritical="no";} 
   
   /**
    * 
    * @param {type} valids
    * @param {type} message
    * @param {type} iscritical
    * @param {type} sectionid
    * @returns {undefined}
    * $('#art_start_date').val()>$('#last_vl_date').val()
((($('#death_date').val()).replace('-','')).substring(0,6))!==$('#month_of_death').val()
Here we are doing simple evaluation where we eval() the submitted jquery syntax parameter and if it returns a false, we break a loop and announce a message 
    */
    dynamicValidationCheck(valids,message,iscritical,sectionid,highlightfields);
//      if(valids.indexOf("<=")>=0){   lessOrEqual(valids,message,iscritical,sectionid);      }
// else if(valids.indexOf(">=")>=0){   greaterOrEqual(valids,message,iscritical,sectionid);   }
// else if(valids.indexOf("!=")>=0){   notEqual(valids,message,iscritical,sectionid);         }
// else if(valids.indexOf("<")>=0) {   less(valids,message,iscritical,sectionid);             }
// else if(valids.indexOf(">")>=0) {   greater(valids,message,iscritical,sectionid);          }

}



function dynamicValidationCheck(valids, message, iscritical, sectionid,highlightfields) {
    //1<2
    //eg 1<2 means for all the age disagregations, indicator 1 should be less than indicator 2
console.log(valids);
console.log(message);

console.log("Evaluation result of :"+valids+" is "+eval(valids));
var hel=highlightfields.split(",");
if(eval(valids)){
    //
     breakloop=true;
     console.log(message);
     $("#fedback").html(" <img style='width:4%;' src='images/stop.png'/> <b>" + message+"</b>");
    alert(message);
    
 for (var x=0;x<hel.length;x++){
    
      redborder(hel[x],message);
    }
    
}
else {
    for (var x=0;x<hel.length;x++)
    {
    
      greyborder(hel[x],message);
    }
         
    
}


}//end of dynamic check

function displayIfFunctionEvaluated(evalsyntax,destination)
{
    
    if (eval(evalsyntax)) {
        console.log(" custom show " + evalsyntax);
        $(destination).show();

    } else {
        $(destination).hide();
        $(destination).val("");  $(destination).trigger("change");
        console.log(" custom hide at display if evaluated " + evalsyntax+" results to "+eval(evalsyntax));
    }
    
}

function hideIfFunctionEvaluated(evalsyntax,destination)
{
    
    if (eval(evalsyntax)) {
        console.log(" custom show " + evalsyntax);
        $(destination).hide();

    } else {
        $(destination).show();
        $(destination).trigger("change");
        console.log("hide " + destination + ":::while at display ");
    }
    
}

//raise an alarm if indicator 1 is not less than indicator 2

function less(valids, message, iscritical, sectionid) {
    //1<2
    //eg 1<2 means for all the age disagregations, indicator 1 should be less than indicator 2


     $("#fedback").html("");

    var val1, val2;

    var columns = ["bl19_Male","bl19_Female"];
    var agearray = ["bl19_Male","bl19_Female"];



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
         if (agegender[0] === 'Male') {
            sx = "Male";
        } else if (agegender[0] === 'Female') {
            sx = "Female";
        }

         ag=agearray[i];

        if ((isNumber(val1) ) || ( isNumber(val2) )) {
            if (val1 < val2) {
                if (iscritical === '1') {
                    
                     $("#fedback").html(" <img style='width:4%;' src='images/stop.png'/> <b>" + message+"</b>");
                    alert("For age disaggregation " +columns[i]+ "   , " + message);
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
                        $(" <font color='red'><b>  " + message + "</b></font> <br/>").insertBefore("#msg" + sectionid);
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

    var columns = ["bl19_Male","bl19_Female"];
    var agearray = ["bl19_Male","bl19_Female"];


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
         if (agegender[0] === 'Male') {
            sx = "Male";
        } else if (agegender[0] === 'Female') {
            sx = "Female";
        }

         ag=agearray[i];

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

   var columns = ["bl19_Male","bl19_Female"];
    var agearray = ["bl19_Male","bl19_Female"];



    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = columns.length;
    for (var i = 0; i < num_elems; i++) {

        var valarray = valids.split("<=");



        console.log(valarray[0]+"<="+valarray[1]);
            
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk

        var agegender = columns[i].split("_");
        var sx = "";
        var ag = "";
         if (agegender[0] === 'Male') {
            sx = "Male";
        } else if (agegender[0] === 'Female') {
            sx = "Female";
        }

         ag=agearray[i];

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

     var columns = ["bl19_Male","bl19_Female"];
    var agearray = ["bl19_Male","bl19_Female"];



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
         if (agegender[0] === 'Male') {
            sx = "Male";
        } else if (agegender[0] === 'Female') {
            sx = "Female";
        }

          ag=agearray[i];

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

     var columns = ["bl19_Male","bl19_Female"];
    var agearray = ["bl19_Male","bl19_Female"];



    //    
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
    var num_elems = columns.length;
    for (var i = 0; i < num_elems; i++) {

        var valarray = valids.split("!=");



        //    alert(columns[i]+"_"+indicator_id);
        val1 = multisum(valarray[0], columns[i]);
        val2 = multisum(valarray[1], columns[i]);
        //m_uk

        var agegender = columns[i].split("_");
        var sx = "";
        var ag = "";
        if (agegender[0] === 'Male') {
            sx = "Male";
        } else if (agegender[0] === 'Female') {
            sx = "Female";
        }

        ag=agearray[i];

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

    var valsarr = vals.split("+");

    var totl = 0;
 console.log("valsarray"+valsarr);
 
    for (var v = 0; v < valsarr.length; v++)
    {
        if(document.getElementById(valsarr[v]+"_"+age)!==null){
        
        var val = document.getElementById(valsarr[v]+"_"+age).value;
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




function redborder(elem,msg) {

        if(document.getElementById(elem)!==null){
        $("#" + elem).css('border-color', '#ff0000');
        $("#" + elem).css('background', '#ffffff');
         $("#" + elem).prop('title',msg);
     }
 
}
function yellowborder(elem,msg) {

        if(document.getElementById(elem)!==null){
        $("#" + elem).css('border-color', '#fc7044');
        $("#" + elem).css('background', '#ffffff');
         $("#" + elem).prop('title',msg);
     }
 
}
function blackborder(elem,msg) 
{

        if(document.getElementById(elem)!==null){
        $("#" + elem).css('border-color', '#000000');
        $("#" + elem).css('background', '#ffffff');
         $("#" + elem).prop('title',msg);
     }
 
}
function greyborder(elem,msg) 
{

        if(document.getElementById(elem)!==null){
        $("#" + elem).css('border-color', '#808080');
        $("#" + elem).css('background', '#ffffff');
         $("#" + elem).prop('title',msg);
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

function displayIfFunction(sourceele,iscondition,equalval,destination,action)
{
    
    
   //as at this point and version of the application, the action is assumed to be show 
   var srcval=$(sourceele).val();
    //equalto
    //notequalto
    //contains
    if(iscondition==='equalto')
    {
        console.log("...in display if "+iscondition);
        
        if(srcval===equalval)
        {
            
            console.log("show "+destination);
            $(destination).show();} 
                         else { $(destination).hide();$(destination).val("");
                             $(destination).trigger("change");
         console.log("hide "+destination+":::while at equalto "+sourceele);                 
        }
    }
    else  if(iscondition==='notequalto')
    {
        
        if(srcval!==equalval){$(destination).show();  console.log("show "+destination);} 
                         else{$(destination).hide();  console.log("hide "+destination); $(destination).val("");$(destination).trigger("change");}
    }
     else  if(iscondition==='greaterthan')
    {
        
        if(srcval>equalval){$(destination).show();  console.log("show "+destination);} 
                         else{$(destination).hide();  console.log("hide "+destination); $(destination).val("");$(destination).trigger("change");}
    }
        else  if(iscondition==='lessthan')
    {
        
        if(srcval<equalval){$(destination).show();  console.log("show "+destination);} 
                         else{$(destination).hide();  console.log("hide "+destination); $(destination).val("");$(destination).trigger("change");}
    }
    else  if(iscondition==='contains')
    {
        
        if(srcval.indexOf(equalval)>=0 ){$(destination).show();  console.log("show "+destination);} 
                         else{$(destination).hide();  console.log("hide "+destination); $(destination).val("");$(destination).trigger("change");}
    }
        else  if(iscondition==='lessthan')
    {
        
        if(srcval<equalval ){$(destination).show(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).hide(); }
    }
    else  if(iscondition==='greaterthan')
    {
        
        if(srcval>equalval ){$(destination).show(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).hide(); }
    }
       else  if(iscondition==='greaterthanorequal')
    {
        
        if(srcval>=equalval ){$(destination).show(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).hide(); }
    }
       else  if(iscondition==='lessthanorequal')
    {
        
        if(srcval<=equalval ){$(destination).show(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).hide(); }
    }
    
    
   
     else  
    {
        
       $(destination).hide();
    }
    
}



function displayIfFunctionDontClearVal(sourceele,iscondition,equalval,destination,action)
{
    
    
   //as at this point and version of the application, the action is assumed to be show 
   var srcval=$(sourceele).val();
    //equalto
    //notequalto
    //contains
    if(iscondition==='equalto')
    {
        console.log("...in display if "+iscondition);
        
        if(srcval===equalval)
        {
            
            console.log("show "+destination);
            $(destination).show();} 
                         else { $(destination).hide();
                             $(destination).trigger("change");
         console.log("hide "+destination+":::while at equalto "+sourceele);                 
        }
    }
    else  if(iscondition==='notequalto')
    {
        
        if(srcval!==equalval){$(destination).show();  console.log("show "+destination);} 
                         else{$(destination).hide();  console.log("hide "+destination); $(destination).trigger("change");}
    }
     else  if(iscondition==='greaterthan')
    {
        
        if(srcval>equalval){$(destination).show();  console.log("show "+destination);} 
                         else{$(destination).hide();  console.log("hide "+destination); $(destination).trigger("change");}
    }
        else  if(iscondition==='lessthan')
    {
        
        if(srcval<equalval){$(destination).show();  console.log("show "+destination);} 
                         else{$(destination).hide();  console.log("hide "+destination); $(destination).trigger("change");}
    }
    else  if(iscondition==='contains')
    {
        
        if(srcval.indexOf(equalval)>=0 ){$(destination).show();  console.log("show "+destination);} 
                         else{$(destination).hide();  console.log("hide "+destination); $(destination).trigger("change");}
    }
    
    
    
      else  if(iscondition==='lessthan')
    {
        
        if(srcval<equalval ){$(destination).show(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).hide(); }
    }
    else  if(iscondition==='greaterthan')
    {
        
        if(srcval>equalval ){$(destination).show(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).hide(); }
    }
       else  if(iscondition==='greaterthanorequal')
    {
        
        if(srcval>=equalval ){$(destination).show(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).hide(); }
    }
       else  if(iscondition==='lessthanorequal')
    {
        
        if(srcval<=equalval ){$(destination).show(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).hide(); }
    }
    
    
   
     else  
    {
        
       $(destination).hide();
    }
    
}


function hideIfFunction(sourceele,iscondition,equalval,destination,action)
{
    
    
   //as at this point and version of the application, the action is assumed to be show 
   var srcval=$(sourceele).val();
    //equalto
    //notequalto
    //contains
    if(iscondition==='equalto')
    {
        console.log("...in display if "+iscondition);
        
        if(srcval===equalval)
        {
            
            console.log("show "+destination);
            $(destination).hide();$(destination).val("");  $(destination).trigger("change");
        } 
     else { $(destination).show();
         console.log("hide "+destination+":::while at hideif "+sourceele);                 
        }
    }
    else  if(iscondition==='notequalto')
    {
        
        if(srcval!==equalval){$(destination).hide(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).show(); }
    }
    else  if(iscondition==='contains')
    {
        
        if(srcval.indexOf(equalval)>=0 ){$(destination).hide(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).show(); }
    }
     else  if(iscondition==='lessthan')
    {
        
        if(srcval<equalval ){$(destination).hide(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).show(); }
    }
    else  if(iscondition==='greaterthan')
    {
        
        if(srcval>equalval ){$(destination).hide(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).show(); }
    }
       else  if(iscondition==='greaterthanorequal')
    {
        
        if(srcval>=equalval ){$(destination).hide(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).show(); }
    }
       else  if(iscondition==='lessthanorequal')
    {
        
        if(srcval<=equalval ){$(destination).hide(); $(destination).val("");$(destination).trigger("change"); } 
                         else{$(destination).show(); }
    }
     else  
    {
        
       $(destination).hide();
    }
    
}

//Below is a function for disabling passed select options 

function disableSelectOptionsIf(source_element,iscondition,expectedsource_value,dest_element,options_to_disable){
    
    
    var srcval=$(source_element).val();
    var opts_=options_to_disable.split(":");
    
   
    
    
    
    
    
     if(iscondition==='equalto')
     {   
      
         if(srcval===expectedsource_value)
        {
            for(var x=0;x<opts_.length;x++) { $(""+dest_element+" option[value='"+ opts_[x] + "']").attr('disabled', true); } 
        console.log("Inside Disable if "+source_element+"_"+iscondition+"_"+expectedsource_value+"_"+dest_element+"_"+options_to_disable);
        }
        else 
        {
          for(var x=0;x<opts_.length;x++) { $(dest_element+" option[value='"+ opts_[x] + "']").attr('disabled', false); }            
        }
         
         
     }
    else   if(iscondition==='notequalto')
    {
        
          if(srcval!==expectedsource_value)
        {
            for(var x=0;x<opts_.length;x++) { $(dest_element+" option[value='"+ opts_[x] + "']").attr('disabled', true); } 
        
        }
        else 
        {
          for(var x=0;x<opts_.length;x++) { $(dest_element+" option[value='"+ opts_[x] + "']").attr('disabled', false); }            
        }
        
    }
    else   if(iscondition==='contains')
    {
        
         if(srcval.indexOf(expectedsource_value)>=0 )
        {
            for(var x=0;x<opts_.length;x++) { $(dest_element+" option[value='"+ opts_[x] + "']").attr('disabled', true); } 
        
        }
        else 
        {
          for(var x=0;x<opts_.length;x++) { $(dest_element+" option[value='"+ opts_[x] + "']").attr('disabled', false); }            
        }  
        
    }
    
    
}

function setValueIf(source_element,iscondition,expectedsource_value,dest_element,value_to_set){
    //SetValueIf may not work for MultiSelectAs at Now
    
    var srcval=$(source_element).val();
    
   
    
    
     if(iscondition==='equalto')
     {   
      
if  (srcval===expectedsource_value) 
     {  
         $(dest_element).val(value_to_set);  
     }          
else    {  
$(dest_element).val(""); 
}
       
         
         
     }
    else   if(iscondition==='notequalto')
    {
        
         if  (srcval===expectedsource_value) 
     {  
         $(dest_element).val(value_to_set);  
     }          
else    
{  
$(dest_element).val(""); 
}
       
        
    }
    else   if(iscondition==='contains')
    {
        
        if(srcval.indexOf(expectedsource_value)>=0 )

        {  
        $(dest_element).val(value_to_set);  
        }          
        else    
        {  
        $(dest_element).val(""); 
        }  
        
    }
    
    
}

function setValueOfElementToAnotherElement(source_element,dest_element,static_value){
    //Read Data from a variable and set it into another  . If static Value is zero, then   
    var srcval=$(source_element).val(); 
    
    if(static_value!=='')
    {
    $(dest_element).val(static_value); 
    }
    else 
    {
    $(dest_element).val(srcval); 
    }
}


function deductTwoVariables(expectedgreaterval, expectedlessval, destination)
{
    
    
    
}