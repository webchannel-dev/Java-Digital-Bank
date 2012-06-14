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
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewOwnerAssetApprovalAdmin?selectedUserId=${assetWorkflowForm.selectedUserId}&workflowName=${assetWorkflowForm.selectedWorkflow.name}"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="heading-submitted-items" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="title-approve-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="my-uploads"/>
	<bean:define id="tabId" value="admin"/>

	<script type="text/javascript" src="../js/workflow-transitions/confirm-transition.js"></script>

</head>
 
<body id="importPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-my-uploads" filter="false"/></h1> 
	
	<bean:define id="tabId" value="approval"/>
	<%@include file="../asset-admin/inc_my_uploads_tabs.jsp"%>
	
	<c:if test="${userprofile.isAdmin}">
		<p class="tabHolderPopup clearfix">
				<a href="../action/viewOwnerAssetApproval">My Items</a>
				<a class="active" href="../action/viewOwnerAssetApprovalAdmin">Other Users</a>
		</p>
		<div id="tabContent">
	</c:if>

	<h3>All submitted and unapproved items</h3>

	<bean:define id="users" name="assetWorkflowForm" property="listUsers" />
	<bean:size id="iSizeUsers" name="users" />
	
	<c:choose>
		<c:when test="${iSizeUsers gt 0}">
			<%-- Selector to pick user --%>
			<form action="viewOwnerAssetApprovalAdmin" method="post">
				<label for="user">Select a user:</label>
				<html:select name="assetWorkflowForm" property="selectedUserId" styleId="user" onchange="document.getElementById('b_go').click();">
					<html:option value="0">-- Select --</html:option>
					<html:options collection="users" property="id" labelProperty="name"/>
				</html:select>	
				
				<input type="submit" name="b_go" class="button" value="<bright:cmsWrite identifier="button-go-arrow" filter="false" />" style="display: inline;" id="b_go"/>			
				<script type="text/javascript">
				<!--
					// Hide the Go button if JavaScript is available
					document.getElementById('b_go').style.display='none';
				-->
				</script>
			</form>
	
	<div class="hr"></div>	
	
				<c:if test="${assetWorkflowForm.selectedUserId > 0}">
					<bean:define id="actionName" value="viewOwnerAssetApprovalAdmin"/>
			
	<c:set var="sChangeStateAction" value="changeAssetStateOwnerAdmin" />
	<c:set var="sBatchAction" value="workflowOwnerBatchAdmin" />
	<%@include file="inc_owner_workflow_assets.jsp"%>
				</c:if>
		</c:when>
		<c:otherwise>
			<p>There are currently no unapproved user assets.</p>
		</c:otherwise>	
	</c:choose>
	
	<c:if test="${userprofile.isAdmin}">
		</div>
	</c:if>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>