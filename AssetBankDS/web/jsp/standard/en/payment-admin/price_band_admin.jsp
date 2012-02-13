<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan	18-Dec-2006		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="showSubscription" settingName="subscription"/>
<bright:applicationSetting id="showTaxCalculator" settingName="tax-calculator"/>
<bright:applicationSetting id="showPriceBands" settingName="price-bands"/>
<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Price Bands</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="payment"/>
	<bean:define id="tabId" value="priceBands"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1>Price Bands</h1> 

	<%@include file="../payment-admin/inc_payment_tabs.jsp"%>
	<br />

	<logic:present name="priceBandForm">
		<logic:equal name="priceBandForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="priceBandForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	

	<logic:empty name="priceBandForm" property="priceBandList">
		<p>There are currently no price bands in the system.</p>
	</logic:empty>			
	
	<logic:notEmpty name="priceBandForm" property="priceBandList">
	<table cellspacing="0" class="admin" summary="List of price bands">
		<tr>
			<th>Asset Type</th>
			<th>Name</th>
			<th>Type</th>
			<th>Description</th>
			<th>Base Price</th>
			<th>Unit Price</th>
			<th>Max Quantity</th>
			<th>Download Original</th>
			<th>Is Commercial</th>
			<th colspan="2">&nbsp;</th>
		</tr>
		
		<logic:iterate name="priceBandForm" property="priceBandList" id="priceband">
			<tr>
				<td><bean:write name="priceband" property="assetType.name"/></td>
				<td><bean:write name="priceband" property="name"/></td>
				<td><bean:write name="priceband" property="priceBandType.name"/></td>
				<td><bean:write name="priceband" property="description"/></td>
				<td><bean:write name="priceband" property="basePrice.displayAmount" filter="false"/></td>
				<c:choose>
					<c:when test="${priceband.priceBandType.id == 2}">
						<%-- Print --%>
						<td>
							<bean:write name="priceband" property="unitPrice.displayAmount" filter="false"/>
						</td>
						<td>
							<bean:write name="priceband" property="maxQuantity.displayNumber"/>
						</td>
					</c:when>
					<c:otherwise>
						<td>
							n/a
						</td>
						<td>
							n/a
						</td>
					</c:otherwise>
				</c:choose>
				<td>
					<c:choose>
						<c:when test="${priceband.priceBandType.id == 1}">
							<%-- Download --%>
							<c:choose>
								<c:when test="${priceband.canDownloadOriginal}">Yes</c:when>
								<c:otherwise>No</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							n/a
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${priceband.priceBandType.id == 1}">
							<%-- Download --%>
							<c:choose>
								<c:when test="${priceband.isCommercial}">Yes</c:when>
								<c:otherwise>No</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							n/a
						</c:otherwise>
					</c:choose>
				</td>
				<td class="action">
					<c:choose>
						<c:when test="${priceband.priceBandType.id == 1}">
							[<a href="viewDownloadPriceBand?id=<bean:write name='priceband' property='id'/>">edit</a>]
						</c:when>
						<c:otherwise>
							[<a href="viewPrintPriceBand?id=<bean:write name='priceband' property='id'/>">edit</a>]																																																																		</c:otherwise>
					</c:choose>
				</td>
				<td class="action">
					[<a href="deletePriceBand?id=<bean:write name='priceband' property='id'/>" onclick="return confirm('Are you sure you want to delete this price band?');">X</a>]
				</td>
			</tr>
		</logic:iterate>
	</table>	
	</logic:notEmpty>	
	
	<div class="hr"></div>
	
	<p>
		<a href="viewDownloadPriceBand">Add a download price band &raquo;</a><br />
		<a href="viewPrintPriceBand?type=2">Add a print price band &raquo;</a>		
	</p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>