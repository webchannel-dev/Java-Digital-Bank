<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	09-May-2005		Created
	 d2		Matt Stevenson	10-May-2005		Added link to add group
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4    	Ben Browning   21-Feb-2006    HTML/CSS tidy up
	 d5		Matt Stevenson	23-Feb-2006		Added link to edit formats
	 
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<%-- See if usage before download is enabled --%>
<bright:applicationSetting id="showUseDropdownBeforeDownload" settingName="showUseDropdownBeforeDownload"/>
<bright:applicationSetting id="simpleConvertOptionsForDownload" settingName="simpleConvertOptionsForDownload" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Usage Types</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="usage"/>
	<bean:define id="pagetitle" value="Download Options"/>
	<bean:define id="tabId" value="usage"/>
	
</head>

<c:set var="parentId" value="${usageForm.parentUsageType.id}"  />

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	
	
	<h1><bean:write name="pagetitle" filter="false"/></h1>
	<c:if test="${showMaskTab}">
		<%@include file="inc_download_options_tabs.jsp"%>
	</c:if>
	
	<c:if test="${!showUseDropdownBeforeDownload}">
		<p>
			<em>Note: you currently have usage tracking disabled. 
				To enable it, in the ApplicationSettings file set 'showUseDropdownBeforeDownload=true' and restart <bright:cmsWrite identifier="app-name" filter="false"/>.</em>
		</p>
	</c:if>

	<logic:present name="usageForm">
		<logic:equal name="usageForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="usageForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />	
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<h2>Manage Usage Type List</h2>
	
	<p>
		<c:choose>
			<c:when test="${parentId > 0}">
				<h3>
					'<bean:write name="usageForm" property="parentUsageType.description" filter="false"/>' subtypes
					&nbsp;|&nbsp;
					<a href="viewUsageTypes?parentId=<bean:write name='usageForm' property='parentUsageType.parentId' />">back up</a>
				</h3>
			</c:when>
			<c:otherwise>				
				<h3>Top level types</h3>
			</c:otherwise>
		</c:choose>
	</p>	

	<logic:notEmpty name="usageForm" property="usageTypes">
		<table cellspacing="0" class="list highlight" summary="List of Usage Types">
			<thead>
				<tr>
					<th>Name</th>
					<c:if test="${parentId<=0}">
						<th>Media Type</th>
						<th>Image Download Tab</th>
						<th>Details (* = Mandatory)</th>
						<th>Download Original</th>
					</c:if>
					<th colspan="6">Actions</th>
				</tr>		
			</thead>
			<tbody>
			<logic:iterate name="usageForm" property="usageTypes" id="usageType">
				<tr>
					<td><bean:write name="usageType" property="description"/></td>
					<c:if test="${parentId<=0}">
						<td>
							<c:choose>
								<c:when test="${usageType.assetTypeId==0}">
									All
								</c:when>
								<c:otherwise>
									<bean:write name="usageType" property="assetTypeDescription"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${usageType.assetTypeId==0 || usageType.assetTypeId==2}">
									<c:choose>
										<c:when test="${usageType.downloadTabId==1}">
											simple only
										</c:when>
										<c:when test="${usageType.downloadTabId==2}">
											advanced only
										</c:when>
										<c:when test="${usageType.downloadTabId==3}">
											simple &amp; advanced
										</c:when>
									</c:choose>											
								</c:when>
								<c:otherwise>
									-
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${usageType.canEnterDetails}">
									Yes
									<c:if test="${usageType.detailsMandatory}"><span class="required">*</span></c:if>
								</c:when>
								<c:otherwise>
									No
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${usageType.downloadOriginal}">
									Yes
								</c:when>
								<c:otherwise>
									No
								</c:otherwise>
							</c:choose>
						</td>
					</c:if>
					<logic:equal name="usageType" property="editable" value="true">
						<td class="action">
							[<a href="viewUsageTypes?parentId=<bean:write name='usageType' property='id'/>">open</a>]
						</td>				
						<td class="action">
							[<a href="moveUsageType?id=<bean:write name='usageType' property='id'/>&amp;parentId=<bean:write name='parentId' />&amp;up=true">up</a>]
						</td>
						<td class="action">
							[<a href="moveUsageType?id=<bean:write name='usageType' property='id'/>&amp;parentId=<bean:write name='parentId' />&amp;up=false">down</a>]
						</td>
						<td class="action">
							[<a href="viewUpdateUsageType?id=<bean:write name='usageType' property='id'/>&amp;parentId=<bean:write name='parentId' />">edit</a>]
						</td>				
						<c:if test="${simpleConvertOptionsForDownload}">
						<td class="action">
							[<a href="viewUsageTypeFormats?id=<bean:write name='usageType' property='id'/>&amp;parentId=<bean:write name='parentId' />">edit&nbsp;sizes</a>]
						</td>
						</c:if>
						<td>
							[<a href="deleteUsageType?id=<bean:write name='usageType' property='id'/>&amp;parentId=<bean:write name='parentId' />" onclick="return confirm('Are you sure you want to delete this usage type from the list?');" title="Delete this usage type">X</a>]
						</td>
					</logic:equal>
					<logic:notEqual name="usageType" property="editable" value="true">
						<td class="action disabled">
							[open]
						</td>				
						<td class="action disabled">
							[up]
						</td>
						<td class="action disabled">
							[down]
						</td>
						<td class="action disabled">
							[edit]
						</td>
						<c:if test="${simpleConvertOptionsForDownload}">
						<td class="action disabled">
							[edit&nbsp;sizes]
						</td>
						</c:if>
						<td class="disabled">
							[X]
						</td>
					</logic:notEqual>
				</tr>
			</logic:iterate>
		
			</tbody>
		</table>		
	</logic:notEmpty>
	
	<logic:empty name="usageForm" property="usageTypes">
		<p>There are currently no usage types at this level.</p>
	</logic:empty>
	

					
	
	<logic:equal name="usageForm" property="usageType.id" value="0">
		<form action="../action/addUsageType" method="post" style="padding:1em 0">
			<h3>Add new usage type:</h3>
			<input type="hidden" name="parentId" value="<bean:write name='parentId' />" />
			<html:hidden name="usageForm" property="parentUsageType.id"/>
			
			<%@include file="_usage_admin_form_fields.jsp"%>
			
			<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-add" filter="false" />" />
		</form>
	</logic:equal>
	<logic:greaterThan name="usageForm" property="usageType.id" value="0">
		<form action="../action/updateUsageType" method="post" style="padding:1em 0">
			<h3>Edit usage type:</h3>
			<html:hidden name="usageForm" property="usageType.id"/>
			<input type="hidden" name="parentId" value="<bean:write name='parentId' />" />
			<html:hidden name="usageForm" property="parentUsageType.id" />
	
			<%@include file="_usage_admin_form_fields.jsp"%>
				
			<div style="text-align:left; ">			
				<html:submit property="b_save" styleClass="button flush" ><bright:cmsWrite identifier="button-save" filter="false" /></html:submit>
				<html:submit property="b_cancel" styleClass="button" ><bright:cmsWrite identifier="button-cancel" filter="false" /></html:submit>
			</div>
			
		</form>
	</logic:greaterThan>
	
	<div class="hr"></div>
	
	<a href="../action/viewUsageTypeFormats?id=0">Change default usage formats...</a>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>