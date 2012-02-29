<bright:applicationSetting id="restrictThumb" settingName="restricted-image-homogenized"/>
<bright:applicationSetting id="includeDims" settingName="thumbnails-cropped-not-scaled"/>
<%@include file="../inc/restrict_preview_check.jsp"%>
<c:choose>
	<c:when test="${restrict == true}">
		<c:set var="thumbSrc" value="..${restrictThumb}"/>
	</c:when>
	<c:otherwise>
		<c:set var="thumbSrc" value="../servlet/display?file=${asset.homogenizedImageFile.path}"/>
	</c:otherwise>
</c:choose>
<logic:notEqual name='includeDims' value='true'>
	<style type="text/css">
		/*center images within box if retaining aspect ratios*/
		#homePage a.imgWrapper {  
			height:<c:out value='${thumbnailWidth + 6}'/>px !important;
			width:<c:out value='${thumbnailWidth + 6}'/>px !important;
			text-align: center;
			line-height:<c:out value='${thumbnailWidth + 6}'/>px !important;
			}	
			
		#homePage a.imgWrapper img {  
			margin-left: auto !important;
		   margin-right: auto !important;
			display:inline !important; 
			vertical-align:middle
			}	
	</style>
</logic:notEqual>
<img src="<bean:write name='thumbSrc'/>" <logic:equal name='includeDims' value='true'>width="<c:out value='${thumbnailWidth}'/>px" height="<c:out value='${thumbnailHeight}'/>px"</logic:equal>  alt="<c:out value='${image.searchName}'/>" />

