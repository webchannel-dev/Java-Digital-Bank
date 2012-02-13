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
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Filter Groups</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="filters"/>
	<bean:define id="pagetitle" value="Filter Groups"/>
	<bean:define id="tabId" value="filters"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>From here you can add edit and delete <bright:cmsWrite identifier="app-name" filter="false"/> filter groups.</p> 

	<div id="tabContent">
  	<logic:notEmpty name="filterGroupForm" property="filterGroups">
		<table cellspacing="0" class="admin" summary="List of Filter Groups">
			<tr>
				<th>Group name</th>
				<th colspan="3">&nbsp;</th>
			</tr>
			
			<logic:iterate name="filterGroupForm" property="filterGroups" id="group" indexId="index">
			
				<tr>
					<td>
						<bean:write name="group" property="name"/>
					</td>
					<td class="action">
						[<a href="viewFilterGroup?id=<bean:write name='group' property='id'/>">edit</a>]
					</td>
					<td class="action">
						[<a href="deleteFilterGroup?id=<bean:write name='group' property='id'/>" onclick="return confirm('Are you sure you want to remove this filter group? Any filters currently in the group will be removed from it first.');" title="Delete this filter group">X</a>]
					</td>
				</tr>
			
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="filterGroupForm" property="filterGroups">
		<p>There are currently no filter groups defined.
	</logic:empty>
	</div>
	


	<p><a href="viewFilterGroup">Add a new filter group &raquo;</a></p>
	<p><a href="viewManageFilters?type=1">&laquo; Back to filters </a></p>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>