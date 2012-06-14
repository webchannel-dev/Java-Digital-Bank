<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	18-Jul-2006		Created
--%>
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Subscription Models</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	
	<bean:define id="section" value="payment"/>
	<bean:define id="pagetitle" value="Subscription Models"/>
	<bean:define id="tabId" value="subscription"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../payment-admin/inc_payment_tabs.jsp"%>
	
	<logic:present  name="subscriptionModelsForm">
		<logic:equal name="subscriptionModelsForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="subscriptionModelsForm" property="errors" id="error">
						<bean:write name="error" filter="false"/>
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	
	
	<p>If you deactivate a subscription model, it will not be available to new subscribers.
		Existing subscribers will not be affected.</p>										
	<br/>

	<logic:empty name="subscriptionModelsForm" property="models">
		<p>There are currently no subscription models set up.</p>
	</logic:empty>
		
	<logic:notEmpty name="subscriptionModelsForm" property="models">
	<table cellspacing="0" class="admin" summary="List of subscription models">
		<tr>
			<th>Model</th>
			<th>Price</th>
			<th>Num Downloads</th>
			<th>Duration</th>
			<th>Active?</th>
			<th>Active Subscribers?</th>
			<th colspan="2">&nbsp;</th>
		</tr>
		
		<logic:iterate name="subscriptionModelsForm" property="models" id="model">
			<tr>
				<td><bean:write name="model" property="description"/></td>
				<td><bean:write name="model" property="price.displayAmount" filter="false" /></td>
				<td><bean:write name="model" property="noOfDownloads.displayNumber"/></td>
				<td><bean:write name="model" property="duration.displayNumber"/></td>
				<td>
					<c:choose>
						<c:when test="${model.inactive}">No</c:when>
						<c:otherwise>Yes</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${model.hasActiveSubscriptions}">Yes</c:when>
						<c:otherwise>No</c:otherwise>
					</c:choose>
				</td>
				<td class="action">
					<c:choose>
						<c:when test="${model.inactive}">
							[<a href="activateSubscriptionModel?id=<bean:write name='model' property='id'/>&active=true" onclick="return confirm('Are you sure you want to activate this subscription model?');">activate</a>]
						</c:when>
						<c:otherwise>
							[<a href="activateSubscriptionModel?id=<bean:write name='model' property='id'/>&active=false" onclick="return confirm('Are you sure you want to deactivate this subscription model?');">deactivate</a>]							
						</c:otherwise>
					</c:choose>
				</td>
				
				<td class="action">
					[<a href="viewSubscriptionModel?id=<bean:write name='model' property='id'/>">edit</a>]
				</td>

				<c:choose>
					<c:when test="${model.hasActiveSubscriptions}">
						<td class="action">--</td>
					</c:when>
					<c:otherwise>
						<td class="action">
							[<a href="deleteSubscriptionModel?id=<bean:write name='model' property='id'/>" onclick="return confirm('Are you sure you want to delete this subscription model?\nAll subscriptions to this model will also be removed.');">X</a>]
						</td>						
					</c:otherwise>
				</c:choose>
			</tr>
		</logic:iterate>
	</table>	
	</logic:notEmpty>	

	<div class="hr"></div>
	
	<p><a href="viewSubscriptionModel">Add a subscription model &raquo;</a></p>

	<br />


	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>