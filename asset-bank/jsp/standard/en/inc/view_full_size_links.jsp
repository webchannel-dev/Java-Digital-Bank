	<bright:applicationSetting id="assetRepurposingEnabled" settingName="asset-repurposing-enabled"/>
	<bright:applicationSetting id="canViewLargeSize" settingName="can-view-large-size"/>
	
	<c:if test="${!userprofile.largeImagesOnView}">
		
		<c:set var="previewAssetId" value="${assetForm.asset.id}"/>
		<c:if test="${assetForm.asset.surrogateAssetId>0}">
			<c:set var="previewAssetId" value="${assetForm.asset.surrogateAssetId}"/>
		</c:if>
		
		<logic:equal name="canViewLargeSize" value="true">
			<%-- The following is a maximum size for the 'view large size' image. Change in the settings file as necessary. --%>
			<bright:applicationSetting id="imageSize" settingName="view-large-image-size"/>
			<bright:applicationSetting id="largeAssetToggle" settingName="large-asset-toggle"/>
			<c:if test="${userprofile.userCanViewLargerSize && (originalImageWidth>imageSize || originalImageHeight>imageSize)}">
			<li class="link">
				<c:set var="imageWidth" value="${originalImageWidth}"/>
				<c:set var="imageHeight" value="${originalImageHeight}"/>
	
				<c:if test="${imageSize>0}">
					<%@include file="../inc/calc_view_image_size.jsp"%>
				</c:if>
				
				<a href="../action/viewFullSizedImage?id=<bean:write name='previewAssetId'/>&amp;size=<bean:write name='imageSize'/>" target="_blank" title="<bright:cmsWrite identifier="tooltip-view-image-new-window" filter="false"/>" onclick="popupViewImage(<bean:write name='previewAssetId'/>,<bean:write name='imageWidth'/>,<bean:write name='imageHeight'/>,<c:out value="${assetForm.asset.numPages>1}"/>);return false;" class="view" ><c:choose><c:when test="${largeAssetToggle != 'off'}"><bright:cmsWrite identifier="link-large-image-popup" filter="false" /></c:when><c:otherwise><bright:cmsWrite identifier="link-view-larger" filter="false" /></c:otherwise></c:choose> <%-- <bean:write name='imageWidth' format='0'/> X  <bean:write name='imageHeight' format='0'/> --%></a>
			</li>
			</c:if>
		
		
			<c:if test="${ !(originalImageWidth>imageSize || originalImageHeight>imageSize)}">
				<c:set var="canViewFullSize" value="true"/>		
			</c:if>
			
			<c:if test="${userprofile.userCanViewLargerSize && canViewFullSize}">
				<li class="link">
			
					<%-- The following is a maximum size for the 'view full size' image. Change in the settings file as necessary. -1 means actual size of image--%>
					<bright:applicationSetting id="imageSize" settingName="view-full-image-max-size"/>
			
					<c:set var="imageWidth" value="${originalImageWidth}"/>
					<c:set var="imageHeight" value="${originalImageHeight}"/>
			
					<c:if test="${imageSize>0}">
						<%@include file="../inc/calc_view_image_size.jsp"%>
					</c:if>
					
					<a href="../action/viewFullSizedImage?id=<bean:write name='previewAssetId'/>&amp;size=<bean:write name='imageWidth'/>" target="_blank" onclick="popupViewImage(<bean:write name='previewAssetId'/>,<bean:write name='imageWidth'/>,<bean:write name='imageHeight'/>,<c:out value="${assetForm.asset.numPages>1}"/>); return false;" title="<bright:cmsWrite identifier="tooltip-view-full-image-new-window" filter="false"/>" class="view"><c:choose><c:when test="${largeAssetToggle != 'off'}"><bright:cmsWrite identifier="link-large-image-popup" filter="false" /></c:when><c:otherwise><bright:cmsWrite identifier="link-view-full-size" filter="false" /></c:otherwise></c:choose></a>
					
				</li>
			</c:if>
		</logic:equal>
	</c:if>

	<c:if test="${assetRepurposingEnabled && userprofile.userCanRepurposeAssets && assetForm.asset.isImage && assetForm.userCanDownloadAsset && (assetForm.asset.hasFile || assetForm.asset.surrogateAssetId>0) && empty batchAssetForm}">
		<li class="link" id="embedLink" style="display:none;">
			<c:if test="${assetForm.asset.hasFile}">
				<c:choose>
					<c:when test="${assetForm.asset.format.fileExtension=='pdf'}">
						<a class="embed" href="viewRepurposedImages?id=<bean:write name='assetForm' property='asset.id'/>&amp;embed=true"><bright:cmsWrite identifier="link-embed-as-image" filter="false"/></a>
					</c:when>
					<c:otherwise>
						<a class="embed" href="viewRepurposedImages?id=<bean:write name='assetForm' property='asset.id'/>&amp;embed=true"><bright:cmsWrite identifier="link-embed" filter="false"/></a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${assetForm.asset.surrogateAssetId>0}">
				<a class="embed" href="viewRepurposedImages?id=<bean:write name='assetForm' property='asset.surrogateAssetId'/>&amp;embed=true&amp;parentId=<bean:write name="assetForm" property="asset.id"/>"><bright:cmsWrite identifier="link-embed" filter="false"/></a>
			</c:if>
		</li>
		<%-- If this is a pdf, present the option to embed the file rather than an image version --%>
		<c:if test="${assetForm.asset.format.fileExtension=='pdf'}">
			<li class="link" id="embedLinkFile">
				<a href="viewEmbeddableFile?id=<bean:write name="assetForm" property="asset.id"/>" class="embed"><bright:cmsWrite identifier="link-embed-as-file" filter="false"/></a>
			</li>
		</c:if>
		<script type="text/javascript">
			document.getElementById('embedLink').style.display='block';
			<c:if test="${assetForm.asset.format.fileExtension=='pdf'}">
				document.getElementById('embedLinkFile').style.display='block';
			</c:if>
		</script>
	</c:if>