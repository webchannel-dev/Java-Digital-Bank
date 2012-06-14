<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		08-Aug-2005		Created.
	d2      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%@include file="inc_checkout_settings.jsp"%>


<head>
	<title><bright:cmsWrite identifier="e-title-order-payment" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="purchases"/>
</head>

<body id="checkout">
	<%@include file="../inc/body_start.jsp"%>
	<%@include file="../customisation/payment_gateway_logo.jsp"%>
	
	<h1>Order Payment</h1> 
	
	<div class="head">
			<a href="../action/viewUserOrderOverview"><bright:cmsWrite identifier="e-link-back-orders" filter="false"/></a>
	</div>	
	<%@include file="../customisation/credit_card_logos.jsp"%>

	<%@include file="inc_orderpayment_summary.jsp"%>
	<%@include file="inc_checkout_loggedin_profile_check.jsp"%>

	<c:if test="${bLoginProfileValid}">

		<h2><bright:cmsWrite identifier="e-subhead-terms-and-conditions" filter="false"/></h2>
		
		<p><bright:cmsWrite identifier="e-ts-and-cs-applicable" filter="false"/></p>		
		<%@include file="inc_orderpayment_commercial_tcs_buttons.jsp"%>

			<logic:iterate name="orderPaymentForm" property="order.assets" id="asset">
				<p>
					<bright:cmsWrite identifier="item" filter="false" case="mixed" />: <bean:write name="asset" property="description"/><br />

					<logic:iterate name="asset" property="priceBands" id="priceBand" indexId="pbIndx">
						<logic:equal name="pbIndx" value="0">
							<bright:cmsWrite identifier="e-commercial-option" filter="false"/> <bean:write name="priceBand" property="commercialOptionPurchase.commercialOption.name"/><br />
							Terms: <bean:write name="priceBand" property="commercialOptionPurchase.commercialOption.terms" filter="false"/><br />
						</logic:equal>
					</logic:iterate>
				</p>
			</logic:iterate>				
		
		<%@include file="inc_orderpayment_commercial_tcs_buttons.jsp"%>
									
		<br/>		
	</c:if>

				
<%@include file="../inc/body_end.jsp"%>
</body>
</html>