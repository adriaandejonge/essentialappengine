package com.appspot.images;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class StoreImageServlet extends HttpServlet {

  private static final long serialVersionUID = 
      -8433333970866700129L;

  protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
    StringTemplateGroup group = new StringTemplateGroup("xhtml",
            "WEB-INF/templates/xhtml");
    StringTemplate st = group.getInstanceOf("upload-file");
    response.getWriter().write(st.toString());
  }

  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException,
          IOException {

    if (ServletFileUpload.isMultipartContent(request)) {
      Map<String, String> formValues = 
          new HashMap<String, String>();
      byte[] imageBytes = null;
      try {
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iterator = upload.getItemIterator(request);
        while (iterator.hasNext()) {
          FileItemStream item = iterator.next();

          if (item.isFormField()) {
            formValues.put(item.getFieldName(), Streams.asString(
                    item.openStream()));
          } else {
            InputStream inputStream = item.openStream();
            imageBytes = inputStreamToBytes(inputStream);
          }
        }
      } catch (FileUploadException e) {
        throw new ServletException(e);
      }

      DatastoreService datastoreService = DatastoreServiceFactory
              .getDatastoreService();
      Entity imageEntity = new Entity("ImageEntity");
      if (imageBytes != null) {
        imageEntity.setProperty("data", new Blob(imageBytes));
      }
      imageEntity.setProperty("caption", formValues.get("caption"));
      datastoreService.put(imageEntity);
    }
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
