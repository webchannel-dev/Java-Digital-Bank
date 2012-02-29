<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		14-Sep-2005		Created.
	d2		Tamora James	03-Oct-2005		Added to demo.
	d3    Steve Bryan    15-Nov-2005    Made approval field mandatory by adding a mand field if you want
	d4    Ben Browning   14-Feb-2006    HTML/CSS tidy up
	d5    Steve Bryan    19-May-2006    Changed to get usage type list from form.
	d6		Ben Browning	27-Apr-2010		jQuery switchover
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="showUseDropdown" settingName="showUseDropdownBeforeDownload"/>
<bright:applicationSetting id="showUseDropDownConversionOnly" settingName="showUseDropDownConversionOnly"/>


<bean:define id="usageTypes" name="requestApprovalForm" property="usageTypeList" />


<head>
	
	<title><bright:cmsWrite identifier="title-request-approval" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>
	<bean:define id="helpsection" value="request_approval_lightbox"/>
	
	<script type="text/javascript" src="../js/group-edit.js"></script>
	<script type="text/JavaScript">
		$j(function() {
			assetApproval.init();
		});
	</script>
</head>

<body id="approvalPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline">
		<c:choose>
			<c:when test="${ecommerce}">
				<bright:cmsWrite identifier="e-ecommerce-offline-option-name" filter="false"/> Purchase
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="heading-request-approval" filter="false" />
			</c:otherwise>
		</c:choose>
	</h1> 

	<logic:empty  name="requestApprovalForm" property="approvalList">
		<p>You currently have no items requiring approval.</p>	
		<p><a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-lightbox" filter="false" /></a></p>
	</logic:empty>
	
