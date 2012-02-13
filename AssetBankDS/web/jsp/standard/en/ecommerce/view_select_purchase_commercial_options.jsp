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
	
<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Select ${offlineOptionName} Options"/>

<head>
	<title><bright:cmsWrite identifier="e-title-review-commercial-options" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="checkout"/>
</head>

<body id="approvalPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline">Select <bright:cmsWrite identifier="e-ecommerce-offline-option-name" filter="false"/> Options</h1> 

	<logic:empty  name="checkoutForm" property="commercialOptionsList">
		<p>
			<bright:cmsWrite identifier="e-no-commercial-options-available" filter="false"/>
		</p>	
		<p><a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-lightbox" filter="false" /></a></p>
	</logic:empty>
	<logic:notEmpty  name="checkoutForm" property="commercialOptionsList">
		<logic:equal name="checkoutForm" property="hasErrors" value="true"> 
			<div class="error">
			<logic:iterate name="checkoutForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
			</div>
		</logic:equal>

		<p>
			<bright:cmsWrite identifier="e-determine-commercial-option" filter="false"/>
		</p>
		&nbsp;
		<div class="panelWide">
			<h3><bright:cmsWrite identifier="e-commercial-option-types" filter="false"/></h3>
			<table class="report" cellspacing="10px" border="0">
				<tr>
					<th><bright:cmsWrite identifier="e-name" filter="false"/></th>
					<th><bright:cmsWrite identifier="e-description" filter="false"/></th>
					<th><bright:cmsWrite identifier="e-price" filter="false"/></th>
					<th><bright:cmsWrite identifier="e-subhead-terms-and-conditions" filter="false"/></th>
				</tr>
				<logic:iterate name="checkoutForm" property="commercialOptionsList" id="commercialOption" >
					<tr>
						<td>
							<strong>
								<bean:write name="commercialOption" property="name"/>
							</strong>
						</td>
						<td>
							<bean:write name="commercialOption" property="description"/>
						</td>
						<td>
							<logic:equal name="commercialOption" property="price.amount" value="0">
										<bright:cmsWrite identifier="e-tbc" filter="false"/>
							</logic:equal>
							<logic:notEqual name="commercialOption" property="price.amount" value="0">
									<bean:write name="commercialOption" property="price.displayAmount" filter="false"/>									
							</logic:notEqual>
						</td>	
						<td>
							<a href="../action/viewTerms?id=<bean:write name='commercialOption' property='id'/>" onclick="popupTerms('<bean:write name='commercialOption' property='id'/>'); return (false);" onkeypress="popupHelp('<bean:write name='commercialOption' property='id'/>'); return (false);">[<bright:cmsWrite identifier="e-view" filter="false"/>]</a>
						</td>			
					</tr>
				</logic:iterate>
			</table>
		</div>
			
		<div class="hr"></div>
		&nbsp;
		<p>
			<bright:cmsWrite identifier="e-select-commercial-option" filter="false" replaceVariables="true" />
		</p>
		&nbsp;
		<html:form action="viewReviewPurchaseCommercialOptions" method="get" >
			
			
			<ul class="lightbox clearfix cart option">
		
			<li class="headers">
         	<div class="asset-thumb">&nbsp;</div>
         	<div class="asset-name"><bright:cmsWrite identifier="item" filter="false" case="mixed" /></div>
         	<div class="asset-details">
         		<span class="asset-option"><bright:cmsWrite identifier="e-commercial-option-nc" filter="false"/></span>
				</div>
         </li>
				
		
			<logic:iterate name="userprofile" property="assetBox.assetsWithPriceBandsAsVec" id="assetInList" indexId="assetIndex">
				<bean:define id="asset" name="assetInList" property="asset" />														
				<li class="clearfix">		
							
					<c:if test="${asset.typeId!=2}">
						<c:set var="resultImgClass" value="icon"/>
					</c:if>
					<c:if test="${asset.typeId==2}">
						<c:set var="resultImgClass" value="image"/>
					</c:if>
					
					<div class="asset-thumb">
						<a class="<bean:write name='resultImgClass'/>" href="viewAsset?id=<bean:write name='asset' property='id'/>">
							<bean:define id="disablePreview" value="true"/>	
							<%@include file="../inc/view_thumbnail.jsp"%>
						</a><br />            		
					</div>
					
						<div class="asset-name">
								 <h3><a href="viewAsset?id=<bean:write name='asset' property='id'/>"><bean:write name='asset' property='name'/></a></h3>
						</div>
					<div class="asset-details">

						
						<c:forEach var="price" items="${userprofile.assetBox.assetPrices[asset.id]}" varStatus="priceIndex">
							<div id="asset-item-details">																	
								<span class="asset-option">	
								<html:select name="checkoutForm" property="assetPriceCommercialOption(${userprofile.assetBox.assetPrices[userprofile.assetBox.assetsWithPriceBandsAsVec[assetIndex].asset.id][priceIndex.index].hashInAssetBox}).id" >
									<html:optionsCollection name="checkoutForm" property="commercialOptionsList" label="name" value="id"/>
								</html:select>
								</span>
 								</div>
						</c:forEach>
									
					</div>						
				</li>						
			</logic:iterate>
			</ul>
	
			<div class="buttonHolder">
				<input type="submit" class="button" value="<bright:cmsWrite identifier="button-next" filter="false" />" />
			</div>
		</html:form>
		<br />
	</logic:notEmpty>

					
		<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>