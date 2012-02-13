
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:applicationSetting id="hideLightbox" settingName="hide-lightbox"/>

	<%@include file="../inc/asset_panel_download_link.jsp" %>

	<%-- View asset details link --%>
	<a href="viewAsset?<c:out value='${viewUrlParams}' />" class="view"><bright:cmsWrite identifier="link-view-details" filter="false" /></a>
	
	<c:if test="${!userprofile.isFromCms && !hideLightbox}">
		
		<%-- For price bands and if the asset is not free, show a link going to the view page --%>
		<c:choose>
			<c:when test="${ecommerce && usePriceBands}">
				
				
				<logic:notEqual name="item" property="inAssetBox" value="true">
					<a id="ajaxAddToLightBox<c:out value='${item.id}'/>" class="add" href="../action/addToAssetBox?<c:out value='id=${item.id}&${forwardParams}' />">					
						<c:if test="${userprofile.numAssetBoxes>1}">
							<bright:cmsWrite identifier="link-add-to-lightbox" filter="false" />
						</c:if>
						<c:if test="${userprofile.numAssetBoxes<=1}">
							<bright:cmsWrite identifier="link-add-to-mylightbox" filter="false" />
						</c:if>
					</a>
				</logic:notEqual>
				<logic:equal name="item" property="inAssetBox" value="true">
					<a class="in"><c:if test="${userprofile.numAssetBoxes>1}"><bright:cmsWrite identifier="link-in-lightbox" filter="false" /></c:if><c:if test="${userprofile.numAssetBoxes<=1}"><bright:cmsWrite identifier="link-in-my-lightbox" filter="false" /></c:if></a>														
				</logic:equal>
				
				
				
			</c:when>
			<c:otherwise>
				
				<c:if test="${!userprofile.assetBox.shared || userprofile.assetBox.editable}">
					<c:set var="linkText"><bright:cmsWrite identifier="link-add-to-lightbox" filter="false" /></c:set>
						
					
					<a id="ajaxAddToLightBox<c:out value='${item.id}'/>" class="add" href="../action/addToAssetBox?<c:out value='id=${item.id}&${forwardParams}' />" onclick="addToLightBox(this,<c:out value='${item.id}'/>,<c:out value='${userprofile.assetBox.numAssets}'/>, '<c:out value='${forwardParams}'/>'); return false" <logic:notEqual name="item" property="inAssetBox" value="true">style="display:block;"</logic:notEqual>>	
						<c:if test="${userprofile.numAssetBoxes>1}">
							<bright:cmsWrite identifier="link-add-to-lightbox" filter="false" />
						</c:if>
						<c:if test="${userprofile.numAssetBoxes<=1}">
							<bright:cmsWrite identifier="link-add-to-mylightbox" filter="false" />
						</c:if>
					</a>
				</c:if>

				
				<a class="in" <logic:equal name="item" property="inAssetBox" value="true">style="display:block;"</logic:equal>><c:if test="${userprofile.numAssetBoxes>1}"><bright:cmsWrite identifier="link-in-lightbox" filter="false" /></c:if><c:if test="${userprofile.numAssetBoxes<=1}"><bright:cmsWrite identifier="link-in-my-lightbox" filter="false" /></c:if></a>														
				
				
			</c:otherwise>
		</c:choose>
	</c:if>




