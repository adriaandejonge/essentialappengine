package com.appspot.datastore;

import com.google.appengine.api.datastore.*;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RetrieveDataServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    String id = request.getRequestURI()
        .replaceAll("/retrieve-data/", "");
    DatastoreService datastoreService =
        DatastoreServiceFactory.getDatastoreService();

    Entity blogPost = null;
    try {
      blogPost = datastoreService.get(
          KeyFactory.createKey("BlogPost", id));
    } catch (EntityNotFoundException e) {
      // This should not happen: let the 500 page handle it
      throw new ServletException(e);
    }


    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate html = group.getInstanceOf("retrieve-blog-post");
    html.setAttributes(blogPost.getProperties());

    response.getWriter().write(html.toString());
  }
}
