<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/import.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="/include/meta.jsp"%>
	<meta name="keywords" content="" />
	<meta name="Description" content="" />
	<title>用户管理</title>
	<%@ include file="/include/css.jsp"%>
	
	<style type="text/css">
		.userManage {
			font-family:verdana;
			font-size:14px;
			color:#fff;
			width:100%;
			table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
		}
		.userManage a {
			text-decoration:none;
			color:#fff;
		}
		.userManage th {
			background-color:#8577d8;
			word-break:keep-all;/* 不换行 */
			white-space:nowrap;/* 不换行 */
			overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
			text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
		}
		.userManage td {
			background-color:#8577d8;
			word-break:keep-all;/* 不换行 */
			white-space:nowrap;/* 不换行 */
			overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
			text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
		}
		.userManage tr:hover {
			background-color:#8577d8;
		}
	</style>
</head>

<body>
<div class="container">
	<%@ include file="/include/autoShow.jsp"%>
	<div class="row mainContent">
		<div class="col-md-2 col-sm-3 col-xs-1 leftNavigationBar">
			<%@ include file="/include/leftNavigationBar.jsp"%>
		</div>
		<div class="col-md-10 col-sm-9 col-xs-5 content">
<table border="0" cellspacing="1" class="userManage">
	<thead>
		<tr>
			<th width="5%">序号</th>
			<th width="10%">别名</th>
			<th width="10%">角色</th>
			<th width="10%">区域</th>
			<th width="5%">状态</th>
			<th>描述</th>
			<th width="21%">最后登陆时间</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${dataContainer.dataList}" var="user" varStatus="status">
		<tr id="<c:out value="${user.id}"/>">
			<td><c:out value="${status.index + dataContainer.offset}"/></td>
			<td><c:out value="${user.alias}"/></td>
			<td><c:out value="角色"/></td>
			<td><c:out value="区域"/></td>
			<td><c:out value="${user.status}"/></td>
			<td><c:out value="${user.description}"/></td>
			<td><fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
