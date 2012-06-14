<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
     d3     Ben Browning    14-Feb-2006     HTML/CSS tidy up
	 d4		Matt Stevenson	14-Mar-2006		Added related images
--%>
 
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://www.assetbank.co.uk/taglib/abplugin" prefix="abplugin" %>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="showOpenFile" settingName="show-open-file"/>
<bright:applicationSetting id="getRelatedAssets" settingName="get-related-assets"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:applicationSetting id="firstAttributePosition" settingName="first-attribute-position"/>
<bright:applicationSetting id="firstAttributeShowLabel" settingName="first-attribute-show-label"/>
<bright:applicationSetting id="hidePreviewPanelIfNoFile" settingName="display-empty-asset-without-icon"/>
<bright:applicationSetting id="assetRepurposingEnabled" settingName="asset-repurposing-enabled"/>
<bright:applicationSetting id="appUrl" settingName="application-url"/>
<bright:applicationSetting id="showFlashVideoOnViewDetails" settingName="show-flash-video-on-view-details"/>
<bright:applicationSetting id="canEditAssetVersions" settingName="can-edit-asset-versions"/>

<c:set var="assetForm" scope="request" value="${assetForm}"/>

<c:set var="isArticle" value="${assetForm.asset.entity.isArticle}"/>

<%@include file="../inc/asset_title.jsp"%>

<head>
	<title><bright:cmsWrite identifier="title-asset-details" filter="false" replaceVariables="true"/></title> 
	<jsp:include flush="true" page="../inc/head-elements.jsp"/>

	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="view_asset"/>
	<script type="text/javascript">
	<!-- 
	
		//Set up global variables for any map attributes
		var sLat = "";
		var wLng = "";
		var nLat = "";
		var eLng = "";
		
		var $whichAttribute = ""; 			//This is a jquery element that acts as a referenece for which attribute we are dealing with
		
		$j(function(){
			viewMapPopupInit();
			// collapsible headings 
			attrHeadings.init('#mainCol');
		})

	//-->
	</script>
</head>
<%@include file="../inc/set_this_page_url.jsp"%>

