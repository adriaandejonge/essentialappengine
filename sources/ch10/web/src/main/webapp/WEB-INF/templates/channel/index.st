<!DOCTYPE html>
<html>
  <link rel=stylesheet href=style-article.css>
  <meta charset="utf-8">
  <body>
    <header>
      <h1>Blog title</h1>
      <h2>Written by Adriaan de Jonge</h2>
    </header>
    <nav>
      <a href="/1">First link</a>
      <a href="/2">Second link</a>
    </nav>
    <article>
      <header>
        <h1>Dynamic text box</h1>
      </header>
      <p>Opened: <span id="opened">none yet</span></p>
      <p>Message: <span id="message">none yet</span></p>
      <p>Error: <span id="error">none yet</span></p>
      <p>Close: <span id="close">none yet</span></p>
    </article>
    <footer>Copyright &copy; 2011 - all rights reserved</footer>
        <script type="text/javascript" src="/_ah/channel/jsapi"></script>
    <script type="text/javascript" src="/script-article.js"></script>
    <script>
    var myModule = Object.create({
    sendMessage : function() {
      var xhr = new XMLHttpRequest();
      xhr.open('POST', '/send-message', true);
      xhr.send();
    },
    initChannel : function() {
      var channel = new goog.appengine.Channel('$token$');
      var socket = channel.open();
      socket.onopen = channelHandler.onOpened;
      socket.onmessage = channelHandler.onMessage;
      socket.onerror = channelHandler.onError;
      socket.onclose = channelHandler.onClose;
      var selected = document.querySelectorAll('article h1');
      selected.item(0).addEventListener('click',
          myModule.sendMessage, false);
    }});
    document.addEventListener('DOMContentLoaded',
        myModule.initChannel, false);
    </script>
  </body>
</html>