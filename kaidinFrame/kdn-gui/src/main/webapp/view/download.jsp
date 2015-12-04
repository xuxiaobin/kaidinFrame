<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/import.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="/include/meta.jsp"%>
	<meta name="keywords" content="download 下载" />
	<meta name="Description" content="download" />
	<title>小斌的下载</title>
	<%@ include file="/include/css.jsp"%>
	<link href="css/paging.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.download {
			font-family:verdana;
			font-size:14px;
			color:#fff;
			width:100%;
			table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
		}
		.download a {
			text-decoration:none;
			color:#fff;
		}
		.download th {
			background-color:#8577d8;
			word-break:keep-all;/* 不换行 */
			white-space:nowrap;/* 不换行 */
			overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
			text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
		}
		.download tr:hover {
			background-color:#8577d8;
		}
	</style>
</head>

<body>
<div class="container">
	<%@ include file="/include/autoShow.jsp"%>
	<div class="mainContent">
		<%@ include file="/include/leftNavigationBar.jsp"%>
		<div class="content">
<table border="0" cellspacing="1" class="download">
	<thead>
		<tr>
			<th width="5%">序号</th>
			<th>文件名（点击下载）</th>
			<th width="20%">大小</th>
			<th width="22%">最后更新时间 ↓</th>
		</tr>
	</thead>
	<tbody>
<c:forEach items="${dataContainer.dataList}" var="downloadFile" varStatus="status">
	<tr>
		<td><c:out value="${status.index + dataContainer.offset}"/></td>
		<td><a href="<c:out value="${downloadFile.downloadPath}"/>"><c:out value="${downloadFile.name}"/></a></td>
		<td><c:out value="${downloadFile.size}"/></td>
		<td><fmt:formatDate value="${downloadFile.lastModified}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
</c:forEach>
	</tbody>
</table>
		</div>
	</div>
	<%@ include file="/include/foot.jsp"%>
</div>
<%@ include file="/include/js.jsp"%>
</body>
</html>
