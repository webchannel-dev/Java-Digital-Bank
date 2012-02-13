
<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'user'}">
			<h2 class="current">My Items</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewUnsubmittedAssets">My Items</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'admin'}">
			<h2 class="current">Other Users</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewUnsubmittedAssetsAdmin">Other Users</a></h2>
		</c:otherwise>		
	</c:choose>
	<div class="tabClearing">&nbsp;</div>
</div>