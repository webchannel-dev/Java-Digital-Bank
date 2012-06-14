<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:notPresent name="sectionPage">
	<bean:define id="sectionPage" value="promoted"/>
</logic:notPresent>

<li>
	<div class="detailWrapper">		
		<a href="../action/viewAsset?id=<bean:write name='item' property='id'/>&index=<bean:write name='item' property='position'/>&total=<bean:write name='browseItemsForm' property='searchResults.numResults'/>&promoted=<bean:write name='promoted'/>&recent=<bean:write name='recent'/>&page=<bean:write name='browseItemsForm' property='searchResults.pageIndex'/>&pageSize=<bean:write name='browseItemsForm' property='searchResults.pageSize'/>" class="thumb">
			<bean:define id="asset" name="item" />
			<%@include file="../inc/view_thumbnail.jsp"%>		
		</a>
		<div class="metadataWrapper">
			<c:choose>
				<c:when test="${asset.isVideo}">
					<%-- Icon for video  --%>
					<img src="../images/standard/icon/video.gif" border="0" width="15" height="11" alt="Video" class="media_icon" />
				</c:when>
				<c:when test="${asset.isAudio}">
					<%-- Icon for audio  --%>
					<img src="../images/standard/icon/audio.gif" border="0" width="13" height="12" alt="Audio" class="media_icon" />
				</c:when>
			</c:choose>	
			<c:set var="viewUrlParams" value="id=${item.id}&index=${item.position}&total=${browseItemsForm.searchResults.numResults}&promoted=${promoted}&recent=${recent}&page=${browseItemsForm.searchResults.pageIndex}&pageSize=${browseItemsForm.searchResults.pageSize}" />															
			<%@include file="../inc/result_asset_descriptions.jsp"%>
			<%@include file="../inc/inc_result_views_ratings.jsp"%>

		</div>

	
	</div>	<!-- end of detailWrapper -->	
	<%-- Output any attributes with icons here --%>
    <div class="iconAttrWrapper">
		<logic:iterate name="descriptions" id="description" indexId="index">
			<c:if test="${not empty description && not empty description.description}">
				<c:if test="${ not empty description.iconFile }">
					<p class="iconAttr <c:out value='${description.classIdentifier}'/>">
						<logic:equal name="description" property="isLink" value="true">
							<a href="viewAsset?<c:out value='${viewUrlParams}' />#<c:out value='${description.classIdentifier}'/>" title="View asset details">
						</logic:equal>
							<bean:write name="description" property="description" filter="false"/> <img src="../servlet/display?file=${description.iconFile}" /><logic:equal name="description" property="isLink" value="true"></a></logic:equal></p>
				</c:if> 
			</c:if>
		</logic:iterate>  
	</div>
	<c:choose>
		<c:when test="${sectionPage == 'promoted'}">
			<logic:notPresent name="forwardParams">
				<c:set var="forwardParams" value="forward=viewPromotedAssets" />
			</logic:notPresent>
		</c:when>
		<c:otherwise>
			<logic:notPresent name="forwardParams">
				<c:set var="forwardParams" value="forward=viewRecentAssets" />
			</logic:notPresent>
		</c:otherwise>
	</c:choose>
	
	<p class="action">
		<c:if test="${sectionPage == 'promoted'}">
			<logic:equal name="userprofile" property="isAdmin" value="true">			
				<a class="remove" href="../action/removeAssetFromPromoted?<c:out value='id=${item.id}&page=${browseItemsForm.searchResults.pageIndex}&pageSize=${browseItemsForm.searchResults.pageSize}' />"  onclick="return confirm('Are you sure you want to remove this from the promoted section?');"><bright:cmsWrite identifier="link-remove-from-promoted" filter="false" /></a>					
			</logic:equal>
		</c:if>
		
		<logic:notEqual name="hideThumbnails" value="true">
			<%-- Add to lightbox link --%>
			<%-- Pass beans: item, viewUrl, forwardParams --%>
			<%@include file="../inc/add_to_lightbox.jsp"%>
		</logic:notEqual>

	</p>
</li>