package com.appspot.mail;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class ReceiveJMMailServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
      throws ServletException, IOException {

    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    try {
      MimeMessage receivedMessage = new MimeMessage(session, request
          .getInputStream());

      DatastoreService datastoreService = DatastoreServiceFactory
          .getDatastoreService();

      Entity mail = new Entity("ReceivedJMMail");

      mail.setProperty("subject", receivedMessage.getSubject());
      mail.setProperty("content", readMessage(receivedMessage));
      mail.setProperty("sender", readSender(receivedMessage));
      mail.setProperty("date", new Date());

      datastoreService.put(mail);

    } catch (MessagingException e) {
      throw new ServletException(e);
    }
  }

  private Object readMessage(MimeMessage receivedMessage)
      throws MessagingException, IOException, ServletException {

    Object multipartObject = receivedMessage.getContent();

    if (multipartObject instanceof MimeMultipart) {
      MimeMultipart multipart = (MimeMultipart) multipartObject;
      BodyPart bodypart = multipart.getBodyPart(0);
      return bodypart.getContent();
    }
    return null;
  }

  private String readSender(MimeMessage receivedMessage)
      throws ServletException, MessagingException {
    try {
      Address[] addresses = receivedMessage.getFrom();

      if (addresses.length > 0) {
        return addresses[0].toString();
      }
      return "";
    } catch (AddressException e) {
      throw new ServletException(e);
    }
  }
}
