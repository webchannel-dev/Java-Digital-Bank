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
	<logic:equal name="taxValueForm" property="taxValue.taxType.id" value="0">	
		<bean:define id="pagetitle" value="Add Tax Value"/>
	</logic:equal>
	<logic:notEqual name="taxValueForm" property="taxValue.taxType.id" value="0">	
		<bean:define id="pagetitle" value="Edit Tax Value"/>
	</logic:notEqual>

	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="payment"/>	
</head>

<body id="adminPage" >
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="taxValueForm">
		<logic:equal name="taxValueForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="taxValueForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<html:form action="saveTaxValue" method="post" focus="taxValue.taxRegion.id">
		
		<%-- Hard code the tax type to 1 --%>
		<input type="hidden" name="taxValue.taxType.id" value="1" />
		
		<%-- Pass through ids in case of validation error --%>
		<bean:parameter id="taxtypeid" name="taxtypeid" value="0" />
		<bean:parameter id="taxregionid" name="taxregionid" value="0" />
		<input type="hidden" name="taxtypeid" value="${taxtypeid}" />
		<input type="hidden" name="taxregionid" value="${taxregionid}" />
		
		<table cellspacing="0" class="form" summary="Form for tax value details">
			<tr>
				<th>
					<label for="taxtype">Tax Region:</label>
				</th>
				<td>
					<bright:refDataList componentName="TaxManager" methodName="getTaxRegionList" id="taxRegionList"/>
					<html:select name="taxValueForm" property="taxValue.taxRegion.id" styleId="taxtype">
						<html:option value="0">-- Please select --</html:option>
						<html:optionsCollection name="taxRegionList" value="id" label="name"/>
					</html:select>									
				</td>	
			</tr>
			<tr>												
				<th>
					<label for="percent">Tax Percent:</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="percent" name="taxValueForm" property="taxValue.taxPercent.formNumber" size="16" maxlength="16"/>
				</td>													
			</tr>
			<tr>												
				<th>
					<label for="zero">Zero if tax number given:</label>
				</th>
				<td>
					<html:checkbox name="taxValueForm" property="taxValue.zeroIfTaxNumberGiven" styleClass="checkbox" styleId="zero" />
				</td>													
			</tr>	
		</table>
		
		<div class="hr"></div>
		
		<div class="buttonHolder">
			<input type="submit" class="button" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		</div>		
	</html:form>
	<form name="cancelForm" action="viewTaxAdmin" method="get">
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton">
	</form>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>