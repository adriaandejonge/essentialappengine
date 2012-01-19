package com.appspot.urlfetch;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class SimpleURLServlet extends HttpServlet {

  private static final long serialVersionUID = 5618163722864625730L;

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

    URL url  = new URL(
    "http://www.nu.nl/feeds/rss/algemeen.rss");

    InputStream inputStream = url.openStream();
    inputStreamToOutputStream(inputStream,
        response.getOutputStream());
  }

  public void inputStreamToOutputStream(InputStream inputStream,
                                          OutputStream outputStream)
          throws IOException {

    byte[] buffer = new byte[1024];
    int length;

    while ((length = inputStream.read(buffer)) >= 0) {
      outputStream.write(buffer, 0, length);
    }

    inputStream.close();
  }
}