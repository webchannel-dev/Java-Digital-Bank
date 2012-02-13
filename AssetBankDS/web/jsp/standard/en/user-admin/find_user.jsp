<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		19-Jan-2004		Created.
	d2		Matt Stevenson	06-May-2005		Removed presentation, changes to reflect 
											new user and group implementation
	d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	d4		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
	d5		Martin Wilson	20-Aug-2007		Restructured search form and added 'is admin'
	d6      Matt Woollard   13-Nov-2007     Added message user option
	d7  	Matt Woollard   10-Jun-2009		Added ability to search for hidden/unapproved users
	d8	 	Matt Woollard	23-Sep-2009	    Added functionality to set a user expiry date
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="bSuppressActiveDirectoryLogo" settingName="suspend-ad-authentication" />
<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="remoteDirectoryIcon" settingName="remote-user-directory-icon" />
<bright:applicationSetting id="remoteDirectoryName" settingName="remote-user-directory-name" />
<bright:applicationSetting id="specifyRemoteUsername" settingName="specify-remote-username" />
<bright:applicationSetting id="forceRemoteAuthentication" settingName="force-remote-authentication"/>
<bright:applicationSetting id="usersHaveOrganisations" settingName="users-have-organisation"/>
<bright:applicationSetting id="orgUnitAllUsers" settingName="org-unit-admin-can-access-all-users"/>
<bright:applicationSetting id="loginAsUserEnabled" settingName="enable-login-as-user"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Find User</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="users"/>
	<bean:define id="pagetitle" value="Users"/>
	<bean:define id="tabId" value="manageUsers"/>
	<script type="text/JavaScript">
		// give the forename field the focus once the dom is ready
		$j(function () {
  			$j('#forename').focus();
		});
	</script>	
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../user-admin/inc_user_tabs.jsp"%>

	<html:form action="findUser" method="get">

		<bean:parameter id="orderId" name="orderId" value="1"/>
	<input type="hidden" id="orderId" name="orderId" value="<bean:write name='orderId'/>" />

	<logic:present  name="userForm">
		<logic:equal name="userForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="userForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											

	<h2>Find Existing Users</h2>
	<table cellspacing="0" class="shortform" style="margin-bottom:1.5em;" summary="Form for searching for users">
		<tr>
			<th><label for="forename"><bright:cmsWrite identifier="label-forename" /></label></th>
			<td>
				<input type="text" class="text" id="forename" name="searchCriteria.forename" maxlength="50" size="15" value="<bean:write name="userForm" property="searchCriteria.forename" />" />
			</td>
			<th><label for="surname"><bright:cmsWrite identifier="label-surname" filter="false"/></label></th>
			<td>
				<input type="text" class="text" id="surname" name="searchCriteria.surname" maxlength="50" size="15" value="<bean:write name="userForm" property="searchCriteria.surname" />" />
			</td>		
		</tr>
		<tr>
			<th><label for="username">Username:</label></th>
			<td>
				<input type="text" class="text" id="username" name="searchCriteria.username" maxlength="50" size="15" value="<bean:write name="userForm" property="searchCriteria.username" />" />			
			</td>
			<th><label for="email">Email:</label></th>
			<td>
				<input type="text" class="text" id="email" name="searchCriteria.emailAddress" maxlength="200" size="15" value="<bean:write name="userForm" property="searchCriteria.emailAddress" />" />			
			</td>	
		</tr>
		
		<tr>
			<th><c:if test="${usersHaveOrganisations}"><label for="organisation"><bright:cmsWrite identifier="label-organisation" filter="false"/></label></c:if>&nbsp;</th>
			<td>
				<c:if test="${usersHaveOrganisations}"><input type="text" class="text" id="organisation" name="searchCriteria.organisation" maxlength="50" size="15" value="<bean:write name="userForm" property="searchCriteria.organisation" />" /></c:if>&nbsp;		
			</td>
			<th><label for="group">Group:</label></th>
			<td>
				<bean:define name="userForm" property="groups" id="groups"/>
				<html:select name="userForm" property="searchCriteria.groupId" styleId="group">
					<option value="0">[any]</option>
					<logic:iterate name="groups" id="group">
						<!-- Do not show 'public' users group -->
						<c:if test="${group.id != 2}">
							<option value="<bean:write name='group' property='id'/>" <c:if test='${group.id==userForm.searchCriteria.groupId}'>selected="selected"</c:if>><bean:write name='group' property='nameWithOrgUnit'/>
						</c:if>
					</logic:iterate>	
				</html:select>
			</td>
		</tr>
		
		<tr>

			
			<logic:equal name="userprofile" property="isAdmin" value="true">
				<th>User status:</th>
				<td>
					<html:checkbox name="userForm" property="searchCriteria.hidden" styleClass="checkbox" styleId="inactive"/><label for="inactive">Hidden/inactive</label><br />
					
					<html:checkbox name="userForm" property="searchCriteria.notApproved" styleClass="checkbox" styleId="unapproved" /><label for="unapproved">Unapproved</label><br />
					
					<html:checkbox name="userForm" property="searchCriteria.expired" styleClass="checkbox" styleId="expired" /><label for="expired">Expired</label>
				</td>	
			</logic:equal>
			<logic:notEqual name="userprofile" property="isAdmin" value="true">
				<th></th><td></td>	
			</logic:notEqual>
			
			<logic:equal name="userprofile" property="isAdmin" value="true">
				<th>User type:</th>
				<td>
					<html:checkbox name="userForm" property="searchCriteria.isAdmin" styleClass="checkbox" styleId="isadmin"/><label for="isadmin">Admin user</label>
				</td>	
			</logic:equal>
			<logic:notEqual name="userprofile" property="isAdmin" value="true">
				<th></th><td></td>	
			</logic:notEqual>
		</tr>

	
		
		<tr>
			<th>&nbsp;</th><td><input type="submit" class="button flush" value="Find Users" style="margin-top:0.3em"/> </td>
			
		</tr>

	</table>	


	<c:if test="${userForm.emptyResult || not empty userForm.users}">
		<div id="tabContent">
			<h3>Users:</h3>
	</c:if>
	<bean:define id="noSearch" value="false"/>
	<logic:empty name="userForm" property="users">
		<logic:equal name="userForm" property="emptyResult" value="true">
			<p style="margin-bottom:0">There are no users in the system whose details match your criteria.</p>
			</div>
		</logic:equal>
		<logic:equal name="userForm" property="emptyResult" value="false">
			<bean:define id="noSearch" value="true"/>
			<div class="hr"></div>
		</logic:equal>
	</logic:empty>
	<logic:notEmpty name="userForm" property="users">
			<table cellspacing="0" class="admin" summary="List of users" >		
				<tr>
					<th style="padding-right:0"></th>
					<th>
						<c:choose>
							<c:when test="${orderId == 1}">
								<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=2" class="ascending" title="Sort by name">Name</a>
							</c:when>
							<c:when test="${orderId == 2}">
								<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=1" class="descending" title="Sort by name">Name</a>
							</c:when>
							<c:otherwise>
								<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/>&amp;searchCriteria.isAdmin=<bean:write name='userForm' property='searchCriteria.isAdmin'/>&amp;orderId=1" class="sort" title="Sort by name">Name</a>
							</c:otherwise>
						</c:choose>	
						
					</th>
					<th>
						<c:choose>
							<c:when test="${orderId == 3}">
								<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=4" class="ascending" title="Sort by username">Username</a>
							</c:when>
							<c:when test="${orderId == 4}">
								<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=3" class="descending" title="Sort by username">Username</a>
							</c:when>
							<c:otherwise>
								<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=3" class="sort" title="Sort by username">Username</a>
							</c:otherwise>
						</c:choose>	
						
					</th>
					<th>
					
						<c:choose>
							<c:when test="${orderId == 5}">
								<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=6" class="ascending" title="Sort by email address">Email</a>
							</c:when>
							<c:when test="${orderId == 6}">
								<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=5" class="descending" title="Sort by email address">Email</a>
							</c:when>
							<c:otherwise>
								<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=5" class="sort" title="Sort by email address">Email</a>
							</c:otherwise>
						</c:choose>	
						
						
					</th>
					<c:if test="${userForm.showExpiryDate}">
						<th>
							
							<c:choose>
								<c:when test="${orderId == 11}">
									<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=12" class="ascending" title="Sort by expiry date">Expiry date</a>
								</c:when>
								<c:when test="${orderId == 12}">
									<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=11" class="descending" title="Sort by expiry date">Expiry date</a>
								</c:when>
								<c:otherwise>
									<a href="../action/findUser?searchCriteria.forename=<bright:write name='userForm' property='searchCriteria.forename' encodeForUrl='true'/>&amp;searchCriteria.surname=<bright:write name='userForm' property='searchCriteria.surname' encodeForUrl='true'/>&amp;searchCriteria.username=<bright:write name='userForm' property='searchCriteria.username' encodeForUrl='true'/>&amp;searchCriteria.emailAddress=<bright:write name='userForm' property='searchCriteria.emailAddress' encodeForUrl='true'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;searchCriteria.hidden=<bean:write name='userForm' property='searchCriteria.hidden'/>&amp;searchCriteria.notApproved=<bean:write name='userForm' property='searchCriteria.notApproved'/>&amp;searchCriteria.expired=<bean:write name='userForm' property='searchCriteria.expired'/><c:if test="${userForm.searchCriteria.isAdmin}">&amp;searchCriteria.isAdmin=on</c:if>&amp;orderId=11" class="sort" title="Sort by expiry date">Expiry date</a>
								</c:otherwise>
							</c:choose>	
							
						</th>
						<th colspan="<c:choose><c:when test='${userprofile.isAdmin}'>5</c:when><c:otherwise>4</c:otherwise></c:choose>">
							&nbsp;
						</th>
					</c:if>
				</tr>
				<logic:iterate name="userForm" property="users" id="user">
					<tr>
						<td style="padding-right:0">
							<c:choose>
								<c:when test="${user.remoteUser}">
									<img src="..<bean:write name="remoteDirectoryIcon" filter="false"/>" border="0"  title="<bean:write name="remoteDirectoryName" filter="false"/> User" alt="<bean:write name="remoteDirectoryName" filter="false"/> User" style="margin-right: 8px"/>
								</c:when>
								<c:otherwise>
									<img src="../images/standard/icon/user_local.gif" alt="Local User" title="Local User" style="margin-right: 8px"/>
								</c:otherwise>
							</c:choose>		
						</td>
						<td>
							<bean:write name="user" property="surname" />,														
							<bean:write name="user" property="forename" />
						</td>
						<td>
							<div class="constrain">
								<bean:write name="user" property="username" />
							</div>	
						</td>
						<td>
							<div class="constrain">
								<bean:write name="user" property="emailAddress" />
							</div>
						</td>
						<c:if test="${userForm.showExpiryDate}">
							<td>
								<bean:write name="user" property="expiryDate" filter="false"/>
							</td>
						</c:if>
						<td class="action">
							[<a href="viewUpdateUser?id=<bean:write name='user' property='id'/>&amp;searchCriteria.forename=<bean:write name='userForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='userForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='userForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='userForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>" title="Edit this users profile">edit</a>]
						</td>
						<td class="action">
							<c:choose>
								<c:when test="${!user.remoteUser}">
									<c:if test="${userprofile.isAdmin || !orgUnitAllUsers}">[<a href="viewChangeUserPassword?id=<bean:write name='user' property='id'/>&amp;searchCriteria.forename=<bean:write name='userForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='userForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='userForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='userForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>" title="Change this users password">reset password</a>]</c:if>
								</c:when>
								<c:otherwise>
									-								
								</c:otherwise>
							</c:choose>
						</td>
						<td class="action">
								[<a href="viewMessageUsers?toUser=<bright:write name='user' property='username' encodeForUrl="true"/>" alt="Message user" title="Message this user" charset="UTF-8">message</a>]
						</td>
						<c:if test="${loginAsUserEnabled && userprofile.isAdmin}">
							<td class="action">
								<c:choose><c:when test="${userprofile.user.id != user.id}">[<a href="loginAsUser?id=<c:out value='${user.id}'/>">login as this user</a>]</c:when><c:otherwise>-</c:otherwise></c:choose>
							</td>
						</c:if>
						<td class="action">
							<c:choose>
							<c:when test="${user.id!=1}">
								<c:if test="${userprofile.isAdmin || !orgUnitAllUsers}">[<a href="deleteUser?id=<bean:write name='user' property='id'/>" alt="Delete user" onclick="return confirm('Are you sure you want to delete this user? \nALL related information for the user including usage records <c:if test="${ecommerce}" >and orders</c:if> will also be deleted.');" title="Delete this user">X</a>]</c:if>
							</c:when>
							<c:otherwise>
							<c:if test="${userprofile.isAdmin || !orgUnitAllUsers}">-</c:if>
							</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</logic:iterate>
			</table>
	
			<logic:equal name="userprofile" property="isAdmin" value="true">
				<a href="../action/exportUsers">Export these users &raquo;</a>
			</logic:equal>
	
		</div>
	</logic:notEmpty>
	

	<logic:equal name="userprofile" property="isAdmin" value="true">
		<h2>Add New Users</h2>
	</logic:equal>	
	<p><a href="../action/viewAddUser?searchCriteria.forename=<bean:write name='userForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='userForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='userForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='userForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='userForm' property='searchCriteria.groupId'/>&amp;noSearch=<bean:write name='noSearch'/>">Add a new user &raquo;</a></p>

	<logic:equal name="userprofile" property="isAdmin" value="true">
		<p>
			<a href ="../action/viewImportUsers">Import users &raquo;</a>
		</p>
	</logic:equal>

	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>