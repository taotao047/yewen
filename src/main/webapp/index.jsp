<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Java后端WebSocket的Tomcat实现</title>
    <style>
        button:nth-child(0n+3){
            background-color: pink;
        }
        input:nth-child(2){
            background-color: pink;
        }
        input:nth-child(6){
            background-color: hotpink;
        }
    </style>
</head>
<body>
Welcome
<br/>
<input type="text" id="id" value="taotao">
<button>确认id</button>
<input id="text" type="text"/>
<button onclick="send()">发送消息</button>
<input type="text" value="目标导生">
<hr/>
<button onclick="closeWebSocket()">关闭WebSocket连接</button>
<hr/>
<div id="message"></div>
</body>

<script type="text/javascript">
    var idbtn = document.querySelector("button:nth-child(3)");
    var idtext = document.querySelector("input:nth-child(2)");
    var targettext = document.querySelector("input:nth-child(6)");
    console.log(idbtn);
    console.log(idtext);
    var websocket = null;
    var wsStr = "ws://670505a9e2a6.ngrok.io/chat-server/"+idtext.value+"/"+targettext.value;
    idbtn.onclick = function () {
        wsStr = "ws://670505a9e2a6.ngrok.io/chat-server/"+idtext.value+"/"+targettext.value;
        console.log(wsStr);
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            websocket = new WebSocket(wsStr);
        }
        else {
            alert('当前浏览器 Not support websocket')
        }
        //连接发生错误的回调方法
        websocket.onerror = function () {
            setMessageInnerHTML("WebSocket连接发生错误");
        };

        //连接成功建立的回调方法
        websocket.onopen = function () {
            setMessageInnerHTML("WebSocket连接成功");
            // websocket.send("hi~主机,我是"+idtext.value);
        }

        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            setMessageInnerHTML(event.data);
        }

        //连接关闭的回调方法
        websocket.onclose = function () {
            setMessageInnerHTML("WebSocket连接关闭");
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
        }
    }



    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
</script>
</html>