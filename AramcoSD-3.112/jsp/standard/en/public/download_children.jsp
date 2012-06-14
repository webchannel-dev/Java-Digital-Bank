<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard		25-Mar-2009		Created.
	 d2     Matt Woollard       03-Apr-2009     Changed to direct download - bypass usage types selection
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>

	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="downloadImage"/>
	<bean:parameter id="repurposeAsset" name="repurposeAsset" value="false"/>
	
	<title><bright:cmsWrite identifier="title-download-child-assets" filter="false" replaceVariables="true" /></title> 
	
	<%@include file="../inc/head-elements.jsp"%>	
	<script src="../js/lib/prototype_old/scriptaculous.js" type="text/javascript"></script>
	<script src="../js/cropper/cropper.js" type="text/javascript" ></script>
	<script src="../js/toggle-button.js" type="text/javascript"></script>
	<script src="../js/download-form.js" type="text/javascript"></script>

	<c:if test="${downloadForm.asset.isImage}">
		<jsp:include page="inc_download_js.jsp"/>		
		<jsp:include page="inc_cropper_js.jsp"/>		
	</c:if>

</head>

<%@include  file="inc_download_form_settings.jsp"%>

<body  id="downloadPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="snippet-download" filter="false"/> <bean:write name="downloadForm" property="asset.entity.childRelationshipToNamePlural"/></h1> 			

	<c:set var="assetTypeName"><bright:cmsWrite identifier="image" case="mixed" filter="false"/></c:set>
	<%@include file="inc_download_top.jsp"%>				 
       
	<logic:empty name="downloadForm" property="childAssets">
		There are no files for you to download for this asset.
	</logic:empty>
	
	<logic:notEmpty name="downloadForm" property="childAssets">

		<bean:define id="asset" name="downloadForm" property="asset" />
		<bean:define id="resultImgClass" value="image" />								
		<bean:define id="ignoreCheckRestrict" value="yes"/>	
		<bean:define id="disablePreview" value="true"/>							
		
		<c:set var="termForChildren"><bright:write name="downloadForm" property="asset.entity.childRelationshipToNamePlural" case="lower"/></c:set>
		<p><bright:cmsWrite identifier="snippet-download-all-children-text" filter="false" replaceVariables="true"/></p>
	
		<form action="downloadChildAssets" method="get">
			<input type="hidden" name="id" value="<c:out value='${asset.id}' />">
			<input type="hidden" name="directDownload" value="true">
			<input type="hidden" name="b_downloadOriginal" value="true" />			
			<input class="button flush" type="Submit" value="<bright:cmsWrite identifier="button-download-all" filter="false"/>" />
		</form>
		
		<br />
		<div class="hr"></div>
		<br />
	
	</logic:notEmpty>
	
	<ul class="childList">
	<logic:iterate name="downloadForm" property="childAssets" id="asset">
		<li>
			<bean:define id="resultImgClass" value="image" />								
			<bean:define id="ignoreCheckRestrict" value="yes"/>	
			<bean:define id="disablePreview" value="true"/>		
			
			<c:choose>
				<c:when test="${asset.isImage && not assetForm.downloadAsDocument}">
					<c:set var="downloadActionURL" value="../action/downloadImage"/>
				</c:when>
				<c:when test="${asset.isVideo}">
					<c:set var="downloadActionURL" value="../action/downloadVideo"/>
				</c:when>
				<c:when test="${asset.isAudio}">
					<c:set var="downloadActionURL" value="../action/downloadAudio"/>
				</c:when>
				<c:otherwise>
					<c:set var="downloadActionURL" value="../action/downloadFile"/>
				</c:otherwise>
			</c:choose>	
			<form action="<bean:write name="downloadActionURL"/>" method="get" class="floatRight">
				<input type="hidden" name="id" value="<c:out value='${asset.id}' />">
				<input type="hidden" name="directDownload" value="true">
				<input type="hidden" name="b_downloadOriginal" value="true" />					
				<input class="button" type="Submit" value="Download" />
			</form>	
			<bright:applicationSetting id="restrictThumb" settingName="restricted-image-homogenized"/>
			<bright:applicationSetting id="includeDims" settingName="thumbnails-cropped-not-scaled"/>
			<%@include file="../inc/restrict_preview_check.jsp"%>
			<c:choose>
				<c:when test="${restrict == true}">
					<c:set var="thumbSrc" value="..${restrictThumb}"/>
				</c:when>
				<c:otherwise>
					<c:set var="thumbSrc" value="../servlet/display?file=${asset.displayHomogenizedImageFile.path}"/>
				</c:otherwise>
			</c:choose>				
			<img src="<bean:write name='thumbSrc'/>" width="40" height="40"  alt="<c:out value='${image.searchName}'/>" />
			
			<c:out value='${asset.name}' /><br />
			
		</li>
		
	</logic:iterate>
	</ul>
	

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>

