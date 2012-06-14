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
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
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
<bright:applicationSetting id="useVideoKeywords" settingName="use-video-keywords"/>
<bright:applicationSetting id="canEditAssetVersions" settingName="can-edit-asset-versions"/>

<c:set var="assetForm" scope="request" value="${assetForm}"/>

<c:set var="isArticle" value="${assetForm.asset.entity.isArticle}"/>

<%@include file="../inc/asset_title.jsp"%>

<head>
	<title><bright:cmsWrite identifier="title-video-details" filter="false" replaceVariables="true"/></title> 
	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	<bean:define id="section" value=""/>
	<bean:define id="pagetitle" value="Video Details"/>
	<bean:define id="helpsection" value="video_details"/>	          
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
	
	<logic:present name="facebookLikeButton" >
  		<bright:applicationUrl/>
		<c:set var="fbCompleteUrl" 		scope="request" value="${applicationUrl}/action/viewAsset?id=${assetForm.asset.id}" />
		<c:set var="fbPreviewImageUrl" 	scope="request" value="${applicationUrl}/servlet/display.jpg?file=${assetForm.asset.displayPreviewImageFile.path}" />
		<c:set var="fbPageTitle"		scope="request" value="${assetName}" />
		<jsp:include flush="true" page="inc_facebook_like_button_meta.jsp"/>
	</logic:present>
</head>
<%@include file="../inc/set_this_page_url.jsp"%>

<body id="detailsPage" class="<c:if test="${isArticle}">article</c:if> video-asset">

	<%@include file="../inc/body_start.jsp"%>

	
	<jsp:include flush="true" page="../public/inc_view_asset_heading.jsp"/>

	<%@include file="../public/inc_file_breadcrumb.jsp"%>
	
	<div class="clearing"></div>
	<bright:refDataList id="conditions" componentName="ListManager" methodName="getListItem" argumentValue="view-video-copy"/>	
	<bean:write name="conditions" property="body" filter="false"/>
		
	<%@include file="../inc/asset_view_error_messages.jsp"%>	
		
	<h2><bean:write name="assetForm" property="asset.searchName"/></h2>				 

	<jsp:include flush="true" page="../batch-release/inc_moved_asset_warning.jsp"/> 
	
	<bright:refDataList componentName="VideoPreviewManager" methodName="isPreviewBeingGenerated" argumentValue="${assetForm.asset.id}" id="beingGenerated" dontPassLanguage="true" />
	<c:if test="${beingGenerated}">
		<div class="info"><bright:cmsWrite identifier="snippet-preview-being-generated"></bright:cmsWrite></div>
	</c:if>


	<bean:define id="asset" name="assetForm" property="asset" />
	<bean:define id="resultImgClass" value="image floatLeft" />
	<bean:define id="floatFlashPlayerLeft" value="true"/>
	<%@include file="../inc/view_preview.jsp"%>		
	<div class="floatLeft video-ac"> 
		<c:if test="${!isArticle}"><%@include file="../public/inc_view_video_asset_actions.jsp"%></c:if>
   		
  		<logic:present name="facebookLikeButton" >
			<jsp:include flush="true" page="inc_facebook_like_button.jsp"/>
		</logic:present>
	
	</div>
   	
   	<c:if test="${firstAttributePosition=='right'}">
		<%@include file="inc_first_attribute_panel.jsp"%>
	</c:if>	
	<div class="clearing"></div>


	<%-- START Video keywords block --%>	    
   	<c:if test="${assetForm.asset.class.simpleName == 'VideoAsset' && useVideoKeywords}">
 		<c:if test="${not empty assetForm.asset.videoKeywords}">
	   		<%@include file="../public/inc_video_keywords_panel.jsp"%>
	   	</c:if>	   			
	   	<c:if test="${empty assetForm.asset.videoKeywords}">
	   		<br/>
	   	</c:if>	   			
    </c:if>
   	<%-- END Video keywords block --%>
   
	<%@include file="inc_feedback_link.jsp"%>
	<%@include file="inc_relatedassets_link.jsp"%>	
	
	<c:if test="${firstAttributePosition=='below'}">
		<%@include file="inc_first_attribute_panel.jsp"%>
	</c:if>
	
   	<%-- Image data --%>
    
   	<c:set var="fileSize" value="${assetForm.asset.fileSizeInBytes/(1024*1024)}"/>
    <c:set var="hideLabels" value="${assetForm.asset.entity.id>0 && !assetForm.asset.entity.showAttributeLabels}"/>
   	<%@include file="inc_attribute_fields_with_extensions.jsp"%>
	
	<c:if test="${isArticle}"><%@include file="../public/inc_view_video_asset_actions.jsp"%></c:if>
	
	<jsp:include page="../public/inc_relatedassets.jsp"/>

	<%@include file="../public/inc_feedback.jsp"%>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>