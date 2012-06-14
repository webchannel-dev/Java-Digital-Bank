<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Matt Stevenson	09-May-2005		Removed presentation, changed group implementation
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Matt Stevenson	12-Jan-2006		Updated with validation
	 d5		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
	 d6		Matt Woollard	18-Oct-2007		Added option to email user about changes
	 d7     Matt Woollard   13-Nov-2007     Added last modified by
	 d8     Matt Woollard   13-Nov-2007     Delete and suspend checkboxes added
	 d9		Matt Woollard	03-Apr-2009		Added additional user approval enabled
	 d10	Matt Woollard   10-Jun-2009		Added ability to search for hidden/unapproved users
	 d11	Matt Woollard   11-Jun-2009		Changes to additional approval
	 d12 	Matt Woollard	23-Sep-2009	    Added functionality to set a user expiry date
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="usernameIsEmailaddress" settingName="username-is-emailaddress"/>
<bright:applicationSetting id="noActiveDirectory" settingName="suspend-ad-authentication" />
<bright:applicationSetting id="specifyRemoteUsername" settingName="specify-remote-username" />
<bright:applicationSetting id="directoryName" settingName="remote-user-directory-name" />
<bright:applicationSetting id="userInvitations" settingName="user-invitations-enabled" />
<bright:applicationSetting id="additionalUserApprovalEnabled" settingName="additional-user-approval-enabled" />
<bright:applicationSetting id="orgUnitAllUsers" settingName="org-unit-admin-can-access-all-users"/>
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />

