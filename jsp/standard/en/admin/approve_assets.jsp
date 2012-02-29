<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Tamora James	03-Oct-2005		Added to demo
    d2      Ben Browning   14-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewApproveAssets?userId=${assetApprovalForm.user.id}"/>
<c:set scope="session" var="imageDetailReturnName" value="Approve Assets"/>

<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<bean:define id="usageTypes" name="assetApprovalForm" property="usageTypeList" />

<head>
	<title><bright:cmsWrite identifier="title-approve-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="asset-approval"/>
	<script type="text/javascript" src="../js/calendar.js"></script>
	<script type="text/javascript" src="../js/group-edit.js"></script>
	
	<script type="text/JavaScript">
		$j(function() {
			assetApproval.init();
		});
	</script>
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-approve-items" filter="false" /></h1> 
	<c:set var="fullName" value="${assetApprovalForm.user.fullName}" />	
	<bright:cmsWrite identifier="intro-approve-items-p2" filter="false" replaceVariables="true" />
	
	
	<bean:size id="approvalListSize" name="assetApprovalForm" property="approvalList"/>
	
	<logic:equal name="assetApprovalForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="assetApprovalForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="saveUserAssetApprovalStatus" method="post" styleClass="floated">
		<input type="hidden" name="userId" value="<bean:write name='assetApprovalForm' property='user.id' />" />


		<ul class="assetList" id="assetsForApproval">
		<logic:iterate name="assetApprovalForm" property="approvalList" id="assetapproval" indexId="index">
			<bean:define id="asset" name="assetapproval" property="asset" />

			<c:if test="${asset.typeId!=2}">
				<c:set var="resultImgClass" value="icon"/>
			</c:if>
			<c:if test="${asset.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if>
			<li class="selected <c:if test="${index==0}">first</c:if>" >
				<table cellspacing="0">
					<tr>
						<td class="image">
							<a href="../action/viewAsset?id=<bean:write name='asset' property='id' />" >
									<bean:define id="disablePreview" value="true"/>	
									<%@include file="../inc/view_thumbnail.jsp"%>
							</a>
							<a href="../action/viewAsset?id=<bean:write name='asset' property='id' />" >View this item&raquo;</a>
						</td>	
						<td>

							<label for="approvalstatus_<bean:write name='asset' property='id'/>">Action:</label>
							<select class="action group_edit" name="approvalstatus_<bean:write name='asset' property='id'/>" id="approvalstatus_<bean:write name='asset' property='id'/>" style="width:100px;">
								<option value='3'>Reject</option>
								<option value='1' selected>Skip</option>
						
								<c:choose>
									<c:when test="${assetapproval.requiresHighResApproval}">
										<option value='4'>Approve</option>
									</c:when>
									<c:otherwise>
										<option value='2'>Approve</option>
									</c:otherwise>
								</c:choose>
							</select>
							<br />
							
							<label>Approval type:</label>
							<c:choose>	
								<c:when test="${assetapproval.requiresHighResApproval}">
									<div class="holder">High resolution download request</div><br />
								</c:when>
								<c:otherwise>
									<div class="holder">Standard download request</div><br />
								</c:otherwise>
							</c:choose>

					
							<label><bright:cmsWrite identifier="label-intended-usage" filter="false"/></label>
							<div class="holder">
							<logic:iterate name="usageTypes" id="usageType">
								<c:if test="${usageType.id == assetapproval.usageTypeId}">
									<bean:write name="usageType" property="description"/>
								</c:if>
							</logic:iterate>
							</div>
							<br />
					
					
							
							<logic:notEmpty name="assetapproval" property="userNotes">
								<!-- Users notes -->
								<label><bright:cmsWrite identifier="label-message-from-user" filter="false" /></label>
								<div class="holder"><bean:write name="assetapproval" property="userNotes" /></div>
								<br />
							</logic:notEmpty>
							
							<label for="adminnotes_<bean:write name='asset' property='id'/>"><bright:cmsWrite identifier="label-message-to-user" filter="false"/></label>			
							<input type="hidden" name="maxlength_adminnotes_<bean:write name='asset' property='id'/>" value="4000">
							<input type="hidden" name="maxlengthmessage_adminnotes_<bean:write name='asset' property='id'/>" value="The value for admin notes has exceeded its 4000 character maximum length">
							<textarea name="adminnotes_<bean:write name='asset' property='id'/>" id="adminnotes_<bean:write name='asset' property='id'/>" class="admin group_edit" rows="4" cols="35"><bean:write name='assetapproval' property='adminNotes'/></textarea>		
							<br />			
							
						
							<label for="approvalexpiry_<bean:write name='asset' property='id'/>"><bright:cmsWrite identifier="label-approval-expires" filter="false"/></label>
							<input name="approvalexpiry_<bean:write name='asset' property='id'/>" id="approvalexpiry_<bean:write name='asset' property='id'/>" maxlength="10" value="<bean:write name='assetapproval' property='dateExpires.formDate'/>" class="text small date" /> 
							<a href="#" title="Date selector popup"><img class="icon js-enabled-show" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementsByName('approvalexpiry_<bean:write name="asset" property="id"/>')[0], <bean:write name='daysBeforeMonths' />); return false;" width="16" height="15" style="padding-right: 0;" /></a>							
		
							<span><em><bright:cmsWrite identifier="snippet-blank-30-days" filter="false"/></em></span>	
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
	
		

				</table>
			</li>
		</logic:iterate>

		</ul>
		
		
			<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-submit" filter="false" />" /> 
			<a href="viewAssetApproval" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>
	
	

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>