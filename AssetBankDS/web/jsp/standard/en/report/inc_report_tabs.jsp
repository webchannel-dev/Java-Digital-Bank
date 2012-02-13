<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="auditEnabled" settingName="enable-audit-logging" />

<div class="adminTabs">
	
	<c:choose>
		<c:when test="${tabId == 'usageReports'}">
			<h2 class="current first">Usage Reports</h2>
		</c:when>
		<c:otherwise>
			<h2 class="first"><a href="viewReportHome">Usage Reports</a></h2>
		</c:otherwise>		
	</c:choose>

	<c:choose>
		<c:when test="${tabId == 'scheduledReports'}">
			<h2 class="current">Scheduled Reports</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewScheduledReport">Scheduled Reports</a></h2>
		</c:otherwise>		
	</c:choose>

	<c:choose>
		<c:when test="${tabId == 'searchReports'}">
			<h2 class="current">Search Reports</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewSearchReportPage">Search Reports</a></h2>
		</c:otherwise>		
	</c:choose>

	<c:choose>
		<c:when test="${tabId == 'assetReports'}">
			<h2 class="current">Asset Reports</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewAssetReportPage">Asset Reports</a></h2>
		</c:otherwise>		
	</c:choose>

	<c:if test="${ecommerce}">

		<c:choose>
			<c:when test="${tabId == 'eccomerceReports'}">
				<h2 class="current">ECommerce Reports</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="../action/viewEcommerceReports">ECommerce Reports</a></h2>
			</c:otherwise>		
		</c:choose>

	</c:if>		
	
	<c:if test="${auditEnabled}">
		<c:choose>
			<c:when test="${tabId == 'auditReports'}">
				<h2 class="current">Audit Reports</h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="../action/viewAuditReportPage">Audit Reports</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	
	<div class="tabClearing">&nbsp;</div>
	
</div>