package com.appspot.security;

import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
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

public class CheckOAuthServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {

    StringTemplateGroup group = new StringTemplateGroup("xhtml",
            "WEB-INF/templates/xhtml");

    OAuthService oauthService = OAuthServiceFactory
        .getOAuthService();

    try {
      User user = oauthService.getCurrentUser();
      StringTemplate template = group.getInstanceOf("oauth");
      template.setAttribute("user", user);
      response.getWriter().write(template.toString());

    } catch (OAuthRequestException e) {
      response.setStatus(401);
    }
  }
}
