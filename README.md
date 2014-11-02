

App Engine Managed VMs Java EmojiSlator
Copyright (C) 2010-2014 Google Inc.

## Sample guestbook for use with App Engine Java.

Requires [Apache Maven](http://maven.apache.org) 3.1 or greater, and JDK 7+ in order to run.

To build, run

    mvn package

Building will run the tests, but to explicitly run tests you can use the test target

    mvn test

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.


 mvn appengine:gcloud_app_run

see managed VM's help doc: 
https://docs.google.com/a/google.com/document/d/1wno0ZZaDulHugUJ2RIc0uvZSlixzC3xAwo5Te-bxL50/edit#



For further information, consult the [Java App Engine](https://developers.google.com/appengine/docs/java/overview) documentation.

To see all the available goals for the App Engine plugin, run

    mvn help:describe -Dplugin=appengine

To deploy run:
gcloud preview app deploy --server preview.appengine.google.com target/guestbook-1.0-SNAPSHOT



