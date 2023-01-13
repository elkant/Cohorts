/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function sum_indicators(indicator_id){
    var total=0, elem_value="";
var columns = ["m_9","f_9","m_14","f_14","m_19","f_19","m_24","f_24","m_25","f_25","value"];
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
var num_elems = columns.length;
for(var i=0;i<num_elems;i++){
//    alert(columns[i]+"_"+indicator_id);
    elem_value=document.getElementById(columns[i]+"_"+indicator_id).value;
    if(elem_value!=="")
    {
  total = total+parseInt(elem_value);  
    }
}

document.getElementById("total_"+indicator_id).value = total;
//alert(total);
}

    function redbordercl(id)
    {
     $("#"+id).css('background-color', '#ff0000');
     $("#"+id).css('border-color', '#ff0000');
     $("#"+id).css('color', '#ffffff');
     
    }
    
    function blackbordercl(id){
     $("#"+id).css('background-color','#aeb5ae');
     $("#"+id).css('border-color','#000000');
     $("#"+id).css('color','#000000');
     
    }
    
    
    function indicate_changed(id){
     $(id).css('background-color', '#fae512');
     
    } 
    function section_changed(section_id){
      $("#section"+section_id).css('background','red');
      format_section(section_id);
    }
    
    function section_saved(section_id){
      $("#section"+section_id).css('background','green');
      format_section(section_id);
    }
    
    function format_section(section_id){
     $("#collapse"+section_id).collapse();
     $("#section_"+section_id).removeClass('blue');
     $("#section_"+section_id).css('color','white'); 
     $("#section_"+section_id).css('font-weight','700'); 
    }
//eg function sum_multipleindicators('1+2+3-1','4'){
function buildAutocalculate(dataelements,savebtn)
{
  var dems=dataelements.split(",");
  for(var s=0;s<dems.length;s++)
  {
     var innerdems=dems[s].split("@");  
     
     //if the formula has a -(, then work on the two data values separately and submit the total
     //here, we are assuming a maximum of only 
     var lftside=innerdems[0];
     var rightside=innerdems[1];
     
     if(lftside.indexOf("-(")>=0){
         var innersplit=lftside.split("-(");
         var ttl=0;
         
         for(var ii=0;ii<innersplit.length;ii++){
            //do all the calculation here 
            if(ii===0)
            {
         ttl=ttl+parseInt(autocalculate_and_return(innersplit[ii]));  
            }
     else {
       ttl=ttl-parseInt(autocalculate_and_return(innersplit[ii]));     
         
     }
             
         }         
        
            document.getElementById(rightside).value = ttl;   
            if(ttl<0){redbordercl(rightside);
                $("#validate_"+savebtn).hide();
            $("#msg"+savebtn).html("Validation Error!. Please correct to save entries");
            } else 
            {blackbordercl(rightside);
                $("#validate_"+savebtn).show();
             $("#msg"+savebtn).html("");
            }
         
     }
     else {
        autocalculate(innerdems[0],innerdems[1],savebtn);
    }
  }
}

function autocalculate_and_return(sourceindicators)
{
   // console.log(sourceindicators+" = source indicators");
    
//var agesets = ["m_9","f_9","m_14","f_14","m_19","f_19","m_24","f_24","m_25","f_25","value"];
var agesets = ["value"];
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);

//if the split function contains both + and -, work on splitting the + first then - inside the function loops



 var sourcearrays=sourceindicators.split("+");

var num_agesets = agesets.length;

var num_indics = sourcearrays.length;

//alert(num_indics);

for(var i=0;i<num_agesets;i++){
 var total=0, elem_value="",value="";   
for(var f=0;f<num_indics;f++){
//    alert(columns[i]+"_"+indicator_id);
//check for existance of -

//check if there is subtraction
if(sourcearrays[f].indexOf("-")>=0){
    
  //  deal with splitting the -
  var sourcearrays_neg=sourcearrays[f].split("-");
  
  for(var g=0;g<sourcearrays_neg.length;g++){
    
   value=document.getElementById(sourcearrays_neg[g]).value;
   //in array index 0, pick the value in the array and summ with 0, then onwards
   
  if(g===0){
                       // console.log(g+" value ya "+sourcearrays_neg[g]+" ni "+value);
                    if(value!==''){
                        
  total = total+parseInt(value);
  
    } 
    else 
    { 
        value=0;
       total = total+parseInt(value);    
    }
  }
  else 
    {
        if(value===''){ value=0;}
       total = total-parseInt(value);    
    }
      
  }
  
    
}
//if no subtration i.e only addition
else {
    
  value=document.getElementById(sourcearrays[f]).value;
    if(value!=='')
    {
        
  total = total+parseInt(value); 
  //console.log(" sum of "+sourcearrays[f]+" is "+total);
  
    }
    
    
}

    
}//end of looping of indicators



}//end of age sets


return total;
}



function autocalculate(sourceindicators,destination_indicator,save_button){
  //  console.log(sourceindicators+" = source indicators");
  //  console.log(destination_indicator+" = destination indicators");
var agesets = ["total"];
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);

//if the split function contains both + and -, work on splitting the + first then - inside the function loops

sourceindicators=sourceindicators.replace("(","");

 var sourcearrays=sourceindicators.split("+");

var num_agesets = 1;

var num_indics = sourcearrays.length;

//alert(num_indics);

for(var i=0;i<num_agesets;i++){
 var total=0, elem_value="",value="";   
for(var f=0;f<num_indics;f++){
//    alert(columns[i]+"_"+indicator_id);
//check for existance of -

//check if there is subtraction
if(sourcearrays[f].indexOf("-")>=0){
    
  //  deal with splitting the -
  var sourcearrays_neg=sourcearrays[f].split("-");
  
  for(var g=0;g<sourcearrays_neg.length;g++){
    
   value=document.getElementById(sourcearrays_neg[g]).value;
   //in array index 0, pick the value in the array and summ with 0, then onwards
   
  if(g===0){
                        //console.log(g+" value ya "+sourcearrays_neg[g]+" ni "+value);
                    if(value!==''){
                        
  total = total+parseInt(value);
  
    } 
    else 
    { 
        value=0;
       total = total+parseInt(value);    
    }
  }
  else 
    {
        if(value===''){ value=0;}
       total = total-parseInt(value);    
    }
      
  }
  
    
}
//if no subtration i.e only addition
else {
    
  value=document.getElementById(sourcearrays[f]).value;
    if(value!=='')
    {
        
  total = total+parseInt(value); 
  //console.log(" sum of "+sourcearrays[f]+" is "+total);
  
    }
    
    
}
   
    
 if(isNumber(total))
{
document.getElementById(destination_indicator).value = total;
}   
   
if(total<0){redbordercl(destination_indicator);
    $("#validate_"+save_button).hide();
    $("#msg"+save_button).html("Validation Error!. Please correct to save entries");
}
else {blackbordercl(destination_indicator);
    $("#msg"+save_button).html("");
    $("#validate_"+save_button).show();}    
}//end of looping of indicators



}//end of age sets


//alert(total);
}




function isNumber(n) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}

 function removeFirstZero(elem){
           
           
           var vl=$("#"+elem).val();
           
           if(vl.length>=2 && vl.startsWith("0"))
           {
               
               vl=vl.replace("0","");
               $("#"+elem).val(vl);
                  // alert(vl);
           }
           
       
           
       }