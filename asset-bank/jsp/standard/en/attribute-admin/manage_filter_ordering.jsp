<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	06-Feb-2007		Created
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Filter Ordering</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="filters"/>
	<bean:define id="pagetitle" value="Filters"/>
	<bean:define id="tabId" value="filters"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" /></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>From here you can re-order filters that are linked to categories or access levels.</p> 
	<bean:define id="sequencing" value="true"/>
	<div id="tabContent">
			<c:set var="isCategory" value="true"/>

			<c:if test="${not empty filterForm.categoryFilters}">
				<h3>Category Filters:</h3>
				<c:forEach var="fbcEntry" items="${filterForm.categoryFilters}">
					<c:set var="category" value="${fbcEntry.key}"/>
					<c:set var="filters" value="${fbcEntry.value}"/>
					<table cellspacing="0" class="admin" summary="List of Filters" style="margin-left: <bean:write name='category' property='depth'/>em;">
						<tr><th colspan="5"><bean:write name='category' property='name'/></th></tr>
						
						<c:set var="count" value="0"/>
						<logic:iterate name="filters" id="filter" indexId="index"><c:set var="count" value="${count+1}"/></logic:iterate>
						<logic:iterate name="filters" id="filter" indexId="index">
						 	<%@include file="inc_filter_row.jsp"%>
						</logic:iterate>
					</table>
				</c:forEach>
			</c:if>

			<div class="hr"></div>

			<c:if test="${not empty filterForm.accessLevelFilters}">
				<h3>Access Level Filters:</h3>
				<c:forEach var="fbcEntry" items="${filterForm.accessLevelFilters}">
					<c:set var="category" value="${fbcEntry.key}"/>
					<c:set var="filters" value="${fbcEntry.value}"/>
					<table cellspacing="0" class="admin" summary="List of Filters" style="margin-left: <bean:write name='category' property='depth'/>em;">
						<tr><th colspan="5"><bean:write name='category' property='name'/></th></tr>

						<c:set var="count" value="0"/>
						<logic:iterate name="filters" id="filter" indexId="index"><c:set var="count" value="${count+1}"/></logic:iterate>
						<logic:iterate name="filters" id="filter" indexId="index">
							<%@include file="inc_filter_row.jsp"%>
						</logic:iterate>
					</table>				
				</c:forEach>
			</c:if>

	</div>
	

	<p><a href="viewManageFilters?type=1">&laquo; Back to filters</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>