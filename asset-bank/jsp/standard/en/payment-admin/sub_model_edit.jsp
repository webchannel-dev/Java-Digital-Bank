<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan	19-Jul-2006		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	<logic:equal name="subscriptionModelsForm" property="model.id" value="0">	
		<bean:define id="pagetitle" value="Add Subscription Model"/>
	</logic:equal>
	<logic:notEqual name="subscriptionModelsForm" property="model.id" value="0">	
		<bean:define id="pagetitle" value="Edit Subscription Model"/>
	</logic:notEqual>

	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="payment"/>	
</head>

<body id="adminPage" >
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	
	<h2>Model details</h2>

	<%-- Show warning if subscription has active users --%>
	<c:if test="${subscriptionModelsForm.model.hasActiveSubscriptions}">											
		<p>Note that this subscription model has active subscriptions, which may be affected by changes you make.</p>
	</c:if>
	
	<logic:present  name="subscriptionModelsForm">
		<logic:equal name="subscriptionModelsForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="subscriptionModelsForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	
	
	<html:form action="saveSubscriptionModel" method="post" focus="model.description">
		
		<%-- Pass through params in case of validation error --%>
		<bean:parameter id="id" name="id" value="0" />
		<bean:parameter id="active" name="active" value="" />
		<input type="hidden" name="id" value="<c:out value='${id}' />" />
		<input type="hidden" name="active" value="<c:out value='${active}' />" />
			
		<html:hidden name="subscriptionModelsForm" property="model.id" />
		
		<table cellspacing="0" class="form" summary="Form for subscription model details">
			<tr>
				<th>
					<label for="description">Description:</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="description" name="subscriptionModelsForm" property="model.description" size="16" maxlength="100"/>										
				</td>	
			</tr>
			<logic:notEmpty name="subscriptionModelsForm" property="model.translations">
				<logic:iterate name="subscriptionModelsForm" property="model.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="subscriptionModelDesc<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="subscriptionModelsForm" property="model.translations[${tIndex}].language.id"/>
								<html:hidden name="subscriptionModelsForm" property="model.translations[${tIndex}].language.name"/>
								<input type="text" class="text" name="model.translations[<bean:write name='tIndex'/>].description" maxlength="40" id="subscriptionModelDesc<bean:write name='tIndex'/>" value="<bean:write name="translation" property="description" filter="false"/>"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>												
				<th>
					<label for="price">Price:</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="price" name="subscriptionModelsForm" property="model.price.formAmount" size="16" maxlength="16"/>
				</td>													
			</tr>
			<tr>												
				<th>
					<label for="numdownloads">No of downloads (per day):</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="numdownloads" name="subscriptionModelsForm" property="model.noOfDownloads.formNumber" size="16" maxlength="16"/>
				</td>													
			</tr>
			<tr>												
				<th>
					<label for="duration">Duration (days):</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="duration" name="subscriptionModelsForm" property="model.duration.formNumber" size="16" maxlength="16"/>
				</td>													
			</tr>
			<tr>												
				<th>
					<label for="disabled">Disabled:</label>
				</th>
				<td>
					<html:checkbox styleClass="checkbox" styleId="disabled" name="subscriptionModelsForm" property="model.inactive" />
				</td>													
			</tr>	
		</table>
		
		<div class="hr"></div>
		
		<h2>Groups</h2>

		<table cellspacing="0" class="form" summary="Form for adding groups to subscription model">
			<tr>
				<th>Subscription Groups:</th>
				<td colspan="2">
					<logic:iterate name="subscriptionModelsForm" property="groups" id="group" indexId="index">
						<c:choose>
							<c:when test="${group.id le 2}">
								<%-- Do not include logged in users or public --%>
							</c:when>
							<c:otherwise>
								<!-- check if the current group should be selected or not -->
								<logic:match name="subscriptionModelsForm" property="model.isInGroup[${group.id}]" value="true">
									<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" checked="checked" value="<bean:write name='group' property='id'/>">
								</logic:match>
								<logic:notMatch name="subscriptionModelsForm" property="model.isInGroup[${group.id}]" value="true">
									<logic:equal name="subscriptionModelsForm" property="hasErrors" value="true">
										<logic:match name="subscriptionModelsForm" property="selectedGroup[${group.id}]" value="true">
											<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" checked="checked" value="<bean:write name='group' property='id'/>">
										</logic:match>
										<logic:notMatch name="subscriptionModelsForm" property="selectedGroup[${group.id}]" value="true">
											<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" value="<bean:write name='group' property='id'/>">
										</logic:notMatch>
									</logic:equal>
									<logic:notEqual name="subscriptionModelsForm" property="hasErrors" value="true">
										<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" value="<bean:write name='group' property='id'/>">
									</logic:notEqual>
								</logic:notMatch>
						
								&nbsp;<bean:write name="group" property="nameWithOrgUnit"/><br />
	
							</c:otherwise>
						</c:choose>
					</logic:iterate>
				</td>
			</tr>
		</table>	
		
		
		<div class="hr"></div>
		
		<h2>Upgrades</h2>
		
		<p>Fill in below whether it is possible to upgrade from this model to other models, and the associated upgrade prices.</p>

		<table cellspacing="0" class="form" summary="Form for defining upgrades for subscription model">
			<tr>
				<th>
					Model
				</th>
				<th>
					Upgrade available
				</th>
				<th>
					Price
				</th>
			</tr>

			<logic:iterate name="subscriptionModelsForm" property="models" id="upgradeModel" indexId="index">
				<c:choose>
					<c:when test="${upgradeModel.id == subscriptionModelsForm.model.id}">
						<%-- Do not include the current model --%>
					</c:when>
					<c:otherwise>
						<tr>
							<td>
								<c:out value="${upgradeModel.description}" />
								<c:if test="${upgradeModel.inactive}">(inactive)</c:if>					
							</td>
							
							<!-- check whether upgrade should be selected or not -->							
							<c:choose>
								<c:when test="${subscriptionModelsForm.upgradeSelected[upgradeModel.id]}">
									<td><input type="checkbox" class="checkbox" name="upgradecheck_<bean:write name='upgradeModel' property='id'/>" checked="checked" value="<bean:write name='upgradeModel' property='id'/>"></td>
									<td><input type="text" name="upgradeprice_<bean:write name='upgradeModel' property='id' />" value="<c:out value='${subscriptionModelsForm.upgradePrices[upgradeModel.id]}' escapeXml='false' />" /></td>
								</c:when>
								<c:otherwise>
									<td><input type="checkbox" class="checkbox" name="upgradecheck_<bean:write name='upgradeModel' property='id'/>" value="<bean:write name='upgradeModel' property='id'/>"></td>
									<td><input type="text" name="upgradeprice_<bean:write name='upgradeModel' property='id' />" value="" /></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:otherwise>
				</c:choose>
			</logic:iterate>
		</table>	
		
		
		<div class="hr"></div>
		
		<div class="buttonHolder">
			<input type="submit" class="button" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		</div>		
	</html:form>
	<form name="cancelForm" action="viewManagePayment" method="get">
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton">
	</form>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>