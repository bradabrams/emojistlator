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
<%@ page import="java.util.Date" %>
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
      //Object requestId = pageContext.getAttribute("guestbookName");
      String requestId = "athicha";
      pageContext.setAttribute("guestbookName", requestId);
      for (int i = 0; i < 10; i++){
             String num = Integer.toString (i);
             pageContext.setAttribute("greeting_imageUrls"+num,
                 "http://storage.googleapis.com/debuggerdemo.appspot.com/emoji/blank.png");
      }

      if (requestId != null) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key guestbookKey = KeyFactory.createKey("Guestbook", requestId);
        
        Query query = new Query("Greeting", guestbookKey)
            .addSort("date", Query.SortDirection.DESCENDING);
        List<Entity> greetings = datastore.prepare(query)
            .asList(FetchOptions.Builder.withLimit(5));
        if (!greetings.isEmpty()) {
          Entity greeting = greetings.get(0);
          pageContext.setAttribute("input_text",
                  greeting.getProperty("content"));
          for (int i = 0; i < 10; i++){
             String num = Integer.toString (i);
             pageContext.setAttribute("greeting_imageUrls"+num,
                 greeting.getProperty("imageUrls"+num));
          }
        }
      }
      //Date date = new Date();
      //String newRequestId = Long.toString(date.getTime());
      //pageContext.setAttribute("guestbookName", newRequestId);
    %>
    
    <form action="/translate" method="post">
        <div>
          <input type="text" name="content" style="font-size:15px; height:2em; width:500px;"/>
          <input type="submit" value="Translate">
          <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
        </div>
    </form>

    <blockquote>${fn:escapeXml(input_text)} =
       <image src=${greeting_imageUrls0} height="200"></image> 
       <image src=${greeting_imageUrls1} height="200"></image> 
       <image src=${greeting_imageUrls2} height="200"></image> 
       <image src=${greeting_imageUrls3} height="200"></image> 
       <image src=${greeting_imageUrls4} height="200"></image> 
       <image src=${greeting_imageUrls5} height="200"></image> 
       <image src=${greeting_imageUrls6} height="200"></image> 
       <image src=${greeting_imageUrls7} height="200"></image> 
       <image src=${greeting_imageUrls8} height="200"></image> 
       <image src=${greeting_imageUrls9} height="200"></image> 
    </blockquote>

  </div>
  <code>limited vocabulary: sad, mad, wow, surprise, worried, dog, happy, hot, kiss, drink, city, night, question</code>

</body>
</html>
