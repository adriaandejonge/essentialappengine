"use strict";
var channelHandler = Object.create({
onOpened : function() {
  var el = document.getElementById('opened');
  el.firstChild.nodeValue = 'opened';
},
onMessage : function(message) {
  var el = document.getElementById('message');
  el.firstChild.nodeValue = message.data;
},
onError : function() {
  var el = document.getElementById('error');
  el.firstChild.nodeValue = 'error';
},
onClose : function() {
  var el = document.getElementById('close');
  el.firstChild.nodeValue = 'close';
}
});