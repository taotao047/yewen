var table = layui.use('table', function () {
    var table = layui.table;
    //第一个实例
    table.render({
        elem: '#guiders',
        height: 500,
        url: 'http://localhost:8080/update',
        method: "post",
        where: {
            isByMajor: "True",
            guiderMajor: "ALL",
            guiderName: "ALL"
        },
        cols: [[ //表头
            {field: 'guiderPro', title: '专业', width: 160, align: 'center'}
            , {field: 'guiderName', title: '导生名', width: 160, align: 'center'}
            , {field: 'state', title: '状态', width: 80, sort: true, align: 'center'}
            , {field: 'operation', title: '操作', width: 240, align: 'center'}
        ]],
        parseData: function (res) {
            const data = res.data;
            for (const datum of data) {
                datum["operation"] = "<li><button style=\"width: 62px;height: 28px;color: black;border-radius: 6px;background: #f2eded; cursor:pointer;\" onmouseover=render(this) onmouseleave=render(this)>留言</button><button style=\"width: 62px;height: 28px;color: black;border-radius: 6px;background: #f2eded;cursor:pointer;\" id='chatBtn' onclick = chat(this) onmouseover=render(this) onmouseleave=render(this)>咨询</button></li>"
                if (datum["state"] == true){
                    datum["state"] = "<embed src=\"images/onlineIcon.svg\" type=\"image/svg+xml\"/>"
                }
                else {
                    datum["state"] = "<embed src=\"images/outlineIcon.svg\" type=\"image/svg+xml\"/>"
                    datum["operation"] = "<li><button style=\"width: 62px;height: 28px;color: black;border-radius: 6px;background: #f2eded; cursor:pointer;\" onmouseover=render(this) onmouseleave=render(this)>留言</button><button style=\"width: 62px;height: 28px;color: black;border-radius: 6px;background: #f2eded;cursor:not-allowed;\" id='chatBtn' disabled>咨询</button></li>"
                }
            }
            return {
                "code": res.code,
                "msg": res.msg,
                "count": res.count,
                "data": res.data
            }
        }
    });
});
layui.use('dropdown', function () {
    var dropdown = layui.dropdown
    dropdown.render({
        elem: '#chooseCollege',
        trigger: 'hover',
        data: [{
            title: '商学院'
            , id: 100
            , href: 'javascript:;'
        }, {
            title: '工学院'
            , id: 101
            , href: 'javascript:;' //开启超链接
        }, {
            title: '工学院'
            , id: 101
            , href: 'javascript:;' //开启超链接
        }, {
            title: '长江艺术设计学院'
            , id: 101
            , href: 'javascript:;' //开启超链接
        }, {
            title: '工学院'
            , id: 101
            , href: 'javascript:;' //开启超链接
        }]
        //菜单被点击的事件
        , click: function (obj) {
            console.log(obj);
            layer.msg('回调返回的参数已显示再控制台');
        }
    });
});

var flowChatBox = document.querySelector("#flowChatBox");
var userID = localStorage.getItem('uID');
var targetID;
var msgInput = document.querySelector('#sendBox input');
var chatBox = document.querySelector('#chatBox');
var ws;
//初始化
document.getElementById('uID').innerHTML = userID;
//ws连接配置
if ('WebSocket' in window) {
    ws = new WebSocket("ws://localhost:8080/chat-server/" + userID);
    //连接发生错误的回调方法
    ws.onerror = function (e) {
        console.log("WebSocket连接发生错误，原因为" + e)
    };
    //连接成功建立的回调方法
    ws.onopen = function () {
        console.log("客户端WebSocket连接成功");
    };
    //接收到消息的回调方法
    ws.onmessage = function (event) {
        console.log(event.data);
        var messageJSON = JSON.parse(event.data);
        if (messageJSON.isSystem == true) {
            //打开提示窗口询问导生是否和学生进行聊天
            if (messageJSON.Request == true) {
                targetID = messageJSON.targetID;
                document.querySelector("#chatTips").querySelector("span").innerHTML = messageJSON.targetID;
                document.querySelector("#chatTips").style.setProperty("display", "block");
            } else {//导生拒绝与该同学进行聊天
                document.querySelector("#chatTips").style.setProperty("display", "none");
                alert("该导生拒绝与您进行聊天");
            }
        }

        //是系统信息
        else {
            //将对方发过来的信息展示在前端。导生或者学生都能收到。
            console.log(messageJSON.message);
            setMessageInnerHTML(messageJSON.message, targetID);
        }
    }

    //连接关闭的回调方法
    ws.onclose = function (e) {
        console.log("ws连接已关闭，信息如下" + e.code + e.reason + e.wasClean);
    }
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        flowChatBox.style.setProperty("display", "none");
        chatBox.innerHTML = "";
        ws.close();
    }
} else {
    alert('当前浏览器不支持本网站！')
}


function setMessageInnerHTML(message, userID) {
    var div = document.createElement('div');
    var box = document.createElement('strong');
    box.innerHTML = userID + ":";
    if (userID == this.userID) {
        box.style.setProperty("color", "green");
    } else {
        box.style.setProperty("color", "#0000FF");
    }
    div.append(box);
    div.append(message);
    chatBox.append(div);
}

function sendMsg() {
    var myMessage = msgInput.value;
    var info = {"isSystem": false, "targetID": targetID, "message": myMessage};
    console.log(JSON.stringify(info))
    ws.send(JSON.stringify(info));
    setMessageInnerHTML(myMessage, userID);
}

function showChatBox(targetID) {
    document.querySelector("#chatBox>span").innerHTML = "和" + targetID + "的聊天";
    flowChatBox.style.setProperty("display", "block");
}

//用户需要向别人请求聊天的时候
function chat(obj) {
    document.querySelector("#chatBox").innerHTML = "<span></span>";
    targetID = obj.parentElement.parentElement.parentElement.parentElement.children[1].querySelector('div').innerHTML;
    showChatBox(targetID);
    //isSystem表示向别人请求聊天而不是已经开始聊天了。
    var info = {"isSystem": true, "targetID": targetID, "GuidersResp": false};
    console.log(JSON.stringify(info));
    ws.send(JSON.stringify(info));
}

function closeChatBlock() {
    flowChatBox.style.setProperty("display", "none");
}

//接受别人的聊天请求
function accept(obj) {
    targetID = obj.parentElement.querySelector("span").innerHTML;
    showChatBox(targetID);
    obj.parentElement.style.setProperty("display", "none");
}

function refuse(obj) {
    //系统消息。导生拒绝向该同学聊天。
    var info = {"isSystem": true, "GuidersResp": true, "Refuse": true, "targetID": targetID};
    ws.send(JSON.stringify(info));
    obj.parentElement.style.setProperty("display", "none");
}

function render(obj) {
    if (obj.style.background != "orange") {
        obj.style.setProperty("background", "orange");
    } else {
        obj.style.setProperty("background", "#f2eded");
    }
}