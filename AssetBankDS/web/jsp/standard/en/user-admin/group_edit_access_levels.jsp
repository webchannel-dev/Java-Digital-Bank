<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	09-May-2005		Created
	 d2		Matt Stevenson	10-May-2005		Incorporated categories
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Matt Stevenson	19-May-2005		Added javascript for selecting parent categories
	 d5		Matt Stevenson	14-Jun-2005		Modified parent category permission setting
	 d6      Steve Bryan    29-Dec-2005    Updated for org units
	 d7		Ben Browning	21-Feb-2006		HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="notUsingActiveDirectory" settingName="suspend-ad-authentication"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="comments" settingName="comments"/>
<bright:applicationSetting id="ratings" settingName="ratings"/>
<bright:applicationSetting id="hideRestrictedAssetImages" settingName="hide-restricted-asset-images"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Bright Interactive</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript" src="../js/groups.js"></script>
	<bean:define id="section" value="groupAccessLevels"/>
	
	<logic:equal name="groupForm" property="group.id" value="0">	
		<bean:define id="pagetitle" value="Add Group"/>
	</logic:equal>
	<logic:notEqual name="groupForm" property="group.id" value="0">	
		<bean:define id="pagetitle" value="Edit Group"/>
	</logic:notEqual>
	<script type="text/javascript">
		<!-- 
		/*
		Displays an alert to the user to check they want to set all the permissions to the same.
		*/
		function confirmSetAllDownload(a_iPermissionLevel)
		{
			if (confirm('Are you sure you want to set all the access levels to this download permission level?'))
			{
				setAll('catPermission', ':', a_iPermissionLevel, <c:out value='${groupForm.group.id}'/>);
			}
		}

		function confirmSetAllUpload(a_iPermissionLevel)
		{
			if (confirm('Are you sure you want to set all the access levels to this upload permission level?'))
			{
				setAll('catUploadPermission', '', a_iPermissionLevel, <c:out value='${groupForm.group.id}'/>);
			}
		}

		function confirmSetAllDownloadOriginal(a_bChecked)
		{
			if (confirm('Are you sure you want to set all the access levels to allow download original: ' + a_bChecked + '?'))
			{
				setAllCheckbox('catDownloadOriginal', a_bChecked);
			}
		}

		function confirmSetAllDownloadAdvanced(a_bChecked)
		{
			if (confirm('Are you sure you want to set all the access levels to allow advanced download: ' + a_bChecked + '?'))
			{
				setAllCheckbox('catDownloadAdvanced', a_bChecked);
			}
		}

		function confirmSetAllReviewAssets(a_bChecked)
		{
			if (confirm('Are you sure you want to set all the access levels to allow reviewing of assets: ' + a_bChecked + '?'))
			{
				setAllCheckbox('catReviewAssets', a_bChecked);
			}
		}
		
		function confirmSetAllHighResAssets(a_bChecked)
		{
			if (confirm('Are you sure you want to set all the access levels to force high res approval: ' + a_bChecked + '?'))
			{
				setAllCheckbox('catHighResApproval', a_bChecked);
			}
		}

		function confirmSetAllViewRestrictedAssets(a_bChecked)
		{
			if (confirm('Are you sure you want to set all the access levels to allow viewing of restricted assets: ' + a_bChecked + '?'))
			{
				setAllCheckbox('catViewRestrictedAssets', a_bChecked);
			}
		}


		//-->
	</script>
</head>

