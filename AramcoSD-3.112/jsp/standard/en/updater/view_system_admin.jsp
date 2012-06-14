<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="showUpdates" settingName="application-updater"/>
<bright:applicationSetting id="canPublish" settingName="allow-publishing"/>



<head>
	<bean:define id="pagetitle" value="System"/>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle"/> </title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="system"/>
	<bean:define id="tabId" value="system"/>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../updater/inc_system_tabs.jsp"%>
	
	<h3>System Administration Hub</h3>										

								
	<c:if test="${showUpdates}">
		<p>
			Go to the <a href="viewApplicationUpdateAdmin">Updates</a> section to check for and download updates for <bright:cmsWrite identifier="app-name" filter="false"/>. 
		</p>
	</c:if>
	
	<p>
		Go to the <a href="viewDeveloperAdmin">Developer</a> section for developer related tools. 
	</p>
	
	<c:if test="${canPublish}">
		<p>
			Go to the <a href="viewSynchAdmin">Synchronisation</a> section to manage syncronisation of <bright:cmsWrite identifier="items" filter="false" /> between <bright:cmsWrite identifier="app-name" filter="false"/> instances. 
		</p>
	</c:if>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>