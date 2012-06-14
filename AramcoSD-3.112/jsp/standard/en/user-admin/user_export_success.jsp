<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	08-Feb-2008		Created from synchronise/export_success.jsp
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Export</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="users"/>
	<bean:define id="pagetitle" value="Export users"/>
	<bean:define id="tabId" value="manageUsers"/>
</head>

<body id="resultsPage">

	<%@include file="../inc/body_start.jsp"%>
   
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../user-admin/inc_user_tabs.jsp"%>
	
	<p>You have successfully exported your selected users.</p>
	<p>Click on the link below to download your tab delimited users export file:</p>
		
	<ul>
		<li>
			Data File: <a href="downloadExportFile?file=<bean:write name="ExportResult" property="dataFile.filePath"/>&filename=<bean:write name="ExportResult" property="dataFile.fileName"/>"><bean:write name="ExportResult" property="dataFile.fileName"/></a>
		</li>
	</ul>
	<br/>
	
	
	      
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>