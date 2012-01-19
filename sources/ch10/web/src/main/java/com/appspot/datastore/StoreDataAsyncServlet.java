package com.appspot.datastore;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StoreDataAsyncServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate html = group.getInstanceOf("store-blog-post");
    response.getWriter().write(html.toString());
  }

  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
      throws ServletException, IOException {

    AsyncDatastoreService datastoreService =
        DatastoreServiceFactory.getAsyncDatastoreService();

    String title = request.getParameter("title");
    Entity blogPost = new Entity("BlogPost", normalize(title));
    blogPost.setProperty("title", title);

    String author = request.getParameter("author");
    blogPost.setProperty("author", author);
    String content = request.getParameter("content");
    blogPost.setProperty("content", content);
    blogPost.setProperty("date", new Date());

    // if any: (be careful with Tasks Queue)
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    blogPost.setProperty("user", user);

    Future<Key> key = datastoreService.put(blogPost);

    // do something else, for example process the result template
    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate html = group.getInstanceOf("done-blog-post");
    html.setAttribute("title", title);
    html.setAttribute("author", author);
    html.setAttribute("content", content);

    try {
      // block until the result is available
      key.get();
      // and do nothing with the resulting key
    } catch (InterruptedException e) {
      throw new ServletException(e);
    } catch (ExecutionException e) {
      throw new ServletException(e);
    }

    response.getWriter().write(html.toString());
  }

  private String normalize(String str) {
    String trimmedLower = str.toLowerCase().trim();
    return trimmedLower.replaceAll("\\W+", "-");
  }
}
