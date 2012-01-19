package com.appspot.urlfetch;

import com.google.appengine.api.urlfetch.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.appengine.api.urlfetch.FetchOptions.Builder
    .withDeadline;
import static com.google.appengine.api.urlfetch.HTTPMethod.GET;
import static javax.servlet.http.HttpServletResponse
    .SC_BAD_GATEWAY;
import static javax.servlet.http.HttpServletResponse
    .SC_INTERNAL_SERVER_ERROR;

public class GracefulExceptionServlet extends HttpServlet {
  private static Logger LOG =
      Logger.getLogger("GracefulExceptionServlet");
  private static final long serialVersionUID = -620624461988405858L;

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    String result = null;
    try {
      URLFetchService urlfetch = URLFetchServiceFactory
          .getURLFetchService();

      URL url = new URL(
          "http://www.nu.nl/feeds/rss/algemeen.rss");
      HTTPRequest httpRequest = new HTTPRequest(url, GET,
          withDeadline(0.2).followRedirects().allowTruncate());

      HTTPResponse httpResponse = urlfetch.fetch(httpRequest);
      result = new String(httpResponse.getContent());

    } catch (MalformedURLException e) {
      /// severe: This is a programming error, not an incident
      LOG.log(Level.SEVERE, "URL cannot be parsed", e);
      result = "<error>Internal error in the application</error>";
      response.setStatus(SC_INTERNAL_SERVER_ERROR);

    } catch (ResponseTooLargeException e) {
      // severe: allowTruncate should prevent this
      LOG.log(Level.SEVERE, "Response larger than 1MB", e);
      result = "<error>Internal error in the application</error>";
      response.setStatus(SC_INTERNAL_SERVER_ERROR);

    } catch (IOException e) {
      // minor: could happen all the time!
      LOG.log(Level.WARNING, "Feed unavailable", e);
      result = "<error>External feed unavailable</error>";
      response.setStatus(SC_BAD_GATEWAY);

    } finally {

      response.getWriter().write(result);
    }
  }
}