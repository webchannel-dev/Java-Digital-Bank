<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
    d1      Martin Wilson    13-Jul-2006    Created                                     
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>

<head>
	<title><bright:cmsWrite identifier="e-title-order-history" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="purchases"/>
	<script type="text/JavaScript">
		$j(function () {
			initDatePicker();
		});
	</script>
</head>

<body id="lightboxPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1>
	
	<h2>
		<bright:cmsWrite identifier="e-link-purchased-items" filter="false"/>
	</h2>

<%-- Always show the date range form --%>
	<div style="padding:1em 0;">
		<logic:present name="reportForm"> 
			<logic:equal name="reportForm" property="hasErrors" value="true"> 
				<div class="error">
					<logic:iterate name="reportForm" property="errors" id="errorText">
						<bean:write name="errorText" filter="false"/><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>

		<html:form action="viewOrderHistory" method="get">
		
		<p><span class="inlineLabel"><bright:cmsWrite identifier="e-label-from" filter="false"/></span> <html:text styleClass="small text date" name="reportForm" property="startDateString" size="20"/> 
		
		&nbsp;
		<span class="inlineLabel"><bright:cmsWrite identifier="e-label-to" filter="false"/></span> <html:text styleClass="small text date" name="reportForm" property="endDateString" size="20"/> 
		
		
		&nbsp;
		
		<input type="submit" name="submit" class="button inline" value="<bright:cmsWrite identifier="button-go-arrow" filter="false" />" />
		</p>
		
		</html:form>
	</div>

<%-- Show the rest if there are no errors --%>
<logic:notEqual name="reportForm" property="hasErrors" value="true"> 
	
	<logic:empty name="reportForm" property="details">
		<p><bright:cmsWrite identifier="e-no-orders-found" filter="false"/></p>
	</logic:empty>
	<logic:notEmpty name="reportForm" property="details">
		<h3 class="report">
		<c:choose>
			<c:when test="${reportForm.startDate==null&&reportForm.endDate==null}">
				<bright:cmsWrite identifier="e-search-all-orders" filter="false"/>
			</c:when>
			<c:when test="${reportForm.startDate!=null&&reportForm.endDate==null}">
				<bright:cmsWrite identifier="e-search-orders-since" filter="false"/> <bean:write name="reportForm" property="startDate" format="dd/MM/yyyy"/>
			</c:when>
			<c:when test="${reportForm.startDate==null&&reportForm.endDate!=null}">
				<bright:cmsWrite identifier="e-search-orders-before" filter="false"/> <bean:write name="reportForm" property="endDate" format="dd/MM/yyyy"/>
			</c:when>
			<c:when test="${reportForm.startDate!=null&&reportForm.endDate!=null}">
			<bright:cmsWrite identifier="e-search-orders-between" filter="false"/> <bean:write name="reportForm" property="startDate" format="dd/MM/yyyy"/> <bright:cmsWrite identifier="e-and" filter="false"/> <bean:write name="reportForm" property="endDate" format="dd/MM/yyyy"/>
			</c:when>
		</c:choose>
		</h3>
		<div class="hr"></div>
		<logic:iterate name="reportForm" property="details" id="order">
			<table class="report" cellspacing="0" border="0">
				<tr>
					<td style="width:287px;"><strong><bright:cmsWrite identifier="e-order-id" filter="false"/>:</strong>&nbsp; <bean:write name="order" property="displayPurchaseId"/></td>
					<td><strong><bright:cmsWrite identifier="e-psp-id" filter="false"/>:</strong>&nbsp;<bean:write name="order" property="pspTransId" /></td>
				</tr>
				<tr>
					<td><strong><bright:cmsWrite identifier="e-date-placed" filter="false"/>: </strong>&nbsp;<bean:write name="order" property="datePlaced" format="dd/MM/yyyy"/></td>
					<td></td>
				</tr>
				
				<tr class="topSpace">
					<th><bright:cmsWrite identifier="e-order-details" filter="false"/>:</th>
					<td>&nbsp;</td>
				</tr>	
				<logic:iterate name="order" property="assets" id="asset">
				<tr>
					<td><bean:write name="asset" property="description"/></td>
					<td><bean:write name="asset" property="price.displayAmount" filter="false" /></td>
				</tr>
				</logic:iterate>
				<tr >
					<th><bright:cmsWrite identifier="e-subtotal" filter="false"/>:</th>
					<td><bean:write name="order" property="subtotal.displayAmount" filter="false" /></td>
				</tr>
				<tr>
					<th><bright:cmsWrite identifier="e-tax" filter="false"/>:</th>
					<td><bean:write name="order" property="VATPercent.displayNumber"/>%</td>
				</tr>
				<tr>
					<th><bright:cmsWrite identifier="e-total" filter="false"/>:</th>
					<td><bean:write name="order" property="total.displayAmount" filter="false" /></td>
				</tr>
			</table>
		<div class="rule"></div>
		</logic:iterate>
	</logic:notEmpty>

</logic:notEqual>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>