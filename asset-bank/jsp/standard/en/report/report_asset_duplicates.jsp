<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		02-Nov-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>




<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Report"/>

<bright:applicationSetting id="duplicate-asset-size-threshold" settingName="duplicate-asset-size-threshold"/>
<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Asset Reports"/>
	<c:set var="reportForm" value="${assetReportForm}" />
	<script language="Javascript">
function setChoice(ctrl,id)
{
	var ctrlDel = document.getElementById('delete_id'+id);
	var ctrlDup = document.getElementById('notDuplicate_id'+id);

	if(ctrl == ctrlDel && ctrlDel.checked)
	{
		ctrlDup.checked = false;
	}
	else if(ctrl == ctrlDup && ctrlDup.checked)
	{
		ctrlDel.checked = false;
	}
}
	</script>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	
	<div class="head">
		<%@include file="back_to_reports.jsp"%>
	</div>
	
	<h2>Potentially Duplicate Assets</h2>

	<bean:size id="size" name="assetReportForm" property="details" />
	
	<logic:empty name="assetReportForm" property="details">
		<p>This report compares file sizes on assets in <bright:cmsWrite identifier="app-name" filter="false"/> to determine potential duplicates.
		<br />It includes a filename comparison for images under <bean:write name="duplicate-asset-size-threshold"/> bytes in size (this can be controlled using the 'duplicate-asset-size-threshold' setting in ApplicationSettings.properties).</p>
		
		<strong>There are no potentially duplicate assets in the system.</strong>
		<br />
		<div class="hr"></div>
	</logic:empty>
	
	<logic:notEmpty name="assetReportForm" property="details">
		<p>This report compares file sizes on assets in <bright:cmsWrite identifier="app-name" filter="false"/> to determine potential duplicates.
		<br />It includes a filename comparison for images under <bean:write name="duplicate-asset-size-threshold"/> bytes in size (this can be controlled using the 'duplicate-asset-size-threshold' setting in ApplicationSettings.properties).</p>
		
		<p>You can select duplicate assets to delete from the system, or mark assets that are not duplicates, by checking the boxes on the right hand side and clicking the 'Update' button at the bottom of the page.</p>
		<p>This report has a limit to the number of assets that can be returned. After deleting the duplicate assets shown on this page you will be shown the next batch of duplicate assets if there are any more.</p>
		<br />
		
		<bright:applicationSetting settingName="batch-releases-enabled" id="batchReleasesEnabled" />
		<form action="deleteDuplicateAssets" method="post">
		<table cellspacing="0" class="report" summary="Duplicate Asset Report">		
			<tr class="underline">
				<th>Thumbnail</th>
				<%-- th>
					<bright:cmsWrite identifier="item" filter="false" case="mixed" /> Title
				</th --%>
				<th>
					Filename
				</th>
				<th>
					Date Uploaded
				</th>
				<th>
					Added By
				</th>
				<th>
					File Size (bytes)
				</th>
				<c:if test="${!batchReleasesEnabled}">
				<th>
					Delete?
				</th>
				</c:if>
				<th>
					Not Duplicate?
				</th>
			</tr>

			<logic:iterate name="assetReportForm" property="details" id="group">
				<logic:iterate name="group" id="asset">
				<tr>
					<td style="padding:5px 5px 5px 0;"><a href="viewAsset?id=<bean:write name='asset' property='id'/>"><img src="../servlet/display?file=<bean:write name='asset' property='displayThumbnailImageFile.path'/>" alt="<c:out value='${asset.searchName}'/>" style="border: 1px solid #333"/></a></td>
					<td>
						<a href="viewAsset?id=<bean:write name='asset' property='id'/>"><bean:write name="asset" property="fileName" /></a><c:if test="${asset.extendsCategory != null && asset.extendsCategory.id > 0}"> [Category Extension Asset]</c:if>
					</td>
					<td>
						<fmt:formatDate value="${asset.dateAdded}" pattern="${dateFormat}" />
					</td>
					<td>
						<bean:write name="asset" property="addedByUser.username" />
					</td>
					<td>
						<c:choose>
							<c:when test="${asset.fileSizeInBytes <= 0}">
								n/a
							</c:when>
							<c:otherwise>
								<bean:write name="asset" property="fileSizeInBytes"/>
							</c:otherwise>
						</c:choose>
					</td>
					<c:if test="${!batchReleasesEnabled}">
					<td>
						<input type="hidden" name="dupe_id<bean:write name='asset' property='id'/>" value="<bean:write name='asset' property='id'/>"/>
						<input type="checkbox" id="delete_id<bean:write name='asset' property='id'/>" name="id" value="<bean:write name='asset' property='id'/>" onclick="setChoice(this,<bean:write name='asset' property='id'/>);"/>
					</td>
					</c:if>
					<td>
						<input type="checkbox" id="notDuplicate_id<bean:write name='asset' property='id'/>" name="nonDuplicate_id<bean:write name='asset' property='id'/>" value="<bean:write name='asset' property='id'/>" onclick="setChoice(this,<bean:write name='asset' property='id'/>);" <c:if test='${asset.notDuplicate}'>checked="checked"</c:if>/>
					</td>
				</tr>
				</logic:iterate>
				<tr class="rule">
					<td colspan="8">
						&nbsp;	
					</td>
				</tr>

			</logic:iterate>
		</table>
		<span style="float: right;"><input type="submit" name="delete" value="Update &raquo;" class="button" onclick="return(confirm('Are you sure you want to delete or update these assets?'));"/></span>
		</form>
	</logic:notEmpty>
	
	<p><%@include file="back_to_reports.jsp"%></p>
	
	<div class="hr"></div>	

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>