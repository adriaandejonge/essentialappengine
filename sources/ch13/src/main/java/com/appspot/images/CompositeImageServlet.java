package com.appspot.images;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Composite;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Composite.Anchor;

public class CompositeImageServlet extends HttpServlet {

  private static final long serialVersionUID = 
      9107100571002086737L;

  protected void doGet(HttpServletRequest req,
          HttpServletResponse resp) throws ServletException,
          IOException {
    InputStream backgroudInputStream = 
        this.getClass().getResourceAsStream("background.png");
    byte[] backgroundBytes = 
        inputStreamToBytes(backgroudInputStream);
    Image backgroundImage = 
        ImagesServiceFactory.makeImage(backgroundBytes);    
    Composite backgroundComposite = 
        ImagesServiceFactory.makeComposite(backgroundImage, 0, 0, 
                1.0f, Anchor.TOP_LEFT);
    
    InputStream overlayInputStream = 
      this.getClass().getResourceAsStream("overlay.png");    
    byte[] overlayBytes = inputStreamToBytes(overlayInputStream); 
    ImagesService imageService = 
      ImagesServiceFactory.getImagesService();
    Image overlayImage = 
      ImagesServiceFactory.makeImage(overlayBytes);
    Composite overlayComposite = 
      ImagesServiceFactory.makeComposite(
              overlayImage, 0, 120, 0.5f, Anchor.TOP_LEFT);
    
    List<Composite> list = new ArrayList<Composite>();
    list.add(backgroundComposite);
    list.add(overlayComposite);
    
    Image result = imageService.composite(list, 1500, 1500, 
            Long.parseLong("FF00FF00", 16));
    resp.setContentType("image/png");
    
    OutputStream outputStream =  resp.getOutputStream();
    
   outputStream.write(result.getImageData());
    outputStream.close();

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
