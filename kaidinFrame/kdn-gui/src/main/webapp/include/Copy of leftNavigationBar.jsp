<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="leftNavigationBar">
<ul class="first">
	<c:forEach var="menu" items="${menus}">
		<li><a href="<c:out value="${menu.url}"/>"><c:out value="${menu.name}"/></a></li>
		<c:if test="${!empty menu.submenuList}">
			<ul class="first">
			<c:forEach var="subMenu" items="${menu.submenuList}">
				<li><a href="<c:out value="${subMenu.url}"/>"><c:out value="${subMenu.name}"/></a></li>
			</c:forEach>
			</ul>
		</c:if>
	</c:forEach>
</ul>
</div>