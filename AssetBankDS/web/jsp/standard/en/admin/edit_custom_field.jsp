<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	30-Nov-2007		Created
	 d2		Matt Stevenson	07-Dec-2007		Added 'required' field
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@page import="org.apache.lucene.search.SortField"%>
<%@page import="com.bright.assetbank.attribute.bean.SortAttribute"%>


<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="useOrgUnits" settingName="orgunit-use" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | User Fields</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="users"/>
	<bean:define id="pagetitle" value="Edit User Field"/>
	<bean:define id="tabId" value="customFields"/>
	<script type="text/JavaScript">
		// give the fieldName field the focus once the dom is ready
		$j(function () {
  			$j('#fieldName').focus();
		});
	</script>	
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../user-admin/inc_user_tabs.jsp"%>

	<p>Edit the user field details in the form below and click Save...</p> 

	<logic:equal name="customFieldForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="customFieldForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:equal>

	<form name="editCustomFieldForm" action="saveCustomField" method="get" class="floated">
		<html:hidden name="customFieldForm" property="customField.id"/>
		
			<label for="fieldName">Field Name:</label>
			<html:text styleClass="text" name="customFieldForm" property="customField.name" maxlength="150" size="32" styleId="fieldName" />
			<br />
			
			<logic:notEmpty name="customFieldForm" property="customField.translations">
				<logic:iterate name="customFieldForm" property="customField.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						
						<label for="label<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
			
						<html:hidden name="customFieldForm"  property="customField.translations[${tIndex}].language.id"/>
						<html:hidden name="customFieldForm"  property="customField.translations[${tIndex}].language.name"/>
						<input type="text" class="text" name="customField.translations[<bean:write name='tIndex'/>].name" maxlength="255" id="label<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" filter="false"/>" /><br />
					
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<input type="hidden" name="customField.usageType.id" value="<bean:write name='customFieldForm' property='customField.usageType.id'/>" />

	
		<label>Field Type:</label>
		<input type="hidden" name="customField.type.id" value="<bean:write name='customFieldForm' property='customField.type.id'/>"/>
		<div class="holder"><bean:write name='customFieldForm' property='customField.type.description'/></div>
		<br />
		
		<label for="isRequired">Required?:</label>
		<input class="checkbox" type="checkbox" name="customField.isRequired" id="isRequired" value="1" <c:if test='${customFieldForm.customField.isRequired}'>checked</c:if>/>
		<br />
		
		<label for="externalUsers">External user registration?:</label>
		<input class="checkbox" type="checkbox" name="customField.isSubtype" id="externalUsers" value="1" <c:if test='${customFieldForm.customField.isSubtype}'>checked</c:if>/>
		<br />

		<bean:define name="customFieldForm" property="orgUnits" id="orgUnits"/>
		<bean:size id="numOrgUnits" name="orgUnits" />
		<c:if test="${useOrgUnits && numOrgUnits > 0}">
			<label for="orgUnit">Organisational Unit:</label>
			<html:select name="customFieldForm" styleId="orgUnit" property="customField.orgUnitId">
				<option value="0">- None -</option>
				<html:options collection="orgUnits" property="id" labelProperty="category.name"/>
			</html:select><br />	
		</c:if>
		
		<div class="hr"></div>

		<input class="button flush" type="submit" value="Save" />
		<a href="../action/viewManageCustomFields" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	
	</form>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>