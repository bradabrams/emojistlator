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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.QueryResultIterator;
import java.util.ArrayList;
import java.util.List;


import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import java.util.Random ; 
import java.io.PrintWriter;



public class DeleteAllEntriesServlet extends HttpServlet {


    private static final Logger log = Logger.getLogger(DeleteAllEntriesServlet.class.getName());


  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
        DatastoreService datastoretodelete = 
                    DatastoreServiceFactory.getDatastoreService();
    Query mydeleteq = new Query();
    PreparedQuery pq = datastoretodelete.prepare(mydeleteq);
   
    
      //slow way:
      for (Entity result : pq.asIterable()) {
           try {
              datastoretodelete.delete(result.getKey());
          } catch (Exception e) {}  
    
   }
   

   // fast way:
   /*
   List<Key> keys = new ArrayList<Key>();
   QueryResultIterator<Entity> iterator = pq.asQueryResultIterator();
   while (iterator.hasNext()) {
     keys.add(iterator.next().getKey());
   }
   try {
      datastoretodelete.delete(keys);
   } catch (Exception e) {}
*/

    //output 
    PrintWriter out = resp.getWriter();
    out.println("<html>");
    out.println("<body>");
    out.println("<h1>Successfully Deleted All Entries</h1>");
    out.println("</body>");
    out.println("</html>"); 
 
  }

  

  

}
