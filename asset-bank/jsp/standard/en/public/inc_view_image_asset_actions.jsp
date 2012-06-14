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
							
   			<%@include file="inc_view_asset_add_to_basket.jsp"%>	
			

			<c:set var="originalImageWidth" value="${assetForm.asset.width}"/>
			<c:set var="originalImageHeight" value="${assetForm.asset.height}"/>
			<c:if test="${!restrict}">
				<%@include file="../inc/view_full_size_links.jsp"%>		
			</c:if>
			<%-- Show open link if PDF (as it is a document but we treat it as an image). Should this be in a class.. e.g. format.canBeOpened ..? Maybe. --%>
    		<c:if test="${showOpenFile && not empty assetForm.asset.format.contentType &&  assetForm.asset.format.fileExtension=='pdf'}">
				<li class="link">
					<a class="view" href="../servlet/display?file=<bean:write name='assetForm' property='encryptedFilePath'/>&contentType=<bean:write name='assetForm' property='asset.format.contentType'/>&filename=<bean:write name='assetForm' property='asset.name'/>.<bean:write name='assetForm' property='asset.format.fileExtension'/>">open document</a>
				</li>
			</c:if>

			
				<c:if test="${userprofile.isAdmin || (nonAdminCanPrint && assetForm.userCanDownloadAsset)}">
					<li class="link">
						<a href="viewPrintImage?id=<c:out value="${assetForm.asset.id}"/>" class="print"><bright:cmsWrite identifier="link-print-image" filter="false"/></a> 
					</li>
				</c:if>	
				
				<c:if test="${ sendAsEcardEnabled }">
					<li class="link">
						<a href="viewSendEcard?id=<c:out value="${assetForm.asset.id}"/>" class="ecard"><bright:cmsWrite identifier="link-send-e-card" filter="false"/></a>
					</li>
				</c:if>
    		</ul>		
    	</div>   <!-- End of actions -->
		
		<c:if test="${!userprofile.largeImagesOnView}">
			<!-- Don't show if on large image view (as no room for the panel) -->
			<%@include file="inc_client_side_edit_panel.jsp"%>
		</c:if>	