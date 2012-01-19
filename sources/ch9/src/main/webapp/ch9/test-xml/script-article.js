"use strict";

var myModule = Object.create({
ajaxHandler : function handler(e) {
 if(this.readyState == 4 && this.status == 200) {
   var name = this.responseXML.getElementsByTagName('name');
   var description = this.responseXML.getElementsByTagName('description');
   var lastChange = this.responseXML.getElementsByTagName('lastChange');
   var newText = name.item(0).firstChild.nodeValue + ' '
     + description.item(0).firstChild.nodeValue + ' '
     + lastChange.item(0).firstChild.nodeValue + ' ';
   var selected = document.querySelectorAll('p#replace');
   selected.item(0).firstChild.nodeValue = newText;
 } else if (this.readyState == 4 && this.status != 200) {
   // error handling
 } else {
   // status handling
 }
},
hasClicked : function(e) {
  if(e.target.nodeName == "H1") {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = myModule.ajaxHandler;
    xhr.open("GET", "values.xml");
    xhr.send();
  }
},
initEvents : function() {
  var bodyEl = document.getElementsByTagName('body');
  if(bodyEl.length > 0) bodyEl.item(0).addEventListener('click', myModule.hasClicked, false);
}});

document.addEventListener('DOMContentLoaded', myModule.initEvents, false);
