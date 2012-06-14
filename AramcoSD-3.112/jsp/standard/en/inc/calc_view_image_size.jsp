<c:choose>
	<c:when test="${ (imageWidth >= imageHeight) && (imageWidth>imageSize)}">
		<c:choose>
			<c:when test="${ imageWidth>imageSize}">
				<c:set var="imageHeight" value="${(imageHeight*imageSize)/imageWidth}"/>
				<c:set var="imageWidth" value="${imageSize}"/>
			</c:when>
			<c:otherwise>
				<c:set var="imageSize" value="${imageWidth}"/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${ imageHeight>imageSize}">
				<c:set var="imageWidth" value="${(imageWidth*imageSize)/imageHeight}"/>
				<c:set var="imageHeight" value="${imageSize}"/>
			</c:when>
			<c:otherwise>
				<c:set var="imageSize" value="${imageHeight}"/>
			</c:otherwise>
		</c:choose>
	</c:otherwise>				
</c:choose>