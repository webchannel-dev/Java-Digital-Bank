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

<bean:parameter id="stateName" name="stateName" value=""/>
<bean:parameter id="workflowName" name="workflowName" value=""/>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewOwnerAssetApproval?workflowName=${workflowName}&stateName=${stateName}"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="heading-submitted-items" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="title-approve-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="my-uploads"/>
	<bean:define id="helpsection" value="submitted_assets"/>
	<bean:define id="tabId" value="user"/>

 
	<script type="text/javascript" src="../js/bulk-approve.js"></script>
	<script type="text/javascript" charset="utf-8">

		// Global variable to store content for alert.
		var baWarningText = "<bright:cmsWrite identifier="js-make-sure-items-selected" filter="false"/>";
			
		$j(function(){
			
			// Initialise approval actions:
			<c:choose>
				<c:when test="${assetWorkflowForm.state.transitionsHaveMessages}">
					var baOptions = {messages : true};
				</c:when>
				<c:otherwise>
					var baOptions = {messages : false};
				</c:otherwise>
			</c:choose>	
			bulkApprove.init(baOptions);
			
			
			
			// Selecting assets
			var $checkArray = $j('input.checkbox');		//get reference to all checkboxes on the page
			var $checkedboxes = $j('li input:checked');	//get reference to all initially checked checkboxes on the page
					
			//highlight wrapping li of any already selected checkboxes
			$checkedboxes.parents('li').addClass('selected');

			// click event on checkboxes
			$checkArray.click(function(event) {
				event.stopPropagation();	
				//highlight surrounding li
				$j(this).parents('li:eq(0)').toggleClass('selected');

			});
			
			
		})
		
		
	</script>
</head>
 
<body id="importPage" class="assetSelectPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-my-uploads" filter="false"/></h1> 

	<bean:define id="tabId" value="approval"/>

	<c:if test="${userprofile.canUploadAtAll}"> <!-- Show the my uploads tab etc if the user can upload (otherwise they can just edit)-->
		<%@include file="../asset-admin/inc_my_uploads_tabs.jsp"%>
	</c:if>

	<c:if test="${userprofile.isAdmin}">
		<p class="tabHolder clearfix">
				<a class="active" href="../action/viewOwnerAssetApproval">My Items</a>
				<a href="../action/viewOwnerAssetApprovalAdmin">Other Users</a>
		</p>
		<div id="tabContent">
	</c:if>
	
	<bright:refDataList componentName="AttributeManager" methodName="getStaticAttribute" argumentValue="dateAdded" id="dateAddedAtt"/>

	
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