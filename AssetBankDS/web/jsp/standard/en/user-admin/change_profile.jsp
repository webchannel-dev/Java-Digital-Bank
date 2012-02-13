<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		05-Oct-2005		Created.
	 d2		Ben Browning		21-Feb-2006		HTML/CSS Tidy up
	 d3     Matt Woollard       13-Nov-2007     Added last modified by field
	 d4     Matt Woollard       03-Apr-2009		Added additional approval field
	 d5	    Matt Woollard       11-Jun-2009		Changes to additional approval
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="canRequestPermissionsUpdate" settingName="users-can-request-permission-update"/>
<bright:applicationSetting id="usernameIsEmailaddress" settingName="username-is-emailaddress"/>
<bright:applicationSetting id="marketingGroupsEnabled" settingName="marketing-groups-enabled"/>
<bright:applicationSetting id="userInvitations" settingName="user-invitations-enabled" />
<bright:applicationSetting id="additionalUserApprovalEnabled" settingName="additional-user-approval-enabled" />

<bean:define id="userForm" name="changeProfileForm" />

<%-- Check for parameter indicating we should turn on strict validation --%>
<bean:parameter id="strict" name="strict" value="" />
<c:if test="${not empty strict && strict == 'true'}"><c:set var="strictValidation" value="true" /></c:if>

<head>
	
	<title><bright:cmsWrite identifier="title-your-profile" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="profile"/>
	<bean:define id="tabId" value="profile"/>
	<script type="text/JavaScript">
		// give the forename field the focus once the dom is ready
		$j(function () {
  			$j('#forename').focus();
		});
	</script>	
</head>

<body id="profilePage">

	<%@include file="../inc/body_start.jsp"%>

	<h1><bright:cmsWrite identifier="heading-your-profile" filter="false" /></h1> 	 
	<%@include file="../user-admin/inc_user_profile_tabs.jsp"%>

	<bright:cmsWrite identifier="intro-change-profile" filter="false" />
	
	<p>
	<c:if test="${!changeProfileForm.user.remoteUser}">
		 <bright:cmsWrite identifier="profile-change-password" filter="false"/>
	</c:if>
	
	<c:if test="${canRequestPermissionsUpdate}">
		<c:if test="${!changeProfileForm.user.remoteUser}">
			or
		</c:if>
		<c:if test="${changeProfileForm.user.remoteUser}">
			You can also
		</c:if>

		<a href="viewRequestUserUpdate">request more permissions</a>.
	</c:if>
	</p>
	<logic:present name="changeProfileForm">
		<logic:equal name="changeProfileForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="changeProfileForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<c:if test="${usernameIsEmailaddress}">
		<bright:cmsWrite identifier="email-used-for-username" filter="false"/>
	</c:if>	
	
	<c:if test="${userForm.lastUpdatedBy.id gt 0 }">
		<p><bright:cmsWrite identifier="profile-modified-by" filter="false"/><bean:write name="userForm" property="lastUpdatedBy.username"/></p>
	</c:if>
	
	<c:if test="${userInvitations}">
		<c:if test="${userForm.invitedByUser.id gt 0 }">
			<p>Invited by: <bean:write name="userForm" property="invitedByUser.username"/></p>
		</c:if>
	</c:if>
	
	<c:if test="${additionalUserApprovalEnabled}">
		<logic:notEmpty name="userForm" property="user.additionalApproverDetails">
			<p>Initially approved by: <bean:write name="userForm" property="user.additionalApproverDetails"/> </p>
		</logic:notEmpty>
	</c:if>

	<html:form action="/changeProfile" method="post">
		<html:hidden name="userForm" property="user.id"/>
		<html:hidden name="userForm" property="user.remoteUser"/>
		<html:hidden name="userForm" property="user.lastModifiedBy"/>
		
		<c:if test="${not empty strict}">
			<input type="hidden" name="strict" value="<c:out value='${strict}' />" />
		</c:if>
		
		<c:if test="${marketingGroupsEnabled}">
			<bean:define id="reloadOnChangeLanguage" value="true"/>
		</c:if>
		
		<table class="form" cellpadding="0" cellspacing="0" summary="Form for changing your user profile">
			
			<%@include file="inc_user_fields.jsp"%>
			
			<c:if test="${marketingGroupsEnabled}">
				<logic:notEmpty name="changeProfileForm" property="marketingGroups">
					<bean:define id="marketingGroupForm" name="changeProfileForm"/>
					<%@include file="../inc/inc_marketing_group_subscription.jsp"%>
				</logic:notEmpty>
			</c:if>
			
			<tr>
				<th>&nbsp;</th>
				<td>
					<input type="submit" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
				</td>
			</tr>
		</table>
	</html:form>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>