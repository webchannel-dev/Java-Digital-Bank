<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Matt Stevenson	09-May-2005		Removed presentation, changed group implementation
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Matt Stevenson	12-Jan-2006		Updated with validation
	 d5		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="useOrgUnits" settingName="orgunit-use" />


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | User Permissions</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="approval"/>
	<bean:define id="pagetitle" value="Request to Update Permissions"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>

	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present  name="userForm">
		<logic:equal name="userForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="userForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<html:form action="updateUserRequest" method="post">
		
		<%-- Set the request flag to false --%>
		<input type="hidden" name="user.requiresUpdate" value="false">			
	
		<c:set var="showAddressFields" value="false" />
		<%@include file="inc_user_fields_readonly.jsp"%>
	
		
		<div class="hr"></div>
	
		<h3>User Request</h3>
		<table cellspacing="0" class="form" summary="Form for user details">
		<c:if test="${useOrgUnits}">
			<tr>
				<th>Selected Org Unit:</th>
				<td class="padded">
					<bean:write name="userForm" property="requestedOrgUnit" />
					<logic:empty name="userForm" property="requestedOrgUnit">(none)</logic:empty>
				</td>
			</tr>			
		</c:if>
		
			<tr>
				<th>User message:</th>
				<td class="padded">
					<bean:write name="userForm" filter="false" property="user.registrationInfoAsHtml" />
				</td>
			</tr>
		</table>

		<div class="hr"></div>
	
		<h3>User Permissions</h3>
		<%@include file="inc_user_permissions.jsp"%>
		
		<div class="hr"></div>
	
		<h3>Your message</h3>
		<table cellspacing="0" class="form" summary="Form for user details">
			<tr>
				<th>Message:</th>
				<td class="padded">
					<html:textarea styleClass="text" name="userForm" property="message" />
				</td>
			</tr>
		</table>


		<p>
			An email will be sent to the user informing them their permissions have been updated, and including your message.
		</p>
											
		<div class="hr"></div>
		
		<div class="buttonHolder">
			<input type="submit" class="button" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		</div>
	</html:form>
	
	<form name="cancelForm" action="viewApproval" method="get">
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton">
	</form>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>