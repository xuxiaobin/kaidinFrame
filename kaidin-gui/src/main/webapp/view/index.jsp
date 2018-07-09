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
</head>

<body>
<div class="container">
	<%@ include file="/include/autoShow.jsp"%>
	<div class="row mainContent">
		<div class="col-md-2 col-sm-3 col-xs-1 leftNavigationBar">
			<%@ include file="/include/leftNavigationBar.jsp"%>
		</div>
		<div class="col-md-10 col-sm-9 col-xs-5 content">
			<div class="text">
				小斌的网站在持续更新中！
			</div>
		</div>
	</div>
	<object codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="0" height="0">
		<param name="movie" value="swf/dewplayer.swf?mp3=music/background.mp3&autostart=1&bgcolor=ffffff" />
		<param name="quality" value="high" />
		<param value="transparent" name="wmode" />
		<embed src="swf/dewplayer.swf?mp3=music/background.mp3&autostart=1&bgcolor=ffffff" width="1" height="1" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"></embed>
	</object>
	<a class="bshareDiv" href="http://www.bshare.cn/share">分享按钮</a><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#uuid=&amp;style=3&amp;fs=4&amp;textcolor=#000&amp;bgcolor=#DDD&amp;text=分享到"></script>
	<%@ include file="/include/foot.jsp"%>
</div>
<%@ include file="/include/js.jsp"%>
</body>
</html>
