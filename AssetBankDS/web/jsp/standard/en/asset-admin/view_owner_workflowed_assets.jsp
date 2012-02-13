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
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewOwnerAssetApproval"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="heading-submitted-items" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="title-approve-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="my-uploads"/>
	<bean:define id="helpsection" value="submitted_assets"/>
	<bean:define id="tabId" value="user"/>

	<script type="text/javascript" src="../js/workflow-transitions/confirm-transition.js"></script>

</head>
 
<body id="importPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-my-uploads" filter="false"/></h1> 

	<bean:define id="tabId" value="approval"/>
	<%@include file="../asset-admin/inc_my_uploads_tabs.jsp"%>
	

	
	<c:if test="${userprofile.isAdmin}">
		<p class="tabHolderPopup clearfix">
				<a class="active" href="../action/viewOwnerAssetApproval">My Items</a>
				<a href="../action/viewOwnerAssetApprovalAdmin">Other Users</a>
		</p>
		<div id="tabContent">
	</c:if>
	
	<bright:refDataList componentName="AttributeManager" methodName="getStaticAttribute" argumentValue="dateAdded" id="dateAddedAtt"/>
	<!-- <div class="info floatRight">
			<a href="search?addedByUserId=<c:out value='${userprofile.user.id}' />&approvalStatus=3&sortAttributeId=<c:out value='${dateAddedAtt.id}' />&sortDescending=true"><bright:cmsWrite identifier="link-view-owner-approved-items" /> &raquo;</a>
		</div> -->
	
	<h3><bright:cmsWrite identifier="subhead-submitted-items" filter="false" /></h3>

	<bean:define id="actionName" value="viewOwnerAssetApproval"/>
	
	<c:set var="sChangeStateAction" value="changeAssetStateOwner" />
	<c:set var="sBatchAction" value="workflowOwnerBatch" />
	<%@include file="inc_owner_workflow_assets.jsp"%>

	<c:if test="${userprofile.isAdmin}">
		</div>
	</c:if>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>