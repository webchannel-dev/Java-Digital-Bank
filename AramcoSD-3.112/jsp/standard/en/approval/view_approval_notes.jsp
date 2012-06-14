<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1    Steve Bryan		13-Sep-2005		Created.
	d2		Tamora James	03-Oct-2005		Added to demo.
	d3		Ben Browning	22-Feb-2006		HTML/CSS tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="commercial-options" settingName="commercial-options"/>
<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />


<c:choose>
	<c:when test="${ecommerce}">
		<c:set var="sSubmittedText" value="You purchased this item on" />	
		<c:set var="sApprovalDateText" value="Purchase was approved on" />	
		<c:set var="sRejectionDateText" value="Purchase was rejected on" />	
		<c:set var="sExpiryDateText" value="You can download this item until" />				
	</c:when>
	<c:otherwise>
		<c:set var="sSubmittedText"><bright:cmsWrite identifier="snippet-made-request-on" filter="false"/></c:set>	
		<c:set var="sApprovalDateText"><bright:cmsWrite identifier="snippet-request-approved-on" filter="false"/></c:set>
		<c:set var="sRejectionDateText"><bright:cmsWrite identifier="snippet-request-rejected-on" filter="false"/></c:set>
		<c:set var="sExpiryDateText"><bright:cmsWrite identifier="snippet-approval-expires-on" filter="false"/></c:set>			
	</c:otherwise>
</c:choose>



<head>
	
	<title><bright:cmsWrite identifier="title-approval-notes" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>
</head>

<body id="approvalPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<c:choose>
		<c:when test="${ecommerce}">
			<h1 class="underline"><bright:cmsWrite identifier="heading-purchase-notes" filter="false" /></h1> 
		</c:when>
		<c:otherwise>
			<h1 class="underline"><bright:cmsWrite identifier="heading-approval-notes" filter="false" /></h1>
		</c:otherwise>
	</c:choose>
	
			<logic:notEmpty name="approvalNotesForm" property="assetApproval.dateSubmitted.date">
				<logic:notEmpty name="sSubmittedText">
					<p><bean:write name="sSubmittedText" /> <bean:write name="approvalNotesForm" property="assetApproval.dateSubmitted.displayDate" />.</p>
				</logic:notEmpty>
			</logic:notEmpty>
		
		<logic:equal name="approvalNotesForm" property="assetApproval.approvalStatus.id" value="2">
			<logic:notEmpty name="approvalNotesForm" property="assetApproval.dateApproved.date">
				<logic:notEmpty name="sApprovalDateText">
					<p><bean:write name="sApprovalDateText" /> <bean:write name="approvalNotesForm" property="assetApproval.dateApproved.displayDate" />.</p>
				</logic:notEmpty>
			</logic:notEmpty>

			<logic:notEmpty name="approvalNotesForm" property="assetApproval.dateExpires.date">
				<logic:notEmpty name="sExpiryDateText">
					<p><bean:write name="sExpiryDateText" /> <strong><bean:write name="approvalNotesForm" property="assetApproval.dateExpires.displayDate" /></strong>,
						<bright:cmsWrite identifier="snippet-ensure-access-item" filter="false"/></p>
				</logic:notEmpty>
			</logic:notEmpty>					
		</logic:equal>

		<logic:equal name="approvalNotesForm" property="assetApproval.approvalStatus.id" value="3">
			<logic:notEmpty name="approvalNotesForm" property="assetApproval.dateApproved.date">
				<logic:notEmpty name="sRejectionDateText">
					<p><bean:write name="sRejectionDateText" /> <bean:write name="approvalNotesForm" property="assetApproval.dateApproved.displayDate" />.</p>
				</logic:notEmpty>
			</logic:notEmpty>
		</logic:equal>


	<div class="hr"></div>
		
	<logic:notEmpty name="approvalNotesForm" property="assetApproval.userNotes">
		<p>
			<bright:cmsWrite identifier="snippet-your-notes" filter="false"/><br/ ><br/ >
			<em><bean:write name="approvalNotesForm" property="assetApproval.userNotes" /></em>
		</p>
		<div class="hr"></div>
	</logic:notEmpty>
	
	
	<logic:notEmpty name="approvalNotesForm" property="assetApproval.adminNotes">
		<p>
			<bright:cmsWrite identifier="snippet-admin-notes" filter="false"/><br/ ><br/ >
			<em><bean:write name="approvalNotesForm" property="assetApproval.adminNotes" /></em>
		</p>
		<div class="hr"></div>
	</logic:notEmpty>
	
	<logic:equal name="commercial-options" value="true">
		<logic:notEmpty name="approvalNotesForm" property="orderList">
			<p>
				<bright:cmsWrite identifier="e-purchased-commercial-options" filter="false" replaceVariables="true" /><br/ ><br/ >
				<table class="admin" cellspacing="0" border="0">
					<tr>
						<th><bright:cmsWrite identifier="e-order-id" filter="false" replaceVariables="true" /></th>
						<th><bright:cmsWrite identifier="e-date-placed" filter="false" replaceVariables="true" /></th>
						<th><bright:cmsWrite identifier="e-total-cost" filter="false" replaceVariables="true" /></th>
						<th colspan="1">&nbsp;</th>
					</tr>
					<logic:iterate name="approvalNotesForm" property="orderList" id="order">
						<tr>
							<td><bean:write name="order" property="displayPurchaseId"/></td>
							<td><fmt:formatDate value="${order.datePlaced}" pattern="${dateFormat}" /></td>
							<td><bean:write name="order" property="total.displayAmount" filter="false"/></td>
							<td class="action">
								[<a href="viewUserOrderDetails?orderId=<bean:write name='order' property='id'/>"><bright:cmsWrite identifier="e-view" filter="false" replaceVariables="true" /></a>]
							</td>
						</tr>
					</logic:iterate>						
				</table>
			</p>
			<div class="hr"></div>
		</logic:notEmpty>
	</logic:equal>
		
	<p>
		<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-lightbox" filter="false" /></a>
	</p>
				
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>