package com.appspot.urlfetch;

import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

import static com.google.appengine.api.urlfetch.FetchOptions
                                            .Builder.withDeadline;
import static com.google.appengine.api.urlfetch.HTTPMethod.GET;

public class FetchOptionsServlet extends HttpServlet {

  private static final long serialVersionUID = -793535469564534397L;

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    URLFetchService urlfetch = URLFetchServiceFactory
        .getURLFetchService();

    URL url = new URL(
        "http://www.nu.nl/feeds/rss/algemeen.rss");
    HTTPRequest httpRequest = new HTTPRequest(url, GET,
        withDeadline(0.2).doNotFollowRedirects().allowTruncate());

    // or doNotFollowRedirects().setDeadline(0.2) - mind the change
    // from 'with' to 'set'

    HTTPResponse httpResponse = urlfetch.fetch(httpRequest);
    response.getOutputStream().write(
        httpResponse.getContent());
  }
}