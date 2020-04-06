<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Welcome...</title>
</head>
<body>
<div style="width: 200px;height: 200px;border: #35c5c5 2px solid;margin: 10% auto;text-align: center;padding: 0;">
    <p style="margin: 28% auto;font-size: 20px;">跳转页面中</p>
    <p style="margin: -20% auto;">如果页面无任何反应请<a href="${pageContext.request.contextPath}/index"
                                               style="display: inherit;">点击跳转</a></p>
</div>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript">
    //location.href="index";
    const text = "跳转页面中";
    let j = 0;
    const textShow = $("div").children(":eq(0)");
    setInterval(function () {
        let setText = text;
        for (let i = 0; i <= j; i++) {
            textShow.text(setText);
            if (j === 4){
                setText = text;
                j = 0;
            } else {
                setText += "。";
                j++;
            }
        }
    },1000);
</script>
</body>
</html>
