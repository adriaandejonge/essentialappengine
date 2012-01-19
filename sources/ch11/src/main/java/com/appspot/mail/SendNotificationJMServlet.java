package com.appspot.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class SendNotificationJMServlet extends HttpServlet {

  private static final String SENDER_MAIL = 
      "info@blo-gae.appspotmail.com";
  private static final String SENDER_NAME = "No Reply";
  private static final StringTemplateGroup templates = 
      new StringTemplateGroup("mail", "WEB-INF/templates/mail");

  private static final long serialVersionUID = 
      -1705697052143137757L;

  protected void doPost(HttpServletRequest request, 
    HttpServletResponse response)
      throws ServletException, IOException {

    String recipient = request.getParameter("recipient");
    String thread = request.getParameter("thread");
    String url = request.getParameter("url");

    String subject = createSubject(thread);
    String body = createBody(url, thread);

    sendMail(recipient, subject, body);
  }

  private void sendMail(String recipient, String subject, 
      String body) throws UnsupportedEncodingException, 
      ServletException {
    try {
      InternetAddress senderAddress = new InternetAddress(
          SENDER_MAIL, SENDER_NAME);
      InternetAddress recipientAddress = 
        new InternetAddress(recipient);

      Properties props = new Properties();
      Session session = Session.getDefaultInstance(props, null);
      Message mail = new MimeMessage(session);

      mail.setFrom(senderAddress);
      mail.addRecipient(Message.RecipientType.TO, recipientAddress);
      mail.setSubject(subject);
      mail.setText(body);
      Transport.send(mail);
    } catch (AddressException e) {
      throw new ServletException(e);
    } catch (MessagingException e) {
      throw new ServletException(e);
    }
  }

  private String createBody(String url, String thread) {
    StringTemplate body = 
        templates.getInstanceOf("notification-body");
    body.setAttribute("url", filter(url));
    body.setAttribute("thread", filter(thread));
    return body.toString();
  }

  private String createSubject(String thread) {
    StringTemplate subject = 
        templates.getInstanceOf("notification-subject");
    subject.setAttribute("thread", filter(thread));
    return subject.toString();
  }

  private String filter(String text) {
    if (text == null)
      return "";
    return text.replaceAll("<?>?", "");
  }
}
