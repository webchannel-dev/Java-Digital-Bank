<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	05-Jul-2007		Created
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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Display Attributes</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="displayAttributes"/>
	<bean:define id="pagetitle" value="Name Attribute Saved"/>
	<bean:define id="tabId" value="displayAttributes"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>You have successfully set the desciption attribute for your assets. </p>
	
	<p><strong>Note that you will need to <a href="viewReindexStatus">reindex all <bright:cmsWrite identifier="items"/></a> for this change to take effect.</strong></p>
	
	<p>Return to the <a href="viewManageDisplayAttributes">display attribute management page &raquo;</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>