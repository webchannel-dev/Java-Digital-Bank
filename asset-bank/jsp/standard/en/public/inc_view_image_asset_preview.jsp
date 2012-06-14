		<div class="floatLeft actions image-options" style="clear:left">
			<bean:define id="asset" name="assetForm" property="asset" />
			<%@include file="../inc/view_preview.jsp"%>		
			<%-- If using price bands, then show the view links here - BB commented this out as I have moved them back to the normal place
			<c:if test="${usePriceBands}">
				<ul>
					<c:set var="originalImageWidth" value="${assetForm.asset.width}"/>
					<c:set var="originalImageHeight" value="${assetForm.asset.height}"/>
					<logic:equal name="restrict" value="false">
						<%@include file="../inc/view_full_size_links.jsp"%>		
					</logic:equal>
				</ul>
			</c:if> 
			 --%>
		</div>