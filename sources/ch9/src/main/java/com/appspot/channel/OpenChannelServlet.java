package com.appspot.channel;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenChannelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        // PLEASE NOTE: this value should be different
        //              for EACH user!!!
        String token = channelService.createChannel("1");

        StringTemplateGroup group = new StringTemplateGroup("xhtml",
            "WEB-INF/templates/channel");
        StringTemplate st = group.getInstanceOf("index");
        st.setAttribute("token", token);
        response.getWriter().write(st.toString());

    }
}
