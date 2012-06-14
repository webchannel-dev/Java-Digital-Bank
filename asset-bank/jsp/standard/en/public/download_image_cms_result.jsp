<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Chris Preager		24-May-2005		Imported from Image Manager
	 d2 		Ben Browning		17-Feb-2006		HTML/CSS tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bright:applicationSetting id="downloadRefreshPeriod" settingName="download-page-refresh-period"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Download Image</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<logic:empty name="downloadFile">
		<logic:notPresent name="downloadAttempts">
			<bean:define id="downloadAttempts" value="0"/>
		</logic:notPresent>
		<logic:notPresent name="emailAsset">
			<bean:define id="emailAsset" value="false"/>
		</logic:notPresent>
		<meta HTTP-EQUIV="refresh" CONTENT="<bean:write name='downloadRefreshPeriod'/>;URL=viewCmsDownloadProgress?filePath=<bean:write name="filePath"/>&downloadFilename=<bean:write name="downloadFilename"/>&downloadAttempts=<bean:write name='downloadAttempts'/>&emailAsset=<bean:write name='emailAsset'/>"></meta>
	</logic:empty>
	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="download_lightbox"/>
	<c:set var="pagetitle" value="Download Image"/>
</head>

<body id="downloadLightbox">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1>

	<h2><bright:cmsWrite identifier="subhead-please-wait-image-prepared" filter="false"/></h2>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>