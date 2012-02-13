<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Publishing Admin</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="publishing"/>
	<bean:define id="pagetitle" value="Publishing"/>	
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
 
	<h2>Manage Publishing Actions</h2> 
 
		<logic:notEmpty name="publishingForm" property="publishingActions" >
 		<table cellspacing="0" class="list" id="publishing-actions-list">
 			<thead>
	 			<tr>
	 				<th>Name</th>
	 				<th>Publish Location</th>
	 				<th>Access Level</th>
	 				<th>Run Daily</th>
	 				<th colspan="3">Actions</th>
	 			</tr>
 			</thead>
 			<tbody>
				<logic:iterate name="publishingForm" property="publishingActions" id="publishingAction">
					<tr id="publishing-action-<c:out value="${publishingAction.id}" />" class="publishing-action-item">
						<td class="item-name"><bean:write name="publishingAction" property="name" filter="false"/></td>
						<td class="item-path"><bean:write name="publishingAction" property="path" filter="false"/></td>
						<td class="item-access-level"><c:out value="${accessLevelsMap[publishingAction.accessLevelId].name}" /></td>
						<td class="item-run-now"><c:if test="${publishingAction.runDaily}">yes</c:if><c:if test="${!publishingAction.runDaily}">no</c:if></td>
						<td class="action">[<a class="run-link" title="Run this publishing action now" href="runPublishingAction?publishingActionId=<bean:write name="publishingAction" property="id" filter="false"/>">run now</a>]</td>
						<td class="action">[<a class="edit-link" title="Modify this publishing action" href="viewAddPublishingAction?publishingActionId=<bean:write name="publishingAction" property="id" filter="false"/>">edit</a>]</td>
						<td class="action">[<a class="delete-link" title="Delete this publishing action" href="deletePublishingAction?publishingActionId=<bean:write name="publishingAction" property="id" filter="false"/>" onclick="return confirm('Are you sure you want to delete this publishing action?');">X</a>]</td>
					</tr>
				</logic:iterate>
			</tbody>
		</table>
	</logic:notEmpty>
		<logic:empty name="publishingForm" property="publishingActions" >
		<p>There are currently no Publishing Actions.</p> 
	</logic:empty>

	<div class="hr"></div> 

	<p><a id="add-publishing-action-link" href="../action/viewAddPublishingAction">Add a new Publishing Action &raquo;</a></p>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>