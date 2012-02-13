<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	17-Feb-2006		HTML/CSS Tidy up
	 d4		Matt Stevenson	05-Mar-2007		Work on video functionality
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="assetAdminJavascriptFile" settingName="asset-admin-javascript-file"/>
<bright:applicationSetting id="showFlashVideoOnViewDetails" settingName="show-flash-video-on-view-details"/>


<head>
	
	<title><bright:cmsWrite identifier="title-upload" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	<script src="../js/calendar.js" type="text/javascript"></script>
	<script src="../js/keywordChooser.js" type="text/javascript"></script>
	<script src="../js/asset-upload.js" type="text/javascript"></script>
	<script src="../js/agreements.js" type="text/javascript"></script>
	
	<c:if test="${not empty assetAdminJavascriptFile}">
		<script src="<bean:write name="assetAdminJavascriptFile" filter="false"/>" type="text/javascript"></script>
	</c:if>

	<script type="text/javascript">
	<!-- 
	function savePressed()
	{
		document.getElementById('savingDiv').style.display="block";
	}
	//-->
	</script>

	<%-- Set up category javascript and jsp variables --%>
	<c:set var="ctrlIsCheckboxControl" value="0" scope="request" />
	<%@include file="../category/inc_asset_category_head_js.jsp"%>
	

	
	<bean:define id="section" value="upload"/>
	<bean:define id="helpsection" value="asset_metadata"/>		


</head>

<body id="importPage" onload="setDescSelectedCategories(); setPermSelectedCategories(); setCatIdsFields(); showHideAgreementType(); syncAgreementPreviewButton();">

	<%@include file="../inc/body_start.jsp"%>
	
	<div id="dataLookupCode"></div>
	
	<h1 class="underline">
	<c:if test="${assetForm.tempFileLocation == null}">
		<c:if test="${assetForm.asset.entity.id<=0 || assetForm.asset.entity.allowAssetFiles }">
			<bright:cmsWrite identifier="heading-upload" filter="false" />
		</c:if>
		<c:if test="${assetForm.asset.entity.id>0 && !assetForm.asset.entity.allowAssetFiles }">
			<bright:cmsWrite identifier="heading-add-asset" case="mixed" filter="false" />
		</c:if>
	</c:if>
	<c:if test="${assetForm.tempFileLocation != null}">
		<c:choose>
			<c:when test="${assetForm.asset.isVideo}">
				<bright:cmsWrite identifier="heading-step-2-thumbnail" filter="false" />
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="heading-step-2-metadata" filter="false" />
			</c:otherwise>
		</c:choose>
	</c:if>
	<c:if test="${not empty assetForm.asset.entity.name}">
		(<bean:write name="assetForm" property="asset.entity.name" filter="false"/>)
	</c:if>	
	</h1> 


	<p>
		<logic:notEmpty name="assetForm" property="previewImageFile">
			<bean:define id="resultImgClass" value="image"/>
			<bean:define id="asset" name="assetForm" property="asset"/>
			<bean:define id="ignoreCheckRestrict" value="yes"/>
			<bean:define id="floatFlashPlayerLeft" value="false"/>
			<%@include file="../inc/view_preview.jsp"%>

			<c:if test="${assetForm.asset.isVideo}">
				<p><a href="viewSelectThumbnail?file=<bean:write name='assetForm' property='tempFileLocation'/>&pageNo=0&dirName=<bean:write name='assetForm' property='tempDirName'/>&index=<bean:write name='assetForm' property='tempFileIndex'/>&selectedAssetEntityId=<bean:write name='assetForm' property='selectedAssetEntityId'/>&originalFilename=<bean:write name='assetForm' property='originalFilename'/>" onclick="if (hasChanged) { return confirm('Clicking this link after making changes to the details in step 3 will cause you to lose the changes you have made. Continue?'); }"><bright:cmsWrite identifier="link-choose-diff-frame" filter="false"/></a></p>                                
 
                        </c:if>
		</logic:notEmpty>
	</p>

	<c:if test="${assetForm.asset.isVideo}">
		<br/><h1 class="underline"><bright:cmsWrite identifier="heading-step-3-metadata" filter="false" /></h1>
	</c:if>
	
	<c:set var="sIsImport" value="false" scope="request"/>
	<%@include file="inc_asset_errors.jsp"%>
		
		<html:form enctype="multipart/form-data" action="addAsset" styleId="updateForm" method="post">
		<html:hidden name="assetForm" property="tempDirName"/>
		<html:hidden name="assetForm" property="tempFileIndex"/>
		<html:hidden name="assetForm" property="deferEntitySelection"/>
		<html:hidden name="assetForm" property="peerId"/>
		<html:hidden name="assetForm" property="parentId"/>
		<html:hidden name="assetForm" property="emptyAsset"/>
		<html:hidden name="assetForm" property="originalFilename"/>
		<html:hidden name="assetForm" property="addingFromBrowseCatId" />
		<html:hidden name="assetForm" property="addingFromBrowseTreeId" />
		
		<%-- category extension asset hidden fields and settings --%>
		<%@include file="inc_cat_extension_hiddens.jsp"%>
		
		<bean:parameter name="changePreviewStart" id="changePreviewStart" value="false"/>
		<c:if test="${changePreviewStart}">
			<bean:parameter name="frame" id="frame" value="0"/>
			<input type="hidden" name="selectedFrame" value="<bean:write name='frame'/>"/>
		</c:if>

		<logic:equal name="assetForm" property="hasErrors" value="false">
			<c:set var="uploading" scope="request" value="true"/>
		</logic:equal>
		<c:set var="assetForm" scope="request" value="${assetForm}"/>		
		<jsp:include page="inc_fields.jsp"/>
		
		<div class="hr"></div>
		
		<div class="centered">
			<p><input type="submit" name="saveButton" class="button" id="submitButton" value="<bright:cmsWrite identifier="button-submit" filter="false" />" onclick="savePressed();" /> </p>
		</div>
	
		<div id="savingDiv" style="text-align:center; margin: 10px; display:none;">
			<bright:cmsWrite identifier="snippet-please-wait-file" filter="false" />
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