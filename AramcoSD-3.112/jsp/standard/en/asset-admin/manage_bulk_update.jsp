<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		14-Jun-2005		Created.
	 d2		Ben Browning		17-Feb-2006		HTML/CSS tidy up
	 d3     Matt Woollard       17-Jul-2009     Added delete option
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="maxSizeForSelection" settingName="max-bulk-update-size-for-selection"/>
<bright:applicationSetting id="maxBatchUpdateResults" settingName="max-batch-update-results"/>
<bright:applicationSetting id="categoryExtensionAssetsEnabled" settingName="category-extension-assets-enabled"/>
<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled" />
					
<head>
	
	<title><bright:cmsWrite identifier="title-bulk-update" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-bulkupdate"/>
	<bean:define id="tabId" value="bulkupdate"/>
</head>

<body id="uploadPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<%-- Bulk update has both a controller and a queue! --%>
	
	<c:set var="bMoreBatch" value="false"/>
	<c:set var="bHasBulkUpdateController" value="false"/>
	<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.type == 'BATCHUPDATE'}">
		<c:if test="${userprofile.batchUpdateController.hasNext}">
			<c:set var="bMoreBatch" value="true"/>
		</c:if>
	</c:if>	
	<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.type == 'BULKUPDATE'}">
		<c:set var="bHasBulkUpdateController" value="true"/>
	</c:if>	

	<h1><bright:cmsWrite identifier="heading-update-assets" filter="false" case="mixed" /></h1> 

	<%@include file="inc_batch_tabs.jsp"%>
		
	<h2><bright:cmsWrite identifier="subhead-bulk-update" filter="false"/></h2>
	<bright:cmsWrite identifier="intro-bulk-update" filter="false"/>
	<c:if test="${categoryExtensionAssetsEnabled}">
		<bright:cmsWrite identifier="snippet-extension-asset-bulk-update" filter="false" />
	</c:if>

	<c:choose>
		<c:when test="${bHasBulkUpdateController}">
			<c:set var="numInBatch" value="${userprofile.batchUpdateController.numberInBatch}" />	
			<c:set var="numSelUpdate" value="${userprofile.batchUpdateController.numberSelectedForUpdate}" />	
			<c:set var="numUpdatePermissionDenied" value="${userprofile.batchUpdateController.numberUpdatePermissionDenied}" />
			<c:if test="${numUpdatePermissionDenied > 0}">
				<bright:cmsWrite identifier="copy-bulk-update-permissions-warning" filter="false" replaceVariables="true" />
			</c:if>
			<bright:cmsWrite identifier="copy-bulk-update-1" filter="false" replaceVariables="true" />
			
				
			<c:choose>
				<c:when test="${bulkUpdateStatusForm.inProgress}">
					<p><bright:cmsWrite identifier="snippet-bulk-executing" filter="false"/> <a href="../action/bulkUpdateViewStatus"><bright:cmsWrite identifier="link-view-status" filter="false"/></a></p>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${userprofile.batchUpdateController.numberInBatch le maxSizeForSelection}">
							<p>
								<a href="bulkUpdateViewBatch"><bright:cmsWrite identifier="link-select-items-bulk" filter="false" /></a>
							</p>		
						</c:when>
						<c:otherwise>
							<p><bright:cmsWrite identifier="snippet-batch-exceeds-size" filter="false" replaceVariables="true" /></p>
						</c:otherwise>						
					</c:choose>

					<p><a href="../action/bulkUpdateEnterMetadata"><bright:cmsWrite identifier="link-run-update-batch" filter="false"/></a></p>
					<p><a href="../action/bulkFindAndReplaceEnterMetadata"><bright:cmsWrite identifier="link-run-find-replace-batch" filter="false"/></a></p>
					<c:if test="${!batchReleasesEnabled}"><p><a href="../action/viewBulkDeleteAssets"><bright:cmsWrite identifier="link-bulk-delete-assets" filter="false"/></a></p></c:if>

					<p><a href="../action/cancelBulk"><bright:cmsWrite identifier="link-finish-batch" filter="false"/></a> <bright:cmsWrite identifier="snippet-so-items-available" filter="false"/></p>
						
					<c:if test="${!empty bulkUpdateStatusForm.messages}">	
						<p><bright:cmsWrite identifier="snippet-bulk-finished" filter="false"/> <a href="../action/bulkUpdateViewStatus"><bright:cmsWrite identifier="link-view-status" filter="false"/></a></p>
					</c:if>
					
				</c:otherwise>
			</c:choose>
							
		</c:when>
		<c:otherwise>
			<p><strong><bright:cmsWrite identifier="snippet-bulk-not-in-progress" filter="false"/></strong></p>

			<c:if test="${!empty bulkUpdateStatusForm.messages}">	
				<p><bright:cmsWrite identifier="snippet-bulk-finished" filter="false"/> <a href="../action/bulkUpdateViewStatus"><bright:cmsWrite identifier="link-view-status" filter="false"/></a></p>
			</c:if>
			<p><a href="../action/bulkUpdateViewSearch"><bright:cmsWrite identifier="link-start-new-bulk-update" filter="false"/></a></p>
		</c:otherwise>
	</c:choose>
	
	<br /><br />
	<div class="hr"></div>
	<p>
		<em><bright:cmsWrite identifier="snippet-batch-bulk-note" filter="false"/></em>
	</p>
		

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>