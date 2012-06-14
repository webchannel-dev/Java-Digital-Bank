<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	19-May-2008		Created
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Filter Group</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="filters"/>

	<c:choose>
		<c:when test="${filterGroupForm.filterGroup.id > 0}">
			<bean:define id="pagetitle" value="Edit Filter Group"/>
		</c:when>
		<c:otherwise>
			<bean:define id="pagetitle" value="Add Filter Group"/>
		</c:otherwise>
	</c:choose>
	<bean:define id="tabId" value="filters"/>
	
	
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>

	<logic:notEmpty name="filterGroupForm" property="errors">
		<div class="error">
			<logic:iterate name="filterGroupForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:notEmpty>
	
	<p>Enter the name of this filter group, select it's type and click the 'Save' button:</p> 

	<form name="filterGroupForm" action="saveFilterGroup" method="post" class="floated">
		<html:hidden name="filterGroupForm" property="filterGroup.id"/>
		<input type="hidden" name="filterGroup.filterTypeId" value="1"/>
					
	
		<label for="name">Filter group name:</label> 
		<html:text styleClass="text" name="filterGroupForm" property="filterGroup.name" maxlength="255" styleId="name" />
		<br />	
		
		<div class="hr"></div>
		<input class="button flush" type="submit" value="Save" />
		<a href="../action/viewManageFilterGroups" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</form>

		
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>