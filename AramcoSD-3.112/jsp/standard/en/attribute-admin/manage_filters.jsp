<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	19-Sep-2007		Created
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Filters</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="filters"/>
	<bean:define id="pagetitle" value="Attributes"/>
	<bean:define id="tabId" value="filters"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>From here you can add edit and delete <bright:cmsWrite identifier="app-name" filter="false"/> filters.</p> 

	<div id="tabContent">
	<c:choose>
		<c:when test="${filterForm.filtersCount > 0 || filterForm.allLinkedFiltersCount > 0}">
			
				<c:set var="isCategory" value="false"/>
				<c:set var="count" value="${filterForm.filtersCount}"/>
				<logic:notEmpty name="filterForm" property="filters">
					<h3>Global Filters:</h3>
					<table cellspacing="0" class="admin" summary="List of Filters">
						<tr>
							<th>Name</th>
							<th colspan="4">&nbsp;</th>
						</tr>
						<logic:iterate name="filterForm" property="filters" id="filter" indexId="index">
							<%@include file="inc_filter_row.jsp"%>
						</logic:iterate>
					</table>
					<p><a href="moveFilter">Order alphabetically &raquo;</a><br />
					<a href="viewManageFilterGroups">Manage filter groups &raquo;</a></p>
				</logic:notEmpty>
				
				<c:set var="isCategory" value="true"/>
				<logic:notEmpty name="filterForm" property="allLinkedFilters">
					<div class="hr"></div>
					<h3>Category/Access Level Linked Filters:</h3>
					<table cellspacing="0" class="admin" summary="List of Filters">
						<tr>
							<th>Name</th>
							<th colspan="4">&nbsp;</th>
						</tr>
						<logic:iterate name="filterForm" property="allLinkedFilters" id="filter" indexId="index">
							<%@include file="inc_filter_row.jsp"%>
						</logic:iterate>
					</table>
					<p><a href="viewReorderFilters">Reorder &raquo;</a></p>
				</logic:notEmpty>
			
			
		</c:when>
		<c:otherwise>
			<p>There are currently no filters defined.</p>
		</c:otherwise>
	</c:choose>
	</div>
	

	
	<p><a href="viewFilter?type=1">Add a new filter &raquo;</a></p>
	
	
	

	
	<logic:notEmpty name="filterForm" property="filters">
	
	<div class="hr"></div>
	
		<br/><h3>Default filter</h3>

		<p>Please select the filter that you would like to use as the default from the list below:</p>

		
		<form name="defaultFilterForm" action="setDefaultFilter" method="post">
		
			<html:select name="filterForm" property="defaultFilterId" size="1">
				<option value="-1">[No filtering]</option>
				<logic:notEmpty name="filterForm" property="filters">
					<logic:iterate name="filterForm" property="filters" id="filter">
						<option value="<bean:write name='filter' property='id'/>" <c:if test="${filter.id == filterForm.defaultFilter.id}">selected</c:if>><bean:write name='filter' property='name'/></option>
					</logic:iterate>
				</logic:notEmpty>
			</html:select>&nbsp;&nbsp;<input type="submit" name="submit" value="Set as default &raquo;" class="button"/>
		
		</form>
	
	</logic:notEmpty>
		
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>