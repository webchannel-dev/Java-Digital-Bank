<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="showSubscription" settingName="subscription"/>
<bright:applicationSetting id="showTaxCalculator" settingName="tax-calculator"/>
<bright:applicationSetting id="showPriceBands" settingName="price-bands"/>
<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Payment</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/calendar.js" type="text/javascript"></script>
	
	<bean:define id="section" value="payment"/>
	<bean:define id="pagetitle" value="Payment"/>
	<bean:define id="tabId" value="payment"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../payment-admin/inc_payment_tabs.jsp"%>
	
	
	<p>Payment Administration Hub.</p>										
	<p>
		<ul>									
			<c:if test="${showSubscription}">
				<li>
					Click <a href="viewSubscriptionModelsAdmin">Subscription Models</a> to manage subscriptions available to users. <br/><br/>
				</li>
			</c:if>
			<c:if test="${showTaxCalculator}">
				<li>
					Click <a href="viewTaxAdmin">Tax</a> to manage tax values applicable to different regions. <br/><br/>
				</li>
			</c:if>	
			<c:if test="${showPriceBands}">
				<li>
					Click <a href="viewPriceBandAdmin">Price Bands</a> to manage price bands available for assets. <br/><br/>
				</li>
			</c:if>	
			<c:if test="${showCommercialOptions}">
				<li>
					Click <a href="viewCommercialOptionAdmin">Commercial Options</a> to manage commercial options available for assets. <br/><br/>
				</li>
			</c:if>	
		</ul>
	</p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>