package com.appspot.xmpp;

import com.google.appengine.api.xmpp.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendXMPPServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    JID jid = new JID("adriaandejonge@gmail.com");
    String text = "hello world";
    Message message = new MessageBuilder()
        .withRecipientJids(jid)
        .withBody(text)
        .build();

    XMPPService xmppService = XMPPServiceFactory.getXMPPService();
    if(xmppService.getPresence(jid).isAvailable()) {
      SendResponse sendResponse = xmppService.sendMessage(message);
      if(sendResponse.getStatusMap().get(jid) == SendResponse
          .Status.SUCCESS) {
        response.getWriter().write("OK");
      }
      else {
        response.getWriter().write("NOT OK");
      }

    }  else {
      response.getWriter().write("Unavailable");
    }

  }
}
