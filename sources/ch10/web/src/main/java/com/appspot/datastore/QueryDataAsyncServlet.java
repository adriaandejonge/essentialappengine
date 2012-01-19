package com.appspot.datastore;

import com.google.appengine.api.datastore.*;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class QueryDataAsyncServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    AsyncDatastoreService datastoreService = DatastoreServiceFactory
        .getAsyncDatastoreService();

    Query query = new Query("BlogPost");
    query.addFilter("date", Query.FilterOperator.LESS_THAN,
        new Date(System.currentTimeMillis() - 5000));
    query.addSort("date", Query.SortDirection.DESCENDING);
    PreparedQuery preparedQuery = datastoreService.prepare(query);

    Iterator iterator = preparedQuery.asIterator();

    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate html = group.getInstanceOf("query-blog-post");
    html.setAttribute("items", iterator);
    response.getWriter().write(html.toString());
  }
}
