<%@ page pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>新闻中国</title>
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" type="">
        function check() {
            const login_username = document.getElementById("uName");
            const login_password = document.getElementById("uPwd");
            if (login_username.length < 1) {
                alert("用户名不能为空！请重新填入！");
                login_username.focus();
                return false;
            } else if (login_password.value < 1) {
                alert("密码不能为空！请重新填入！");
                login_password.focus();
                return false;
            }
            return true;
        }
        function focusOnLogin() {
            const login_username = document.getElementById("uName");
            login_username.focus();
        }
    </script>
</head>
<body onload="focusOnLogin()">
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
            <%--显示错误--%>
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
    <%--左侧新闻--%>
    <div class="sidebar">
        <h1><img src="${pageContext.request.contextPath}/images/title_1.gif" alt="国内新闻"/></h1>
        <div class="side_list">
            <ul>
                <c:forEach items="${requestScope.domesticNews}" var="s">
                    <li><a href="${pageContext.request.contextPath}/readNews?newsId=${s.id}"><b>${s.title}</b></a></li>
                </c:forEach>
            </ul>
        </div>
        <h1><img src="${pageContext.request.contextPath}/images/title_2.gif" alt="国际新闻"/></h1>
        <div class="side_list">
            <ul>
                <c:forEach items="${requestScope.internationalNews}" var="s">
                    <li><a href="${pageContext.request.contextPath}/readNews?newsId=${s.id}"><b>${s.title}</b></a></li>
                </c:forEach>
            </ul>
        </div>
        <h1><img src="${pageContext.request.contextPath}/images/title_3.gif" alt="娱乐新闻"/></h1>
        <div class="side_list">
            <ul>
                <c:forEach items="${requestScope.entertainmentNews}" var="s">
                    <li><a href="${pageContext.request.contextPath}/readNews?newsId=${s.id}"><b>${s.title}</b></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="main">
        <div class="class_type">
            <img src="${pageContext.request.contextPath}/images/class_type.gif" alt="新闻中心"/>
        </div>
        <%--获取主题--%>
        <div class="content" style="height: 820px;">
            <ul class="class_date">
                <li class='class_month'>
                    <%--<c:forEach items="${requestScope.themeList}" var="s" varStatus="vs">
                        <a href='${pageContext.request.contextPath}/index?themeId=${s.key}'><b>${s.value}</b></a>
                        <c:if test="${vs.index == 11}">
                            </li>
                            <li class='class_month'>
                        </c:if>
                    </c:forEach>--%>
            </ul>
            <%--获取新闻--%>
            <ul class="classList">
                <li><P>加载中...请稍后</P></li>
                <%--<c:forEach items="${requestScope.newsList}" var="s">
                    <li>
                        <a href="${pageContext.request.contextPath}/readNews?newsId=${s.id}">${s.title}</a>
                        <span>${s.date}</span>
                    </li>
                </c:forEach>--%>
                <%--<p align="right"> 当前页数:[
                    <label>
                        <INPUT id="goto" type="text" VALUE="${requestScope.pages+1}" style="width: 20px"
                            oninput="value=value.replace(/[^\d]/g,'')">
                    </label>/${requestScope.totalPage}]
                    <c:if test="${requestScope.pages>0}">
                        <a href="${pageContext.request.contextPath}/index?themeId=${requestScope.sub}">首页</a>
                        <a href="${pageContext.request.contextPath}/index?themeId=${requestScope.sub}&pages=${requestScope.pages-1}">上一页</a>
                    </c:if>
                    <c:if test="${requestScope.pages<requestScope.totalPage-1}">
                        <a href="${pageContext.request.contextPath}/index?themeId=${requestScope.sub}&pages=${requestScope.pages+1}">下一页</a>
                        <a href="${pageContext.request.contextPath}/index?themeId=${requestScope.sub}&pages=${requestScope.totalPage-1}">末页</a>
                    </c:if>
                </p>--%>
            </ul>
        </div>
        <%--右侧图文--%>
        <div class="picNews">
            <ul>
                <li><a href="#"><img src="${pageContext.request.contextPath}/images/Picture1.jpg" width="249" alt=""/> </a><a
                        href="${pageContext.request.contextPath}#">  幻想中穿越时空</a></li>
                <li><a href="#"><img src="${pageContext.request.contextPath}/images/Picture2.jpg" width="249" alt=""/> </a><a
                        href="${pageContext.request.contextPath}#">  国庆多变的发型</a></li>
                <li><a href="#"><img src="${pageContext.request.contextPath}/images/Picture3.jpg" width="248" alt=""/> </a><a
                        href="${pageContext.request.contextPath}#">  新技术照亮都市</a></li>
                <li><a href="#"><img src="${pageContext.request.contextPath}/images/Picture4.jpg" width="248" alt=""/> </a><a
                        href="${pageContext.request.contextPath}#">  群星闪耀红地毯</a></li>
            </ul>
        </div>
    </div>