<body id="adminPage" onload="enableControls(<c:out value='${groupForm.group.id}'/>);" >
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present  name="groupForm">
		<logic:equal name="groupForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="groupForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<html:form styleId="group_edit" action="saveGroupAccessLevelPermissions" method="post">
		<html:hidden name="groupForm" property="group.isDefaultGroup"/>
		<html:hidden name="groupForm" property="group.id"/>
		<input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
		<input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
		<input type="hidden" name="pageSize" value="<c:out value="${param.pageSize}"/>"/>
		<bean:parameter id="treeId" name="treeId"/>
		<input type="hidden" name="treeId" value="<bean:write name='treeId'/>"/>
		
		
		<logic:notEmpty name="groupForm" property="categoryList">
		
			<h2>Access Levels for '<c:out value="${groupForm.group.name}" escapeXml="false" />':</h2>
		
			<p>From the list below you can set the permissions that a member of this group will have to each of the access levels.</p>
			 <c:if test="${groupForm.group.id > 2}">
				<p><strong>Please note:</strong> Members of this Group will also inherit the permissions of the "*Logged in Users" group.</p>
			</c:if>
			
			<div class="permissionKey">
				<p><strong>Download Permission Key:</strong></p>
				<ul>
					<li>0 = None</li>
					<li>1 = View</li>
					<li>2 = Download after approval</li>
					<li>3 = Download</li>
					<li>4 = Approve downloads</li>
				</ul>
			</div>
			<div class="permissionKey">
				<p><strong>Upload Permission Key:</strong></p>
				<ul>
					<li>0 = None</li>
					<li>1 = Upload (requires approval)</li>
					<li>2 = Upload and edit</li>
					<li>3 = Upload, edit and delete</li>
					<li>4 = Approve uploads</li>
				</ul>
			</div>
			<div class="permissionKey">
				<p><strong>Advanced Permission Key:</strong></p>
				<ul>
					<li>1 = Advanced download</li>
					<li>2 = Download originals</li>
					<li>3 = Requires approval for high-res</li>
					<c:if test="${ratings || comments}"><li>4 = Review/rate <bright:cmsWrite identifier="items" filter="false"/></li></c:if>
					<c:if test="${hideRestrictedAssetImages}"><li>5 = View/download restricted <bright:cmsWrite identifier="items" filter="false"/></li></c:if>
				</ul>
			</div>
			<div class="clearing">&nbsp;</div>
			<table id="permissionsTable" cellspacing="0" cellpadding="2" border="0" class="permissions" summary="Table of permissions for current group">
				<tr>
					<th>&nbsp;</th>
					<th colspan="5" style="text-align: center;">Download Permission</th>
					<td>&nbsp;</td>
					<th colspan="5" style="text-align: center;">Upload Permission</th>
					<td>&nbsp;</td>
					<th <c:if test="${ratings || comments}">colspan="4"</c:if><c:if test="${!ratings && !comments}">colspan="3"</c:if> style="text-align: center;" class="newPerm">Advanced Permissions</th>
				</tr>
				<tr>
					<th style="text-align: left; width:230px; line-height:20px;" rowspan="2" class="accessLevelsHeading">
						<strong>Access Level</strong>
						<img id="ajaxLoader" class="ajaxLoader" src="../images/standard/misc/ajax_loader_small.gif" >
					</th>
					<td class="radio">0</td>
					<td class="radio">1</td>
					
					<c:choose>
						<c:when test="${!groupForm.group.isDefaultGroup || ecommerce}">
							<td class="radio">2</td>
						</c:when>
						<c:otherwise>
							<td class="radio"></td>
						</c:otherwise>
					</c:choose>
					
					<td class="radio">3</td>

					<c:choose>
						<c:when test="${!groupForm.group.isDefaultGroup}">
							<td class="radio">4</td>
						</c:when>
						<c:otherwise>
							<td class="radio"></td>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${!groupForm.group.isDefaultGroup}">
							<td class="spacer">&nbsp;</td>
							<td class="radio">0</td>
							<td class="radio">1</td>
							<td class="radio">2</td>
							<td class="radio">3</td>
							<td class="radio">4</td>
						</c:when>
						<c:otherwise>
							<td class="spacer">&nbsp;</td>
							<td class="radio"></td>
							<td class="radio"></td>
							<td class="radio"></td>
							<td class="radio"></td>
							<td class="radio"></td>
						</c:otherwise>
					</c:choose>
					
					<td class="spacer">&nbsp;</td>
					<td class="checkbox">1</td>
					<td class="checkbox">2</td>
					<td class="checkbox">3</td>
					<c:if test="${ratings || comments}">
						<td class="checkbox">4</td>
					</c:if>
					<c:if test="${hideRestrictedAssetImages}">
						<td class="checkbox">5</td>
					</c:if>
				</tr>
				<tr>
					<th class="radio"><a href="#" onclick="confirmSetAllDownload('0'); return false;">all</a></th>
					<th class="radio"><a href="#" onclick="confirmSetAllDownload('1'); return false;">all</a></th>

					<c:choose>
						<c:when test="${!groupForm.group.isDefaultGroup || ecommerce}">
							<th class="radio"><a href="#" onclick="confirmSetAllDownload('5'); return false;">all</a></th>
						</c:when>
						<c:otherwise>
							<td class="radio"></td>
						</c:otherwise>
					</c:choose>

					<th class="radio"><a href="#" onclick="confirmSetAllDownload('2'); return false;">all</a></th>

					<c:choose>
						<c:when test="${!groupForm.group.isDefaultGroup}">
							<th class="radio"><a href="#" onclick="confirmSetAllDownload('7'); return false;">all</a></th>
						</c:when>
						<c:otherwise>
							<td class="radio"></td>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${!groupForm.group.isDefaultGroup}">
							<th class="spacer">&nbsp;</th>
							<th class="radio"><a href="#" onclick="confirmSetAllUpload('0'); return false;">all</a></th>
							<th class="radio"><a href="#" onclick="confirmSetAllUpload('6'); return false;">all</a></th>
							<th class="radio"><a href="#" onclick="confirmSetAllUpload('3'); return false;">all</a></th>
							<th class="radio"><a href="#" onclick="confirmSetAllUpload('4'); return false;">all</a></th>
							<th class="radio"><a href="#" onclick="confirmSetAllUpload('12'); return false;">all</a></th>
						</c:when>
						<c:otherwise>
							<td class="spacer">&nbsp;</td>
							<td class="radio"></td>
							<td class="radio"></td>
							<td class="radio"></td>
							<td class="radio"></td>
							<td class="radio"></td>
						</c:otherwise>
					</c:choose>

					<td class="spacer">&nbsp;</td>
					<th class="radio">
						<a href="#" onclick="confirmSetAllDownloadAdvanced(true); return false;">all</a><strong>/</strong><a href="#" onclick="confirmSetAllDownloadAdvanced(false); return false;">none</a>&nbsp;
					</th>
					<th class="radio">
						<a href="#" onclick="confirmSetAllDownloadOriginal(true); return false;">all</a><strong>/</strong><a href="#" onclick="confirmSetAllDownloadOriginal(false); return false;">none</a>&nbsp;						
					</th>
					
					<th class="radio">
						<a href="#" onclick="confirmSetAllHighResAssets(true); return false;">all</a><strong>/</strong><a href="#" onclick="confirmSetAllHighResAssets(false); return false;">none</a>						
					</th>					
					
					<c:if test="${ratings || comments}">
						<th class="radio">
							<a href="#" onclick="confirmSetAllReviewAssets(true); return false;">all</a><strong>/</strong><a href="#" onclick="confirmSetAllReviewAssets(false); return false;">none</a>						
						</th>
					</c:if>

					<c:if test="${hideRestrictedAssetImages}">
						<th class="radio">
							<a href="#" onclick="confirmSetAllViewRestrictedAssets(true); return false;">all</a><strong>/</strong><a href="#" onclick="confirmSetAllViewRestrictedAssets(false); return false;">none</a>						
						</th>
					</c:if>
					
				</tr>
				
				<logic:iterate name="groupForm" property="categoryList.categories" id="category" indexId="index" scope="request">
					<logic:notEqual name='category' property='depth' value='0'>
						<%@include file="group_access_levels_row.jsp" %>
					</logic:notEqual>
				</logic:iterate>
			</table>
		</logic:notEmpty>
		
		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="listGroups?name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>
		
	
		

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>