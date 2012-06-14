<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Kevin Bennett	04-Jan-2007		Created
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Commercial Options</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="payment"/>
	<bean:define id="tabId" value="commercialOptions"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1>Commercial Options</h1> 

	<%@include file="../payment-admin/inc_payment_tabs.jsp"%>
	<br />

	<logic:present name="commercialOptionForm">
		<logic:equal name="commercialOptionForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="commercialOptionForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	

	<logic:empty name="commercialOptionForm" property="commercialOptionList">
		<p>There are currently no commercial options in the system.</p>
	</logic:empty>			
	
	<logic:notEmpty name="commercialOptionForm" property="commercialOptionList">
	<table cellspacing="0" class="admin" summary="List of commercial options">
		<tr>
			<th>Name</th>
			<th>Price</th>
			<th colspan="2">&nbsp;</th>
		</tr>
		
		<logic:iterate name="commercialOptionForm" property="commercialOptionList" id="commopt">
			<tr>
				<td><bean:write name="commopt" property="name"/></td>
				<td><bright:writeMoney name="commopt" property="price.displayAmount" /></td>
				<td class="action">
					[<a href="viewCommercialOption?id=<bean:write name='commopt' property='id'/>">edit</a>]
				</td>
				<td class="action">
					[<a href="deleteCommercialOption?id=<bean:write name='commopt' property='id'/>" onclick="return confirm('Are you sure you want to delete this commercial option?');">X</a>]
				</td>
			</tr>
		</logic:iterate>
	</table>	
	</logic:notEmpty>	
	
	<div class="hr"></div>
	
	<p>
		<a href="viewCommercialOption">Add a commercial option &raquo;</a><br />
	</p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>