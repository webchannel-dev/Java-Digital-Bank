<%--
	Displays the average rating of an asset as stars.
	To use, set a var called ratedItem - this object must implement the properties: averageRating, averageRatingFloor and averageRatingFraction

	NOTE: this doesn't work if ratings-are-votes=true (so admin users aren't allowed to add 'avergae-rating' to the display attributes if this is so).
--%>
<c:choose>
	<c:when test="${ratedItem.averageRating >= 0}">
		<span style="position:relative; top:2px;">
			<bright:applicationSetting id="maxRating" settingName="max-rating"/>
			<c:if test="${ratedItem.averageRating >= 1}"><c:forEach begin="1" end="${ratedItem.averageRatingFloor}" var="value"><img src="../images/standard/icon/star.gif" alt=""/></c:forEach></c:if><c:if test="${ratedItem.averageRatingFloor < maxRating}"><c:choose><c:when test="${ratedItem.averageRatingFraction == 0}"><img src="../images/standard/icon/star_off.gif" alt=""/></c:when><c:otherwise><img src="../images/standard/icon/star_half.gif" alt=""/></c:otherwise></c:choose></c:if><c:if test="${ratedItem.averageRatingFloor+2 <= maxRating}"><c:forEach begin="${ratedItem.averageRatingFloor+2}" end="${maxRating}" var="value"><img src="../images/standard/icon/star_off.gif" alt=""/></c:forEach></c:if>
		</span>
		<%--(<bean:write name="ratedItem" property="averageRating"/>)--%>
	</c:when>
	<c:otherwise>
		<span class="notRated">
		<bright:cmsWrite identifier="snippet-not-yet-rated" filter="false"/>
		</span>
	</c:otherwise>
</c:choose>