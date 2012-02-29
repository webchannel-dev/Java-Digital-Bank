<%@include file="../inc/doctype_html.jsp" %>


<%-- History:
	d1		Martin Wilson	04-Oct-2005		Created
	d2		Matt Stevenson	04-Jan-2006		Added move to universal link
	d3 	Ben Browning	17-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>



<head>
	
	<title><bright:cmsWrite identifier="title-approve-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="asset-approval"/>
	<bean:define id="tabId" value="approvedownloads"/>
	
</head>
<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-approve-items" filter="false" /></h1> 

	<%@include file="inc_approval_tabs.jsp"%>

<%--  
	<h2><bright:cmsWrite identifier="subhead-approve-uploaded-items" filter="false" /></h2>

	<c:set var="bMore" value="false"/>
	<c:set var="bHasBulkUpdateController" value="false"/>
	<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.type == 'BATCHUPDATE'}">
		<c:if test="${userprofile.batchUpdateController.hasNext}">
			<c:set var="bMore" value="true"/>
		</c:if>
	</c:if>	
	<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.type == 'BULKUPDATE'}">
		<c:set var="bHasBulkUpdateController" value="true"/>
	</c:if>	

		
		<logic:equal name="approvalListForm" property="unapprovedUploadedAssetsCount" value="0">
			<bright:cmsWrite identifier="intro-approve-items-none" filter="false" />
		</logic:equal>
		<logic:notEqual name="approvalListForm" property="unapprovedUploadedAssetsCount" value="0">
			<c:set var="uploadedAssets" value="${approvalListForm.unapprovedUploadedAssetsCount}" />	
			<bright:cmsWrite identifier="intro-approve-items" filter="false" replaceVariables="true" />
			<logic:notEqual name="bMore" value="true">
				<bright:cmsWrite identifier="snippet-not-approve-all" filter="false"/>
			</logic:notEqual>
			<logic:equal name="bMore" value="true">
				<bright:cmsWrite identifier="snippet-note-cancel-batch" filter="false"/>
			</logic:equal>
		</logic:notEqual>
--%>

	
	<logic:equal name="ecommerce" value="false">
		<h2><bright:cmsWrite identifier="subhead-approve-items-download" filter="false" /></h2>
		
		<logic:empty name="approvalListForm" property="usersWithApprovalLists">
			<bright:cmsWrite identifier="snippet-no-items-approval" filter="false" replaceVariables="true" />
		</logic:empty>
		<logic:notEmpty name="approvalListForm" property="usersWithApprovalLists">
		
			<bright:cmsWrite identifier="snippet-users-items-approval" filter="false" />
			<table cellspacing="0" class="list highlight" summary="List of users">
				<thead>		
					<tr>
						<th><bright:cmsWrite identifier="label-date" filter="false"/></th>
						<th><bright:cmsWrite identifier="label-name" filter="false"/></th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
				<logic:iterate name="approvalListForm" property="usersWithApprovalLists" id="record">
					<tr>
						<td>
							<bean:write name="record" property="dateOfOldestApprovalRequest.displayDate" />
						</td>
						<td>
							<bean:write name="record" property="name" />
						</td>
						<td class="action">
							[<a href="viewApproveAssets?userId=<bean:write name='record' property='id'/>"><bright:cmsWrite identifier="link-process-request" filter="false"/></a>]
						</td>														
					</tr>
				</logic:iterate>	
				</tbody>		
			</table>
		</logic:notEmpty>
	</logic:equal>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>