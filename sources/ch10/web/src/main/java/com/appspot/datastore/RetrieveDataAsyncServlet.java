package com.appspot.datastore;

import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RetrieveDataAsyncServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    String id = request.getRequestURI()
        .replaceAll("/retrieve-data/", "");
    AsyncDatastoreService datastoreService =
        DatastoreServiceFactory.getAsyncDatastoreService();

    // start asynchronous call
    Future<Entity> blogPostFuture =
        datastoreService.get(KeyFactory.createKey("BlogPost", id));

    // do something else

    Entity blogPost = null;
    try {
      // and block until result is available
      blogPost = blogPostFuture.get();
    } catch (InterruptedException e) {
      throw new ServletException(e);
    } catch (ExecutionException e) {
      throw new ServletException(e);
    }


    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate html = group.getInstanceOf("retrieve-blog-post");
    html.setAttributes(blogPost.getProperties());

    response.getWriter().write(html.toString());
  }
}
