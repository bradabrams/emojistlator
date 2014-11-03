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

package com.google.appengine.demos.emojislator;

import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.io.StringWriter;
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
import java.io.PrintWriter;
import java.security.InvalidParameterException;

public class TranslateServlet extends HttpServlet {

  private static final Logger log = Logger.getLogger(TranslateServlet.class.getName());



  //Handler for the /translate path
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    log.info("start processing");
   
    String feed;
   
   /* Bug# 2 (Trace)       */   
   
   
    if (randInt (0,100) <= 5) {
       try {
          log.info("In Experiment for getting related twitter feed");
          feed = getTwitterFeed();
       } catch(Exception e) { } 
    }
 
   

   

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    String guestbookName = req.getParameter("guestbookName");
    log.info("storing key " + guestbookName);
    Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);



    //Get the content to translate 
    String content = req.getParameter("content");
    Date date = new Date();
    

    Entity greeting = new Entity("Greeting", guestbookKey);
    
    String[] imageUrls2 = null;
    try {
      //Get list of Images to display
      imageUrls2 = getImageUrls(content);
      log.info("number of translations:" + imageUrls2.length);


    
    } catch (Exception e) {
      StringWriter errors = new StringWriter();
      e.printStackTrace(new PrintWriter(errors));
      log.severe("Something bad happened!" + errors.toString());
    }


    greeting.setProperty("user", user);
    greeting.setProperty("date", date);
    greeting.setProperty("content", content);

   

    int i = 0;
    for (String s: imageUrls2) {
      String num = Integer.toString(i);
      greeting.setProperty("imageUrls"+num, s); 
      i++;
    }

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(greeting);

    resp.sendRedirect("/");  
    log.info("finish processing");
  }


  public String[] getImageUrls (String content) throws Exception {
    String temp = "";
    java.util.ArrayList l = new java.util.ArrayList ();
   

    content = canonicalize (content);

    for (String word: content.split(" ")) {
      switch (word) {
        case "tonight":
           l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/night_moon.jpg");
           break;
        case "the":
        case "in":
        case "a":
        case "to":
           break; // we don't need translations for these
        case "dog":
        case "dogs":
        case "doggie":
          l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/dog.jpg");
          break;
        case "happy":
          l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/happy.png");
          break;
        case "hot":
        case "sun":
        case "sunny":
          l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/sun.jpg");
          break;
        case "kiss":
        case "kissed":
        case "kisses":
          l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/kiss.jpg");
          break;
        case "drinks":
        case "drink":
        case "drank":
          l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/cocktail.jpg");
          break;
        case "city":
          l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/OfficeBuilding.png");
          break;
        case "night":
           l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/night_moon.jpg");
           break;
        case "wowed":
        case "wow":
           l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/wowed.png");
           break;
        case "worry":
        case "worried":
           l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/worried.png");
           break;
        case "sad":
           l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/sad.png");
           break;
        case "mad":
           l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/mad.png");
           break;
        case "surprise":
        case "surprised":
           l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/surprised.png");
           break;        
        case "grandma":
        case "grandmother":
           l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/grandma.jpg");
           break;        
        case "cupcake":
        case "birthday":
        case "cake":
           l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/cupcake.jpg");
           break;        

        case "question":
        case "questions":
        case "questioning":
        case "?":
          l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/QuestionMark.jpg");
          break;
        case "die":
           // Bug #1: Exception in Logs 
           // throw new Exception ("ha!  die, get it?");       
        default:
           log.info ("didn't find image for word");

      
      }
    }
    while (l.size() < 10) {
      l.add ("http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/blank.png");
    }
    return (String[]) l.toArray(new String[0]);
  }
  

  public static String canonicalize (String value) {

     value = value.toLowerCase();
     value = value.replace (",", " ,");


    /*
    BUG #3: Debuger uncomment this code to fix the bug  
    
      
    value = value.replace ("?", " ?");
    value = value.replace ("!", " !");
    value = value.replace (".", " .");
    value = value.replace ("\"", " \"");
  */

    return value;

  }


  public static String getTwitterFeed () {
     String value = "not set";
     try {

       
      URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
      HTTPResponse response = urlFetch.fetch(new URL("http://twitter.com/brada"));

      String lines[] = new String(response.getContent()).split("\\r?\\n");
      for (String line : lines) {
         log.info ("from twitter" + line);
      }

      
      } catch (MalformedURLException e) {
        // ...
      } catch (IOException e) {
       // ...
    }
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
