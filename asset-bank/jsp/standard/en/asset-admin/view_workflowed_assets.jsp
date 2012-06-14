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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bean:parameter id="approvalType" name="approvalType" value=""/>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewAssetUploadOrEditApproval?approvalType=${approvalType}&stateName=${assetWorkflowForm.state.name}&workflowName=${assetWorkflowForm.selectedWorkflow.name}"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="menu-approve" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="title-approve-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="asset-approval"/>

	<logic:equal name="approvalType" value="uploads"><bean:define id="tabId" value="approveuploads"/></logic:equal>
	<logic:equal name="approvalType" value="edits"><bean:define id="tabId" value="approveedits"/></logic:equal>

	<c:if test="${not empty assetWorkflowForm.assetStateUpdateBatch}">
		<meta HTTP-EQUIV="refresh" CONTENT="3;URL=viewAssetUploadOrEditApproval?stateName=<c:out value='${assetWorkflowForm.state.name}'/>&approvalType=<bean:write name='approvalType'/>&workflowName=<c:out value='${assetWorkflowForm.selectedWorkflow.name}'/>"></meta>
	</c:if>
		
	<script type="text/javascript" src="../js/group-edit.js"></script>	 
	<script type="text/javascript" src="../js/bulk-approve.js"></script>

	<script type="text/javascript">
		
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
			
			// Selecting assets:
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
			
			$j('#userFilter').change(function(){
				$j('#mainForm').submit();
			})
			
			
		})
		

		
		
	</script>

</head>
 
