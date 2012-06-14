<%@include file="../inc/doctype_html.jsp" %>
 
<!-- Developed by bright interactive www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Ben Browning	09-Feb-2006		Tidied up html
	 d3		Steve Bryan		28-Apr-2009		Do not show option for create copy if we are in workflow
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="assetAdminJavascriptFile" settingName="asset-admin-javascript-file"/>
<bright:applicationSetting id="canCreateAssetVersions" settingName="can-create-asset-versions"/>
<bright:applicationSetting id="showFlashVideoOnViewDetails" settingName="show-flash-video-on-view-details"/>
<bright:applicationSetting id="autoCompleteEnabled" settingName="auto-complete-enabled"/>
<bright:applicationSetting id="useVideoKeywords" settingName="use-video-keywords"/>
<bean:parameter name="copying" id="copying" value="0" />
<c:choose>
	<c:when test="${copying==1}">
		<c:set var="pagetitle"><bright:cmsWrite identifier="title-create-copy" filter="false" /></c:set>
		<c:set var="pageheading"><bright:cmsWrite identifier="heading-create-copy" filter="false" /></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="pagetitle"><bright:cmsWrite identifier="title-update" filter="false" /></c:set>
		<c:set var="pageheading"><bright:cmsWrite identifier="heading-update" filter="false" /></c:set>
	</c:otherwise>
</c:choose>

<c:if test="${not empty assetForm.asset.entity.name}">
	<c:set var="pagetitle"><c:out value="${pagetitle}"/> <bean:write name="assetForm" property="asset.entity.name" filter="false"/></c:set>
	<c:set var="pageheading"><c:out value="${pageheading}"/> <bean:write name="assetForm" property="asset.entity.name" filter="false"/></c:set>
</c:if>
	
