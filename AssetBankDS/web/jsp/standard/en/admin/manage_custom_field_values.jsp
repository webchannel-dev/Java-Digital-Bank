<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	03-Dec-2007		Created
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



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | User Field Values</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="users"/>
	<bean:define id="pagetitle" value="User Field Values"/>
	<bean:define id="tabId" value="customFields"/>
	<script type="text/JavaScript">
		// give the fieldValue field the focus once the dom is ready
		$j(function () {
  			$j('#fieldValue').focus();
		});
	</script>		
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../user-admin/inc_user_tabs.jsp"%>

	<bean:parameter name="id" id="id" value="0"/>
		
	<p>Edit the values available for this field or <a href="viewManageCustomFields">return to the list of user fields &raquo;</a></p> 

	<div id="tabContent">
  	
	<logic:notEmpty name="customFieldForm" property="values">
		<table cellspacing="0" class="admin" summary="List of Custom Field Values">
			<tr>
				<th>Value</th>
				<th colspan="4">&nbsp;</th>
			</tr>
			
			<logic:iterate name="customFieldForm" property="values" id="value" indexId="index">
			
				<tr>
					<td>
						<bean:write name="value" property="value"/>
					</td>
					<td class="action">
						[<a href="deleteCustomFieldValue?id=<bean:write name='value' property='id'/>&customFieldId=<bean:write name='id'/>" onclick="return confirm('Are you sure you want to remove this custom field value?');" title="Delete this value">X</a>]
					</td>
				</tr>
			
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="customFieldForm" property="values">
		<p>There are currently no values defined for this user field.
	</logic:empty>
	</div>
	


	<h3>Add new a value to this field:</h3>

	<logic:equal name="customFieldForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="customFieldForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:equal>

	<p>
		<form name="addCustomFieldValueForm" action="saveCustomFieldValue" method="get">
			<input type="hidden" name="customFieldValue.customFieldId" value="<bean:write name='customFieldForm' property='customField.id'/>"/>
			<table class="form" cellspacing="0" border="0">
				<tr>
					<th>Value:</th>
					<td>
						<html:text styleClass="text" name="customFieldForm" property="customFieldValue.value" maxlength="150" size="32" styleId="fieldValue" />
					</td>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<td><br/><input type="submit" name="addField" value="Add value &raquo;" class="button flush" /></td>
				</tr>
			</table>
		</form>
	</p>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>