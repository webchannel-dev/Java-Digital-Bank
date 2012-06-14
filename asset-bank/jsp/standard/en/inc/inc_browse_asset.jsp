<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:empty name="item" property="displayHomogenizedImageFile.path">
	<c:set var="resultImgClass" value="icon"/>
</logic:empty>
<logic:notEmpty name="item" property="displayHomogenizedImageFile.path">
	<c:set var="resultImgClass" value="image"/>
</logic:notEmpty>
<c:set var="stateClasses"><logic:equal name="hideThumbnails" value="true">noThumb clearfix </logic:equal>
<c:if test="${not empty item.entity.name}"><bean:write name="item" property="entity.compactName"/> </c:if>
<c:if test="${highlightIncompleteAssets && not item.complete}">incomplete </c:if>
<c:if test="${highlightRestrictedAssets && item.isRestricted}">restricted </c:if>
<c:if test="${item.agreementTypeId == 2}">agreementApplies</c:if>
<c:if test="${item.agreementTypeId == 1}">unrestricted</c:if>
</c:set>

	<%-- There is always a thumbnail for images --%>
	<c:if test="${item.typeId==2}">
		<c:set var="resultImgClass" value="image"/>
	</c:if>

	<%-- finally decide whether to show in list view --%>
	<c:choose>
		<c:when test="${panel.view == 'list'}">
			<tr>
		</c:when>
		<c:when test="${panel.view == 'minimal'}">
			<tr class="compact">
		</c:when>
		<c:otherwise>
			<li class="assetList <c:out value='${stateClasses}'/>">
		</c:otherwise>
	</c:choose>	


	
	<%-- Set up variables for URLs --%>
	<c:set var="posParams" value="index=${item.position}&total=${browseItemsForm.searchResults.numResults}" />
	
	<c:choose>
		<c:when test="${empty popularityId}">
			<c:set var="posParams"><bean:write name="posParams" filter="false"/>&categoryId=<c:out value="${browseItemsForm.category.id}"/>&categoryTypeId=<c:out value="${browseItemsForm.category.categoryTypeId}"/>&collection=<c:out value="${browseItemsForm.category.name}"/>&sortAttributeId=<c:out value="${browseItemsForm.sortAttributeId}"/>&sortDescending=<c:out value="${browseItemsForm.sortDescending}"/></c:set>
		</c:when>
		<c:when test="${not empty popularityId}">
			<c:set var="posParams"><bean:write name="posParams" filter="false"/>&popularityId=<bean:write name="popularityId"/></c:set>
		</c:when>
	</c:choose>

	<c:if test="${!empty browseItemsForm.selectedFilter}">
		<c:set var="posParams"><bean:write name="posParams" filter="false"/>&filterId=<bean:write name="browseItemsForm" property="selectedFilter.id"/></c:set>
	</c:if>
	
	<c:set var="viewUrlParams" value="id=${item.id}&${posParams}" />
																
	<%-- finally decide whether to show in list view --%>
	<c:choose>
		<c:when test="${panel.view == 'list'}">
				<%@include file="../inc/result_list_asset.jsp"%>
				
				<logic:notEqual name="hideThumbnails" value="true">
				<td class="action">
					<%-- Add to lightbox link --%>
					<%-- Pass beans: item, viewUrl, forwardParams --%>
					<%@include file="../inc/add_to_lightbox.jsp"%>
				</td>
			</logic:notEqual>
				
			</tr>
		</c:when>
		<c:when test="${panel.view == 'minimal'}">
			<%@include file="../inc/result_compact_asset.jsp"%>
			
			<logic:notEqual name="hideThumbnails" value="true">
			<td class="action">
				<%-- Add to lightbox link --%>
				<%-- Pass beans: item, viewUrl, forwardParams --%>
				<%@include file="../inc/add_to_lightbox.jsp"%>
			</td>
		</logic:notEqual>
			
		</tr>
		</c:when>
		<c:otherwise>
			<%@include file="../inc/result_asset.jsp"%>
	
			<logic:notEqual name="hideThumbnails" value="true">
				<p class="action">
					<%-- Add to lightbox link --%>
					<%-- Pass beans: item, viewUrl, forwardParams --%>
					<%@include file="../inc/add_to_lightbox.jsp"%>
				</p>
			</logic:notEqual>
			
			
			</li>
		</c:otherwise>
	</c:choose>	
	
