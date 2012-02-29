<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Woollard		05-Jun-2008		Created
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
		<a href="../action/viewEcommerceReports">&laquo; Back to Reports</a>
	</div>
	
	<h2 class="report">Subscriptions report</h2>

	<logic:empty name="ecommerceReportForm" property="details">
		<p>There have been no subscriptions taken out between the specified dates.</p>
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
		
		
		<table cellspacing="0" class="report permissions" summary="Usage report">		
		<tr>
			<th>
				Subscription
			</th>
			<th>
				Subscribers
			</th>
			<th>
				Total earned
			</th>
			<th>
				Start date
			</th>
			<th>
				Email
			</th>
			<th>
				Active
			</th>
			<th>
				Price paid
			</th>
		</tr>
		<logic:iterate name="ecommerceReportForm" property="details" id="order">
			<tr class="downloadbreakdown categoryRow">
				<td>
					<bean:write name="order" property="subscriptionName"/>
				</td>
				<td>
					<bean:write name="order" property="numSubscribers"/>
				</td>
				<td>
					<bean:write name="order" property="totalCost.displayAmount" filter="false"/>
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			
			<logic:notEmpty name="order" property="userSubscriptionReports">
				<logic:iterate name="order" property="userSubscriptionReports" id="userReport">
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>
							<bean:write name="userReport" property="startDate" />
						</td>
						<td>
							<bean:write name="userReport" property="email" />
						</td>
						<td>
							<c:choose>
								<c:when test="${userReport.active}">
									Yes
								</c:when>
								<c:otherwise>
									No
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<bean:write name="userReport" property="pricePaid.displayAmount" filter="false"/>
						</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</logic:iterate>
	</table>
		
			
			
		<div class="hr"></div>
	</logic:notEmpty>
	<p><a href="../action/viewEcommerceReports" class="bold">&laquo; Back to Reports</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>