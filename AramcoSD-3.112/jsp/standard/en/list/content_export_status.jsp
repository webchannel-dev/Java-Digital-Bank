<%@include file="../inc/doctype_html.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Content Export</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="export"/>
	<bean:define id="pagetitle" value="Export"/>
	<bean:define id="helpsection" value="export"/>
	<bean:define id="tabId" value="export"/>
	<logic:equal name="exportStatusForm" property="inProgress" value="true">
		<meta HTTP-EQUIV="refresh" CONTENT="10;URL=viewContentExportStatus"></meta>
	</logic:equal>
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	<logic:equal name="exportStatusForm" property="inProgress" value="true">

	<h3 class="loader"><img src="../images/standard/misc/ajax_loader.gif" width="24" height="24" alt="loading..." />The export is in progress.</h3>
	
	<p>This page will automatically refresh every 10 seconds until the export process finishes. If for some reason this does not happen you may <a href="viewContentExportStatus">update the page</a> manually to check the status.</p>
	</logic:equal>

	<logic:equal name="exportStatusForm" property="inProgress" value="false">
		

		<c:if test="${exportStatusForm.result.exportCount > 0}">
			<div class="confirm">
				<h3>The export has completed successfully!</h3>
				<p>You can now download your Excel file:</p>
				<ul class="arrow">
					<li>
						<a href="downloadExportFile?file=<bean:write name="exportStatusForm" property="result.dataFile.filePath"/>&filename=<bean:write name="exportStatusForm" property="result.dataFile.fileName"/>"><bean:write name="exportStatusForm" property="result.dataFile.fileName"/></a>
					</li>
				</ul>
			</div>
		</c:if>

		
	</logic:equal>
	
	<logic:notEmpty name="exportStatusForm" property="messages">
		<div class="logHeading">Export log:</div>
		<ul class="log stripey">
			
		<logic:iterate name="exportStatusForm" property="messages" id="message">
			<li><bean:write name="message" /></li>
		</logic:iterate>
		</ul>
	</logic:notEmpty>
	
	<a href="viewContentExport">&laquo; Back</a>
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>