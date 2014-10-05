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
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>

<%
    String guestbookName = request.getParameter("guestbookName");
    if (guestbookName == null) {
        guestbookName = "default";
    }
    pageContext.setAttribute("guestbookName", guestbookName);

    
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
%>
<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
} else {
%>
<p>Hello!
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
    to include your name with your emoji.</p>
<%
    }
%>

<%
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
    Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);
    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
    int imageCount = -1;
    if (greetings.isEmpty()) {
%>
<p> There are no translations yet.. be the first!</p>
<%
} else {
%>
<p>Past Emoji Translations.</p>
<%
    for (Entity greeting : greetings) {
        pageContext.setAttribute("greeting_content",
                greeting.getProperty("content"));
 
        for (int i = 0; i < 10; i++){
           String num = Integer.toString (i);
           pageContext.setAttribute("greeting_imageUrls"+num,
               greeting.getProperty("imageUrls"+num));
        }

        if (greeting.getProperty("user") == null) {
%>
<p>An anonymous person ask for:</p>
<%
} else {
    pageContext.setAttribute("greeting_user",
            greeting.getProperty("user"));
%>
<p><b>${fn:escapeXml(greeting_user.nickname)}</b> wrote:</p>
<%
    }
%>

<blockquote>${fn:escapeXml(greeting_content)}</blockquote>
 to be translated into emoji 
 <br>
<blockquote>
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
    <% 
    } 
}
    %>

<form action="/sign" method="post">
    <div><textarea name="content" rows="3" cols="60"></textarea></div>
    <div><input type="submit" value="Post Message to Convert to emoji"/>
    </div>
    <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
</form>



</body>
</html>
