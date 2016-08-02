<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8">
<title>非常抱歉，您访问的页面不存在。</title>
<style type="text/css">
body,h1,h2,dl,dt,dd{margin:0;padding:0; font-size:100%;}
a {text-decoration:none;color:#0069ca;}
body{ text-align:center;}
a:hover {text-decoration:none;color:#f60;}
.error{ margin:90px auto 0; text-align:left; width:480px;}
.error h1{ margin-bottom:20px;}
.error h1 a { background: url("http://source.qunar.com/site/images/2011/qn.header.png") no-repeat 0 -8px ; display: block; height: 52px;}
.error h1 a span{ display:none;}
.error h2{ margin-bottom:20px; font-size:16px; line-height:1.5; }
.error dl{ font-size:12px; line-height:24px; }
.error dl dt{ margin-bottom:8px; }
.error dl dd a{ margin: 0 8px; }
.error dl dd a.back { margin:0; }
</style>
</head>
<body>
<div class="error">
	<c:choose>
	    <c:when test="${commonVO != null}"><h2>${commonVO.message}</h2></c:when>
	    <c:otherwise><h2>非常抱歉，您访问的页面不存在。</h2></c:otherwise>
	 </c:choose>
</div>
</body>
</html>
