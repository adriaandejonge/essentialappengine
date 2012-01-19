package com.appspot.cache;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ETagCacheServlet extends HttpServlet {
  private static final long serialVersionUID = 4308584640538822293L;

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

        MemcacheService cache = MemcacheServiceFactory
        .getMemcacheService();

    String cacheKey = request.getRequestURI() + "." + "etag";
    String result;

    if (!cache.contains(cacheKey) ||
        !cache.get(cacheKey).equals(request
            .getHeader("If-None-Match"))) {

      String etag = Long.toString(System.currentTimeMillis());
      response.setHeader("ETag", etag);
      cache.put(cacheKey, etag, Expiration.byDeltaSeconds(120));

      result = "Loaded into cache at " + (new Date());
      response.getWriter().write(result);

    } else {

      response.setStatus(304);
    }
  }
}