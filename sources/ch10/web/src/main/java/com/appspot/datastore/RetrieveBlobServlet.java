package com.appspot.datastore;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RetrieveBlobServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    String id = request.getRequestURI()
        .replaceAll("/retrieve-blob/", "");

    BlobstoreService blobstoreService =
        BlobstoreServiceFactory.getBlobstoreService();

    blobstoreService.serve(new BlobKey(id), response);
  }
}
