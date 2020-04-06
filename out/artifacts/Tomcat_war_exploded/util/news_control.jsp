<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>新闻列表--管理后台</title>
    <link href="../css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="../newsPages/console_element/top.jsp" %>
<div id="main">
    <%@include file="../newsPages/console_element/left.html" %>
    <div id="opt_area">
        <%--<h1 id="opt_type"> 编辑新闻 </h1>--%>
        <table>
            <tbody>
            <c:forEach items="${requestScope.newsList}" var="s">
                <tr>
                    <td style="width: 600px"><a
                            href="${pageContext.request.contextPath}/readNews?newsId=${s.id}">${s.title}</a></td>
                    <td style="width: 80px">作者：${s.author}</td>
                    <td style="width: 35px"><a href="${pageContext.request.contextPath}/addNews?id=${s.id}">修改</a></td>
                    <td><a href="${pageContext.request.contextPath}/delNews?id=${s.id}">删除</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%--<c:forEach items="${requestScope.newsList}" var="s">
            <li>
                <a href="${pageContext.request.contextPath}/readNews?newsId=${s.id}">${s.title}</a>
                <span>${s.date}</span>
            </li>
        </c:forEach>--%>
        <p align="right"> 当前页数:[
            <label>
                <INPUT id="goto" type="text" VALUE="${requestScope.pages+1}" style="width: 20px"
                       oninput="value=value.replace(/[^\d]/g,'')">
            </label>/${requestScope.totalPage}]
            <c:if test="${requestScope.pages>0}">
                <a href="${pageContext.request.contextPath}/setNews?pages=0">首页</a>
                <a href="${pageContext.request.contextPath}/setNews?pages=${requestScope.pages-1}">上一页</a>
            </c:if>
            <c:if test="${requestScope.pages<requestScope.totalPage-1}">
                <a href="${pageContext.request.contextPath}/setNews?pages=${requestScope.pages+1}">下一页</a>
                <a href="${pageContext.request.contextPath}/setNews?pages=${requestScope.totalPage-1}">末页</a>
            </c:if>
        </p>
    </div>
</div>
<div id="footer">
    <%@include file="../newsPages/console_element/bottom.html" %>
</div>
</body>
</html>
