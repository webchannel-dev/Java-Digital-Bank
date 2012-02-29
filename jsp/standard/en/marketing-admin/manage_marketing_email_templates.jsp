<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	05-Sep-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Manage Marketing Email Templates</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="marketing"/>
	<bean:define id="pagetitle" value="Marketing"/>
	<bean:define id="helpsection" value="marketing-email-templates"/>
	<bean:define id="tabId" value="emailTemplates"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../marketing-admin/inc_marketing_tabs.jsp"%>
	
	<h2>Marketing Email Templates</h2>
	
	<logic:empty name="emailTemplateForm" property="emailTemplates">
		<p>There are currently no marketing email templates.</p>
	</logic:empty>
	<logic:notEmpty name="emailTemplateForm" property="emailTemplates">
		<table cellspacing="0" class="admin list"  summary="List of groups">
		<tr>
		<th>Title</th>
		<th colspan="2">&nbsp;</th>
		</tr>
		
		<logic:iterate name="emailTemplateForm" property="emailTemplates" id="listItem">
			<tr>
			<td><bean:write name="listItem" property="code"/></td>
			<td class="action">
				[<a href="editMarketingEmailTemplate?textId=<bean:write name="listItem" property="textId"/>&typeId=2">edit</a>]
				[<a href="deleteMarketingEmailTemplate?textId=<bean:write name="listItem" property="textId"/>&typeId=2" onclick="return confirm('Are you sure you want to delete this marketing email template?');">delete</a>]
			</td>
			</tr>
		</logic:iterate>
		</table>	
	</logic:notEmpty>
	
	<div class="hr"></div>
	
	<a href="editMarketingEmailTemplate?typeId=2">Add Email Template &raquo;</a>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>