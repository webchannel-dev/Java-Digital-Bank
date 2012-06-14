<%@include file="../inc/doctype_html.jsp" %>
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
<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>


<head>
	<bean:define id="section" value="purchases"/>
	<title><bright:cmsWrite identifier="e-title-order-overview" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="tabId" value="myOrders"/>
	<script type="text/JavaScript">
		$j(function () {
			initDatePicker();
		});
	</script>	
</head>


<body>
	<%@include file="../inc/body_start.jsp"%>

	<h1><bright:cmsWrite identifier="e-subhead-purchases" filter="false"/></h1> 
	
	<%@include file="../ecommerce/inc_my_purchase_tabs.jsp"%>
	
	<p>
		<h3 class="report"><bright:cmsWrite identifier="e-search-orders" filter="false"/></h3>
		<logic:present name="orderForm"> 
			<logic:equal name="orderForm" property="hasErrors" value="true"> 
				<div class="error">
					<logic:iterate name="orderForm" property="errors" id="errorText">
						<bright:writeError name="errorText" /><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>
		<html:form action="viewUserOrderOverview" method="get">
			<c:choose>
			<c:when test="${showCommercialOptions}">
				<input type="hidden" name="orderWorkflow" value="0" />
			</c:when>
			<c:otherwise>	
				<input type="hidden" name="orderWorkflow" value="1" />
			</c:otherwise>
		</c:choose> 
		<table class="form" cellspacing="0" cellpadding="0" >
			<tr>
				<th style="padding-top: 8px;">
					<label for="placed_between_field"><bright:cmsWrite identifier="e-label-placed-between" filter="false"/></label>
				</th>
				<td style="padding-top: 8px;">
					<html:text styleClass="small text date" name="orderForm" property="startDateString" size="20"/> 

					<bright:cmsWrite identifier="e-and" filter="false"/>
					<html:text styleClass="small text date" name="orderForm" property="endDateString" size="20"/> 

					
				</td>
			</tr>
		</table>
		<input type="submit" name="submitOrderSearch" class="button flush" value="<bright:cmsWrite identifier="button-search" filter="false" />" />	
		
		</html:form>
	</p>	
	<div class="hr"></div>	
		
	<logic:empty name="orderForm" property="orderList">
		<p><bright:cmsWrite identifier="e-no-orders-found" filter="false"/></p>
	</logic:empty>
	<logic:notEmpty name="orderForm" property="orderList">
		<p>
			<table class="admin" cellspacing="0" border="0">
				<tr>
					<th><bright:cmsWrite identifier="e-order-id" filter="false"/></th>
					<th><bright:cmsWrite identifier="e-date-placed" filter="false"/></th>
					<th><bright:cmsWrite identifier="e-date-fulfilled" filter="false"/></th>
					<th><bright:cmsWrite identifier="e-order-status" filter="false"/></th>
					<th><bright:cmsWrite identifier="e-total-cost" filter="false"/></th>
					<th colspan="1">&nbsp;</th>
				</tr>
				<logic:iterate name="orderForm" property="orderList" id="order">
					<tr>
						<td><bean:write name="order" property="displayPurchaseId"/></td>
						<td><fmt:formatDate value="${order.datePlaced}" pattern="${dateFormat}" /></td>
						<td>
							<c:choose>
								<c:when test="${order.hasPrints && order.status.id==1}">
									<fmt:formatDate value="${order.dateFulfilled}" pattern="${dateFormat}" />
								</c:when>
								<c:when test="${order.hasPrints && order.status.id==2}">
									<bright:cmsWrite identifier="e-awaiting" filter="false"/>
								</c:when>
								<c:otherwise>
									<bright:cmsWrite identifier="e-not-applicable" filter="false"/>
								</c:otherwise>			
							</c:choose>
						</td>
						<td><bean:write name="order" property="status.name"/></td>
						<td><bright:writeMoney name="order" property="total.displayAmount" /></td>
						<td class="action">
							[<a href="viewUserOrderDetails?orderId=<bean:write name='order' property='id'/>"><bright:cmsWrite identifier="e-view" filter="false"/></a>]
						</td>
					</tr>
				</logic:iterate>						
			</table>
		</p>
	</logic:notEmpty>
																													
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>