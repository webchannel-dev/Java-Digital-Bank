<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		 James Home		19-Jan-2004		Created.
	 d2		 Tamora James	18-May-2005		Tidied presentation to fit with Demo UI design
	 d3      Steve Bryan    13-Dec-2005     Specialised for descriptive categories, which are non restrictive with tree id=2
	 d4      Ben Browning   14-Feb-2006     HTML/CSS tidy up
	 d5      Steve Bryan    20-Apr-2006     Move edit button to by the entry in the list
	 d6      James Home    	26-Jan-2007     Updated to deal with category editing permissions
	 d7		 Matt Stevenson	22-Nov-2007		Removed returnCode reference
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="catExtensionAssetsEnabled" settingName="category-extension-assets-enabled" />

<bean:parameter id="ouid" name="ouid" value="-1" />
<bean:parameter id="parentId" name="categoryId" value="-1" />
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewCategoryAdmin?categoryId=${parentId}&ouid=${ouid}"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="category-root" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Category Admin</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="category"/>
	<bean:define id="pagetitle" value="Categories"/>
	
	<script type="text/JavaScript">
		// once the dom is ready

		$j(function () {
			//give the newCatName field the focus 
			$j('#newCatName').focus();
			// enable drag and drop sorting
			initSorting('relocateCategory');	
		});
	</script>

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

	<logic:present  name="categoryAdminForm">
		<logic:equal name="categoryAdminForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="categoryAdminForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	

	<p><bright:cmsWrite identifier="snippet-currently-editing" filter="false" />&nbsp;
		<logic:equal name="categoryAdminForm" property="root" value="false">
			<strong>
				<a href="viewCategoryAdmin?categoryId=-1">Root Category</a>
				
				<logic:iterate name="categoryAdminForm" property="ancestorCategoryList" id="ancestorCategory"> &raquo;
					<a href="viewCategoryAdmin?categoryId=<bean:write name='ancestorCategory' property='id' />"><bean:write name="ancestorCategory" property="name" filter="false"/></a>
				</logic:iterate>
				
				&raquo; <bean:write name="categoryAdminForm" property="categoryName" filter="false"/>
			</strong>
		</logic:equal>
	
		<logic:equal name="categoryAdminForm" property="root" value="true">
			<strong>Root Category</strong>
		</logic:equal>
	</p>
	
	<br />

	<logic:empty name='categoryAdminForm' property='categoryName'>
		<bean:define id='currentCategoryName' value="Root category"/>
	</logic:empty>
	<logic:notEmpty name='categoryAdminForm' property='categoryName'>
		<bean:define id='currentCategoryName' name='categoryAdminForm' property='categoryName'  type="java.lang.String"/>
	</logic:notEmpty>
	
	<h3><bean:write name='currentCategoryName' filter="false"/> sub-categories:</h3>
	
	<%-- Work out whether the user can edit the subcategories of this category --%>
	<c:set var="canAddSubcategories" value="${userprofile.isAdmin}"/>
	<logic:match name="userprofile" property="canEditSubcategories[${categoryAdminForm.categoryId}]}" value="true">
		<c:set var="canAddSubcategories" value="true"/>
	</logic:match>
	<logic:notEmpty name="categoryAdminForm" property="ancestorCategoryList">
		<logic:iterate name="categoryAdminForm" property="ancestorCategoryList" id="ancestor">
			<logic:match name="userprofile" property="canEditSubcategories[${ancestor.id}]}" value="true">
				<c:set var="canAddSubcategories" value="true"/>
			</logic:match>
		</logic:iterate>
	</logic:notEmpty>
	<logic:equal name="categoryAdminForm" property="subCategoryListIsEmpty" value="false">
	
		<%-- The table id value is read by the initSorting method as an extra parameter --%>
		<table cellspacing="0" class="list" summary="Category list" id="<c:out value='${parentId}'/>">			
			<thead>
				<tr>
					<th class="width150">Name:</th>
					<th colspan="7">Actions:</th>
				</tr>
			</thead>
			<tbody>										
			<logic:iterate name="categoryAdminForm" property="subCategoryList" id="objCategory" indexId="myindex">
				<tr class="id<bean:write name='objCategory' property='id' />">
					<td class="<c:if test="${canAddSubcategories}">sort</c:if>">
						<bean:write name="objCategory" property="name" filter="false"/>
					</td>
					<logic:notEqual name="canAddSubcategories" value="true">
						<td class="action">
							[<a href="viewCategoryAdmin?categoryId=<bean:write name='objCategory' property='id' />"><bright:cmsWrite identifier="link-open" filter="false" /></a>]
						</td>
					</logic:notEqual>
					<logic:equal name="canAddSubcategories" value="true">
						<c:set var="openAction" value="viewCategoryAdmin" />
						<c:set var="moveAction" value="moveCategory" />
						<c:set var="updateAction" value="viewUpdateCategory" />
						<c:set var="deleteAction" value="viewDeleteCategory" />
						<%@include file="../category/inc_common_admin_actions.jsp"%>
					</logic:equal>
					<logic:match name="userprofile" property="canEditSubcategories[${objCategory.id}]}" value="true">
						<logic:equal name="canAddSubcategories" value="false">
							<td class="action">
								(manage subcategories)
							</td>
						</logic:equal>
					</logic:match>
				</tr>
			</logic:iterate>
			</tbody>
		</table>

		
		<logic:equal name="userprofile" property="isAdmin" value="true">
			<a href="alphabetiseSubCategories?<c:if test='${categoryAdminForm.categoryId > 0}'>id=<bean:write name='categoryAdminForm' property='categoryId'/></c:if><c:if test='${categoryAdminForm.categoryId <= 0}'>root=1</c:if>&categoryTypeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>" onclick="return confirm('Are you sure you want to alphabetise the list of subcategories? Your current ordering will be lost');">Alphabetise categories &raquo;</a>
		</logic:equal>
		
		<br /><br/>
					
	</logic:equal>
	
	<logic:equal name="categoryAdminForm" property="subCategoryListIsEmpty" value="true">
	
		<p>There are currently no sub-categories at this level.</p>
		<logic:equal name="categoryAdminForm" property="root" value="true">
			<c:if test="${userprofile.isAdmin}">
			<p>
			<strong>Please note: </strong>if you expected to see categories here, you may be using <a href="../action/viewPermissionCategories">Access Levels</a> only instead of categories.
			</p>
			</c:if>
		</logic:equal>		
	</logic:equal>
	
	<br/>
	
	<logic:equal name="canAddSubcategories" value="true">		
		<c:set var="catName" value="category" />
		<c:set var="addAction" value="addCategory" />
		<%@include file="inc_add_category_form.jsp"%>
	</logic:equal>

	<logic:equal name="userprofile" property="isAdmin" value="true">
		<div class="hr"></div>
		<a href="viewImportCategories">Import categories from a file &raquo;</a>
	</logic:equal>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>