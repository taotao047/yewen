function submit() {
    let feedbacks = ["手机号格式错误", "用户名由字母、数字、下划线6-16个字符组成",
        "邮箱格式错误", "密码由6-16个字符组成", "用户名/手机号/邮箱重复"];
    let i;
    const uPhoneIn = $('#uPhone').val();
    const PhoneRegex = /^1\d{10}/;

    const uIDIn = $("#uID").val();
    const IDRegex = /^[a-zA-Z0-9_\u4e00-\u9fa5]{6,16}$/

    const uEmailIn = $("#uEmail").val();
    const EmailRegex = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/

    const uPasswordIn = $("#uPassword").val();
    const PasswordRegex = /^.{6,16}$/

    const errorMessages = $("span[class~='error-message']");
    if (!$("input[type='checkbox']").is(":checked")) {
        if ($("#bottom-text2").length === 0) {
            $("button[type='submit']").after("<sapn class='reg-feedback' " +
                "style='color: red;position: relative' id='bottom-text2'>请阅读并同意《用户服务协议》和《隐私政策》</sapn>");
        }
        $("button[type='submit']").after("<span></span>")
        return;
    }
    if (uPhoneIn.match(PhoneRegex)) {
        errorMessages[0].innerHTML = "";
        errorMessages[0].classList.remove("reg-feedback");
    } else {
        errorMessages[0].innerHTML = feedbacks[0];
        errorMessages[0].classList.add("reg-feedback");
        return;
    }
    if (uIDIn.match(IDRegex)) {
        errorMessages[1].innerHTML = "";
        errorMessages[1].classList.remove("reg-feedback");
    } else {
        errorMessages[1].innerHTML = feedbacks[1];
        errorMessages[1].classList.add("reg-feedback");
        return;
    }
    if (uEmailIn.match(EmailRegex)) {
        errorMessages[2].innerHTML = "";
        errorMessages[2].classList.remove("reg-feedback");
    } else {
        errorMessages[2].innerHTML = feedbacks[2];
        errorMessages[2].classList.add("reg-feedback");
        return;
    }
    if (uPasswordIn.match(PasswordRegex)) {
        errorMessages[3].innerHTML = "";
        errorMessages[3].classList.remove("reg-feedback");
    } else {
        errorMessages[3].innerHTML = feedbacks[3];
        errorMessages[3].classList.add("reg-feedback");
        return;
    }
    $.ajax({
            url: "http://localhost:8080/register",
            data: {
                uID: uIDIn,
                uEmail: uEmailIn,
                uPassword: uPasswordIn,
                uPhone: uPhoneIn
            },
            type: "POST",
            dataType: "text"
        }
    ).done(function (text) {
        if (text.match("true")) {
            localStorage.setItem("uID", uIDIn);
            localStorage.setItem("uPassword", uPasswordIn);
            window.open("../yewen.html");
        } else {
            if ($("#bottom-text1").length === 0) {
                $("button[type='submit']").after("<sapn class='reg-feedback' " +
                    "style='color: red' id='bottom-text1'>" + feedbacks[4] + "</sapn>");
            }
        }
    }).fail(function (xhr, status, errorThrown) {
        alert("服务器在开小差...");
        console.log("Error: " + errorThrown);
    })
}

//注册提交
// function submit() {
//     /*对一些行为的处理*/
//     var registerInfo = document.getElementById("registerInfo");

//     if (!document.getElementById("agreeBox").checked) {//没有勾选同意协议
//         registerInfo.innerHTML = "请阅读并同意下列协议";
//         registerInfo.style.setProperty("visibility", "visible");
//         return;
//     }
//     registerInfo.style.setProperty("visibility", "hidden");
//     registerInfo.innerHTML = "";
//     var uIDV = this.uID.value;
//     var uPasswordV = this.uPassword.value;
//     var uEmailV = this.uEmail.value;
//     var uPhoneV = this.uPhone.value;
//     /*执行注册事务*/
//     var xhr = new XMLHttpRequest();
//     xhr.open("post", "http://localhost:8080/register");
//     xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
//     var sendText="uID=" + uIDV + "&" +
//         "uPassword=" + uPasswordV + "&" + "uEmail=" + uEmailV + "&" + "uPhone=" +
//         uPhoneV;
//     var encodeText = encodeURI(encodeURI(sendText));
//     console.log(encodeText);
//     xhr.send(encodeText);
//     xhr.onload = function () {
//         var respText = xhr.responseText;
//         console.log(respText);
//         if (respText.match("true")) {

//         } else {
//             console.log("注册失败");
//             registerInfo.innerHTML = "手机号、邮箱、用户名重复！"
//             registerInfo.style.setProperty("visibility", "visible");
//         }
//     }
//
// }