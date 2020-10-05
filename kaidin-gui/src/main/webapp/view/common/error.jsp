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
		p {
			text-align:center;
			font-size:24px;
			font-weight:bold;
			color:#FF0000;
		}
	</style>
</head>

<body onload="showError('<c:out value="${errorMsg}"/>')">
<div class="container">
	<%@ include file="/include/autoShow.jsp"%>
	<div class="topOfMainContent"></div>
	<div class="row mainContent">
		<div class="col-md-2 col-sm-3 col-xs-1 leftNavigationBar">
			<%@ include file="/include/leftNavigationBar.jsp"%>
		</div>
		<div class="col-md-10 col-sm-9 col-xs-5 content">
			<div>
				<br />
				<br />
				<br />
				<br />
				<p>出错啦 o(╥﹏╥)o</p>
				<br />
				<br />
				<p>异常编码：<c:out value="${errCode}"/></p>
				<p>异常信息：<c:out value="${errMsg}"/></p>
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
