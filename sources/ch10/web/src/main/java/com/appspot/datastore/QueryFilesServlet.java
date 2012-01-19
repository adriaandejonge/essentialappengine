package com.appspot.datastore;

import com.google.appengine.api.datastore.*;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class QueryFilesServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    DatastoreService datastoreService = DatastoreServiceFactory
        .getDatastoreService();

    Query query = new Query("__BlobInfo__");
    PreparedQuery preparedQuery = datastoreService.prepare(query);

    List list = preparedQuery.asList(
        FetchOptions.Builder.withLimit(10));

    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate html = group.getInstanceOf("query-files");
    html.setAttribute("list", list);
    response.getWriter().write(html.toString());
  }
}
