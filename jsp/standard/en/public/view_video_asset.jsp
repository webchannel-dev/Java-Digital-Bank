<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
     d3     Ben Browning    14-Feb-2006     HTML/CSS tidy up
	 d4		Matt Stevenson	14-Mar-2006		Added related images
	 d5		Matt Stevenson	01-Mar-2007		Modified to use flash movies for preview
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
<bright:applicationSetting id="previewDownloadNotEmbed" settingName="video-preview-download-not-embed"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:applicationSetting id="firstAttributePosition" settingName="first-attribute-position"/>
<bright:applicationSetting id="firstAttributeShowLabel" settingName="first-attribute-show-label"/>
<bright:applicationSetting id="showFlashVideoOnViewDetails" settingName="show-flash-video-on-view-details"/>
<bright:applicationSetting id="assetRepurposingEnabled" settingName="asset-repurposing-enabled"/>
<bright:applicationSetting id="showOpenFile" settingName="show-open-file"/>

<c:set var="assetForm" scope="request" value="${assetForm}"/>

<%@include file="../inc/asset_title.jsp"%>

<head>
	<title><bright:cmsWrite identifier="title-video-details" filter="false" replaceVariables="true"/></title> 
	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	<bean:define id="section" value=""/>
	<bean:define id="pagetitle" value="Video Details"/>
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

	<c:if test="${assetForm.asset.previewClipBeingGenerated}">
		<div class="info"><bright:cmsWrite identifier="snippet-preview-being-generated"></bright:cmsWrite></div>
	</c:if>

	<div class="clearfix">
		<bean:define id="asset" name="assetForm" property="asset" />
		<bean:define id="resultImgClass" value="image floatLeft" />
		<bean:define id="floatFlashPlayerLeft" value="true"/>
		<%@include file="../inc/view_preview.jsp"%>		
		 
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
					<li class="link"  id="embedLink" style="display:none;">
						<c:if test="${assetForm.asset.hasFile}">
							<a class="embed" href="viewRepurposedVideos?id=<bean:write name='assetForm' property='asset.id'/>&embed=true"><bright:cmsWrite identifier="link-embed" filter="false"/></a>
						</c:if>
						<c:if test="${assetForm.asset.surrogateAssetId>0}">
							<a class="embed" href="viewRepurposedVideos?id=<bean:write name='assetForm' property='asset.surrogateAssetId'/>&embed=true&parentId=<bean:write name="assetForm" property="asset.id"/>"><bright:cmsWrite identifier="link-embed" filter="false"/></a>
						</c:if>
					</li>
					<script type="text/javascript">
						document.getElementById('embedLink').style.display='block';
					</script>
				</c:if>

				<%@include file="inc_view_asset_add_to_basket.jsp"%>	
				<logic:equal name="restrict" value="false">
					<c:choose>
						<c:when test="${showOpenFile || assetForm.asset.format.canViewOriginal}">
							<c:choose>
			    				<c:when test="${not empty assetForm.asset.format.viewFileInclude}">
			    					<li class="link">
			    						<c:set var="url">../action/viewCustomFileTypePreview?viewFileInclude=<bright:write name="assetForm" property="asset.format.viewFileInclude" filter="false" encodeForUrl="true"/>&file=<bean:write name='assetForm' property='asset.originalFileLocation'/>&id=<bean:write name='assetForm' property='asset.id'/>&filename=<bean:write name='assetForm' property='asset.originalFilename'/>&width=<bean:write name='assetForm' property='asset.width'/>&height=<bean:write name='assetForm' property='asset.height'/></c:set>
			    						<a class="view" href="<bean:write name="url" filter="false"/>" target="_blank" onclick="popupPreview('<bean:write name="url" filter="false"/>',<bright:write name="assetForm" property="asset.width"/>,<bright:write name="assetForm" property="asset.height"/>+20); return false;"><bright:cmsWrite identifier="link-view-full-version" filter="false"/></a>
			    					</li>
			    				</c:when>
			    				<c:when test="${not empty assetForm.asset.format.contentType}">
			    					<li class="link">
				    					<a class="view" href="../servlet/display?file=<bean:write name='assetForm' property='encryptedFilePath'/>&contentType=<bean:write name='assetForm' property='asset.format.contentType'/>&filename=<bean:write name='assetForm' property='asset.name'/>.<bean:write name='assetForm' property='asset.format.fileExtension'/>">open file</a>
				    				</li>
			    				</c:when>
			    			</c:choose>
						</c:when>
						<c:when test="${!showFlashVideoOnViewDetails && not empty assetForm.asset.encryptedEmbeddedPreviewClipLocation}">
							<%@include file="inc_preview_link.jsp"%>
						</c:when>
					</c:choose>
				</logic:equal>
				<li class="border"></li>

    		</ul>		

    	</div>   <!-- End of actions -->
    	
    	<c:if test="${firstAttributePosition=='right'}">
			<%@include file="inc_first_attribute_panel.jsp"%>
		</c:if>
		
    </div>   <!-- end of clearfix -->
	<br/>
   <br/>
    
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