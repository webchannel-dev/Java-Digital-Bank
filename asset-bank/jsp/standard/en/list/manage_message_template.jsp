<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Jon Harvey			24-Jan-2011		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Manage Message templates</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="pagetitle" value="Manage Content Area"/>
	<bean:define id="tabId" value="content"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	<div class="head"><a href="manageMessageTemplateTypes">&laquo; Back to Message Template Groups</a></div>
	<h2>Edit Message Templates</h2> 
	<logic:notEmpty name="messageTemplateForm" property="messageTemplates">
		<table cellspacing="0" class="admin list"  summary="List of groups">
		<tr>
		<th>Title</th>
		<c:if test="${supportMultiLanguage}">
			<th>Language</th>
		</c:if>
		<th colspan="2">&nbsp;</th>
		</tr>
		<logic:iterate name="messageTemplateForm" property="messageTemplates" id="listItem">
			<tr>
			<td><bean:write name="listItem" property="code"/></td>
			<c:if test="${supportMultiLanguage}">
				<td><bean:write name="listItem" property="language.name"/></td>
			</c:if>
			<td class="action">
			[<a href="editMessageTemplate?textId=<bean:write name="listItem" property="textId"/>&typeId=<bean:write name="messageTemplateForm" property="typeId"/>&languageId=<bean:write name="listItem" property="language.id"/>">edit</a>]&nbsp;<c:choose><c:when test="${messageTemplateForm.hidden}">[<a href="showMessageTemplate?textId=<bean:write name="listItem" property="textId"/>&typeId=<bean:write name="messageTemplateForm" property="typeId"/>">show</a>]</c:when><c:otherwise>[<a href="hideMessageTemplate?textId=<bean:write name="listItem" property="textId"/>&typeId=<bean:write name="messageTemplateForm" property="typeId"/>">hide</a>]</c:otherwise></c:choose>
			</td>
			</tr>
		</logic:iterate>
		</table>	
	</logic:notEmpty>
	<logic:empty name="messageTemplateForm" property="messageTemplates">
		<p>There are currently no <c:if test="${messageTemplateForm.hidden}">hidden </c:if>message templates.</p>
	</logic:empty>
	
	
	<br />
	<p><a href="manageMessageTemplateTypes">&laquo; Back to Message Template Groups</a></p>
	<c:choose>
		<c:when test="${messageTemplateForm.hidden}">
			<p><a href="manageMessageTemplates?typeId=<bean:write name="messageTemplateForm" property="typeId"/>">View visible templates &raquo;</a></p>
		</c:when>
		<c:otherwise>
			<p><a href="manageHiddenMessageTemplates?typeId=<bean:write name="messageTemplateForm" property="typeId"/>">View hidden templates &raquo;</a></p>
		</c:otherwise>
	</c:choose>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>