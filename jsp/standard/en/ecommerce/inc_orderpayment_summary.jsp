<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

		<%-- Purchase summary --%>	
		<h2><bright:cmsWrite identifier="e-subhead-order-summary" filter="false"/></h2>	
		<p>
			<bright:cmsWrite identifier="e-invoice-number" filter="false"/> <strong><bean:write name="orderPaymentForm" property="order.displayPurchaseId" /></strong><br />
			<bright:cmsWrite identifier="e-date-placed" filter="false"/>: <fmt:formatDate value="${orderPaymentForm.order.datePlaced}" pattern="${dateFormat}" />
		</p>

		<p class="totals">
			
			<%-- Shipping --%>
			<c:if test="${useShippingCosts && orderPaymentForm.order.shippingCost.amount gt 0}">
				<bright:cmsWrite identifier="items" filter="false" case="mixed" /> <bright:cmsWrite identifier="e-cost" filter="false"/>: <bean:write name="orderPaymentForm" property="order.basketCost.displayAmount" filter="false" /><br />
				<bright:cmsWrite identifier="e-shipping" filter="false"/>: <bean:write name="orderPaymentForm" property="order.shippingCost.displayAmount" filter="false" /><br />
			</c:if>

			<%-- Subtotal --%>
			<bright:cmsWrite identifier="e-subtotal" filter="false"/>: <bean:write name="orderPaymentForm" property="order.subtotal.displayAmount" filter="false" /><br />
			
			<%-- Tax details --%>
	      <c:if test="${orderPaymentForm.order.vatPercent.number gt 0}">
				<bright:cmsWrite identifier="e-tax" filter="false"/> @ <bean:write name="orderPaymentForm" property="order.vatPercent.displayNumber" filter="false" /><br />	
			</c:if>
	      	
			<%-- Total --%>
			<strong>
				<bright:cmsWrite identifier="e-total" filter="false"/>:<bean:write name="orderPaymentForm" property="order.total.displayAmount" filter="false" />
			</strong><br />
	
		</p>	
		
		<div class="hr"></div>

		<%-- Display general errors --%>
		<logic:equal name="orderPaymentForm" property="hasErrors" value="true"> 
			<div class="error">
				<logic:iterate name="orderPaymentForm" property="errors" id="errorText">
					<bean:write name="errorText" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
		
