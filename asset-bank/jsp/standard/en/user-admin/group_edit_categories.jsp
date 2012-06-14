<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		26-Jan-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="notUsingActiveDirectory" settingName="suspend-ad-authentication"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> Edit Group Categories</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript" src="../js/groups.js"></script>
	<bean:define id="section" value="groupCategories"/>
	
	<logic:equal name="groupForm" property="group.id" value="0">	
		<bean:define id="pagetitle" value="Add Group"/>
	</logic:equal>
	<logic:notEqual name="groupForm" property="group.id" value="0">	
		<bean:define id="pagetitle" value="Edit Group Categories"/>
	</logic:notEqual>
	<script type="text/javascript">
		<!-- 
		/*
		Displays an alert to the user to check they want to set all the permissions to the same.
		*/
		function confirmSetAllEdit()
		{
			if (confirm('Are you sure you want to give this group edit permission to all categories?'))
			{
				setAllCheckbox('catEditSubcategories', true);
			}
		}

		//-->
	</script>
</head>

<body id="adminPage" onload="enableControls(<c:out value='${groupForm.group.id}'/>);" >
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="groupForm">
		<logic:equal name="groupForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="groupForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<html:form styleId="group_edit" action="saveGroupCategoryPermissions" method="post">
		<html:hidden name="groupForm" property="group.isDefaultGroup"/>
		<html:hidden name="groupForm" property="group.id"/>
		<input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
		<input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
		<input type="hidden" name="pageSize" value="<c:out value="${param.pageSize}"/>"/>
		<bean:parameter id="treeId" name="treeId"/>
		<input type="hidden" name="treeId" value="<bean:write name='treeId'/>"/>
		
		<logic:notEmpty name="groupForm" property="categoryList">
		
			<h2>Categories for '<c:out value="${groupForm.group.name}" escapeXml="false" />':</h2>
		
			<p>From the list below you can choose in which categories this group can edit subcategories.</p>
			
			<div class="clearing">&nbsp;</div>
	
			<table cellspacing="0" cellpadding="2" class="permissions" style="width:auto" summary="Table of permissions for current group">
				<tr>
					<th style="text-align: left; vertical-align:top;" rowspan="2"><strong>Category</strong></th>
					<th style="text-align: center; vertical-align:top; width:140px;">Edit Subcategories</th>
				</tr>
				<tr>
					
					<th style="text-align: center;"><a href="#" onclick="confirmSetAllEdit(); return false;">all</a></th>					
				</tr>
				<logic:iterate name="groupForm" property="categoryList.categories" id="category" indexId="index">
					<logic:notEqual name='category' property='depth' value='0'>
						<tr class="rowBorder">
							<td><span class="catPermission<bean:write name='category' property='depth'/>"><bean:write name="category" property="name" /></span>
							</td>
							<td style="text-align: center;">
								<input type="hidden" name="catPermission<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>!<bean:write name='category' property='parentId'/>:0">
								<logic:match name="groupForm" property="editSubcategoriesPermission[${category.id}]" value="true">
									<input type="checkbox" class="checkbox" id="catEditPermission<bean:write name='category' property='id'/>" name="catEditSubcategories<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>" checked="checked">
								</logic:match>
								<logic:notMatch name="groupForm" property="editSubcategoriesPermission[${category.id}]" value="true">
									<input type="checkbox" class="checkbox" id="catEditPermission<bean:write name='category' property='id'/>" name="catEditSubcategories<bean:write name='category' property='id'/>" value="<bean:write name='category' property='id'/>">
								</logic:notMatch>
							</td>
						</tr>
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