<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Kevin Bennett		08-Jan-2007		Created
	d2		Kevin Bennett		24-Jan-2007		Amended so main page content is not displayed if no commercial options in system
	d3      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bean:parameter id="trans_id" name="trans_id" value="" />

<head>
	<title><bright:cmsWrite identifier="e-title-review-commercial-options" filter="false" replaceVariables="true" /></title>  
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="checkout"/>
</head>

<body id="approvalPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="e-heading-review-order"/></h1> 

		<logic:equal name="checkoutForm" property="hasErrors" value="true"> 
			<div class="error">
				<logic:iterate name="checkoutForm" property="errors" id="errorText">
					<bean:write name="errorText" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>

		<logic:notEmpty name="checkoutForm.commercialOptionsList">
		<p>
			<bright:cmsWrite identifier="e-review-commercial-option" filter="false"/>
		</p>			
			
		<br />							
				<ul class="lightbox clearfix cart option">
			
				<li class="headers">	         
				<div class="asset-name"><bright:cmsWrite identifier="item" filter="false" case="mixed" /></div>	         		
				<div class="asset-details">
					<span class="asset-option"><bright:cmsWrite identifier="e-commercial-option-nc" filter="false"/></span>
						<span class="asset-price"><bright:cmsWrite identifier="e-price" filter="false"/></span>
					</div>
			 </li>
								
				<logic:iterate name="userprofile" property="assetBox.assetsWithPriceBandsAsVec" id="assetInList" indexId="assetIndex">
				<bean:define id="asset" name="assetInList" property="asset" />																												
					<li class="clearfix">		
												
						<div class="asset-name">
						<h3><bean:write name='asset' property='name'/></h3>					
						</div>
						<div class="asset-details">
	
							<c:forEach var="price" items="${userprofile.assetBox.assetPrices[asset.id]}">
								
								
								<c:set var="commercialOption" value="${userprofile.assetBox.assetPriceCommercialOptions[price.hashInAssetBox]}" />																												
																												
								<div id="asset-item-details">
										
									<span class="asset-option">	
											<bean:write name="commercialOption" property="name"/>
									</span>
									<span class="asset-price">
										<logic:equal name="commercialOption" property="price.amount" value="0">
												<bright:cmsWrite identifier="e-tbc" filter="false"/>
										</logic:equal>
										<logic:notEqual name="commercialOption" property="price.amount" value="0">
											<bean:write name="commercialOption" property="price.displayAmount" filter="false"/>	
										</logic:notEqual>
									</span>
									</div>
							</c:forEach>
						</div>						
					</li>						
				</logic:iterate>
				<li id="totals">
	
						
						<div class="asset-name">&nbsp;</div>
						<div class="asset-details">
	
						<span class="asset-option">&nbsp;</span>	
						<span class="asset-price">
						<p class="totals">
					
							<%-- Shipping --%>
							<c:if test="${useShippingCosts && userprofile.assetBox.hasPrint}">
								<bright:cmsWrite identifier="a-lightbox" filter="false"/> <bright:cmsWrite identifier="e-cost" filter="false"/>: <bean:write name="userprofile" property="assetBox.basketTotal.displayAmount" filter="false" /><br />
								<bright:cmsWrite identifier="e-shipping" filter="false"/>
								<c:if test="${not empty userprofile.assetBox.shippingRegion.name}">(<c:out value="${userprofile.assetBox.shippingRegion.name}" />)</c:if>
								:
								<bean:write name="userprofile" property="assetBox.shippingTotal.displayAmount" filter="false" /><br />	
							</c:if>
				
							<%-- Subtotal --%>
							<bright:cmsWrite identifier="e-subtotal" filter="false"/>: <bean:write name="userprofile" property="assetBox.price.subtotalAmount.displayAmount" filter="false" /><br />
							
							<%-- Tax details --%>
							<logic:notEmpty name="userprofile" property="assetBox.price.taxNumber">
								<bright:cmsWrite identifier="e-label-tax-number" filter="false"/> <bean:write name="userprofile" property="assetBox.price.taxNumber" filter="false" /><br />
							</logic:notEmpty>
							<c:if test="${showTaxRegion && not empty userprofile.assetBox.price.tax.taxRegion.name}">
								<bright:cmsWrite identifier="e-label-tax-region" filter="false"/> <bean:write name="userprofile" property="assetBox.price.tax.taxRegion.name" filter="false" /><br />
							</c:if>
							
							<c:if test="${!userprofile.assetBox.price.excludesTax}">
								<%-- Show potential tax amount --%>
								<bean:write name="userprofile" property="assetBox.price.tax.taxType.name" /> @ <bean:write name="userprofile" property="assetBox.price.tax.taxPercent.displayNumber" />%: 
									<bean:write name="userprofile" property="assetBox.price.taxAmountWithoutNumber.displayAmount" filter="false" /><br />
								
								<%-- If tax can be zeroed, show tax amount --%>
								<logic:notEmpty name="userprofile" property="assetBox.price.taxNumber">
									<c:if test="${userprofile.assetBox.price.tax.zeroIfTaxNumberGiven}">
										<bean:write name="userprofile" property="assetBox.price.tax.taxType.name" /> <bright:cmsWrite identifier="e-label-added" filter="false"/> <bean:write name="userprofile" property="assetBox.price.taxAmount.displayAmount" filter="false" /><br />
									</c:if>
								</logic:notEmpty>
							</c:if>
							
							<%-- Total --%>
							<strong>
								<bright:cmsWrite identifier="e-total" filter="false"/>
								<c:if test="${userprofile.assetBox.price.excludesTax}"><bright:cmsWrite identifier="e-excluding-tax" filter="false"/></c:if>
								: <bean:write name="userprofile" property="assetBox.price.totalAmount.displayAmount" filter="false" />
							</strong><br />
					
						</p>	
				   </span>
				</li>
				</ul>
	
			<br />
			<div class="hr"></div>
			&nbsp;
		</logic:notEmpty>
						
		<form action="../action/submitCommercialPurchase" method="get" name="checkoutForm">
			<input type="hidden" name="trans_id" value="<c:out value='${trans_id}' />" />
				<bright:cmsWrite identifier="commercial-user-notes-intro" filter="false"/><br/>
				<html:textarea name="checkoutForm" property="userNotes" rows="7" cols="90" />
			<div class="hr"/></div>
			
			<p><bright:cmsWrite identifier="request-offline-payment-text" filter="false"/></p>
			<p><html:checkbox name="checkoutForm" property="prefersOfflinePayment" styleClass="checkbox" styleId="payOffline" /> <label for="payOffline"><bright:cmsWrite identifier="e-label-pay-offline" filter="false"/></label></p> 	
			<div class="hr"></div>
		
			<div class="buttonHolder">
				<input type="submit" class="button" value="<bright:cmsWrite identifier="button-submit" filter="false" />" />
			</div>
		</form>
										
		<form name="cancelForm" action="viewAssetBox" method="get">
			<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton"></input>
		</form>


				
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>