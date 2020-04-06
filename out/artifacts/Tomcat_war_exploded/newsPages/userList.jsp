<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户列表--管理后台</title>
    <link href="../css/admin.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js"></script>

</head>
<body>
<%@include file="console_element/top.jsp" %>
<div id="main">
    <%@include file="console_element/left.html" %>
    <div id="opt_area">
        <h1 id="opt_type"> 编辑用户 </h1>
        <ul class="classList">
            <li>
                <table>
                    <tbody id="from">
                    <tr>
                        <td style="width: 75px;">id</td>
                        <td style="width: 75px;">用户名</td>
                        <td style="width: 75px;">密码</td>
                        <td style="width: 75px;">权限</td>
                        <td style="width: 200px;">操作<button style="margin-left: 40px" onclick="addUser()">新建账号</button></td>
                    </tr>
                    <c:forEach items="${requestScope.allUser}" var="s">
                        <tr>
                            <td>${s.userId}</td>
                            <td class="${s.userId}">${s.userName}</td>
                            <td class="${s.userId}">${s.password}</td>
                            <td class="${s.userId}">${s.permission}</td>
                            <td><button onclick='setUser(this)' style="margin-right: 5px;">修改账户</button>
                            <button onclick='setUserPermission(this)' style="margin-right: 5px;">提升权限</button>
                            <button onclick='delUser(${s.userId},this)' style="margin-right: 5px;">删除账号</button></td>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/userList.js"></script>
</body>
</html>
