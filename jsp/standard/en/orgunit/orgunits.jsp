<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan			09-Dec-2005		Created
	 d2      Ben Browning   	14-Feb-2006    HTML/CSS tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" />  | Organisational Units</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="orgunits"/>
	<bean:define id="pagetitle" value="Organisational Units"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present name="orgUnitForm">
		<logic:equal name="orgUnitForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="userForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<h2>Manage Organisational Units</h2>
	
	<table cellspacing="0" class="list highlight">
		<thead>
			<tr>
				<th>Name</th>
				<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}"><th>Storage Quota</th><th>Storage Usage</th></c:if>
				<th>Custom Content</th>
				<th colspan="4">Actions</th>
			</tr>
		</thead>
		<tbody>
			<logic:iterate name="orgUnitForm" property="orgUnitList" id="orgunit">
				<tr>
					<td><bean:write name="orgunit" property="category.name"/></td>
					
					<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}">
						<td>
							<c:if test="${orgunit.diskQuotaMb <= 0}"><span class="disabled">No limit</span></c:if>
							<c:if test="${orgunit.diskQuotaMb > 0}"><bean:write name="orgunit" property="diskQuotaMb" format="###,###,###"/> Mb</c:if>
						</td>
						<td>
							<c:set var="diskUsage" value="${orgunit.diskUsageBytes / (1024*1024) }"/>
							<c:set var="diskUsagePercent" value="${(diskUsage / orgunit.diskQuotaMb) * 100 }"/>
							<bean:write name="diskUsage" format="###,###,##0.000"/> Mb
							<c:if test="${orgunit.diskQuotaMb>0}">(<bean:write name="diskUsagePercent" format="0.0"/>%)</c:if>
						</td>
					</c:if>
					<td>
						<logic:notEmpty name="orgunit" property='orgUnitExtraContentPages' >
							<bright:refDataList id="langs" componentName="LanguageManager" methodName="getLanguages" transactionManagerName="DBTransactionManager"/>
							<bean:size name='langs' id='noOfLangs' />
							<table cellspacing="0">
							<logic:iterate name="langs" id="lang">
								<tr>
									<c:if test="${noOfLangs > 1}">
										<td><bean:write name='lang' property='name' />: </td>
									</c:if>	
									<td>
									<logic:iterate name="orgunit" property='orgUnitExtraContentPages' id="content" >
										<td>
										[<a href="viewEditListItem?id=<c:out value='${content.contentListItemIdentifier}'/>&amp;languageId=<c:out value='${lang.id}' />&amp;url=/action/listOrgUnits" title="Edit content page">edit content page</a>]</td> 
										<td>[<a href="viewEditListItem?id=<c:out value='${content.menuListItemIdentifier}'/>&amp;languageId=<c:out value='${lang.id}' />&amp;url=/action/listOrgUnits"  title="Edit menu item">edit menu item</a>]</td>
									</logic:iterate>
									</td>
								</tr>
							</logic:iterate>
							</table>
						</logic:notEmpty>
					</td>	
					<td class="action">
						[<a href="editOrgUnit?id=<bean:write name='orgunit' property='id'/>">edit</a>]
					</td>
					<td class="action">
						[<a href="viewPermissionCategories?ouid=<bean:write name='orgunit' property='id'/>">access levels</a>]
					</td>
					<td class="action">
						[<a href="viewOrgUnitDetails?ouid=<bean:write name='orgunit' property='id'/>">view details</a>]
					</td>
					<td class="action">
						<c:choose>
						<%-- Only admin can delete the org units --%>
						<c:when test="${userprofile.isAdmin}">
						[<a href="deleteOrgUnit?id=<bean:write name='orgunit' property='id'/>" onclick="return confirm('Are you sure you want to delete this organisational unit?\nAll groups and access levels within it will be removed.\nYou should first ensure all users and assets within the org unit are deleted or moved to a different org unit.');" title="delete this organisational unit" >X</a>]
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
	
	<br />
	
	<logic:equal name="userprofile" property="isAdmin" value="true">
		<p><strong><a href="editOrgUnit">Add an organisational unit &raquo;</a></strong></p>
		<p>When you add a new organisational unit, you must add at least one user to the admin group, so that you can devolve responsibility to them.</p>
	
	</logic:equal>
	
	<logic:notEmpty name="orgUnitForm" property="orgUnitList">
		<p>Each org unit can have it's own content page that is only shown to members of the org unit (via a link in the left hand menu). To make a content page appear for an org unit click the edit content page link and populate the text you want to appear in the on the content page. To hide the content page again, just clear the content for the page.</p>
		<p>Changes to org unit content are only refreshed when a user logs in (i.e. if you make a change to org unit content while org unit users are logged in then they will only see the changes when the log out and back in again).</p>
	</logic:notEmpty>

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>