<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	05-Sep-2007		Created
	 d2		Matt Stevenson		15-Feb-2008		Modified to allow for hidden templates
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Manage Email templates</title> 
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
	<h2>Edit Email Templates</h2> 
	<c:if test="${!emailTemplateForm.hidden}">
		<p><a href="changeFromEmail">Change the 'from' address for all email templates</a></p>
	</c:if>
	<logic:notEmpty name="emailTemplateForm" property="emailTemplates">
		<table cellspacing="0" class="admin list"  summary="List of groups">
		<tr>
		<th>Title</th>
		<c:if test="${supportMultiLanguage}">
			<th>Language</th>
		</c:if>
		<th colspan="2">&nbsp;</th>
		</tr>
		<logic:iterate name="emailTemplateForm" property="emailTemplates" id="listItem">
			<tr>
			<td><bean:write name="listItem" property="code"/></td>
			<c:if test="${supportMultiLanguage}">
				<td><bean:write name="listItem" property="language.name"/></td>
			</c:if>
			<td class="action">
			[<a href="editEmailTemplate?textId=<bean:write name="listItem" property="textId"/>&typeId=1&languageId=<bean:write name="listItem" property="language.id"/>">edit</a>]&nbsp;<c:choose><c:when test="${emailTemplateForm.hidden}">[<a href="showEmailTemplate?textId=<bean:write name="listItem" property="textId"/>&typeId=1">show</a>]</c:when><c:otherwise>[<a href="hideEmailTemplate?textId=<bean:write name="listItem" property="textId"/>&typeId=1">hide</a>]</c:otherwise></c:choose>
			</td>
			</tr>
		</logic:iterate>
		</table>	
	</logic:notEmpty>
	<logic:empty name="emailTemplateForm" property="emailTemplates">
		<p>There are currently no <c:if test="${emailTemplateForm.hidden}">hidden </c:if>email templates.</p>
	</logic:empty>
	
	
	<br />
	<p><a href="manageLists">&laquo; Back to Content Areas</a></p>
	<c:choose>
		<c:when test="${emailTemplateForm.hidden}">
			<p><a href="manageEmailTemplates?typeId=1">View visible templates &raquo;</a></p>
		</c:when>
		<c:otherwise>
			<p><a href="manageHiddenEmailTemplates?typeId=1">View hidden templates &raquo;</a></p>
		</c:otherwise>
	</c:choose>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>