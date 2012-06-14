
		<%-- Purchase summary --%>	
		<bean:size id="noItems" name="checkoutForm" property="approvalList" />
		<h2><bright:cmsWrite identifier="e-subhead-purchase-summary" filter="false"/></h2>	
		<p>
			<bright:cmsWrite identifier="e-number-items in basket" filter="false" replaceVariables="true" />
		</p>

		<%-- Optional content managed text for price bands --%>
		<c:if test="${usePriceBands && userprofile.assetBox.hasPrint}">															
			<bright:cmsWrite identifier="checkout-text-for-prints" filter="false" />
		</c:if>		

		<%-- Flag whether to show the calculation for shipping and VAT --%>
		<c:set var="bShowCalculation" value="true" />				

		
		
		
		
		<%-- Check if shipping not calculated yet --%>
		<c:if test="${useShippingCosts && userprofile.assetBox.hasPrint && !userprofile.assetBox.isShippingRegionSet}">
			<p class="totals">
				<bright:cmsWrite identifier="a-lightbox" filter="false"/> <bright:cmsWrite identifier="e-cost" filter="false" replaceVariables="true" />: <bright:writeMoney name="userprofile" property="assetBox.basketTotal.displayAmount" /><br />
				<bright:cmsWrite identifier="shipping-calculation-notice-text" filter="false"/>
			</p>
			<c:set var="bShowCalculation" value="false" />
		</c:if>
		
		
		
		
		
		<%-- Text for has commercial usages instead of totals --%>
		<c:if test="${usePriceBands && useCommercialOptions && userprofile.assetBox.hasCommercialUsage}">															
			<p>
				<bright:cmsWrite identifier="commercial-calculation-notice-text" filter="false" />		
			</p>	
			<c:set var="bShowCalculation" value="false" />
		</c:if>
						
		<c:if test="${bShowCalculation}">

			<p class="totals">
		
				<%-- Shipping --%>
				<c:if test="${useShippingCosts && userprofile.assetBox.hasPrint}">
					<bright:cmsWrite identifier="a-lightbox" filter="false"/> <bright:cmsWrite identifier="e-label-cost" filter="false"/> <bright:writeMoney name="userprofile" property="assetBox.basketTotal.displayAmount" /><br />
					<bright:cmsWrite identifier="e-shipping" filter="false"/>
					<c:if test="${not empty userprofile.assetBox.shippingRegion.name}">(<c:out value="${userprofile.assetBox.shippingRegion.name}" />)</c:if>
					:
					<bright:writeMoney name="userprofile" property="assetBox.shippingTotal.displayAmount" /><br />	
				</c:if>
	
				<%-- Subtotal --%>
				<bright:cmsWrite identifier="e-subtotal" filter="false"/>: <bright:writeMoney name="userprofile" property="assetBox.price.subtotalAmount.displayAmount" /><br />
				
				<%-- Tax details --%>
				<logic:notEmpty name="userprofile" property="assetBox.price.taxNumber">
					<bright:cmsWrite identifier="e-label-tax-number" filter="false"/> <bean:write name="userprofile" property="assetBox.price.taxNumber"  /><br />
				</logic:notEmpty>
				<c:if test="${showTaxRegion && not empty userprofile.assetBox.price.tax.taxRegion.name}">
					<bright:cmsWrite identifier="e-label-tax-region" filter="false"/> <bean:write name="userprofile" property="assetBox.price.tax.taxRegion.name"  /><br />
				</c:if>
				
				<c:if test="${!userprofile.assetBox.price.excludesTax}">
					<%-- Show potential tax amount --%>
					<bean:write name="userprofile" property="assetBox.price.tax.taxType.name" /> @ <bean:write name="userprofile" property="assetBox.price.tax.taxPercent.displayNumber" />%: 
						<bright:writeMoney name="userprofile" property="assetBox.price.taxAmountWithoutNumber.displayAmount" /><br />
					
					<%-- If tax can be zeroed, show tax amount --%>
					<logic:notEmpty name="userprofile" property="assetBox.price.taxNumber">
						<c:if test="${userprofile.assetBox.price.tax.zeroIfTaxNumberGiven}">
							<bean:write name="userprofile" property="assetBox.price.tax.taxType.name" /> <bright:cmsWrite identifier="e-label-added" filter="false"/> <bright:writeMoney name="userprofile" property="assetBox.price.taxAmount.displayAmount" /><br />
						</c:if>
					</logic:notEmpty>
				</c:if>

				<%-- Discount --%>
				<c:if test="${userprofile.maxDiscount > 0}">
					<bright:cmsWrite identifier="e-label-discount" filter="false"/> <bean:write name="userprofile" property="maxDiscount"/>%<br/>
				</c:if>
				
				<%-- Total --%>
				<strong>
					<bright:cmsWrite identifier="e-total" filter="false"/>
					<c:if test="${userprofile.assetBox.price.excludesTax}"><bright:cmsWrite identifier="e-excluding-tax" filter="false"/></c:if>
					: <bright:writeMoney name="userprofile" property="totalWithDiscount.displayAmount" />
				</strong><br />
		
			</p>	
			
		</c:if>

		
		<div class="hr"></div>

		<%-- Display general errors --%>
		<logic:equal name="checkoutForm" property="hasErrors" value="true"> 
			<div class="error">
				<logic:iterate name="checkoutForm" property="errors" id="errorText">
					<bean:write name="errorText" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
		
