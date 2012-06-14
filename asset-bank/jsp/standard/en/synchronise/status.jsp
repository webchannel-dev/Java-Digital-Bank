<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		20-May-2009		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	<bean:define id="pagetitle" value="System"/>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle"/> </title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="system"/>
	<bean:define id="tabId" value="synch"/>

	<logic:equal name="statusForm" property="inProgress" value="true">
		<meta HTTP-EQUIV="refresh" CONTENT="10;URL=viewPublishingStatus"></meta>
	</logic:equal>
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1>Synchronisation Status</h1> 

	<%@include file="../updater/inc_system_tabs.jsp"%>
	
	<logic:equal name="statusForm" property="inProgress" value="true">
		<p>There is currently a synchronisation in progress. This page will refresh regularly to show the status of the synchronisation.
		<p>Alternatively you can manually <a href="viewPublishingStatus">refresh the page</a> or <a href="../action/cancelPublishing" onclick="return confirm('Are you sure you want to cancel publishing? The current batch will be completed then the process will stop');">cancel this synchronisation &raquo;</a></p>
	</logic:equal>
	<logic:equal name="statusForm" property="inProgress" value="false">
		<p>Synchronisation has completed. See below for a log of the last synchronisation or <a href="viewSynchAdmin">run another synchronisation &raquo;</a></p>
	</logic:equal>

	<h3>Synchronisation log:</h3>
	<logic:notEmpty name="statusForm" property="messages">
		<ul class="normal">
		<logic:iterate name="statusForm" property="messages" id="message">
			<li><bean:write name="message" /></li>
		</logic:iterate>
		</ul>
	</logic:notEmpty>

	

<%@include file="../inc/body_end.jsp"%>
</body>
</html>