<body id="workflow" class="assetSelectPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-approve-items" filter="false" /></h1> 

	<%@include file="../asset-admin/inc_approval_tabs.jsp"%>

	<!-- h2><bright:cmsWrite identifier="subhead-approve-uploaded-items" filter="false" /></h2-->
	
	<bean:define id="totalSize" name="assetWorkflowForm" property="approvalList.totalSize" />
	<bean:define id="returnSize" name="assetWorkflowForm" property="approvalList.returnSize" />
	<bean:define id="workflowName" name="assetWorkflowForm" property="selectedWorkflow.description"/>
	<bean:define id="allUsers" name="assetWorkflowForm" property="allUsers"/>
	<bean:define id="actionName" value="viewAssetUploadOrEditApproval"/>
	
	<%@include file="../asset-admin/inc_workflow_selector.jsp"%>

	<c:if test="${returnSize == 0}">
		<div class="info">
			<c:choose>
				<c:when test="${tabId == 'approveedits'}">
					<bright:cmsWrite identifier="snippet-no-edits-to-approve" filter="false"/>
				</c:when>
				<c:otherwise>
					<bright:cmsWrite identifier="snippet-no-uploaded-items" filter="false"/> 
				</c:otherwise>		
			</c:choose>
			
			
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
						<bright:writeError name="error" /><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>
	
		<bean:define id="workflows" name="assetWorkflowForm" property="approvalList.workflowList" />
		<bean:size id="sizeWorkflows" name="workflows" />

		<%--  TODO - in the case theres more than one workflow and one variation, we could loop twice, 
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

				<%-- Show links to the states, if there is more than one state in total--%>
				<c:if test="${sizeStates gt 1}">

					<p class="tabHolder clearfix">
						<logic:iterate id="stateHolder" name="states" indexId="index3">
				
							<bean:define id="state" name="stateHolder" property="state" />				
	
		
							<a href="?selectedUserId=<bean:write name='assetWorkflowForm' property='selectedUserId'/>&workflowName=<c:out value='${assetWorkflowForm.workflowName}'/>&stateName=<c:out value='${state.name}'/>&approvalType=<c:out value='${approvalType}' />" <c:if test="${ stateHolder.state.name == assetWorkflowForm.stateName }">class="active"</c:if>>
								<c:out value="${state.description}" />		
								(<c:out value="${stateHolder.numberAssets}"/><c:if test="${stateHolder.numberAssets lt stateHolder.numberAssetsActual}"> of <c:out value="${stateHolder.numberAssetsActual}"/></c:if>)
							</a>

						</logic:iterate>	
			
					</p>
			
					<div id="tabContent">
							
		
				</c:if>
					
				<!-- Transitions -->
				<form name="form" id="mainForm" method="post" action="changeAssetStateApprover" class="approvalState" style="width:100%">

					<input type="hidden" name="workflowName" value="<bean:write name='assetWorkflowForm' property='workflowName'/>"/>
					<input type="hidden" name="selectedUserId" value="<bean:write name='assetWorkflowForm' property='selectedUserId'/>"/>
					<input type="hidden" name="stateName" value="<bean:write name='assetWorkflowForm' property='stateName'/>"/>
					<input type="hidden" name="approvalType" value="<bean:write name='approvalType'/>"/>
				
					<%--  Now the state lists --%>
					
					<logic:iterate name="states" id="stateHolder" indexId="index3">
					
						<c:if test="${ stateHolder.state.name == assetWorkflowForm.stateName }">
						
							<bean:define id="state" name="stateHolder" property="state" />			
							
							<%-- action buttons and message field --%>
							
							<div style="float:right; margin-left: 1.5em">
								<c:if test="${not empty assetWorkflowForm.assetStateUpdateBatch}">
									<h3><bright:cmsWrite identifier="heading-update" filter="false"/> <bright:cmsWrite identifier="snippet-in-progress" filter="false"/></h3>
									<h3><bright:cmsWrite identifier="snippet-complete" filter="false"/>: <c:out value="${assetWorkflowForm.assetStateUpdateBatch.percentageUpdated}"/> % 
									(<c:out value="${assetWorkflowForm.assetStateUpdateBatch.numberUpdated}"/> <bright:cmsWrite identifier="snippet-of" filter="false"/> <c:out value="${assetWorkflowForm.assetStateUpdateBatch.batchSize}"/>)</h3>									
								</c:if>
								<c:if test="${empty assetWorkflowForm.assetStateUpdateBatch}">		
								<ul class="radioButtons" style="float:right;" id="workflowActions" >
									<bean:size id="numActions" name="state" property="transitionList"/>
									<logic:iterate id="transition" name="state" property="transitionList" indexId="index4">	
										<c:if test="${ ! transition.hidden }">
											<li>
												<input type="hidden" id="text_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" value="<c:out value='${transition.confirmationText}' />" />
												<label class="multilineButton <c:if test="${index4+1 == numActions}">last</c:if> <c:if test="${transition.messageMandatory}">message</c:if>">
													<input type="radio" name="transition" id="radio_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" <c:if test="${transition.hasMessage}">class="showMessage"</c:if> value="<c:out value="${transition.transitionNumber}" />" <c:if test="${index4 == 0}">checked="checked"</c:if> />	
												
											
													<c:out value="${transition.description}" /><br />
													<span><c:if test="${state.transitionsHaveHelpText}"><c:out value="${transition.helpText}" /></c:if></span>
												
												</label>
											</li>	
										</c:if>
									</logic:iterate>
								</ul>
								</c:if>					
							
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
									<div class="js-enabled-hide">
									<div id="userMessageForm" title="Message">
										<label for="newMessage_<c:out value='${ais.assetId}'/>"><bright:cmsWrite identifier="snippet-message-to-next" filter="false"/>
									
										<c:choose>
											<c:when test="${transition.messageMandatory}">
												<span class="required">*</span>(<bright:cmsWrite identifier="snippet-message-required" />)</span>
											</c:when>
											<c:otherwise>
												<c:if test="${transition.hasMessage}"><span>(<bright:cmsWrite identifier="snippet-optional" filter="false"/>)</span></c:if>
											</c:otherwise>
										</c:choose>
										</label>
										<textarea name="message" class="group_edit" rows="8" id="newMessage_<c:out value='${ais.assetId}'/>"></textarea>
										<div id="confirmationText" class="warning"></div>
									</div>
									</div>
								</c:if>
							
								<input type="submit" value="Submit" class="button flush js-enabled-hide" style="clear:right; float:right"/>
				
							</div>
							
							
							<%-- end action buttons and message form  --%>
							
							
							
							<c:if test="${sizeStates == 1}">
								<h3><c:out value="${assetWorkflowForm.state.description}" /> (<c:out value="${stateHolder.numberAssetsActual}"/>) &nbsp;&nbsp; <span style="font-size: 0.9em"><a href="workflowApproverBatch?state=<c:out value="${state.name}" />&variation=<c:out value="${variation.variationName}" />&workflow=<c:out value="${workflow.workflowName}" />&returnUrl=<c:out value="${imageDetailReturnUrl}"/>&approvalType=<c:out value="${approvalType}" />"><bright:cmsWrite identifier="link-batch-approve-assets" filter="false"/></a>&nbsp;|&nbsp;<a href="workflowApproverBulk?state=<c:out value="${state.name}" />&variation=<c:out value="${variation.variationName}" />&workflow=<c:out value="${workflow.workflowName}" />&returnUrl=<c:out value="${imageDetailReturnUrl}"/>&approvalType=<c:out value="${approvalType}" />"><bright:cmsWrite identifier="link-bulk-approve-assets" filter="false"/></a></span></h3>
							</c:if>
							
							
							<p><c:out value="${assetWorkflowForm.state.helpText}" /></p>
							
							<c:if test="${stateHolder.numberAssets < stateHolder.numberAssetsActual}">
								<c:set var="numberAssets" value="${stateHolder.numberAssets}" />
								<p><bright:cmsWrite identifier="snippet-number-items-shown" filter="false" replaceVariables="true" /></p>
							</c:if>
					    	

						

							<p class="noBottomMargin">
								<bright:cmsWrite identifier="snippet-select" filter="false"/>: 
								<a href="javascript:selectItems( true, 'mainForm' )"><bright:cmsWrite identifier="snippet-all" filter="false"/></a> | 
								<a href="javascript:selectItems( false, 'mainForm' )"><bright:cmsWrite identifier="snippet-none" filter="false"/></a>
								
						
								<!-- Dropdown showing users -->
								&nbsp;&nbsp;&nbsp;&nbsp;
								<label for="userFilter"><bright:cmsWrite identifier="snippet-show" filter="false"/>:</label>
									<html:select name="assetWorkflowForm" property="filterByUserId" styleId="userFilter">
									<option value="0">- <bright:cmsWrite identifier="snippet-all-users" filter="false"/> -</option>
									<html:options collection="allUsers" property="id" labelProperty="displayName"/>
								</html:select>	
								<input type="submit" value="<bright:cmsWrite identifier="button-go" filter="false"/>" class="js-enabled-hide"/>
							

							<div class="clearing"></div>
							
	

								<div id="itemsWrapper">
									<%-- sort by user --%>
									<logic:iterate name="assetWorkflowForm" property="users" id="user" indexId="userIndex">
										<div id="user_<c:out value="${userIndex}"/>">
											<div class="sectionHead">
												<span>
													<bright:cmsWrite identifier="snippet-select" filter="false"/>:
													<a href="javascript:selectItems( true, 'user_<c:out value="${userIndex}" />' )"><bright:cmsWrite identifier="snippet-all" filter="false"/></a> | 
													<a href="javascript:selectItems( false, 'user_<c:out value="${userIndex}" />' )"><bright:cmsWrite identifier="snippet-none" filter="false"/></a>
													
												</span>
												<h3>
													<c:out value="${user.displayName}"/> <bright:cmsWrite identifier="items" filter="false"/>
													
												</h3>
												<a href="workflowApproverBatch?state=<c:out value="${state.name}" />&variation=<c:out value="${variation.variationName}" />&workflow=<c:out value="${workflow.workflowName}"/>&submittedByUserId=<c:out value="${user.id}"/>&returnUrl=<c:out value="${imageDetailReturnUrl}"/>" class="batch-link"><bright:cmsWrite identifier="link-batch-approve-assets" filter="false"/></a><span style="float:left; margin-left: 0.5em">|</span><a href="workflowApproverBulk?state=<c:out value="${state.name}" />&variation=<c:out value="${variation.variationName}" />&workflow=<c:out value="${workflow.workflowName}"/>&submittedByUserId=<c:out value="${user.id}"/>&returnUrl=<c:out value="${imageDetailReturnUrl}"/>&approvalType=<c:out value="${approvalType}" />" class="batch-link" style="margin-left:0.5em"><bright:cmsWrite identifier="link-bulk-approve-assets" filter="false"/></a>									
											</div>
											<%-- sort by message / user --%>
											<logic:iterate id="message" name="assetWorkflowForm" property="messagesForUser(${user.id})" indexId="index">
												
												<div id="user_<c:out value="${userIndex}"/>_message_<c:out value="${index}"/>">
													<div class="sectionSubHead">
													
														<%-- if there's at least one message we need to show dividers and messages --%>	
														<c:if test="${index > 0}"><div class="dividerLine"></c:if>
															<c:if test="${ not empty message }">
																<div class="approvalMessageBox">
																	<div class="approvalMessageBoxInner">
																		<span class="quote quoteLeft">&#147;</span>
																		<span class="messageContent"><c:out value="${ message }" /></span>
																		<span class="quote quoteRight">&#148;</span> 
																		<span class="quoteTL"></span>
																		<span class="quoteBL"></span>
																		<span class="quoteTR"></span>
																		<span class="quoteBR"></span>
																	</div>
																	<span class="quoteTail"></span>
																</div>
															</c:if>
														<c:if test="${index > 0}"></div></c:if>
													</div>
													
													<ul class="lightbox">
				
														<!-- remove brackets from the message to avoid a JSP error below -->
														<c:set var="messageId" value="${fn:replace(message, '(', '')}"/> 
														<c:set var="messageId" value="${fn:replace(messageId, ')', '')}"/>
														<logic:iterate id="ais" name="assetWorkflowForm" property="assetsForUserAndMessage(${user.id}<>${messageId})">
														
															<c:if test="${ais.assetTypeId!=2}">
																<c:set var="resultImgClass" value="icon"/>
															</c:if>
															<c:if test="${ais.assetTypeId==2}">
																<c:set var="resultImgClass" value="image"/>
															</c:if>
	
															<li>
																<div class="selector">
																	<label>
																		<input type="checkbox" class="checkbox" name="assetId_<c:out value='${ais.assetId}' />" />
																		<bright:cmsWrite identifier="label-select-item" filter="false"/>
																	</label>	
																</div>	
																<div class="detailWrapper">
																
																	<c:set var="thumbSrc" value="../servlet/display?file=${ais.thumbnailImageFile.path}"/>
																	<a href="viewAsset?id=<c:out value='${ais.assetId}' />" class="thumb"><img class="image" src="<bean:write name='thumbSrc'/>" alt="<bean:write name='ais' property='assetId'/>" /></a>
																	<div class="metadataWrapper">
																		<ul class="attributeList">
																		<li><bright:cmsWrite identifier="label-id" /> <c:out value="${ais.assetId}" /></li>
																		<li><bright:cmsWrite identifier="label-date-submitted"/>: <fmt:formatDate value="${ais.submittedDate}" pattern="${dateFormat}" /></li>
																		<li><bright:cmsWrite identifier="label-user" />: <c:out value='${ais.user.displayName}' /></li>
																		<c:if test="${!empty ais.user.emailAddress}"><li>(<c:out value="${ais.user.emailAddress}" />)</li></c:if>
																		</ul>
																	</div>
																	
																</div>
																
																<p class="action" >
											
																	<c:if test="${ais.auditEntry.username==null }">
																		<br />
																	</c:if>
																	<c:if test="${!ais.hasEarlierVersion }">
																		<br />
																	</c:if>
																		
																	<c:if test="${ais.hasEarlierVersion}">
																		<a href="viewEditApprovalDifferences?id=<c:out value='${ais.assetId}' />" onclick="differencePopup(this); return false;" class="view"><bright:cmsWrite identifier="link-view-whats-changed" filter="false"/></a>
																	</c:if>		
																	<%--  Only show audit if there is at least one audit entry --%>	
																	<c:if test="${ais.auditEntry.username!=null}">
																		<a href="../action/viewWorkflowAudit?id=<c:out value='${ais.assetId}'/>" target="_blank" class="view" onclick="popupAssetWorkflowAudit(<c:out value='${ais.assetId}' />); return false;" title="<bright:cmsWrite identifier="tooltip-view-workflow-audit" filter="false"/>"><bright:cmsWrite identifier="link-approval-messages" filter="false"/></a>	
																	</c:if>
																
																	
																	
																	<a href="viewAsset?id=<c:out value='${ais.assetId}' />" class="view"><bright:cmsWrite identifier="link-view-edit-details" filter="false" /></a>
														
																</p>
															</li>
															
														
														</logic:iterate>
												
													</ul>
												
												</div>
													
												<div class="clearing"></div>

											</logic:iterate>
											
										</div>
									
										<div class="clearing"></div>
										
									</logic:iterate>
									<%-- end user sorting --%>
								
								</div>
													
						</c:if>
						
					</logic:iterate>
					<%-- end of the state lists --%>
					
					<div id="bottomOfForm">
					</div>
				
				</form>
				
				
				<c:if test="${sizeStates gt 1}">
					</div>
				</c:if>
			</logic:iterate>
	
		</logic:iterate>
			
	</c:if>


	<%@include file="../inc/body_end.jsp"%>
</body>
</html>
