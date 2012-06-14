<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard		03-Apr-2008		Created.
	 d2		Matt Woollard		02-May-2008		Improvement to table layout
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<bright:applicationSetting id="displayDatetimeFormat" settingName="display-datetime-format"/>

<head>
	<title><bright:cmsWrite identifier="title-audit-info" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="view_asset"/>	
	<script type="text/javascript" src="../js/popup.js"></script>
</head>

<body id="popup">

<div class="copy">
	
	<c:set var="assetTitle" value="${assetAuditForm.assetTitle}" />
	<h2><bright:cmsWrite identifier="subhead-audit-info" filter="false" replaceVariables="true" /></h2>
	<table class="usage" cellspacing="0" cellpadding="0" border="0" style="margin:0;">
		<tr>	
			<th class="left"><bright:cmsWrite identifier="label-date-time" filter="false"/></th>
			<logic:equal name="userprofile" property="isAdmin" value="true">
			<th><bright:cmsWrite identifier="label-username" filter="false"/></th>
			</logic:equal>
			<th><bright:cmsWrite identifier="label-ip-address" filter="false"/></th>
			<th><bright:cmsWrite identifier="label-access-type" filter="false"/></th>
		</tr>
		
		<logic:iterate name="assetAuditForm" property="assetAuditLog" id="entry">
			
			<tr>
				<td class="left">	
					<bean:write name="entry" property="date" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/>
				</td>
				<td>	
					<bean:write name="entry" property="username"/>
				</td>
				<td>	
					<c:choose>
						<c:when test="${entry.ipAddress == null}">
							N/A
						</c:when>
						
						<c:otherwise>
							<bean:write name="entry" property="ipAddress"/>&nbsp;
						</c:otherwise>
					</c:choose>						
				</td>
				<td>			
					<c:choose>
						<c:when test="${entry.type == 'Modified'}">
							<a href="viewAssetAudit?xmlLog=<bean:write name="entry" property="id"/>" target="_blank"><bean:write name="entry" property="type"/></a>
						</c:when>
						
						<c:otherwise>
							<bean:write name="entry" property="type"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>

		</logic:iterate>

			
	<logic:empty name="assetAuditForm" property="assetAuditLog">
		<td colspan="4" class="left">
			No transactions recorded
		</td>
	</logic:empty>
	
	</table>		
	
	<br />
	<div style="text-align:right;">
		<script type="text/javascript">
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
		</script>
	</div>
		
</div>

</body>
</html>