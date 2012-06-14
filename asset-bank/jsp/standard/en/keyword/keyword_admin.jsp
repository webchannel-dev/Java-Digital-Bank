<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		20-Apr-2004		Created.
	 d2		Matt Stevenson	07-Mar-2007		Modified to deal with multiple trees
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="useKeywordSearchChooser" settingName="keyword-search-chooser" />

<bean:parameter id="filter" name="filter" value="a" />
<bean:parameter id="attributeId" name="attributeId" value="0"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Category Admin</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Keywords"/>
	
	<style type="text/css">
	<!--
		table.keywordNav { width:400px; }
	-->
	</style>
	
	<script type="text/javascript">
		<!--
			// give the newCatName field the focus once the dom is ready
			$j(function () {
  				$j('#newCatName').focus();
 			});		
		-->
	</script>
	
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="categoryAdminForm">
		<logic:equal name="categoryAdminForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="categoryAdminForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<p><bright:cmsWrite identifier="snippet-currently-editing" filter="false" />&nbsp;
		<logic:equal name="categoryAdminForm" property="root" value="false">
			<strong>
				<a href="viewKeywordAdmin?categoryId=-1&categoryTypeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>">Root Category</a>
				
				<logic:iterate name="categoryAdminForm" property="ancestorCategoryList" id="ancestorCategory"> &raquo;
					<a href="viewKeywordAdmin?categoryId=<bean:write name='ancestorCategory' property='id' />&categoryTypeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>"><bean:write name="ancestorCategory" property="name" /></a>
				</logic:iterate>
				
				&raquo; <bean:write name="categoryAdminForm" property="categoryName" />
			</strong>
		</logic:equal>
	
		<logic:equal name="categoryAdminForm" property="root" value="true">
			<strong>Root</strong>
		</logic:equal>
	</p>
	
	<div class="hr"></div>
	
	<logic:empty name='categoryAdminForm' property='categoryName'>
		<bean:define id='currentCategoryName' value="Root"/>
	</logic:empty>
	<logic:notEmpty name='categoryAdminForm' property='categoryName'>
		<bean:define id='currentCategoryName' name='categoryAdminForm' property='categoryName'  type="java.lang.String"/>
	</logic:notEmpty>
	
	<h3>Add new keyword to <em><bean:write name='currentCategoryName' /></em>:</h3>
	
	<html:form action="addKeyword" method="post" >	
		<html:hidden name="categoryAdminForm" property="categoryId"/>
		<html:hidden name="categoryAdminForm" property="categoryTreeId"/>

		<table class="form" cellspacing="0" cellpadding="0">
			<tr>
				<th style="width:100px;"><label for="newCatName"><bright:cmsWrite identifier="label-new-keyword" filter="false" /></label></th>
				<td>
					<html:text styleClass="text" name="categoryAdminForm" property="newCategory.name" value="" maxlength="150" size="32" styleId="newCatName" />
				</td>
			</tr>
			<logic:notEmpty name="categoryAdminForm" property="newCategory.translations">
				<logic:iterate name="categoryAdminForm" property="newCategory.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="newCatName<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
							</th>
							<td>
								<html:hidden name="categoryAdminForm" property="newCategory.translations[${tIndex}].language.id"/>
								<html:hidden name="categoryAdminForm" property="newCategory.translations[${tIndex}].language.name"/>
								<html:text styleClass="text" name="categoryAdminForm" property="newCategory.translations[${tIndex}].name" value="" maxlength="150" size="32" styleId="newCatName${tIndex}" />
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>	
			</logic:notEmpty>
			<tr>
				<th><label for="synonyms"><bright:cmsWrite identifier="label-synonyms" filter="false" /></label></th>
				<td>
					<html:textarea styleClass="text" styleId="synonyms" name="categoryAdminForm" property="newCategory.description" rows="4" style="width: 200px;"/>
				</td>
			</tr>
			<logic:notEmpty name="categoryAdminForm" property="newCategory.translations">
				<logic:iterate name="categoryAdminForm" property="newCategory.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="synonyms<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
							</th>
							<td>
								<html:hidden name="categoryAdminForm" property="newCategory.translations[${tIndex}].language.id"/>
								<html:hidden name="categoryAdminForm" property="newCategory.translations[${tIndex}].language.name"/>
								<html:textarea styleClass="text" name="categoryAdminForm" property="newCategory.translations[${tIndex}].description" rows="4" style="width: 200px;" styleId="synonyms${tIndex}" />
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>	
			</logic:notEmpty>
			<tr>
				<th><label for="isBrowsable">Searchable keyword?:</label></th>
				<td>
					<html:checkbox styleClass="checkbox" styleId="isBrowsable" name="categoryAdminForm" property="newCategory.isBrowsable" />
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<input type="submit" name="addCategory" value="<bright:cmsWrite identifier="button-add" filter="false" />" class="button flush" />
				</td>
			</tr>
		</table>
	
	</html:form>
	
	<br/>
	
	<a href="viewImportKeywords?treeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>&attributeId=<bean:write name='attributeId'/>">Import keywords from a file &raquo;</a>

	<div class="hr"></div>

	<c:set var="sUrl" value="viewKeywordAdmin?categoryId=${categoryAdminForm.categoryId}&categoryTypeId=${categoryAdminForm.categoryTreeId}&filter=" />
	
	<c:if test="${userprofile.currentLanguage.usesLatinAlphabet}">
		<c:set var="showAllTabKeywordPicker" value="true" />
		<%@include file="inc_a_to_z.jsp"%>
		<br />
	</c:if>

	<h3><em><bean:write name='currentCategoryName' /></em> keywords:</h3>
	
	<logic:equal name="categoryAdminForm" property="subCategoryListIsEmpty" value="false">
	
		<table cellspacing="0" class="admin list" summary="Keyword list">													
			<logic:iterate name="categoryAdminForm" property="subCategoryList" id="objCategory">
				<tr>
					<td>
						<bean:write name="objCategory" property="name" />
					</td>
					<td class="action">
						[<a href="viewKeywordAdmin?categoryId=<bean:write name='objCategory' property='id' />&categoryTypeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>"><bright:cmsWrite identifier="link-open" filter="false" /></a>]
					</td>				
					<td class="action">
						[<a href="viewChangeKeywordParent?parentId=<bean:write name='categoryAdminForm' property='categoryId' />&amp;categoryIdToMove=<bean:write name='objCategory' property='id' />&categoryTypeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>">move</a>]
					</td>				
					<td class="action">
						[<a href="viewUpdateKeyword?categoryId=<bean:write name='objCategory' property='id' />&categoryTypeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>">edit</a>]
					</td>
					<td class="action">
						<%-- Check cannot be deleted --%>
						<c:if test="${!objCategory.cannotBeDeleted}">
							[<a href="deleteKeyword?categoryId=<bean:write name='categoryAdminForm' property='categoryId' />&amp;categoryIdToDelete=<bean:write name='objCategory' property='id' />&categoryTypeId=<bean:write name='categoryAdminForm' property='categoryTreeId'/>&filter=<bean:write name="filter" />" title="Delete this keyword" onclick="return confirm('Are you sure you want to delete this keyword?');">X</a>]
						</c:if>
					</td>
				</tr>
			</logic:iterate>
		</table>
					
	</logic:equal>
	
	<logic:equal name="categoryAdminForm" property="subCategoryListIsEmpty" value="true">
	
		<p>There are currently no keywords at this level beginning with the letter <strong><span style="text-transform:uppercase"><bean:write name="filter" /></span></strong>.</p>
	</logic:equal>
	
	<br />
	<div class="hr"></div>
	
	<p><a href="../action/viewManageAttributes">&laquo; Back to attributes</a></p>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>