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
	
</div>	<!-- end of detailWrapper -->