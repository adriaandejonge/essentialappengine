package com.appspot.xmpp;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.xmpp.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class XMPPSubscriptionServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {


    String action = request.getRequestURI()
        .replaceAll("/_ah/xmpp/subscription/", "")
        .replaceAll("/", "");

    XMPPService xmppService = XMPPServiceFactory.getXMPPService();
    Subscription subscription =
        xmppService.parseSubscription(request);

    DatastoreService datastoreService = DatastoreServiceFactory
        .getDatastoreService();
    Entity entity = new Entity("Subscription");
    entity.setProperty("from", "" + subscription.getFromJid());
    entity.setProperty("to", "" + subscription.getToJid());
    entity.setProperty("type",
        "" + subscription.getSubscriptionType());
    entity.setProperty("stanza", subscription.getStanza());
    entity.setProperty("created", new Date());
    entity.setProperty("action", action);

    datastoreService.put(entity);
  }
}
