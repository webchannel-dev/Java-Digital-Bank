<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<div class="detailWrapper">
	<a href="viewAsset?<c:out value='${viewUrlParams}' />" class="thumb">
		<%@include file="../inc/view_thumbnail.jsp"%>								
	</a>	
	
	<div class="metadataWrapper">
		<c:choose>
			<c:when test="${item.isVideo}">
				<%-- Icon for video  --%>
				<img src="../images/standard/icon/video.gif" border="0" width="15" height="11" alt="Video" class="media_icon" />
			</c:when>
			<c:when test="${item.isAudio}">
				<%-- Icon for audio  --%>
				<img src="../images/standard/icon/audio.gif" border="0" width="13" height="12" alt="Audio" class="media_icon" />
			</c:when>
		</c:choose>	
	
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


