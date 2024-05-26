<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<%@ page import="com.slot.Model.*,com.slot.Common.*" %>
<%
	try{
		request.setCharacterEncoding("utf-8");
		String menu = (String)request.getAttribute("MENU");
%>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><%=menu%></title>
	<%--<link rel="shortcut icon" type="image/png" href="/img/Front/common/Thor_logo.png" />--%>
	<link rel="stylesheet" href="/assets/css/styles.min.css" />
</head>
<%
		}catch(Exception e){ e.printStackTrace(); }
%>			