</div>
<%@include file="index-elements/index_bottom.html" %>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript">
    let themeId;
    let pages = 0;
    const goto = $("#goto");
    const newsList = $(".classList");
    goto.keydown(function () {
        if (event.keyCode === 13) {
            let p = goto.val() - 1;
            p = p > ${requestScope.totalPage-1} ? ${requestScope.totalPage-1} : p;
            p = 0 > p ? 0 : p;
            location.href = "news.jsp?themeId=${requestScope.sub}&pages=" + p;
        }
    });
    //主题列表li：.class_month
    $(function () {
        $.ajax({
            "url": "/getTheme",
            "type": "get",
            "success": function (re) {
                let getRe = $.parseJSON(re);
                const themeShow = $(".class_month");
                let i = 0;
                for(let id in getRe){
                    themeShow.append("<a class='changeTheme' id='"+id.substring(1, id.length-1)+"' href='javascript:void(0)'><b>"+getRe[id]+"</b></a>");
                    if (i === 0) {
                        themeId = id.substring(1, id.length-1);
                    } else if (i === 11){
                        themeShow.append("</li><li class='class_month'>");
                    }
                    i++;
                }
                $(".changeTheme").click(function () {
                    themeId = $(this).attr("id");
                    pages = 0;
                    getNews();
                });
                getNews();
            }
        });
    });
    function getNews() {
        $.ajax({
            "url": "/getNews",
            "type": "get",
            "data": {"themeId":themeId,"pages":pages},
            "success": function (re) {
                newsList.children().remove();
                if (re === "error"){
                    newsList.append("<p>数据请求失败，请稍后重试</p>")
                } else {
                    let getRe = $.parseJSON(re);
                    for (let i = 0;i<getRe.news.length;i++) {
                        newsList.append("<li>\n" +
                            "<a href='${pageContext.request.contextPath}/readNews?newsId="+getRe.news[i].id+"'>" + getRe.news[i].title + "</a>\n" +
                            "<span>" + getRe.news[i].date + "</span>\n" +
                            "</li>")
                    }
                    let str = "";
                    str += "<p align='right'> 当前页数:[\n" +
                        "<label>\n" +
                        "<INPUT id='goto' type='text' VALUE=" + (getRe.pages+1) + " style='width: 20px'\n" +
                        "oninput='value=value.replace(/[^\\d]/g,'')'>\n" +
                        "</label>/" + getRe.totalPage + "]\n";
                    if (parseInt(getRe.pages) > 0){
                        str += "<a class='jump' id='0' href='javascript:void(0)'>首页</a>\n" +
                            "<a class='jump' id='" + (getRe.pages-1) + "' href='javascript:void(0)'>上一页</a>\n";
                    }
                    if (parseInt(getRe.pages) < parseInt(getRe.totalPage)-1){
                        str += "<a class='jump' id='" + (getRe.pages+1) + "' href='javascript:void(0)'>下一页</a>\n" +
                            "<a class='jump' id='" + (getRe.totalPage-1) + "' href='javascript:void(0)'>末页</a>\n";
                    }
                    str += "</p>";
                    newsList.append(str);
                    $(".jump").click(function () {
                        pages = $(this).attr("id");
                        getNews();
                    });
                }
            },
            "error": function () {
                //错误提示
                newsList.append("<p>数据请求失败，请稍后重试</p>")
            }
        });
    }
</script>
</body>
</html>
