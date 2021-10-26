/*对输入框的修饰*/
var input = document.querySelector('.a-inputBox input');
input.selectionStart = 8;
for (let inputBox of document.getElementsByTagName('input')) {
    inputBox.setAttribute("autoComplete", "off");
    inputBox.addEventListener('focus', function () {
        this.parentElement.style.setProperty("border-color", "#4285f4");
    })
    inputBox.addEventListener('blur', function () {
        this.parentElement.style.setProperty("border-color", "#9F9F9F");
    })

}

//注册提交
function submit() {
    /*对一些行为的处理*/
    var registerInfo = document.getElementById("registerInfo");
    if (this.uID.value == "" || this.uPassword.value == "" || this.uEmail.value == "" || this.uPhone.value == "") {
        registerInfo.style.setProperty("visibility", "visible");
        registerInfo.innerHTML = "请完善您的注册信息！";
        return;
    }
    if (!document.getElementById("agreeBox").checked) {//没有勾选同意协议
        registerInfo.innerHTML = "请阅读并同意下列协议";
        registerInfo.style.setProperty("visibility", "visible");
        return;
    }
    registerInfo.style.setProperty("visibility", "hidden");
    registerInfo.innerHTML = "";
    var uIDV = this.uID.value;
    var uPasswordV = this.uPassword.value;
    var uEmailV = this.uEmail.value;
    var uPhoneV = this.uPhone.value;
    /*执行注册事务*/
    var xhr = new XMLHttpRequest();
    xhr.open("post", "http://localhost:8080/register");
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var sendText="uID=" + uIDV + "&" +
        "uPassword=" + uPasswordV + "&" + "uEmail=" + uEmailV + "&" + "uPhone=" +
        uPhoneV;
    var encodeText = encodeURI(encodeURI(sendText));
    console.log(encodeText);
    xhr.send(encodeText);
    xhr.onload = function () {
        var respText = xhr.responseText;
        console.log(respText);
        if (respText.match("true")) {
            localStorage.setItem("uID", uIDV);
            localStorage.setItem("uPassword", uPasswordV);
            window.open("../yewen.html");
        } else {
            console.log("注册失败");
            registerInfo.innerHTML = "手机号、邮箱、用户名重复！"
            registerInfo.style.setProperty("visibility", "visible");
        }
    }

}