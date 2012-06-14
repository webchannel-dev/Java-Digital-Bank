<bright:applicationSetting id="restrictThumb" settingName="restricted-image-featured"/>
<bright:applicationSetting id="iFeaturedImageWidth" settingName="featured-image-width"/>
<bright:applicationSetting id="iFeaturedImageHeight" settingName="featured-image-height"/>

<%@include file="../inc/restrict_preview_check.jsp"%>
<c:choose>
	<c:when test="${restrict == true}">
		<c:set var="thumbSrc" value="..${restrictThumb}"/>
	</c:when>
	<c:otherwise>
		<c:set var="thumbSrc" value="../servlet/display?file=${asset.featuredImageFile.path}"/>
	</c:otherwise>
</c:choose>


<c:choose>
	<c:when test="${asset.isImage || restrict == true}">
		<a href="../action/viewAsset?id=<bean:write name='asset' property='id'/>"><img  src="<bean:write name='thumbSrc'/>" alt="Featured Image" class="featuredImage" /></a>
	</c:when>
	<c:when test="${asset.isVideo}">
		<c:set var="width" value="${iFeaturedImageWidth}"/>
		<c:set var="height" value="${iFeaturedImageHeight}"/>
		<c:set var="file" value="${asset.encryptedEmbeddedPreviewClipLocation}"/>
		<c:set var="autoplay" value="false"/>
		<c:set var="splashImage" value="../servlet/display.jpg?file=${asset.displayPreviewImageFile.path}"/>
		<bean:define id="marginBottom" value="0"/>
		<%@include file="../public/inc_flash_player.jsp"%>
	</c:when>
</c:choose>

