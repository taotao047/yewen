var infoBox = document.getElementById('infoBox');
var ws;
function init() {
    for (let inputBox of document.getElementsByTagName('input')) {
        inputBox.setAttribute("autoComplete","off");
        inputBox.addEventListener('focus',function () {
            this.parentElement.style.setProperty("border-color","#4285f4");
        })
        inputBox.addEventListener('blur',function () {
            this.parentElement.style.setProperty("border-color","#9F9F9F");
        })
    }
    if(!(('localStorage' in window)&&(window.localStorage !==null))){
        alert("业问目前不支持您的浏览器");
    }
}
init();

function loginCheck() {
    var xhr = new XMLHttpRequest();
    var uid = document.getElementById('uID').value;
    var uPassword = document.getElementById('uPassword').value;
    console.log(uid + uPassword);
    xhr.open("post","http://localhost:8080/login");
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xhr.send("uID="+uid+
        "&uPassword="+uPassword);
    xhr.onload = function () {
        if(xhr.responseText=="true"){
            infoBox.style.setProperty('visibility','hidden');
            localStorage.setItem("uID",uid);
            localStorage.setItem("uPassword",uPassword);
            window.open("../yewen.html");
        }else {//用户名或密码错误
            infoBox.style.setProperty('visibility','visible');
        }
    }
}
