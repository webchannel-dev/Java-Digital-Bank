<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<bright:applicationSetting id="highlightIncompleteAssets" settingName="highlight-incomplete-assets"/>
<bright:applicationSetting id="highlightRestrictedAssets" settingName="highlight-restricted-assets"/>
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

			<c:if test="${relatedAsset.typeId!=2}">
				<c:set var="resultImgClass" value="icon"/>
			</c:if>
			<c:if test="${relatedAsset.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if>
	
			<li class="<c:if test="${not empty relatedAsset.entity.name}"><bean:write name="relatedAsset" property="entity.name"/> </c:if><c:if test="${highlightIncompleteAssets && not relatedAsset.complete}">incomplete </c:if><c:if test="${highlightRestrictedAssets && relatedAsset.isRestricted}">restricted </c:if>">
				<div class="detailWrapper">
					
					<a class="thumb" href="<bean:write name='thumbUrl'/>?id=<bean:write name="relatedAsset" property="id"/>">
						<%-- temporarily set the asset bean to be the related asset so that we can use the thumbnail include --%>
						<bean:define id="tempAsset" name="asset"/>
						<bean:define id="asset" name="relatedAsset" />
						<%@include file="../inc/view_thumbnail.jsp"%>	
						
						<%-- set it back to the main asset for the rest of the page --%>
						<bean:define id="asset" name="tempAsset" />
					</a>
					<br />
					
					<%-- output the relationship description if present and if this isnt a multi lingual asset bank --%>
					<c:if test="${!supportMultiLanguage}">
						<c:set var="relDesIdentifier" value="${asset.id}:${relationshipTypeId}" />
						<bean:define id="identifier" name="relDesIdentifier" />
						<bean:define id="relationshipDescription" name="relatedAsset" property='<%= "relationshipDescriptionFromSearchString(" + identifier + ")" %>'/>

						<c:set var="relDesEntityId" value="${relatedAsset.entity.id}" />
						<bean:define id="entityId" name="relDesEntityId" />
						<c:choose>
							<c:when test="${relationshipTypeId==1}">
								<bean:define id="relationshipDescriptionLabel" name="asset" property='<%= "entity.relationshipDescriptionLabelForPeer(" + entityId + ")" %>'/>
							</c:when>
							<c:when test="${relationshipTypeId==2}">
								<bean:define id="relationshipDescriptionLabel" name="asset" property='<%= "entity.relationshipDescriptionLabelForChild(" + entityId + ")" %>'/>
							</c:when>
						</c:choose>
						<logic:notEmpty name='relationshipDescription'>
							<bean:write name='relationshipDescriptionLabel' />: <bean:write name='relationshipDescription' />
						</logic:notEmpty> 
					</c:if>
					
					<bean:define id="item" name="relatedAsset"/>
					<c:set var="viewUrlParams" value="id=${item.id}" />	
					<%@include file="../inc/result_asset_descriptions.jsp"%> 
	
	            	<bean:define id="bShowApprovalNotes" value="false"/>
				</div>	<!-- end of detailWrapper -->	
				<p class="action">
					
					<%@include file="../inc/asset_panel_download_link.jsp" %>
					
					<bean:define id="item" name="relatedAsset" />
					<%@include file="../inc/add_to_lightbox.jsp"%>
					
					<c:if test="${relationshipTypeId>0 && (userprofile.isAdmin == true || assetForm.userCanUpdateAsset == true)}">
						<c:if test="${!supportMultiLanguage}">			
							<logic:notEmpty name='relationshipDescriptionLabel'>
								<a class="view" href="viewEditRelatedAssetDescriptions?id=<bean:write name='assetForm' property='asset.id'/>&<c:choose><c:when test='${relationshipTypeId==1}'>peerAssetIds</c:when><c:when test='${relationshipTypeId==2}'>childAssetIds</c:when></c:choose>=<bean:write name='relatedAsset' property='id' />&amp;edit=true">Edit <bean:write name='relationshipDescriptionLabel' /></a>
							</logic:notEmpty>
						</c:if>
						<c:choose>
    						<c:when test="${relatedAsset.userDeletableRelationship && assetForm.userCanUpdateAsset}">
								<a class="view " href="deleteAssetRelationship?parentId=<bean:write name='assetForm' property='asset.id'/>&childId=<bean:write 
								name='relatedAsset' property='id'/>&relationshipTypeId=<bean:write name="relationshipTypeId"/>#related" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-delete-relationship"/>');"><bright:cmsWrite identifier="link-remove-link" filter="false"/></a>
							</c:when>
							<c:otherwise>
								<span class="view disabled"><bright:cmsWrite identifier="link-remove-link" filter="false"/></span><br />							
							</c:otherwise>
						</c:choose>
						<c:if test="${relationshipTypeId==2 && lastRelatedAsset}">
							<span class="view disabled">Move down list</span>
						</c:if>
						<c:if test="${relationshipTypeId==2 && !lastRelatedAsset}">
							<a class="view" href="moveAssetRelationship?id=<bean:write name='assetForm' property='asset.id'/>&childId=<bean:write name='relatedAsset' property='id'/>&i=<bean:write name="index"/>">Move down list</a>
						</c:if>
					</c:if>
				</p>
			</li>
		

