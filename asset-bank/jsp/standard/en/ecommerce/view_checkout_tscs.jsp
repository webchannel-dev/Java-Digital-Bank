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
		<%@include file="inc_checkout_loggedin_profile_check.jsp"%>

		<c:if test="${bLoginProfileValid}">

			<h2><bright:cmsWrite identifier="e-subhead-terms-and-conditions" filter="false"/></h2>
			<%@include file="inc_checkout_tcs_buttons.jsp"%>
			<bright:cmsWrite identifier="conditions-intro-text" filter="false"/>
			
			<c:choose>
				<c:when test="${usePriceBands && useCommercialOptions && userprofile.assetBox.hasCommercialUsage}">															
					<%@include file="../customisation/commercial_conditions_copy.jsp"%>						
				</c:when>
				<c:otherwise>
					<%@include file="../customisation/conditions_copy.jsp"%>	
				</c:otherwise>
			</c:choose>					

			<%@include file="inc_checkout_tcs_buttons.jsp"%>
										
			<br/>		
		</c:if>
	
	</logic:notEmpty>
					
<%@include file="../inc/body_end.jsp"%>
</body>
</html>