package com.appspot.tasks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayloadServlet extends HttpServlet {

  private static final long serialVersionUID = 
    -7897709050363917514L;

  protected void doGet(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {

   InputStream inputStream = request.getInputStream();
   ByteArrayOutputStream byteArrayStream = 
     new ByteArrayOutputStream();
   int length;
   byte[] buffer = new byte[1024];
   
   while((length = inputStream.read(buffer)) >= 0) {
     byteArrayStream.write(buffer, 0, length);
   }
   
   byte[] result = byteArrayStream.toByteArray();

    // do something with result

  }
}
