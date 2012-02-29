<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design
	 d3		Steve Bryan	   12-May-2005		Upgrade
	 d4		Matt Woollard	02-May-2008		Updated table layout
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<bright:applicationSetting id="enableAudit" settingName="enable-audit-logging"/>
<bright:applicationSetting id="displayDatetimeFormat" settingName="display-datetime-format"/>

<head>
	
	<title><bright:cmsWrite identifier="title-usage-info" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<bean:define id="pagetitle" value="Usage Details"/>
	<bean:define id="helpsection" value="view_asset"/>	
	<script type="text/javascript" src="../js/popup.js"></script>
</head>

<body id="popup">

<div class="copy">
	
	<c:set var="assetTitle" value="${assetUseForm.assetTitle}" />
	<h2><bright:cmsWrite identifier="subhead-usage-info" filter="false" replaceVariables="true" /></h2>
	
	<table class="usage" cellspacing="0" cellpadding="0" border="0" style="margin:0;">
		<tr>	
			<th class="left"><bright:cmsWrite identifier="label-date-time" filter="false"/></th>
			<logic:equal name="userprofile" property="isAdmin" value="true">
			<th>Name</th>
			</logic:equal>
			<th><bright:cmsWrite identifier="label-usage-description" filter="false"/></th>
			<th><bright:cmsWrite identifier="label-access-type" filter="false"/></th>
			<th><bright:cmsWrite identifier="snippet-other" case="mixed" filter="false"/>&nbsp;<bright:cmsWrite identifier="snippet-details" case="mixed" filter="false"/></th>
			
			<c:if test="${enableAudit}">
				<th><bright:cmsWrite identifier="label-ip-address" filter="false"/></th>
			</c:if>
		</tr>
		
		<logic:iterate name="assetUseForm" property="assetUsageLog" id="entry">
			
			<tr>
				<td class="left">	
					<bean:write name="entry" property="date" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/>
				</td>
				<logic:equal name="userprofile" property="isAdmin" value="true">
				<td>
					<c:if test="${entry.user == null}">
						Public User
					</c:if>
					<c:if test="${entry.user != null}">
						<bean:write name="entry" property="user.fullName"/>
					</c:if>
				</td>
				</logic:equal>
				<td>	
					<bean:write name="entry" property="description"/>
					<bean:size id="numSecondaryTypes" name="entry" property="secondaryUsageTypes" />
					
					<logic:notEmpty name="entry" property="secondaryUsageTypes">
						<br/>
					</logic:notEmpty>
					<logic:iterate name="entry" property="secondaryUsageTypes" id="secondaryUsageType" indexId="index">
						<bean:write name="secondaryUsageType"/><c:if test="${index+1!=numSecondaryTypes}">,</c:if>
					</logic:iterate>				
				</td>
				<td>	
					<bean:write name="entry" property="type"/>
				</td>
				<td>	
					<c:choose><c:when test="${entry.moreDetails != '' && entry.moreDetails != entry.description}"><bean:write name="entry" property="moreDetails"/></c:when><c:otherwise>n/a</c:otherwise></c:choose>
				</td>
				<c:if test="${enableAudit}">
					<td>	
						<bean:write name="entry" property="ipAddress"/>
					</td>
				</c:if>
			</tr>

		</logic:iterate>
		<logic:empty name="assetUseForm" property="assetUsageLog">
		<tr>
		<logic:equal name="userprofile" property="isAdmin" value="true">
			<td colspan="6" class="left">
		</logic:equal>
		<logic:equal name="userprofile" property="isAdmin" value="false">
			<td colspan="5" class="left">
		</logic:equal>
				<bright:cmsWrite identifier="snippet-item-not-dl" filter="false"/>
			</td>
		</tr>
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