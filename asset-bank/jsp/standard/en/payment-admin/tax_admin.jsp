<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan	21-Jul-2006		Created
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Tax</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="payment"/>
	<bean:define id="tabId" value="tax"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1>Tax</h1> 

	<%@include file="../payment-admin/inc_payment_tabs.jsp"%>
	<br />

	<logic:present  name="taxValueForm">
		<logic:equal name="taxValueForm" property="hasErrors" value="true">
			<div class="error">
			<logic:iterate name="taxValueForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
			</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	

	<logic:empty name="taxValueForm" property="taxValueList">
		<p>There are currently no tax values in the system.</p>
	</logic:empty>			
	
	<logic:notEmpty name="taxValueForm" property="taxValueList">
	<table cellspacing="0" class="admin" summary="List of tax values">
		<tr>
			<th>Tax Region</th>
			<th>Percent</th>
			<th>Zero if tax number given</th>
			<th colspan="2">&nbsp;</th>
		</tr>
		
		<logic:iterate name="taxValueForm" property="taxValueList" id="tax">
			<tr>
				<td><bean:write name="tax" property="taxRegion.name"/></td>
				<td><bean:write name="tax" property="taxPercent.displayNumber"/></td>
				<td>
					<c:choose>
						<c:when test="${tax.zeroIfTaxNumberGiven}">Yes</c:when>
						<c:otherwise>No</c:otherwise>
					</c:choose>
				</td>
				<td class="action">
					[<a href="viewTaxValue?taxtypeid=<bean:write name='tax' property='taxType.id'/>&taxregionid=<bean:write name='tax' property='taxRegion.id'/>">edit</a>]
				</td>
				<td class="action">
					[<a href="deleteTaxValue?taxtypeid=<bean:write name='tax' property='taxType.id'/>&taxregionid=<bean:write name='tax' property='taxRegion.id'/>" onclick="return confirm('Are you sure you want to delete this tax value?');">X</a>]
				</td>
			</tr>
		</logic:iterate>
	</table>	
	</logic:notEmpty>	
	
	<div class="hr"></div>
	
	<p><a href="viewTaxValue">Add a tax value &raquo;</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>