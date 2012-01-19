package com.appspot.capabilities;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    long start = System.currentTimeMillis();
    Logger logger = Logger.getLogger(this.getClass().getName());
    logger.fine("fine");
    logger.info("info");
    logger.severe("severe");
    logger.warning("warning");
    try {
      // FAIL on purpose
      (new String[1])[2].toLowerCase();
    } catch(Exception e) {
      logger.log(Level.SEVERE, "Failed 'unexpectedly'", e);
    }



    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate template = group.getInstanceOf("hello-world");

    template.setAttribute("loadtime",
        "" + (System.currentTimeMillis() - start));
    template.setAttribute("gaestatus", "OK");
    response.getWriter().write(template.toString());

  }
}
