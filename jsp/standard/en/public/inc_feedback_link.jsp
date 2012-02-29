<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


<bright:applicationSetting id="comments" settingName="comments"/>
<bright:applicationSetting id="ratings" settingName="ratings"/>
<bright:applicationSetting id="multipleComments" settingName="multiple-comments-per-asset"/>
<bean:define name="assetForm" property="asset.assetFeedbackCount" id="feedbackCount"/>
	
<c:set var="type">
	<%@include file="inc_feedback_snippet.jsp"%>
</c:set>
<c:if test="${comments && !ratings}">
	<c:if test="${feedbackCount > 0}">
		<c:choose>
			<c:when test="${feedbackCount == 1}">
				<bright:cmsWrite identifier="snippet-feedback-present-single" filter="false" replaceVariables="true"/>
			</c:when>
			<c:when test="${feedbackCount > 1}">
				<bright:cmsWrite identifier="snippet-feedback-present" filter="false" replaceVariables="true"/>
			</c:when>
		</c:choose>
		<a href="../action/viewAsset?<%= request.getQueryString() %>#feedback">&nbsp;<bright:cmsWrite identifier="link-view" filter="false" case="mixed"/>&nbsp;<bright:cmsWrite identifier="snippet-reviews" filter="false" case="lower"/> &raquo;</a>
	</c:if>
	<c:if test="${(userprofile.isAdmin || assetForm.userCanReviewAsset) && (multipleComments || !userGivenFeedback)}">
		<c:if test="${feedbackCount > 0}">&nbsp;|&nbsp;</c:if>
		<a href="viewSubmitFeedback?id=<bean:write name='assetForm' property='asset.id'/>&queryString=<%= java.net.URLEncoder.encode(request.getQueryString(),"UTF-8") %>"><bright:cmsWrite identifier="button-submit" filter="false"/> <bright:cmsWrite identifier="snippet-review" filter="false" case="lower"/> &raquo;</a>
	</c:if><br/><br/>
</c:if>