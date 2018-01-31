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

Instructions for Windows deployment


# Usage
The first thing to do is to start the apache server.  Simply go to 
To access the servlet's main page, go to [ParsePNum](http://localhost:8080/ParsePNum/phonenumberparse.jsp)

Currently, there are 3 ways to use ParsePNum.
  1. GET from servlet page
  1. POST from servlet page
  1. GET by editting URL on address bar

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
