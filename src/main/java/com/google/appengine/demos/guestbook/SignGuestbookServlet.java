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
import java.util.Random ; 
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SignGuestbookServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());


  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

     log.info("start processing");
   
    String feed;
    if (randInt (0,100) <= 5) {
       try {
          log.info("In Experiment for getting realated twitter feed");
          feed = getTwitterFeed();
       } catch(Exception e) { } 
    }

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

  public static String getTwitterFeed () {
     String value = "not set";
     try {
       URL url = new URL("https://twitter.com/brada");
       BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
       String line;

       while ((line = reader.readLine()) != null) {
        value = line;
        log.info ("from twitter" + line);
       }
       reader.close();

      } catch (MalformedURLException e) {
        // ...
      } catch (IOException e) {
       // ...
    }
    return value;
  }

  public String[] getImageUrls (String content) {
    String temp = "";
    java.util.ArrayList l = new java.util.ArrayList ();
   

    content = canonicalize (content);

    for (String word: content.split(" ")) {
      switch (word) {
        case "the":
        case "in":
        case "a":
        case "to":
           break; // we don't need translations for these
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
        case "drinks":
          l.add ("http://pix.iemoji.com/sbemojix2/0315.png");
          break;
        case "city":
          l.add ("http://pix.iemoji.com/sbemojix2/0390.png");
          break;
        case "tonight":
        case "night":
           l.add ("http://emojipedia.org/wp-content/uploads/2014/04/128x128x1f303-google-android.png.pagespeed.ic.L7YwLMeyQP.png");
           break;
        case "question":
        case "questions":
        case "?":
          l.add ("http://emojipedia.org/wp-content/uploads/2013/08/160x160xwhite-question-mark-ornament.png.pagespeed.ic.fU4MuC4p7q.png");
          
        default:
          log.info("did not find a match for: " + word);
          break;
      }
    }
    return (String[]) l.toArray(new String[0]);
  }

  public static String canonicalize (String value) {

     value = value.toLowerCase();

       /* value = value.replace ("?", " ?");
    value = value.replace ("!", " !");
    value = value.replace (".", " .");
    value = value.replace (",", " ,");
    value = value.replace ("\"", " \"");
    */
    return value;

  }

  /**
 * Returns a pseudo-random number between min and max, inclusive.
 * The difference between min and max can be at most
 * <code>Integer.MAX_VALUE - 1</code>.
 *
 * @param min Minimum value
 * @param max Maximum value.  Must be greater than min.
 * @return Integer between min and max, inclusive.
 * @see java.util.Random#nextInt(int)
 */
public static int randInt(int min, int max) {

    // NOTE: Usually this should be a field rather than a method
    // variable so that it is not re-seeded every call.
    Random rand = new Random();

    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
}

}
