<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		08-Aug-2005		Created by splitting original checkout.
	d2      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%@include file="inc_checkout_settings.jsp"%>

<head>
	<title><bright:cmsWrite identifier="e-title-checkout" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="checkout"/>
</head>

<body id="checkout">
	<%@include file="../inc/body_start.jsp"%>
	<%@include file="../customisation/payment_gateway_logo.jsp"%>
	
	<h1><bright:cmsWrite identifier="e-heading-checkout" filter="false"/></h1> 
	
	<div class="head">
			<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-lightbox" filter="false" /></a>
	</div>	
	<%@include file="../customisation/credit_card_logos.jsp"%>
	
	<logic:empty  name="checkoutForm" property="approvalList">
		<p><bright:cmsWrite identifier="e-no-items" filter="false"/></p>	
		<c:if test="${userprofile.isLoggedIn}">
			<p>
				<bright:cmsWrite identifier="e-review-purchases" filter="false"/>
			</p>
		</c:if>
	</logic:empty>

	<logic:notEmpty name="checkoutForm" property="approvalList">
		
		<%@include file="inc_checkout_basket_summary.jsp"%>

		<h2><bright:cmsWrite identifier="e-heading-shipping" filter="false"/> &raquo;</h2>
		
		<%-- Content managed confirmation text --%>
		<bright:cmsWrite identifier="checkout-shipping-text" filter="false"/>

		<html:form action="/saveShippingCheckout" focus="shippingUser" method="post">
			
			<table class="form" cellpadding="0" cellspacing="0" summary="Register form">
				<%@include file="inc_address_fields.jsp"%>

				<tr>
					<th valign="top"><label for="saveaddress"><bright:cmsWrite identifier="e-label-save-as-home-address" filter="false"/></label></th>
					<td>
						<html:checkbox styleClass="checkbox" styleId="saveaddress" name="checkoutForm" property="copyShippingAddressToProfile" />
					</td>
				</tr>						
								
			</table>
			
			<div class="hr"></div>
		
			<div class="buttonHolder">
				<input type="submit" class="button" value="<bright:cmsWrite identifier="e-button-continue" filter="false"/>"> 
			</div>		
		</html:form>
						
		<br/>
	
	</logic:notEmpty>
					
<%@include file="../inc/body_end.jsp"%>
</body>
</html>