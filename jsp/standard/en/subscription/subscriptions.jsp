<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan	25-Jul-2006		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Your Subscription</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/calendar.js" type="text/javascript"></script>
	
	<bean:define id="section" value="subscription"/>
	<bean:define id="pagetitle" value="Your Subscription"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 


	<logic:present  name="subscriptionForm">
		<logic:equal name="subscriptionForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="subscriptionForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											

	<logic:empty name="subscriptionForm" property="subscriptions">
		<p>You do not currently have any active subscriptions.</p>
	</logic:empty>
		
	<logic:notEmpty name="subscriptionForm" property="subscriptions">
	<table cellspacing="0" class="admin" summary="List of subscriptions">
		<tr>
			<th>Start Date</th>
			<th>Model</th>
			<th>Price</th>
			<th>Num Downloads</th>
			<th>Duration</th>
			<th>Status</th>
		</tr>
		
		<logic:iterate name="subscriptionForm" property="subscriptions" id="sub">
			<c:choose>
				<c:when test="${sub.active || !sub.started}"><c:set var="class" value="" /></c:when>
				<c:otherwise><c:set var="class" value="disabled" /></c:otherwise>
			</c:choose>
			<tr class="<c:out value='${class}' />">
				<td><bean:write name="sub" property="startDate.displayDate"/></td>
				<td><bean:write name="sub" property="model.description"/></td>
				<td>
					<c:choose>
						<c:when test="${sub.pricePaid.amount gt 0}">
							<bean:write name="sub" property="pricePaid.displayAmount" filter="false" />
						</c:when>
						<c:otherwise>
							<bean:write name="sub" property="model.price.displayAmount" filter="false" />
						</c:otherwise>
					</c:choose>
				</td>
				<td><bean:write name="sub" property="model.noOfDownloads.displayNumber"/></td>
				<td><bean:write name="sub" property="model.duration.displayNumber"/></td>
				<td>
					<c:choose>
						<c:when test="${sub.active}">Active</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${!sub.started}">
									Not started
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${sub.expired}">Expired</c:when>
										<c:otherwise>Not Active</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</logic:iterate>
	</table>	
	</logic:notEmpty>	

	<div class="hr"></div>
	
	<p><a href="viewSubscription">Subscribe now &raquo;</a></p>

	<br />
	<div class="hr"></div>

	<h2>Downloads</h2>
	<p>
		You have <strong><bean:write name="userprofile" property="downloadsLeft" /></strong> downloads left today.	
	</p>
	<div class="hr"></div>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>