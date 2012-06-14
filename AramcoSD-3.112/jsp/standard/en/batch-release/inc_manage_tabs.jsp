<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'manage'}">
			<h2 class="current"><bright:cmsWrite identifier="heading-manage-batch-releases" filter="false" case="mixed" /></h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManageBatchReleases"><bright:cmsWrite identifier="heading-manage-batch-releases" filter="false" case="mixed" /></a></h2>
		</c:otherwise>		
	</c:choose>

	<c:choose>
		<c:when test="${tabId == 'br-reports'}">
			<h2 class="current"><bright:cmsWrite identifier="tab-reports" filter="false"/></h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewBatchReleaseReports"><bright:cmsWrite identifier="tab-reports" filter="false"/></a></h2>
		</c:otherwise>		
	</c:choose>

	<div class="tabClearing">&nbsp;</div>
</div>