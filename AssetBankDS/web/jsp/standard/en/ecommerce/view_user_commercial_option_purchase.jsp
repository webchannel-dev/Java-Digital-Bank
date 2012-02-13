<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Kevin Bennett		08-Jan-2007		Created
	d2      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />

<head>
	<bean:define id="section" value="purchases"/>
	<title><bright:cmsWrite identifier="e-commercial-option" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>


<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	<h1 class="underline"><bright:cmsWrite identifier="e-commercial-option" filter="false"/></h1>
	
	<table class="report" cellspacing="0" border="0"> 
		<tr>
			<th><bright:cmsWrite identifier="e-commercial-option" filter="false"/>:</th>		
			<td>
				<bean:write name="commercialOrderForm" property="commercialOptionPurchase.commercialOption.name"/>
			</td>
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="e-price" filter="false"/>:</th>
			<td>
				<logic:equal name="commercialOrderForm" property="commercialOptionPurchase.price.amount" value="0">
					<bright:cmsWrite identifier="e-tbc" filter="false" replaceVariables="true" />
				</logic:equal>
				<logic:notEqual name="commercialOrderForm" property="commercialOptionPurchase.price.amount" value="0">
					<bean:write name="commercialOrderForm" property="commercialOptionPurchase.price.displayAmount"/>
				</logic:notEqual>
				
			</td>		 
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="e-label-description" filter="false"/></th>
			<td>
				<bean:write name="commercialOrderForm" property="commercialOptionPurchase.formDescription"/>
			</td>		 
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="e-subhead-terms-and-conditions" filter="false"/>:</th>
			<td>
				<bean:write name="commercialOrderForm" property="commercialOptionPurchase.commercialOption.terms" filter="false"/>
			</td>		 
		</tr>
	</table>

	<div class="hr"></div>	
	
	<a href="viewUserOrderDetails?orderId=<bean:write name="commercialOrderForm" property="orderId"/>"><bright:cmsWrite identifier="e-link-back-to-order-details" filter="false"/></a>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>