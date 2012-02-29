<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
	<td class="thumb">
		<a href="viewAsset?<c:out value='${viewUrlParams}' />" class="thumb">
			<bean:define id="asset" name="item" />

			<!-- <%@include file="../inc/view_homogenized_thumb.jsp"%> -->
			<%@include file="../inc/view_thumbnail.jsp"%>
						
		</a>	
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
	</td>
	
	
	<%-- get and show the browse list view display attributes --%>
	<bean:define id="descriptions" name="item" property='<%= "descriptions(5)" %>' />

	<logic:notEmpty name="descriptions">

		<logic:iterate name="descriptions" id="description" indexId="index">
			<td>
				<logic:notEmpty name="description">
					<c:if test="${firstIsHeader && index==0}"><h2></c:if><logic:equal name="description" property="isLink" value="true"><a href="viewAsset?<c:out value='${viewUrlParams}' />" title="View asset details"></logic:equal><bean:write name="description" property="description" filter="false"/><logic:equal name="description" property="isLink" value="true"></a></logic:equal><c:choose><c:when test="${firstIsHeader && index==0}"></h2></c:when><c:otherwise><br/></c:otherwise></c:choose>
				</logic:notEmpty>&nbsp;
				
			</td>
		</logic:iterate>

	</logic:notEmpty>	

	<c:if test="${showViews || showDownloads || item.canBeRated}">	
		<td>
			<%@include file="../inc/inc_result_views_ratings.jsp"%>
		</td>
	</c:if>	