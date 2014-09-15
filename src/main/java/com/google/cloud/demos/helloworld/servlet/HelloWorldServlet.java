package com.google.cloud.demos.helloworld.servlet;

import java.io.IOException;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class HelloWorldServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
              throws IOException {

        resp.setContentType("text/plain");
      
        resp.getWriter().println("testing testing!!");
      
      
      
         
    }
}
