<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>主题列表--管理后台</title>
    <link href="../css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="console_element/top.jsp" %>
<div id="main">
    <%@include file="console_element/left.html" %>
    <div id="opt_area">
        <h1 id="opt_type"> 编辑主题 </h1>
        <ul class="classList">
            <li>
                <table>
                    <tbody>
                    <c:forEach items="${requestScope.themeList}" var="s">
                        <tr id="${s.key}">
                            <td style="width: 75px">${s.key}</td>
                            <td style="width: 75px">${s.value}</td>
                            <td onclick='setTheme(this)'
                                style="width: 35px;text-decoration: underline;color: blue;cursor: pointer;">修改
                            </td>
                            <td onclick='removeTheme("${s.key}")'
                                style="width: 35px;text-decoration: underline;color: blue;cursor: pointer;">删除
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </li>
        </ul>
    </div>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript">
    let thisForm;
    let thisFormHtml;
    let input_key;
    let input_value;

    function setTheme(obj) {
        input_key = $(obj).prev().prev().text();
        input_value = $(obj).prev().text();
        thisForm = $(obj).parent();
        thisFormHtml = thisForm.html();
        $(thisForm).html("<td>" + input_key + "</td>" +
            "<td><label><input id='" + input_key + "' type='text' name='newThemeName' value='" +
            input_value + "' style='width: 100px;'/></label></td>" +
            "<td><button onclick='submitNewThemeName(this)'>提交</button></td>" +
            "<td><button onclick='recoveryForm()'>取消</button></td>" +
            "<td><input type='text' name='themeId' value='" + input_key + "' style='display:none;' readonly/></td>");
    }

    function submitNewThemeName(obj) {
        const input = $(obj).parent().prev().children().children();
        input_key = input.attr("id");
        input_value = input.val();
        $(obj).parent().parent().append("<form id='newThemeName' action='${pageContext.request.contextPath}/setTheme' method='post'>" +
            "<label><input type='text' name='themeId' value='" + input_key + "'>" +
            "<input type='text' name='newThemeName' value='" + input_value + "'></label></form>");
        $("#newThemeName").submit();
    }

    function recoveryForm() {
        $(thisForm).html(thisFormHtml);
    }

    function removeTheme(id) {
        /*location.href = "/delTheme?id=" + id;*/
        $.get("/delTheme",{id},function (re) {
            if (re){
                $("#" + id).remove();
            }
        });
    }
</script>
</body>
</html>
