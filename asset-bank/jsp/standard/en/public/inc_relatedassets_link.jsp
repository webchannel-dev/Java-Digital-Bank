<bright:applicationSetting id="showRelatedAssets" settingName="get-related-assets" />
<bright:applicationSetting id="siblingAssetsEnabled" settingName="show-sibling-assets" />

<c:if test="${showRelatedAssets == true && (not empty assetForm.peerAssets || not empty assetForm.childAssets || not empty assetForm.parentAssets)}">
	<c:if test="${not empty assetForm.parentAssets}">
		<a name="parents"></a>
		<h3>
			<c:if test="${empty assetForm.parentRelationshipName}">
				<bean:size name="assetForm" property="parentAssets" id="numParents"/>
				<c:if test="${numParents==1}">
					<bright:cmsWrite identifier="snippet-parent" case="mixed" filter="false"/>
				</c:if>
				<c:if test="${numParents!=1}">
					<bright:cmsWrite identifier="snippet-parents" case="mixed" filter="false"/>
				</c:if>
			</c:if>
			<c:if test="${not empty assetForm.parentRelationshipName}"><bean:write name="assetForm" property="parentRelationshipName"/></c:if>
			:
			<logic:iterate name="assetForm" property="parentAssets" id="parent" indexId="index">
				<c:if test="${index>0}">,</c:if>
				<a href="../action/viewAsset?id=<bean:write name="parent" property="id"/>"><bean:write name="parent" property="name"/>
				<logic:notMatch name="parent" property="id" value="${parent.name}">(<bean:write name="parent" property="id"/>)</logic:notMatch></a>
			</logic:iterate>
		</h3>
	</c:if>
			
	<c:choose>
		<c:when test="${asset.entity.id<=0}">
			<p><bright:cmsWrite identifier="snippet-related-items" filter="false"/></p>
		</c:when>
		<c:otherwise>
			<c:if test="${(not empty assetForm.siblingAssets && siblingAssetsEnabled) || not empty assetForm.childAssets || not empty assetForm.peerAssets}">
				<p><bright:cmsWrite identifier="snippet-related-asset-lead-in" filter="false"/>
			</c:if>
			<c:if test="${siblingAssetsEnabled}">
				<c:if test="${not empty assetForm.siblingAssets}">
					<bean:size name="assetForm" property="siblingAssets" id="numSiblings"/>
					<a href="#siblings"><bean:write name="numSiblings"/> <c:if test="${numSiblings==1}"><bean:write name="asset" property="entity.termForSibling"/></c:if><c:if test="${numSiblings>1}"><bean:write name="asset" property="entity.termForSiblings"/></c:if></a><c:choose><c:when test="${not empty assetForm.childAssets || not empty assetForm.peerAssets}"> <bright:cmsWrite identifier="snippet-and" filter="false"/></c:when><c:otherwise>.</c:otherwise></c:choose>
				</c:if>
			</c:if>
			<c:if test="${not empty assetForm.childAssets}">
				<bean:size name="assetForm" property="childAssets" id="numChildren"/>
				<a href="#children"><bean:write name="numChildren"/> <c:if test="${numChildren==1}"><bean:write name="asset" property="entity.childRelationshipToName"/></c:if><c:if test="${numChildren>1}"><bean:write name="asset" property="entity.childRelationshipToNamePlural"/></c:if></a><c:choose><c:when test="${not empty assetForm.peerAssets}"> <bright:cmsWrite identifier="snippet-and" filter="false"/></c:when><c:otherwise>.</c:otherwise></c:choose>
			</c:if>
			<c:if test="${not empty assetForm.peerAssets}">
				<bean:size name="assetForm" property="peerAssets" id="numPeers"/>
				<a href="#peers"><bean:write name="numPeers"/> <c:if test="${numPeers==1}"><bean:write name="asset" property="entity.peerRelationshipToName"/></c:if><c:if test="${numPeers>1}"><bean:write name="asset" property="entity.peerRelationshipToNamePlural"/></c:if></a>.
			</c:if>
			<c:set var="hasParentInfo"><bright:cmsWrite identifier="snippet-has-parent" filter="false"/></c:set>
			<c:if test="${not empty assetForm.parentAssets && not empty hasParentInfo}">- <bright:cmsWrite identifier="snippet-has-parent" filter="false"/></c:if>
			</p>
		</c:otherwise>
	</c:choose>
</c:if>	

<%-- Show any attributes from parents that have ShowOnChild set --%>
<c:set var="parentAttributesMap" value="${assetForm.parentAttributes}" />
<c:if test="${showRelatedAssets == true && not empty assetForm.parentAssets && !empty parentAttributesMap}">

	<logic:iterate name="assetForm" property="parentAssets" id="parent" indexId="index">
		
		<c:set var="vecAttributes" value="${parentAttributesMap[parent.id]}" />
		<c:if test="${!empty vecAttributes}">
			
			<div id="attributeGroupHeading">
				<a href="javascript:showHide('parentAttributeGroup<c:out value="${parent.id}"/>','headerIcon<c:out value="${parent.id}"/>');">
					<h3>
						<c:if test="${not empty assetForm.parentRelationshipName}"><bean:write name="assetForm" property="parentRelationshipName"/></c:if>
						<bean:write name="parent" property="name"/>
						<logic:notMatch name="parent" property="id" value="${parent.name}">(<bean:write name="parent" property="id"/>)</logic:notMatch>
					</h3>
					<img border="0" id="headerIcon<c:out value="${parent.id}"/>" align="right" style="position: relative; top: -11px;" src="../images/standard/icon/subtract.gif">
				</a>
			</div>
			<div class="attributeGroupPanel" id="parentAttributeGroup<c:out value="${parent.id}"/>" style="display:block;">
			
			<table class="form stripey" name="stripey" cellspacing="0" cellpadding="0">
			
				<c:set var="reqLastAttId" scope="request" value="0"/>
				<logic:iterate name="vecAttributes" id="attributeValue">
				
					<%--  Pass attributeValue, reqHideLabels and reqLastAttId into request scope --%>			 
					<c:set var="attributeValue" scope="request" value="${attributeValue}"/> 
					<c:set var="reqHideLabels" scope="request" value="${hideLabels}"/> 
					<c:set var="assetIdForAttributes" scope="request" value="${parent.id}"/>
					<jsp:include flush="true" page="../public/inc_view_attribute_field.jsp"/> 

					<c:set var="reqLastAttId" scope="request" value="${attributeValue.attribute.id}"/>
					
				</logic:iterate>
			</table>	
			
			</div>
		
		</c:if>
	</logic:iterate>

</c:if>
