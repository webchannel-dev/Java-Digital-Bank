<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attributes</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Attributes"/>
	<bean:define id="tabId" value="manageAttributes"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>

	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>This page lists the hidden attributes currently in the system and enables you to make them visible again:</p>
	
	<div id="tabContent">
	
		<bright:refDataList componentName="AttributeManager" methodName="getHiddenAttributes" id="attributes"/>
	
		<logic:notEmpty name="attributes">
			<table cellspacing="0" class="admin" summary="List of Attributes">
				<tr>
					<th>Attribute</th>
					<th>Type</th>
					<th>&nbsp;</th>
				</tr>
				<logic:iterate name="attributes" id="attribute" indexId="index">
				<tr>
					<td><bean:write name="attribute" property="label"/></td>
					<td><%@include file="inc_attribute_types.jsp"%></td>
					<td class="action">[<a href="showAttribute?id=<c:out value='${attribute.id}' />">show</a>]</td>
				</tr>
				</logic:iterate>
			</table>
		</logic:notEmpty>

		<logic:empty name="attributes">
			<p>All the attributes in the system are currently visible.</p>
		</logic:empty>
	
	</div>

	<p><a href="viewManageAttributes">View visible attributes &raquo;</a></p>
	
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