<body id="detailsPage" <c:if test="${isArticle}">class="article"</c:if>>

	<%@include file="../inc/body_start.jsp"%>
	
	<jsp:include flush="true" page="../public/inc_view_asset_heading.jsp"/> 

	<%@include file="../public/inc_file_breadcrumb.jsp"%>
	
	<div class="clearing"></div>
	<bright:refDataList id="conditions" componentName="ListManager" methodName="getListItem" argumentValue="view-file-copy"/>	
	<bean:write name="conditions" property="body" filter="false"/>
	    
	<%@include file="../inc/asset_view_error_messages.jsp"%>	
		
    <h2><bean:write name="assetForm" property="asset.searchName"/></h2>		
    		 
    <jsp:include flush="true" page="../batch-release/inc_moved_asset_warning.jsp"/> 
		 
	<div class="imageAndActions">
		<c:if test="${assetForm.asset.hasFile || assetForm.asset.entity.id<=0 || assetForm.asset.entity.allowFiles || not empty assetForm.asset.entity.thumbnailFilename || !hidePreviewPanelIfNoFile}">
	    	<div class="floatLeft">
				<bean:define id="asset" name="assetForm" property="asset" />
				<c:choose>
					<c:when test="${not empty assetForm.asset.format.previewInclude}">
						<c:set var="previewInclude" value="${assetForm.asset.format.previewInclude}"/>
						<c:set scope="request" var="fileUrl" value="../../servlet/display/${assetForm.encryptedFilePath}"/>
						<jsp:include page="<%= (java.lang.String) pageContext.findAttribute(\"previewInclude\") %>"/>
					</c:when>
					<c:when test="${empty assetForm.asset.displayPreviewImageFile.path}">
						<div class="thumbFile">
							<bean:define id="resultImgClass" value="icon" />
							<%@include file="../inc/view_thumbnail.jsp"%>		
						</div>
					</c:when>
					<c:otherwise>
						<bean:define id="resultImgClass" value="image floatLeft" />
						<%@include file="../inc/view_preview.jsp"%>				
					</c:otherwise>
				</c:choose>
			</div>
			
			<c:if test="${!isArticle}">
	    	
	
				<div class="actions <c:if test="${ecommerce}">add-to-basket</c:if>">
	
		 			<ul>    	
						<%-- Button area --%>
						<jsp:include page="inc_view_asset_buttons.jsp"/>
						<c:if test="${assetRepurposingEnabled && assetForm.asset.hasFile && userprofile.userCanRepurposeAssets && assetForm.userCanDownloadAsset && empty batchAssetForm}">
					        <li class="link" id="embedLink">
					        	<a href="viewEmbeddableFile?id=<bean:write name="assetForm" property="asset.id"/>" class="embed"><bright:cmsWrite identifier="link-embed" filter="false"/></a>
					        </li>
						</c:if>
				
						<%@include file="inc_view_asset_add_to_basket.jsp"%>
						<c:if test="${showOpenFile || assetForm.asset.format.canViewOriginal}">
		    		        <c:choose>
		    		        	<c:when test="${not empty assetForm.asset.format.viewFileInclude}">
		    		        		<li class="link">
			    	        			<a class="view" href="../action/viewCustomFileTypePreview?viewFileInclude=<bright:write name="assetForm" property="asset.format.viewFileInclude" filter="false" encodeForUrl="true"/>&file=<bean:write name='assetForm' property='encryptedFilePath'/>&id=<bean:write name='assetForm' property='asset.id'/>&filename=<bean:write name='assetForm' property='asset.originalFilename'/>" target="_blank" onclick="popupPreview('../action/viewCustomFileTypePreview?viewFileInclude=<bright:write name="assetForm" property="asset.format.viewFileInclude" filter="false" encodeForUrl="true"/>&file=<bean:write name='assetForm' property='encryptedFilePath'/>&id=<bean:write name='assetForm' property='asset.id'/>&filename=<bean:write name='assetForm' property='asset.originalFilename'/>',<bright:write name="assetForm" property="asset.format.previewWidth"/>,<bright:write name="assetForm" property="asset.format.previewHeight"/>); return false;"><bright:cmsWrite identifier="snippet-view-preview" filter="false"/></a>
		    		        		</li>
		    		        	</c:when>
		    		        	<c:when test="${not empty assetForm.asset.format.contentType}">
		    		        		<li class="link">
			    	        			<a class="view" href="../servlet/display?file=<bean:write name='assetForm' property='encryptedFilePath'/>&contentType=<bean:write name='assetForm' property='asset.format.contentType'/>&filename=<bean:write name='assetForm' property='asset.name'/>.<bean:write name='assetForm' property='asset.format.fileExtension'/>">open file</a>
			    	        		</li>
		    		        	</c:when>
		    		        </c:choose>
						</c:if>
						<li class="border"></li>
		    		</ul>
				</div> <!-- End of div.actions -->
			
				<%@include file="inc_client_side_edit_panel.jsp"%>
				
				
			</c:if>
	    	
		</c:if>
    	<c:if test="${firstAttributePosition=='right'}">
			<%@include file="inc_first_attribute_panel.jsp"%>
		</c:if>
		<div class="clearing"></div>
   </div>
   
   <c:if test="${assetForm.asset.hasFile || assetForm.asset.entity.id<=0 || not empty assetForm.asset.entity.thumbnailFilename || !hidePreviewPanelIfNoFile}">
		<br />
	</c:if>
	
	<%@include file="inc_feedback_link.jsp"%>
	<%@include file="inc_relatedassets_link.jsp"%>	
	
	<c:if test="${firstAttributePosition=='below'}">
		<%@include file="inc_first_attribute_panel.jsp"%>
	</c:if>
	
	

   <c:set var="fileSize" value="${assetForm.asset.fileSizeInBytes/(1024*1024)}"/>

	<c:set var="hideLabels" value="${assetForm.asset.entity.id>0 && !assetForm.asset.entity.showAttributeLabels}"/>
	<%@include file="inc_attribute_fields_with_extensions.jsp"%>

	<%-- If the asset doesnt have a file and has a type that a) cant have files and b) doesnt not have a thumbnail, move the buttons here if hidePreviewPanelIfNoFile --%>
	<c:set var="currentSwitch" value="${!(assetForm.asset.entity.id<=0 || assetForm.asset.entity.allowFiles || not empty assetForm.asset.entity.thumbnailFilename || !hidePreviewPanelIfNoFile)}" />
	<c:if test="${currentSwitch || isArticle}">

		<div class="actions" >
			<ul>		
				<%-- Button area --%>
				<c:set var="viewUrl" scope="request" value="${viewUrl}"/>
				<c:set var="pos" scope="request" value="${pos}"/>
				<c:set var="params" scope="request" value="${params}"/>
				<jsp:include page="inc_view_asset_buttons.jsp"/>
				<%@include file="inc_view_asset_add_to_basket.jsp"%>
				<li class="link empty"></li>
			</ul>
		</div>

	</c:if>

	<jsp:include page="../public/inc_relatedassets.jsp"/>

	<%@include file="../public/inc_feedback.jsp"%>

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>


