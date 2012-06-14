<c:choose>
	<c:when test="${asset.isVideo}">
		<c:set var="assetTypeName"><bright:cmsWrite identifier="video" case="mixed" filter="false"/></c:set>
	</c:when>
	<c:when test="${asset.isAudio}">
		<c:set var="assetTypeName"><bright:cmsWrite identifier="audio" case="mixed" filter="false"/></c:set>
	</c:when>
	<c:when test="${asset.isImage}">
		<c:set var="assetTypeName"><bright:cmsWrite identifier="image" case="mixed" filter="false"/></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="assetTypeName"><bright:cmsWrite identifier="file" case="mixed" filter="false"/></c:set>
	</c:otherwise>
</c:choose>