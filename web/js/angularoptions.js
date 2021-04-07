/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/* swap open/close side menu icons */


//Load the years drop down 

var d = new Date();
var curryear = d.getFullYear();


 var yearsarray=[];
 
var appl = angular.module("kpapp",[]);

appl.controller("kpcont", function($scope) {  
    
   //add the last three years to the years drop down 
   for(var a=curryear-2;a<=curryear;a++){    
    yearsarray.push(a);    
   } 
 $scope.miaka = yearsarray;
 //console.log(yearsarray);
// var _yesno=["Yes","No"];
// 
// var _yesnona=["Yes","No","NA"];
// 
  var mns=[{id:'10',val:'October',yearoffset:1},{id:'11',val:'November',yearoffset:1},{id:'12',val:'December',yearoffset:1},{id:'01',val:'January',yearoffset:0},{id:'02',val:'February',yearoffset:0},{id:'03',val:'March',yearoffset:0},{id:'04',val:'April',yearoffset:0},{id:'05',val:'May',yearoffset:0},{id:'06',val:'June',yearoffset:0},{id:'07',val:'July',yearoffset:0},{id:'08',val:'August',yearoffset:0},{id:'09',val:'September',yearoffset:0}];
//   
// $scope.yesno=_yesno;
 $scope.months=mns;
// $scope.yesnona=_yesnona;

 
});



appl.directive("requiredOption", function() {
  return {
    template : `<font style='color:red;'>*</font>`
  };
});


// Directive to show refresh, Data export button and Options for excel reports



    



