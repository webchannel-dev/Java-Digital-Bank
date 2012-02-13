<%@include file="../inc/doctype_html.jsp" %>

<%-- History:
	 d1		Martin Wilson		28-Sep-2005		Created.
	 d2		Ben Browning		17-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%@include file="../inc/set_this_page_url.jsp"%>

<head>
	
	<title><bright:cmsWrite identifier="title-my-uploads" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="my-uploads"/>
</head>

<bright:refDataList componentName="AttributeManager" methodName="getStaticAttribute" argumentValue="dateAdded" id="dateAddedAtt"/>
<bright:applicationSetting id="numDaysShown" settingName="number-days-my-uploads" />


<body id="importPage">


	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-my-uploads" filter="false"/></h1> 

		
	<bean:define id="tabId" value="approved"/>
	<%@include file="inc_my_uploads_tabs.jsp"%>
		

	
	<h3><bright:cmsWrite identifier="subhead-your-approved-items" filter="false"/></h3>
	
	<c:if test="${myUploadsForm.numIncompleteAssets>0 && userprofile.canUpdateAssets}">
		<div class="warning">
			
			<p>
			<c:choose>
				<c:when test="${myUploadsForm.numIncompleteAssets==1}">
					<bright:cmsWrite identifier="snippet-1-live-item-incomplete" filter="false"/>
					[<a href="search?addedByUserId=<c:out value='${userprofile.user.id}' />&completeness=3&sortAttributeId=<c:out value='${dateAddedAtt.id}' />&sortDescending=true"><bright:cmsWrite identifier="link-view-this-item" filter="false"/></a>]
				</c:when>
				<c:otherwise>
					<c:set var="numIncompleteItems"><bean:write name="myUploadsForm" property="numIncompleteAssets"/></c:set>
					<bright:cmsWrite identifier="snippet-x-live-items-incomplete" filter="false" replaceVariables="true" />
					[<a href="search?addedByUserId=<c:out value='${userprofile.user.id}' />&completeness=3&sortAttributeId=<c:out value='${dateAddedAtt.id}' />&sortDescending=true"><bright:cmsWrite identifier="link-view-these-items" filter="false"/></a>]
				</c:otherwise>
			</c:choose>		
					[<a href="createNewBatch?addedByUserId=<c:out value='${userprofile.user.id}' />&completeness=3&finishedUrl=<c:out value="${thisUrl}"/>"><bright:cmsWrite identifier="link-batch-update-lowercase" filter="false"/></a>]
					[<a href="createNewBulkUpdateFromSearch?addedByUserId=<c:out value='${userprofile.user.id}' />&completeness=3&cancelUrl=<c:out value="${thisUrl}"/>"><bright:cmsWrite identifier="link-bulk-update-lowercase" filter="false"/></a>]		
			</p>
		</div>
	</c:if>

	<bean:size id="dayCount" name="myUploadsForm" property="userUploadsForDayList"/>
	<c:if test="${dayCount==0}">
		<bright:cmsWrite identifier="snippet-no-aprroved-items-you-uploaded" filter="false" replaceVariables="true" />
	</c:if>

	<c:if test="${dayCount>0}">
		<bright:cmsWrite identifier="snippet-aprroved-items-you-uploaded" filter="false" replaceVariables="true" />

		<table border="0" cellspacing="0" class="list highlight">
			<thead>
				<tr>
					<th><bright:cmsWrite identifier="subhead-date-of-upload" filter="false"/></th>
					<th><bright:cmsWrite identifier="subhead-no-of-items" filter="false"/></th>
					<th><bright:cmsWrite identifier="label-actions" filter="false"/></th>
				</tr>
			</thead>
		
			<tbody>
				<logic:iterate name="myUploadsForm" property="userUploadsForDayList" id="uploadList">
					<tr>
						<td>
							<bean:write name="uploadList" property="date.displayDate"/> 
						</td>
						<td>
							<bean:write name="uploadList" property="count"/>
						</td>
						<td>
							[<a href="search?addedByUserId=<c:out value='${userprofile.user.id}' />&dateAddedLower=<bean:write name='uploadList' property='date.formDate'/>&dateAddedUpper=<bean:write name='uploadList' property='date.formDate'/>&sortAttributeId=<c:out value='${dateAddedAtt.id}' />&sortDescending=true"><bright:cmsWrite identifier="link-view-items" filter="false"/></a>]
							[<a href="createNewBatch?addedByUserId=<c:out value='${userprofile.user.id}' />&dateAddedLower=<bean:write name='uploadList' property='date.formDate'/>&dateAddedUpper=<bean:write name='uploadList' property='date.formDate'/>&finishedUrl=<c:out value="${thisUrl}"/>"><bright:cmsWrite identifier="link-batch-update-lowercase" filter="false"/></a>]
							[<a href="createNewBulkUpdateFromSearch?addedByUserId=<c:out value='${userprofile.user.id}' />&dateAddedLower=<bean:write name='uploadList' property='date.formDate'/>&dateAddedUpper=<bean:write name='uploadList' property='date.formDate'/>&cancelUrl=<c:out value="${thisUrl}"/>"><bright:cmsWrite identifier="link-bulk-update-lowercase" filter="false"/></a>]
						</td>
					</tr>
				</logic:iterate>
			</tbody>	
		</table>
	</c:if>

	<bright:refDataList componentName="AttributeManager" methodName="getStaticAttribute" argumentValue="dateAdded" id="dateAddedAtt"/>
	<a href="search?addedByUserId=<c:out value='${userprofile.user.id}' />&approvalStatus=3&sortAttributeId=<c:out value='${dateAddedAtt.id}' />&dateAddedUpper=<c:out value='${myUploadsForm.assetsShownFromDate.formDate}' />&sortDescending=true"><bright:cmsWrite identifier="link-view-older-approved-items" filter="false"/></a>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>