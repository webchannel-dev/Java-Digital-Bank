<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Tamora James	05-Sep-2005		Created
	 d2		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
	 d3      Steve Bryan    23-May-2006    Added list of users who want permissions
	 d4     Matt Woollard	03-Apr-2009		Added additional user approval field
	 d5	    Matt Woollard   11-Jun-2009		Changes to additional approval
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="bSuppressActiveDirectoryLogo" settingName="suspend-ad-authentication" />

<%-- Rename the struts form --%>
<bean:define id="userForm" name="userApprovalForm" />


<bright:applicationSetting id="canRequestPermissionsUpdate" settingName="users-can-request-permission-update"/>
<bright:applicationSetting id="showIntendedUse" settingName="users-have-reg-message"/>
<bright:applicationSetting id="showOrganisation" settingName="users-have-organisation"/>
<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bright:applicationSetting id="remoteDirectoryIcon" settingName="remote-user-directory-icon" />
<bright:applicationSetting id="remoteDirectoryName" settingName="remote-user-directory-name" />
<bright:applicationSetting id="additionalUserApprovalEnabled" settingName="additional-user-approval-enabled" />
<bright:applicationSetting id="useOrgUnits" settingName="orgunit-use" />

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | User Approvals</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="approval"/>
	<bean:define id="helpsection" value="approve_users"/>
	<bean:define id="pagetitle" value="Approval"/>
	<bean:define id="tabId" value="manageApprovals"/>
</head>

<body id="adminPage">
   			
				<%@include file="../inc/body_start.jsp"%>
				
				<h1><bean:write name="pagetitle" /></h1> 
		
				<%@include file="inc_approval_tabs.jsp"%>
	
				<logic:empty name="userForm" property="users">
					
					<p>There are no users awaiting approval.</p>
			
				</logic:empty>
				<logic:notEmpty name="userForm" property="users">
				
					<p>The following users have registered to use  <bright:cmsWrite identifier="app-name" filter="false" />:</p>
			
					<table cellspacing="0" class="admin" summary="List of users">		
						<tr>
							<th></th>
							<th>
								<bright:cmsWrite identifier="label-name" filter="false"/>
							</th>
							<th>								
								<bright:cmsWrite identifier="label-user-register-date" filter="false"/>
							</th>
							<c:if test="${showOrganisation||useOrgUnits}">
							<th>
								<bright:cmsWrite identifier="label-organisation" filter="false"/>
							</th>
							</c:if>
							<logic:equal name="showIntendedUse" value="true">
							<th>
								<bright:cmsWrite identifier="label-user-reg-message" filter="false"/>
							</th>
							</logic:equal>
							<c:if test="${additionalUserApprovalEnabled}">
								<th>
									Initially approved by:
								</th>
							</c:if>
							<th>
								&nbsp;
							</th>
						</tr>
						<logic:iterate name="userForm" property="users" id="user">
							<tr>
								<td style="padding-right:0">
									<c:choose>
										<c:when test="${user.remoteUser}">
											<img src="..<bean:write name="remoteDirectoryIcon" />" border="0"  title="<bean:write name="remoteDirectoryName" /> User" alt="<bean:write name="remoteDirectoryName" /> User" style="margin-right: 8px"/>
										</c:when>
										<c:otherwise>
											<img src="../images/standard/icon/user_local.gif" alt="Local User" title="Local User" style="margin-right: 8px"/>
										</c:otherwise>
									</c:choose>						
								</td>
								<td>
									<bean:write name="user" property="fullName"/>
								</td>
								<td>
									<fmt:formatDate value="${user.registerDate}" pattern="${dateFormat}" />
								</td>
								<c:if test="${showOrganisation||useOrgUnits}">
								<td>
									<logic:empty name="user" property="organisation">-</logic:empty>
									<bean:write name="user" property="organisation"/>
								</td>
								</c:if>
								<logic:equal name="showIntendedUse" value="true">
								<td>
									<logic:empty name="user" property="registrationInfo">-</logic:empty>
									<span alt="<bean:write name='user' property='registrationInfo'/>" title="<bean:write name='user' property='registrationInfo'/>"><bright:writeWithTruncateTag name="user" property="registrationInfo" maxLength="50" endString="..." /></span>
								</td>
								</logic:equal>
								<c:if test="${additionalUserApprovalEnabled}">
									<td>
										<logic:notEmpty name="user" property="additionalApproverDetails">
											<bean:write name="user" property="additionalApproverDetails"/> 
										</logic:notEmpty>
									</td>
								</c:if>
								<td class="action">
									[<a href="viewApproveUser?id=<bean:write name='user' property='id'/>" title="View this users profile">view</a>]
								</td>														
							</tr>
						</logic:iterate>
					</table>
				</logic:notEmpty>
				<br/>

			<c:if test="${canRequestPermissionsUpdate}">
				
				<div class="hr"></div>
				
				<h2>Requests for Permissions Update</h2>
				
				<logic:empty name="userForm" property="usersRequiringUpdates">
					
					<p>There are no users awaiting update of permissions.</p>
			
				</logic:empty>
				<logic:notEmpty name="userForm" property="usersRequiringUpdates">
				
					<p>The following users have requested update of permissions:</p>
			
					<table cellspacing="0" class="admin" summary="List of users">		
						<tr>
							<th></th>
							<th>
								Name
							</th>
							<th>
								Username
							</th>
							<th>
								Email
							</th>
							<th>
								&nbsp;
							</th>
						</tr>
						<logic:iterate name="userForm" property="usersRequiringUpdates" id="user">
							<tr>
								<td>
									<c:choose>
										<c:when test="${user.remoteUser}">
											<img src="..<bean:write name="remoteDirectoryIcon" />" border="0"  title="<bean:write name="remoteDirectoryName" /> User" alt="<bean:write name="remoteDirectoryName" /> User" style="margin-right: 8px"/>
										</c:when>
										<c:otherwise>
											<img src="../images/standard/icon/user_local.gif" alt="Local User" title="Local User" style="margin-right: 8px"/>
										</c:otherwise>
									</c:choose>							
								</td>
								<td>
									<bean:write name="user" property="fullName"/>
								</td>
								<td>
									<bean:write name="user" property="username"/>
								</td>
								<td>
									<bean:write name="user" property="emailAddress"/>
								</td>
								<td class="action">
									[<a href="viewUpdateUserRequest?id=<bean:write name='user' property='id'/>" title="Update this users profile">view</a>]
								</td>														
							</tr>
						</logic:iterate>
					</table>
				</logic:notEmpty>
				<br />
			</c:if>
         
			<%@include file="../inc/body_end.jsp"%>

</body>
</html>