<bean:parameter name="cancelAction" id="cancelAction" value=""/>
<bean:parameter name="saveAction" id="saveAction" value=""/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Edit User</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="users"/>
	<bean:define id="pagetitle" value="Edit User"/>
	
	
	<script type="text/javascript">
		<!--  
	  function setEmailSelection(emailChk)
	  {
	  	if(emailChk.checked)
	  	{
	  		document.getElementById('messageRow').style.display='inline';
	  	}
	  	else 
	  	{
	  		document.getElementById('messageRow').style.display='none';
	  	}
	  }
	  
	  function deleteUserWarning(form)
	  {
	  	if(document.getElementById('deleted').checked)
	  	{
	  		return confirm('Are you sure you want to delete this user?');
	  	}
	  	else 
	  	{
			return true;
	  	}
	  }

		// when the dom is ready
		$j(function () {
			$j('#username').focus(); 	//give the current username field the focus
			initDatePicker();
		});	
	
	  //-->
	</script>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>

	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:notEmpty name="userForm" property="lastUpdatedBy">
		<p>Last modified by: <bean:write name="userForm" property="lastUpdatedBy.username"/></p>
	</logic:notEmpty>
	
	<c:if test="${userInvitations}">
		<logic:notEmpty name="userForm" property="invitedByUser">
			<p>Invited by: <bean:write name="userForm" property="invitedByUser.username"/></p>
		</logic:notEmpty>
	</c:if>
	
	<c:if test="${additionalUserApprovalEnabled}">
		<logic:notEmpty name="userForm" property="user.additionalApproverDetails">
			<p>Initially approved by: <bean:write name="userForm" property="user.additionalApproverDetails"/> </p>
		</logic:notEmpty>
	</c:if>
	
	
	<logic:present  name="userForm">
		<logic:equal name="userForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="userForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>		
	
	<c:if test="${usernameIsEmailaddress}">
		<p>Note: normally email address is used for the username, so the fields should match.</p>
	</c:if>									
	
	<logic:notEmpty name="saveAction">
		<form name="saveForm" action="<bean:write name='saveAction'/>" method="post">
			<input type="hidden" name="saveAction" value="<bean:write name='saveAction'/>"/>
			<input type="hidden" name="cancelAction" value="<bean:write name='cancelAction'/>"/>
	</logic:notEmpty>
	<logic:empty name="saveAction">
		<form name="saveForm" action="updateUser" method="post" onSubmit="return deleteUserWarning(this);">
	</logic:empty>

		<html:hidden name="userForm" property="user.id"/>
	
		<html:hidden name="userForm" property="user.remoteUser"/>
		<html:hidden name="userForm" property="user.requiresUpdate" />
		<html:hidden name="userForm" property="user.requestedOrgUnitId" />
		<html:hidden name="userForm" property="user.notApproved" />
	
		<input type="hidden" name="searchCriteria.forename" value="<bean:write name="userForm" property="searchCriteria.forename" filter="false"/>">
		<input type="hidden" name="searchCriteria.surname" value="<bean:write name="userForm" property="searchCriteria.surname" filter="false"/>"/>
		<input type="hidden" name="searchCriteria.username" value="<bean:write name="userForm" property="searchCriteria.username" filter="false"/>"/>
		<input type="hidden" name="searchCriteria.emailAddress" value="<bean:write name="userForm" property="searchCriteria.emailAddress" filter="false"/>"/>
		<html:hidden name="userForm" property="searchCriteria.groupId"/>
		<input type="hidden" name="oldUsername" value="<bean:write name="userForm" property="oldUsername" filter="false"/>"/>
	
		<%-- Add id as hidden field in case there is an error and we have to come back to the form --%>
		<input type="hidden" name="id" value="<c:out value='${userForm.user.id}' />" /> 
	
		<table cellspacing="0" class="form" summary="Form for user details">
			<c:if test="${!noActiveDirectory}">
				<tr>
					<th style="padding-top: 10px;">Authentication:</th>
					<td style="padding-top: 10px;" colspan="2">
						<logic:equal name="userForm" property="user.remoteUser" value="true">
							<bean:write name="directoryName" filter="false"/>
						</logic:equal>
						<logic:equal name="userForm" property="user.remoteUser" value="false">
							Local (not <bean:write name="directoryName" filter="false"/>)
						</logic:equal>
					</td>
				</tr>	
			</c:if>
			<input type="hidden" name="mandatory_user.username" value="Please enter a username." />
			<tr>
				<c:choose>
					<c:when test="${!userForm.user.remoteUser && (userprofile.isAdmin || !orgUnitAllUsers)}">	
						<th><label for="username">Username:<span class="required">*</span></label></th>		
						<td>
							<input type="text" class="text" name="user.username" maxlength="100" size="16" value="<bean:write name="userForm" property="user.username" filter="false"/>"/>			
						</td>
					</c:when>
					<c:otherwise>
						<th><label for="forename">Username:</label></th>
						<td class="padded">
							<html:hidden name="userForm" property="user.username" />
							<bean:write name="userForm" property="user.username" />
						</td>
					</c:otherwise>
				</c:choose>	
			</tr>
				
			<html:hidden name="userForm" property="user.displayName"/>
			<c:if test="${userForm.user.remoteUser && not empty user.displayName}">
				<tr>
					<th><label>AD Full Name:</label></th>
					<td class="padded">
						<bean:write name="userForm" property="user.displayName" filter="false" />
					</td>
				</tr>	
			</c:if>
			
			<c:if test="${specifyRemoteUsername}">
				<tr>
					<th><label>Remote Username:</label></th>
					<td class="padded">
						<html:text styleClass="text" name="userForm" property="user.remoteUsername"/>
					</td>
				</tr>	
			</c:if>

			<%@include file="inc_user_fields.jsp"%>
			
			<tr>
				<th><label for="expiry"><bright:cmsWrite identifier="label-user-expiry-date" filter="false"/></label></th>
				<td colspan="2">
					<input type="text" class="text date small" id="expiry" name="expiryDate" size="20" value="<bean:write name="userForm" property="expiryDate" filter="false"/>"/>	
					<span class="inline">(<c:out value="${dateFormatHelpString}" />)</span>				
					Leave empty for never
				</td>													
			</tr>			
			
			<tr>
				<th style="vertical-align: top"><label id="adminNotes">Admin Notes</label>:</th>
				<td><html:textarea name="userForm" property="user.adminNotes" rows="5" cols="40"></html:textarea></td>
			</tr>
					
		</table>
		
		
	
		<%@include file="inc_user_permissions.jsp"%>
		
		<table cellspacing="0" class="form" summary="Form for user permissions">
			<tr>
				<th>Suspend User?:</th>
				<td>
					<html:checkbox name="userForm" property="user.isSuspended" styleClass="checkbox" />
				</td>
				<td class="padded">
					*The user will not be able to login until the suspension has been removed.
				</td>
			</tr>
			<tr>
				<th>Hide User?:</th>
				<td>
					<html:checkbox name="userForm" property="user.hidden" styleClass="checkbox" />
				</td>
				<td class="padded">
					*The user will not be able to login and they will not be displayed in the user list.
				</td>
			</tr>			
			<c:if test="${userprofile.isAdmin || !orgUnitAllUsers}">	
			<tr>
				<th>Delete user?:</th>
				<td>
					<html:checkbox name="userForm" styleId="deleted" property="user.isDeleted" styleClass="checkbox"/>
				</td>
				<td class="padded">
					*Warning! This will delete all user details. Tick the 'Email user' checkbox below to send an email on deletion.
				</td>
			</tr>
			</c:if>
		</table>
		
		<table cellspacing="0" class="form" summary="email user" style="margin:0;">
			<tr>
				<th><label for="email">Email user?:</label></th>
				<td>
					<html:checkbox style="width: auto;" styleId="email" styleClass="checkbox" name="userForm" property="notifyUser" onclick="setEmailSelection(this);"/>
				</td>					
			</tr>	
		</table>
		<table class="form" cellspacing="0" style="margin:0;"  id="messageRow">		
			<tr>
				<th style="vertical-align: top"><label id="message">Optional Additional Message</label>:</th>
				<td><textarea name="message" rows="5" cols="40"><bean:write name="userForm" property="message" filter="false"/></textarea></td>
			</tr>	
		</table>	
		<div class="hr"></div>


		<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 

		<logic:notEmpty name="cancelAction">
			<a href="viewFindUser" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		</logic:notEmpty>
		<logic:empty name="cancelAction">
			<a href="findUser?searchCriteria.forename=<bean:write name='userForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='userForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='userForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='userForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			
		</logic:empty>			
	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>