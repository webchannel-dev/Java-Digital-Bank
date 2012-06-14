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
	
	<div class="head"><a href="manageLists">&laquo; Back to Content Areas</a></div>
	<h2>Message Template Groups</h2> 
	<logic:notEmpty name="templateTypes">
	<p>Choose the group of Message Templates to edit.</p>
		<table cellspacing="0" class="admin list"  summary="List of groups">
		<tr>
			<th colspan="2">Group Name</th>
		</tr>
		<logic:iterate name="templateTypes" id="listItem">
			<tr>
			<td><bean:write name="listItem" property="name"/></td>
			<td class="action">
				[<a href="manageMessageTemplates?typeId=<bean:write name="listItem" property="id"/>">edit</a>]
			</td>
			</tr>
		</logic:iterate>
		</table>	
	</logic:notEmpty>
	<logic:empty name="templateTypes">
		<p>There are currently no message template types.</p>
	</logic:empty>
		
	<br />
	<p><a href="manageLists">&laquo; Back to Content Areas</a></p>	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>