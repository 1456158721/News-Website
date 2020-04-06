<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>添加主题--管理后台</title>
    <link href="../css/admin.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <%@include file="console_element/top.jsp" %>
    <div id="main">
      <%@include file="console_element/left.html" %>
      <div id="opt_area">
        <h1 id="opt_type"> 添加主题： </h1>
        <form action="${pageContext.request.contextPath}/addTheme" method="post" onsubmit="return check()">
          <label>
            <input id="theme" name="theme" type="text">
            <input type="submit" value="提交">
          </label>
        </form>
      </div>
    </div>
    <div id="footer">
      <%@include file="console_element/bottom.html" %>
    </div>
  </body>
</html>
