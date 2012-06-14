<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		02-Feb-2011		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/abplugin.tld" prefix="abplugin" %>

<bean:parameter name="search" id="search" value="false" />
<bean:parameter name="assetId" id="assetId" value="-1" />
<bean:parameter name="messageId" id="messageId" value="-1" />
<bean:parameter name="messageGroup" id="messageGroup" value="" />
<bean:parameter name="report" id="report" value="false" />

<c:set var="forwardParams" value="search=${search}&assetId=${assetId}&report=${report}&messageId=${messageId}&messageGroup=${messageGroup}" />
<c:set var="encForwardParams" value="search=${search}%26assetId=${assetId}%26report=${report}%26messageId=${messageId}%26messageGroup=${messageGroup}" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" /> | <bright:cmsWrite identifier='batch-release' /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<%@include file="../inc/set_this_page_url.jsp"%>
	<bean:define id="section" value="batchReleases"/>
		
	<c:if test="${not empty batchReleaseForm.pdfGenerationStatus && (batchReleaseForm.pdfGenerationStatus == 'Pending' || batchReleaseForm.pdfGenerationStatus == 'Running')}">
		<meta HTTP-EQUIV="refresh" CONTENT="5;URL=viewBatchRelease?brId=<c:out value='${batchReleaseForm.id}'/>&<bean:write name='forwardParams' />"></meta>
	</c:if>
	
</head>

