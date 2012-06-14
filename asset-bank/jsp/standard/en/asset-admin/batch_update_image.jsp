<%@include file="../inc/doctype_html.jsp"%>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Chris Preager		24-May-2005		Imported from Image Manager
	 d2		Matt Stevenson		05-Oct-2005		Added delete button
	 d3      Ben Browning   	14-Feb-2006    HTML/CSS tidy up
	 d4		Matt Woollard		03-Oct-2008		Added workflow transitions
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="canViewFullSize" settingName="public-can-view-full-size"/>
<bright:applicationSetting id="showPreview" settingName="show-audio-preview-in-page"/>
<bright:applicationSetting id="previewDownloadNotEmbed" settingName="audio-preview-download-not-embed"/>
<bright:applicationSetting id="showFlashVideoOnViewDetails" settingName="show-flash-video-on-view-details"/>
<bright:applicationSetting id="canEditAssetVersions" settingName="can-edit-asset-versions"/>
<c:set var="versionCheck" value="${batchAssetForm.asset.currentVersionId<=0 || canEditAssetVersions}" />

<head>
	
	<title><bright:cmsWrite identifier="title-batch-update" filter="false"/></title> 
	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	<script src="../js/category.js" type="text/javascript"></script>
	
	<script src="../js/keywordChooser.js" type="text/javascript"></script>
	<script src="../js/asset-upload.js" type="text/javascript"></script>
	<script src="../js/agreements.js" type="text/javascript"></script>
	<script type="text/javascript" src="../js/brwsniff.js"></script>
	<script type="text/javascript">
	<!-- 

		function savePressed()
		{
			document.getElementById('savingDiv').style.display="block";
		}
		function showMessages()
		{
			document.getElementById('approvalMessages').style.display="block";
		}
		function hideMessages()
		{
			document.getElementById('approvalMessages').style.display="none";
		}
		
		
		//Set up global variables for any map attributes
		var sLat = "";
		var wLng = "";
		var nLat = "";
		var eLng = "";
		
		var $whichAttribute = ""; 			//This is a jquery element that acts as a referenece for which attribute we are dealing with
		
		$j(function(){
			popupMapInit();
			initDatePicker();
		})
	
	//-->
	</script>

	<bean:define id="assetForm" name="batchAssetForm"/>

	<%-- Set up category javascript --%>
	<c:set var="ctrlIsCheckboxControl" value="0" scope="request"/>
	<%@include file="../category/inc_asset_category_head_js.jsp"%>
	
	<c:choose>
		<c:when test="${userprofile.batchUpdateController.batchType == 'UNSUBMITTED'}">
			<bean:define id="section" value="view-unsubmitted"/>
			<bean:define id="bodyid" value="importPage"/>
		</c:when>
		<c:when test="${userprofile.batchUpdateController.batchType == 'APPROVAL'}">
			<bean:define id="section" value="asset-approval"/>
			<bean:define id="bodyid" value="workflow"/>
		</c:when>
		<c:when test="${userprofile.batchUpdateController.batchType == 'OWNER'}">
			<bean:define id="section" value="view-submitted"/>
			<bean:define id="bodyid" value="importPage"/>
		</c:when>
		<c:otherwise>
			<bean:define id="section" value="batch"/>
			<bean:define id="bodyid" value="batchUpdatePage"/>
		</c:otherwise>
	</c:choose>
	
	<bean:define id="helpsection" value="batch-batchupdate"/>
		
</head>

