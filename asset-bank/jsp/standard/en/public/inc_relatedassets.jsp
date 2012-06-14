<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

		<a name="related"></a>
		
		<bright:applicationSetting id="showRelatedAssets" settingName="get-related-assets"/>
		<bright:applicationSetting id="assetEntitiesEnabled" settingName="asset-entities-enabled"/>
		<bright:applicationSetting id="showEditRelationships" settingName="show-edit-relationships"/>
		<bright:applicationSetting id="groupedAssets" settingName="group-related-assets"/>
		<bright:applicationSetting id="enableSiblings" settingName="show-sibling-assets"/>
		<bright:applicationSetting id="linkToDownloadPage" settingName="related-asset-thumbnails-link-to-download"/>
		<bright:applicationSetting id="maxNumberToShow" settingName="related-assets-max-for-view"/>
		<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled"/>
		
		<c:if test="${not empty hideEditRelationships && hideEditRelationships}">
			<c:set var="showEditRelationships" value="${!hideEditRelationships}"/>		
		</c:if>
		
		<c:choose>
			<c:when test="${!linkToDownloadPage || !assetForm.asset.hasFile}"><c:set var="thumbUrl">viewAsset</c:set></c:when>
			<c:when test="${assetForm.asset.isImage}"><c:set var="thumbUrl">viewDownloadImage</c:set></c:when>
			<c:when test="${assetForm.asset.isVideo}"><c:set var="thumbUrl">viewDownloadVideo</c:set></c:when>
			<c:when test="${assetForm.asset.hasFile}"><c:set var="thumbUrl">viewDownloadFile</c:set></c:when>
		</c:choose>
		
		
		<c:if test="${showRelatedAssets}">
			<c:set var="linkDisabled" value="${backgroundEditInProgress || !assetForm.asset.thisCurrentVersion}" />
			<c:set var="showChildren" value="${(assetForm.asset.entity.allowChildren && assetForm.userCanUpdateAsset) || not empty assetForm.childAssets}"/>
			<c:set var="showPeers" value="${(assetForm.asset.entity.allowPeers && assetForm.userCanUpdateAsset) || (not empty assetForm.peerAssets || not empty assetForm.groupedPeerAssets)}"/>
			<c:set var="showSiblings" value="${enableSiblings && not empty assetForm.siblingAssets}"/>

			<c:if test="${showChildren || showPeers || showSiblings}"><h3 style="margin-top:1em"><bright:cmsWrite identifier="subhead-asset-relationships" case="mixed" replaceVariables="true" filter="false" /></h3></c:if>
			
			<p>
				<bean:define id="first" value="true"/>
				<c:if test="${showEditRelationships && assetForm.userCanUpdateAsset && (assetForm.asset.entity.allowChildren || assetForm.asset.entity.allowPeers || assetForm.entityCanHaveParents)}">
					<bright:a disabled="${linkDisabled}" href="../action/viewEditAssetRelationships?id=${assetForm.asset.id}"><bright:cmsWrite identifier="link-edit-relationships" filter="false"/></bright:a>
					<bean:define id="first" value="false"/>
				</c:if>
				<c:if test="${assetEntitiesEnabled && assetForm.asset.entity.hasDefaultRelationship && assetForm.userCanUpdateAsset}">
					 <c:if test="${!first}">| </c:if><bright:a disabled="${linkDisabled}" href="viewDefaultRelationshipsForAsset?id=${assetForm.asset.id}"><bright:cmsWrite identifier="link-default-relationships" replaceVariables="true" filter="false" /></bright:a>
					<bean:define id="first" value="false"/>
				</c:if>
				<c:if test="${assetEntitiesEnabled && assetForm.userCanUpdateAsset && (assetForm.asset.entity.allowPeers || assetForm.asset.entity.allowChildren)}">
					 <c:if test="${!first}">| </c:if><bright:a disabled="${linkDisabled}" href="viewAddEmptyRelatedAssets?id=${assetForm.asset.id}"><bright:cmsWrite identifier="link-add-empty-related-assets" replaceVariables="true" filter="false" /></bright:a>
				</c:if>
			</p>
			
			<c:if test="${showSiblings}">
				<a name="siblings"></a>
				<h3 style="margin-top:1em"><bright:cmsWrite identifier="snippet-other" filter="false" case="mixed"/> <bean:write name="assetForm" property="siblingRelationshipName"/>:</h3>
				
				<ul class="lightbox clearfix">
					<bean:define id="asset" name="assetForm" property="asset"/>
					<c:set var="relationshipTypeId" value="4"/>
					<logic:iterate name="assetForm" property="siblingAssets" id="relatedAsset" indexId="index">
						<bean:define id="relatedAsset" name="relatedAsset"/>
						<%@include file="../public/inc_relatedasset_asset.jsp"%>
					</logic:iterate>
				</ul>
				<bean:size name="assetForm" property="siblingAssets" id="numRelatedAssets"/>
				<c:if test="${numRelatedAssets == maxNumberToShow}">
					<bright:cmsWrite identifier="snippet-too-many-related-assets-to-show" filter="false"/>
				</c:if>
			
			</c:if>
		
			<c:if test="${showChildren}">
						
				<c:if test="${not empty assetForm.parentAssets}">
					<div class="hr"></div>
				</c:if>
				
				<a name="children"></a>
				<h3 style="margin-top:1em"><bean:write name="assetForm" property="asset.entity.childRelationshipToNamePlural" />:</h3>
				
				<c:if test="${not empty assetForm.childAssets}">
					<ul class="lightbox clearfix">
						<bean:define id="asset" name="assetForm" property="asset"/>
						<bean:size name="assetForm" property="childAssets" id="numRelatedAssets"/>
						<c:set var="relationshipTypeId" value="2"/>
						<logic:iterate name="assetForm" property="childAssets" id="relatedAsset" indexId="index">
							<bean:define id="relatedAsset" name="relatedAsset"/>
							<c:set var="lastRelatedAsset" value="${index == numRelatedAssets-1}"/>
							<%@include file="../public/inc_relatedasset_asset.jsp"%>	 		
						</logic:iterate>
					</ul>
					<c:if test="${numRelatedAssets == maxNumberToShow}">
						<bright:cmsWrite identifier="snippet-too-many-related-assets-to-show" filter="false"/>
					</c:if>
				</c:if>
				<c:if test="${empty assetForm.childAssets}">
					<p>There are currently no <bean:write name="assetForm" property="asset.entity.childRelationshipToNamePlural" /></p>
				</c:if>
				
				<c:if test="${assetForm.userCanUpdateAsset}">
						
					<%--  Further need upload for first two links --%>
					<c:if test="${userprofile.canUploadAtAll}">
						<c:if test="${assetForm.asset.entity.hasNonExtensionAssetChildren && not empty assetForm.userEditableChildAssetEntityIds}"><bright:a disabled="${linkDisabled}" href="../action/viewUploadAssetFile?parentId=${assetForm.asset.id}&entityRestrictions=${assetForm.userEditableChildAssetEntityIds}"><bright:cmsWrite identifier="link-add-single" filter="false"/> <bean:write name="assetForm" property="asset.entity.childRelationshipToName" /></bright:a> |
						<bright:a disabled="${linkDisabled}" href="../action/viewDataImport?parentId=${assetForm.asset.id}"><bright:cmsWrite identifier="link-add-multiple" filter="false"/> <bean:write name="assetForm" property="asset.entity.childRelationshipToNamePlural" /></bright:a> |</c:if>
					</c:if>
					<c:set var="selectAssetBySearchUrl">../action/selectAssetBySearch?selectUrl=relateSelectedAssets&amp;relationName=<bright:write name="assetForm" property="asset.entity.childRelationshipToNamePlural" encodeForUrl="true"/>&amp;newSearch=true&amp;relationshipTypeId=2&amp;sourceAssetId=<bean:write name="assetForm" property="asset.id"/><logic:iterate name="assetForm" property="asset.entity.childRelationships" id="entityRel"><c:if test="${entityRel.userEditable}">&amp;selectedEntities=<bean:write name='entityRel' property='relatesToAssetEntityId' /></c:if></logic:iterate></c:set>
					<bright:a disabled="${linkDisabled}" href="${selectAssetBySearchUrl}"><bright:cmsWrite identifier="link-find-multiple" filter="false"/> <bean:write name="assetForm" property="asset.entity.childRelationshipToNamePlural" /></bright:a>
				</c:if>

			</c:if>
			
			<c:if test="${showPeers}">

				<c:if test="${showChildren || not empty assetForm.siblingAssets}">
					<div class="hr"></div>
				</c:if>

				<a name="peers"></a>
				<c:choose>
					<c:when test="${not empty assetForm.asset.entity.peerRelationshipToNamePlural}">
						<h3 style="margin-top:1em"><bean:write name="assetForm" property="asset.entity.peerRelationshipToNamePlural" />:</h3>
					</c:when>
					<c:otherwise>
						<h3 style="margin-top:1em"><bright:cmsWrite identifier="heading-related-items" filter="false" case="mixed"/></h3>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${groupedAssets}">
						<table cellspacing="0" cellpadding="0" class="related">
						<logic:notEmpty name="assetForm" property="peerAssetCategories">
							<logic:iterate name="assetForm" property="peerAssetCategories" id="categoryName">
								<logic:present name="assetForm" property="peerAssetsForCategory(${categoryName})">
									<tr>
										<th colspan="3"><bean:write name="categoryName"/></th>
									</tr>
									<logic:iterate name="assetForm" property="peerAssetsForCategory(${categoryName})" id="relatedAsset">
										<tr>
											<td class="id"><bean:write name="relatedAsset" property="id"/></td>
											<td><bean:write name="relatedAsset" property="searchName"/></td>
											<td class="links"><a class="view" href="viewAsset?id=<bean:write name='relatedAsset' property='id'/>"><bright:cmsWrite identifier="link-view-details" filter="false" /></a>&nbsp;|&nbsp;<bright:a disabled="${linkDisabled}" styleClass="view" href="deleteAssetRelationship?parentId=${assetForm.asset.id}&childId=${relatedAsset.id}&relationshipTypeId=1#related"><bright:cmsWrite identifier="link-remove-link" filter="false" /></bright:a></td>
										</tr>
									</logic:iterate>
								</logic:present>
							</logic:iterate>
						</logic:notEmpty>
						</table>	
					</c:when>
					<c:otherwise>
						<c:if test="${not empty assetForm.peerAssets}">
							<ul class="lightbox clearfix">
								<bean:define id="asset" name="assetForm" property="asset"/>
								<c:set var="relationshipTypeId" value="1"/>
								<logic:iterate name="assetForm" property="peerAssets" id="relatedAsset" indexId="index">
									<bean:define id="relatedAsset" name="relatedAsset"/>				
									<%@include file="../public/inc_relatedasset_asset.jsp"%>	 		
								</logic:iterate>
							</ul>
							<bean:size name="assetForm" property="peerAssets" id="numRelatedAssets"/>
							<c:if test="${numRelatedAssets == maxNumberToShow}">
								<bright:cmsWrite identifier="snippet-too-many-related-assets-to-show" filter="false"/>
							</c:if>
						</c:if>
					</c:otherwise>
				</c:choose>
				<c:if test="${empty assetForm.peerAssets && empty assetForm.groupedPeerAssets}">
					<p>There are currently no <bean:write name="assetForm" property="asset.entity.peerRelationshipToNamePlural" /></p>
				</c:if>
				<c:if test="${assetForm.asset.entity.id>0 && assetForm.userCanUpdateAsset}">
					<c:if test="${assetForm.asset.entity.hasNonExtensionAssetPeers}"><bright:a disabled="${linkDisabled}" href="../action/viewUploadAssetFile?peerId=${assetForm.asset.id}"><bright:cmsWrite identifier="link-add-single" filter="false"/> <bean:write name="assetForm" property="asset.entity.peerRelationshipToName" /></bright:a> |</c:if>
					<%-- id="selectPeerAssetBySearch" is used by Selenium tests --%>
					<c:set var="selectAssetBySearchUrl">../action/selectAssetBySearch?selectUrl=relateSelectedAssets&amp;relationName=<bright:write name="assetForm" property="asset.entity.peerRelationshipToNamePlural" encodeForUrl="true"/>&amp;newSearch=true&amp;relationshipTypeId=1&amp;sourceAssetId=<bean:write name="assetForm" property="asset.id"/><logic:iterate name="assetForm" property="asset.entity.peerRelationships" id="entityRel">&amp;selectedEntities=<bean:write name='entityRel' property='relatesToAssetEntityId' /></logic:iterate></c:set>
					<bright:a disabled="${linkDisabled}" styleId="selectPeerAssetBySearch" href="${selectAssetBySearchUrl}"><bright:cmsWrite identifier="link-find-multiple" filter="false"/> <bean:write name="assetForm" property="asset.entity.peerRelationshipToNamePlural" /></bright:a>
				</c:if>
			</c:if>
		</c:if>

		

