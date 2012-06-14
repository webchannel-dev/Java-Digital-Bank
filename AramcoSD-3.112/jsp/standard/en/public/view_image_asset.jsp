<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
     d3     Ben Browning    14-Feb-2006     HTML/CSS tidy up
	 d4		Matt Stevenson	14-Mar-2006		Added related images
	 d5     Matt Woollard   12-Aug-2009     Moved image size calculations to an include
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
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:applicationSetting id="showOpenFile" settingName="show-open-file"/>
<bright:applicationSetting id="firstAttributePosition" settingName="first-attribute-position"/>
<bright:applicationSetting id="firstAttributeShowLabel" settingName="first-attribute-show-label"/>
<bright:applicationSetting id="nonAdminCanPrint" settingName="can-non-admin-print"/>
<bright:applicationSetting id="cacheLargeImages" settingName="cache-large-image"/>
<bright:applicationSetting id="largeImageToggle" settingName="large-asset-toggle"/>
<bright:applicationSetting id="sendAsEcardEnabled" settingName="send-as-ecard-enabled"/>

<c:set var="assetForm" scope="request" value="${assetForm}"/>

<c:set var="isArticle" value="${assetForm.asset.entity.isArticle}"/>

<%@include file="../inc/asset_title.jsp"%>

<head>
	<title><bright:cmsWrite identifier="title-image-details" filter="false" replaceVariables="true"/></title> 
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
	
    <logic:present name="facebookLikeButton" >
  		<bright:applicationSetting id="applicationUrl" settingName="application-url" />
		<c:set var="fbCompleteUrl" 		scope="request" value="${applicationUrl}/action/viewAsset?id=${assetForm.asset.id}" />
		<c:set var="fbPreviewImageUrl" 	scope="request" value="${applicationUrl}/servlet/display.jpg?file=${assetForm.asset.displayPreviewImageFile.path}" />
		<c:set var="fbPageTitle"		scope="request" value="${assetName}" />
		<jsp:include flush="true" page="inc_facebook_like_button_meta.jsp"/>
	</logic:present>
	
</head>
<%@include file="../inc/set_this_page_url.jsp"%>

<body id="detailsPage" class="<c:if test="${cacheLargeImages && largeImageToggle != 'off'}">largeViewEnabled <c:if test="${userprofile.largeImagesOnView}">largeViewOn</c:if></c:if> <c:if test="${assetForm.asset.entity.isArticle}">article</c:if>">

	<%@include file="../inc/body_start.jsp"%>
	
	<jsp:include flush="true" page="../public/inc_view_asset_heading.jsp"/>
	
	
	<c:if test="${cacheLargeImages && largeImageToggle != 'off'}">
		<p class="tabHolderPopup">
			<c:choose>
				<c:when test="${userprofile.largeImagesOnView}">
					<a href="toggleLargeView?returnUrl=<c:out value='${thisUrlForGet}'/>" ><bright:cmsWrite identifier="tab-normal-view" filter="false" /></a>
					<a class="active" href="toggleLargeView?switch=1&returnUrl=<c:out value='${thisUrlForGet}'/>"><bright:cmsWrite identifier="tab-large-image-view" filter="false" /></a>
				</c:when>
				<c:otherwise>
					<a class="active" href="toggleLargeView?returnUrl=<c:out value='${thisUrlForGet}'/>" ><bright:cmsWrite identifier="tab-normal-view" filter="false" /></a>
					<a href="toggleLargeView?switch=1&returnUrl=<c:out value='${thisUrlForGet}'/>"><bright:cmsWrite identifier="tab-large-image-view" filter="false" /></a>
				</c:otherwise>
			</c:choose>
		</p>
	</c:if>	
	
	<%@include file="../public/inc_file_breadcrumb.jsp"%>

	<bright:refDataList id="conditions" componentName="ListManager" methodName="getListItem" argumentValue="view-image-copy"/>	
	<bean:write name="conditions" property="body" filter="false"/>
	
	<%@include file="../inc/asset_view_error_messages.jsp"%>

	<h2><bean:write name="assetForm" property="asset.searchName" filter="false"/></h2>	

	<jsp:include flush="true" page="../batch-release/inc_moved_asset_warning.jsp"/> 
	
	<div class="imageAndActions">
		
		<c:choose>
			<c:when test="${cacheLargeImages && largeImageToggle != 'off' && userprofile.largeImagesOnView && !ecommerce}">
				<c:if test="${!isArticle}"><%@include file="../public/inc_view_image_asset_actions.jsp"%></c:if>
				<%@include file="../public/inc_view_image_asset_preview.jsp"%>
				<logic:present name="facebookLikeButton" >
					<jsp:include flush="true" page="inc_facebook_like_button.jsp"/>
				</logic:present>
			</c:when>
			<c:otherwise>
				<%@include file="../public/inc_view_image_asset_preview.jsp"%>
				<div class="floatLeft">
				<c:if test="${!isArticle}"><%@include file="../public/inc_view_image_asset_actions.jsp"%></c:if>
				<logic:present name="facebookLikeButton" >
					<jsp:include flush="true" page="inc_facebook_like_button.jsp"/>
				</logic:present>
				</div>
			</c:otherwise>
		</c:choose>	
    	
    	<c:if test="${firstAttributePosition=='right'}">
			<%@include file="inc_first_attribute_panel.jsp"%>
		</c:if>
    		
    	
    <div class="clearing"></div>	
	</div>

	<br/>
	
	<jsp:include page="inc_feedback_link.jsp"/>
	<%@include file="inc_relatedassets_link.jsp"%>

	<c:if test="${firstAttributePosition=='below'}">
		<%@include file="inc_first_attribute_panel.jsp"%>
	</c:if>

   	<%-- Image data --%>    
   	<c:set var="fileSize" value="${assetForm.asset.fileSizeInBytes/(1024*1024)}"/>
	<c:set var="hideLabels" value="${assetForm.asset.entity.id>0 && !assetForm.asset.entity.showAttributeLabels}"/>
   	<%@include file="inc_attribute_fields_with_extensions.jsp"%>
	
	<c:if test="${isArticle}"><%@include file="../public/inc_view_image_asset_actions.jsp"%></c:if>
	
	<jsp:include page="../public/inc_relatedassets.jsp"/>
	
	<%@include file="../public/inc_feedback.jsp"%>
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>