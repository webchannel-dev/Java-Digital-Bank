<logic:notPresent name="displayCase">
	<bean:define id="displayCase" value=""/>
</logic:notPresent>

<c:choose>
	<c:when test="${ratings && !comments}">
		<c:choose>
			<c:when test="${displayCase == 'lower'}">
				<bright:cmsWrite identifier="snippet-rating" filter="false" case="lower"/>
			</c:when>
			<c:when test="${displayCase == 'upper'}">
				<bright:cmsWrite identifier="snippet-rating" filter="false" case="upper"/>
			</c:when>
			<c:when test="${displayCase == 'mixed'}">
				<bright:cmsWrite identifier="snippet-rating" filter="false" case="mixed"/>
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="snippet-rating" filter="false"/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${ratings && !comments}">
		<c:choose>
			<c:when test="${displayCase == 'lower'}">
				<bright:cmsWrite identifier="snippet-comment" filter="false" case="lower"/>
			</c:when>
			<c:when test="${displayCase == 'upper'}">
				<bright:cmsWrite identifier="snippet-comment" filter="false" case="upper"/>
			</c:when>
			<c:when test="${displayCase == 'mixed'}">
				<bright:cmsWrite identifier="snippet-comment" filter="false" case="mixed"/>
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="snippet-comment" filter="false"/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${displayCase == 'lower'}">
				<bright:cmsWrite identifier="snippet-review" filter="false" case="lower"/>
			</c:when>
			<c:when test="${displayCase == 'upper'}">
				<bright:cmsWrite identifier="snippet-review" filter="false" case="upper"/>
			</c:when>
			<c:when test="${displayCase == 'mixed'}">
				<bright:cmsWrite identifier="snippet-review" filter="false" case="mixed"/>
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="snippet-review" filter="false"/>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>