<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bright:applicationSetting id="showThumbnails" settingName="show-thumbnails-in-asset-reports" />

<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Report"/>



<head>
	
	<title><bright:cmsWrite identifier="title-br-outstanding-acknowledgements-report" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
	<bean:define id="section" value="batchReleases"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="batch-releases" filter="false" case="mixed" /></h1> 
	
	<c:set var="tabId" value="br-reports" />
	
	<%@include file="inc_manage_tabs.jsp"%>

	<div class="head tabsAbove stripBelow">
		<h2><bright:cmsWrite identifier="tab-outstanding-acknowledgements" filter="false"/></h2>
		<p><bright:cmsWrite identifier="snippet-oustanding-acknowledgements-intro" filter="false" /></p>
	</div>
	<bean:parameter id="userId" name="userId" value="-1" />
	<bean:parameter id="recipientId" name="recipientId" value="-1" />
	<bean:parameter id="showAll" name="showAll" value="-1" />
			
	<div class="sortStrip">
		<form name="filterForm" action="viewBatchReleaseAcknowledgementReport" method="get">
			<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager"  methodName="getBatchReleaseCreationUsers" id="users"/>
			<label for="userId"><bright:cmsWrite identifier="label-owner" filter="false" />:</label>
			<select name="userId" id="userId" style="max-width: 22em">
				<option value="-1">[<bright:cmsWrite identifier='snippet-any' filter='false' />]</option>
				<logic:iterate name='users' id='user'>
					<option value="<bean:write name='user' property='id' />" <c:if test='${user.id == userId}'>selected</c:if>><bean:write name='user' property='forename' /> <bean:write name='user' property='surname' /> (<bean:write name='user' property='username' />)</option>
				</logic:iterate>
			</select>
			&nbsp; &nbsp;
			<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager"  methodName="getBatchReleaseAcknowledgementRecipients" id="users"/>
			<label for="recipientId"><bright:cmsWrite identifier="label-acknowledgement-recipient" filter="false" />:</label>
			<select name="recipientId" id="recipientId" style="max-width: 22em">
				<option value="-1">[<bright:cmsWrite identifier='snippet-any' filter='false' />]</option>
				<logic:iterate name='users' id='user'>
					<option value="<bean:write name='user' property='id' />" <c:if test='${user.id == recipientId}'>selected</c:if>><bean:write name='user' property='forename' /> <bean:write name='user' property='surname' /> (<bean:write name='user' property='username' />)</option>
				</logic:iterate>
			</select>
			&nbsp; &nbsp;
			<label><input type="checkbox" name="showAll" value="1" <c:if test="${showAll > 0}">checked</c:if>/> <bright:cmsWrite identifier="link-show-all" /></label>
			&nbsp; &nbsp;

		<input type="submit" class="button" name="submit" value="<bright:cmsWrite identifier="button-refresh-view" filter="false"/>" />

		</form>
	</div>						   


	<logic:empty name="report" property="reportEntries">
		<br />
		<p><bright:cmsWrite identifier="snippet-no-oustanding-acknowledgements" filter="false" /></p>
	</logic:empty>
	<logic:notEmpty name="report" property="reportEntries">

		<table cellspacing="0" class="report permissions" summary="Batch release report">		
			<tr>
				<th>
					<bright:cmsWrite identifier="label-name-nc" filter="false" />
				</th>
				<th>
					<bright:cmsWrite identifier="label-owner" filter="false" />
				</th>
				<th>
					<bright:cmsWrite identifier="label-release-date" filter="false" />
				</th>
				<th>
					<c:choose><c:when test="${showAll > 0}"><bright:cmsWrite identifier="subhead-acknowledgements" /></c:when><c:otherwise><bright:cmsWrite identifier="label-outstanding-acknowledgements" filter="false" /></c:otherwise></c:choose>
				</th>
			</tr>
			<logic:iterate name="report" property="reportEntries" id="reportEntry">
				<tr >
					<td><a href="viewBatchRelease?brId=<bean:write name='reportEntry' property='batchReleaseId' />&report=true"><bean:write name="reportEntry" property="batchReleaseName" /></a></td>
					<td><bean:write name="reportEntry" property="ownerName" /> (<bean:write name="reportEntry" property="ownerUsername" />)</td>
					<td><bean:write name="reportEntry" property="releaseDate" format="dd/MM/yyyy" /></td>
					<td>
						<ul class="userStatus">
						<logic:iterate name="reportEntry" property="acknowledgements" id="ack">
							<li <c:choose><c:when test="${ack.acknowledged}">class="passed"</c:when><c:otherwise>class="failed"</c:otherwise></c:choose>><bean:write name="ack" property="user.forename" /> <bean:write name="ack" property="user.surname" /><c:if test="${ack.orgUnitId > 0}"> (<a href="viewOrgUnitDetails?ouid=<c:out value='${ack.orgUnitId}' />"><bean:write name="ack" property="orgUnitName" /></a>)</c:if>  - <c:choose><c:when test="${ack.acknowledged}"><bright:cmsWrite identifier="snippet-acknowledged" /></c:when><c:otherwise><bright:cmsWrite identifier="snippet-not-yet-acknowledged" case="sentence" /></c:otherwise></c:choose></li>
						</logic:iterate>
					</td>
				</tr>
			</logic:iterate>

			<tr><td colspan="2">&nbsp;</td>
				<td><strong><bright:cmsWrite identifier="label-total" filter="false" /></strong></td>
				<td><strong><bean:write name='report' property='total' /></strong></td>
			</tr>
		</table>
	
	</logic:notEmpty>
	
	<div class="hr"></div>
	
	<p><a href="viewBatchReleaseReports"><bright:cmsWrite identifier="link-back-to-reports" filter="false" /></a>&nbsp;|&nbsp;<a href="downloadBatchReleaseReport?type=report&userId=<c:out value='${userId}' />&recipientId=<c:out value='${recipientId}' />&showAll=<c:out value='${showAll}' />">Download this report</a></p>	
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>