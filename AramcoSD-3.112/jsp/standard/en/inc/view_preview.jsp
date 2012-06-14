<bright:applicationSetting id="restrictThumb" settingName="restricted-image-preview"/>
<bright:applicationSetting id="sensitiveThumb" settingName="sensitive-image-preview"/>
<bright:applicationSetting id="flashPlayerHeight" settingName="flash-player-height"/>
<bright:applicationSetting id="flashPlayerWidth" settingName="flash-player-width"/>
<bright:applicationSetting id="useVideoKeywords" settingName="use-video-keywords"/>

<%@include file="../inc/restrict_preview_check.jsp"%>
<logic:notPresent name="resultImgClass">
	<bean:define id="resultImgClass" value="image"/>
</logic:notPresent>

<logic:notPresent name='overideLargeImageView'>
	<bean:define id="overideLargeImageView" value="false"/>
</logic:notPresent>

<bean:parameter name="showSensitive" id="showSensitive" value="false"/>

<logic:notMatch name="userprofile" property="clickedOnSensitiveImage[${asset.id}]" value="true">
	<c:set var="hideSensitiveImage" value="${!userprofile.isAdmin && asset.isSensitive == true && !showSensitive && ignoreCheckRestrict!='yes' && asset.isImage}"/>
</logic:notMatch>
<c:choose>
	<c:when test="${showFlashVideoOnViewDetails && not empty assetForm.asset.encryptedEmbeddedPreviewClipLocation && not beingGenerated}">
		<logic:present parameter="height">
			<bean:parameter id="height" name="height"/>
		</logic:present>
		<logic:notPresent parameter="height">		
			<c:set var="height" value="${flashPlayerHeight}"/>
		</logic:notPresent>
		<logic:present parameter="width">
			<bean:parameter id="width" name="width"/>
		</logic:present>
		<logic:notPresent parameter="width">		
			<c:set var="width" value="${flashPlayerWidth}"/>
		</logic:notPresent>
		<bean:define id="file" name="assetForm" property="asset.encryptedEmbeddedPreviewClipLocation"/>
		<bean:define id="autoplay" value="false"/>
		<c:set var="splashImage" value="../servlet/display.jpg?file=${asset.displayPreviewImageFile.path}"/>
		<c:choose>
			<c:when test="${assetForm.asset.class.simpleName == 'VideoAsset' && useVideoKeywords && assetForm.asset.hasFLVMetaData}">
				<%@include file="../public/inc_flash_player_stream.jsp"%>
			</c:when>
			<c:otherwise>
				<%@include file="../public/inc_flash_player.jsp"%>		
			</c:otherwise>
		</c:choose>		
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${restrict}">
				<c:set var="thumbSrc" value="..${restrictThumb}"/>
			</c:when>
			<c:when test="${hideSensitiveImage}">
				<c:set var="thumbSrc" value="..${sensitiveThumb}"/>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${asset.isImage && userprofile.largeImagesOnView && not empty asset.largeImageFile.path && !overideLargeImageView}">
						<c:set var="thumbSrc" value="../servlet/display?file=${asset.largeImageFile.path}"/>
					</c:when>
					<c:when test="${not empty asset.displayPreviewImageFile.path}">
						<c:set var="thumbSrc" value="../servlet/display?file=${asset.displayPreviewImageFile.path}"/>
					</c:when>
					<c:when test="${empty asset.displayPreviewImageFile.path && not empty assetForm}">
						<c:set var="thumbSrc" value="../servlet/display?file=${assetForm.previewImageFile.path}"/>
					</c:when>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<c:if test="${hideSensitiveImage}">
			<a href="../action/viewAsset?<%= request.getQueryString() %>&showSensitive=true">
		</c:if>
		<img class="<bean:write name='resultImgClass'/>" src="<bean:write name='thumbSrc'/>" alt="Asset Preview" />		
		<c:if test="${hideSensitiveImage}">
			</a>
		</c:if>
	</c:otherwise>
</c:choose>

