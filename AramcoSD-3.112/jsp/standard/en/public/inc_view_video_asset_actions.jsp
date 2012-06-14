<c:choose>
	<c:when test="${ecommerce}">
		<div class="actions add-to-basket">
	</c:when>
	<c:otherwise>
		<div class="actions">
	</c:otherwise>
</c:choose>

	<ul>		
		<%-- Button area --%>
		<c:set var="viewUrl" scope="request" value="${viewUrl}"/>
		<c:set var="pos" scope="request" value="${pos}"/>
		<c:set var="params" scope="request" value="${params}"/>
		<jsp:include page="inc_view_asset_buttons.jsp"/>

		<c:if test="${assetRepurposingEnabled && userprofile.userCanRepurposeAssets && assetForm.userCanDownloadAsset && (assetForm.asset.hasFile || assetForm.asset.surrogateAssetId>0)}">
			<li class="link"  id="embedLink" style="display:none;">
				<c:if test="${assetForm.asset.hasFile}">
					<a class="embed" href="viewRepurposedVideos?id=<bean:write name='assetForm' property='asset.id'/>&embed=true"><bright:cmsWrite identifier="link-embed" filter="false"/></a>
				</c:if>
				<c:if test="${assetForm.asset.surrogateAssetId>0}">
					<a class="embed" href="viewRepurposedVideos?id=<bean:write name='assetForm' property='asset.surrogateAssetId'/>&embed=true&parentId=<bean:write name="assetForm" property="asset.id"/>"><bright:cmsWrite identifier="link-embed" filter="false"/></a>
				</c:if>
			</li>
			<script type="text/javascript">
				document.getElementById('embedLink').style.display='block';
			</script>
		</c:if>

		<%@include file="inc_view_asset_add_to_basket.jsp"%>	
		<logic:equal name="restrict" value="false">
			<c:choose>
				<c:when test="${showOpenFile || assetForm.asset.format.canViewOriginal}">
					<c:choose>
	    				<c:when test="${not empty assetForm.asset.format.viewFileInclude}">
	    					<li class="link">
	    						<c:set var="url">../action/viewCustomFileTypePreview?viewFileInclude=<bright:write name="assetForm" property="asset.format.viewFileInclude" filter="false" encodeForUrl="true"/>&file=<bean:write name='assetForm' property='asset.originalFileLocation'/>&id=<bean:write name='assetForm' property='asset.id'/>&filename=<bean:write name='assetForm' property='asset.originalFilename'/>&width=<bean:write name='assetForm' property='asset.width'/>&height=<bean:write name='assetForm' property='asset.height'/></c:set>
	    						<a class="view" href="<bean:write name="url" filter="false"/>" target="_blank" onclick="popupPreview('<bean:write name="url" filter="false"/>',<bright:write name="assetForm" property="asset.width"/>,<bright:write name="assetForm" property="asset.height"/>+20); return false;"><bright:cmsWrite identifier="link-view-full-version" filter="false"/></a>
	    					</li>
	    				</c:when>
	    				<c:when test="${not empty assetForm.asset.format.contentType}">
	    					<li class="link">
		    					<a class="view" href="../servlet/display?file=<bean:write name='assetForm' property='encryptedFilePath'/>&contentType=<bean:write name='assetForm' property='asset.format.contentType'/>&filename=<bean:write name='assetForm' property='asset.name'/>.<bean:write name='assetForm' property='asset.format.fileExtension'/>">open file</a>
		    				</li>
	    				</c:when>
	    			</c:choose>
				</c:when>
				<c:when test="${!showFlashVideoOnViewDetails && not empty assetForm.asset.encryptedEmbeddedPreviewClipLocation}">
					<%@include file="inc_preview_link.jsp"%>
				</c:when>
			</c:choose>
		</logic:equal>
		

	</ul>		

</div>   <!-- End of actions -->