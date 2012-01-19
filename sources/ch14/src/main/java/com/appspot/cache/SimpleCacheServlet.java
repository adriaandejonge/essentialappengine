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

public class SimpleCacheServlet extends HttpServlet {

  private static final long serialVersionUID = 1211642763887796727L;

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

    MemcacheService cache = MemcacheServiceFactory
        .getMemcacheService();

    String cacheKey = request.getRequestURI() + "." + "article";
    String result;

    if (!cache.contains(cacheKey)) {

      result = "Loaded into cache at " + (new Date());
      cache.put(cacheKey, result, Expiration.byDeltaSeconds(120));
    } else {

      result = "FROM CACHE: " + cache.get(cacheKey);
    }
    
    response.getWriter().write(result);
  }
}
