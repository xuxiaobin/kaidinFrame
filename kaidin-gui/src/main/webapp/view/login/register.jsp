<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/import.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="/include/meta.jsp"%>
	<meta name="Keywords" content="暂时没有关键字" />
	<meta name="Description" content="本网页是小斌的练手之作！哈哈！" />
	<title>小斌的个人主页</title>
	<%@ include file="/include/css.jsp"%>
	<style type="text/css">
		form input {
			width:70px;
			height:25px;
		}
		table input {
			width:150px;
			height:18px;
			font-size:15px;
		}
		table tr {
			width:300px;
			font-size:15px;
		}
		p {
			text-align:center;
			font-size:24px;
			font-weight:bold;
			color:#FF0000;
		}
		.error {
			visibility:hidden;
			font-size:15px;
			font-weight:normal;
			color:#FF0000;
		}
	</style>
</head>

<body onload="showError()">
<div class="container">
	<%@ include file="/include/autoShow.jsp"%>
	<div class="topOfMainContent"></div>
	<div class="row mainContent">
		<div class="col-md-2 col-sm-3 col-xs-1 leftNavigationBar">
			<%@ include file="/include/leftNavigationBar.jsp"%>
		</div>
		<div class="col-md-10 col-sm-9 col-xs-5 content">
			<div class="text">
				<br />
				<br />
				<p>注册！</p>
				<br />
				<br />
				<form name="login" action="login.html?method=login" method="post" onSubmit="check();">
					<table align="center" border="1">
						<tr>
							<td height="5">账 号：</td>
							<td height="5"><input type="text" id="loginName" name="loginName" onblur="javascript:isEmpty('loginName', 'nameError')"></td>
							<td height="5"><p id="nameError" class="error">账号不能为空</p></td>
						</tr>
						<tr>
							<td>密 码：</td>
							<td><input type="password" id="password" name="password" style="font-size:12px" onblur="javascript:isEmpty('password', 'passError')"></td>
							<td><p id="passError" class="error">密码不能为空</p></td>
						</tr>
						<tr>
							<td>验证码：</td>
							<td><input type="text" id="captcha" name="captcha" style="font-size:12px" onblur="javascript:isEmpty('captcha', 'codeError')"></td>
							<td><p id="codeError" class="error">验证码不能为空</p></td>
						</tr>
						<tr>
							<td colspan="2" rowspan="2"><img id="codeimg" src="login.html?method=getCaptcha" onClick="javascript:changeImg('codeimg', 'login.html?method=getCaptcha')"></td>
							<td ><a href="javascript:changeImg('codeimg', 'login.html?method=getCaptcha')">看不清楚?</a></td>
						</tr>
					</table>
				<p>
					<input type="reset" name="submit" value="重 置">&nbsp;&nbsp;
					<input type="submit" name="submit" value="注册">
				</p>
				</form>
			</div>
		</div>
	</div>
	<div class="bottomOfMainContent"></div>
	<%@ include file="/include/foot.jsp"%>
</div>
<%@ include file="/include/js.jsp"%>
<script src="js/check.js"></script>
<script>
	function showError() {
		var errorMsg = '<c:out value="${errorMsg}"/>';
		if ('' != errorMsg) {
			alert(errorMsg)
		}
	}
	//用window.onload调用myfun()不要括号 
	window.onload=showError;
</script>
</body>
</html>
