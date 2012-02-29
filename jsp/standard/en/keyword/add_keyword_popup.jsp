<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		20-Apr-2004		Created.
	 d2		Matt Stevenson	08-Mar-2007		Added tree ids
	 d3		Matt Stevenson	23-Mar-2007		Changed retrieval of tree id
	 d4		Matt Stevenson	22-Nov-2007		Removed returnCode reference
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
<body id="popup" onload="this.focus();">

	<h1><bright:cmsWrite identifier="heading-keywords" filter="false" /></h1> 
	
	<c:choose>
		<c:when test="${categoryAdminForm.categoryTreeId > 0}">
			<bean:define id="categoryTypeId" name="categoryAdminForm" property="categoryTreeId"/>
		</c:when>
		<c:otherwise>
			<bean:parameter id="categoryTypeId" name="categoryTypeId" value="-1"/>
		</c:otherwise>
	</c:choose>
	
	<p class="tabHolderPopup">
		<a class="active" href="viewAddKeywordPopup?categoryTypeId=<bean:write name='categoryTypeId'/>"><bright:cmsWrite identifier="tab-quick-add" filter="false" /></a>
		<a href="viewKeywordAdminPopup?categoryTypeId=<bean:write name='categoryTypeId'/>"><bright:cmsWrite identifier="tab-keyword-list" filter="false" /></a>
	</p>
	
	<div id="tabContent">
	
	<logic:present  name="categoryAdminForm">
		<logic:equal name="categoryAdminForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="categoryAdminForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<h3><bright:cmsWrite identifier="label-add-new-keyword" filter="false" /></h3>

	<br />
	
	<html:form action="quickAddKeywordPopup" method="post">	
	
		<input type="hidden" name="categoryTreeId" value="<bean:write name='categoryTypeId'/>"/>

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
					<input type="text" class="text" name="newCategory.name" maxlength="150" size="32" id="newCatName" />
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
									<html:text styleClass="text" name="categoryAdminForm" property="newCategory.translations[${tIndex}].name" maxlength="150" size="32" styleId="newCatName${tIndex}" />
								</td>
							</tr>
						</logic:greaterThan>
					</logic:iterate>	
				</logic:notEmpty>
			</c:if>
			<tr>
				<th style="width:90px;"><label for="synonyms"><bright:cmsWrite identifier="label-synonyms" filter="false" />:</label></th>
				<td>
					<textarea class="text" id="synonyms" name="newCategory.description" rows="4" style="width: 202px;" ></textarea>
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
			<c:if test="${not empty categoryAdminForm.subCategoryList}">
				<tr>
					<th>
						<bright:cmsWrite identifier="label-parent" filter="false" />
					</th>
					<td>
						<html:select name="categoryAdminForm" property="categoryId">
							<html:option value="-1">[none]</html:option>
							<html:optionsCollection name="categoryAdminForm" property="subCategoryList" label="name" value="id"/>
						</html:select>
					</td>
				</tr>
			</c:if>
		<c:if test="${useKeywordSearchChooser}">
			<tr>
				<th style="width:90px;"><label for="isBrowsable"><bright:cmsWrite identifier="label-visible-on-search" filter="false" /></label></th>
				<td>
					<input type="checkbox" class="checkbox" id="isBrowsable" name="newCategory.isBrowsable" checked="checked" />
				</td>
			</tr>
		</c:if>
			<tr>
				<th style="width:90px;"></th>
				<td>
					<input type="submit" name="addagain" value="<bright:cmsWrite identifier="button-add" filter="false" />" class="button flush" />
				</td>
			</tr>
		</table>

	</html:form>

	<c:if test="${userprofile.isAdmin}">
		Alternatively, you can import keywords from a file by selecting 'edit keywords' from the Admin> Attributes page.
	</c:if>
	
	</div>

	<div style="text-align:right;">
		<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button js-enabled-show-inline" id="submitButton" onclick="window.close();" />
		
	</div>

</body>
</html>