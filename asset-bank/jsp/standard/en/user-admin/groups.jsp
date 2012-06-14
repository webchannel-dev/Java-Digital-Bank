<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	09-May-2005		Created
	 d2		Matt Stevenson	10-May-2005		Added link to add group
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Matt Stevenson	03-Jan-2005		Added organisation units
	 d5		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
	 d6		James Home		29-Jan-2007		Added links
	 d7		Matt Stevenson	03-Apr-2008		Added link to remove all users
	 d8      Steve Bryan    21-Apr-2008    Added indicator for Org Unit Admin group
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="enableGroupUsageExlusions" settingName="enable-group-usage-exclusions"/>
<bright:applicationSetting id="useGroupRoles" settingName="use-group-roles"/>
<bright:applicationSetting id="useOrgUnits" settingName="orgunit-use" />


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Manage Groups</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="groups"/>
	<bean:define id="pagetitle" value="Groups"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="userForm">
		<logic:equal name="userForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="userForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	
	
	<bean:parameter id="groupCleared" name="groupCleared" value="0"/>

	<c:if test="${groupCleared > 0}">
		<div class="confirm">Users successfully removed from group</div>
	</c:if>
	
	<h2>Manage Groups</h2>
	
	<form action="listGroups" method="get">
		<input type="hidden" name="pageSize" value="<c:out value="${param.pageSize}"/>" />
		<input type="hidden" name="page" value="0" />
		<table>
			<tr>
				<td>Filter by Name:</td>
				<td>
					<input type="text" class="inline" name="name" value="<c:out value="${param.name}"/>" maxlength="30" size="20"/>
					<input class="button flush" type="submit" value="Filter" />
					<input class="button flush" type="submit" name="Cancel" value="Cancel Filter" />
				</td>
			</tr>
		</table>	
	</form>
	
	<c:if test="${not empty userForm.searchResults}">
		<c:set var="formBean" value="${userForm}"/>
		<c:set var="linkUrl">listGroups?name=<c:out value="${param.name}"/></c:set>
		<c:set var="styleClass" value="pager"/>
		<div class="clearfix"><%@include file="../inc/pager.jsp"%></div>
		<br/>
	</c:if>
	
	<table cellspacing="0" class="list highlight" summary="List of groups">
		<thead>
			<tr>
				<c:if test="${useOrgUnits}">
					<th>Org Unit Name</th>
				</c:if>	
				<th>Group Name</th>
				<th colspan="6">Actions</th>
			</tr>
		</thead>
		
		<tbody>	
			<logic:iterate name="userForm" property="searchResults.searchResults" id="group">
				<tr>
					<c:if test="${useOrgUnits}">
						<td><bean:write name="group" property="orgUnitReference.name"/></td>
					</c:if>
					<td>
						<bean:write name="group" property="name"/>
						<c:if test="${group.orgUnitAdminGroup}"><br/><em>(org&nbsp;unit&nbsp;admins)</em></c:if>
					</td>
					<td class="action">
						[<a href="viewGroup?id=<bean:write name='group' property='id'/>&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>">edit</a>]
					</td>
					<td class="action">
						[<a id="accessLevelPermissionsLink<bean:write name='group' property='id'/>" href="viewGroupAccessLevelPermissions?id=<bean:write name='group' property='id'/>&treeId=2&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>">access levels</a>]
						<script type="text/javascript"><!--
							document.getElementById('accessLevelPermissionsLink<bean:write name='group' property='id'/>').href += '&expandable=true';
						--></script>
					</td>
					<c:if test="${userprofile.isAdmin}">
						<td class="action">
							[<a href="viewGroupCategoryPermissions?id=<bean:write name='group' property='id'/>&treeId=1&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>">categories</a>]
						</td>
					</c:if>
					<td class="action">
						[<a href="viewGroupAttributeExclusion?id=<bean:write name='group' property='id'/>&groupName=<bean:write name='group' property='name'/>&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>">attribute exclusions</a>]
					</td>
					<td class="action">
						[<a href="viewGroupAttributeVisibility?id=<bean:write name='group' property='id'/>&groupName=<bean:write name='group' property='name'/>&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>">attribute visibility</a>]
					</td>
					<td class="action">
						[<a href="viewGroupFilterExclusion?id=<bean:write name='group' property='id'/>&groupName=<bean:write name='group' property='name'/>&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>">filter exclusions</a>]
					</td>
				<c:if test="${enableGroupUsageExlusions}">
					<td class="action">
						[<a href="viewGroupUsageExclusion?id=<bean:write name='group' property='id'/>&groupName=<bean:write name='group' property='name'/>&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>">usage exclusions</a>]
					</td>					
				</c:if>
				<c:if test="${useGroupRoles}">
					<td class="action">
						[<a href="viewGroupRoles?id=<bean:write name='group' property='id'/>&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>">permissions</a>]
					</td>					
				</c:if>
					<td class="action">
						<c:choose>
							<%-- Cannot remove users from public or logged in groups --%>
							<c:when test="${group.id >2}">
								[<a href="removeUsers?id=<bean:write name='group' property='id'/>&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>" onclick="return confirm('Are you sure you want to remove all users from the group: <c:out value='${group.name}'/>?');">remove all users</a>]
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
						
					</td>
					<td>
						<c:choose>
							<%-- Can not delete the public or logged in groups --%>
							<c:when test="${((group.id > 2) && (group.canDelete == true))}">
								[<a href="deleteGroup?id=<bean:write name='group' property='id'/>&name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>" onclick="return confirm('Are you sure you want to delete this group?');">X</a>]
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</logic:iterate>
		</tbody>	
	</table>	
	
	<c:if test="${not empty userForm.searchResults}">
		<div class="clearfix"><%@include file="../inc/pager.jsp"%></div>
	</c:if>	
	
	<br />
	
	<p><a href="viewGroup">Add a group &raquo;</a></p>
	
	<logic:equal name="userprofile" property="isAdmin" value="true">
		<p>
			<p><a href ="viewImportGroups">Import groups &raquo;</a></p>
		</p>
	</logic:equal>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>