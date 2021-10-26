<%@ page import="java.net.Socket" %>
<%@ page import="java.net.DatagramSocket" %>
<%@ page import="java.net.DatagramPacket" %>
<%@ page import="java.net.InetAddress" %><%--
  Created by IntelliJ IDEA.
  User: TaoVh
  Date: 2021/8/4
  Time: 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        textarea {
            background-color: hotpink;
        }
    </style>
</head>
<body>
<button>发送</button>
<label for="sendInfo">发送的信息</label>
<textarea id="sendInfo" cols="30" rows="10" type="text"></textarea>
<script>
    var btn = document.querySelector('button');
    let text = document.getElementById('sendInfo');
    console.log(text);
    btn.onclick = function () {
        let xhr = new XMLHttpRequest();
        let str = "http://localhost:8080/tests/send.jsp?id="+"你好";
        console.log(str)
        xhr.open("get", str);
        xhr.send();
        <%
        if (request.getParameter("id")!=null){
            DatagramSocket socket = new DatagramSocket();
            String msg = request.getParameter("id");
            InetAddress localhost=InetAddress.getByName("localhost");
            int port = 6666;
            DatagramPacket packet =new DatagramPacket(msg.getBytes(),0,msg.getBytes().length,localhost,port);
            socket.send(packet);
            System.out.println(msg);
            System.out.println("发送端发送成功！");
//            socket.close();
//            System.out.println("发送端关闭成功！");
        }
        %>
    }
</script>
</body>
</html>
