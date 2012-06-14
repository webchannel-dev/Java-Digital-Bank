<%@include file="../../../standard/en/inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Jon Harvey		26-Jan-2011		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Job Queue</title> 
	<%@include file="../../../standard/en/inc/head-elements.jsp"%>
	<bean:define id="section" value="system"/>
	<bean:define id="pagetitle" value="Job Queue"/>
	<bean:define id="helpsection" value="indesignjobs"/>
	<bean:define id="tabId" value="jobQueue"/>
</head>
<body id="adminPage">
	<%@include file="../../../standard/en/inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<%@include file="../../../standard/en/updater/inc_system_tabs.jsp"%>
	
	<%@include file="inc_idjobs_tabs.jsp"%>
	<div id="tabContent">
		<logic:notEmpty name="inDesignJobs">
			<%@include file="inc_jobs_table.jsp"%>
		</logic:notEmpty>
		<logic:empty name="inDesignJobs">
			<p>There are currently no jobs.</p>
		</logic:empty>
	</div>	
	
	<%@include file="../../../standard/en/inc/body_end.jsp"%>
</body>
</html>