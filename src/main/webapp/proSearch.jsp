<%@ page import="com.yewen.controller.TestThread" %><%--
  Created by IntelliJ IDEA.
  User: TaoVh
  Date: 2021/7/26
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>专业搜索</title>

</head>
<body>
    <%
        //可行
        session.setAttribute("datas","haha");
//        new Thread(new TestThread(session)).start();
    %>
    <%=session.getAttribute("datas")%>
</body>
</html>
