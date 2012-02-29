<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		20-Apr-2004		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="useKeywordSearchChooser" settingName="keyword-search-chooser" />

<head>
	
	<title><bright:cmsWrite identifier="title-keywords" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="keyword"/>
	<script type="text/JavaScript">
		// give the name field the focus once the dom is ready
		$j(function () {
  			$j('#name').focus();
 		});
	</script>	
</head>

<body id="popup">
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-keywords" filter="false" /></h1>

	<logic:present  name="categoryForm">
		<logic:equal name="categoryForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="categoryForm" property="errors" id="error">
					<bean:write name="error" filter="false"/>
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<html:form action="updateKeywordPopup" styleId="keywordForm" method="post">
		<input type="hidden" name="categoryId" value="<c:out value="${categoryForm.category.id}"/>"/>
		<input type="hidden" name="categoryTypeId" value="<c:out value='${categoryForm.category.categoryTypeId}'/>"/>
		<input type="hidden" name="parentId" value="<c:out value="${categoryForm.category.parentId}"/>"/>
		
		<table class="form" cellspacing="0" cellpadding="0" style="width:auto">
			<tr>
				<th style="width:50px;"><label for="name"><bright:cmsWrite identifier="label-keyword" filter="false" />:</label></th>
				<td>
					<html:text styleClass="text" name="categoryForm" property="category.name" size="30" maxlength="150" styleId="name"></html:text>
				</td>
			</tr>
			<logic:notEmpty name="categoryForm" property="category.translations">
				<logic:iterate name="categoryForm" property="category.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="newCatName<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
							</th>
							<td>
								<html:hidden name="categoryForm" property="category.translations[${tIndex}].language.id"/>
								<html:hidden name="categoryForm" property="category.translations[${tIndex}].language.name"/>
								<html:text styleClass="text" name="categoryForm" property="category.translations[${tIndex}].name" maxlength="150" size="32" styleId="newCatName${tIndex}" />
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>	
			</logic:notEmpty>
			<tr>
				<th style="width:50px;"><label for="synonyms"><bright:cmsWrite identifier="label-synonyms" filter="false" /></label></th>
				<td>
					<html:textarea styleClass="text" styleId="synonyms" name="categoryForm" property="category.description" rows="4" style="width: 200px;"/>
				</td>
			</tr>
			<logic:notEmpty name="categoryForm" property="category.translations">
				<logic:iterate name="categoryForm" property="category.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="synonyms<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
							</th>
							<td>
								<html:hidden name="categoryForm" property="category.translations[${tIndex}].language.id"/>
								<html:hidden name="categoryForm" property="category.translations[${tIndex}].language.name"/>
								<html:textarea styleClass="text" name="categoryForm" property="category.translations[${tIndex}].description" rows="4" style="width: 200px;" styleId="synonyms${tIndex}" />
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>	
			</logic:notEmpty>
		<c:if test="${useKeywordSearchChooser}">
			<tr>
				<th><label for="isBrowsable"><bright:cmsWrite identifier="label-visible-on-search" filter="false" /></label></th>
				<td>
					<html:checkbox styleClass="checkbox" styleId="isBrowsable" name="categoryForm" property="category.isBrowsable" />
				</td>
			</tr>
		</c:if>
		<tr>
			<th><label for="isExpired"><bright:cmsWrite identifier="label-expired" filter="false" /></label></th>
			<td>
				<html:checkbox styleClass="checkbox" styleId="isExpired" name="categoryForm" property="category.expired" />
			</td>
		</tr>
		</table>
		
		<div class="hr"></div>

		<input type="submit" name="save" value="<bright:cmsWrite identifier="button-save" filter="false" />" class="button flush floated" />
		<!-- hack a cancel link for the sake of consistency -->
		
		<a href="updateKeyword?categoryId=<c:out value='${categoryForm.category.id}'/>&categoryTypeId=<c:out value='${categoryForm.category.categoryTypeId}'/>&cancel=true" class="cancelLink js-enabled-show hidden" onclick="$('hiddenCancel').value = '<bright:cmsWrite identifier="button-cancel" filter="false" />'; $('keywordForm').submit(); return false;">Cancel</a>
		
		
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button js-enabled-hide"  />	

		
	</html:form>


</body>
</html>