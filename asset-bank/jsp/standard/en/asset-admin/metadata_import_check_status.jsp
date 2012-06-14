<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard		16-Jan-2008	Created.
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
	<bean:define id="pagetitle" value="Metadata Import: Checking file"/>

	<logic:equal name="metadataImportForm" property="checkInProgress" value="true">
		<meta HTTP-EQUIV="refresh" CONTENT="20;URL=metadataImportCheck"></meta>
	</logic:equal>
	

</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 


	<logic:equal name="metadataImportForm" property="checkInProgress" value="true">
	<p>The metadata file is currently being checked.</p>
	<p>This page will automatically refresh every 20 seconds until the check process finishes. If for some reason this does not happen you may 		<a href="metadataImportCheck">update the page</a> manually to check the status.</p>
	
	<div class="hr"></div>
	
	<c:choose>		
		<c:when test="${empty param.cancel}">
			<p><a href="metadataImportCancelCheck" onclick="return confirm('Are you sure you want to cancel the check?');">Cancel this check</a></p>
		</c:when>
		<c:otherwise>
			<p><em>Cancelling...</em></p>
		</c:otherwise>
	</c:choose>	
	</logic:equal>
	<logic:equal name="metadataImportForm" property="checkInProgress" value="false">
	<p>The metadata check has finished.</p>
	</logic:equal>

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>