<body id="adminPage"> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier='batch-release' case="mixed" /></h1> 
	

	<%-- START TOOLBAR: Actions to transition batch release workflows, generate PDFs, view assets etc --%>
	
	<div class="toolbar noTopMargin">
		
		<h2><bean:write name="batchReleaseForm" property="name" /></h2>
		

		
		



		<c:choose>

			<c:when test="${empty batchReleaseForm.pdfGenerationStatus && batchReleaseForm.assetCount > 0}">
				<div class="group first"  style="float:right">
					<a href="generateBatchReleasePDF?brId=<bean:write name='batchReleaseForm' property='id'/>&name=<bean:write name='batchReleaseForm' property='name'/>&<c:out value='${forwardParams}' />" class="button noRightMargin"><bright:cmsWrite identifier="link-print-pdf" filter="false" /></a>
				</div>
			</c:when>
			<c:otherwise>
				<c:if test="${batchReleaseForm.pdfGenerationStatus == 'Pending' || batchReleaseForm.pdfGenerationStatus == 'Running'}">
					<div class="group first"  style="float:right">
						<bright:cmsWrite identifier="snippet-indesign-pdf-status" />: 
						<bean:write name="batchReleaseForm" property="pdfGenerationStatus" />
					</div>				
				</c:if>
				<c:if test="${batchReleaseForm.pdfGenerationStatus == 'Completed'}">
					<div class="group first" style="float:right">	
						<a href="downloadBatchReleasePDF?brId=<bean:write name='batchReleaseForm' property='id'/>&pdfFileName=<bean:write name='batchReleaseForm' property='pdfFileName'/>&userPdfFileName=<bean:write name='batchReleaseForm' property='userPdfFileName'/>" class="button noRightMargin"><bright:cmsWrite identifier="button-download" /></a>
					</div>
					<div class="group first" style="float:right; padding-right: 10px">	
						<bright:cmsWrite identifier="snippet-indesign-pdf-status" />:
						<bean:write name="batchReleaseForm" property="pdfGenerationStatus" />  
					</div>				
				</c:if>
				<c:if test="${batchReleaseForm.pdfGenerationStatus == 'Failed'}">
					<c:if test="${batchReleaseForm.assetCount > 0 }"> 
						<div class="group first" style="float:right">
							<a href="generateBatchReleasePDF?brId=<bean:write name='batchReleaseForm' property='id'/>&name=<bean:write name='batchReleaseForm' property='name'/>" class="button noRightMargin"><bright:cmsWrite identifier="link-print-pdf" filter="false" /></a> 
						</div>	
					</c:if>
					<div class="group first" style="float:right; padding-right: 10px">
						<bright:cmsWrite identifier="snippet-indesign-pdf-status" />:     
						<bean:write name="batchReleaseForm" property="pdfGenerationStatus" /> - 
						<bean:write name="batchReleaseForm" property="pdfGenError" /> 
					</div>													
				</c:if>				
			</c:otherwise>
		</c:choose>
       
		<abplugin:include-view-extensions extensibleEntity="batchRelease" position="toolbarEnd"/>

		<div class="clearing"></div>
	</div>
	
	<div class="clearing"></div>
	<%-- END TOOLBAR --%>
	

	<logic:present  name="batchReleaseForm">
		<logic:equal name="batchReleaseForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="batchReleaseForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>

	<%--  Show message when batch release is invalid or currently being validated (pending) --%>	
	<c:if test="${batchReleaseForm.invalid || batchReleaseForm.pending}">
		<c:choose>
			<c:when test="${batchReleaseForm.currentState.name == 'not-ready-for-release'}">
				<%-- are we here as a result of a failed release? If so display a message --%>
				<div class="error">
					<c:set var="url" value="validateBatchRelease?brId=${batchReleaseForm.id}&${forwardParams}" />
					<bright:cmsWrite identifier="snippet-batch-release-not-released" filter="false" replaceVariables="true" />
				</div>
			</c:when>
			<c:otherwise>
				<c:if test="${batchReleaseForm.invalid}">
					<div class="error">
						<c:set var="url" value="validateBatchRelease?brId=${batchReleaseForm.id}&${forwardParams}" />
						<bright:cmsWrite identifier="snippet-batch-release-invalid" filter="false" replaceVariables="true" />
					</div>
				</c:if>
				<c:if test="${batchReleaseForm.pending}">
					<div class="warning">
						<c:set var="url" value="viewBatchRelease?brId=${batchReleaseForm.id}&${forwardParams}" />
						<bright:cmsWrite identifier="snippet-batch-release-pending" filter="false" replaceVariables="true" />
					</div>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:if>

	<%-- START STATUS MESSAGE: Current status of batch release--%>
	
	<div class="info noIcon">
		<div class="toolbar noMargin">
			<h3><span style="display:inline-block; width: 118px">Status: </span>
				<bean:define id="releaseInfoHolder" name="batchReleaseForm" /><%@include file="inc_release_status.jsp"%>
			</h3>
			<c:choose>
				<c:when test="${batchReleaseForm.assetCount <= 0}">
					<div class="group">
						<div class="warningInline"><bright:cmsWrite identifier="noAssetsInBatchRelease" /></div>
					</div>
				</c:when>
				<c:otherwise>
					<c:if test="${empty batchReleaseForm.releaseDate}">

						<c:if test="${showToolbarTransitions}">
							<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager"  methodName="getHasUserApprovedBatchRelease" argumentValue="${userprofile.user.id}" argument2Value="${batchReleaseForm.id}" id="hasApproved"/>

							<logic:iterate name="batchReleaseForm" property="currentState.transitionList" id="transition">
								<c:if test="${!transition.hidden && !((batchReleaseForm.currentState.name == 'awaiting-approval' || batchReleaseForm.currentState.name == 'further-approval-required') && hasApproved)}">
									<div class="group">
										<a href="transitionBatchRelease?id=<bean:write name='batchReleaseForm' property='id' />&workflowName=<bean:write name='batchReleaseForm' property='currentState.workflowName'/>&transition=<bean:write name='transition' property='transitionNumber'/>&<c:out value='${forwardParams}'/>" onclick="return confirm('<bright:cmsWrite identifier='snippet-confirm-batch-release-transition' case='sentence' />');" class="button noRightMargin"><bean:write name='transition' property='description' /></a>
									</div>	
								</c:if>
							</logic:iterate>
						</c:if>

					</c:if>
				</c:otherwise>
			</c:choose>	
		</div>
		<div class="clearing"></div>
	</div>

	<%-- END STATUS MESSAGE --%>


	<%-- START METADATA --%>

	<table border="0" cellspacing="0" class="form stripey" style="margin-top:0; width: 100%;">
		
		<tr>          

			<th><bright:cmsWrite identifier="label-contents" filter="false"/></th>  
			<c:if test="${batchReleaseForm.assetCount != visibleAssetCount}">
				<c:set var="visibleSnippet">&nbsp;(<c:out value='${visibleAssetCount}' /> visible)</c:set>
			</c:if>
			<td class="padded"><bright:cmsWrite identifier="snippet-this-br-contains" filter="false"/> <strong><c:out value="${batchReleaseForm.assetCount}" /></strong> 
				<c:choose> 
					<c:when test="${batchReleaseForm.assetCount == 1}">   
						<bright:cmsWrite identifier="item" /><c:out value='${visibleSnippet}' escapeXml="false" />. <a href="viewAssetsInBatchRelease?brId=<bean:write name='batchReleaseForm' property='id' />&amp;searchPage=batchrelease&brUrl=<c:out value='${thisUrlForGet}' />"><bright:cmsWrite identifier="link-view-item" filter="false"/></a>
					</c:when>
					<c:otherwise> 
					     <bright:cmsWrite identifier="items" /><c:out value='${visibleSnippet}' escapeXml="false" />. <c:if test="${batchReleaseForm.assetCount > 0}"><a href="viewAssetsInBatchRelease?brId=<bean:write name='batchReleaseForm' property='id' />&amp;searchPage=batchrelease&amp;brUrl=<c:out value='${thisUrlForGet}' />"><bright:cmsWrite identifier="link-view-items" case="sentence" filter="false"/></a></c:if>
					</c:otherwise>
				</c:choose> 
				<c:if test="${batchReleaseForm.assetCount > 0}">  
 					<c:if test="${userprofile.canManageBatchReleases}">
						| <a href="viewAssetChangeDescriptions?brId=<bean:write name='batchReleaseForm' property='id' />&queryString=<c:out value='${encForwardParams}' />"><bright:cmsWrite identifier="link-item-change-descriptions" filter="false" case="sentence" /></a>
					</c:if>
				</c:if>
			</td>             

		</tr>
		<tr>	
			<th style="line-height:24px"><bright:cmsWrite identifier="label-validity" /></th>
			<td class="padded">

				<c:choose>
					<c:when test="${batchReleaseForm.valid}">
						<div class="confirmInline" style="float:left; margin-right:1em"><bright:cmsWrite identifier="snippet-valid" /></div> 
						<c:if test="${batchReleaseForm.assetCount > 0}"><a href="validateBatchRelease?brId=${batchReleaseForm.id}&${forwardParams}" class="button noRightMargin"><bright:cmsWrite identifier="link-revalidate-batch-release" filter="false" /></a></c:if>
					</c:when>
					<c:when test="${batchReleaseForm.invalid || batchReleaseForm.pending}">
						<c:choose>
							<c:when test="${batchReleaseForm.currentState.name == 'not-ready-for-release'}">
								<%-- are we here as a result of a failed release? If so display a message --%>
								<div class="errorInline" style="float:left; margin-right:1em">
									<c:set var="url" value="validateBatchRelease?brId=${batchReleaseForm.id}&${forwardParams}" />
									<bright:cmsWrite identifier="snippet-invalid" />
								</div>
								<a href="validateBatchRelease?brId=${batchReleaseForm.id}&${forwardParams}" class="button noRightMargin"><bright:cmsWrite identifier="link-validate-batch-release" filter="false" /></a>
							</c:when>
							<c:otherwise>
								<c:if test="${batchReleaseForm.invalid}">
									<div class="errorInline" style="float:left; margin-right:1em">
										<c:set var="url" value="validateBatchRelease?brId=${batchReleaseForm.id}&${forwardParams}" />
										<bright:cmsWrite identifier="snippet-invalid" />
									</div>
									
									<a href="validateBatchRelease?brId=${batchReleaseForm.id}&${forwardParams}" class="button noRightMargin"><bright:cmsWrite identifier="link-validate-batch-release" filter="false" /></a>
								</c:if>
								
								<c:if test="${batchReleaseForm.pending}">
									<div class="warningInline">
										<c:set var="url" value="viewBatchRelease?brId=${batchReleaseForm.id}&${forwardParams}" />
										<bright:cmsWrite identifier="snippet-being-validated" />
									</div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:when>
				</c:choose>

			</td>
		</tr>
		
		<tr>
			<th><bright:cmsWrite identifier="label-description"  />:</th>
			<td class="padded"><bean:write name='batchReleaseForm' property='description' /></td>
		</tr>
		

		<tr>
			<th><bright:cmsWrite identifier="label-created-date" /></th>
			<td class="padded"><bean:write name='batchReleaseForm' property='creationDate' format='dd/MM/yyyy' /></td>
		</tr>
		


		<logic:notEmpty name="batchReleaseForm" property="releaseDate">
			<tr>
				<th><bright:cmsWrite identifier="label-release-date" /></th>
				<td class="padded"><bean:write name='batchReleaseForm' property='releaseDate' format='dd/MM/yyyy' /></td>
			</tr>
		</logic:notEmpty>
	
		<c:if test="${batchReleaseForm.createdByUserId > 0}">
			<bright:refDataList componentName="UserManager" transactionManagerName="DBTransactionManager" methodName="getUser" argumentValue="${batchReleaseForm.createdByUserId}" dontPassLanguage="true" id="owner"/>
			<tr>
				<th><bright:cmsWrite identifier="label-created-by" /></th>
				<td class="padded"><bean:write name='owner' property='username' /><logic:notEmpty name='owner' property='emailAddress'> (<a href="mailto:<bean:write name='owner' property='emailAddress' />"><bean:write name='owner' property='emailAddress' /></a>)</logic:notEmpty></td>
			</tr>
		</c:if>
		<tr>
			<th><bright:cmsWrite identifier="label-batch-release-notes" /></th>
			<td class="padded"><bean:write name='batchReleaseForm' property='notes' /></td>
		</tr>
	</table>

	<%-- END METADATA --%>


	<%-- START USER LISTS: Show batch release managers the approval users and acknowledgement users --%>

	<c:if test="${userprofile.canManageBatchReleases}">

		<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager"  methodName="getApprovalUsersForBatchRelease" argumentValue="${batchReleaseForm.id}" id="approvalUsers"/>
			
		<logic:notEmpty name='approvalUsers'>
			<br />
			<h3><bright:cmsWrite identifier="subhead-approval-users" />:</h3> 

			<table border="0" cellspacing="0" class="list highlight noTopMargin" >
				<thead>
					<tr>
						<th><bright:cmsWrite identifier="label-name-nc" /></th>
						<th><bright:cmsWrite identifier="label-username-nc" /></th>
						<th><bright:cmsWrite identifier="label-email-nc" />
						<th><bright:cmsWrite identifier="label-status-nc" /></th>
						<th></th>
					</tr>
			
				</thead>
		
				<tbody>
					<logic:iterate name='approvalUsers' id='approval'>
						<tr>
							<td><bean:write name='approval' property='user.forename' /> <bean:write name='approval' property='user.surname' /> </td>
							<td><bean:write name='approval' property='user.username' /></td>
							<td><a href="mailto:<bean:write name='approval' property='user.emailAddress' />"><bean:write name='approval' property='user.emailAddress' /></a></td>
							<td>
								<c:choose>
									<c:when test='${approval.approved}'><span class="inlineConfirm"><bright:cmsWrite identifier='snippet-approved' /></span></c:when>
									<c:otherwise><span class="inlineError"><bright:cmsWrite identifier='snippet-awaiting-approval'  /></span></c:otherwise>
								</c:choose>
							</td>
						</tr>
					</logic:iterate>		
				</tbody>

			</table>
		</logic:notEmpty>

		<c:if test="${batchReleaseForm.releaseDate != null}">

			<%-- released batch release - list the affected users and acknowledgement status --%>

			<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager"  methodName="getAcknowledgements" argumentValue="${batchReleaseForm.id}" id="acknowledgements"/>
			
			<logic:notEmpty name="acknowledgements">
				<br />
				<h3><bright:cmsWrite identifier="subhead-acknowledgements" />:</h3> 
				
				<table border="0" cellspacing="0" class="list stripey noTopMargin">
					<thead>
						<tr>
							<th><bright:cmsWrite identifier="label-name-nc" /></th>
							<th><bright:cmsWrite identifier="label-username-nc" /></th>
							<th><bright:cmsWrite identifier="label-email-nc" />
							<th><bright:cmsWrite identifier='snippet-acknowledged' case="sentence"/></th>
						</tr>
					</thead>
					<tbody>
					<logic:iterate name="acknowledgements" id="acknowledgement">
						<tr>
							<td><bean:write name='acknowledgement' property='user.forename' /> <bean:write name='acknowledgement' property='user.surname' /> </td>
							<td><bean:write name='acknowledgement' property='user.username' /></td>
							<td><a href="mailto:<bean:write name='acknowledgement' property='user.emailAddress' />"><bean:write name='acknowledgement' property='user.emailAddress' /></a></td>
							<td>
								<c:choose>
									<c:when test='${acknowledgement.acknowledged}'><span class="inlineConfirm"><bright:cmsWrite identifier='snippet-acknowledged' case="sentence"/></span></c:when>
									<c:otherwise><span class="inlineError"><bright:cmsWrite identifier='snippet-not-yet-acknowledged' case="sentence"/></span></c:otherwise>
								</c:choose>
							</td>
						</tr>
					</logic:iterate>		
					</tbody>
				</table>
			</logic:notEmpty>	
		</c:if>

	</c:if>
	
	<%-- END USER LISTS --%>


	<%-- START ERROR LOG: Log showing errors that appeared during validation of the release --%>
	
	<bright:refDataList componentName="BatchReleaseErrorManager" transactionManagerName="DBTransactionManager"  methodName="getErrorsForBatchRelease" argumentValue="${batchReleaseForm.id}" id="errors"/>

	<c:if test="${batchReleaseForm.invalid && not empty errors}">
		<br />
		<div class="log">
			<h3><bright:cmsWrite identifier="subhead-error-log" /></h3>
			<ul>
				<logic:iterate name="errors" id="error" indexId="count">
					<li <c:if test="${count% 2 != 0}">class="even"</c:if>><bean:write name="error" property="error" /><c:if test="${error.assetId > 0}"> <a href="viewAsset?id=<c:out value='${error.assetId}' />"><bright:cmsWrite identifier="link-view-asset" filter="false" /></a></c:if></li>
				</logic:iterate>
			</ul>
		</div>
	</c:if>
	
	<%-- END ERROR LOG --%>

	
	<br />
	
	<p>
		<c:choose>
			<c:when test='${assetId > 0}'>
				<c:set var="backURL" value="viewAsset?id=${assetId}" />
			</c:when>
			<c:when test='${messageId > 0}'>
				<c:set var="backURL" value="readMessage?mgroup=${messageGroup}&id=${messageId}" />
			</c:when>
			<c:when test='${search}'>
				<c:set var="backURL" value="batchReleaseSearch?cachedCriteria=1" />
			</c:when>
			<c:when test="${report}">
				<c:set var="backURL" value="viewBatchReleaseAcknowledgementReport" />
			</c:when>
			<c:otherwise>
				<c:set var="backURL" value="viewManageBatchReleases" />
			</c:otherwise>
		</c:choose>

		<a href="<c:out value='${backURL}' />"><bright:cmsWrite identifier="link-back" case="sentence" filter="false" /></a>
		
	


	</p>

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>