package com.appspot.images;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.images.ImagesService.OutputEncoding;

public class ResizeImageServlet extends HttpServlet {

  private static final long serialVersionUID = 
      -8616065571872910090L;

  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException,
          IOException {

    InputStream backgroudInputStream =
            this.getClass().getResourceAsStream("background.png");
    byte[] backgroundBytes =
            inputStreamToBytes(backgroudInputStream);
    Image image = ImagesServiceFactory.makeImage(backgroundBytes);


    Transform transform = ImagesServiceFactory.makeResize(
                    100, 500);

    ImagesService imageService = ImagesServiceFactory
                    .getImagesService();
    image = imageService.applyTransform(transform, image,
            OutputEncoding.PNG);

    response.setContentType("image/png");

    OutputStream outputStream = response.getOutputStream();

    outputStream.write(image.getImageData());
    outputStream.close();
  }

  public byte[] inputStreamToBytes(InputStream inputStream)
          throws IOException {

    ByteArrayOutputStream byteArrayOutputStream = 
        new ByteArrayOutputStream(
            1024);
    byte[] buffer = new byte[1024];
    int length;

    while ((length = inputStream.read(buffer)) >= 0) {
      byteArrayOutputStream.write(buffer, 0, length);
    }

    inputStream.close();
    return byteArrayOutputStream.toByteArray();
  }
}
