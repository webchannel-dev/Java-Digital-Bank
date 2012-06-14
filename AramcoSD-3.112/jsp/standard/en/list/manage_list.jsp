<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d14		Martin Wilson	14-Jun-2006		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<head>
	
	<title><bright:cmsWrite identifier="title-manage-content" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="pagetitle" value="Manage Content Area"/>
	<bean:define id="tabId" value="content"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	<div class="head"><a href="manageLists">&laquo; Back to Content Areas</a></div>
	<h2>Edit <bean:write name="listForm" property="list.name"/></h2>
	
		<logic:empty name="listForm" property="list.items">
			<p>There are no content items for this content area.</p>
		</logic:empty>
		<logic:notEmpty name="listForm" property="list.items">
			<table cellspacing="0" class="list"  summary="List of groups">
				<thead>
				<tr>
					<th>Title</th>
					<c:if test="${supportMultiLanguage}">
						<th>Language</th>
					</c:if>
					<th colspan="2">&nbsp;</th>
				</tr>
				</thead>
				<tbody>	
					<logic:iterate name="listForm" property="list.items" id="listItem">
						<tr <c:if test="${listItem.created}">class="disabled"</c:if>>
							<td><bean:write name="listItem" property="title"/></td>
							<c:if test="${supportMultiLanguage}">
								<td><bean:write name="listItem" property="language.name"/></th>
							</c:if>
							<td class="action">
							<c:choose>
								<c:when test="${not listItem.created}">
									[<a href="viewEditListItem?id=<bean:write name='listItem' property='identifier'/>&listOrder=2&languageId=<bean:write name='listItem' property='language.id'/>">edit</a>]
								</c:when>
								<c:otherwise>
									<span style="color: black">[<a href="viewEditListItem?id=<bean:write name='listItem' property='identifier'/>&listOrder=2&languageId=<bean:write name='listItem' property='language.id'/>">add</a>]</span>
								</c:otherwise>
							</c:choose>
							</td>
							<td class="action">
								<c:if test="${not listItem.cannotBeDeleted || not listItem.language.default}">
									<c:choose>
										<c:when test="${not listItem.created}">
											[<a href="deleteListItem?id=<bean:write name='listItem' property='identifier'/>&listid=<bean:write name='listForm' property='list.id'/>&languageId=<bean:write name='listItem' property='language.id'/>&listOrder=2" onclick="return confirm('Are you sure you want to delete this content item?');">X</a>]
										</c:when>
										<c:otherwise>
											[<span class="disabled"><strong>X</strong></span>]
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
							</tr>
					</logic:iterate>
				</tbody>
			</table>
		</logic:notEmpty>		
	<logic:notEqual name="listForm" property="list.cannotAddNew" value="true">
		<p><a href="viewEditListItem?listid=<bean:write name='listForm' property='list.id'/>">Add new item &raquo;</a>
	</logic:notEqual>
	
	<br />
	<p><a href="manageLists">&laquo; Back to Content Areas</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>