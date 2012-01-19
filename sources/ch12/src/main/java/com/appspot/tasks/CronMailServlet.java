package com.appspot.tasks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
import com.google.appengine.api.mail.MailService.Message;

public class CronMailServlet extends HttpServlet {

  private static final long serialVersionUID = -7897709050363917514L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    long startTime = System.currentTimeMillis();

    MailService mailService = MailServiceFactory.getMailService();
    Message message = new Message("No Reply <info@blo-gae.appspotmail.com>",
        "Adrian the Young <adriaandejonge+1@gmail.com>", "CRON Job",
        "Check it!!!\n\n");
    mailService.send(message);

    long diff = System.currentTimeMillis() - startTime;
    response.getWriter().write("time: " + diff); 
    

  }
}
