<bright:applicationSetting id="comments" settingName="comments"/>
<bright:applicationSetting id="ratings" settingName="ratings"/>

<bean:define id="ratingName" value="rating"/>
<bean:define id="ratingsAreVisible" name="assetForm" property="<%= \"asset.isAttributeValueVisible(\" + ratingName + \")\" %>"/>

<c:if test="${!ratingsAreVotes && (ratingsAreVisible || (comments && !ratings) || userGivenFeedback)}">
	<c:if test="${(comments || ratings) && visibleEntries > 0}">

		<bright:applicationSetting id="maxRating" settingName="max-rating"/>
		<bean:define id="displayCase" value="mixed"/>
		
		<div class="clearing"></div>
		<div class="hr"></div>
		<h3 id="feedback"><%@include file="inc_feedback_plural_snippet.jsp"%></h3>
		
		<logic:notEmpty name="assetForm" property="asset.assetFeedback">
			<logic:iterate name="assetForm" property="asset.assetFeedback" id="feedback">
				<c:if test="${(comments && !ratings) || ratingsAreVisible || (feedback.userId == userprofile.user.id)}">
					<p><em><bean:write name="feedback" property="user.fullName"/> - <bean:write name="feedback" property="date" format="dd/MM/yyyy"/></em><c:if test="${userprofile.isAdmin || (feedback.userId == userprofile.user.id)}">&nbsp;&nbsp;&nbsp;[<a href="viewFeedback?feedbackId=<bean:write name='feedback' property='id'/>&amp;queryString=<%= java.net.URLEncoder.encode(request.getQueryString(),"UTF-8") %>">edit</a>&nbsp;|&nbsp;<a href="deleteFeedback?id=<bean:write name='feedback' property='id'/>&amp;queryString=<%= java.net.URLEncoder.encode(request.getQueryString(),"UTF-8") %>" onclick="return confirm('Are you sure you want to delete this feedback entry?');">delete</a>]</c:if></p>
					<c:if test="${ratings}">
						<p><c:forEach begin="1" end="${maxRating}" var="value"><c:choose><c:when test="${value <= feedback.rating}"><img src="../images/standard/icon/star.gif" alt=""/></c:when><c:otherwise><img src="../images/standard/icon/star_off.gif" alt=""/></c:otherwise></c:choose>&nbsp;</c:forEach></p>
					</c:if>
					<c:if test="${comments}">
						<logic:present name="feedback" property="subject"><p><em><bean:write name="feedback" property="subject"/></em></p></logic:present>
						<p><bean:write name="feedback" property="comments"/></p>
					</c:if>
					<div class="hr"></div>
				</c:if>
			</logic:iterate>
		</logic:notEmpty>


		<logic:empty name="assetForm" property="asset.assetFeedback">
			<p><bright:cmsWrite identifier="snippet-no-feedback" filter="false" replaceVariables="true"/></p>
		</logic:empty>
		
		<bean:parameter name="feedback" id="feedback" value="0"/>
		<logic:equal name="feedback" value="1">
			<script type="text/javascript">
				<!--
					document.location = "#feedback";
				-->
			</script>
		</logic:equal>

	</c:if>
</c:if>