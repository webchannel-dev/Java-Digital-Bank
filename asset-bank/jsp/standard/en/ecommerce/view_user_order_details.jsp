<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Kevin Bennett		08-Jan-2007		Created
	d2		Matt Stevenson		18-Apr-2007		Fixed problem with VAT, added discount
	d3     Matt Woollard       11-Feb-2008     Replaced content with list items
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>

<bright:applicationSetting id="shippingCalculationType" settingName="shipping-calculation-type"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands" />

<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Order Details"/>

<bean:define id="section" value="purchases"/>
<c:set var="pagetitle"><bright:cmsWrite identifier="e-title-order-details" filter="false" replaceVariables="true" /></c:set>

<head>
	<title><bean:write name="pagetitle" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>


<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	<h1 class="underline"><bean:write name="pagetitle" /></h1>

		<table class="orderSummary" cellspacing="0" border="0">
			<colgroup>
				<col style="width:50%"></col>
				<col></col>
			</colgroup>
			<tr>
				<td><strong><bright:cmsWrite identifier="e-order-id" filter="false"/>:</strong> <bean:write name="orderForm" property="order.displayPurchaseId"/></td>
				<td><strong><bright:cmsWrite identifier="e-psp-id" filter="false"/>:</strong> <bean:write name="orderForm" property="order.pspTransId"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>																																								
				<td><strong><bright:cmsWrite identifier="e-date-placed" filter="false"/>:</strong> <fmt:formatDate value="${orderForm.order.datePlaced}" pattern="${dateFormat}" /></td>
				<td><strong><bright:cmsWrite identifier="e-status" filter="false"/>:</strong> <bean:write name="orderForm" property="order.status.name"/>
					<logic:equal name="orderForm" property="order.status.id" value="1">
						<logic:notEmpty name="orderForm" property="order.dateFulfilled">
							 (<fmt:formatDate value="${orderForm.order.dateFulfilled}" pattern="${dateFormat}"/>)
						</logic:notEmpty>
					</logic:equal>
				</td>
			</tr>
		</table>
			
		<%@include file="../ecommerce/inc_shipping_address.jsp"%>
		<logic:equal name="orderForm" property="order.personal" value="false">
			<%@include file="../ecommerce/inc_vat_receipt_address.jsp"%>
		</logic:equal>		

		<br />
		<table class="report" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<th><bright:cmsWrite identifier="item" filter="false" case="mixed" /> <bright:cmsWrite identifier="e-id" filter="false"/></th>
				<th><bright:cmsWrite identifier="item" filter="false" case="mixed" /></th>
				<logic:equal name="usePriceBands" value="true">				
					<th><bright:cmsWrite identifier="e-label-format" filter="false"/></th>
					<th><bright:cmsWrite identifier="e-format-type" filter="false"/></th>
					<logic:equal name="orderForm" property="order.personal" value="true">
						<th><bright:cmsWrite identifier="e-quantity" filter="false"/></th>
						<th><bright:cmsWrite identifier="e-price-of-items" filter="false"/></th>	
						<logic:notEqual name="orderForm" property="order.shippingAddress.id" value="0">						
							<logic:equal name="shippingCalculationType" value="1">						
								<th><bright:cmsWrite identifier="e-shipping-cost" filter="false"/></th>						
								<th><bright:cmsWrite identifier="e-total-cost" filter="false"/></th>
							</logic:equal>
						</logic:notEqual>
					</logic:equal>
					<logic:equal name="orderForm" property="order.personal" value="false">
						
						<c:if test="${orderForm.commercialOptionsExist}">
							<th><bright:cmsWrite identifier="e-commercial-option" filter="false"/></th>
						</c:if>	
						
						<th><bright:cmsWrite identifier="e-price" filter="false"/></th>
						<th colspan=1>&nbsp;</th>
					</logic:equal>
				</logic:equal>
				<logic:notEqual name="usePriceBands" value="true">
					<th><bright:cmsWrite identifier="e-price" filter="false"/></th>
				</logic:notEqual>
			</tr>
			<logic:iterate name="orderForm" property="order.assets" id="asset">
				<logic:iterate name="asset" property="priceBands" id="priceBand" indexId="pbIndx">
					<tr>
						<logic:equal name="pbIndx" value="0">
							<td><a href="viewAsset?id=<bean:write name='asset' property='assetId'/>"><bean:write name="asset" property="assetId"/></a></td>
							<td><bean:write name="asset" property="description"/></td> 
						</logic:equal>
						<logic:notEqual name="pbIndx" value="0">
							<td>&nbsp;</td><td>&nbsp;</td>
						</logic:notEqual>
						<logic:equal name="usePriceBands" value="true">				
							<td>
								<bean:write name="priceBand" property="priceBand.name"/>
							</td>
							<td>
								<bean:write name="priceBand" property="priceBandType.name"/>
							</td>
							<logic:equal name="orderForm" property="order.personal" value="true">
								<td>
									<bean:write name="priceBand" property="quantity"/>
								</td>
								<td>
									<bright:writeMoney name="priceBand" property="cost.displayAmount" />
								</td>
								<logic:notEqual name="orderForm" property="order.shippingAddress.id" value="0">
									<logic:equal name="shippingCalculationType" value="1">	
										<td>
											<bright:writeMoney name="priceBand" property="shippingCost.displayAmount" />
										</td>
										<td>								
											<bright:writeMoney name="priceBand" property="totalCost.displayAmount" />							
										</td>
									</logic:equal>
								</logic:notEqual>							
							</logic:equal>
							<logic:equal name="orderForm" property="order.personal" value="false">
								
								<c:if test="${orderForm.commercialOptionsExist}">
									<td>
										<bean:write name="priceBand" property="commercialOptionPurchase.commercialOption.name"/>
									</td>
								</c:if>
								
								<td>
									<logic:equal name="priceBand" property="commercialOptionPurchase.price.amount" value="0">
										<bright:cmsWrite identifier="e-tbc" filter="false"/>
									</logic:equal>
									<logic:notEqual name="priceBand" property="commercialOptionPurchase.price.amount" value="0">
										<bright:writeMoney name="priceBand" property="commercialOptionPurchase.price.displayAmount" />	
									</logic:notEqual>
								</td>
								<c:if test="${orderForm.commercialOptionsExist}">						
									<td>
										[<a href="viewUserCommercialOptionPurchase?&orderId=<bean:write name='orderForm' property='order.id'/>&assetId=<bean:write name='asset' property='assetId'/>&priceBandId=<bean:write name='priceBand' property='priceBand.id'/>"><bright:cmsWrite identifier="e-link-view-commercial-option-details" filter="false"/></a>]	
									</td>
								</c:if>
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="usePriceBands" value="true">
							<td>
								<bright:writeMoney name="asset" property="price.displayAmount" />
							</td>
						</logic:notEqual>
					</tr>
				</logic:iterate>
				<logic:equal name="usePriceBands" value="true">
					<logic:equal name="orderForm" property="order.personal" value="true">
						<tr>
							<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
							<td>
								<strong>
									<em><bright:writeMoney name="asset" property="price.displayAmount" /></em>
								</strong>	
							</td>
							<logic:notEqual name="orderForm" property="order.shippingAddress.id" value="0">
								<logic:equal name="shippingCalculationType" value="1">	
									<td>
										<strong>
											<bright:writeMoney name="asset" property="shippingCost.displayAmount" />
										</strong>
									</td>
									<td>
										<strong>
											<bright:writeMoney name="asset" property="totalCost.displayAmount" />
										</strong>
									</td>
								</logic:equal>
							</logic:notEqual>
						</tr>	
					</logic:equal>
				</logic:equal>
			</logic:iterate>				
		</table>

		<table class="admin" cellspacing="0" border="0">
			<logic:equal name="orderForm" property="order.personal" value="true">
				<logic:notEqual name="orderForm" property="order.shippingAddress.id" value="0">			
					<tr>
						<th><bright:cmsWrite identifier="item" filter="false" case="mixed" /> <bright:cmsWrite identifier="e-label-cost" filter="false"/></th>
						<td><bright:writeMoney name="orderForm" property="order.basketCost.displayAmount" /></td>
					</tr	
					<tr>
						<th><bright:cmsWrite identifier="e-shipping-cost" filter="false"/>:</th>
						<td><bright:writeMoney name="orderForm" property="order.shippingCost.displayAmount" /></td>
					</tr>
				</logic:notEqual>
			</logic:equal>
			<tr>
				<th><bright:cmsWrite identifier="e-subtotal" filter="false"/>:</th>
				<td>
					<c:choose>
						<c:when test="${orderForm.order.subtotal.amount==0}">
							<bright:cmsWrite identifier="e-tbc" filter="false"/>
						</c:when>
						<c:otherwise>
							<bright:writeMoney name="orderForm" property="order.subtotal.displayAmount" />
						</c:otherwise>
					</c:choose>	
				</td>
			</tr>
			<c:if test="${orderForm.order.vatPercent.number > 0}">
			<tr>
				<th><bright:cmsWrite identifier="e-tax" filter="false"/>:</th>
				<td><bean:write name="orderForm" property="order.vatPercent.displayNumber"/>%</td>
			</tr>
			</c:if>
			<c:if test="${orderForm.order.discountPercentage > 0}">
			<tr>
				<th><bright:cmsWrite identifier="e-label-discount" filter="false"/></th>
				<td><bean:write name="orderForm" property="order.discountPercentage"/>%</td>
			</tr>
			</c:if>
			<tr>
				<th><bright:cmsWrite identifier="e-total" filter="false"/>:</th>
				<td>
					<c:choose>
						<c:when test="${orderForm.order.total.amount==0}">
							<bright:cmsWrite identifier="e-tbc" filter="false"/>
						</c:when>
						<c:otherwise>
							<bright:writeMoney name="orderForm" property="order.total.displayAmount" />
						</c:otherwise>
					</c:choose>	
					
				</td>
			</tr>
		</table>

		<logic:equal name="orderForm" property="order.requiresOnlinePayment" value="true">
				<html:form action="viewOrderPayment" method="get">
					<input type="hidden" name="id" value="<bean:write name="orderForm" property="order.id"/>"/>
					<input type="submit" class="button flush" value="<bright:cmsWrite identifier="e-link-make-payment" filter="false"/>" > 
				</html:form>
		</logic:equal>

	<div class="hr"></div>	
	<p>
		<a href="viewUserOrderOverview?orderSearchCache=true"><bright:cmsWrite identifier="e-link-back-orders" filter="false"/></a>
	</p>
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>