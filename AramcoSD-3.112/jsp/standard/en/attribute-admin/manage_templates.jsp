<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard	16-May-2008      Created
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Attribute Templates</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="filters"/>
	<bean:define id="pagetitle" value="Attribute Templates"/>
	<bean:define id="tabId" value="filters"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../user-admin/inc_user_profile_tabs.jsp"%>
	
	<p>From here you can add edit and delete <bright:cmsWrite identifier="app-name" filter="false"/> attribute templates</p> 

	<div id="tabContent">
  	<logic:notEmpty name="filterForm" property="filters">
		<table cellspacing="0" class="admin" summary="List of Templates">
			<tr>
				<th>Template name</th>
				<th colspan="3">&nbsp;</th>
			</tr>
			
			<logic:iterate name="filterForm" property="filters" id="filter" indexId="index">
			
				<tr>
					<td>
						<bean:write name="filter" property="name"/>
					</td>
					<td class="action">
						[<a href="viewFilter?id=<bean:write name='filter' property='id'/>&type=2">edit</a>]
					</td>
					<td class="action">
						[<a href="deleteFilter?id=<bean:write name='filter' property='id'/>&type=2" onclick="return confirm('Are you sure you want to remove this template?');" title="Delete this template">X</a>]
					</td>
				</tr>
			
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="filterForm" property="filters">
		<p>There are currently no Templates defined.
	</logic:empty>
	</div>
	

	<p><a href="viewFilter?type=2">Add a new attribute template &raquo;</a></p>
	
	<logic:notEmpty name="filterForm" property="filters">
	
	</logic:notEmpty>
		
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>