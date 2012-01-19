package com.appspot.mail;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Low-level alternative to ReceiveJMMailServlet.
 */
public class ReceiveLLMailServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request,
          HttpServletResponse response)
      throws ServletException, IOException {

    String rawMail = readMailFromRequest(request);
    DatastoreService datastoreService = DatastoreServiceFactory
          .getDatastoreService();

      Entity mail = new Entity("ReceivedLLMail");

      mail.setProperty("rawmail", rawMail);
      mail.setProperty("date", new Date());

      datastoreService.put(mail);
  }

  private String readMailFromRequest(HttpServletRequest request)
      throws IOException {
    BufferedReader reader = request.getReader();
    StringBuilder stringBuilder = new StringBuilder(1000);
    char[] buffer = new char[1024];
    int length = 0;
    while ((length = reader.read(buffer)) > 0) {
      String readData = String.valueOf(buffer, 0, length);
      stringBuilder.append(readData);
    }
    reader.close();
    String result = stringBuilder.toString();
    return result;
  }
}
