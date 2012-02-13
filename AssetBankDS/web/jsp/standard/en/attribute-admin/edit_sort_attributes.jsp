<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	27-Nov-2006		Created
	 d2		Matt Stevenson	30-Nov-2006		Changed to reflect new sort attribute structure
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attribute Sorting</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="sorting"/>
	<bean:define id="pagetitle" value="Attribute Sorting"/>
	<bean:define id="tabId" value="attributeSorting"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>	
	
	<p>Update the details for this sort attribute in the form below and click the Save button:</p>
	
	<logic:notEmpty name="sortAttributeForm" property="errors">
		<div class="error">
			<logic:iterate name="sortAttributeForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:notEmpty>

	<form name="saveForm" action="saveSortAttribute" method="post" class="floated">
		<html:hidden name="sortAttributeForm" property="sortAttribute.order"/>
		<html:hidden name="sortAttributeForm" property="sortAttribute.sortAreaId"/>
		<label>Sort field:</label>
		<html:hidden name="sortAttributeForm" property="sortAttribute.attribute.id"/>
		<span><bean:write name="sortAttributeForm" property="sortAttribute.attribute.label"/></span>
		<br />

		<label for="sortType">Sort Type:</label>
		<bean:define id="sortAttribute" name="sortAttributeForm" property="sortAttribute"/>
		<html:select name="sortAttributeForm" property="sortAttribute.type" size="1" styleId="sortType">
			<option value="<%= SortField.STRING %>" <% if (((SortAttribute)sortAttribute).getType() == SortField.STRING) { %>selected<% 	} %>>Alphanumeric or date</option>
			<option value="<%= SortField.FLOAT %>" <% if (((SortAttribute)sortAttribute).getType() == SortField.FLOAT) { %>selected<% 	} %>>Numeric</option>
		</html:select>
		<br />
		<label for="reverse">Reverse sort?:</label>
		<html:checkbox name="sortAttributeForm" property="sortAttribute.reverse" styleId="reverse" styleClass="checkbox" />
		<br />

			
		<div class="hr"></div>	
		<input type="submit" name="submit" class="button flush" value="Save &raquo;" />
		<a href="../action/viewManageSortAttributes<c:if test="${sortAttribute.sortAreaId > 1}">?browse=1</c:if>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			
	</form>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>