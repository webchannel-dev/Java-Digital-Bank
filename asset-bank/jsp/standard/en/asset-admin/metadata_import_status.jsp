<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		06-Jul-20046	Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Metadata Import</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="metadata-import"/>
	<bean:define id="pagetitle" value="Metadata Import: Status"/>

	<logic:equal name="metadataImportStatusForm" property="inProgress" value="true">
		<meta HTTP-EQUIV="refresh" CONTENT="20;URL=metadataImportViewStatus"></meta>
	</logic:equal>
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:equal name="metadataImportStatusForm" property="inProgress" value="true">
	<p>The metadata import is in progress.</p>
	<p>This page will automatically refresh every 20 seconds until the import process finishes. If for some reason this does not happen you may <a href="metadataImportViewStatus">update the page</a> manually to check the status.</p>
	
	<div class="hr"></div>
	<c:choose>		
		<c:when test="${empty param.cancel}">
			<p><a href="metadataImportCancelImport" onclick="return confirm('Are you sure you want to abort this import?');">Cancel this import</a> <em>(the import will abort after the next row is processed)</em></p>
		</c:when>
		<c:otherwise>
			<p><em>Cancelling...</em></p>
		</c:otherwise>
	</c:choose>	
	</logic:equal>
	<logic:equal name="metadataImportStatusForm" property="inProgress" value="false">
	<p>The metadata import has finished.</p>
	</logic:equal>
	
	<logic:notEmpty name="metadataImportStatusForm" property="messages">

		<ul class="normal">
		<logic:iterate name="metadataImportStatusForm" property="messages" id="message">
			<li><bean:write name="message" /></li>
		</logic:iterate>
		</ul>
		<div class="hr"></div>
		<p><a href="../action/metadataImportNew">Start new metadata import &raquo;</a></p>
	</logic:notEmpty>
	

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>