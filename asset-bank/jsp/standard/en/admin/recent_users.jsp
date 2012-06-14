<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		14-Sep-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bright:applicationSetting id="bSuppressActiveDirectoryLogo" settingName="suspend-ad-authentication" />
<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="recentlyRegisteredDays" settingName="recently-registered-days"/>
<bright:applicationSetting id="remoteDirectoryIcon" settingName="remote-user-directory-icon" />
<bright:applicationSetting id="remoteDirectoryName" settingName="remote-user-directory-name" />


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Recently Registered</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="approval"/>
	<bean:define id="pagetitle" value="Approval"/>
	<bean:define id="tabId" value="recentlyRegistered"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
				
	<h1><bean:write name="pagetitle" /></h1> 

	<%@include file="inc_approval_tabs.jsp"%>

	<logic:empty name="listUsersForm" property="users">
		
		<p>There are no users that have registered in the past <bean:write name='recentlyRegisteredDays'/> day(s).</p>

	</logic:empty>
	<logic:notEmpty name="listUsersForm" property="users">
		
		
		<p>The following is a list of all the users that have registered in the past <bean:write name='recentlyRegisteredDays'/> day(s):</p>

		<table cellspacing="0" class="admin" summary="List of users">		
			<tr>
				<th></th>
				<th>
					User id:
				</th>
				<th>
					<bright:cmsWrite identifier="label-name" filter="false"/>
				</th>
				<th>								
					Registration date:
				</th>
				<th>
					<bright:cmsWrite identifier="label-groups" filter="false"/>
				</th>
				<th>
					&nbsp;
				</th>
			</tr>
			<logic:iterate name="listUsersForm" property="users" id="user">
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
						<bean:write name="user" property="id"/>
					</td>
					<td>
						<bean:write name="user" property="fullName"/>
					</td>
					<td>
						<bean:write name="user" property="registerDate" format="dd/MM/yyyy"/>
					</td>
					<td>
						<logic:notEmpty name="user" property="groups">
							<logic:iterate name="user" property="groups" id="group" indexId="index">
								<bean:write name="group" property="nameWithOrgUnit"/><c:if test="${user.groupCount > (index+1)}">, </c:if> 
							</logic:iterate>
						</logic:notEmpty>
					</td>
					<td class="action">
						<a href="viewUpdateUser?id=<bean:write name='user' property='id'/>&saveAction=updateRecentUser&cancelAction=viewRecentlyRegistered">edit &raquo;</a>
					</td>														
				</tr>
			</logic:iterate>
		</table>
	
	</logic:notEmpty>
			
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>