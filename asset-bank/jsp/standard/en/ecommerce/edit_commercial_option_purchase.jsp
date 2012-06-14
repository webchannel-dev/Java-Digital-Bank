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
<bean:define id="pagetitle" value="Commercial Option Edit"/>

<head>
	<bean:define id="section" value="orders"/>
	<title>Commercial Option Edit</title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>


<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	<h1 class="underline"><bean:write name="pagetitle" /></h2>
		
	<p>
		<logic:notEmpty name="commercialOrderForm" property="commercialOptionsList">
			<div class="panelWide">
				<h3><bright:cmsWrite identifier="e-commercial-option-types" filter="false"/></h3>
				<table class="report" cellspacing="10px" border="0">
					<tr>
						<th><bright:cmsWrite identifier="e-name" filter="false"/></th>
						<th><bright:cmsWrite identifier="e-description" filter="false"/></th>
						<th><bright:cmsWrite identifier="e-price" filter="false"/></th>
						<th><bright:cmsWrite identifier="e-terms" filter="false"/></th>
					</tr>
					<logic:iterate name="commercialOrderForm" property="commercialOptionsList" id="commercialOption" >
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
								<bright:writeMoney name="commercialOption" property="price.displayAmount" />
							</td>				
							<td>
								<a href="../action/viewTerms?id=<bean:write name='commercialOption' property='id'/>" onclick="popupTerms('<bean:write name='commercialOption' property='id'/>'); return (false);" onkeypress="popupHelp('<bean:write name='commercialOption' property='id'/>'); return (false);"><bright:cmsWrite identifier="e-label-option-view" filter="false"/></a>
							</td>	
						</tr>
					</logic:iterate>
				</table>
			</div>
		</p>
			
		<div class="hr"></div>
	
	
		<table class="report" cellspacing="0" border="0">
			<tr>
				<html:form action="editCommercialOptionPurchase" method="get">
					<input type ="hidden" name="commercialOptionPurchaseEditType" value="noReset"></input>	
					<th><bright:cmsWrite identifier="e-confirm-commercial-option" filter="false"/></th>		
					<td>
						<html:select name="commercialOrderForm" property="commercialOptionPurchase.commercialOption.id" >											
							<html:optionsCollection property="commercialOptionsList" label="name" value="id"/>							
						</html:select>
						<html:submit property="b_select" styleClass="button inline" value="Select &raquo;"/>
					</td>
				</html:form>
			</tr>
		</table>
	
	<div class="hr"></div>	
		
	</logic:notEmpty>
		
	
	
	<html:form action="updateCommercialOptionPurchase" method="post">
		<html:hidden name="commercialOrderForm" property="orderId"></html:hidden>
		<html:hidden name="commercialOrderForm" property="priceBandId"></html:hidden>	
		<html:hidden name="commercialOrderForm" property="assetId"></html:hidden>	
		
		
			<table class="report" cellspacing="0" border="0">
				<tr>
					<th><bright:cmsWrite identifier="e-price" filter="false"/>:</th>
					<td>

							<html:text styleClass="text" styleId="price" name="commercialOrderForm" property="commercialOptionPurchase.price.formAmount"/>

					</td>		 
				</tr>
				<logic:notEmpty name="commercialOrderForm" property="commercialOptionsList">
					<tr>
						<th><bright:cmsWrite identifier="e-description" filter="false" case="mixed"/>:</th>
						<td>
								<html:textarea styleClass="text" styleId="description" name="commercialOrderForm" property="commercialOptionPurchase.formDescription" rows="6" style="width: 400px;"/>
						</td>	 
					</tr>
				</logic:notEmpty>
			</table>
		

		<div class="hr"></div>	
	
		<div class="buttonHolder">
			<input type="submit" class="button" value="<bright:cmsWrite identifier="button-save" filter="false" />"<logic:notEmpty name="commercialOrderForm" property="commercialOptionsList"> onclick="return (confirm('<bright:cmsWrite identifier="e-js-confirm-save-commercial-option" filter="false"/>'));"</logic:notEmpty>> 
		</div>		
	</html:form>
	
	<form name="cancelForm" action="viewCommercialOrderDetails" method="get">
		<html:hidden name="commercialOrderForm" property="orderId"></html:hidden>
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton"></input>
	</form>
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>