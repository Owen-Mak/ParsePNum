<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Phone Number Parse</title>
</head>
<body>
	<h2>GET</h2>
	<form name="getnumber" action="/ParsePNum/phonenumbers/parse/text/" method="get" accept-charset="UTF-8"
          enctype="multipart/form-data">
         Phone number:
         <input type="text" name="phoneNumber" size="25">
         <input type="submit" value="Submit">
    </form>
	<hr/>
	<h2>POST</h2>
	<form name="postnumber" action="/ParsePNum/phonenumbers/parse/file" method="post" accept-charset="UTF-8"
          enctype="multipart/form-data">
         <p>Upload a file here</p>
         <input type="file" name="numberFile" size="30">
         <input type="submit" value="Submit">
    </form>
</body>
</html>