<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		19-Jan-2007		Created.
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
	
	<h1><bright:cmsWrite identifier="e-heading-order-payment" filter="false"/></h1> 
	
	<div class="head">
			<a href="../action/viewUserOrderOverview"><bright:cmsWrite identifier="e-link-back-orders" filter="false"/></a>
	</div>	
	<%@include file="../customisation/credit_card_logos.jsp"%>
			
	<%@include file="inc_orderpayment_summary.jsp"%>
	<%@include file="inc_checkout_loggedin_profile_check.jsp"%>

	<c:if test="${bLoginProfileValid}">

			<h2><bright:cmsWrite identifier="e-subhead-ready-to-buy" filter="false"/></h2>
			
			<%-- Email address confirmation --%>
			<c:if test="${userprofile.isLoggedIn}">
				<logic:notEmpty name="userprofile" property="user.emailAddress">
					<p>
						Confirmation of purchase will be sent to the following email address: <bean:write name="userprofile" property="user.emailAddress" />. <br />
						If this is not correct, then please <a href="viewChangeProfile">update your profile</a> to update your details.
					</p>
				</logic:notEmpty>
			</c:if>

			
			<form action="<c:out value='${orderPaymentForm.pspUrl}' />" method="post" name="purchaseForm">
				<logic:notEmpty name="orderPaymentForm" property="purchaseFormKeys">
					<logic:iterate name="orderPaymentForm" property="purchaseFormKeys" id="key">
						<input type="hidden" name="<c:out value='${key}' />" value="<c:out value='${orderPaymentForm.purchaseForm[key]}' />" />
					</logic:iterate>
				</logic:notEmpty>
				<p>
					<bright:cmsWrite identifier="e-review-order" filter="false"/>
				</p>
				<input type="submit" class="button flush" value="<bright:cmsWrite identifier="e-button-purchase" filter="false"/>" /> 
																
			</form>
					
<br/>		
		
		</c:if>
	
					
<%@include file="../inc/body_end.jsp"%>
</body>
</html>