<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard		09-Oct-2008     Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<bright:applicationSetting id="enableAudit" settingName="enable-audit-logging"/>

<head>
	
	<title><bright:cmsWrite identifier="title-usage-info" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<bean:define id="pagetitle" value="Usage Details"/>
	<bean:define id="helpsection" value="view_asset"/>	
</head>

<body id="popup">

<div class="copy">
	
	<c:set var="assetTitle" value="${assetUseForm.assetTitle}" />
	<h2><bright:cmsWrite identifier="subhead-asset-approval-messages" filter="false"/></h2>
	
	<table class="usage" cellspacing="0" cellpadding="0" border="0" style="margin:0;">
		<tr>
			<th class="left"><bright:cmsWrite identifier="label-username-nc" filter="false" /></th>	
			<th><bright:cmsWrite identifier="label-date-time" filter="false" /></th>
			<th><bright:cmsWrite identifier="label-transition" filter="false" /></th>
			<th><bright:cmsWrite identifier="label-message-nc" filter="false"/></th>
			<c:if test="${userprofile.isAdmin}">
				<th>Delete</th>
			</c:if>
		</tr>
		
		<logic:iterate name="workflowAuditForm" property="approvalAudit" id="entry">
			
			<tr>
				<td class="left">	
					<bean:write name="entry" property="name"/> (<bean:write name="entry" property="username"/>)
				</td>
				<td>	
					<bean:write name="entry" property="dateAdded" format="dd/MM/yyyy HH:mm:ss"/>
				</td>
				<td>	
					<bean:write name="entry" property="transition" />&nbsp;
				</td>
				<td>	
					<bean:write name="entry" property="message"/>&nbsp;
				</td>
				
				<c:if test="${userprofile.isAdmin}">
					<td>
						<center>[<a href="deleteWorkflowAuditEntry?id=<bean:write name="entry" property="id"/>&assetId=<bean:write name="entry" property="assetId"/>">X</a>]</center>
					</td>
				</c:if>
				
				
			</tr>

		</logic:iterate>
		
		<logic:empty name="workflowAuditForm" property="approvalAudit">
			<tr>
				<td colspan="5" class="left">
					<bright:cmsWrite identifier="snippet-no-workflow-audit" filter="false"/>
				</td>
			</tr>
		</logic:empty>
		
	</table>
	<br />

	<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button js-enabled-show" id="closeButton" onclick="window.close();" />

	<logic:notEmpty name="workflowAuditForm" property="approvalAudit">
		<c:if test="${userprofile.isAdmin}">
			<a href="deleteWorkflowAuditEntry?id=-1&assetId=<bean:write name="entry" property="assetId"/>">Delete all messages &raquo;</a>
		</c:if>
	</logic:notEmpty>
	
	

		
</div>

</body>
</html>