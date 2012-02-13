<bright:applicationSetting id="showSubscription" settingName="subscription"/>
<bright:applicationSetting id="showTaxCalculator" settingName="tax-calculator"/>
<bright:applicationSetting id="showPriceBands" settingName="price-bands"/>
<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>

<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'payment'}">
			<h2 class="current">Payment</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewManagePayment">Payment</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:if test="${showSubscription}">
		<c:choose>
			<c:when test="${tabId == 'subscription'}">
				<h2 class="current">Subscription Models</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewSubscriptionModelsAdmin">Subscription Models</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:if test="${showTaxCalculator}"> 
		<c:choose>
			<c:when test="${tabId == 'tax'}">
				<h2 class="current">Tax</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewTaxAdmin">Tax</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:if test="${showPriceBands}">
		<c:choose>
			<c:when test="${tabId == 'priceBands'}">
				<h2 class="current">Price Bands</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewPriceBandAdmin">Price Bands</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<c:if test="${showCommercialOptions}">
		<c:choose>
			<c:when test="${tabId == 'commercialOptions'}">
				<h2 class="current">Commercial Options</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="viewCommercialOptionAdmin">Commercial Options</a></h2>
			</c:otherwise>		
		</c:choose>
	</c:if>
	<div class="tabClearing">&nbsp;</div>
</div>