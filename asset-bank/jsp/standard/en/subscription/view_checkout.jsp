<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		26-Jul-2005		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bean:parameter id="trans_id" name="trans_id" value="" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Subscriptions</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="subscription"/>
</head>

<body id="checkout">
	<%@include file="../inc/body_start.jsp"%>
	<%@include file="../customisation/payment_gateway_logo.jsp"%>
	
	<h1>Confirm Subscription</h1> 
	
	<div class="hr"></div>	
	<%@include file="../customisation/credit_card_logos.jsp"%>
	
	
	<%-- Purchase summary --%>
	<h2>Purchase summary</h2>	
	<p>
		You are about to purchase the following:
	</p>
	<p>
		Subscription: <bean:write name="subscriptionForm" property="subscription.model.description" /><br/>
		Start date: <bean:write name="subscriptionForm" property="subscription.startDate.displayDate" />
	</p>
		
	<p class="totals">
		<logic:notEmpty name="subscriptionForm" property="price.taxNumber">
			Your tax number: <bean:write name="subscriptionForm" property="price.taxNumber" /><br />
		</logic:notEmpty>
		<logic:notEmpty name="subscriptionForm" property="price.tax.taxRegion.name">
			Tax region: <bean:write name="subscriptionForm" property="price.tax.taxRegion.name" /><br />
		</logic:notEmpty>
		
		Subtotal: <bright:writeMoney name="subscriptionForm" property="price.subtotalAmount.displayAmount" /><br />
		
		<%-- Show potential tax amount --%>
		<bean:write name="subscriptionForm" property="price.tax.taxType.name" /> @ <bean:write name="subscriptionForm" property="price.tax.taxPercent.displayNumber" />%: 
			<bright:writeMoney name="subscriptionForm" property="price.taxAmountWithoutNumber.displayAmount" /><br />
		
		<%-- If tax can be zeroed, show tax amount --%>
		<logic:notEmpty name="subscriptionForm" property="price.taxNumber">
			<c:if test="${subscriptionForm.price.tax.zeroIfTaxNumberGiven}">
				<bean:write name="subscriptionForm" property="price.tax.taxType.name" /> added: <bright:writeMoney name="subscriptionForm" property="price.taxAmount.displayAmount" /><br />
			</c:if>
		</logic:notEmpty>
		
		<strong>
			Total: <bright:writeMoney name="subscriptionForm" property="price.totalAmount.displayAmount" />
		</strong><br />

	</p>	
		
	<div class="hr"></div>
		

	<%-- Display general errors --%>
	<logic:equal name="subscriptionForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="subscriptionForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
		
								
	<%-- Purchase options --%>
	<h2>Ready to buy &raquo;</h2>
						
	<%-- Email address confirmation --%>
	<logic:notEmpty name="subscriptionForm" property="emailAddress">
		<p>
			Confirmation of purchase will be sent to the following email address: <bean:write name="subscriptionForm" property="emailAddress" />. <br />
			If this is not correct, then please 
			<c:choose>
				<c:when test="${userprofile.isLoggedIn}">
					<a href="viewChangeProfile">update your profile</a> 
				</c:when>
				<c:otherwise>
					<a href="viewSubscriptionRegister">start over</a> 
				</c:otherwise>				
			</c:choose>
				
			to update your details.
		</p>
	</logic:notEmpty>
	
	<br />

	<%-- The PSP form --%>
	<logic:notEmpty name="subscriptionForm" property="pspUrl">
		<p>
			<form action="<c:out value='${subscriptionForm.pspUrl}' />" method="post" name="purchaseForm">
				<logic:notEmpty name="subscriptionForm" property="purchaseFormKeys">
					<logic:iterate name="subscriptionForm" property="purchaseFormKeys" id="key">
						<input type="hidden" name="<c:out value='${key}' />" value="<c:out value='${subscriptionForm.purchaseForm[key]}' />" />
					</logic:iterate>
				</logic:notEmpty>
				
				<input type="submit" class="button flush" value="Purchase &raquo;" /> 
																
			</form>
		</p>
	</logic:notEmpty>
								
					
<%@include file="../inc/body_end.jsp"%>
</body>
</html>