<logic:notEmpty name="requestApprovalForm" property="approvalList">
	<c:set var="repopulate" value="false" />
	<logic:present name="requestApprovalForm">
	<logic:equal name="requestApprovalForm" property="hasErrors" value="true"> 
		<c:set var="repopulate" value="true" />
		<div class="error">
			<logic:iterate name="requestApprovalForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	</logic:present>
	
	<form action="../action/saveRequestApproval" method="post" name="requestApprovalForm" class="floated">
		
		<logic:equal name="requestApprovalForm" property="isAppeal" value="true">
			<input type="hidden" name="appeal" value="appeal" />
		</logic:equal>
		
		<html:hidden name="requestApprovalForm" property="isHighResRequest"/>	
		
		
		<bright:cmsWrite identifier="intro-request-approval" filter="false"/>
		
		<c:if test="${requestApprovalForm.isHighResRequest}">
			<p><bright:cmsWrite identifier="intro-request-high-res" filter="false" replaceVariables="true" /></p>
			
		</c:if>
		

		
		<ul class="assetList" id="assetsForApproval">
		
		<bean:size id="approvalListSize" name="requestApprovalForm" property="approvalList"/>

		<logic:iterate name="requestApprovalForm" property="approvalList" id="assetapproval" indexId="index">
			<bean:define id="asset" name="assetapproval" property="asset" />
			<bean:define id="assetid" name="asset" property="id" />
			
			<input type="hidden" name="id" value="<c:out value="${assetid}" />" />
	
			<%-- Get the parameters out (painfully) --%>
			<c:set var="request_include" value=''/>
			<c:set var="request_usernotes" value=''/>
			<c:set var="request_usageTypeId" value=''/>  
			<c:forEach var='parameter' items='${paramValues}'>
				<c:set var="key" value="include_${assetid}" /> 
				<c:if test="${parameter.key == key}">
					<c:forEach var='value' items='${parameter.value}'>
						<c:set var="request_include" value='${value}'/>   
					</c:forEach>
				</c:if>  
				<c:set var="key" value="usernotes_${assetid}" /> 
				<c:if test="${parameter.key == key}">
					<c:forEach var='value' items='${parameter.value}'>
						<c:set var="request_usernotes" value='${value}'/>   
					</c:forEach>
				</c:if>  
				<c:set var="key" value="usageTypeId_${assetid}" /> 
				<c:if test="${parameter.key == key}">
					<c:forEach var='value' items='${parameter.value}'>
						<c:set var="request_usageTypeId" value='${value}'/>   
					</c:forEach>
				</c:if>  
			</c:forEach>
			<%-- <c:out value="include_${assetid}:${request_include} usernotes_${assetid}:${request_usernotes} usageTypeId_${assetid}:${request_usageTypeId}" /> --%>
	
			
			<li <c:if test="${index==0}">class="first"</c:if>>	
				<c:choose>
					<c:when test="${asset.isImage || asset.isVideo}">
						<c:set var="resultImgClass" value="image"/>
					</c:when>
					<c:otherwise>
						<c:set var="resultImgClass" value="icon"/>
					</c:otherwise>
				</c:choose>				
				
				<table cellspacing="0">
					<tr>
						<td class="image">
							<a href="../action/viewAssetInPopup?id=<bean:write name='asset' property='id' />" target="_blank">
								<bean:define id="disablePreview" value="true"/>	
								<%@include file="../inc/view_thumbnail.jsp"%>
							</a>
						</td>
						
						<td>
							<h3>
								<a href="viewAsset?id=<bean:write name='asset' property='id' />">
									<bean:write name="asset" property="id"/>: <bean:write name="asset" property="name"/>
								</a>
							</h3>
					
							<label for="include_<bean:write name='asset' property='id'/>" class="">
					 			Include? &nbsp;
							</label> 
							<input type="checkbox" name="include_<bean:write name='asset' property='id'/>" 
									<logic:equal name='repopulate' value='false'>checked="checked"</logic:equal>
									<logic:equal name='repopulate' value='true'><logic:notEmpty name='request_include'>checked="checked"</logic:notEmpty></logic:equal> id="include_<bean:write name='asset' property='id'/>" class="checkbox include_checkbox" />
							<br />
					
							<logic:notEmpty name="assetapproval" property="adminNotes">	
					
							
								<label><bright:cmsWrite identifier="label-message-from-admin" filter="false"/></label>
							
								<div class="holder">
									<bean:write name="assetapproval" property="adminNotes"/>
								</div>
								<br />
					
							</logic:notEmpty>
		
							<c:if test="${showUseDropdown && !showUseDropDownConversionOnly}">
				
						
								<label for="usageTypeId_<bean:write name='asset' property='id'/>"><bright:cmsWrite identifier="label-intended-use" filter="false"/> <span class="required">*</span></label>
								<select class="ctrlUsage group_edit" name="usageTypeId_<bean:write name='asset' property='id'/>" id="usageTypeId_<bean:write name='asset' property='id'/>">
									<option value="0">- Select -</option>
									<logic:iterate name="usageTypes" id="usageType">
										<c:if test="${(requestApprovalForm.isHighResRequest && usageType.highResolution) || !requestApprovalForm.isHighResRequest}">
											<option value="<bean:write name='usageType' property='id'/>"
												<logic:equal name='repopulate' value='true'>
													<c:if test='${request_usageTypeId == usageType.id}'>selected</c:if>										
												</logic:equal>									
											><bean:write name="usageType" property="description"/>
											</option>
										</c:if>
									</logic:iterate>
								</select>							
								<br />
						
								<%-- Remove this if you do not want the usage field to be mandatory --%>
								<input type="hidden" name="mandatory_usageTypeId_<bean:write name='asset' property='id'/>" value="Please enter Intended Use for each image." />
			
							</c:if>
						
						
							<label for="usernotes_<bean:write name='asset' property='id'/>"><bright:cmsWrite identifier="label-your-notes" filter="false"/></label>
								
							<logic:equal name='repopulate' value='false'><c:set var='textvalue' value='${assetapproval.userNotes}'/></logic:equal>
							<logic:equal name='repopulate' value='true'><c:set var='textvalue' value='${request_usernotes}'/></logic:equal>						
							<input type="hidden" name="maxlength_usernotes_<bean:write name='asset' property='id'/>" value="4000">
							<input type="hidden" name="maxlengthmessage_usernotes_<bean:write name='asset' property='id'/>" value="The notes field has exceeded its maximum field size of 4000 characters">
							<textarea name="usernotes_<bean:write name='asset' property='id'/>" id="usernotes_<bean:write name='asset' property='id'/>" class="admin group_edit" rows="4" cols="35"><c:out value="${textvalue}" /></textarea>		
							<br />
						
						</td>
						
						<td <c:if test="${approvalListSize gt 1}">class="copyAction"</c:if>>	
							<%-- If on the first item and there are more then one then show 'copy to all' link --%>
							<c:if test="${index==0}">
								<c:if test="${approvalListSize gt 1}">
										<a href="#" id="copyValuesLink">Copy these values to all items &raquo;</a>
								</c:if>	
							</c:if>	
							&nbsp;
						</td>
		
					</tr>
				</table>	
				
				
	
			</li>		
		</logic:iterate>
	
		</ul>
	

			<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-submit" filter="false" />" /> 
			<a href="viewAssetBox" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</form>
	
	
	
</logic:notEmpty>
				
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>