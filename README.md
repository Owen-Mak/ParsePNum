# ParsePNum
Java servlet used to parse phone numbers using google's libphonenumber library.

# What is it?
This is a Java servlet created with Eclipse Java EE IDE for Web Developers version Oxygen. 2 Release (4.7.2).
The servlet's main page contains 2 web forms to receive input either as a string or a file.  Once the input is received, the servlet 
will output a JSON string containing all Canadian phone numbers found from the input.

# Quick Links
*  [The libphonenumber library](https://github.com/googlei18n/libphonenumber)
*  [Quick guide on how to create a servlet](http://www.codejava.net/ides/eclipse/how-to-create-deploy-and-run-java-servlet-in-eclipse) 
*  [Quick guide to JUnit tests in mockito](http://javaworld-abhinav.blogspot.ca/2014/06/testing-servlets-using-junit-and-mockito.html)

# How to deploy?
What you will need:

*  [Eclipse Java EE IDE for Web Developers](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen2)
*  [Apache Tomcat 7](https://tomcat.apache.org/download-70.cgi) - choose the appropriate link for the OS you are using
*  [WAR file for project](https://github.com/Owen-Mak/ParsePNum/blob/master/ParsePNum.war)

Instructions for Windows deployment:

  1. Install the eclipse IDE into any folder
  1. Install the Apache Tomcat into any folder
  1. Start the eclipse IDE.
  1. Select a workspace.
  1. Once inside eclipse, choose File -> Import -> Web -> WAR file
  1. Import the WAR file you downloaded from "How to deploy?" [Link Here to WAR file](https://github.com/Owen-Mak/ParsePNum/blob/master/ParsePNum.war)
  
  ![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/ImportWAR.png)
  
  1. Use "ParsePNum" as name of the web project and set Target Runtime to Apache Tomcat v7.0.  Choose Finish.
  
  ![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/ImportWAR2.png)
  
To create a new Apache Server:

  1. Go to Server window with Window -> Show View -> Server
  1. Click on link to create a new server
  
  ![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/NewServerLink.png)
  
  1. Choose Tomcat v7.0 Server, and set host name as localhost.  Select Next.
  
  ![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/NewServerSetup1.png)
  
     * If this is your first time, you may need to tell the IDE where the Tomcat installation directory is.
     Use Browse button and search for your Apache Tomcat installation folder.
     
     ![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/NewServerSetup2.png)
  
  1. Right click the newly created server, choose "Add and Remove..."
  
  ![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/NewServerSetup3.png)
  
  1. Add the servlet ParsePNum from "Available" to "Configured".
  1. The server is ready to be deployed.  Read below for usage.

# Usage
The first thing to do is to start the apache server.  

Simply go to Window -> Show View -> Server

Next start the server from the server menu.  Here's a screenshot:

![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/RunServer.png)

After the server is running, to access the servlet's main page, go to [http://localhost:8080/ParsePNum/phonenumberparse.jsp](http://localhost:8080/ParsePNum/phonenumberparse.jsp).  This assumes you started server on port 8080.

Here's a screenshot of the main page:
![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/MainPage.png)

Currently, there are 3 ways to use ParsePNum.
  1. GET from servlet page
     *  Simply type text into the GET textbox and click submit.
  1. POST from servlet page
     *  Select a file to upload and click submit.
  1. GET by editting URL on address bar
     *  Edit the address bar as per screenshot below.
     
     ![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/GetAddressBar.png)

# Unit testing

The tests are in src/PhonenumbersTest.java.  They are written using JUnit and Mockito libraries.  Currently, the only 
method being tested is doGet() method.  

To start a unit test:
* Open PhonenumbersTest.java
* Select Run from main menu -> Run As -> JUnit Test

![picture](https://github.com/Owen-Mak/ParsePNum/blob/master/screenshots/ExecuteUnitTest.png)

To add more test cases, simply create a new test# method, and add that test method onto the main method.

# To Do
*  Unit testing for doPost()
   *  Ideally using Spring Test Framework or JUnit with Mockito
*  Include more test cases 
*  Automated testing using Travis CI
*  Parsing PDF or MS Word files
*  Add instructions to deploy in other OS (linux, macOS)
