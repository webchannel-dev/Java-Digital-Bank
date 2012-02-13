<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	30-Nov-2007		Created
	 d2		Matt Stevenson	07-Dec-2007		Added required switch to custom fields
	 d3		Matt Stevenson	18-Mar-2008		Integrated custom fields
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
	<bean:define id="helpsection" value="user_fields"/>
	<bean:define id="pagetitle" value="Users"/>
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

	<p>From here you can add edit and delete <bright:cmsWrite identifier="app-name" filter="false"/> user fields.</p> 

	<div id="tabContent">
  	
	<logic:notEmpty name="customFieldForm" property="customFields">
		<table cellspacing="0" class="admin" summary="List of Custom Fields">
			<tr>
				<th>Field Name <span class="required">*</span></th>
				<th>Field Type</th>
				<th>Is Required</th>
				<th>External User Registration</th>
				<th colspan="3">&nbsp;</th>
			</tr>
			
			<logic:iterate name="customFieldForm" property="customFields" id="field" indexId="index">
			
				<tr>
					<td>
						<bean:write name="field" property="name"/>
					</td>
					<td>
						<bean:write name="field" property="type.description"/>
					</td>
					<td>
						<c:choose>
							<c:when test="${field.isRequired}">
								yes
							</c:when>
							<c:otherwise>
								no
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${field.isSubtype}">
								yes
							</c:when>
							<c:otherwise>
								no
							</c:otherwise>
						</c:choose>
					</td>
					<td class="action">
						[<a href="viewCustomField?id=<bean:write name='field' property='id'/>">edit</a>]
					</td>
					<td class="action">
						[<a href="deleteCustomField?id=<bean:write name='field' property='id'/>" onclick="return confirm('Are you sure you want to remove this custom field? Any values associated with this field will also be removed');" title="Delete this field">X</a>]
					</td>
					<td class="action">
						<c:if test="${field.isDropdown || field.isCheckboxList}">
							[<a href="viewEditCustomFieldValues?id=<bean:write name='field' property='id'/>" title="add values to this field">edit field values</a>]
						</c:if>
					</td>
				</tr>
			
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="customFieldForm" property="customFields">
		<p>There are currently no user fields defined.
	</logic:empty>
	</div>
	
 	

	<h3>Add new user field:</h3>
	<p><span class="required">*</span> denotes a mandatory field.</p>
	<logic:equal name="customFieldForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="customFieldForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:equal>

	<p>
		<form name="addCustomFieldForm" action="saveCustomField" method="get">
			<%-- only one type of custom fields at the moment and it is user fields - can just use hidden id --%>
			<input type="hidden" name="customField.usageType.id" value="<bean:write name='customFieldForm' property='firstUsageType.id'/>" />
			<table class="form" cellspacing="0" border="0">
				<tr>
					<th>Field Name: <span class="required">*</span></th>
					<td>
						<html:text styleClass="text" name="customFieldForm" property="customField.name" value="" maxlength="150" size="32" styleId="fieldName" />
					</td>
				</tr>
				<logic:notEmpty name="customFieldForm" property="customField.translations">
					<logic:iterate name="customFieldForm" property="customField.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
							<tr>
								<th class="translation">
									<label for="label<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
								</th>
								<td>
									<html:hidden name="customFieldForm"  property="customField.translations[${tIndex}].language.id"/>
									<html:hidden name="customFieldForm"  property="customField.translations[${tIndex}].language.name"/>
									<input type="text" class="text" name="customField.translations[<bean:write name='tIndex'/>].name" maxlength="255" id="label<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" filter="false"/>" />
								</td>
							</tr>
						</logic:greaterThan>
					</logic:iterate>
				</logic:notEmpty>
				<%-- tr>
					<th>Field Usage Type:</th>
					<td>
						<c:choose>
							<c:when test="${customFieldForm.usageTypeCount == 1}">
								<input type="hidden" name="customField.usageType.id" value="<bean:write name='customFieldForm' property='firstUsageType.id'/>" />
								<bean:write name="customFieldForm" property="firstUsageType.description"/>
							</c:when>
							<c:otherwise>
								<html:select name="customFieldForm" property="customField.usageType.id" size="1">
									<logic:iterate name="customFieldForm" property="usageTypes" id="usageType">
										<option value="<bean:write name='usageType' property='id'/>"><bean:write name='usageType' property='description'/></option>
									</logic:iterate>
								</html:select>
							</c:otherwise>
						</c:choose>								
					</td>
				</tr --%>
				<tr>
					<th>Field Type:</th>
					<td>
						<html:select name="customFieldForm" property="customField.type.id" size="1">
							<logic:iterate name="customFieldForm" property="types" id="type">
								<option value="<bean:write name='type' property='id'/>"><bean:write name='type' property='description'/></option>
							</logic:iterate>
						</html:select>
					</td>
				</tr>
				<tr>
					<th>Required?:</th>
					<td>
						<html:checkbox name="customFieldForm" styleClass="checkbox" property="customField.isRequired" value="1"/>
					</td>
				</tr>
				<tr>
					<th>External user registration?:</th>
					<td>
						<html:checkbox name="customFieldForm" styleClass="checkbox" property="customField.isSubtype" value="1"/>
					</td>
				</tr>

				<bean:define name="customFieldForm" property="orgUnits" id="orgUnits"/>
				<bean:size id="numOrgUnits" name="orgUnits" />
				<c:if test="${useOrgUnits && numOrgUnits > 0}">
					<tr>
						<th>Organisational Unit:</th>
						<td>
							<html:select name="customFieldForm" styleId="orgUnit" property="customField.orgUnitId">
								<option value="0">- None -</option>
								<html:options collection="orgUnits" property="id" labelProperty="category.name"/>
							</html:select>
						</td>
					</tr>
				</c:if>

				<tr>
					<th>&nbsp;</th>
					<td><br/><input type="submit" name="addField" value="Add user field &raquo;" class="button flush" /></td>
				</tr>
			</table>
		</form>
	</p>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>