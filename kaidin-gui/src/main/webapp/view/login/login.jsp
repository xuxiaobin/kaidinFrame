<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<body onload="showError('<c:out value="${errorMsg}"/>')">
<div class="container">
	<%@ include file="/include/autoShow.jsp"%>
	<div class="topOfMainContent"></div>
	<div class="mainContent">
		<%@ include file="/include/leftNavigationBar.jsp"%>
		<div class="content">
			<div class="text">
				<br />
				<br />
				<br />
				<br />
				<p>欢迎管理员登录！</p>
				<br />
				<br />
				<form name="login" action="login.html?method=login" method="post" onSubmit="check();">
					<table border="1">
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
							<td colspan="2" rowspan="2"><img id="codeimg" src="captcha.html?method=getCaptchaImage" onClick="javascript:changeImg('codeimg', 'captcha.html?method=getCaptchaImage')"></td>
							<td ><a href="javascript:changeImg('codeimg', 'captcha.html?method=getCaptchaImage')">看不清楚?</a></td>
						</tr>
					</table>
				<p>
					<input type="reset" name="submit" value="重 置">&nbsp;&nbsp;
					<input type="submit" name="submit" value="登 录">
				</p>
				</form>
			</div>
		</div>
	</div>
	<div class="bottomOfMainContent"></div>
	<%@ include file="/include/foot.jsp"%>
</div>
<%@ include file="/include/js.jsp"%>
<script src="thirdParty/jquery/jquery.md5.js"></script>
<script src="js/check.js"></script>
</body>
</html>
