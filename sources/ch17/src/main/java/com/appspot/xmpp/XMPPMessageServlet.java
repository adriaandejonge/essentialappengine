package com.appspot.xmpp;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.xmpp.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class XMPPMessageServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    XMPPService xmppService = XMPPServiceFactory.getXMPPService();
    Message message =
        xmppService.parseMessage(request);

    DatastoreService datastoreService = DatastoreServiceFactory
        .getDatastoreService();
    Entity entity = new Entity("Message");

    StringBuilder to = new StringBuilder();
    for(JID jid : message.getRecipientJids()) {
      to.append(jid.toString());
      to.append("; ");
    }
    entity.setProperty("to", to.toString());

    entity.setProperty("from", "" + message.getFromJid());
    entity.setProperty("type", "" + message.getMessageType());
    entity.setProperty("stanza", message.getStanza());
    entity.setProperty("body", message.getBody());
    entity.setProperty("xml", message.isXml());
    entity.setProperty("created", new Date());

    datastoreService.put(entity);
  }
}
