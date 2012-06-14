<bright:applicationSetting id="showPriceBands" settingName="price-bands"/>
<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>

<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'orders'}">
			<h2 class="current"><bright:cmsWrite identifier="e-orders" filter="false"/></h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManageOrders"><bright:cmsWrite identifier="e-orders" filter="false"/></a></h2>
		</c:otherwise>		
	</c:choose>
	<c:if test="${showPriceBands}">
		<c:choose>
			<c:when test="${tabId == 'personalOrders'}">
				<h2 class="current"><bright:cmsWrite identifier="e-subhead-personal-orders" filter="false"/></h2>		
			</c:when>
			<c:otherwise>
				<h2><a href="viewPersonalOrderOverview?orderStatus=2&withPriceBandType=2&orderWorkflow=1"><bright:cmsWrite identifier="e-subhead-personal-orders" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>		
	<c:if test="${showCommercialOptions}">
		<c:choose>
			<c:when test="${tabId == 'commercialOrders'}">
				<h2 class="current"><bright:cmsWrite identifier="e-subhead-commercial-orders" filter="false"/></h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewCommercialOrderOverview?orderStatus=3&orderWorkflow=2"><bright:cmsWrite identifier="e-subhead-commercial-orders" filter="false"/></a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>		
	<div class="tabClearing">&nbsp;</div>
</div>