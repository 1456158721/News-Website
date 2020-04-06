<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="css/read.css" rel="stylesheet" type="text/css"/>
    <title>${requestScope.thisNews.title}</title>
</head>
<body>
<div id="header">
    <div id="top_login">
        <form action="${pageContext.request.contextPath}/login" method="post" onsubmit="return check()">
            <label> 登录名 </label>
            <label for="uName"></label>
            <input type="text" name="uName" id="uName" value="" class="login_input"/>
            <label> 密&#160;&#160;码 </label>
            <label for="uPwd"></label>
            <input type="password" name="uPwd" id="uPwd" value="" class="login_input"/>
            <input type="submit" class="login_sub" value="登录"/>
            <a href="${pageContext.request.contextPath}/index">返回首页</a>
            <label id="error" style="color: red;">${requestScope.Error}</label>
            <img src="images/friend_logo.gif" alt="Google" id="friend_logo"/>
        </form>
    </div>
    <div id="nav">
        <div id="logo"><img src="${pageContext.request.contextPath}/images/logo.jpg" alt="新闻中国"/></div>
        <div id="a_b01"><img src="${pageContext.request.contextPath}/images/a_b01.gif" alt=""/></div>
    </div>
</div>
<div id="container">
    <%@include file="index-elements/index_sidebar.jsp" %>
    <div class="main">
        <div class="class_type"><img src="images/class_type.gif" alt="新闻中心"/></div>
        <div class="content">
            <ul class="classList">
                <li style="font-size: xx-large;padding-top: 5px;word-break: break-word;">${requestScope.thisNews.title}</li>
                <hr>
                <li style="color: darkgrey;padding-top: 5px">作者：${requestScope.thisNews.author}</li>
                <li style="color: darkgrey;padding-top: 5px">发布时间：${requestScope.thisNews.date}</li>
                <li style="font-size: 17px;padding-top: 5px;word-break: break-word;">${requestScope.thisNews.content}</li>
                <li><img src="${requestScope.thisNews.image}" alt="" style="width: 250px"/></li>
            </ul>
        </div>
        <hr>
        <c:forEach items="${requestScope.commentList}" var="thisComm">
            <table border="0" cellpadding="0" cellspacing="0" align="center" width="530">
                <tr>
                    <td>留言人：</td>
                    <td>${thisComm.userName}</td>
                </tr>
                <tr>
                    <td>IP：</td>
                    <td>${thisComm.ip}</td>
                </tr>
                <tr>
                    <td>留言时间：</td>
                    <td>${thisComm.date}</td>
                </tr>
                <tr>
                    <td>${thisComm.comment}</td>
                </tr>
            </table>
            <hr>
        </c:forEach>
        <form action="${pageContext.request.contextPath}/addComment" method="post">
            <table border="0" cellpadding="0" cellspacing="0" align="center" width="530">
                <label style="display: none;">
                    <input name="newsId" type="text" maxlength="0"
                           style="display: none;" readonly value="${requestScope.strId}">
                    <input name="ip" type="text" maxlength="0"
                           style="margin-bottom: 0;margin-top: 10px;display: none;"
                           readonly value="${requestScope.ip}">
                </label>
                <tr>
                    <td>
                        <label>评论&emsp;</label>
                    </td>
                </tr>
                <tr>
                    <td>用户名</td>
                    <td>
                        <label>
                            <input name="userName" type="text" maxlength="30"
                                   style="margin-bottom: 0;margin-top: 10px;">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td width="50" height="35">ip:&emsp;</td>
                    <td>
                        <p>${requestScope.ip}</p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                                <textarea required rows="10" cols="10" name="comment"
                                          style="height: 150px;width: 169px;margin-top: 10px;
                                          resize:none;"></textarea>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="发表"
                               style="padding: 3px 10px 3px 10px;margin: 5px;
                                   border: black 2px solid;font-size: larger;">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<%
    request.removeAttribute( "news_view" );
    request.removeAttribute( "comments_view" );
%>
<%@include file="index-elements/index_bottom.html" %>
</body>
</html>
