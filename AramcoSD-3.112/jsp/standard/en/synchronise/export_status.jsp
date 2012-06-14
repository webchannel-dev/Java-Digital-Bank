<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		02-Jun-2008		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Export</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="export"/>
	<bean:define id="pagetitle" value="Export"/>
	<bean:define id="helpsection" value="export-import"/>

	<logic:equal name="exportStatusForm" property="inProgress" value="true">
		<meta HTTP-EQUIV="refresh" CONTENT="10;URL=viewExportStatus"></meta>
	</logic:equal>
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:equal name="exportStatusForm" property="inProgress" value="true">
	<p>The export is in progress.</p>
	<p>This page will automatically refresh every 10 seconds until the export process finishes. If for some reason this does not happen you may <a href="viewExportStatus">update the page</a> manually to check the status.</p>
	</logic:equal>
	<logic:equal name="exportStatusForm" property="inProgress" value="false">
		<p>The export has finished.</p>

		<c:if test="${exportStatusForm.result.exportCount > 0}">
			<p>You have successfully exported your selected <bright:cmsWrite identifier="items" filter="false" />.</p>
			<ul>
				<li>
					Data File: <a href="downloadExportFile?file=<bean:write name="exportStatusForm" property="result.dataFile.filePath"/>&filename=<bean:write name="exportStatusForm" property="result.dataFile.fileName"/>"><bean:write name="exportStatusForm" property="result.dataFile.fileName"/></a>
				</li>
				<c:if test="${not empty exportStatusForm.result.zipFiles}">
					<logic:iterate name="exportStatusForm" property="result.zipFiles" id="zipFile">
						<li>
							Zip File: <a href="downloadExportFile?file=<bean:write name="zipFile" property="filePath"/>&filename=<bean:write name="zipFile" property="fileName"/>"><bean:write name="zipFile" property="fileName"/></a>
						</li>
					</logic:iterate>
				</c:if>	
			</ul>
			<c:if test="${empty exportStatusForm.result.zipFiles && exportStatusForm.result.totalFileCount>0}">
				<br/>
				<p><c:out value="${exportStatusForm.result.totalFileCount}"/> files (including any working versions) have been written to the current system storage device 
				export directory:<br/><br/><strong><c:out value="${exportStatusForm.result.fileExportLocation}"/></strong></p>
			</c:if>
			<br/>
			
			<c:if test="${userprofile.isAdmin}">
				<p>You may now <a href="deleteExportedAssets?filename=<bean:write name="exportStatusForm" property="result.dataFile.filePath"/>" onclick="return confirm('Are you sure you want to delete these <bright:cmsWrite identifier="items" filter="false" />? This operation can only be undone by re-importing the assets!')">delete the exported <bright:cmsWrite identifier="items" filter="false" /></a> from the system.
				(Note, this is only reversible if you exported the asset files)</p>
			</c:if>

		</c:if>

		<p>Export log:</p>

	</logic:equal>
	
	<logic:notEmpty name="exportStatusForm" property="messages">
		<ul class="normal">
		<logic:iterate name="exportStatusForm" property="messages" id="message">
			<li><bean:write name="message" /></li>
		</logic:iterate>
		</ul>
	</logic:notEmpty>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>