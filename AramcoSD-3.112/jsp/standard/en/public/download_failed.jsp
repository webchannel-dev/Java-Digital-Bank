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
<bright:applicationSetting id="webmasterEmail" settingName="webmaster-email"/>



<head>
	
	<title><bright:cmsWrite identifier="title-download-failure" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="download"/>
</head>

<body id="downloadLightbox">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-download-failure" filter="false" /></h1>

	<p>
	Sorry, an error has occurred whilst preparing your file for download.
	</p>
	<p>
		If this problem persists, please email <a href="mailto:<c:out value='${webmasterEmail}' />"/><c:out value='${webmasterEmail}' /></a>.
	</p>						
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>