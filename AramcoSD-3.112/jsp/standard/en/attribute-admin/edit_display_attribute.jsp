<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	19-Oct-2007		Created
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
<bright:applicationSetting id="useParentMetadata" settingName="include-metadata-from-parents-for-search"/>
<bright:applicationSetting id="assetEntitiesEnabled" settingName="asset-entities-enabled"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Display Attributes</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Edit Display Attribute"/>
	<bean:define id="tabId" value="displayAttributes"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>Edit the display attribute below then click 'Save'.</p> 

	<logic:notEmpty name="displayAttributeForm" property="errors">
		<div class="error">
			<logic:iterate name="displayAttributeForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:notEmpty>

	<form name="saveDisplayAttribute" action="editDisplayAttribute" method="post" class="floated">
		<input type="hidden" name="new" value="0"/>
		<html:hidden name="displayAttributeForm" property="displayAttribute.sequenceNumber"/>
		<html:hidden name="displayAttributeForm" property="displayAttribute.attribute.label"/>
		<html:hidden name="displayAttributeForm" property="displayAttribute.attribute.id"/>
		<html:hidden name="displayAttributeForm" property="displayAttribute.groupId" />
		
		<label>Attribute to display:</label>
		<div class="holder"><bean:write name="displayAttributeForm" property="displayAttribute.attribute.label"/></div>
		<br />
	
		<%@include file="inc_display_attribute_fields.jsp"%>

	
		<div class="hr"></div>
		
	
		<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
		<a href="../action/viewManageDisplayAttributes?daGroupId=<bean:write name='displayAttributeForm' property='displayAttribute.groupId' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />	
	</form>

		
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>