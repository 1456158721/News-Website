<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>提交新闻--管理后台</title>
    <link href="<%=request.getContextPath()%>/css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="../newsPages/console_element/top.jsp" %>
<div id="main">
    <%@include file="../newsPages/console_element/left.html" %>
    <div id="opt_area">
        <h1 id="opt_type"> 添加新闻 </h1>
        <div>
            <form id="submitForm" action="${pageContext.request.contextPath}/addNews" method="post" name="myForm"
                  enctype="multipart/form-data">
                <table border="0" cellpadding="0" cellspacing="0" align="center" width="530">
                    <label>
                        <input name="id" type="text" maxlength="0"
                               style="display: none;" value="${requestScope.strId}" readonly>
                        <input id="submitPath" name="submitPath" type="text" maxlength="0"
                               style="display: none;" value="formal" readonly>
                    </label>
                    <tr>
                        <td>
                            <label>主题&emsp;</label>
                        </td>
                        <td>
                            <label>
                                <select name="subject" style="margin-bottom: 0;">
                                    <c:if test="${requestScope.themeList==null}">
                                        <option value="null">未添加任何主题，请添加后再试</option>
                                    </c:if>
                                    <c:if test="${requestScope.themeList!=null}">
                                        <c:forEach items="${requestScope.themeList}" var="s">
                                            <option value='${s.key}'
                                                    <c:if test="${requestScope.getSubject!=null && requestScope.getSubject.equals(s.key)}">selected</c:if> >${s.value}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td width="50" height="35">标题&emsp;</td>
                        <td>
                            <label>
                                <input id="tips" name="title" type="text" maxlength="30"
                                       style="margin-bottom: 0;margin-top: 10px;"
                                       value="${requestScope.getTitle}">
                                <span style="color: red"></span>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td width="50" height="35">作者&emsp;</td>
                        <td>
                            <label>
                                <input name="author" type="text" maxlength="10"
                                       style="margin-bottom: 0;margin-top: 10px;"
                                       value="${requestScope.getAuthor}">
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td width="50" height="35">摘要&emsp;</td>
                        <td>
                            <label>
                                <textarea required rows="3" cols="3" name="abstract" maxlength="100"
                                          style="height: 50px;width: 169px;margin-top: 10px;
                                          resize: none;">${requestScope.getAbstract}</textarea>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td width="50" height="35">内容&emsp;</td>
                        <td>
                            <label>
                                <textarea required rows="10" cols="10" name="content"
                                          style="height: 150px;width: 169px;margin-top: 10px;
                                          resize:none;">${requestScope.getContent}</textarea>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td width="60" height="35">上传图片&emsp;</td>
                        <td>
                            <label>
                                <input id="upload-input"
                                       style="top: 0; bottom: 0; left: 0;right: 0; margin-top: 10px;"
                                       type="file"
                                       accept="image/gif, image/jpg, image/jpeg"
                                       onchange="imgChange();"/>
                            </label>
                            <%--submit没有传--%>
                            <c:if test="${requestScope.submit != null}">
                                <c:if test="${requestScope.submit == 0}">
                                    <p style="color: green;display: inline"><img src="<%=request.getContextPath()%>/images/li_ok.gif" alt=""/>上传成功</p>
                                </c:if>
                                <c:if test="${requestScope.submit == 1}">
                                    <p style="color: red;display: inline"><img src="<%=request.getContextPath()%>/images/li_err.gif" alt=""/>图片格式异常</p>
                                </c:if>
                                <c:if test="${requestScope.submit == 2}">
                                    <p style="color: red;display: inline"><img src="<%=request.getContextPath()%>/images/li_err.gif" alt=""/>图片不能大于5MB</p>
                                </c:if>
                                <c:if test="${requestScope.submit == 3}">
                                    <p style="color: red;display: inline"><img src="<%=request.getContextPath()%>/images/li_err.gif" alt=""/>图片地址异常</p>
                                </c:if>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p></p>
                        </td>
                        <td>
                            <img id="imgShow" src="${requestScope.imgPath}"
                                 style="width: 169px;" alt="">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="提交"
                                   style="padding: 3px 10px 3px 10px;margin: 5px;
                                   border: black 2px solid;font-size: larger;">
                            <input type="reset" value="重置"
                                   style="padding: 3px 10px 3px 10px;margin: 5px;
                                   border: none;font-size: larger;">
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div id="footer">
        <%@include file="../newsPages/console_element/bottom.html" %>
    </div>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <script type="text/javascript">
        const imgShow = $("#imgShow");
        const form = $("#submitForm");
        const addImg = $("#upload-input");
        const tips = $("#tips");
        let flag = true;
        function imgChange() {
            imgShow.attr("src", window.URL.createObjectURL(document.getElementById("upload-input").files[0]));
            $("#submitPath").val("draft");
            addImg.attr("name", "file");
            form.submit();
        }
        tips.click(function () {
            tips.css("border","black 1px solid");
            tips.css("color","black");
            tips.next().text("");
        });
        tips.blur(function () {
            if (tips.val().length === 0){
                return;
            }
            $.post("/addNewsSelect",{title:tips.val()},function (re) {
                if (re === "标题正确"){
                    tips.next().css("color","green");
                    flag = true;
                } else{
                    tips.next().css("color","red");
                    tips.css("border","red 1px solid");
                    flag = false;
                }
                tips.next().text(re);
            });
        });
        form.submit(function () {
            if (!flag){
                return;
            }
            console.info("");
        })
    </script>
</body>
</html>
