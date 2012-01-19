package com.appspot.urlfetch;

import com.google.appengine.api.urlfetch.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import static com.google.appengine.api.urlfetch.HTTPMethod.*;

public class SyncUrlPostServlet extends HttpServlet {

  private static final long serialVersionUID = -793535469564534397L;

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    URLFetchService urlfetch = URLFetchServiceFactory
        .getURLFetchService();

    URL url  = new URL(
    "http://tinyurl.com/create.php");



    HTTPRequest httpRequest = new HTTPRequest(url, POST);
    httpRequest.setPayload(("url=" +
        URLEncoder.encode("http://nu.nl", "UTF-8")).getBytes());


    HTTPResponse httpResponse = urlfetch.fetch(httpRequest);
    response.getOutputStream().write(
        httpResponse.getContent());
  }
}