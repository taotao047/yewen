<%@ page import="java.net.DatagramSocket" %>
<%@ page import="java.net.DatagramPacket" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.lang.*" %>
<%@ page import="com.yewen.controller.RecThread" %>
<%@ page import="com.yewen.controller.TestThread" %>
<%--
  Created by IntelliJ IDEA.
  User: TaoVh
  Date: 2021/8/4
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        textarea {
            background-color: yellowgreen;
        }
    </style>
</head>
<body>
<button>
    接受按钮
</button>
<label for="recInfo">接受到的信息:</label>
<textarea id="recInfo" cols="30" rows="10"></textarea>
</body>
<script>
    let recBtn = document.querySelector('button');
    let text = document.getElementById('recInfo');

    <%
       new Thread(new TestThread(session));
    %>
    recBtn.onclick = function () {
        alert("${datas}");
    }
</script>
</html>
