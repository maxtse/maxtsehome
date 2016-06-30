<%--
  Created by IntelliJ IDEA.
  User: xyb
  Date: 16-6-19
  Time: 上午12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<c:out value="${data}"/>
<jsp:text><%= request.getParameter("data")%></jsp:text>
i am jsp
</body>
</html>
