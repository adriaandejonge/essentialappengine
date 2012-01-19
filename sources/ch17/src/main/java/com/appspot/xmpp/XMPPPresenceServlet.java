package com.appspot.xmpp;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.xmpp.Presence;
import com.google.appengine.api.xmpp.Subscription;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class XMPPPresenceServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {


    String action = request.getRequestURI()
        .replaceAll("/_ah/xmpp/presence/", "")
        .replaceAll("/", "");

    XMPPService xmppService = XMPPServiceFactory.getXMPPService();
    Presence presence =
        xmppService.parsePresence(request);

    DatastoreService datastoreService = DatastoreServiceFactory
        .getDatastoreService();
    Entity entity = new Entity("Presence");
    entity.setProperty("from", "" + presence.getFromJid());
    entity.setProperty("to", "" + presence.getToJid());
    entity.setProperty("type","" + presence.getPresenceType());
    entity.setProperty("stanza", presence.getStanza());
    entity.setProperty("show", "" + presence.getPresenceShow());
    entity.setProperty("status", presence.getStatus());
    entity.setProperty("available", presence.isAvailable());
    entity.setProperty("created", new Date());
    entity.setProperty("action", action);

    datastoreService.put(entity);
  }
}
