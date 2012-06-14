<c:choose>
	<c:when test="${showViews}">
		<span class="extraInfo">[<bean:write name="item" property="numViews"/>&nbsp;<c:if test="${item.numViews==1}"><bright:cmsWrite identifier="snippet-view" filter="false" /></c:if><c:if test="${item.numViews!=1}"><bright:cmsWrite identifier="snippet-views" filter="false" /></c:if>]</span>
	</c:when>
	<c:when test="${showDownloads}">
		<span class="extraInfo">[<bean:write name="item" property="numDownloads"/>&nbsp;<c:if test="${item.numDownloads==1}"><bright:cmsWrite identifier="snippet-download" filter="false" /></c:if><c:if test="${item.numDownloads!=1}"><bright:cmsWrite identifier="snippet-downloads" filter="false" /></c:if>]</span>
	</c:when>
</c:choose>


<%-- Show the 'rate this' if allowed for this asset and the average rating is shown in the display attributes --%>
<c:if test="${item.canBeRated}" >
	<logic:notPresent name="displayAttributeGroup">
		<%-- default to the group of search display attributes --%>
		<c:set var="displayAttributeGroup" value="1" scope="request" />
	</logic:notPresent>
	<logic:notPresent name="ratingIsInDisplay">
		<bright:refDataList id="ratingIsInDisplay" componentName="DisplayAttributeManager" methodName="isRatingDisplayAttribute" argumentValue="${displayAttributeGroup}"/>
	</logic:notPresent>

	<c:if test="${ratingIsInDisplay}" >
		<p class="block">
			<c:set var="ratedItem" value="${item}"/>
			<%@include file="../inc/inc_average_rating_stars.jsp"%>
			
			<a href="viewFeedback?<c:out value='${viewUrlParams}' />"><bright:cmsWrite identifier="button-submit" filter="false"/> <bright:cmsWrite identifier="snippet-review" filter="false" case="lower"/> &raquo;</a>
		</p>
	</c:if>
</c:if>
