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


<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Order Details"/>

<c:set var="pagetitle"><bright:cmsWrite identifier="e-title-commercial-order-details" filter="false" replaceVariables="true" /></c:set>

<head>
	<bean:define id="section" value="orders"/>
	<title><bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>


<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1>
	
	<h2><em><bright:cmsWrite identifier="snippet-step-1" filter="false"/>&nbsp;</em> Edit price(s) </h2>
	<p>
		<table class="report" cellspacing="0" border="0">
			<tr>
				<th><bright:cmsWrite identifier="item" filter="false" case="mixed" /> <bright:cmsWrite identifier="e-id" filter="false"/></th>
				<th><bright:cmsWrite identifier="item" filter="false" case="mixed" /></th>
				<th><bright:cmsWrite identifier="e-price-band" filter="false"/></th>
				<th><bright:cmsWrite identifier="e-price-band-type" filter="false"/></th>
				<c:if test="${orderForm.commercialOptionsExist}">
					<th><bright:cmsWrite identifier="e-commercial-option-nc" filter="false"/></th>
				</c:if>
				<th><bright:cmsWrite identifier="e-price" filter="false"/></th>
				<th colspan=1>&nbsp;</th>
			</tr>
			<logic:iterate name="orderForm" property="order.assets" id="asset">
				<logic:iterate name="asset" property="priceBands" id="priceBand" indexId="pbIndx">
					<tr>
						<%@include file="../ecommerce/inc_common_order_assetpb_details.jsp"%>
						
						<c:if test="${orderForm.commercialOptionsExist}">
							<td>
								<bean:write name="priceBand" property="commercialOptionPurchase.commercialOption.name"/>
							</td>
						</c:if>
							<td>
							<bean:write name="priceBand" property="commercialOptionPurchase.price.displayAmount" filter="false"/>
						</td>
						<logic:equal name="orderForm" property="order.requiresApproval" value="true">
							<td>
								[<a href="editCommercialOptionPurchase?commercialOptionPurchaseEditType=reset&orderId=<bean:write name='orderForm' property='order.id'/>&assetId=<bean:write name='asset' property='assetId'/>&priceBandId=<bean:write name='priceBand' property='priceBand.id'/>"><bright:cmsWrite identifier="e-link-edit-commercial-option" filter="false"/></a>]	
							</td>
						</logic:equal>
					</tr>
				</logic:iterate>
			</logic:iterate>				
		</table>
	</p>
	
	<p>
		<table class="report" cellspacing="0" border="0">
			<%@include file="../ecommerce/inc_common_order_price_details.jsp"%>
		</table>
	</p>
	<div class="hr"></div>
	<h2><em><bright:cmsWrite identifier="snippet-step-2" filter="false"/>&nbsp;</em> Edit Order Status </h2>
	
	<p>
		<table class="report" cellspacing="0" border="0">
			<%@include file="../ecommerce/inc_common_order_details.jsp"%>
			<tr>
				<th><bright:cmsWrite identifier="e-label-status" filter="false"/></th><td><bean:write name="orderForm" property="order.status.name"/></td>
				<logic:equal name="orderForm" property="order.statusEditable" value="true">
					<td class="action">
						[<a href="editCommercialOrderStatus?orderId=<bean:write name='orderForm' property='order.id'/>"><bright:cmsWrite identifier="e-link-edit-order-status" filter="false"/></a>]
					</td>
				</logic:equal>
			</tr>
		</table>
		
		<logic:equal name="orderForm" property="order.prefersOfflinePayment" value="true">
			<bright:cmsWrite identifier="e-user-request-offline" filter="false"/>
		</logic:equal>
	</p>
	<p>
		<%@include file="../ecommerce/inc_vat_receipt_address.jsp"%>	
	</p>
	<p>
		<table class="report" cellspacing="0" border="0">
			<tr>
				<th>
					<bright:cmsWrite identifier="e-user-uage-notes" filter="false"/>
				</th>
			</tr>
			<tr>
				<td>
					<bean:write name="orderForm" property="order.userNotes"/>
				</td>
			</tr>
			
		</table>
	</p>

	<div class="hr"></div>	
	<p>
		<a href="viewCommercialOrderOverview?orderSearchCache=true"><bright:cmsWrite identifier="e-link-back-orders" filter="false"/></a>
	</p>
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>