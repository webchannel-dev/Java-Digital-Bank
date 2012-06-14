<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		02-Feb-2011
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="title-batch-release-search" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="batchReleases"/>
	
	
	<c:set var="tabId" value="batchReleaseSearch" scope="request" />
	<script type="text/JavaScript">
		$j(function() {
			initDatePicker();
		});
	</script>
</head>

<body id="searchPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="../public/inc_search_tabs.jsp"%>

	<logic:present  name="batchReleaseSearchForm">
		<logic:equal name="batchReleaseSearchForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="batchReleaseSearchForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<div class="floatRight">
		
		<c:if test="${userprofile.canManageBatchReleases}">
			<c:if test="${runLastSearchSwitch}">&nbsp;|&nbsp;</c:if><a href="viewEditBatchRelease"><bright:cmsWrite identifier="link-add-new-batch-release" filter="false" /></a>
		</c:if>
	</div>

	<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
	<html:form action="batchReleaseSearch" method="post">
	
		<table class="form" cellspacing="0" cellpadding="0" border="0" summary="Form for advanced search">
			<tr>
				<th><bright:cmsWrite identifier="label-keywords" filter="false"/></th> 
				<td><html:text name="batchReleaseSearchForm" property="keywords" /></td>
			</tr>
			<tr>
				<th><bright:cmsWrite identifier="label-name" filter="false"/></th>
				<td><html:text name="batchReleaseSearchForm" property="name" /></td>
			</tr>
			<c:if test="${userprofile.canManageBatchReleases}">
			<tr>
				<th><bright:cmsWrite identifier="label-status" filter="false" /></th>
				<td> 
					<logic:iterate name='states' id='state'>
						<c:set var="stateId" value='state${state.name}' />
						<bean:define id="stateName" name="state" property="name" /><bean:define id="selectedState" name="batchReleaseSearchForm" property='<%= "stateSelected(" + stateName + ")" %>' />
						<input type="checkbox" name="selectedStates" value="<bean:write name='state' property='name' />" id="<c:out value='${stateId}' />" class="checkbox" <c:if test="${selectedState}">checked</c:if> /> <label for="<c:out value='${stateId}' />"><c:choose><c:when test="${state.name == 'released'}">Released</c:when><c:otherwise><bean:write name='state' property='description' /></c:otherwise></c:choose></label>
						<br />
					</logic:iterate>
				</td>
			</tr>
			</c:if>
			<tr>
				<th><bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager"  methodName="getBatchReleaseCreationUsers" id="users"/>
				<bright:cmsWrite identifier="label-created-by" filter="false"/></th>
				<td>
					<html:select name="batchReleaseSearchForm" property="createdByUserId">
						<option value="-1">[<bright:cmsWrite identifier='snippet-any' filter='false' />]</option>
						<logic:iterate name='users' id='user'>
							<option value="<bean:write name='user' property='id' />" <c:if test='${user.id == batchReleaseSearchForm.createdByUserId}'>selected</c:if>><bean:write name='user' property='username' /></option>
						</logic:iterate>
					</html:select>
				</td>
			</tr>
			<tr>
				<th><bright:cmsWrite identifier="label-created-date" filter="false"/></th>
				<td>
					<html:text size="17" maxlength="20" styleClass="date" styleId="startDate_creationDateLower" name="batchReleaseSearchForm" property="creationDateLower" />&nbsp; <bright:cmsWrite identifier="label-to" case="lower" /> <html:text size="17" maxlength="20" styleClass="date" styleId="endDate_creationDateUpper" name="batchReleaseSearchForm" property="creationDateUpper" />&nbsp;
				</td>
			</tr>
			<tr>
				<th><bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager"  methodName="getBatchReleaseApprovalUsers" id="users"/>
				<bright:cmsWrite identifier="label-approval-user" filter="false"/></th>
				<td>
					<html:select name="batchReleaseSearchForm" property="approvalUserId">
						<option value="-1">[<bright:cmsWrite identifier='snippet-any' filter='false' />]</option>
						<logic:iterate name='users' id='user'>
							<option value="<bean:write name='user' property='id' />" <c:if test='${user.id == batchReleaseSearchForm.approvalUserId}'>selected</c:if>><bean:write name='user' property='username' /></option>
						</logic:iterate>
					</html:select>
				</td>
			</tr>
			<tr>
				<th><bright:cmsWrite identifier="label-release-date" filter="false"/></th>
				<td>
					<html:text size="17" maxlength="20" styleClass="date" styleId="startDate_releaseDateLower" name="batchReleaseSearchForm" property="releaseDateLower" />&nbsp; <bright:cmsWrite identifier="label-to" case="lower" /> <html:text size="17" maxlength="20" styleClass="date" styleId="endDate_releaseDateUpper" name="batchReleaseSearchForm" property="releaseDateUpper" />&nbsp;<br />
				</td>
			</tr>	
		</table>

		<div class="hr"></div>

		<input type="submit" name="submit" value="<bright:cmsWrite identifier='button-search' filter='false'/>" class="button flush floated" />
		
		<bean:parameter id="refining" name="refineSearch" value="0" />
		<c:set var="runLastSearchSwitch" value="${userprofile.lastBatchReleaseSearch != null && refining < 1}" />
		<c:if test="${runLastSearchSwitch}">
			<a href="batchReleaseSearch?cachedCriteria=1" class="cancelLink"><bright:cmsWrite identifier="link-run-last-search" filter="false" /></a>
		</c:if>
		
	</html:form>

	<div class="clearing"></div>
	
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>