<body id="<c:out value='${bodyid}' />" onload="<c:if test='${batchAssetForm.areCategoriesVisible}'>setDescSelectedCategories();</c:if> <c:if test='${batchAssetForm.areAccessLevelsVisible}'>setPermSelectedCategories();</c:if> setCatIdsFields(); showHideAgreementType(); syncAgreementPreviewButton(); <c:if test='${batchAssetForm.approvalMode}'>hideMessages();</c:if>">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline">
		<c:choose><c:when test="${userprofile.batchUpdateController.workflow != null}"><bean:define id="workflowName" name="userprofile" property="batchUpdateController.workflow.description"/><bright:cmsWrite identifier="heading-batch-approval-update" filter="false" replaceVariables="true"/>:</c:when><c:otherwise><bright:cmsWrite identifier="heading-batch-update" filter="false" />:</c:otherwise></c:choose><c:choose>
			<c:when test="${batchAssetForm.numberLeftInBatch>0}">
				<c:out value="${batchAssetForm.numberLeftInBatch}" /> <bright:cmsWrite identifier="heading-left-batch" filter="false" />
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="heading-last-batch" filter="false" />
			</c:otherwise>
		</c:choose>
	</h1> 
	
	<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.batchUpdate.existLockedAssetsInBatch}">
		<div class="warning">
			<c:set var="numLockedMatches" value="${userprofile.batchUpdateController.batchUpdate.numLockedAssetsInBatch}" />
			<bright:cmsWrite identifier="assetsLockedWarning" replaceVariables="true" filter="false"/>
		</div>
	</c:if>
	
	<logic:equal name="batchAssetForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="batchAssetForm" property="errors" id="errorText">
				<bright:writeError name="errorText" /><br />
			</logic:iterate>		
			<c:if test="${batchAssetForm.file!=null && not empty batchAssetForm.file.fileName}">
				<br/>Please note: for browser security reasons you will need to browse to your file again (if you changed it).
			</c:if>
		</div>
	</logic:equal>

	
	<c:if test="${batchAssetForm.asset.typeId!=2}">
		<c:set var="resultImgClass" value="icon"/>
	</c:if>
	<c:if test="${batchAssetForm.asset.typeId==2}">
		<c:set var="resultImgClass" value="image"/>
	</c:if>

	<c:if test="${batchAssetForm.asset.isVideo}">
		<bright:refDataList componentName="VideoPreviewManager" methodName="isPreviewBeingGenerated" argumentValue="${batchAssetForm.asset.id}" id="beingGenerated"/>
		<c:if test="${beingGenerated}">
			<div class="info"><bright:cmsWrite identifier="snippet-preview-being-generated"></bright:cmsWrite></div>
		</c:if>
	</c:if>
		
	<div class="clearfix">

		<c:if test="${!versionCheck}">
			<div class="warning">This asset cannot be saved because there is a more recent version of it that is currently unnapproved. Please click 'Skip' to move to the next asset.</div>
		</c:if>

			<logic:notEmpty name="batchAssetForm" property="asset.displayPreviewImageFile.path">
				<bean:define id="resultImgClass" value="image floatLeft"/>
				<bean:define id="asset" name="batchAssetForm" property="asset"/>
				<bean:define id="ignoreCheckRestrict" value="yes"/>
				<bean:define id="overideLargeImageView" value="true"/>
				<%@include file="../inc/view_preview.jsp"%>
				<%-- START Video keywords block --%>	    
	   			<c:if test="${assetForm.asset.isVideo && assetForm.asset.class.simpleName == 'VideoAsset' && useVideoKeywords}">
	   				<c:if test="${not empty assetForm.asset.videoKeywords}">
	   					<%@include file="../public/inc_video_keywords_panel.jsp"%>
	   				</c:if>
	   				<c:if test="${empty assetForm.asset.videoKeywords}">
	   					<br/>
	   				</c:if>
	    		</c:if>
	   			<%-- END Video keywords block --%>
	   			<%-- START Edit Video Keywords button --%>				
				<c:if test="${assetForm.userCanUpdateAsset && assetForm.asset.class.simpleName == 'VideoAsset' && useVideoKeywords && assetForm.asset.hasFLVMetaData}">
					<c:set var="vkUpdate" value="button-add"/>
					<c:if test="${not empty assetForm.asset.videoKeywords}">
						<c:set var="vkUpdate" value="button-edit"/>
					</c:if>
						<form name="updateForm" action="../action/viewUpdateVideoKeywords" method="get">
							<input type="hidden" name="returnUrl" value="viewUpdateNextImage?resume=true" />
							<input type="hidden" name="expectedAssetId" value="<c:out value='${assetForm.asset.id}' />" />
							<input id="editVideoKeywordsButton" class="button" type="submit" value="<bright:cmsWrite identifier="${vkUpdate}" filter="false" /> <bright:cmsWrite identifier="video" filter="false" /> <bright:cmsWrite identifier="keyword-nodes" filter="false" />" />
							<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>" />
						</form>			
				</c:if>
				<%-- END Edit Video Keywords button --%>
				<p/>	
			</logic:notEmpty>
			<logic:empty name="batchAssetForm" property="asset.displayPreviewImageFile.path">
				<img class="floatLeft <bean:write name='resultImgClass'/>" src="../servlet/display?file=<bean:write name='batchAssetForm' property='thumbnailUrl'/>" alt="Image preview" valign="top" />
			</logic:empty>


		<div class="actions rtlmargin">
			<h2 style="margin-bottom:8px;"><bean:write name="batchAssetForm" property="asset.name"/></h2>
			<p>
				<bright:cmsWrite identifier="label-id" filter="false"/> <bean:write name="batchAssetForm" property="asset.id" /><br /> 
				<bright:cmsWrite identifier="label-added" filter="false"/><br />
			<bright:cmsWrite identifier="label-by" filter="false"/> <bean:write name="batchAssetForm" property="addedByUser"/><br />
			<bright:cmsWrite identifier="label-on" filter="false"/> <bean:write name="batchAssetForm" property="addedDate"/></p>
			<c:if test="${batchAssetForm.asset.typeId==2}">
				<ul style="margin-top:0">
					<c:set var="originalImageWidth" value="${assetForm.width}"/>
					<c:set var="originalImageHeight" value="${assetForm.height}"/>
					<%@include file="../inc/view_full_size_links.jsp"%>
				</ul>	
			</c:if>
			<c:if test="${batchAssetForm.asset.typeId==3}">
			    <logic:notEmpty name="batchAssetForm" property="asset.encryptedEmbeddedPreviewClipLocation">
					<bean:define id='assetForm' name='batchAssetForm'/>
					<ul style="margin-top:0">
						<%@include file="../public/inc_preview_link.jsp"%>
					</ul>
				</logic:notEmpty>
			</c:if>
			<c:if test="${batchAssetForm.asset.typeId==4}">
				<ul style="margin-top:0">
				<c:choose>
					<c:when test="${showPreview}">
						<li>
						<c:set var="file" scope="request" value="${batchAssetForm.asset.encryptedEmbeddedPreviewClipLocation}"/>
						<c:set var="contentType" scope="request" value="${batchAssetForm.asset.previewClipFormat.contentType}"/>
						<c:set var="floatFlashPlayerLeft" scope="request" value="true"/>
						<jsp:include flush="true" page="../inc/inc_audio_player.jsp"/>
						<br/><br/>
						</li>
					</c:when>
					<c:otherwise>
						<logic:notEmpty name="assetForm" property="asset.encryptedPreviewClipLocation">
							<bean:define id='assetForm' name='batchAssetForm'/>
								<%@include file="../inc/inc_audio_preview_link.jsp"%>
	    				</logic:notEmpty>
					</c:otherwise>
				</c:choose>
    				<li class="link">
    					<a class="view" href="../servlet/display?file=<bean:write name='batchAssetForm' property='encryptedFilePath'/>&contentType=<bean:write name='batchAssetForm' property='asset.format.contentType'/>&filename=<bean:write name='batchAssetForm' property='asset.name'/>.<bean:write name='batchAssetForm' property='asset.format.fileExtension'/>">open/download file</a>
    				</li>
				</ul>	
			</c:if>
		</div>	<!-- end of actions -->

		<c:if test="${versionCheck}">
			
			<div class="alignRight js-enabled-show" id="topSaveButton">
					<input type="button" class="button" value="<bright:cmsWrite identifier="button-save-continue" filter="false" />" onclick="savePressed(); $j('#updateForm').submit()" /> 
			</div>	

			</script>
			
			<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled" />
			<c:if test="${(userprofile.isAdmin || batchAssetForm.userCanDeleteAsset) && !batchAssetForm.approvalMode && !batchReleasesEnabled}">		
			<div style="padding-top:5px;" class="alignRight">
				<form action="deleteBatchImage" method="get">
					<input type="hidden" name="id" value="<bean:write name='batchAssetForm' property='asset.id'/>" />
					<input type="submit" name="deleteButton" class="button" value="<bright:cmsWrite identifier="button-delete2" filter="false" />" onclick="return (confirm('<bright:cmsWrite identifier="js-confirm-delete-asset" filter="false"/>'));" />
				</form>
			</div>
			</c:if>

		</c:if>

		<div style="padding-top:5px;" class="alignRight">
		<form name="skip" action="viewUpdateNextImage">
			<input type="submit" name="skipButton" class="button" value="<bright:cmsWrite identifier="button-skip" filter="false" />" />
		</form>
		</div>

	</div>	<!-- end of clearfix -->
		
	<html:form enctype="multipart/form-data" action="updateBatchImage" method="post" styleId="updateForm">
		<html:hidden name="batchAssetForm" property="asset.id"/>
		<html:hidden name="batchAssetForm" property="asset.addedByUser.id"/>
		<html:hidden name="batchAssetForm" property="thumbnailUrl"/>
		<html:hidden name="batchAssetForm" property="asset.typeId"/>
		<html:hidden name="batchAssetForm" property="width"/>
		<html:hidden name="batchAssetForm" property="height"/>
		<html:hidden name="batchAssetForm" property="addedByUser"/>
		<html:hidden name="batchAssetForm" property="addedDate"/>
		<html:hidden name="batchAssetForm" property="fileSizeInBytes"/>
		<html:hidden name="batchAssetForm" property="approvalMode"/>
		
		<c:if test="${batchAssetForm.approvalMode}">	
			<%@include file="inc_workflow_batch.jsp"%>
		</c:if>
		
		<c:if test="${batchAssetForm.unsubmittedMode}">	
			<%@include file="inc_workset_batch.jsp"%>
		</c:if>
		
		<br/>
		
		<c:set var="sIsImport" scope="request" value="false"/>
		<c:set var="assetForm" scope="request" value="${batchAssetForm}"/>
		<%@include file="inc_cat_extension_hiddens.jsp"%>
		<jsp:include page="inc_fields.jsp"/>
	
		<div class="hr"></div>
	
	
		<c:if test="${versionCheck}">
			<div style="text-align:right;">
				<input type="submit" name="saveButton" class="button" value="<bright:cmsWrite identifier="button-save-continue" filter="false" />" onclick="savePressed();" /> <br />
			</div>
		</c:if>
	
	</html:form>
	
	<c:if test="${(userprofile.isAdmin || batchAssetForm.userCanDeleteAsset) && !batchAssetForm.approvalMode && !batchReleasesEnabled && versionCheck}">			
		<div style="padding-top: 5px; text-align:right;">
		<form action="deleteBatchImage" method="get">
			<input type="hidden" name="id" value="<bean:write name='batchAssetForm' property='asset.id'/>" />
			<input type="submit" name="deleteButton" class="button" value="<bright:cmsWrite identifier="button-delete2" filter="false" />" onclick="return (confirm('<bright:cmsWrite identifier="js-confirm-delete-asset" filter="false"/>'));" />
		</form>
		</div>
	</c:if>	
	
	<table cellspacing="0" cellpadding="0" width="100%;" style="margin-top:5px;">
		<tr>
			<%--  If this is a workflow batch, then actually cancel it, else just go to the cancel action, in case the user wants to continue --%>	
			<c:choose>
				<c:when test="${userprofile.batchUpdateController.isApproval || userprofile.batchUpdateController.isUnsubmitted}">
					<bean:define id="cancelAction" value="../action/cancelWorkflowBatch"/>				
				</c:when>
				<c:otherwise>
					<logic:empty name="userprofile" property="batchUpdateController.cancelUrl">
						<bean:define id="cancelAction" value="../action/viewManageBatchUpdate"/>
					</logic:empty>
					<logic:notEmpty name="userprofile" property="batchUpdateController.cancelUrl">
						<bean:define id="cancelAction" name="userprofile" property="batchUpdateController.cancelUrl"/>
					</logic:notEmpty>				
				</c:otherwise>
			</c:choose>
			
			<td>
				<form name="cancel" action="<bean:write name='cancelAction'/>">
					<input type="submit" name="cancelButton" class="button" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" style="margin-left: 0;">
				</form>
			</td>
			<td class="alignRight">
				<form name="skip" action="viewUpdateNextImage">
					<input type="submit" name="skipButton" class="button" value="<bright:cmsWrite identifier="button-skip" filter="false" />">
				</form>
			</td>
		</tr>
	</table>
	
	<div id="savingDiv" style="text-align:center; margin: 10px; display:none;">
		<bright:cmsWrite identifier="snippet-please-wait-update" filter="false"/>
	</div>
	
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>