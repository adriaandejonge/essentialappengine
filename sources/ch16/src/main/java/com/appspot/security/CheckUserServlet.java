package com.appspot.security;

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

public class CheckUserServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    StringTemplateGroup group = new StringTemplateGroup("xhtml",
            "WEB-INF/templates/xhtml");

    UserService userService = UserServiceFactory.getUserService();

    if(userService.isUserLoggedIn()) {
      User user = userService.getCurrentUser();
      String url = userService.createLogoutURL(
          request.getRequestURI());
      StringTemplate template = group.getInstanceOf("logged-in");
      template.setAttribute("user", user);
      template.setAttribute("url", url);
      response.getWriter().write(template.toString());

    } else {
      String url = userService.createLoginURL(
                request.getRequestURI());
      StringTemplate template = group.getInstanceOf("logged-out");
      template.setAttribute("url", url);
      response.getWriter().write(template.toString());
    }
  }
}
