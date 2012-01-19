package com.appspot.mail;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
import com.google.appengine.api.mail.MailService.Message;

/**
 * Low-level alternative to SendNotificationJMServlet. 
 */
public class SendNotificationLLServlet extends HttpServlet {

  private static final String SENDER = "No Reply <info@blo-gae.appspotmail.com>";
  private static final StringTemplateGroup templates = 
    new StringTemplateGroup("mail", "WEB-INF/templates/mail");

  private static final long serialVersionUID = 4066792792563798851L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String recipient = request.getParameter("recipient");
    String thread = request.getParameter("thread");
    String url = request.getParameter("url");

    String subject = createSubject(thread);
    String body = createBody(url, thread);

    sendMail(recipient, subject, body);
  }

  private void sendMail(String recipient, String subject, 
      String body) throws IOException {
    MailService mailService = MailServiceFactory.getMailService();
    Message mail = new Message(SENDER, recipient, subject, body);
    mailService.send(mail);
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
