package com.appspot.urlfetch;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class ASyncUrlFetchServlet extends HttpServlet {

  private static final long serialVersionUID = -793535469564534397L;

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    URLFetchService urlfetch = URLFetchServiceFactory
        .getURLFetchService();

    URL url  = new URL(
    "http://www.nu.nl/feeds/rss/algemeen.rss");

    Future<HTTPResponse> future = urlfetch.fetchAsync(url);

    while(!future.isDone()) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        throw new ServletException(e);
      }
    }
    HTTPResponse httpResponse;
    try {
      
      httpResponse = future.get();
      response.getOutputStream().write(
        httpResponse.getContent());

    } catch (InterruptedException e) {
      throw new ServletException(e);
    } catch (ExecutionException e) {
      throw new ServletException(e);
    }


  }
}