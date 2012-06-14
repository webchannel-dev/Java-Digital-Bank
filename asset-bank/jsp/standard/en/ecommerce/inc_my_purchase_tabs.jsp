<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>

<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'myPurchases'}">
			<h2 class="current"><bright:cmsWrite identifier="e-subhead-purchases" filter="false"/></h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewPurchases"><bright:cmsWrite identifier="e-subhead-purchases" filter="false"/></a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'myDownloads'}">
			<h2 class="current"><bright:cmsWrite identifier="e-my-downloads" filter="false"/></h2>		
		</c:when>
		<c:otherwise>
			<h2><a href="viewDownloads"><bright:cmsWrite identifier="e-my-downloads" filter="false"/></a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'myOrders'}">
			<h2 class="current"><bright:cmsWrite identifier="e-subhead-orders" filter="false"/></h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewUserOrderOverview?orderWorkflow=0"><bright:cmsWrite identifier="e-subhead-orders" filter="false"/></a></h2>
		</c:otherwise>		
	</c:choose>
	<div class="tabClearing">&nbsp;</div>
</div>