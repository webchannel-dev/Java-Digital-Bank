<bright:applicationSetting id="restrictThumb" settingName="restricted-image-featured"/>
<%@include file="../inc/restrict_preview_check.jsp"%>
<c:choose>
	<c:when test="${restrict == true}">
		<c:set var="thumbSrc" value="..${restrictThumb}"/>
	</c:when>
	<c:otherwise>
		<c:set var="thumbSrc" value="../servlet/display?file=${asset.featuredImageFile.path}"/>
	</c:otherwise>
</c:choose>

<img  src="<bean:write name='thumbSrc'/>" alt="Featured Image" class="featuredImage" />