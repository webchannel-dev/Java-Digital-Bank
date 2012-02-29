<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		20-Apr-2004		Created.
	 d7		Matt Stevenson	22-Nov-2007		Removed returnCode reference
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="useKeywordSearchChooser" settingName="keyword-search-chooser" />
<bright:applicationSetting id="showTranslations" settingName="show-translations-when-adding-keyword" />

<head>
	
	<title><bright:cmsWrite identifier="title-keywords" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="keyword"/>
	<script type="text/JavaScript">
		// give the newCatName field the focus once the dom is ready
		$j(function () {
  			$j('#newCatName').focus();
 		});
	</script>	
</head>
<body id="popup">

<div>
	
	<h1><bright:cmsWrite identifier="heading-keywords" filter="false" /></h1> 
	<bean:parameter id="categoryTypeId" name="categoryTypeId" value="-1"/>
	

	<p class="tabHolderPopup">
		<a href="viewAddKeywordPopup?categoryTypeId=<bean:write name='categoryTypeId'/>"><bright:cmsWrite identifier="tab-quick-add" filter="false" /></a>
		<a class="active" href="viewKeywordAdminPopup?categoryTypeId=<bean:write name='categoryTypeId'/>"><bright:cmsWrite identifier="tab-keyword-list" filter="false" /></a>
	</p>
	
	<div id="tabContent">
	
	<logic:present  name="categoryAdminForm">
		<logic:equal name="categoryAdminForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="categoryAdminForm" property="errors" id="error">
					<bean:write name="error" filter="false"/>
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<p><bright:cmsWrite identifier="snippet-currently-editing" filter="false" />&nbsp;
		<logic:equal name="categoryAdminForm" property="root" value="false">
			<strong>
				<a href="viewKeywordAdminPopup?categoryId=-1&categoryTypeId=<bean:write name='categoryTypeId'/>"><bright:cmsWrite identifier="snippet-root" filter="false" /></a>
				
				<logic:iterate name="categoryAdminForm" property="ancestorCategoryList" id="ancestorCategory"> &raquo;
					<a href="viewKeywordAdminPopup?categoryId=<bean:write name='ancestorCategory' property='id' />&categoryTypeId=<bean:write name='categoryTypeId'/>"><bean:write name="ancestorCategory" property="name" filter="false"/></a>
				</logic:iterate>
				
				&raquo; <bean:write name="categoryAdminForm" property="categoryName" filter="false"/>
			</strong>
		</logic:equal>
	
		<logic:equal name="categoryAdminForm" property="root" value="true">
			<strong><bright:cmsWrite identifier="snippet-root" filter="false" /></strong>
		</logic:equal>
	</p>
	
	<div class="hr"></div>
	
	<logic:empty name='categoryAdminForm' property='categoryName'>
		<c:set var='currentCategoryName'><bright:cmsWrite identifier="snippet-root" filter="false" /></c:set>
	</logic:empty>
	<logic:notEmpty name='categoryAdminForm' property='categoryName'>
		<bean:define id='currentCategoryName' name='categoryAdminForm' property='categoryName'  type="java.lang.String"/>
	</logic:notEmpty>
	
	<h3><em><bean:write name='currentCategoryName' filter="false"/></em> <bright:cmsWrite identifier="keyword-nodes" filter="false" />:</h3>
	
	
	<c:set var="sUrl" value="viewKeywordAdminPopup?categoryId=${categoryAdminForm.categoryId}&categoryTypeId=${categoryTypeId}&filter=" />
	
	<c:if test="${userprofile.currentLanguage.usesLatinAlphabet}">
		<%@include file="inc_a_to_z.jsp"%>
		<br />
	</c:if>

	<logic:equal name="categoryAdminForm" property="subCategoryListIsEmpty" value="false">
	
		<table cellspacing="0" class="admin list" summary="Keyword list" width="100%">				
													
			<logic:iterate name="categoryAdminForm" property="subCategoryList" id="objCategory">
				
				<tr>	
					<td style="width:50%;">
						<bean:write name="objCategory" property="name" filter="false"/>
						<c:if test="${objCategory.expired}">
							<span class="disabled" style="font-style: italic;">&nbsp;&nbsp;(<bright:cmsWrite identifier="snippet-expired" filter="false" />)</span>
						</c:if>
					</td>
					<td class="action">
						[<a href="viewKeywordAdminPopup?categoryId=<bean:write name='objCategory' property='id' />&categoryTypeId=<bean:write name='categoryTypeId'/>"><bright:cmsWrite identifier="link-open" filter="false" /></a>]
					</td>				
					<td class="action">
						[<a href="viewChangeKeywordParentPopup?parentId=<bean:write name='categoryAdminForm' property='categoryId' />&amp;categoryIdToMove=<bean:write name='objCategory' property='id' />&categoryTypeId=<bean:write name='categoryTypeId'/>"><bright:cmsWrite identifier="link-move" filter="false" /></a>]
					</td>				
					<td class="action">
						[<a href="viewUpdateKeywordPopup?categoryId=<bean:write name='objCategory' property='id' />&categoryTypeId=<bean:write name='categoryTypeId'/>"><bright:cmsWrite identifier="link-edit" filter="false" /></a>]
					</td>
					<td class="action">
						<%-- Check cannot be deleted --%>
						<c:if test="${!objCategory.cannotBeDeleted}">
							[<a href="deleteKeywordPopup?categoryId=<bean:write name='categoryAdminForm' property='categoryId' />&categoryIdToDelete=<bean:write name='objCategory' property='id' />&categoryTypeId=<bean:write name='categoryTypeId'/>&filter=<bean:write name='filter'/>" title="Delete this keyword" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-delete-keyword" filter="false" />');">X</a>]
						</c:if>
					</td>
				</tr>
			</logic:iterate>
		</table>
					
	</logic:equal>
	
	<logic:equal name="categoryAdminForm" property="subCategoryListIsEmpty" value="true">
		<c:if test="${empty filter || filter=='all'}">
			<p><bright:cmsWrite identifier="snippet-no-keywords" filter="false" /></p>
		</c:if>
		<c:if test="${not empty filter && filter!='all'}">
			<p><bright:cmsWrite identifier="snippet-no-keywords-for-letter" filter="false" /> <bright:write name="filter" case="upper" filter="false"/></p>
		</c:if>
	</logic:equal>
	
	<div class="hr"></div>	
	
	<h3><bright:cmsWrite identifier="snippet-add-keyword-to" filter="false" /> <em><bean:write name='currentCategoryName' filter="false"/></em>:</h3>
	
	<html:form action="addKeywordPopup" method="post" >	
		<html:hidden name="categoryAdminForm" property="categoryId"/>
		<html:hidden name="categoryAdminForm" property="categoryTreeId"/>
		
		<%-- This in case of validation failure --%>
		<input type="hidden" name="categoryTypeId" value="<c:out value='${categoryTypeId}' />" />
		
		<c:if test="${not showTranslations}">
			<logic:notEmpty name="categoryAdminForm" property="newCategory.translations">
				<logic:iterate	name="categoryAdminForm" property="newCategory.translations" id="translation" indexId="tIndex">
					<html:hidden name="categoryAdminForm" property="newCategory.translations[${tIndex}].language.id"/>
				</logic:iterate>	
			</logic:notEmpty>
		</c:if>
				
		<table class="form" cellspacing="0" cellpadding="0" style="width:auto;">
			<tr>
				<th style="width:90px;"><label for="newCatName"><bright:cmsWrite identifier="label-new-keyword" filter="false" /></label></th>
				<td>
					<html:text styleClass="text" name="categoryAdminForm" property="newCategory.name" value="" maxlength="150" size="32" styleId="newCatName" />
				</td>
			</tr>
			<c:if test="${showTranslations}">
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
			</c:if>
			<tr>
				<th style="width:90px;"><label for="synonyms"><bright:cmsWrite identifier="label-synonyms" filter="false" />:</label></th>
				<td>
					<textarea class="text" id="synonyms" name="newCategory.description" rows="4" style="width: 202px;"><bean:write name="categoryAdminForm" property="newCategory.description" filter="false"/></textarea>
				</td>
			</tr>
			<c:if test="${showTranslations}">
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
			</c:if>
			<c:if test="${useKeywordSearchChooser}">
				<tr>
					<th><label for="isBrowsable"><bright:cmsWrite identifier="label-visible-on-search" filter="false" /></label></th>
					<td>
						<html:checkbox styleClass="checkbox" styleId="isBrowsable" name="categoryAdminForm" property="newCategory.isBrowsable" />
					</td>
				</tr>
			</c:if>
			<tr>
				<th style="width:90px;"></th>
				<td>
					<input type="submit" name="addCategory" value="<bright:cmsWrite identifier="button-add" filter="false" />" class="button flush" />
				</td>
			</tr>
		</table>

	</html:form>

	<c:if test="${userprofile.isAdmin}">
		Alternatively, you can import keywords from a file by selecting 'edit keywords' from the Admin> Attributes page.
	</c:if>

	</div>

	<p style="text-align:right;">
		<script type="text/javascript">
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
		</script>
	</p>


</div>

</body>
</html>