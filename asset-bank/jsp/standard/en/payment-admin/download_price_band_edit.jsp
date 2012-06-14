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
<c:set var="priceBandForm" value="${downloadPriceBandForm}" />

<%-- Set type = 1 for download --%>
<c:set var="iType" value="1" />



<head>
	<logic:equal name="priceBandForm" property="priceBand.id" value="0">	
		<bean:define id="pagetitle" value="Add Download Price Band"/>
	</logic:equal>
	<logic:notEqual name="priceBandForm" property="priceBand.id" value="0">	
		<bean:define id="pagetitle" value="Edit Download Price Band"/>
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
	
	<html:form action="saveDownloadPriceBand" method="post" focus="priceBand.name">
		
		<%@include file="inc_price_band_form.jsp" %>
	
		<div class="hr"></div>
		
		<p>
			Select the usage types to be included in this price band.
		</p>
		
		<table cellspacing="0" class="form" summary="Table of usages"  width="100%">
			<logic:iterate name="priceBandForm" property="usageTypeList" id="usage">

			<tr>
				<th>
					<bean:write name="usage" property="description"/>
				</th>
				<td style="padding-top:2px;">
						<%-- See if this usage is currently selected --%>
						<bean:define id="bSelected" value="false"/>
						<logic:iterate name="priceBandForm" property="includedUsageList" id="included">
							<c:if test="${included == usage.id}">
								<bean:define id="bSelected" value="true"/>
							</c:if>
						</logic:iterate>
						
						<input class="checkbox" type="checkbox" name="includedUsage_<bean:write name='usage' property='id'/>" <logic:equal name="bSelected" value="true">checked="checked"</logic:equal> value="<bean:write name="usage" property="description"/>">
				</td>
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