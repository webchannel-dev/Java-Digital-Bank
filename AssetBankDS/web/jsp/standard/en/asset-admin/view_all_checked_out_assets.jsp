<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
    d1      Steve Bryan    19-Sep-2008    Copied from view_workflowed_assets.jsp	    
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />
 
<head>
	
	<title><bright:cmsWrite identifier="title-checked-out-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="view-checked-out"/>
	<bean:define id="helpsection" value="checked_out_assets"/>

	<script type="text/javascript" src="../js/workflow-transitions/confirm-transition.js"></script>

</head>
 
<body id="importPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-checked-out-items" filter="false" /></h1> 

	<c:if test="${userprofile.isAdmin}">
		<div class="adminTabs">
			<h2><a href="viewCheckedOutAssets">My Checked Out Assets</a></h2>
			<h2 class="current"><a href="viewAllCheckedOutAssets">All Checked Out Assets</a></h2>
			<div class="tabClearing">&nbsp;</div>
		</div>
	</c:if>

	<c:choose>
    <c:when test="${empty checkedOutAssetsForm.checkedOutAssets}">
    
		<h2><bright:cmsWrite identifier="subhead-all-checked-out-items-empty" filter="false" /></h2>

    </c:when>
    <c:otherwise>
	
		<h2><bright:cmsWrite identifier="subhead-all-checked-out-items" filter="false" /></h2>

		<table cellspacing="0" class="list highlight" summary="Checked Out Assets">		
			<thead>					
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>User</th>	
					<th>Check Out Date</th>	
					<th>Link</th>
				</tr>				
			</thead>
			<tbody>
			    <logic:iterate name="checkedOutAssetsForm" property="checkedOutAssets" id="asset" >
					<tr>
						<td><c:out value="${asset.id}" /></td>
						<td><c:out value="${asset.name}" /></td>
						<td><c:out value="${asset.checkedOutByUser.fullName}" /></td>
						<td><fmt:formatDate value="${asset.dateCheckedOut}" pattern="${dateFormat}" /></td>
						<td><a href="viewAsset?id=<c:out value="${asset.id}" />">view</a></td>
					</tr>
				</logic:iterate>
			</tbody>
		</table>
	
	</c:otherwise>
    </c:choose>	

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>