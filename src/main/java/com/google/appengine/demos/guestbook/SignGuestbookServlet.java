/**
 * Copyright 2012 Google Inc. All Rights Reserved. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.appengine.demos.guestbook;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class SignGuestbookServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());


  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    String guestbookName = req.getParameter("guestbookName");
    Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
    String content = req.getParameter("content");
    String imageUrls; 

    Date date = new Date();
    Entity greeting = new Entity("Greeting", guestbookKey);
    
    String[] imageUrls2 = getImageUrls (content);
    greeting.setProperty("user", user);
    greeting.setProperty("date", date);
    greeting.setProperty("content", content);

    log.info("some sample log");
    log.info(Integer.toString(imageUrls2.length));

    int i = 0;
    for (String s:  imageUrls2) {
      String num = Integer.toString(i);
      greeting.setProperty("imageUrls"+num,s); 
      i++;
    }



    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(greeting);



    resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);
  }

  public String[] getImageUrls (String content) {
    String temp = "";
    java.util.ArrayList l = new java.util.ArrayList ();
    content = content.replace ("?", " ?");
    content = content.replace ("!", " !");
    content = content.replace (".", " .");
    content = content.replace (",", " ,");
    content = content.replace ("\"", " \"");



    for (String word: content.split(" ")) {
      switch (word.toLowerCase()) {
        case "dog":
          l.add ("http://emojipedia.org/wp-content/uploads/2013/07/160x160x192-dog-face.png.pagespeed.ic.CtTA9k9apv.jpg");
          break;
        case "happy":
          l.add ("http://emojipedia.org/wp-content/uploads/2014/04/128x128x1f604-google-android.png.pagespeed.ic.twhEpshRwL.png");
          break;
        case "hot":
          l.add ("http://emojipedia.org/wp-content/uploads/2014/04/128x128x1f31e-google-android.png.pagespeed.ic.bvWcqm12QS.png");
          break;
        case "kiss":
          l.add ("http://emojipedia.org/wp-content/uploads/2014/04/128x128x1f48f-google-android.png.pagespeed.ic.vN74TXwtMP.png");
          break;
        case "question":
        case "questions":
        case "?":
          l.add ("http://emojipedia.org/wp-content/uploads/2013/08/160x160xwhite-question-mark-ornament.png.pagespeed.ic.fU4MuC4p7q.png");
          
        default:
          break;
      }
    }
    return (String[]) l.toArray(new String[0]);
  }

}
