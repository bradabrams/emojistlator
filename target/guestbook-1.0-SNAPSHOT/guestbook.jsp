<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery" %>
<%@ page import="com.google.appengine.api.datastore.QueryResultIterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>
  <div>
    <div>
      <img src="/images/EmojiSlatorLogo.jpg" width="35%">
    </div>

    <% 
      String guestbookName = request.getParameter("guestbookName");
      if (guestbookName == null) {
          guestbookName = "default";
      }
      pageContext.setAttribute("guestbookName", guestbookName);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
      // Run an ancestor query to ensure we see the most up-to-date
      // view of the Greetings belonging to the selected Guestbook.
      Query query = new Query("Greeting", guestbookKey)
          .addSort("date", Query.SortDirection.DESCENDING);
      List<Entity> greetings = datastore.prepare(query)
          .asList(FetchOptions.Builder.withLimit(5));
      if (!greetings.isEmpty()) {
        Entity greeting = greetings.get(0);
        pageContext.setAttribute("greeting_content",
                greeting.getProperty("content"));
        for (int i = 0; i < 10; i++){
           String num = Integer.toString (i);
           pageContext.setAttribute("greeting_imageUrls"+num,
               greeting.getProperty("imageUrls"+num));
        }
      }
    %>
    
    <!-- TODO(athicha): Replace sign with translate -->
    <form action="/sign" method="post">
        <div>
          <input type="text" name="content" style="font-size:15px; height:2em; width:500px;"/>
          <input type="submit" value="Get Emoji!">
          <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
        </div>
    </form>

    <blockquote>${fn:escapeXml(greeting_content)} =
       <image src=${greeting_imageUrls0}></image> 
       <image src=${greeting_imageUrls1}></image> 
       <image src=${greeting_imageUrls2}></image> 
       <image src=${greeting_imageUrls3}></image> 
       <image src=${greeting_imageUrls4}></image> 
       <image src=${greeting_imageUrls5}></image> 
       <image src=${greeting_imageUrls6}></image> 
       <image src=${greeting_imageUrls7}></image> 
       <image src=${greeting_imageUrls8}></image> 
       <image src=${greeting_imageUrls9}></image> 
    </blockquote>

  </div>

</body>
</html>
