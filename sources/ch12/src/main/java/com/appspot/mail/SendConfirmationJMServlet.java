package com.appspot.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class SendConfirmationJMServlet extends HttpServlet {

  private static final String SENDER_MAIL = 
      "info@blo-gae.appspotmail.com";
  private static final String SENDER_NAME = "No Reply";
  private static final StringTemplateGroup templates 
      = new StringTemplateGroup("mail", "WEB-INF/templates/mail");

  private static final long serialVersionUID = 3933171244097665509L;

  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException,
          IOException {

    String recipient = request.getParameter("recipient");
    String thread = request.getParameter("thread");
    String url = request.getParameter("url");
    String message = request.getParameter("message");

    String subject = createSubject(thread);
    String body = createBody(url, thread);
    String htmlBody = createHtmlBody(url, thread);
    String attachment = createAttachment(message);

    sendMail(recipient, subject, body, htmlBody, attachment);
  }

  private void sendMail(String recipient, String subject,
          String body, String htmlBody, String attachment)
          throws UnsupportedEncodingException, ServletException {
    try {
      InternetAddress senderAddress = new InternetAddress(
              SENDER_MAIL, SENDER_NAME);
      InternetAddress recipientAddress = new InternetAddress(
              recipient);

      Multipart multipart = new MimeMultipart();

      MimeBodyPart plainPart = new MimeBodyPart();
      plainPart.setContent(body, "text/plain");
      multipart.addBodyPart(plainPart);

      MimeBodyPart htmlPart = new MimeBodyPart();
      htmlPart.setContent(htmlBody, "text/html");
      multipart.addBodyPart(htmlPart);

      MimeBodyPart attPart = new MimeBodyPart();
      attPart.setContent(attachment, "text/plain");
      attPart.setFileName("message.txt");
      multipart.addBodyPart(attPart);

      Properties props = new Properties();
      Session session = Session.getDefaultInstance(props, null);
      Message mail = new MimeMessage(session);

      mail.setFrom(senderAddress);
      mail.addRecipient(Message.RecipientType.TO, 
        recipientAddress);
      mail.setSubject(subject);
      mail.setContent(multipart);
      Transport.send(mail);

    } catch (AddressException e) {
      throw new ServletException(e);
    } catch (MessagingException e) {
      throw new ServletException(e);
    }
  }

  private String createBody(String url, String thread) {
    StringTemplate body = templates
            .getInstanceOf("confirmation-body");
    body.setAttribute("url", filter(url));
    body.setAttribute("thread", filter(thread));
    return body.toString();
  }

  private String createHtmlBody(String url, String thread) {
    StringTemplate body = templates
            .getInstanceOf("confirmation-html-body");
    body.setAttribute("url", filter(url));
    body.setAttribute("thread", filter(thread));
    return body.toString();
  }

  private String createSubject(String thread) {
    StringTemplate subject = templates
            .getInstanceOf("confirmation-subject");
    subject.setAttribute("thread", filter(thread));
    return subject.toString();
  }

  private String createAttachment(String message) {
    StringTemplate subject = templates
            .getInstanceOf("confirmation-attachment");
    subject.setAttribute("message", filter(message));
    return subject.toString();
  }

  private String filter(String text) {
    if (text == null)
      return "";
    return text.replaceAll("<?>?", "");
  }
}
