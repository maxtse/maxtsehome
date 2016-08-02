<%@ page import="java.util.Map" %>
<body> Hello World</body>

<%
    Map<String, Integer> companyInternshipMap = (Map<String, Integer>)request.getAttribute("nYearCompanyInternshipCount");
    for (Map.Entry<String, Integer> entry : companyInternshipMap.entrySet()) {
        %>

        <tr>
        <td><%=entry.getKey()%></td>
        <td><%=entry.getValue()%></td>
        </tr>
<%
    }
%>