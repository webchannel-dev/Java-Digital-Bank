<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		 Steve Bryan    13-Dec-2005     Copy of page for descriptive categories
	 d2      Steve Bryan    09-Jan-2005     Disallow deletion of universal 
	 d3      Ben Browning   14-Feb-2006     HTML/CSS tidy up
	 d5      Steve Bryan    20-Apr-2006     Move edit button to by the entry in the list
	 d7		 Matt Stevenson	22-Nov-2007		Removed returnCode reference
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="catExtensionAssetsEnabled" settingName="category-extension-assets-enabled" />
<bean:parameter id="ouid" name="ouid" value="0" />
<bean:parameter id="parentId" name="categoryId" value="-1" />
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewPermissionCategories?categoryId=${parentId}&ouid=${ouid}"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="access-level-root" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Access Levels</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script language=JavaScript type="text/javascript">

		function confirmDelete()
		{
			return (confirm("This will delete the access level, all its sub-levels, and take all images out of this access level. You will not be able to undo this action. If the access level has an extension asset this will also be removed. Are you sure you want to do this?"));
		}


		$j(function () {
			//give the newCatName field the focus - BB commented out because annoying when you have lots of access levels
			//$j('#newCatName').focus();
			// Enable drag and drop sorting
			initSorting('relocatePermissionCategory');
		});

	</script>


	<c:choose>
		<c:when test="${orgUnitCategoryAdminForm.orgUnit.id > 0}">
			<bean:define id="section" value="orgunits"/>
			<bean:define id="pagetitle" value="Organisational Unit: Access Levels"/>						
		</c:when>
		<c:otherwise>
			<bean:define id="section" value="permcats"/>
			<bean:define id="pagetitle" value="Access Levels"/>			
		</c:otherwise>
	</c:choose>
	
	<style type="text/css">
	<!--
		form.floated label {
			width: 120px;
		}
	-->
	</style>

</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<%-- Rename the form bean --%>
	<bean:define id="categoryAdminForm" name="orgUnitCategoryAdminForm" />
	
	<logic:present  name="categoryAdminForm">
		<logic:equal name="categoryAdminForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="categoryAdminForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<h2>
		<c:choose>
			<c:when test="${orgUnitCategoryAdminForm.orgUnit.id > 0}">
				Organisational unit: <c:out value="${orgUnitCategoryAdminForm.orgUnit.category.name}" />
			</c:when>
			<c:otherwise>
				Browse Access Levels			
			</c:otherwise>
		</c:choose>
	</h2>
	
	<p>You are at:&nbsp;
		<logic:equal name="categoryAdminForm" property="root" value="false">
			<strong>
				<c:choose>
					<c:when test="${userprofile.isAdmin}">
						<a href="viewPermissionCategories?categoryId=-1">Root Level</a>
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose>
				
				<logic:iterate name="categoryAdminForm" property="ancestorCategoryList" id="ancestorCategory"> &raquo;
					<a href="viewPermissionCategories?categoryId=<bean:write name='ancestorCategory' property='id' />&ouid=<c:out value='${ouid}' />"><bean:write name="ancestorCategory" property="name" filter="false"/></a>
				</logic:iterate>
				
				&raquo; <bean:write name="categoryAdminForm" property="categoryName" filter="false"/>
			</strong>
		</logic:equal>
	
		<logic:equal name="categoryAdminForm" property="root" value="true">
			<strong>Root Level</strong>
		</logic:equal>
	</p>
	
	<br />
	
	<logic:empty name='categoryAdminForm' property='categoryName'>
		<bean:define id='currentCategoryName' value="Root"/>
	</logic:empty>
	<logic:notEmpty name='categoryAdminForm' property='categoryName'>
		<bean:define id='currentCategoryName' name='categoryAdminForm' property='categoryName'  type="java.lang.String"/>
	</logic:notEmpty>
	
	<h3><bean:write name='currentCategoryName' filter="false"/> sub-levels:</h3>
	
	<logic:equal name="categoryAdminForm" property="subCategoryListIsEmpty" value="false">
	
		<table cellspacing="0" class="list" summary="Category list" id="<c:out value='${ouid}' />">		
			<thead>					
				<tr>
					<th>Name</th>
					<th>Selected on load?</th>
					<th>Browsable?</th>
					<c:if test="${not categoryAdminForm.topLevel}">
						<th>Own permissions?</th>
					</c:if>
					<th>Always assignable?</th>
					<th>Image</th>
					<th colspan="5">Actions</th>
				</tr>				
			</thead>
			<tbody>		
				<logic:iterate name="categoryAdminForm" property="subCategoryList" id="objCategory">
					<tr class="id<bean:write name='objCategory' property='id' />">
						<td class="sort">
							<bean:write name="objCategory" property="name" filter="false"/>
						</td>
						<td>
							<c:if test="${objCategory.selectedOnLoad}">yes</c:if>
							<c:if test="${not objCategory.selectedOnLoad}">no</c:if>
						</td>
						<td>
							<c:if test="${objCategory.isBrowsable}">yes</c:if>
							<c:if test="${not objCategory.isBrowsable}">no</c:if>
						</td>
						<c:if test="${not categoryAdminForm.topLevel}">
							<td>
								<c:if test="${objCategory.isRestrictive}">yes</c:if>
								<c:if test="${not objCategory.isRestrictive}">no</c:if>
							</td>
						</c:if>
						<td>
							<c:if test="${objCategory.canAssignIfNotLeaf}">yes</c:if>
							<c:if test="${not objCategory.canAssignIfNotLeaf}">no</c:if>
						</td>
						<td>
							<c:if test="${empty objCategory.imageUrl}">no</c:if>
							<c:if test="${not empty objCategory.imageUrl}">yes</c:if>
						</td>
						<c:set var="openAction" value="viewPermissionCategories" />
						<c:set var="moveAction" value="movePermissionCategory" />
						<c:set var="updateAction" value="viewUpdatePermissionCategory" />
						<c:set var="deleteAction" value="viewDeletePermissionCategory" />
						<%@include file="../category/inc_common_admin_actions.jsp"%>
					</tr>
				</logic:iterate>
			</tbody>
		</table>
					
	</logic:equal>
	
	<logic:equal name="categoryAdminForm" property="subCategoryListIsEmpty" value="true">
	
		<p>There are currently no sub-levels for this access level.</p>
	</logic:equal>
	
	<br />
	
	<p>
		<a href="alphabetiseSubCategories?<c:if test='${categoryAdminForm.categoryId > 0}'>id=<bean:write name='categoryAdminForm' property='categoryId'/></c:if><c:if test='${categoryAdminForm.categoryId <= 0}'>root=1</c:if>&categoryTypeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>&ouid=<c:out value='${ouid}' />" onclick="return confirm('Are you sure you want to alphabetise the list of subcategories? Your current ordering will be lost');">Alphabetise access levels &raquo;</a>
	</p>
	<br />
	
	<c:set var="catName" value="access level" />
	<c:set var="addAction" value="addPermissionCategory" />
	<%@include file="../category/inc_add_category_form.jsp"%>
	
	<c:if test="${userprofile.isAdmin}">
		<div class="hr"></div>
		<a href="viewImportAccessLevels">Import access levels from a file &raquo;</a>
	</c:if>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>