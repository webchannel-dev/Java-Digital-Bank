<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		28-Sep-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Marketing Groups</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="marketing"/>
	<bean:define id="pagetitle" value="Marketing"/>
	<bean:define id="helpsection" value="marketing-groups"/>
	<bean:define id="tabId" value="marketingGroups"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	
	<%@include file="../marketing-admin/inc_marketing_tabs.jsp"%>

	<p>
		This page lists the current marketing groups. Note that the description for each group is visible to users, but the notes 
		are not, and can be used to share information about the group with any other admin users.
	</p>

	<div class="hr"></div>

	<c:if test="${empty marketingGroupForm.marketingGroups}">
		<p>There are currently no marketing groups.</p>
	</c:if>
	<c:if test="${not empty marketingGroupForm.marketingGroups}">

		<table cellspacing="0" class="admin list" summary="List of marketing groups">
			<tr>
				<th>Name:</th>
				<th>Description:</th>
				<th>Notes:</th>
				<th>No. users:</th>
				<th style="width:100px">Actions</th>
			</tr>
					
			<logic:iterate name="marketingGroupForm" property="marketingGroups" id="group" indexId="groupIndex">
				<tr>
					<td><bean:write name="group" property="name"/></td>
					<td><bright:writeWithCR name="group" property="description" filter="false"/></td>
					<td><bright:writeWithCR name="group" property="purpose" filter="false"/></td>
					<td><bean:write name="group" property="numUsers"/></td>
					<td colspan="2">
						[<a href="../action/viewEditMarketingGroup?id=<bean:write name='group' property='id'/>">edit</a>]  
						[<a href="../action/deleteMarketingGroup?id=<bean:write name='group' property='id'/>" onclick="return confirm('Are you sure you want to delete this Marketing Group?')">delete</a>]
					</td>						
				</tr>
				
		
			</logic:iterate>
		</table>
	</c:if>
	
	<div class="hr"></div>
	
	<p><a href="../action/viewAddMarketingGroup">Add a new group &raquo;</a></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>