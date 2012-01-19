package com.appspot.template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StringTemplateServletC extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

     long startTime = System.currentTimeMillis();

     response.getWriter().write("Hello World without ST! ");

     long diff = System.currentTimeMillis() - startTime;
     response.getWriter().write("time: " + diff);

  }
}
