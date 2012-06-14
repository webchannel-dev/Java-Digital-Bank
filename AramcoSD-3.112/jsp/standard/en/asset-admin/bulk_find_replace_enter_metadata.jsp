<%@ page import="com.bright.assetbank.attribute.constant.AttributeConstants" %>
<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<%-- Create a Map containing the constants defined in AttributeConstants and UpdateConstants --%>
<un:useConstants var="attributeConstants" className="com.bright.assetbank.attribute.constant.AttributeConstants" />

<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled"/>
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<c:set var="showNativeLanguages" value="${supportMultiLanguage && !userprofile.currentLanguage.default}"/>

<head>
	
	<title><bright:cmsWrite identifier="title-bulk-find-replace" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<c:set var="assetForm" scope="request" value="${bulkUpdateForm}"/>
	
	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-bulkupdate"/>
	
	<script type="text/JavaScript">
		$j(function () {
  			$j('#textToFind').focus();
 		});
	</script>
</head>

<body id="importPage" >

	<%@include file="../inc/body_start.jsp"%>
	
	<div id="dataLookupCode"></div>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-bulk-update-run-find-replace" filter="false" /></h1> 
	<c:set var="numForUpdate" value="${userprofile.batchUpdateController.numberSelectedForUpdate}" />
	<bright:cmsWrite identifier="intro-bulk-find-replace" filter="false" replaceVariables="true" />
	
	<h2><bright:cmsWrite identifier="subhead-define-find-replace" filter="false"/></h2>
	
	<logic:equal name="bulkUpdateForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="bulkUpdateForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>		
		</div>
	</logic:equal>


	<html:form action="bulkFindAndReplaceStart" method="post" styleId="updateForm">
	
		<input type="hidden" name="maxlength_asset.description" value="4000">
		<input type="hidden" name="maxlengthmessage_asset.description" value="Asset description has exceeded its maximum length of 4000 characters"/>	
		
		<%-- Batch Releases --%>
		<table class="form" cellspacing="0" cellpadding="0" border="0">
			<html:hidden name="assetForm" property="asset.currentBatchReleaseState" />
			<c:if test="${batchReleasesEnabled}">
				<html:hidden name="assetForm" property="asset.currentBatchReleaseReleased" />
				<html:hidden name="assetForm" property="asset.currentBatchReleaseId" />
	
				<c:choose>
					<c:when test="${assetForm.asset.currentBatchReleaseState == 'approved' || assetForm.asset.currentBatchReleaseState == 'awaiting-approval' || assetForm.asset.currentBatchReleaseState == 'further-approval-required'}">
						<%-- just show the name of the currently selected release --%>
						<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager" methodName="getBatchRelease" id="release" argumentValue="${assetForm.asset.currentBatchReleaseId}"/>
						<tr>
							<th><label for="batchRelease"><bright:cmsWrite identifier="batch-release" filter="false" case="sentence"/>:</label> <span class="required">*</span></th>
							<td></td>
							<td><input type="hidden" name="asset.assignedBatchReleaseId" value="<bean:write name='release' property='id' />" /><bean:write name='release' property='name' /></td>
						</tr>
					</c:when>
					<c:otherwise>
						<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager" methodName="getBatchReleasesBeingCreated" id="releases"/>
				
						<%-- releases to select from --%>
						<c:if test="${not empty releases}">
							<tr>
								<th><label for="batchRelease"><bright:cmsWrite identifier="batch-release" filter="false" case="sentence"/>:</label> <span class="required">*</span></th>
								<td></td>
								<td>
									<select name="update_batchrelease" id="update_batchrelease" style="width:auto;">
										<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
										<option value="replace" <logic:equal name='assetForm' property="updateType(batchrelease)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
									</select>	
								</td>
								<td></td>
								<td>
									<html:select name="assetForm" property="asset.assignedBatchReleaseId" size="1">
										<option value="none">- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> -</option>
										<logic:iterate name="releases" id="release">
											<option value="<bean:write name='release' property='id' />" <c:if test="${(assetForm.asset.assignedBatchReleaseId > 0 && assetForm.asset.assignedBatchReleaseId == release.id) || (assetForm.asset.assignedBatchReleaseId <= 0 && assetForm.asset.currentBatchReleaseId == release.id)}">selected</c:if>><bean:write name='release' property='name' /></option>
										</logic:iterate>
									</html:select>
								</td>
							</tr>
						</c:if>
						<%-- no batch releases to select from --%>
						<c:if test="${empty releases}">				
							<tr><td colspan="3"><div class="error"><bright:cmsWrite identifier="noBatchReleasesBeingCreated" filter="false" /></div></td></tr>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:if>
		</table>
		
		
		<%-- Find and Replace --%>
		<table class="form" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<th> <label for="textToFind"><bright:cmsWrite identifier="label-find-what" filter="false" /></label> </th>
				<td> <html:text styleClass="text" styleId="textToFind" property="textToFind" size="16" maxlength="200"/> </td>
			</tr>
			<tr>
				<th> <label for="replace"><bright:cmsWrite identifier="label-replace-with" filter="false" /></label>	</th>
				<td> <html:text styleClass="text" styleId="textToReplace" property="textToReplace" size="16" maxlength="200"/> </td>
			</tr>
		</table>
			
		<table class="form" cellspacing="0" cellpadding="0" border="0">	
			<tr>
				<th> <label class="checkbox"><bright:cmsWrite identifier="label-match-case" filter="false" />?</label> </th>
				<td> <html:checkbox name="assetForm" styleClass="checkbox" property="caseSensitive" /> </td>
			</tr>
			<tr>
				<th> <label class="checkbox"><bright:cmsWrite identifier="label-match-whole-word" filter="false" />?</label> </th>
				<td> <html:checkbox name="assetForm" styleClass="checkbox" property="matchWholeWord" /> </td>
			</tr>
		</table>
		
		<br/>
		<br/>
		
		<h2><bright:cmsWrite identifier="subhead-select-attributes-find-replace" filter="false"/></h2>
		
		<table class="form" cellspacing="0" cellpadding="0" border="0">
			
			<%-- Go through all the attributes: --%>
			<logic:iterate name="assetForm" property="assetAttributes" id="attribute">
			<c:if test="${!attribute.hidden}">
			
				<%-- User-defined attributes --%>
				<c:if test="${empty attribute.fieldName && attribute.id != attributeConstants.k_lAttributeId_SensitivityNotes}">
					<c:choose>
						<c:when test="${attribute.isVisible}">
							<c:choose>
								<%-- Text field or Text area--%>
								<c:when test="${attribute.isTextfield || attribute.isTextarea}">
									<tr>
										<th class="checkbox">
											<label for="${attributeConstants.k_sParam_AttributeId}${attribute.id}"><bean:write name="attribute" property="label" filter="false"/><logic:notEmpty name="attribute" property="label">:</logic:notEmpty> </label>
											<c:if test="${showNativeLanguages}">
												<c:set var="language" value="${userprofile.currentLanguage}"/>
													<span style="font-weight: normal;"> 
														<c:if test="${!showNativeLanguages || empty language.nativeName}">
															(<bean:write name="language" property="name"/>)
														</c:if>
														<c:if test="${showNativeLanguages && not empty language.nativeName}">
															(<bean:write name="language" property="nativeName" filter="false"/>)
														</c:if>
													</span>
											</c:if>
										</th>
										<td>
											<input type="checkbox" class="checkbox" name="${attributeConstants.k_sParam_AttributeId}${attribute.id}" value="${attribute.id}" 
											<logic:present parameter="${attributeConstants.k_sParam_AttributeId}${attribute.id}"> checked </logic:present> />
										</td>
									</tr>
								</c:when>
							</c:choose>
						</c:when>
					</c:choose>
				</c:if>
				
			</c:if>
			</logic:iterate>
		</table>


		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" id="submitButton" value="Start Update" /> 
		<a href="viewManageBulkUpdate" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</html:form>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>