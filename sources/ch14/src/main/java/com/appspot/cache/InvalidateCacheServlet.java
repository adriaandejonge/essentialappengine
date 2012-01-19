package com.appspot.cache;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InvalidateCacheServlet extends HttpServlet {

  private static final long serialVersionUID =
      -3977589077951744418L;

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

    MemcacheService cache = MemcacheServiceFactory
        .getMemcacheService();

    cache.delete("/simple-cache.article");    

    response.getWriter().write("Cache invalidated - /simple-cache");
  }
}