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
	function msg() {
		alert("Hello world!");
	}
</script>
</head>
<body>
	<form>
		<input type="button" value="打开对话框" onclick="click1()"></input>
	</form>
	<form>
		<input type="button" value="Click me" onclick="msg()" />
	</form>
	<a href="http://aiyuqi.tmall.com/?spm=a220o.1000855.1997427721.d4918089.gxht45&p=-1">百度</a>
</body>
</html>