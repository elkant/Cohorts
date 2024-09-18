/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function sum_indicators(indicator_id){
    var total=0, elem_value="";
   // indic	9m	9f	14m	14f	19m	19f	24m	24f	25m	25f	ttl

var columns = ["_17","_19","_24","_29","_34","_39","_44","_49","_50"];
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);
var has_a_non_blank=0;
var num_elems = columns.length;
for(var i=0;i<num_elems;i++){
  
    elem_value=document.getElementById(indicator_id+"_"+columns[i]).value;
    
    if(elem_value!==""){
        has_a_non_blank++;
        console.log(indicator_id+"_"+columns[i]+":"+elem_value);
  total = total+parseInt(elem_value); 
  document.getElementById(indicator_id+"_value").value = total;
    }
}
if(has_a_non_blank===0){total=0; document.getElementById(indicator_id+"_value").value = "";}



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
    
    
    function indicate_changed(id,sumindic){
     $("#"+id).css('background-color', '#fae512');
     if($("#"+id).val()===''){$("#"+id).val("0");}
     sum_indicators(sumindic);
     
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
function buildAutocalculate(dataelements)
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
        console.log("Total__"+rightside);
            document.getElementById(rightside).value = ttl;
             
             
             
            if(ttl<0){redbordercl(rightside);
                $("#savebutton").hide();
            $("#fedback").html("Validation Error!. Please correct to save entries");
            } else 
            {blackbordercl(rightside);
                $("#savebutton").show();
             $("#fedback").html("");
            }
         
     }
     //division
     else   if(lftside.indexOf("/")>=0){
         
         var ttl=0;
         
         //here awe are sssuming that we only having two variables being devided
         
         //i expecte tio be parsing parameters that look like 12/13@14 
       ttl=devide_and_display(dems[s]);  
     
         
         
                 
       // console.log("Total__"+rightside);
        
        //document.getElementById(rightside).value = ttl;
             
             
             
            if(ttl<0){redbordercl(rightside);
                $("#savebutton").hide();
            $("#fedback").html("Validation Error!. Please correct to save entries");
            } else 
            {blackbordercl(rightside);
                $("#savebutton").show();
             $("#fedback").html("");
            }
         
     }
     
     
     
     else {
        autocalculate(innerdems[0],innerdems[1]);
    }
  }
}

function autocalculate_and_return(sourceindicators)
{
   // console.log(sourceindicators+" = source indicators");
    
var agesets = ["_17","_19","_24","_29","_34","_39","_44","_49","_50","value"];
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
    
   value=document.getElementById(sourcearrays_neg[g]+"_"+agesets[i]).value;
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
    
  value=document.getElementById(sourcearrays[f]+"_"+agesets[i]).value;
  $(sourcearrays[f]+"_"+agesets[i]).change();
  
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

function devide_and_return(sourceindicators)
{
   // console.log(sourceindicators+" = source indicators");
    
var agesets = ["_17","_19","_24","_29","_34","_39","_44","_49","_50","value"];
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);

//if the split function contains both + and -, work on splitting the + first then - inside the function loops



 var sourcearrays=sourceindicators.split("/");

var num_agesets = agesets.length;

var num_indics = sourcearrays.length;

//alert(num_indics);

for(var i=0;i<num_agesets;i++){
 var total='', elem_value="",value="";   
for(var f=0;f<num_indics;f++){
//    alert(columns[i]+"_"+indicator_id);
//check for existance of -


if(1==1) {
    
  value=document.getElementById(sourcearrays[f]+"_"+agesets[i]).value;
  $(sourcearrays[f]+"_"+agesets[i]).change();
  
    if(value!=='')
    {
        
  total = parseInt(value); 
  //console.log(" sum of "+sourcearrays[f]+" is "+total);
  
    }
    
    
}

    
}//end of looping of indicators



}//end of age sets


return total;
}


function devide_and_display(sourceindicators)
{
   // console.log(sourceindicators+" = source indicators");
    //i expect to be parsing parameters that look like 12/13@14 
var agesets = ["_17","_19","_24","_29","_34","_39","_44","_49","_50","value"];
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);

//if the split function contains both + and -, work on splitting the + first then - inside the function loops



 var innerdems=sourceindicators.split("@");  
     
     //if the formula has a -(, then work on the two data values separately and submit the total
     //here, we are assuming a maximum of only 
     var sourceiis=innerdems[0];
     var destiis=innerdems[1];


 var sourceiisar=sourceiis.split("/");


console.log("Variable one ni ::"+sourceiisar[0]);
console.log("Variable two ni ::"+sourceiisar[1]);

var num_agesets = agesets.length;


//alert(num_indics);

for(var i=0;i<num_agesets;i++){
    
 var total='', elem_value="",value1="";   
 var value2="";   
//for(var f=0;f<num_indics;f++){
//    alert(columns[i]+"_"+indicator_id);
//check for existance of -


if(1===1) {
    
  value1=document.getElementById(sourceiisar[0]+"_"+agesets[i]).value;
  value2=document.getElementById(sourceiisar[1]+"_"+agesets[i]).value;
  $(sourceiisar[0]+"_"+agesets[i]).change();
  $(sourceiisar[1]+"_"+agesets[i]).change();
  
    if(value1!=='' && value2!=='')
    {
        
  total = Math.round((parseInt(value1)/parseInt(value2))*100); 
  console.log(total);
  if(isNaN(total) || value2==='0' ){total=0;}
  
  //console.log(" sum of "+sourcearrays[f]+" is "+total);
  document.getElementById(destiis+"_"+agesets[i]).value = total;
    }
    
    
}

    
//}//end of looping of indicators



}//end of age sets


return total;
}

function autocalculate(sourceindicators,destination_indicator){
  //  console.log(sourceindicators+" = source indicators");
  //  console.log(destination_indicator+" = destination indicators");
var agesets = ["_17","_19","_24","_29","_34","_39","_44","_49","_50","value"];
//    var indicator_id=document.getElementById("indic_pos_"+indic_pos).value;
//    alert(indicator_id);

//if the split function contains both + and -, work on splitting the + first then - inside the function loops

sourceindicators=sourceindicators.replace("(","");

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
    
   value=document.getElementById(sourcearrays_neg[g]+"_"+agesets[i]).value;
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
    console.log("at Addition point::::"+sourcearrays[f]+"_"+agesets[i]);
  value=document.getElementById(sourcearrays[f]+"_"+agesets[i]).value;
    if(value!=='')
    {
        
  total = total+parseInt(value); 
  //console.log(" sum of "+sourcearrays[f]+" is "+total);
  
    }
    
    
}
   
    
 if(isNumber(total))
{
document.getElementById(destination_indicator+"_"+agesets[i]).value = total;
}   
   
if(total<0){redbordercl(destination_indicator+"_"+agesets[i]);
    $("#savebutton").hide();
    $("#fedback").html("Validation Error!. Please correct to save entries");
}
else {blackbordercl(destination_indicator+"_"+agesets[i]);
    $("#savebutton").html("");
    $("#savebutton").show();}    
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