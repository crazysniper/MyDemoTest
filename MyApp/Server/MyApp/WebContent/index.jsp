<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网页版</title>
<script type="text/javascript">
	function click1() {
		alert("网页的js不可以用click()");
		window.js.openDialog();
	}
	function msg(param) {
		alert("Hello world!" + param);
	}
	function msg2(param1, param2) {
		alert("Hello world!" + param1 + "----" + param2);
	}
</script>
</head>
<body>
	<input type="button" value="打开对话框" onclick="click1()"></input>
	<br>
	<p>
		<input type="button" value="Click me" onclick="msg('haha')" />
	<p>
		<a href="http://aiyuqi.tmall.com/?spm=a220o.1000855.1997427721.d4918089.gxht45&p=-1">百度</a>
	<p>
		<a href=<%=request.getContextPath() + "/call_tel:123456"%>>直接打电话</a>
	<p>
		<a href=<%=request.getContextPath() + "/dail_tel:"%>>调用打电话页面</a>
	<p>
		<a href=<%=request.getContextPath() + "/send_nocontent_tel:"%>>调用发送短信页面（不带内容）</a>
	<p>
		<a href=<%=request.getContextPath() + "/send_content_tel:带有内容"%>>调用发送短信页面（带内容,不带号码）</a>
	<p>
		<a href=<%=request.getContextPath() + "/send_content_num_tel:带有内容"%>>调用发送短信页面（带内容和号码）</a>
	<p>
</body>
</html>