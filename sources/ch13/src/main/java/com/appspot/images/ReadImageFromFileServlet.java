package com.appspot.images;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

public class ReadImageFromFileServlet extends HttpServlet {
  
  private static final long serialVersionUID = 
      7296574226624516172L;

  protected void doGet(HttpServletRequest req,
          HttpServletResponse resp) throws ServletException,
          IOException {
    InputStream backgroudInputStream =
         this.getClass().getResourceAsStream("background.png");
    byte[] backgroundBytes =
            inputStreamToBytes(backgroudInputStream);
    Image backgroundImage =
            ImagesServiceFactory.makeImage(backgroundBytes);

    // do something with image

  }

  public byte[] inputStreamToBytes(InputStream inputStream)
          throws IOException {

    ByteArrayOutputStream byteArrayOutputStream = 
        new ByteArrayOutputStream(1024);
    byte[] buffer = new byte[1024];
    int length;

    while ((length = inputStream.read(buffer)) >= 0) {
      byteArrayOutputStream.write(buffer, 0, length);
    }

    inputStream.close();
    return byteArrayOutputStream.toByteArray();
  }
}
