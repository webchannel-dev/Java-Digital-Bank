<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Martin Wilson		21-Dec-2005		Created
	d2		Matt Stevenson		18-Apr-2007		Fixed problem with VAT and added discount
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
</head>


<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>

	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<div class="head">
		<a href="../action/viewEcommerceReports?startDateString=<bean:write name='ecommerceReportForm' property='startDateString'/>&endDateString=<bean:write name='ecommerceReportForm' property='endDateString'/>&reportType=1">&laquo; Back to Reports</a>
	</div>
	
	<h2 class="report">Order Detail Report</h2>

	<logic:empty name="ecommerceReportForm" property="details">
		<p>There were no orders in the specified date range.</p>
	</logic:empty>
	<logic:notEmpty name="ecommerceReportForm" property="details">
		<c:choose>
			<c:when test="${ecommerceReportForm.startDate != null && ecommerceReportForm.endDate != null}">
				<h3 class="report">Report date range: <bean:write name="ecommerceReportForm" property="startDate" format="dd/MM/yyyy"/> to <bean:write name="ecommerceReportForm" property="endDate" format="dd/MM/yyyy"/></h3>
			</c:when>
			<c:when test="${ecommerceReportForm.startDate != null}">
				<h3 class="report">Report date range: On or after <bean:write name="ecommerceReportForm" property="startDate" format="dd/MM/yyyy"/></h3>
			</c:when>
			<c:when test="${ecommerceReportForm.endDate != null}">
				<h3 class="report">Report date range: On or before <bean:write name="ecommerceReportForm" property="endDate" format="dd/MM/yyyy"/></h3>
			</c:when>
		</c:choose>
		<div class="hr"></div>
		
		<logic:iterate name="ecommerceReportForm" property="details" id="order">
			<p>
			<table class="orderSummary" cellspacing="0" border="0">
				<colgroup>
					<col style="width:60%"></col>
					<col></col>
				</colgroup>
				<tr>
					<td><strong>Order Id:</strong>&nbsp; <bean:write name="order" property="purchaseId"/></td>
					<td><strong>PSP Id:</strong>&nbsp;<bean:write name="order" property="pspTransId" /></td>
				</tr>
				<tr>
					<td><strong>Date Placed:</strong>&nbsp;<bean:write name="order" property="datePlaced" format="dd/MM/yyyy"/></td>
					<td><strong>User:</strong>&nbsp;<bean:write name="order" property="user.displayName"/> <bean:write name="order" property="user.username"/></td>
				</tr>
			</table>
			</p>
			<table cellspacing="0" class="form" summary="Usage report">
					<tr>
						<th>Order Details:</th>
						<td>&nbsp;</td>
					</tr>	
					<logic:iterate name="order" property="assets" id="asset">
					<tr>
						<td><bean:write name="asset" property="description"/></td>
						<td><bean:write name="asset" property="price.displayAmount" filter="false" /></td>
					</tr>
					</logic:iterate>
					<tr>
						<th>Subtotal:</th>
						<td><bean:write name="order" property="subtotal.displayAmount" filter="false"/></td>
					</tr>
					<c:if test="${order.vatPercent.number > 0}">
					<tr>
						<th>VAT:</th>
						<td><bean:write name="order" property="vatPercent.displayNumber"/>%</td>
					</tr>
					</c:if>
					<c:if test="${order.discountPercentage > 0}">
					<tr>
						<th>Discount:</th>
						<td><bean:write name="order" property="discountPercentage"/>%</td>
					</tr>
					</c:if>
					<tr>
						<th>Total:</th>
						<td><bean:write name="order" property="total.displayAmount" filter="false"/></td>
					</tr>
			</table>
		<div class="hr"></div>
		</logic:iterate>
	</logic:notEmpty>
	<p><a href="../action/viewEcommerceReports?startDateString=<bean:write name='ecommerceReportForm' property='startDateString'/>&endDateString=<bean:write name='ecommerceReportForm' property='endDateString'/>&reportType=1">&laquo; Back to Reports</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>