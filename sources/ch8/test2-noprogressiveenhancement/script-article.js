"use strict";

var searchAncestorByName = function(node, name) {
  if(node.tagName == name.toUpperCase()) {
    return node;
  } else if(node.parentNode != undefined) {
	return searchAncestorByName(node.parentNode, name);
  }
}

var hasClicked = function(e) {
	var ancestor = searchAncestorByName(e.target, "article");
	if(ancestor.className == "minimized") ancestor.className = "";
	else ancestor.className = "minimized";
}



var initEvents = function() {
	////window.alert('L2');
	var selected = document.querySelectorAll('article h1');
	
	for (var i = 0; i < selected.length; i++) {
		window.alert('L2.1d ' + selected.item(i));
		selected.item(i).addEventListener('click', hasClicked, false);
		
		
	}
	////window.alert('L3');
}
document.addEventListener('DOMContentLoaded', initEvents, false);
////window.addEventListener('load', initEvents, false);
//window.alert('L1');
