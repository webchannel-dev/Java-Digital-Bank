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
	
	<h1>Checkout</h1> 
	
	<div class="head">
			<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-lightbox" filter="false" /></a>
	</div>	
	<%@include file="../customisation/credit_card_logos.jsp"%>
	
	<logic:empty  name="checkoutForm" property="approvalList">
		<p><bright:cmsWrite identifier="e-no-items" filter="false" replaceVariables="true" /></p>	
		<c:if test="${userprofile.isLoggedIn}">
			<p>
				<bright:cmsWrite identifier="e-review-purchases" filter="false"/>
			</p>
		</c:if>
	</logic:empty>

	<logic:notEmpty name="checkoutForm" property="approvalList">
		
		<%@include file="inc_checkout_basket_summary.jsp"%>
		<%@include file="inc_checkout_loggedin_profile_check.jsp"%>

		<c:if test="${bLoginProfileValid}">
			<h2><bright:cmsWrite identifier="e-subhead-ready-to-buy" filter="false"/></h2>
			
			<%-- Email address confirmation --%>
			<c:if test="${userprofile.isLoggedIn}">
				<logic:notEmpty name="userprofile" property="user.emailAddress">
					<p>
						<bean:define id="emailAddress" name="userprofile" property="user.emailAddress"/>
						<bright:cmsWrite identifier="e-email-confirmation" filter="false" replaceVariables="true" />
					</p>
				</logic:notEmpty>
			</c:if>
			<c:if test="${checkoutForm.isRegistered}">
				<p>
					<bean:define id="emailAddress" name="checkoutForm" property="registerEmailAddress"/>
					<bright:cmsWrite identifier="e-email-confirmation-purchase" filter="false" replaceVariables="true" />
					<br />
					<c:choose>
						<c:when test="${ecommerceUserAddressMandatory}">
							<bright:cmsWrite identifier="e-not-correct-change-details" filter="false"/>	
						</c:when>
						<c:otherwise>
							<bright:cmsWrite identifier="e-not-correct-checkout-again" filter="false"/>						
						</c:otherwise>
					</c:choose>	
				</p>
			</c:if>

			<%-- Content managed confirmation text --%>
			<bright:cmsWrite identifier="checkout-confirmation-text" filter="false"/>		
						
			<c:choose>
			<%-- Offline purchase form --%>
				<c:when test="${ecommerceOfflineOption}">
					
					<%-- Should only have commercial items OR non-commerical, not mixture --%>
					<c:if test="${not empty checkoutForm.pspUrl && userprofile.assetBox.hasNonCommercialUsage }">
						<div class="dialogPanel">
							<form action="<c:out value='${checkoutForm.pspUrl}' />" method="post" name="purchaseForm">
								<logic:notEmpty name="checkoutForm" property="purchaseFormKeys">
									<logic:iterate name="checkoutForm" property="purchaseFormKeys" id="key">
										<input type="hidden" name="<c:out value='${key}' />" value="<c:out value='${checkoutForm.purchaseForm[key]}' />" />
									</logic:iterate>
								</logic:notEmpty>
								<bright:cmsWrite identifier="personal-purchase-text" filter="false"/>
								<input type="submit" class="button flush" value="Purchase &raquo;" /> 
																				
							</form>
						</div>						
					</c:if>

					<c:if test="${userprofile.assetBox.hasCommercialUsage }">		
																										
								
							<form action="../action/purchase" method="post" name="checkoutForm">
								<html:hidden name="checkoutForm" property="registerEmailAddress" />
								<html:hidden name="checkoutForm" property="isRegistered" />
								<input type="hidden" name="trans_id" value="<c:out value='${trans_id}' />" />			
								<html:hidden name="checkoutForm" property="selectedUse" value="offline" />
								
								<logic:notEmpty name="checkoutForm.commercialOptionsList">
									<div class="dialogPanel">
										<p><bright:cmsWrite identifier="commercial-purchase-text" filter="false"/></p>
										<input type="submit" class="button flush" value="<bright:cmsWrite identifier="e-select-options" filter="false"/>" />
									</div>
								</logic:notEmpty>
								<logic:empty name="checkoutForm.commercialOptionsList">
									<input type="submit" class="button flush" value="<bright:cmsWrite identifier="e-checkout-button" filter="false"/>" />
								</logic:empty>
							</form>
						
						

					</c:if>
									
					<div class="clearing"></div>
				</c:when>
				<c:otherwise>
						<form action="<c:out value='${checkoutForm.pspUrl}' />" method="post" name="purchaseForm">
							<logic:notEmpty name="checkoutForm" property="purchaseFormKeys">
								<logic:iterate name="checkoutForm" property="purchaseFormKeys" id="key">
									<input type="hidden" name="<c:out value='${key}' />" value="<c:out value='${checkoutForm.purchaseForm[key]}' />" />
								</logic:iterate>
							</logic:notEmpty>
							<p><bright:cmsWrite identifier="e-review-order" filter="false"/></p>
							<input type="submit" class="button flush" value="<bright:cmsWrite identifier="e-button-purchase" filter="false"/>" /> 
																			
						</form>
				</c:otherwise>
			</c:choose>
										
			<br/>		
			<%@include file="inc_checkout_basket_detail.jsp"%>
		
		</c:if>
	
	</logic:notEmpty>
					
<%@include file="../inc/body_end.jsp"%>
</body>
</html>