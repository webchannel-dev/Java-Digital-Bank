<div class="adminTabs">
	<c:if test="${userprofile.canSubmitRequests}" >
		<c:choose>
			<c:when test="${tabId == 'my-requests'}">
				<h2 class="current"><bright:cmsWrite identifier="heading-my-requests" /></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewMyRequests"><bright:cmsWrite identifier="heading-my-requests" /></a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:if test="${userprofile.canManageRequests || userprofile.isFulfiller}">
		<c:choose>
			<c:when test="${tabId == 'all-requests'}">
				<h2 class="current"><bright:cmsWrite identifier="heading-all-requests" /> <c:if test="${pendingRequestsCount > 0 }">(${pendingRequestsCount})</c:if></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewManageRequests"><bright:cmsWrite identifier="heading-all-requests" /> <c:if test="${pendingRequestsCount > 0 }">(${pendingRequestsCount})</c:if></a></h2>
			</c:otherwise>		
		</c:choose>
		<c:choose>
			<c:when test="${tabId == 'fulfilled-requests'}">
				<h2 class="current"><bright:cmsWrite identifier="heading-fulfilled-requests" /></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewFulfilledRequests"><bright:cmsWrite identifier="heading-fulfilled-requests" /></a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:if test="${userprofile.canManageRequests}">
		<c:choose>
			<c:when test="${tabId == 'rejected-requests'}">
				<h2 class="current"><bright:cmsWrite identifier="heading-rejected-requests" /></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewRejectedRequests"><bright:cmsWrite identifier="heading-rejected-requests" /></a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>

	<div class="tabClearing">&nbsp;</div>
</div>

	