<head>
	
	<title><bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	
	<script src="../js/keywordChooser.js" type="text/javascript"></script>
	<script src="../js/asset-upload.js" type="text/javascript"></script>
	<script src="../js/agreements.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	<!-- 
		function savePressed()
		{
			document.getElementById('savingDiv').style.display="block";
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

	<%-- Set up category javascript --%>
	<c:set var="ctrlIsCheckboxControl" value="0" scope="request"/>
	<%@include file="../category/inc_asset_category_head_js.jsp"%>

	<c:if test="${not empty assetAdminJavascriptFile}">
		<script src="<bean:write name="assetAdminJavascriptFile" filter="false"/>" type="text/javascript"></script>
	</c:if>
	
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="asset_metadata"/>	
	
</head>

<body onload="<c:if test='${assetForm.areCategoriesVisible}'>setDescSelectedCategories();</c:if> setPermSelectedCategories(); setCatIdsFields(); showHideAgreementType(); syncAgreementPreviewButton();">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pageheading" filter="false"/></h1>
     

	<bean:parameter id="cancelUrl" name="returnUrl" value=""/>

	<c:choose>
		<c:when test="${cancelUrl != null && cancelUrl != ''}">
			<c:set var="editAssetReturnAction" value="${cancelUrl}" />
		</c:when>
		<c:otherwise>
			<logic:notPresent name='editAssetReturnAction'>
				<c:set var="editAssetReturnAction" value="viewAsset?id=${assetForm.asset.id}" />
			</logic:notPresent>
		</c:otherwise>
	</c:choose>
   	
	<c:if test="${assetForm.asset.isVideo && assetForm.asset.class.simpleName == 'VideoAsset' && useVideoKeywords}"> 
		<p><a href="<bean:write name='editAssetReturnAction'/>">&laquo; Back to item</a></p>      
    </c:if>

	<%@include file="../inc/extension-asset-info.jsp"%>	
	
	<div id="dataLookupCodex"></div>
	
	<%@include file="inc_asset_errors.jsp"%>

	
	<c:if test="${assetForm.asset.isVideo}">
		<bright:refDataList componentName="VideoPreviewManager" methodName="isPreviewBeingGenerated" argumentValue="${assetForm.asset.id}" id="beingGenerated"/>
		<c:if test="${beingGenerated}">
			<div class="info"><bright:cmsWrite identifier="snippet-preview-being-generated"></bright:cmsWrite></div>
		</c:if>
	</c:if>

	<html:form enctype="multipart/form-data" action="updateAsset" styleId="updateForm" method="post">
		<html:hidden name="assetForm" property="asset.id"/>

		<c:if test="${assetForm.asset.surrogateAssetId<=0}">
			<html:hidden name="assetForm" property="asset.typeId"/>
			<html:hidden name="assetForm" property="asset.format.assetTypeId"/>
			<html:hidden name="assetForm" property="asset.format.fileExtension"/>
			<html:hidden name="assetForm" property="asset.format.converterClass"/>
		</c:if>
		
		<html:hidden name="assetForm" property="fileSizeInBytes"/>
		<logic:equal name="assetForm" property="asset.typeId" value="2">
			<html:hidden name="assetForm" property="height"/>
			<html:hidden name="assetForm" property="width"/>
			<html:hidden name="assetForm" property="numLayers"/>
		</logic:equal>
		<html:hidden name="assetForm" property="changedFrame"/>
		<html:hidden name="assetForm" property="tempDirName"/>
		<html:hidden name="assetForm" property="tempFileIndex"/>
		<html:hidden name="assetForm" property="asset.synchronised"/>
		<html:hidden name="assetForm" property="asset.originalFilename"/>
		<html:hidden name="assetForm" property="asset.surrogateAssetId"/>
		<html:hidden name="assetForm" property="returnUrl"/>
		<html:hidden name="assetForm" property="expectedAssetId"/>
		
		<%-- category extension asset hidden fields and settings --%>
		<%@include file="inc_cat_extension_hiddens.jsp"%>
		<logic:present name='returnUrl'>
			<bean:parameter id="setReturn" name="setReturn" value="false" />
			<c:if test="${setReturn}">
				<input type="hidden" name="url" value="<c:out value='${returnUrl}' />" />
			</c:if>
		</logic:present>

		<c:set var="sIsImport" value="false" scope="request"/>
	
		<logic:notEmpty name="assetForm" property="asset.displayPreviewImageFile.path">
			
			<c:if test="${assetForm.asset.isAudio}">
				<c:set var="showFlashVideoOnViewDetails" value="false"/>
			</c:if>
			
			<bean:define id="resultImgClass" value="image"/>
			<bean:define id="asset" name="assetForm" property="asset"/>
			<bean:define id="ignoreCheckRestrict" value="yes"/>
			<bean:define id="floatFlashPlayerLeft" value="false"/>
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
	
			<c:if test="${assetForm.asset.isVideo}"> 
   				<p><a href="viewSelectThumbnail?id=<bean:write name='assetForm' property='asset.id'/>&pageNo=0&dirName=<bean:write name='assetForm' property='tempDirName'/>" onclick="if (hasChanged) { return confirm('<bright:cmsWrite identifier="js-confirm-lose-changes" filter="false"/>'); }">Choose a different frame for thumbnail &raquo;</a></p>
			</c:if>

		</logic:notEmpty>
		
		<c:if test="${assetForm.asset.isAudio}">
			<c:set var="file" scope="request" value="${assetForm.asset.encryptedEmbeddedPreviewClipLocation}"/>
			<c:set var="asset" scope="request" value="${assetForm.asset}"/>
			<c:set var="floatFlashPlayerLeft" scope="request" value="false"/>
			<br/><br/>
			<jsp:include flush="true" page="../inc/inc_audio_player.jsp"/>
		</c:if>
			
		<br />
		
		<h2 style="margin-top:1em">Edit metadata</h2>
		
		<c:set var="assetForm" scope="request" value="${assetForm}"/>
		<jsp:include page="inc_fields.jsp"/>
		
		<c:if test='${assetForm.areCategoriesVisible}'><div class="hr"></div></c:if>	
				
		<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled" />

		<c:if test="${!batchReleasesEnabled && assetForm.enableSaveAsNewVersion && canCreateAssetVersions && assetForm.asset.currentVersionId le 0 && !assetForm.asset.hasWorkflow}">
			<div id="saveType">
			
			<%-- if this is a single workflow system and the user is editing with approval then automatically select the save as new version option --%>
			<p><strong><bright:cmsWrite identifier="snippet-versioning" filter="false"/></strong></p>
			<ul class="radioList narrow">
				<li><input type="radio" value="false" name="saveAsNewVersion" checked="checked" class="radio" id="version_overwrite" /><label for="version_overwrite"><span><bright:cmsWrite identifier="snippet-save-changes-option" filter="false"/></span></label></li>
				<li><input type="radio" value="true" name="saveAsNewVersion" class="radio" id="version_new"/><label for="version_new"><span><bright:cmsWrite identifier="snippet-save-new-version-option" filter="false"/></span></label></li>
			</ul>
			<div class="hr"></div>
			
			</div>
		</c:if>
		
		<input type="submit" name="saveButton" class="button flush floated" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />" onclick="savePressed();" /> 

		
		<a href="<bean:write name='editAssetReturnAction'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>


	
		<div id="savingDiv" style="text-align:center; margin: 10px; display:none; clear:left;">
		<p><bright:cmsWrite identifier="snippet-file-uploading" filter="false"/></p>
		</div>

	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
	<c:if test="${assetForm.asset.isVideo}">
		<script type="text/javascript">
			<!--
				prepForm();
			-->
		</script> 
	</c:if>

</body>
</html>
