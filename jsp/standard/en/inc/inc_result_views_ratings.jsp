<c:choose>
	<c:when test="${showViews}">
		<div class="extraInfo">[<bean:write name="item" property="numViews"/>&nbsp;<c:if test="${item.numViews==1}"><bright:cmsWrite identifier="snippet-view" filter="false" /></c:if><c:if test="${item.numViews!=1}"><bright:cmsWrite identifier="snippet-views" filter="false" /></c:if>]</div>
	</c:when>
	<c:when test="${showDownloads}">
		<div class="extraInfo">[<bean:write name="item" property="numDownloads"/>&nbsp;<c:if test="${item.numDownloads==1}"><bright:cmsWrite identifier="snippet-download" filter="false" /></c:if><c:if test="${item.numDownloads!=1}"><bright:cmsWrite identifier="snippet-downloads" filter="false" /></c:if>]</div>
	</c:when>
</c:choose>

<%-- Show the 'rate this' if allowed for this asset and the average rating is shown in the display attributes --%>
<c:if test="${item.canBeRated}">
	<p class="block">
		<%-- See if ratings are shown in the display --%>
		<logic:notPresent name="ratingIsInDisplay">
			<bright:refDataList id="ratingIsInDisplay" componentName="AttributeManager" methodName="isDisplayAttribute" argumentValue="rating"/>
		</logic:notPresent>
		<logic:equal name="ratingIsInDisplay" value="true">
			<c:set var="ratedItem" value="${item}"/>
			<%@include file="../inc/inc_average_rating_stars.jsp"%>
			
			<a href="viewFeedback?<c:out value='${viewUrlParams}' />"><bright:cmsWrite identifier="button-submit" filter="false"/> <bright:cmsWrite identifier="snippet-review" filter="false" case="lower"/> &raquo;</a>
		</logic:equal>
	</p>	
</c:if>
