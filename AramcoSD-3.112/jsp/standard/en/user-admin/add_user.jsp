<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Matt Stevenson	06-May-2005		Modified
	 d3		Matt Stevenson	10-May-2005		Group selection changes
	 d4		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d5		Matt Stevenson	12-Jan-2006		Updated with validation
	 d6		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
	 d7	 	Matt Woollard	23-Sep-2009	    Added functionality to set a user expiry date
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="usernameIsEmailaddress" settingName="username-is-emailaddress"/>
<bright:applicationSetting id="specifyRemoteUsername" settingName="specify-remote-username"/>
<bright:applicationSetting id="forceRemoteAuthentication" settingName="force-remote-authentication"/>
<bright:applicationSetting id="showAlertForNewUserNotification" settingName="show-alert-for-new-user-notification"/>
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />

<bean:parameter id="noSearch" name="noSearch"/> 


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Add User</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="users"/>
	<bean:define id="helpsection" value="adding_users"/>
	<bean:define id="pagetitle" value="Add User"/>
	
	
	
	<%--  Javascript for confirmation about sending user notification --%>	
	<script type="text/javascript">
	
		<c:choose>
			<c:when test="${showAlertForNewUserNotification}">
			
				function doConfirmation()
				{
						
					var bSendNotification = document.getElementById('notifyUser').checked;
					
					if (bSendNotification)
					{		
						sConfirmText="<bright:cmsWrite identifier="js-confirm-add-user-with-notification" filter="false"/>";
					}
					else
					{
						sConfirmText="<bright:cmsWrite identifier="js-confirm-add-user-without-notification" filter="false"/>";
					}
								
					return confirm(sConfirmText);
				}
					
			</c:when>
			<c:otherwise>
			
				function doConfirmation()
				{
					return true;
				}
		
			</c:otherwise>
		</c:choose>

		// when the dom is ready
		$j(function () {
  			$j('#username').focus(); 	//give the current username field the focus
			initDatePicker();
		});
	
	</script>
	
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
	
	<c:if test="${usernameIsEmailaddress}">
		<p>Note: normally email address is used for the username, so the fields should match.</p>
	</c:if>									

	<p>If you leave the password fields blank a password will be generated automatically and the new user will be notified.</p>

	<html:form action="addUser" method="post" onsubmit="return doConfirmation();">
		<input type="hidden" name="noSearch" value="<bean:write name='noSearch'/>" />

		<html:hidden name="userForm" property="user.id"/>	
		<html:hidden name="userForm" property="searchCriteria.forename"/>
		<html:hidden name="userForm" property="searchCriteria.surname"/>
		<html:hidden name="userForm" property="searchCriteria.username"/>
		<html:hidden name="userForm" property="searchCriteria.emailAddress"/>
		<html:hidden name="userForm" property="searchCriteria.groupId"/>
		<input type="hidden" name="mandatory_user.username" value="Please enter a username." />
				
		<table cellspacing="0" class="form" style="width: auto;" summary="Form for user details">

			
			<tr>
				<th><label for="username">Username:<span class="required">*</span></label></th>
				<td colspan="2">
					<input type="text" class="text" name="user.username" id="username" maxlength="100" size="25" value="<bean:write name="userForm" property="user.username" filter="false"/>"/>
				</td>
			</tr>
			
			<c:if test="${specifyRemoteUsername}">
				<tr>
					<th><label for="remoteUsername">Remote Username:</label></th>
					<td class="padded">
						<html:text styleClass="text" name="userForm" property="user.remoteUsername" styleId="remoteUsername" />
					</td>
				</tr>	
			</c:if>
			
			<c:if test="${!forceRemoteAuthentication}">
				<tr>
					<th><label for="userPassword">Password:<c:if test="${!specifyRemoteUsername}"></c:if></label></th>
					<td colspan="2">
						<html:password styleClass="text" name="userForm" property="user.password" maxlength="100" size="25" styleId="userPassword" />
					</td>													
				</tr>
				<tr>
					<th><label for="confirmPassword">Repeat password:<c:if test="${!specifyRemoteUsername}"></c:if></label></th>
					<td colspan="2">
						<html:password styleClass="text" name="userForm" property="confirmPassword" maxlength="100" size="25" styleId="confirmPassword" />
					</td>													
				</tr>
			</c:if>

			
			<%@include file="inc_user_fields.jsp"%>
				
		
			
			
			<tr>
				<th><label for="expiry"><bright:cmsWrite identifier="label-user-expiry-date" filter="false"/></label></th>
				<td colspan="2">
					<input type="text" class="text date small" id="expiry" name="expiryDate" size="20" value="<bean:write name="userForm" property="expiryDate" filter="false"/>"/>	
					<span class="inline">(<c:out value="${dateFormatHelpString}" />) - Leave empty for never</span>			
					
				</td>													
			</tr>
			
			<tr>
				<th><label for="notifyUser">Send notification to user?</label></th>
				<td colspan="2" style="text-align:left;">
					<html:checkbox style="width: auto;" name="userForm" property="notifyUser" styleId="notifyUser" />
				</td>
			</tr>
		</table>

		<div class="hr"></div>
		
		<%@include file="inc_user_permissions.jsp"%>
		
		<div class="hr"></div>

		
		<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<logic:equal name="noSearch" value="true">
			<a href="viewFindUser" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		</logic:equal>
		<logic:equal name="noSearch" value="false">
			<a href="findUser?searchCriteria.forename=<bean:write name='userForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='userForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='userForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='userForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			
		</logic:equal>
		
	</html:form>
	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>