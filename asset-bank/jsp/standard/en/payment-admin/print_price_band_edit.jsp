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



<%-- Rename the priceband form reference --%>
<c:set var="priceBandForm" value="${printPriceBandForm}" />

<%-- Set type = 2 for print --%>
<c:set var="iType" value="2" />



<head>
	<logic:equal name="priceBandForm" property="priceBand.id" value="0">	
		<bean:define id="pagetitle" value="Add Print Price Band"/>
	</logic:equal>
	<logic:notEqual name="priceBandForm" property="priceBand.id" value="0">	
		<bean:define id="pagetitle" value="Edit Print Price Band"/>
	</logic:notEqual>

	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="payment"/>	
</head>

<body id="adminPage" >
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="priceBandForm">
		<logic:equal name="priceBandForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="priceBandForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<html:form action="savePrintPriceBand" method="post" focus="priceBand.name">
		
		<%@include file="inc_price_band_form.jsp" %>
		
		<div class="hr"></div>
		
		<p>
			Specify the shipping costs for this price band:
		</p>
		
		<table cellspacing="0" class="form" summary="Table of shipping costs"  width="100%">
			
			<tr>
				<th></th>
				<logic:iterate name="priceBandForm" property="quantityRangeList" id="quantity">
					<th><c:out value="${quantity.name}" /></th>
				</logic:iterate>
			</tr>
			<logic:iterate name="priceBandForm" property="regionList" id="region">

			<tr>
				<th>
					<c:out value="${region.name}" />
				</th>
				<logic:iterate name="priceBandForm" property="quantityRangeList" id="quantity">
					<td>
						<c:set var="regionSelected" value="${priceBandForm.shippingCosts[region.id]}" />
						<c:set var="shippingCost" value="${regionSelected[quantity.id]}" />
						<c:set var="cellName" value="region_${region.id}_quantity_${quantity.id}" />
						<input type="text" class="text" name="<c:out value='${cellName}' />" value="<c:out value='${shippingCost.price.formAmount}' />" maxlength="10" size="5" />
					</td>
				</logic:iterate>
			</tr>

			</logic:iterate>
		</table>
		
		<div class="hr"></div>	
				
			<div class="buttonHolder">
			<input type="submit" class="button" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		</div>		
	</html:form>
	<form name="cancelForm" action="viewPriceBandAdmin" method="get">
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton">
	</form>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>