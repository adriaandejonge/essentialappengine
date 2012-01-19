"use strict";

var myModule = Object.create({
searchAncestorByName : function(node, name) {
  if(node.tagName == name.toUpperCase()) {
    return node;
  } else if(node.parentNode != undefined) {
	return myModule.searchAncestorByName(node.parentNode, name);
  }
},
hasClicked : function(e) {
//window.alert('L3' + e.target.nodeName);
	if(e.target.nodeName == "H1")
	{
	var ancestor = myModule.searchAncestorByName(e.target, "article");
	if(ancestor.className == "minimized") ancestor.className = "";
	else ancestor.className = "minimized";
  }
},
initialHide : function(sourceElement) {
	var ancestor = myModule.searchAncestorByName(sourceElement, "article");
	ancestor.className = "minimized";
},
initEvents : function() {
	//window.alert('L2');
	var bodyEl = document.getElementsByTagName('body');
	if(bodyEl.length > 0) bodyEl.item(0).addEventListener('click', myModule.hasClicked, false);
	
	var selected = document.querySelectorAll('article h1');
	
	for (var i = 0; i < selected.length; i++) {
		//window.alert('L2.1d ' + selected.item(i));
		//selected.item(i).addEventListener('click', hasClicked, false);
		if(i > 0) myModule.initialHide(selected.item(i));
	}
}});


//window.alert('L1');
document.addEventListener('DOMContentLoaded', myModule.initEvents, false);
////window.addEventListener('load', initEvents, false);
//window.alert('L1');
