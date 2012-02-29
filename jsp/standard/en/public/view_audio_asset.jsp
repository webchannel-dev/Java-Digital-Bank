<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	28-Feb-2007		Created from view_video_asset
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
 

<bright:applicationSetting id="canViewFullSize" settingName="public-can-view-full-size"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="numPromoted" settingName="num-promoted-images-homepage"/>
<bright:applicationSetting id="getRelatedAssets" settingName="get-related-assets"/>
<bright:applicationSetting id="previewDownloadNotEmbed" settingName="audio-preview-download-not-embed"/>
<bright:applicationSetting id="showPreview" settingName="show-audio-preview-in-page"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:applicationSetting id="firstAttributePosition" settingName="first-attribute-position"/>
<bright:applicationSetting id="firstAttributeShowLabel" settingName="first-attribute-show-label"/>
<bright:applicationSetting id="assetRepurposingEnabled" settingName="asset-repurposing-enabled"/>

<c:set var="assetForm" scope="request" value="${assetForm}"/>

<%@include file="../inc/asset_title.jsp"%>

<head>
	<title><bright:cmsWrite identifier="title-audio-details" filter="false" replaceVariables="true"/></title> 
	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="view_asset"/>	
	<script type="text/javascript" src="../js/brwsniff.js"></script>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	
	<jsp:include flush="true" page="../public/inc_view_asset_heading.jsp"/>

	<%@include file="../public/inc_file_breadcrumb.jsp"%>

	<bright:refDataList id="conditions" componentName="ListManager" methodName="getListItem" argumentValue="view-video-copy"/>	
	<bean:write name="conditions" property="body" filter="false"/>
		
	<%@include file="../inc/asset_view_error_messages.jsp"%>	
		
	<h2><bean:write name="assetForm" property="asset.searchName"/></h2>				 
	<div class="clearfix">	
		<c:choose>
			<c:when test="${showPreview}">
				<div style="float:left;">
					<logic:notEmpty name="assetForm" property="asset.previewImageFile.path">
						<bean:define id="asset" name="assetForm" property="asset" />
						<bean:define id="resultImgClass" value="image" />
						<%@include file="../inc/view_preview.jsp"%>
						<br/><br/>				
					</logic:notEmpty>
					<c:set var="file" scope="request" value="${assetForm.asset.encryptedEmbeddedPreviewClipLocation}"/>
					<c:set var="contentType" scope="request" value="${assetForm.asset.previewClipFormat.contentType}"/>
					<c:set var="asset" scope="request" value="${assetForm.asset}"/>
					<c:set var="floatFlashPlayerLeft" scope="request" value="true"/>
					<jsp:include flush="true" page="../inc/inc_audio_player.jsp"/>
				</div>
			</c:when>
			<c:otherwise>
				<logic:notEmpty name="assetForm" property="asset.previewImageFile.path">
					<bean:define id="asset" name="assetForm" property="asset" />
					<bean:define id="resultImgClass" value="image floatLeft" />
					<%@include file="../inc/view_preview.jsp"%>				
				</logic:notEmpty>
				<logic:empty name="assetForm" property="asset.previewImageFile.path">
					<div class="thumbFile">
						<bean:define id="asset" name="assetForm" property="asset" />
						<bean:define id="resultImgClass" value="icon" />
						<%@include file="../inc/view_thumbnail.jsp"%>				
					</div>
				</logic:empty>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${ecommerce}">
				<div class="actions add-to-basket">
			</c:when>
			<c:otherwise>
				<div class="actions">
			</c:otherwise>
		</c:choose>
		
	 		<ul>		
				<%-- Button area --%>
				<c:set var="viewUrl" scope="request" value="${viewUrl}"/>
				<c:set var="pos" scope="request" value="${pos}"/>
				<c:set var="params" scope="request" value="${params}"/>
				<jsp:include page="inc_view_asset_buttons.jsp"/>

				<c:if test="${assetRepurposingEnabled && userprofile.userCanRepurposeAssets && assetForm.userCanDownloadAsset && (assetForm.asset.hasFile || assetForm.asset.surrogateAssetId>0)}">
					<li class="link" id="embedLink" style="display:none;">
						<c:if test="${assetForm.asset.hasFile}">
							<a class="embed" href="viewRepurposedAudioClips?id=<bean:write name='assetForm' property='asset.id'/>&embed=true"><bright:cmsWrite identifier="link-embed" filter="false"/></a>
						</c:if>
						<c:if test="${assetForm.asset.surrogateAssetId>0}">
							<a class="embed" href="viewRepurposedAudioClips?id=<bean:write name='assetForm' property='asset.surrogateAssetId'/>&embed=true&parentId=<bean:write name="assetForm" property="asset.id"/>"><bright:cmsWrite identifier="link-embed" filter="false"/></a>
						</c:if>
					</li>
					<script type="text/javascript">
						document.getElementById('embedLink').style.display='block';
					</script>
				</c:if>

				<%@include file="inc_view_asset_add_to_basket.jsp"%>	

				<c:if test="${showPreview=='false'}">
	    			<logic:notEmpty name="assetForm" property="asset.encryptedPreviewClipLocation">
	    				<c:if test="${!showFlashVideoOnViewDetails && not empty assetForm.asset.encryptedEmbeddedPreviewClipLocation}">
							<%@include file="../inc/inc_audio_preview_link.jsp"%>
						</c:if>
	    			</logic:notEmpty>
				</c:if>
				
				<li class="border"></li>

    		</ul>		

    	</div>   <!-- End of actions -->
    
    	<c:if test="${firstAttributePosition=='right'}">
			<%@include file="inc_first_attribute_panel.jsp"%>
		</c:if>
		
    </div>   <!-- end of clearfix -->
    <br />
	
	<%@include file="inc_feedback_link.jsp"%>
	<%@include file="inc_relatedassets_link.jsp"%>	
	
	<c:if test="${firstAttributePosition=='below'}">
		<%@include file="inc_first_attribute_panel.jsp"%>
	</c:if>

	<%-- Image data --%>
    
	<c:set var="fileSize" value="${assetForm.asset.fileSizeInBytes/(1024*1024)}"/>
    
	<%@include file="inc_attribute_fields_with_extensions.jsp"%>

	<jsp:include page="../public/inc_relatedassets.jsp"/>

	<%@include file="../public/inc_feedback.jsp"%>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>