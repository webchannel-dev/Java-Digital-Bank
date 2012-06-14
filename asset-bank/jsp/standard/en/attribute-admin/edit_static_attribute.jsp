<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		25-Oct-2005		Created
	 d2		Ben Browning		22-Feb-2006		HTML/CSS tidy up
	 d3		Francis Devereux	30-Jan-2009		Allow attributes to be mandatory when doing bulk upload
	 d4		Francis Devereux	27-Apr-2009		Allow Added By attribute to be shown on the search form
	   											Allow admin to choose whether Added By is included in the search builder (previously, IsSearchBuilderField was 1 for this attribute in the DB but it wasn't possible to change it to 0 using this JSP)
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="searchBuilderEnabled" settingName="search-builder-enabled"/>
<bright:applicationSetting id="assetEntitiesEnabled" settingName="asset-entities-enabled"/>
<bright:applicationSetting id="useParentMetadata" settingName="include-metadata-from-parents-for-search"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attributes</title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Edit Attribute"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:equal name="attributeForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="attributeForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="saveStaticAttribute" method="post">
		<input type="hidden" name="mandatory_attribute.label" value="Please enter a value for the label." />
		<html:hidden name="attributeForm" property="attribute.id"/>
		<html:hidden name="attributeForm" property="attribute.typeId"/>
		<html:hidden name="attributeForm" property="attribute.static"/>
		<html:hidden name="attributeForm" property="attribute.fieldName"/>
		<html:hidden name="attributeForm" property="attribute.sequence"/>
		<html:hidden name="attributeForm" property="attribute.readOnly"/>
		<table cellspacing="0" class="form" summary="Form for attribute details">
			<tr>
				<th>
					Attribute: 
				</th>
				<td>
					<bean:write name="attributeForm" property="attribute.fieldName"/>
				</td>
			</tr>
			<tr>
				<th>
					<label for="attributeLabel">Label: <span class="required">*</span></label>
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.label" maxlength="255" styleId="attributeLabel"/>
				</td>
			</tr>
			<logic:notEmpty name="attributeForm" property="attribute.translations">
				<logic:iterate name="attributeForm" property="attribute.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="label<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="attributeForm"  property="attribute.translations[${tIndex}].language.id"/>
								<html:hidden name="attributeForm"  property="attribute.translations[${tIndex}].language.name"/>
								<input type="text" class="text" name="attribute.translations[<bean:write name='tIndex'/>].label" maxlength="255" id="label<bean:write name='tIndex'/>" value="<bean:write name="translation" property="label" />" />
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>
				<th>
					<c:choose>
						<c:when test="${attributeForm.attribute.fieldName=='title' || attributeForm.attribute.fieldName=='file' || attributeForm.attribute.readOnly}">
							Mandatory:
						</c:when>
						<c:otherwise>
							<label for="mandatory">Mandatory: </label>
						</c:otherwise>
					</c:choose>
					 
				</th>
				<td>
					<c:choose>
						<c:when test="${attributeForm.attribute.fieldName=='title' || attributeForm.attribute.fieldName=='file'}">
							Yes
						</c:when>
						<c:when test="${attributeForm.attribute.readOnly}">
							N/A
						</c:when>
						<c:otherwise>
							<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.mandatory" styleId="mandatory"/>
						</c:otherwise>
					</c:choose>

				</td>
			</tr>
			<tr>
				<th>
					<c:choose>
						<c:when test="${attributeForm.attribute.fieldName=='title' || attributeForm.attribute.fieldName=='file' || attributeForm.attribute.readOnly}">
							Mandatory on Bulk Upload:
						</c:when>
						<c:otherwise>
							<label for="">Mandatory on Bulk Upload:</label>
						</c:otherwise>
					</c:choose>
					
				</th>
				<td>
					<c:choose>
						<c:when test="${attributeForm.attribute.fieldName=='title' || attributeForm.attribute.fieldName=='file'}">
							Yes
						</c:when>
						<c:when test="${attributeForm.attribute.readOnly}">
							N/A
						</c:when>
						<c:otherwise>
							<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.mandatoryBulkUpload"/>
						</c:otherwise>
					</c:choose>

				</td>
			</tr>
			<c:if test="${attributeForm.attribute.fieldName=='description' || attributeForm.attribute.fieldName=='title' || attributeForm.attribute.fieldName=='file' || attributeForm.attribute.fieldName=='price' || attributeForm.attribute.fieldName=='accessLevels' || attributeForm.attribute.fieldName=='categories'}">
				<tr>
					<th>
						<label for="helpText">Help text:</label> 
					</th>
					<td>
						<html:text styleClass="text" name="attributeForm" property="attribute.helpText" maxlength="100" styleId="helpText" />
					</td>
				</tr>
			</c:if>
			<tr>
				<th style="width:120px;">
					<label for="hideForSort">Hide from sort:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.hideForSort" styleId="hideForSort" />
				</td>
			</tr>
			<tr>
			<c:if test="${attributeForm.attribute.fieldName=='assetId' || attributeForm.attribute.fieldName=='categories' || attributeForm.attribute.fieldName=='accessLevels' || attributeForm.attribute.fieldName=='originalFilename'}">
			<tr>
				<th style="width:120px;">
					<label for="keywordSearchable">Include in keyword searches:</label>
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.keywordSearchable" styleId="keywordSearchable" />
				</td>
			</tr>
			</c:if>
			<tr>
				<th>
					<label for="highlight">Highlight on view:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.highlight" styleId="highlight" />
				</td>
			</tr>
			<tr>
				<th>
					<c:choose>
						<c:when test="${attributeForm.attribute.readOnly || attributeForm.attribute.fieldName=='file'}">
							Default value:
						</c:when>
						<c:otherwise>
							<label for="defaultValue">Default value:</label>
						</c:otherwise>
					</c:choose>
				</th>
				<td>
					<c:choose>
						<c:when test="${attributeForm.attribute.readOnly || attributeForm.attribute.fieldName=='file'}">
							N/A
						</c:when>
						<c:otherwise>
							<html:text styleClass="text" name="attributeForm" property="attribute.defaultValue" maxlength="255" styleId="defaultValue"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
		<div class="hr"></div>
	
		<input class="button flush floated" type="submit" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
		<a href="../action/viewManageAttributes" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</html:form>


	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
