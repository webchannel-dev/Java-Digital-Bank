<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
    d1      Steve Bryan    19-Sep-2008    Created 	    
    d2		Matt Woollard  03-Oct-2008	  Added batch update links  
    d3		Steve Bryan		24-Apr-2009		Moved javascript functions out to a file
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewAssetUploadApproval"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="subhead-approve-uploaded-items" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="title-approve-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="asset-approval"/>
	<bean:define id="tabId" value="approveuploads"/>

	<script type="text/javascript" src="../js/workflow-transitions/confirm-transition.js"></script>
	<script type="text/javascript" src="../js/group-edit.js"></script>

</head>
 
<body id="workflow">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-approve-items" filter="false" /></h1> 

	<%@include file="../asset-admin/inc_approval_tabs.jsp"%>

	<h2><bright:cmsWrite identifier="subhead-approve-uploaded-items" filter="false" /></h2>
	
	<bean:define id="totalSize" name="assetWorkflowForm" property="approvalList.totalSize" />
	<bean:define id="returnSize" name="assetWorkflowForm" property="approvalList.returnSize" />
	<bean:define id="workflowName" name="assetWorkflowForm" property="selectedWorkflow.description"/>
	
	
	
	<bean:define id="actionName" value="viewAssetUploadApproval"/>
	<%@include file="../asset-admin/inc_workflow_selector.jsp"%>
	
	<c:if test="${totalSize > 0}">
	    <div>
	    	<p>
	    		<bright:cmsWrite identifier="snippet-number-uploaded-items" filter="false" replaceVariables="true" /><c:choose><c:when test="${returnSize != totalSize}"><bright:cmsWrite identifier="snippet-number-uploaded-items2" filter="false" replaceVariables="true" /></c:when><c:otherwise>.</c:otherwise></c:choose>
			</p>
			<%-- If there are multiple entries then display the group-edit/individual-edit radio boxes
			(Currently disabled, pending design decision - TRC 20/08/2010)
			<c:if test="${totalSize > 1}">
				<%@include file="../asset-admin/inc_group_edit_selector.jsp"%>
			</c:if>
			--%>
		</div>
	</c:if>
	<c:if test="${returnSize == 0}">
		<div class="info">
			<bright:cmsWrite identifier="snippet-no-uploaded-items" filter="false"/> 
			
			<c:if test="${assetWorkflowForm.downloadedAssetsForApproval > 0}">
				<bright:cmsWrite identifier="snippet-download-requests-pending-approval" filter="false"/>
			</c:if>

		</div>
	</c:if>

	<c:if test="${returnSize > 0}">
		<logic:present  name="assetWorkflowForm">
			<logic:equal name="assetWorkflowForm" property="hasErrors" value="true">
				<div class="error">
					<logic:iterate name="assetWorkflowForm" property="errors" id="error">
							<bean:write name="error" filter="false"/><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>		
	
		<bean:define id="workflows" name="assetWorkflowForm" property="approvalList.workflowList" />
		<bean:size id="sizeWorkflows" name="workflows" />
				
		<%--  TODO - in the case there's more than one workflow and one variation, we could loop twice, 
		the first time to generate tabs for workflow+variations and the second to show the states --%>
		<logic:iterate name="workflows" id="workflow" indexId="index">
			<bean:define id="variations" name="workflow" property="variationList" />				
			<bean:size id="sizeVariations" name="variations" />
		
			<%--  Only show workflow names if theres more than one --%>
			<c:if test="${sizeWorkflows gt 1}">
				<h3>Workflow: <c:out value="${workflow.workflowName}" /></h3>
			</c:if>
			
			<logic:iterate name="variations" id="variation" indexId="index2">
				<bean:define id="states" name="variation" property="stateList" />				
				<bean:size id="sizeStates" name="states" />
		
				<c:if test="${sizeVariations gt 1}">
					<h3>Variation: <c:out value="${variation.variationName}" /></h3>
				</c:if>

				<%-- Show links to the states, if there's more than one state in total--%>
				<c:if test="${sizeStates gt 1}">
								
					<ul>
					<logic:iterate name="states" id="stateHolder" indexId="index3">
						
						<bean:define id="assets" name="stateHolder" property="assetList" />				
						<bean:define id="state" name="stateHolder" property="state" />				
			
						<c:if test="${!empty assets}">	
						<li>
							<a href="#<c:out value="${state.name}" />">
								<c:out value="${state.description}" />		
								(<c:out value="${stateHolder.numberAssets}"/><c:if test="${stateHolder.numberAssets lt stateHolder.numberAssetsActual}"> of <c:out value="${stateHolder.numberAssetsActual}"/></c:if>)
							</a>
							<br/>
						</li>
						</c:if>						
					</logic:iterate>	
					</ul>						
				</c:if>

				<%--  Now the lists --%>
				<logic:iterate name="states" id="stateHolder" indexId="index3">
					
					<div class="hr"></div>	
		
					<bean:define id="assets" name="stateHolder" property="assetList" />				
					<bean:define id="state" name="stateHolder" property="state" />				
		
					<c:if test="${!empty assets}">	
					
						<%--  anchor --%>
						<a name="<c:out value="${state.name}" />"></a>
						
						<h3>
							<c:out value="${state.description}" />						
							(<c:out value="${stateHolder.numberAssets}"/><c:if test="${stateHolder.numberAssets lt stateHolder.numberAssetsActual}"> of <c:out value="${stateHolder.numberAssetsActual}"/></c:if>)
						</h3>
						<p>
							<c:out value="${state.helpText}" />
							<a href="workflowApproverBatch?state=<c:out value="${state.name}" />&variation=<c:out value="${variation.variationName}" />&workflow=<c:out value="${workflow.workflowName}" />"><bright:cmsWrite identifier="link-batch-approve-assets" filter="false"/></a>
						</p>
									
						<logic:iterate name="assets" id="ais" indexId="index3">
							<c:if test="${ais.assetTypeId!=2}">
								<c:set var="resultImgClass" value="icon"/>
							</c:if>
							<c:if test="${ais.assetTypeId==2}">
								<c:set var="resultImgClass" value="image"/>
							</c:if>
							<ul class="lightbox">
								<li>
									<div class="detailWrapper">
										<c:set var="thumbSrc" value="../servlet/display?file=${ais.thumbnailImageFile.path}"/>
										<a href="viewAsset?id=<c:out value='${ais.assetId}' />" class="thumb"><img class="image" src="<bean:write name='thumbSrc'/>" alt="<bean:write name='ais' property='assetId'/>" /></a><br/>
										ID: <c:out value="${ais.assetId}" /><br />
										Date added: <fmt:formatDate value="${ais.dateAdded}" pattern="${dateFormat}" /><br/>
										User: <c:out value='${ais.userName}' />
										<c:if test="${!empty ais.userEmail}">(<c:out value="${ais.userEmail}" />)</c:if>
										
									</div>
									<p class="action">
											<a href="viewAsset?id=<c:out value='${ais.assetId}' />" class="view"><bright:cmsWrite identifier="link-view-edit-details" filter="false" /></a>
									</p>
								</li>
							</ul>
							
							
							<!-- Transitions -->
							<c:set var="onsubmit" value="return doConfirmation(${ais.assetId});" />
							<form name="form_<c:out value='${ais.assetId}' />" method="post" action="changeAssetStateApprover" onsubmit="<c:out value="${onsubmit}" escapeXml="false" />" class="approvalState">
								<input type="hidden" name="assetId" value="<c:out value="${ais.assetId}" />" />
								<input type="hidden" name="workflowInfoId" value="<c:out value='${ais.workflowInfoId}' />" />
								<ul class="radioList">
									<logic:iterate name="state" property="transitionList" id="transition" indexId="index4">	
										<li class="clearfix">
											<input type="hidden" id="text_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" value="<c:out value='${transition.confirmationText}' />" />
											<input type="radio" name="transition" class="radio group_edit" id="radio_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" value="<c:out value="${transition.transitionNumber}" />" <c:if test="${index4 == 0}">checked="checked"</c:if> />	
												
											<label for="radio_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />">
												<c:out value="${transition.description}" />
												<span><c:if test="${state.transitionsHaveHelpText}"><c:out value="${transition.helpText}" /></c:if></span>
												<c:choose>
													<c:when test="${transition.messageMandatory}">
														<span><em>(<bright:cmsWrite identifier="snippet-message-required" />)</em></span><span class="required">*</span>
													</c:when>
													<c:otherwise>
														<c:if test="${transition.hasMessage}"><span><em>(<bright:cmsWrite identifier="snippet-message-optional" />)</em></span></c:if>
													</c:otherwise>
												</c:choose>
											</label>
										</li>											
									</logic:iterate>
								</ul>
								
								<%--  Only show audit if there is at least one audit entry --%>								
								<c:if test="${ais.auditEntry.username!=null}">
									<a href="../action/viewWorkflowAudit?id=<c:out value='${ais.assetId}'/>" target="_blank" class="viewMessages" onclick="popupAssetWorkflowAudit(<c:out value='${ais.assetId}' />); return false;" title="View asset workflow audit in a new window"><bright:cmsWrite identifier="link-previous-messages" filter="false"/> &raquo;</a>
									<strong><bright:cmsWrite identifier="snippet-message-from-previous" filter="false"/></strong> 
									<p class="approvalMessage">	
										<c:out value="${ais.auditEntry.message}" />
										<span class="userDate"><c:out value="${ais.auditEntry.name}" /> (<c:out value="${ais.auditEntry.username}" />) -  <bean:write name="ais" property="auditEntry.dateAdded" format="dd/MM/yyyy HH:mm:ss"/></span>
									</p>
								</c:if>
								
								<%--  Show message if any of the transitions have one --%>
								<c:if test="${state.transitionsHaveMessages}">
									<label for="newMessage_<c:out value='${ais.assetId}'/>"><bright:cmsWrite identifier="snippet-message-to-next" filter="false"/></label>
									<br />
									<textarea name="message" class="group_edit" rows="3" id="newMessage_<c:out value='${ais.assetId}'/>"></textarea>
								</c:if>
								
								<input type="submit" value="Submit" class="button flush" />
							</form>
							<div class="clearing"><!-- &nbsp; --></div>
						</logic:iterate>
						
						
						
					</c:if>
					
				</logic:iterate>
				
			</logic:iterate>
	
		</logic:iterate>
			
	</c:if>


	<%@include file="../inc/body_end.jsp"%>
</body>
</html>