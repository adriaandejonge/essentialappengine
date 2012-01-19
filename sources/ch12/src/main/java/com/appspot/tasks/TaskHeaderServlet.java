package com.appspot.tasks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskHeaderServlet extends HttpServlet {

  private static final long serialVersionUID =
    -1137258632691070463L;

  /**
   * Called by default when scheduled by CRON
   */
  protected void doGet(HttpServletRequest request,
          HttpServletResponse response) throws ServletException,
          IOException {
    
  
    String userAgent = request.getHeader("User-Agent");
    String host = request.getHeader("Host");
    
    boolean isCronTask = "true".equals(
            request.getHeader("X-AppEngine-Cron"));
    String queueName = request.getHeader("X-AppEngine-QueueName");    
    assert !isCronTask || "__cron".equals(queueName);
    
    String taskName = request.getHeader("X-AppEngine-TaskName");
    String taskRetryStr = 
      request.getHeader("X-AppEngine-TaskRetryCount"); 
    int taskRetryCount = taskRetryStr == null ? -1 :
      Integer.parseInt(taskRetryStr);
     
    if(taskRetryCount > 25) return; // and give up
    
    // Perform task knowing all above fields
    
  }
  
  /**
   * Called by default when scheduled by Task Queue API
   */
  protected void doPost(HttpServletRequest request,
          HttpServletResponse response) throws ServletException,
          IOException {
    
    String contentType = request.getHeader("Content-Type");
    String userAgent = request.getHeader("User-Agent");
    String referer = request.getHeader("Referer");
    String host = request.getHeader("Host");
    String contentLengthStr = request.getHeader("Content-Length");
    int contentLength = contentLengthStr == null ? -1 :
      Integer.parseInt(contentLengthStr);
    
    String queueName = request.getHeader("X-AppEngine-QueueName");    
    String taskName = request.getHeader("X-AppEngine-TaskName");
    String taskRetryStr = 
      request.getHeader("X-AppEngine-TaskRetryCount"); 
    int taskRetryCount = taskRetryStr == null ? -1 :
      Integer.parseInt(taskRetryStr);
    
    if(taskRetryCount > 25) return; // and give up
    
    // Perform task knowing all above fields
  }

}
