package com.appspot.datastore;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class StoreBlobServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    BlobstoreService blobstoreService =
        BlobstoreServiceFactory.getBlobstoreService();
    String url = blobstoreService.createUploadUrl("/store-blob");

    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate template = group.getInstanceOf("upload-file");
    template.setAttribute("url", url);
    response.getWriter().write(template.toString());
  }

  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
      throws ServletException, IOException {

    BlobstoreService blobstoreService =
        BlobstoreServiceFactory.getBlobstoreService();

    Map<String, BlobKey> uploadedFiles =
        blobstoreService.getUploadedBlobs(request);
    BlobKey blobKey = uploadedFiles.get("myfile");

    // do something with blobKey - in this example: IGNORE!

    response.sendRedirect("/query-files");
  }
}
