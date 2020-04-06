<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
    <meta http-equiv="description" content="This is my page"/>
    <title>添加主题--管理后台</title>
    <link href="../css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="console_element/top.jsp" %>
<div id="main">
    <div id="opt_list">
        <ul>
            <li><a href="${pageContext.request.contextPath}/addNews">添加新闻</a></li>
            <li><a href="${pageContext.request.contextPath}/setNews">编辑新闻</a></li>
            <li><a href="${pageContext.request.contextPath}/addTheme">添加主题</a></li>
            <li><a href="${pageContext.request.contextPath}/setTheme">编辑主题</a></li>
            <li><a href="${pageContext.request.contextPath}/setUser">编辑用户</a></li>
        </ul>
    </div>
    <div id="opt_area">
        <ul class="classList"></ul>
    </div>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
</body>
</html>
