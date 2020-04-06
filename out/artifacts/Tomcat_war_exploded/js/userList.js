let thisForm;
let thisFormHtml;
let input_id;
let input_name;
let input_pwd;
let input_permission;
let thisParameter;
let thisParameterHtml;

//点击输入框时边框复原为黑色
function recoveryInput (obj) {
    $(obj).css("border", "black 1px solid")
}

function addUser() {
    $("#from").append("<tr class='addTrFromButton'>" +
        "<td class='id'>注：<br />1、id为系统生成，确认提交后即可显示<br />" +
        "2、每个框都是必填；<br />3、权限等级为0-100，数越大权限越高，" +
        "注册后的用户最低为5，默认为10，若不填则默认为10；</td>" +
        "<td class='name'><input type='text' class='0' " +
        "style='width:75px;' onblur='addName(this)' onclick='recoveryInput(this)'/></td>" +
        "<td class = 'pwd'><input type='password' class='0' " +
        "style='width:75px;' onblur='addPwd(this)' onclick='recoveryInput(this)'/></td>" +
        "<td class='permission'><input type='number' id='addPermission' style='width: 30px' value='10' readonly/>" +
        "<button style='margin-right: 5px;' onclick='addUserPermission(this)'>+</button>" +
        "<button style='margin-right: 5px;' onclick='lessUSerPermission(this)'>-</button></td>" +
        "<td><button style='margin-right: 5px;' onclick='submitNewUser(this)'>确认新建</button>" +
        "<button style='margin-right: 5px;' onclick='removeNewUser(this)'>取消新建</button></td></tr>");
}

//控制提交
function addName(obj) {
    if ($(obj).val().length < 1) {
        $(obj).css("border", "red 1px solid");
        $(obj).attr("class", "0");
    } else {
        $(obj).attr("class", "1");
    }
}

function addPwd(obj) {
    if ($(obj).val().length < 1) {
        $(obj).css("border", "red 1px solid");
        $(obj).attr("class", "0");
    } else {
        $(obj).attr("class", "1");
    }
}

//提交按钮
function submitNewUser(obj) {
    const userNameInput = $(obj).parent().prev().prev().prev().children();
    const userPwdInput = $(obj).parent().prev().prev().children();
    const userPerInput = $(obj).parent().prev().children();
    if (parseInt(userNameInput.attr("class")) === 1 && parseInt(userPwdInput.attr("class")) === 1) {
        const newName = userNameInput.val();
        const newPwd = userPwdInput.val();
        const newPermission = userPerInput.val();
        $.post("/addUser", {newName, newPwd, newPermission}, function (re) {
            const getRe = $.parseJSON(re);
            const flag = getRe[0];
            const user = getRe[1];
            if (flag) {
                removeNewUser(obj);
                $("#from").append("<tr>\n" +
                    "<td style='width: 75px'>" + user.userId + "</td>\n" +
                    "<td class='" + user.userId + "' style='width: 75px'>" + user.userName + "</td>\n" +
                    "<td class='" + user.userId + "' style='width: 75px'>" + user.password + "</td>\n" +
                    "<td class='" + user.userId + "' style='width: 75px'>" + user.permission + "</td>\n" +
                    "<td><button onclick='setUser(this)' " +
                    "style='margin-right: 5px;'>修改账户</button>\n" +
                    "<button style='margin-right: 5px;' onclick='setUserPermission(this)'>提升权限</button>\n" +
                    "<button style='margin-right: 5px;' onclick='delUser(" + user.userId + ",this)'>删除账号</button></td>\n" +
                    "</tr>");
            } else {
                alert("提交失败，请稍后再试");
            }
        });
    }
}

//取消新建按钮
function removeNewUser(obj) {
    $(obj).parent().parent().remove();
}

//修改用户名和密码
function setUser(obj) {
    input_id = $(obj).parent().prev().attr("class");
    input_name = $(obj).parent().prev().prev().prev().text();
    input_pwd = $(obj).parent().prev().prev().text();
    input_permission = $(obj).parent().prev().text();
    thisForm = $(obj).parent().parent();
    thisFormHtml = thisForm.html();
    $(thisForm).html("<td>" + input_id + "</td>" +
        "<td><label><input class='" + input_id + "' type='text' name='newUserName' value='" +
        input_name + "' style='width: 100px;'/></label></td>" +
        "<td><label><input class='" + input_id + "' type='password' name='newUserPwd' " +
        "style='width: 100px;'/></label></td>" +
        "<td>" + input_permission + "</td>" +
        "<td><button style='margin-right: 5px;' onclick='submitSetUser(" + input_id + ")'>提交</button>" +
        "<button style='margin-right: 5px;' onclick='recoveryForm(" + input_id + ")'>取消</button></td>");
}

function submitSetUser(id) {
    input_id = id;
    input_name = $("." + id + ":eq(0)").val();
    input_pwd = $("." + id + ":eq(1)").val();
    if (input_name.length > 1 && input_pwd.length > 1) {
        $.post("/setUser", {id: input_id, setName: input_name, setPwd: input_pwd}, function (re) {
            const getRe = $.parseJSON(re);
            const flag = getRe[0];
            const id = getRe[1];
            const name = getRe[2];
            const pwd = getRe[3];
            if (flag) {
                recoveryForm(id);
                $("." + id + ":eq(0)").text(name);
                $("." + id + ":eq(1)").text(pwd);
            }
        });
    } else {
        alert("用户名或密码不能为空");
    }
}

function recoveryForm(id) {
    $("." + id + ":eq(0)").parent().parent().parent().html(thisFormHtml);
}

function delUser(id, obj) {
    if (window.confirm("你确认要删除该用户吗？")){
        /*待完成：获取当前用户的权限并作出限制*/
        $.post("/delUser", {id}, function (re) {
            if (re) {
                $(obj).parent().parent().remove();
            } else {
                alert("ID为" + id + "的用户删除失败");
            }
        });
    }
}

function setUserPermission(obj) {
    thisParameter = $(obj).parent().prev();
    input_permission = thisParameter.text();
    thisParameterHtml = $(obj).parent().parent().html();
    thisParameter.html("<input type='number' readonly value='" + input_permission + "' style='width: 30px' />" +
        "<button onclick='addUserPermission(this)'>+</button><button onclick='lessUSerPermission(this)'>-</button>");
    $(obj).parent().html("<button style='margin-right: 5px;width: 57px;' onclick='submitUserPermission(this)'>提交</button>" +
        "<button style='margin-right: 5px;width: 57px;' onclick='recoveryPermission(this)'>取消</button>");
}

function addUserPermission(obj) {
    const num=$(obj).prev();
    /*待完成：获取当前用户的权限并作出限制*/
    if(parseInt(num.val()) <= 100){
        num.val(parseInt(num.val())+5);
    }
}

function lessUSerPermission(obj) {
    const num=$(obj).prev().prev();
    if(parseInt(num.val()) >= 5){
        num.val(parseInt(num.val())-5);
    }
}

function submitUserPermission(obj) {
    input_id = $(obj).parent().prev().attr("class");
    input_permission = $(obj).parent().prev().children(":eq(0)").val();
    $.post("/setUserPermission", {"id": input_id, "permission": input_permission}, function (re) {
        if (re) {
            recoveryPermission(obj);
            $("." + input_id + ":eq(2)").text(input_permission);
        } else {
            alert("更改ID为" + input_id + "的用户权限失败");
        }
    });
}

function recoveryPermission(obj) {
    $(obj).parent().parent().html(thisParameterHtml);
}