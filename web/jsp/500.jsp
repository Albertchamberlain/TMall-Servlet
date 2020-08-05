<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.PrintStream" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true"%>
<html>
<head>
<title>500 服务器内部错误</title>
</head>
<body>
<%=exception.getMessage()%>
</body>
</html>
