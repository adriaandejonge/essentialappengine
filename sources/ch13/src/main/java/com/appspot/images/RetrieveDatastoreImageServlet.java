package com.appspot.images;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

public class RetrieveDatastoreImageServlet extends HttpServlet {

  private static final long serialVersionUID = 
      -6510323320594401293L;

  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException,
          IOException {
    String keyStr = request.getParameter("key");
    if(keyStr == null) return;
    long key = Long.parseLong(keyStr);
    
    response.setContentType("image/png");
    DatastoreService datastoreService = DatastoreServiceFactory
    .getDatastoreService();
    try {
      Entity entity = datastoreService.get(
         KeyFactory.createKey("ImageEntity", key));
      Blob data = (Blob) entity.getProperty("data");
         response.getOutputStream().write(data.getBytes());
    } catch (EntityNotFoundException e) {
         throw new ServletException(e);
    }
  }
}
