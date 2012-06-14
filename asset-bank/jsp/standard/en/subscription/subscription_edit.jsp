<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan	25-Jul-2006		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bean:parameter name="tsandcs" id="tsandcs" value="false"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Subscriptions</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<script src="../js/popup.js" type="text/javascript"></script>
	
	<script type="text/JavaScript">
		$j(function () {
			initDatePicker();
		});
	</script>
	
	<bean:define id="section" value="subscription"/>
	<bean:define id="pagetitle" value="Subscribe"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="subscriptionForm">
		<logic:equal name="subscriptionForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="subscriptionForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											

	<c:if test="${bValidationError}">
		<c:set var="bLoginProfileValid" value="false" />
		<div class="error">
				${sValidationErrorMessage}
		</div>
	</c:if>

	<h2>
		Select Subscription Model
	</h2>
	
<logic:empty name="subscriptionForm" property="models">
	<p>There are currently no subscription models set up. Please contact us to request a subscription.</p>
</logic:empty>
		
<logic:notEmpty name="subscriptionForm" property="models">	
	<p>
		Select a subscription model from the list below.
	</p>
		
	<html:form action="doSubscription" method="post">
		<html:hidden name="subscriptionForm" property="subscription.id" />
		<html:hidden name="subscriptionForm" property="subscription.refUser.id" />

		<logic:notEmpty name="subscriptionForm" property="models">
		<table cellspacing="0" class="admin" summary="List of models">
			<tr>
				<th></th>
				<th>Type</th>
				<th>Downloads per day</th>
				<th>Duration (days)</th>		
				<c:choose>
					<c:when test="${subscriptionForm.upgradePricesAvailable}">
						<th>Full Price</th>
						<th>Upgrade Price</th>
					</c:when>
					<c:otherwise>
						<th>Price</th>
					</c:otherwise>
				</c:choose>
			</tr>
			
			<logic:iterate name="subscriptionForm" property="models" id="model">
				<tr>
					<td><html:radio styleClass="radio" name="subscriptionForm" property="subscription.model.id" value="${model.id}" /></td>
					<td><bean:write name="model" property="description"/></td>
					<td><bean:write name="model" property="noOfDownloads.displayNumber"/></td>
					<td><bean:write name="model" property="duration.displayNumber"/></td>
					<td><bright:writeMoney name="model" property="price.displayAmount" /></td>
					<c:if test="${subscriptionForm.upgradePricesAvailable}">
						<td>
							<c:if test="${model.bestPrice.amount gt 0}">
								<bright:writeMoney name="model" property="bestPrice.displayAmount" />
							</c:if>
						</td>
					</c:if>
				</tr>
			</logic:iterate>
		</table>	
		</logic:notEmpty>	

		<div class="hr"></div>

		<h2>
			Start date
		</h2>
		<p>
			Select a start date for your subscription.
		</p>

		<table cellspacing="0" class="form" summary="Subscription start date form.">
			<tr>
				<th>
					<label for="startDate">Start Date:</label>
				</th>
				<td>
					<html:text styleClass="date" styleId="startDate" name="subscriptionForm" property="subscription.startDate.formDate" size="16" maxlength="16"/>										

					
				</td>	
			</tr>
		</table>

		<div class="hr"></div>
		
		<c:set var="sTCName" value="License" />
		
		<h2>
			<c:out value="${sTCName}" />
		</h2>
		<p>	
			<c:if test="${tsandcs == true}">
				<input type="checkbox" name="tsandcs" id="accept" style="display: inline; margin-bottom: 0px;" checked="checked" /> 
			</c:if>
			<c:if test="${tsandcs == false}">
				<html:checkbox name="subscriptionForm" property="tsandcs" styleId="accept" style="display: inline; margin-bottom: 0px; "/> 
			</c:if>
			
			<label for="accept">I have read and accept the <a href="../action/viewConditionsPopup" target="_blank" onclick="popupConditions('../action/viewConditionsPopup'); return false;" title="View <c:out value="${sTCName}" /> in a new window"><c:out value="${sTCName}" /></a></label>
		</p>

		<div class="hr"></div>

		<div class="buttonHolder">
			<input type="submit" class="button" value="Continue &raquo;" /> 
		</div>		
	</html:form>
	
	<%-- Cancel button depends on whether logged in --%>
	<c:choose>
		<c:when test="${userprofile.isLoggedIn}">
			<form name="cancelForm" action="viewUserSubscriptions" method="get">
				<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton" />
			</form>
		</c:when>
		<c:otherwise>
			<form name="cancelForm" action="viewSubscriptionRegister" method="get">
				<input type="submit" name="cancel" value="&laquo Back" class="button" id="cancelButton" />
			</form>			
		</c:otherwise>
	</c:choose>
		
	
</logic:notEmpty>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>