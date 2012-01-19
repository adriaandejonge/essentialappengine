package com.appspot.capabilities;

import com.google.appengine.api.capabilities.CapabilitiesService;
import com.google.appengine.api.capabilities.
    CapabilitiesServiceFactory;
import com.google.appengine.api.capabilities.Capability;
import com.google.appengine.api.capabilities.CapabilityState;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CapabilitiesServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response)
      throws ServletException, IOException {
    long start = System.currentTimeMillis();

    CapabilitiesService service =
        CapabilitiesServiceFactory.getCapabilitiesService();

    StringTemplateGroup group = new StringTemplateGroup("xhtml",
        "WEB-INF/templates/xhtml");
    StringTemplate template = group.getInstanceOf("capabilities");
    Map<String, CapabilityState> statusMap =
        new HashMap<String, CapabilityState>();
    statusMap.put("blobstore",
        service.getStatus(Capability.BLOBSTORE));
    statusMap.put("datastore",
        service.getStatus(Capability.DATASTORE));
    statusMap.put("datastorewrite",
        service.getStatus(Capability.DATASTORE_WRITE));
    statusMap.put("images",
        service.getStatus(Capability.IMAGES));
    statusMap.put("mail",
        service.getStatus(Capability.MAIL));
    statusMap.put("memcache",
        service.getStatus(Capability.MEMCACHE));
    statusMap.put("taskqueue",
        service.getStatus(Capability.TASKQUEUE));
    statusMap.put("urlfetch",
        service.getStatus(Capability.URL_FETCH));
    statusMap.put("xmpp",
        service.getStatus(Capability.XMPP));
    template.setAttributes(statusMap);

    Collection<CapabilityState> states = statusMap.values();
    StringBuilder result = new StringBuilder();
    for(CapabilityState state : states) {
      if("ENABLED".equals(state.toString())) {
        result.append(result.length() > 0 ? " " : "");
        result.append(state.getCapability().getName());
      }
    }
    template.setAttribute("gaestatus",
        result.length() <= 0 ? "OK" : "DOWN: " + result);

    template.setAttribute("loadtime",
            "" + (System.currentTimeMillis() - start));

    response.getWriter().write(template.toString());

  }
}
