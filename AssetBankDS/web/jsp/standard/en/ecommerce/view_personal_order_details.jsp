<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Kevin Bennett		08-Jan-2007		Created
	d2      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />

<bright:applicationSetting id="shippingCalculationType" settingName="shipping-calculation-type"/>

<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Order Details"/>

<c:set var="pagetitle"><bright:cmsWrite identifier="e-title-personal-order-details" filter="false" replaceVariables="true" /></c:set>

<head>
	<bean:define id="section" value="orders"/>
	<title><bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>


<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1>
	<p>
		<table class="report" cellspacing="0" border="0">
			<%@include file="../ecommerce/inc_common_order_details.jsp"%>
			<tr>
				<th><bright:cmsWrite identifier="e-label-status" filter="false"/></th>
				<td>
					<bean:write name="orderForm" property="order.status.name"/>
					<logic:equal name="orderForm" property="order.status.id" value="1">
						 (<fmt:formatDate value="${orderForm.order.dateFulfilled}" pattern="${dateFormat}"/>)
					</logic:equal>
				</td>
				<logic:equal name="orderForm" property="order.statusEditable" value="true">
					<td class="action">
						[<a href="updatePersonalOrderStatus?orderId=<bean:write name='orderForm' property='order.id'/>&order.status.id=1" onclick="return confirm('<bright:cmsWrite identifier="e-js-confirm-fulfill-order" filter="false"/>');"><bright:cmsWrite identifier="e-link-fulfill-order" filter="false"/></a>]
					</td>
				</logic:equal>
			</tr>
		</table>
		<%@include file="../ecommerce/inc_shipping_address.jsp"%>																																											
	</p>
	<p>
		<table class="report" cellspacing="0" border="0">
			<tr>
				<th><bright:cmsWrite identifier="item" filter="false" case="mixed" /> <bright:cmsWrite identifier="e-id" filter="false"/></th>
				<th><bright:cmsWrite identifier="item" filter="false" case="mixed" /></th>
				<th><bright:cmsWrite identifier="e-price-band" filter="false"/></th>
				<th><bright:cmsWrite identifier="e-price-band-type" filter="false"/></th>
				<th><bright:cmsWrite identifier="e-quantity" filter="false"/></th>
				<th><bright:cmsWrite identifier="e-price-of-items" filter="false"/></th>
				<logic:notEqual name="orderForm" property="order.shippingAddress.id" value="0">
					<logic:equal name="shippingCalculationType" value="1">											
					<th><bright:cmsWrite identifier="e-shipping-cost" filter="false"/></th>
					<th><bright:cmsWrite identifier="e-total-cost" filter="false"/></th>
					</logic:equal>
				</logic:notEqual>
			</tr>
			<logic:iterate name="orderForm" property="order.assets" id="asset">
				<logic:iterate name="asset" property="priceBands" id="priceBand" indexId="pbIndx">
					<tr>
						<%@include file="../ecommerce/inc_common_order_assetpb_details.jsp"%>
						<td>
							<bean:write name="priceBand" property="quantity"/>
						</td>
						<td>
							<bean:write name="priceBand" property="cost.displayAmount" filter="false"/>
						</td>
						<logic:notEqual name="orderForm" property="order.shippingAddress.id" value="0">
							<logic:equal name="shippingCalculationType" value="1">																		
								<td>
									<bean:write name="priceBand" property="shippingCost.displayAmount" filter="false"/>
								</td>
								<td>							
									<bean:write name="priceBand" property="totalCost.displayAmount" filter="false"/>				
								</td>
							</logic:equal>
						</logic:notEqual>						
					</tr>
				</logic:iterate>
				<tr>
					<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
					<td>
						<strong>
							<bean:write name="asset" property="price.displayAmount" format="italic" filter="false"/>
						</strong>	
					</td>
					<logic:notEqual name="orderForm" property="order.shippingAddress.id" value="0">
						<logic:equal name="shippingCalculationType" value="1">						
							<td>
								<strong>
									<bean:write name="asset" property="shippingCost.displayAmount" filter="false"/>
								</strong>
							</td>
							<td>						
								<strong>								
									<bean:write name="asset" property="totalCost.displayAmount" filter="false"/>							
								</strong>						
							</td>
						</logic:equal>
					</logic:notEqual>
					
				</tr>	
				<tr><td>&nbsp;</td></tr>			
			</logic:iterate>				
		</table>
	</p>
	<p>
		<table class="report" cellspacing="0" border="0">
			<tr>
				<th><bright:cmsWrite identifier="item" filter="false" case="mixed" /> Cost:</th>
				<td><bean:write name="orderForm" property="order.basketCost.displayAmount" filter="false"/></td>
			</tr>
			<logic:notEqual name="orderForm" property="order.shippingAddress.id" value="0">
				<tr>
					<th><bright:cmsWrite identifier="e-shipping-cost" filter="false"/>:</th>
					<td><bean:write name="orderForm" property="order.shippingCost.displayAmount" filter="false" /></td>
				</tr>
			</logic:notEqual>
			<%@include file="../ecommerce/inc_common_order_price_details.jsp"%>
		</table>
	</p>
	<div class="hr"></div>	
	<p>
		<a href="viewPersonalOrderOverview?orderSearchCache=true"><bright:cmsWrite identifier="e-link-back-orders" filter="false"/></a>
	</p>
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>