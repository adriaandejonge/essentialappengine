package com.appspot.template;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class StringTemplateServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {
    long startTime = System.currentTimeMillis();

    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate st = group.getInstanceOf("hello-world");
    st.setAttribute("name", "World");
    response.getWriter().write(st.toString());

    long diff = System.currentTimeMillis() - startTime;
    response.getWriter().write("time: " + diff);

  }
}
