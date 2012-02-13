<div class="adminTabs">
	<c:if test="${userApprovalEnabled}">
		<c:choose>
			<c:when test="${tabId == 'manageApprovals'}">
				<h2 class="current">Approve New Users</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewApproval">Approve New Users</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:choose>
		<c:when test="${tabId == 'recentlyRegistered'}">
			<h2 class="current">Recently Registered</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewRecentlyRegistered">Recently Registered</a></h2>
		</c:otherwise>		
	</c:choose>
	<div class="tabClearing">&nbsp;</div>
</div>