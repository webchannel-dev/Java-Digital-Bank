<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="hideThumbnails" settingName="hide-thumbnails-on-browse-search"/>
<bright:applicationSetting id="highlightIncompleteAssets" settingName="highlight-incomplete-assets"/>
<bright:applicationSetting id="highlightRestrictedAssets" settingName="highlight-restricted-assets"/>

<%@include file="/jsp/standard/en/inc/set_this_page_url.jsp"%>

<ul class="lightbox clearfix" style="margin-top: 1em;">
	<logic:iterate name="searchResults" id="item" indexId="index">
		<li class="assetList <logic:equal name="hideThumbnails" value="true">noThumb clearfix </logic:equal><c:if test="${not empty item.entity.name}"><bean:write name="item" property="entity.compactName"/> </c:if><c:if test="${highlightIncompleteAssets && not item.complete}">incomplete </c:if><c:if test="${highlightRestrictedAssets && (item.isRestricted || item.agreementTypeId == 3)}">restricted </c:if><c:if test="${item.agreementTypeId == 2}">agreementApplies</c:if><c:if test="${item.agreementTypeId == 1}">unrestricted</c:if>">
			<logic:empty name="item" property="displayHomogenizedImageFile.path">
				<c:set var="resultImgClass" value="icon"/>
			</logic:empty>
			<logic:notEmpty name="item" property="displayHomogenizedImageFile.path">
				<c:set var="resultImgClass" value="image"/>
			</logic:notEmpty>
	
			<%-- There's always a thumbnail for images --%>
			<c:if test="${item.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if>
			
			<%-- Set specific variables used in result_asset.jsp and add_to_lightbox.jsp--%>
			<c:set scope="request" var="viewUrlParams" value="id=${item.id}" />
			<c:set scope="request" var="forwardParams" value="forward=${thisUrl}" />

			<%@include file="/jsp/standard/en/inc/result_asset.jsp"%>
			
			<%-- Action links/buttons --%>
			<logic:notEqual name="hideThumbnails" value="true">
				<p class="action">
					<%@include file="/jsp/standard/en/inc/add_to_lightbox.jsp"%>
				</p>
			</logic:notEqual>
		</li>
	</logic:iterate>
</ul>