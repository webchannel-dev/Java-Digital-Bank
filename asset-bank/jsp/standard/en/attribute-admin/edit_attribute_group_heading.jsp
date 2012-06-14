<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		01-Jul-2008		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attributes</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Attributes"/>
	
	<logic:equal name="attributeForm" property="attribute.id" value="0">	
		<bean:define id="pagetitle" value="Add Attribute Group Heading"/>
	</logic:equal>
	<logic:notEqual name="attributeForm" property="attribute.id" value="0">	
		<bean:define id="pagetitle" value="Edit Attribute Group Heading"/>
	</logic:notEqual>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	<p><span class="required">*</span> denotes a mandatory field.</p>
	<logic:equal name="attributeForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="attributeForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="saveAttribute" method="post" styleClass="floated">
		<html:hidden name="attributeForm" property="attribute.id"/>
		<html:hidden name="attributeForm" property="attribute.typeId"/>
		<html:hidden name="attributeForm" property="attribute.sequence"/>
		<input type="hidden" name="attribute.readOnly" value="false"/>
		<input type="hidden" name="attribute.isStatic" value="false"/>

		<label for="label">Label: <span class="required">*</span></label> 
		<html:text styleClass="text" name="attributeForm" property="attribute.label" maxlength="255" styleId="label" />
		<input type="hidden" name="mandatory_attribute.label" value="Please enter a value for the label"/>
		<br />
			
		<logic:notEmpty name="attributeForm" property="attribute.translations">
			<logic:iterate name="attributeForm" property="attribute.translations" id="translation" indexId="tIndex">
				<logic:greaterThan name="translation" property="language.id" value="0">
					<label for="label<bean:write name='tIndex'/>" class="language">(<bean:write name="translation" property="language.name"/>):</label> 
					<html:hidden name="attributeForm"  property="attribute.translations[${tIndex}].language.id"/>
					<html:hidden name="attributeForm"  property="attribute.translations[${tIndex}].language.name"/>
					<input type="text" class="text" name="attribute.translations[<bean:write name='tIndex'/>].label" maxlength="255" id="label<bean:write name='tIndex'/>" value="<bean:write name="translation" property="label" />" />	
					<br />
				</logic:greaterThan>
			</logic:iterate>
		</logic:notEmpty>
			
		<label for="highlight">Show open on view:</label> 
		<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.highlight" styleId="highlight" />
		<br />
		
		<logic:lessEqual name="attributeForm" property="attribute.id" value="0">
			
			<label for="visibleToGroup">Initially visible to:</label> 
		
			<bean:define id="groups" name="attributeForm" property="visibleToGroups"/>
			<html:select name="attributeForm" property="visibleToGroupId" styleId="visibleToGroup">
				<html:option value="0">- none -</html:option>
				<html:options collection="groups" property="id" labelProperty="name"/>
			</html:select>
			<span>(Visibility to custom groups is managed via the Groups menu)</span>
			<br />
		</logic:lessEqual>
	
		
		<div class="hr"></div>
		

		<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
		<a href="../action/viewManageAttributes" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</html:form>
	
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>